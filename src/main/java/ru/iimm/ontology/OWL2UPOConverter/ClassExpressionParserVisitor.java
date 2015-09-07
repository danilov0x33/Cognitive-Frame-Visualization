/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter;

import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
import org.semanticweb.owlapi.model.OWLRestriction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.OWL2UPOConverter.parsedAxioms.SubAxiom;
import ru.iimm.ontology.ontAPI.Ontology;

/**
 * Визитор возвращает список субаксиом, найденных 
 * в выражении.
 * Данный визитор инициирует создание (нескольких) субаксиом из выражения.
 * Этот визитор  первым  вызывается для разбора части (правой) аксиомы.
 * 
 * Он приводит выражение к ДНФ и ННФ, и оно выглядит вот так:
 * (A and B) or С or [hasValue some F] or [hasName some (D or (G and T)) ] or
 * (A and [hasValue some F])
 * 
 * Далее на каждый коньюнкт натравливается визитор (SubAxiomCreatorVisitor), который должен вызвать
 * соответвующий метод фабрики для получения ОДНОЙ субаксиомы.
 * 
 * Субаксиома может быть:
 * 1) простой - типа (А) -> простая субакс.
 * 2) простым ограничением - типа  [hasValue some F] -> простое ограничение на объектное свойство
 * 3) набором простых ограничений - типа [hasValue some F or B or C] -> сложная субаксиома, соединенная с простыми субаксиомами
 * 4) сложным ограничением - типа	[hasValue some F and B] -> сложная субаксиома, соединенная со сложной субаксиомой
 * 5) комбинированного (т.е. с простыми и сложными субаксиомами) ограничения - 
 * типа [hasName some (D or (G and T)) ] или [hasName some (D or [hasValue M]  ]
 * ... пока это все, что рассматривается, но есть еще
 * 6) простым отрицанием - типа (not A)
 * 7) сложным отрицанием - типа (not (A and B))
 * 9) ограничения на типизированные свойства ...
 * 10) пересечения и др
 * 
 * Для перечисленнвх ситуаций вызываются следующие фабричные методы:
 * 1) getSimpleSubaxiom
 * 2) getSimpleRestrictionSubaxiom
 * 3) 4) 5) getGeneralComplexSubAxiom - вернется сложная субаксиома, в которой будут 2 связи 
 * (hasName) с порожденными субаксиомами - простой (D) и сложной (G and T)
 * 
 * Для обработки оставшихся типов следует:
 * 1) создать методы фабрики
 * 2) вызвать их из визитора SubAxiomCreatorVisitor 
 * 
 * @author Lomov P. A.
 *
 */
public class ClassExpressionParserVisitor implements OWLClassExpressionVisitor
{
    
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(ClassExpressionParserVisitor.class);

    /**
     * Список субаксиом, найденных в выражении.
     */
    ArrayList<SubAxiom> sbList;
    
    /**
     * Визитор, который будет создавать субаксиомы из фрагментов выражения.
     */
    private SubAxiomCreatorVisitor sbAxCreator; 

    
    /**
     * @param ont
     */
    public ClassExpressionParserVisitor()
    {
	super();
	this.sbAxCreator = new SubAxiomCreatorVisitor();
	this.sbList= new ArrayList<SubAxiom>();
    }


    public ArrayList<SubAxiom> getSbList()
    {
        return sbList;
    }

    public void setSbList(ArrayList<SubAxiom> sbList)
    {
        this.sbList = sbList;
    }

    /*---------------------------------------
     * -----Visit methods-------------------
     * ------------------------------------*/
    
    
    
    @Override
    public void visit(OWLClass clExp)
    {		
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	clExp.accept(this.sbAxCreator);	
	SubAxiom sb = sbAxCreator.getSbAx();
	if (sb != null) this.sbList.add(sb);
	
	printResultMessages(clExp);
    }

    @Override
    public void visit(OWLObjectIntersectionOf clExp)
    {/*создаем субаксиому для ПЕРЕСЕЧЕНИЯ классов*/
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());

    	/* Приводим выражение к ДНФ и ННФ... */
    	OWLClassExpression exp = OWLont.getOWLont().getDNF(clExp).getNNF().getNNF();
    	LOGGER.info(" Modified exp: {}", exp);
    	/* ... т.е. выражение = одна или несколько коньюнкций 
    	 * 	анонимных и/или именованных классов. Получаем набор этих коньюнкций...  */
    	Set<OWLClassExpression> axDNSet = exp.asDisjunctSet();
	
    	/*Из каждого выражения (к)...*/
    	for (OWLClassExpression clasExp : axDNSet)
    	{
	    /*... создаем субаксиому c помощью визитора */
    		clasExp.accept(sbAxCreator);
    		SubAxiom sb = sbAxCreator.getSbAx();
    		if (sb != null) this.sbList.add(sb);
    		

    	}
    	printResultMessages(clExp);
    }

    @Override
    public void visit(OWLObjectUnionOf clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
    	
	OWLClassExpression exp = OWLont.getOWLont().getDNF(clExp).getNNF();
    	LOGGER.info(" Modified exp: {}", exp);
    	
    	/* ... т.е. выражение = одна или несколько коньюнкций 
    	 * анонимных и/или именованных классов. Получаем набор этих коньюнкций...  */
    	Set<OWLClassExpression> axDNSet = exp.asDisjunctSet();
    	
    	
    	/*Из каждого выражения (к)...*/
    	for (OWLClassExpression clasExp : axDNSet)
    	{
    		/*... создаем субаксиому c помощью визитора */
    		clasExp.accept(sbAxCreator);
    		SubAxiom sb = sbAxCreator.getSbAx();
    		if (sb != null) this.sbList.add(sb);
    		
    	}
	
    	printResultMessages(clExp);
    }

    @Override
    public void visit(OWLObjectComplementOf clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());

	/*Т.к. это отрицание, то сначала в NNF а потов DNF*/
    	OWLClassExpression exp = OWLont.getOWLont().getDNF(clExp.getNNF());
    	LOGGER.info(" Modified exp: {}", exp);
    	/* ... т.е. выражение = одна или несколько коньюнкций 
    	 * анонимных и/или именованных классов. Получаем набор этих коньюнкций...  */
    	Set<OWLClassExpression> axDNSet = exp.asDisjunctSet();
	
    	/*Из каждого выражения (к)...*/
    	for (OWLClassExpression clasExp : axDNSet)
    	{
    		/*... создаем субаксиому c помощью визитора */
    		clasExp.accept(sbAxCreator);
    		SubAxiom sb = sbAxCreator.getSbAx();
    		if (sb != null) this.sbList.add(sb);
    		
    	}
    	printResultMessages(clExp);
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
    	/*... создаем субаксиому c помощью визитора */		
	clExp.accept(sbAxCreator);
	SubAxiom sb = sbAxCreator.getSbAx();
	if (sb != null) this.sbList.add(sb);
	
	printResultMessages(clExp);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());

    	/*... создаем субаксиому c помощью визитора */
	clExp.accept(sbAxCreator);
	SubAxiom sb = sbAxCreator.getSbAx();
	if (sb != null) this.sbList.add(sb);
	printResultMessages(clExp);
    }

    @Override
    public void visit(OWLObjectHasValue clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	    /*... создаем субаксиому c помощью визитора */
	    clExp.accept(sbAxCreator);
	    SubAxiom sb = sbAxCreator.getSbAx();
	    if (sb != null) this.sbList.add(sb);
	    

	    printResultMessages(clExp);
    }

    @Override
    public void visit(OWLObjectMinCardinality clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	    /*... создаем субаксиому c помощью визитора */
	    clExp.accept(sbAxCreator);
	    SubAxiom sb = sbAxCreator.getSbAx();
	    if (sb != null) this.sbList.add(sb);
	    
	    printResultMessages(clExp);
    }

    @Override
    public void visit(OWLObjectExactCardinality clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	    /*... создаем субаксиому c помощью визитора */
	    clExp.accept(sbAxCreator);
	    SubAxiom sb = sbAxCreator.getSbAx();
	    if (sb != null) this.sbList.add(sb);
	    

	    printResultMessages(clExp);
    }

    @Override
    public void visit(OWLObjectMaxCardinality clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	    /*... создаем субаксиому c помощью визитора */
	    clExp.accept(sbAxCreator);
	    SubAxiom sb = sbAxCreator.getSbAx();
	    if (sb != null) this.sbList.add(sb);
	    

	    printResultMessages(clExp);
    }

    @Override
    public void visit(OWLObjectHasSelf clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	    /*... создаем субаксиому c помощью визитора */
	    clExp.accept(sbAxCreator);
	    SubAxiom sb = sbAxCreator.getSbAx();
	    if (sb != null) this.sbList.add(sb);
	    

	    printResultMessages(clExp);
    }

    @Override
    public void visit(OWLObjectOneOf clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	    /*... создаем субаксиому c помощью визитора */
	    clExp.accept(sbAxCreator);
	    SubAxiom sb = sbAxCreator.getSbAx();
	    if (sb != null) this.sbList.add(sb);
	    

	    printResultMessages(clExp);
    }

    @Override
    public void visit(OWLDataSomeValuesFrom clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	/*... создаем субаксиому c помощью визитора */
	clExp.accept(sbAxCreator);
	SubAxiom sb = sbAxCreator.getSbAx();
	if (sb != null) this.sbList.add(sb);
	
	printResultMessages(clExp);
    }

    @Override
    public void visit(OWLDataAllValuesFrom clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	clExp.accept(sbAxCreator);
	SubAxiom sb = sbAxCreator.getSbAx();
	if (sb != null) this.sbList.add(sb);
	
	printResultMessages(clExp);
    }

    @Override
    public void visit(OWLDataHasValue clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	clExp.accept(sbAxCreator);
	SubAxiom sb = sbAxCreator.getSbAx();
	if (sb != null) this.sbList.add(sb);
	
	printResultMessages(clExp);
    }

    @Override
    public void visit(OWLDataMinCardinality clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	clExp.accept(sbAxCreator);
	SubAxiom sb = sbAxCreator.getSbAx();
	if (sb != null) this.sbList.add(sb);
	
	printResultMessages(clExp);
    }

    @Override
    public void visit(OWLDataExactCardinality clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	clExp.accept(sbAxCreator);
	SubAxiom sb = sbAxCreator.getSbAx();
	if (sb != null) this.sbList.add(sb);
	
	printResultMessages(clExp);
    }

    @Override
    public void visit(OWLDataMaxCardinality clExp)
    {
	LOGGER.info(" -----------------------------------------------------------------");
	LOGGER.info(" Parse ["+clExp+"]  as: " + clExp.getClass().getSimpleName());
	
	clExp.accept(sbAxCreator);
	SubAxiom sb = sbAxCreator.getSbAx();
	if (sb != null) this.sbList.add(sb);
	
	printResultMessages(clExp);
    }
    
    
    ///////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
    
    /**
     * 
     * @param restr
     * @return
     */
    private ArrayList<SubAxiom> getRangeSubAxiom(OWLQuantifiedRestriction restr)
    {
	/* Теперь получаем радиус свойства как ClassExp	 */
	OWLClassExpression rangeAx = (OWLClassExpression) restr.getFiller();
	
	
	/* Запускаем на радиус визитор, который вернет все субаксиомы из него */
	ClassExpressionParserVisitor vis = new ClassExpressionParserVisitor();
	rangeAx.accept(vis);

	return vis.sbList;
    }
    
    /**
     * Выводит сообщения
     * @param initialExp
     */
    private void printResultMessages(OWLClassExpression initialExp)
    {
	LOGGER.info(" -- From: "+initialExp+" get: "+ this.sbList.size() +" subaxioms");
	LOGGER.info(" -----------------------------------------------------------------");
	


    }

}
