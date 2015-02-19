/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * @author Lomov P. A.
 *
 */
public abstract class ComplementSubAxiom extends ComplexSubAxiom
{
    /**
     * Экземпляр из ОПП, соответствующий отрицаемому классу. 
     */
    private OWLNamedIndividual subAxIndividual;

    
    
    /* (non-Javadoc)
     * @see ru.iimm.ontology.OWL2UPOConverter.ComplexSubAxiom#getNewTitle()
     */
    @Override
    protected String generateTitle()
    {
	String title = ConstantsOntConverter.UPO_NEGATION_LABEL_TEMPLATE + 
		ConstantsOntConverter.UPO_TITLE_DELIMITER + super.generateTitle();

	return title;
	//"["+ConstantsOntConverter.UPO_NEGATION_LABEL_TEMPLATE+"-"+classIRI.getFragment()+"]"
    }

    /**
     * @param title
     * @param sparqlVar
     * @param sparqlQueryPart
     * @param clsList
     * @param subAxOWL
     */
    public ComplementSubAxiom(String title, String sparqlVar,
	    String sparqlQueryPart, ArrayList<OWLClass> clsList,
	    OWLClassExpression subAxOWL)
    {
	super(title, sparqlVar, sparqlQueryPart, clsList, subAxOWL);
	// TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    public ComplementSubAxiom()
    {
	// TODO Auto-generated constructor stub
    }

    /**
     * @return the subAxIndividual
     */
    public OWLNamedIndividual getSubAxIndividual()
    {
        return subAxIndividual;
    }

    /**
     * @param subAxIndividual the subAxIndividual to set
     */
    public void setSubAxIndividual(OWLNamedIndividual subAxIndividual)
    {
        this.subAxIndividual = subAxIndividual;
    }

}
