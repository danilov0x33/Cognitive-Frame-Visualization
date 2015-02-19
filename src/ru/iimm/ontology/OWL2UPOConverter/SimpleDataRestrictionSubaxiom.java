/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter;

import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLProperty;

/**
 * Субаксиома включающая простое ограничение на типизированное свойство
 * Типа: Human hasName [string]
 * 
 * @author Lomov P. A.
 *
 */
public class SimpleDataRestrictionSubaxiom extends PropertyRestrictionSubAxiom
{
    
    /**
     * Тип в значении свойства.
     */
    OWLDatatype rangeType;

    /**
     * @return the rangeType
     */
    public OWLDatatype getRangeType()
    {
        return rangeType;
    }

    /**
     * @param rangeType the rangeType to set
     */
    public void setRangeType(OWLDatatype rangeType)
    {
        this.rangeType = rangeType;
    }


    /**
     * 
     */
    public SimpleDataRestrictionSubaxiom()
    {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param title
     * @param sparqlVar
     * @param sparqlQueryPart
     * @param clsList
     * @param subAxOWL
     */
    public SimpleDataRestrictionSubaxiom(String title, String sparqlVar,
	    String sparqlQueryPart, ArrayList<OWLClass> clsList,
	    OWLClassExpression subAxOWL)
    {
	super(title, sparqlVar, sparqlQueryPart, clsList, subAxOWL);
	// TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see ru.iimm.ontology.OWL2UPOConverter.ComplexSubAxiom#addSubaxiom(ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt)
     */
    @Override
    public OWLNamedIndividual addSubaxiom(UserPresenOnt upo)
    {	
	// добавляем типы в ОПП как SKOS концепты			
	OWLNamedIndividual rangeInd = 
				upo.addDatatypeRangeToUPO(this.getRangeType().getIRI(),this.getPropertyIRI());    
	return rangeInd;
    }

    /* (non-Javadoc)
     * @see ru.iimm.ontology.OWL2UPOConverter.ComplexSubAxiom#addRelatedSubAxiom(org.semanticweb.owlapi.model.OWLNamedIndividual, org.semanticweb.owlapi.model.IRI, ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt)
     */
    @Override
    public OWLNamedIndividual addRelatedSubAxiom(
	    OWLNamedIndividual mainSubaxInd, IRI relationIRI, UserPresenOnt upo)
    {
	OWLNamedIndividual rangeInd = this.addSubaxiom(upo);
	
	// связываем SKOS концепт левой субаксиомы со SKOS концептом - его типом
	upo.makeObjPrpOBetweenElements(mainSubaxInd.getIRI().toString(), 
		relationIRI.toString(), rangeInd.getIRI().toString(), true);
	
	return rangeInd;
    }


}
