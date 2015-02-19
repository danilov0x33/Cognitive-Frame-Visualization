/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

/**
 * Визитор, собирающей из выражения, которое будет далее 
 * субаксиомой, ограничения на свойства + субаксиомы из радиусов этих свойств.
 * Визитор обрабатывает выражения, которые не являются пересечениями и объединениями
 * (предполагается, что их быть не должно).
 * Данные визитор парсит выражение для создания из него только одной
 * субаксиомы.
 * @author Lomov P. A.
 *
 */
public class ElementCollectorVisitor implements OWLClassExpressionVisitor
{

    /**
     * Пары типа: [hasPart -- Head] из выражений типа: hasPart some Head
     */
    private ArrayList<PrpIRIandSubAxiom> objectPropList;	  
    
    /**
     * Пары типа: [hasName -- String] из выражений типа hasName String
     */
    private ArrayList<PairOfIRI> datypePropList;
    
    /**
     * Список отрицательных классов из выражения - типа Head из [not Head].
     */
    private ArrayList<OWLClass> negClassList;
    
    /**
     * Пары типа: [hasPart -- Head] из выражений типа: not hasPart some Head
     */
    private ArrayList<PrpIRIandSubAxiom> negObjectPropList;	  
    
    /**
     * Пары типа: [hasName -- String] из выражений типа not hasName String
     */
    private ArrayList<PairOfIRI> negDatypePropList;
    
    
    
    /**
     * 
     */
    ElementCollectorVisitor()
    {
	super();
	this.datypePropList = new ArrayList<PairOfIRI>();
	this.objectPropList = new ArrayList<PrpIRIandSubAxiom>();
	this.negClassList = new ArrayList<OWLClass>();
	this.negDatypePropList = new ArrayList<PairOfIRI>();
	this.negObjectPropList = new ArrayList<PrpIRIandSubAxiom>();
    }

    @Override
    public void visit(OWLClass clasExp)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void visit(OWLObjectIntersectionOf clasExp)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void visit(OWLObjectUnionOf clasExp)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void visit(OWLObjectComplementOf clasExp)
    {
	/* Отрицание из-за приведения к NNF может стоять перед:
	 * именованным классом
	 * неименованным классом - свойством, перечислением и др.*/
	
	ElementCollectorVisitor negCollectorVis = new ElementCollectorVisitor();
	OWLClassExpression clexp = clasExp.getOperand();
	
	clasExp.getOperand().accept(negCollectorVis);
	this.negClassList = negCollectorVis.getNegClassList();
	this.negDatypePropList = negCollectorVis.getNegDatypePropList();
	this.negObjectPropList = negCollectorVis.getNegObjectPropList();
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom clasExp)
    {
	OWLObjectProperty prp = clasExp.getProperty().asOWLObjectProperty();
	IRI prpIRI = prp.getIRI();
		
	/* Теперь получаем радиус свойства как ClassExp	 */
	OWLClassExpression rangeAx = clasExp.getFiller();
	
	
	/* Запускаем на радиус визитор, который вернет все субаксиомы из него */
	ClassExpressionParserVisitor vis = new ClassExpressionParserVisitor();
	rangeAx.accept(vis);

	/* Переносим то, что принес визитор в список: свойство -- субаксиома */
	for (SubAxiom subAx : vis.sbList)
	{
	    this.objectPropList.add(new PrpIRIandSubAxiom(prpIRI, subAx));
	}
    }

    @Override
    public void visit(OWLObjectAllValuesFrom clasExp)
    {
	OWLObjectProperty prp = clasExp.getProperty().asOWLObjectProperty();
	IRI prpIRI = prp.getIRI();
		
	/* Теперь получаем радиус свойства как ClassExp	 */
	OWLClassExpression rangeAx = clasExp.getFiller();
	
	
	/* Запускаем на радиус визитор, который вернет все субаксиомы из него */
	ClassExpressionParserVisitor vis = new ClassExpressionParserVisitor();
	rangeAx.accept(vis);

	/* Переносим то, что принес визитор в список: свойство -- субаксиома */
	for (SubAxiom subAx : vis.sbList)
	{
	    this.objectPropList.add(new PrpIRIandSubAxiom(prpIRI, subAx));
	}
    }

    @Override
    public void visit(OWLObjectHasValue clasExp)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void visit(OWLObjectMinCardinality clasExp)
    {
	OWLObjectProperty prp = clasExp.getProperty().asOWLObjectProperty();
	IRI prpIRI = prp.getIRI();
		
	/* Теперь получаем радиус свойства как ClassExp	 */
	OWLClassExpression rangeAx = clasExp.getFiller();
	
	
	/* Запускаем на радиус визитор, который вернет все субаксиомы из него */
	ClassExpressionParserVisitor vis = new ClassExpressionParserVisitor();
	rangeAx.accept(vis);

	/* Переносим то, что принес визитор в список: свойство -- субаксиома */
	for (SubAxiom subAx : vis.sbList)
	{
	    this.objectPropList.add(new PrpIRIandSubAxiom(prpIRI, subAx));
	}
    }

    @Override
    public void visit(OWLObjectExactCardinality clasExp)
    {
	OWLObjectProperty prp = clasExp.getProperty().asOWLObjectProperty();
	IRI prpIRI = prp.getIRI();
		
	/* Теперь получаем радиус свойства как ClassExp	 */
	OWLClassExpression rangeAx = clasExp.getFiller();
	
	
	/* Запускаем на радиус визитор, который вернет все субаксиомы из него */
	ClassExpressionParserVisitor vis = new ClassExpressionParserVisitor();
	rangeAx.accept(vis);

	/* Переносим то, что принес визитор в список: свойство -- субаксиома */
	for (SubAxiom subAx : vis.sbList)
	{
	    this.objectPropList.add(new PrpIRIandSubAxiom(prpIRI, subAx));
	}
	
    }

    @Override
    public void visit(OWLObjectMaxCardinality clasExp)
    {
	OWLObjectProperty prp = clasExp.getProperty().asOWLObjectProperty();
	IRI prpIRI = prp.getIRI();
		
	/* Теперь получаем радиус свойства как ClassExp	 */
	OWLClassExpression rangeAx = clasExp.getFiller();
	
	
	/* Запускаем на радиус визитор, который вернет все субаксиомы из него */
	ClassExpressionParserVisitor vis = new ClassExpressionParserVisitor();
	rangeAx.accept(vis);

	/* Переносим то, что принес визитор в список: свойство -- субаксиома */
	for (SubAxiom subAx : vis.sbList)
	{
	    this.objectPropList.add(new PrpIRIandSubAxiom(prpIRI, subAx));
	}
    }

    @Override
    public void visit(OWLObjectHasSelf clasExp)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void visit(OWLObjectOneOf clasExp)
    {
	// TODO Auto-generated method stub
	
    }

    
    @Override
    public void visit(OWLDataSomeValuesFrom clasExp)
    {
	/* Получаем IRI свойства	 */   	
	OWLDataProperty prp = clasExp.getProperty().asOWLDataProperty();
	IRI prpIRI = prp.getIRI();
	
	/* Получаем радиус свойства как IRI типа значения */
	OWLDatatype datatype =	OWLont.getOWLont().getDatatypeFromRange(prp.getIRI().toString());
	
	/* Добавляем экземпляр-соответсвующий новому радиусу/типу занчений */
	//OWLNamedIndividual datatype = UPOont.getUPOont().addDatatypeRangeToUPO(datatype.getIRI(), prp.getIRI());
		
	/* Добавляем свойство-значение в список свойств классов аксиомы*/
	this.datypePropList.add(new PairOfIRI(prpIRI, datatype.getIRI()));
	
    }

    /*TODO надо перенести добавление радиуса в ОПП в другой метод,
     * т.к. визитор должен только собирать*/
    @Override
    public void visit(OWLDataAllValuesFrom  clasExp)
    {
	/* Получаем IRI свойства	 */   	
	OWLDataProperty prp = clasExp.getProperty().asOWLDataProperty();
	IRI prpIRI = prp.getIRI();
	
	/* Получаем радиус свойства как IRI типа значения */
	OWLDatatype datatype =	OWLont.getOWLont().getDatatypeFromRange(prp.getIRI().toString());
	
	/* Добавляем экземпляр-соответсвующий новому радиусу/типу занчений */
	//OWLNamedIndividual datatype = UPOont.getUPOont().addDatatypeRangeToUPO			(datatype.getIRI(), prp.getIRI());
		
	/* Добавляем свойство-значение в список свойств классов аксиомы*/
	this.datypePropList.add(new PairOfIRI(prpIRI, datatype.getIRI()));
	
    }

    @Override
    public void visit(OWLDataHasValue  clasExp)
    {
	/* Получаем IRI свойства	 */   	
	OWLDataProperty prp = clasExp.getProperty().asOWLDataProperty();
	IRI prpIRI = prp.getIRI();
	
	/* Получаем радиус свойства как IRI типа значения */
	OWLDatatype datatype =	OWLont.getOWLont().getDatatypeFromRange(prp.getIRI().toString());
	
	/* Добавляем экземпляр-соответсвующий новому радиусу/типу занчений */
	//OWLNamedIndividual datatype = UPOont.getUPOont().addDatatypeRangeToUPO(datatype.getIRI(), prp.getIRI());
		
	/* Добавляем свойство-значение в список свойств классов аксиомы*/
	this.datypePropList.add(new PairOfIRI(prpIRI, datatype.getIRI()));
	
    }

    @Override
    public void visit(OWLDataMinCardinality  clasExp)
    {
	/* Получаем IRI свойства	 */   	
	OWLDataProperty prp = clasExp.getProperty().asOWLDataProperty();
	IRI prpIRI = prp.getIRI();
	
	/* Получаем радиус свойства как IRI типа значения */
	OWLDatatype datatype =	OWLont.getOWLont().getDatatypeFromRange(prp.getIRI().toString());
	
	/* Добавляем экземпляр-соответсвующий новому радиусу/типу занчений */
	//OWLNamedIndividual datatype = UPOont.getUPOont().addDatatypeRangeToUPO(datatype.getIRI(), prp.getIRI());
		
	/* Добавляем свойство-значение в список свойств классов аксиомы*/
	this.datypePropList.add(new PairOfIRI(prpIRI, datatype.getIRI()));
	
    }

    @Override
    public void visit(OWLDataExactCardinality  clasExp)
    {
	/* Получаем IRI свойства	 */   	
	OWLDataProperty prp = clasExp.getProperty().asOWLDataProperty();
	IRI prpIRI = prp.getIRI();
	
	/* Получаем радиус свойства как IRI типа значения */
	OWLDatatype datatype =	OWLont.getOWLont().getDatatypeFromRange(prp.getIRI().toString());
	
	/* Добавляем экземпляр-соответсвующий новому радиусу/типу занчений */
	//OWLNamedIndividual datatype = UPOont.getUPOont().addDatatypeRangeToUPO(datatype.getIRI(), prp.getIRI());
		
	/* Добавляем свойство-значение в список свойств классов аксиомы*/
	this.datypePropList.add(new PairOfIRI(prpIRI, datatype.getIRI()));
    }

    @Override
    public void visit(OWLDataMaxCardinality  clasExp)
    {
	/* Получаем IRI свойства	 */   	
	OWLDataProperty prp = clasExp.getProperty().asOWLDataProperty();
	IRI prpIRI = prp.getIRI();
	
	/* Получаем радиус свойства как IRI типа значения */
	OWLDatatype datatype =	OWLont.getOWLont().getDatatypeFromRange(prp.getIRI().toString());
	
	/* Добавляем экземпляр-соответсвующий новому радиусу/типу занчений */
	//OWLNamedIndividual datatype = UPOont.getUPOont().addDatatypeRangeToUPO(datatype.getIRI(), prp.getIRI());
		
	/* Добавляем свойство-значение в список свойств классов аксиомы*/
	this.datypePropList.add(new PairOfIRI(prpIRI, datatype.getIRI()));
    }

    
    

    /**
     * @return the prpFromAxList
     */
    public ArrayList<PrpIRIandSubAxiom> getObjectPropList()
    {
        return objectPropList;
    }

    /**
     * @return the datypePropList
     */
    public ArrayList<PairOfIRI> getDatypePropList()
    {
        return datypePropList;
    }

    /**
     * @return the negClassList
     */
    public ArrayList<OWLClass> getNegClassList()
    {
        return negClassList;
    }

    /**
     * @return the negObjectPropList
     */
    public ArrayList<PrpIRIandSubAxiom> getNegObjectPropList()
    {
        return negObjectPropList;
    }

    /**
     * @return the negDatypePropList
     */
    public ArrayList<PairOfIRI> getNegDatypePropList()
    {
        return negDatypePropList;
    }

    
    
}
