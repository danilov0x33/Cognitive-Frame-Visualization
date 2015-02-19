/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter;


import java.util.ArrayList;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLProperty;

/**
 * Субаксиома, соответвующаа разным ограничения на 
 * некоторое (типизированное или объектное) свойство.
 * @author Lomov P. A.
 *
 */

public abstract class PropertyRestrictionSubAxiom extends ComplexSubAxiom
{
	
    
    
    /**
     * 
     */
    PropertyRestrictionSubAxiom()
    {
	super();
	
    }

    /**
     * 
     * @param title
     * @param sparqlVar
     * @param sparqlQueryPart
     * @param clsList
     * @param subAxOWL
     */
    public PropertyRestrictionSubAxiom(String title, String sparqlVar,
	    String sparqlQueryPart, ArrayList<OWLClass> clsList,
	    OWLClassExpression subAxOWL)
    {
	super(title, sparqlVar, sparqlQueryPart, clsList, subAxOWL);
	
    }


	/**
	 * Свойство, на которое делается ограничение.
	 */
	protected OWLProperty property;
	

	
	
	
	

	
	
	/**
	 * @return the property
	 */
	public OWLProperty getProperty()
	{
	    return property;
	}

	public IRI getPropertyIRI()
	{
		return this.getProperty().getIRI();
	}
	

	public String getPropertyShortIRI()
	{
		return this.getPropertyIRI().getFragment();
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(OWLProperty property)
	{
	    this.property = property;
	}


	
	
    
}
