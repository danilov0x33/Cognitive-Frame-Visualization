package ru.iimm.ontology.OWL2UPOConverter.parsedAxioms;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt;



/**
 * Субаксиома - простое ограничение на объектное свойство.
 * Пример: hasMotor some Motor 
 * @author Lomov P.A.
 */
class SimpleObjectRestrictionSubAxiom extends PropertyRestrictionSubAxiom
{
    
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SimpleObjectRestrictionSubAxiom.class);
    
    /**
     * Выражение из радиуса ограничения. 
     */
    protected OWLClass restrictionRange;

    
    /**
     * 
     */
    SimpleObjectRestrictionSubAxiom()
    {
	super();
	// TODO Auto-generated constructor stub
    }


    /**
     * @param title
     * @param sparqlVar
     * @param sparqlQueryPart
     * @param clsList
     * @param subAxOWL
     */
    public SimpleObjectRestrictionSubAxiom(String title, String sparqlVar,
	    String sparqlQueryPart, ArrayList<OWLClass> clsList,
	    OWLClassExpression subAxOWL, OWLObjectProperty prp, OWLClass range)
    {
	super(title, sparqlVar, sparqlQueryPart, clsList, subAxOWL);
	this.property= prp;
	this.restrictionRange = range;
    }


	@Override
	public OWLProperty getProperty()
	{
		return this.property.asOWLObjectProperty();
	}



	/**
	 * @return the restrictionRange
	 */
	public OWLClass getRestrictionRange()
	{
	    return restrictionRange;
	}


	/**
	 * @param restrictionRange the restrictionRange to set
	 */
	public void setRestrictionRange(OWLClass restrictionRange)
	{
	    this.restrictionRange = restrictionRange;
	}


	/* (non-Javadoc)
	 * @see ru.iimm.ontology.OWL2UPOConverter.ComplexSubAxiom#addSubaxiom(ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt)
	 */
	@Override
	public OWLNamedIndividual addSubaxiom(UserPresenOnt upo)
	{
	    /* Получаем экземпляр (IRI) простой субаксимы = классу из радиуса */
	    SimpleSubAxiom rangeSubAx = DataFactory.getFactory().
		    getSimpleSubAxiom(this.getRestrictionRange().getIRI(), upo);
	    return rangeSubAx.getSubAxIndividual();
	}


	/**
	 * Добавление субаксиомы-простого ограничения вида: hasPart-some HEAD
	 * происходит так: ind ---hasPart---> Head.
	 * Передаваемое отношение заменяется отношением из субаксиомы.
	 */
	@Override
	public OWLNamedIndividual addRelatedSubAxiom(
		OWLNamedIndividual mainSubaxInd, IRI relationIRI, UserPresenOnt upo)
	{
	    //addRelatedSubAxiom(OWLNamedIndividual ind, SimpleObjectRestrictionSubAxiom relatedSubAx, IRI relationIRI)
	    /* Получаем экземпляр (IRI) простой субаксимы = классу из радиуса */
	    SimpleSubAxiom rangeSubAx = DataFactory.getFactory().
		    getSimpleSubAxiom(this.getRestrictionRange().getIRI(), upo);

	    /* Соединяем левую и правую субаксиому отношением
	     * из ограничения */
	    upo.makeObjPrpOBetweenElements(mainSubaxInd.getIRI(), 
		    this.getPropertyIRI(), rangeSubAx.getSubAxIndividual().getIRI(), false);
	    
	    LOGGER.info(" "+mainSubaxInd.getIRI().getFragment()+
		    " --"+this.getProperty().getIRI().getFragment()+"--> "+ 
		    rangeSubAx.getSubAxIndividual().getIRI().getFragment());
	    return rangeSubAx.getSubAxIndividual();
	}
}
