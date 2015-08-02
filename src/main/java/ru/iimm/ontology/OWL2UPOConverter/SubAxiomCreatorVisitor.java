/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Визитор, возвращающий субаксиому из посещаемого выражения.
 * Он инициирует вызов необходимого фабричного метода. 
 * @author Lomov P. A.
 *
 */
public class SubAxiomCreatorVisitor implements OWLClassExpressionVisitor
{
    
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SubAxiomCreatorVisitor.class);
    
    /**
     * Созданная субаксиома.
     */
    private SubAxiom sbAx;

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLClass)
     */
    @Override
    public void visit(OWLClass clExp)
    {
	this.sbAx = DataFactory.getFactory().getSimpleSubAxiom(clExp.getIRI(), UPOont.getUPOont());
	LOGGER.info(" Create subaxiom from: "+ clExp);
	LOGGER.info(" Get subax type: "+ this.sbAx.getClass());
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLObjectIntersectionOf)
     */
    @Override
    public void visit(OWLObjectIntersectionOf clExp)
    {
	this.sbAx = DataFactory.getFactory().getComplexSubaxiom(clExp);
	LOGGER.info(" Create subaxiom from: "+ clExp);
	LOGGER.info(" Get subax type: "+ this.sbAx.getClass());
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLObjectUnionOf)
     */
    @Override
    public void visit(OWLObjectUnionOf clExp)
    {
	//this.sbAx = DataFactory.getFactory().getSubaxiom(clExp, OWLont.getOWLont(), UPOont.getUPOont());
	LOGGER.info("!!WARN!! In this expression shound not be a UNION_OF like That:");
	LOGGER.info("!!WARN!! ", clExp);
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLObjectComplementOf)
     */
    

    @Override
    public void visit(OWLObjectComplementOf clExp)
    {
	OWLClassExpression operand = clExp.getOperand();
	if (!operand.isAnonymous())
	{/*... создаем субаксиому отрицание именованного класса */
	    this.sbAx = DataFactory.getFactory().
		    getSimpleComplementSubAxiom(operand.asOWLClass().getIRI(), UPOont.getUPOont());
	} else
	{/*... иначе - инициируем создание отрицания сложной аксиомы,
 		связанной с субаксиомой/ами из радиуса. */
		this.sbAx = DataFactory.getFactory().getComplexComplementSubAxiom(operand, UPOont.getUPOont()); 
	}

	LOGGER.info(" Create subaxiom from: "+ clExp);
	LOGGER.info(" Get subax type: "+ this.sbAx.getClass());
    }

    /**
     * Если выражение...
     * @param clExp
     */
    @Override
    public void visit(OWLObjectSomeValuesFrom clExp)
    {/*Если это простое ограничение ... */
	if (!clExp.getFiller().isAnonymous())
		{/*... создаем субаксиому - простого ограничения. */
			this.sbAx = DataFactory.getFactory().getSimpleObjectRestrictionSubAxiom(clExp);
		} else
		{/*... иначе - инициируем создание сложной аксиомы для ограничения,
	 	связанной с субаксиомой/ами из радиуса. */
			this.sbAx = DataFactory.getFactory().getComplexSubaxiom(clExp); 
		}
	LOGGER.info(" Create subaxiom from: "+ clExp);
	LOGGER.info(" Get subax type: "+ this.sbAx.getClass());
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLObjectAllValuesFrom)
     */
    
    @Override
    public void visit(OWLObjectAllValuesFrom clExp)
    {
    	/* стоит ли данный вид ограничения инерпретировать как связь - вероятно лучше
    	 * нет т.е. наличие даного огранияния не означай что связь между классами существуют
    	 * пока отключил создание субаксиом*/

	/*Если это простое ограничние... */
	if (!clExp.getFiller().isAnonymous())
		{/*... создаем субаксиому - простого ограничения. */
//-			this.sbAx = DataFactory.getFactory().getSimpleObjectRestrictionSubAxiom(clExp);
		} else
		{/*... иначе - инициируем создание сложной аксиомы для ограничения,
	 	связанной с субаксиомой/ами из радиуса. */
//-			this.sbAx = DataFactory.getFactory().getComplexSubaxiom(clExp); 
		}
	LOGGER.info(" Create subaxiom from: "+ clExp);
//-	LOGGER.info(" Get subax type: "+ this.sbAx.getClass());	
    }


    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLObjectHasValue)
     */
    @Override
    public void visit(OWLObjectHasValue clExp)
    {
	LOGGER.info("!!WARN!! Handling of such type of classExpression is not availiable now:");
	LOGGER.info("!!WARN!! ",clExp );

    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLObjectMinCardinality)
     */
    @Override
    public void visit(OWLObjectMinCardinality clExp)
    {/*Если это простое ограничние... */
	if (!clExp.getFiller().isAnonymous())
		{/*... создаем субаксиому - простого ограничения. */
			this.sbAx = DataFactory.getFactory().getSimpleObjectRestrictionSubAxiom(clExp);
		} else
		{/*... иначе - инициируем создание сложной аксиомы для ограничения,
	 	связанной с субаксиомой/ами из радиуса. */
			this.sbAx = DataFactory.getFactory().getComplexSubaxiom(clExp); 
		}
	LOGGER.info(" Create subaxiom from: "+ clExp);
	LOGGER.info(" Get subax type: "+ this.sbAx.getClass());	
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLObjectExactCardinality)
     */
    @Override
    public void visit(OWLObjectExactCardinality clExp)
    {/*Если это простое ограничние... */
	if (!clExp.getFiller().isAnonymous())
		{/*... создаем субаксиому - простого ограничения. */
			this.sbAx = DataFactory.getFactory().getSimpleObjectRestrictionSubAxiom(clExp);
		} else
		{/*... иначе - инициируем создание сложной аксиомы для ограничения,
	 	связанной с субаксиомой/ами из радиуса. */
			this.sbAx = DataFactory.getFactory().getComplexSubaxiom(clExp); 
		}
	LOGGER.info(" Create subaxiom from: "+ clExp);
	LOGGER.info(" Get subax type: "+ this.sbAx.getClass());	
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLObjectMaxCardinality)
     */
    @Override
    public void visit(OWLObjectMaxCardinality clExp)
    {/*Если это простое ограничние... */
	if (!clExp.getFiller().isAnonymous())
		{/*... создаем субаксиому - простого ограничения. */
			this.sbAx = DataFactory.getFactory().getSimpleObjectRestrictionSubAxiom(clExp);
		} else
		{/*... иначе - инициируем создание сложной аксиомы для ограничения,
	 	связанной с субаксиомой/ами из радиуса. */
			this.sbAx = DataFactory.getFactory().getComplexSubaxiom(clExp); 
		}
	LOGGER.info(" Create subaxiom from: "+ clExp);
	LOGGER.info(" Get subax type: "+ this.sbAx.getClass());	
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLObjectHasSelf)
     */
    @Override
    public void visit(OWLObjectHasSelf clExp)
    {
	LOGGER.info("!!WARN!! Handling of such type of classExpression is not availiable now:");
	LOGGER.info("!!WARN!! ",clExp );

    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLObjectOneOf)
     */
    @Override
    public void visit(OWLObjectOneOf clExp)
    {
//this.sbAx = DataFactory.getFactory().getSubaxiom(clExp, OWLont.getOWLont(), UPOont.getUPOont());
	LOGGER.info("!!WARN!! Handling of such type of classExpression is not availiable now:");
	LOGGER.info("!!WARN!! ",clExp );
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLDataSomeValuesFrom)
     */
    @Override

    /* TODO разобраться с генерацие субаксимом для типизированных свойств*/
    
    public void visit(OWLDataSomeValuesFrom clExp)
    {
	/*Если это простое ограничние (в значении - протой тип)... */
	if (clExp.getFiller().isDatatype())
	{/*... создаем субаксиому - простого типизированного ограничения. */
	    this.sbAx = DataFactory.getFactory().getSimpleDataRestrictionSubaxiom(clExp);
	    return;
	}
	
	LOGGER.info("!!WARN!! Handling of such type of classExpression is not availiable now:");
	LOGGER.info("!!WARN!! ",clExp );
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLDataAllValuesFrom)
     */
    @Override
    public void visit(OWLDataAllValuesFrom clExp)
    {
	if (clExp.getFiller().isDatatype())
	{
	    this.sbAx = DataFactory.getFactory().getSimpleDataRestrictionSubaxiom(clExp);
	    return;
	}
	
	LOGGER.info("!!WARN!! Handling of such type of classExpression is not availiable now:");
	LOGGER.info("!!WARN!! ",clExp );
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLDataHasValue)
     */
    @Override
    public void visit(OWLDataHasValue clExp)
    {
//this.sbAx = DataFactory.getFactory().getSubaxiom(clExp, OWLont.getOWLont(), UPOont.getUPOont());
	LOGGER.info("!!WARN!! Handling of such type of classExpression is not availiable now:");
	LOGGER.info("!!WARN!! ",clExp );
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLDataMinCardinality)
     */
    @Override
    public void visit(OWLDataMinCardinality clExp)
    {
	if (clExp.getFiller().isDatatype())
	{
	    this.sbAx = DataFactory.getFactory().getSimpleDataRestrictionSubaxiom(clExp);
	    return;
	}
	LOGGER.info("!!WARN!! Handling of such type of classExpression is not availiable now:");
	LOGGER.info("!!WARN!! ",clExp );
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLDataExactCardinality)
     */
    @Override
    public void visit(OWLDataExactCardinality clExp)
    {
	if (clExp.getFiller().isDatatype())
	{
	    this.sbAx = DataFactory.getFactory().getSimpleDataRestrictionSubaxiom(clExp);
	    return;
	}

	LOGGER.info("!!WARN!! Handling of such type of classExpression is not availiable now:");
	LOGGER.info("!!WARN!! ",clExp );
    }

    /* (non-Javadoc)
     * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor#visit(org.semanticweb.owlapi.model.OWLDataMaxCardinality)
     */
    @Override
    public void visit(OWLDataMaxCardinality clExp)
    {
	if (clExp.getFiller().isDatatype())
	{
	    this.sbAx = DataFactory.getFactory().getSimpleDataRestrictionSubaxiom(clExp);
	    return;
	}
 
	LOGGER.info("!!WARN!! Handling of such type of classExpression is not availiable now:");
	LOGGER.info("!!WARN!! ",clExp );
    }

    /**
     * @return the sbAx
     */
    public SubAxiom getSbAx()
    {
        return sbAx;
    }

}
