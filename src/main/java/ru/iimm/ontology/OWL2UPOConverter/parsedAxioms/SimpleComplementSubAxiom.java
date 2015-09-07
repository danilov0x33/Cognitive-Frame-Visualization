/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter.parsedAxioms;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * Субаксиома - отрицание именованного класса.
 * @author Lomov P. A.
 *
 */
public class SimpleComplementSubAxiom extends ComplementSubAxiom
{
    
    


    /**
     * @param title
     * @param sparqlVar
     * @param sparqlQueryPart
     * @param clsList
     * @param subAxOWL
     */
    public SimpleComplementSubAxiom(String title, String sparqlVar,
	    String sparqlQueryPart, ArrayList<OWLClass> clsList,
	    OWLClassExpression subAxOWL)
    {
	super(title, sparqlVar, sparqlQueryPart, clsList, subAxOWL);
	// TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    public SimpleComplementSubAxiom()
    {
	// TODO Auto-generated constructor stub
    }
        
    
    public SimpleComplementSubAxiom(SimpleSubAxiom sbAx)
    {
	super();
	this.setClsList(sbAx.getClsList());

    }
    

    
    
}
