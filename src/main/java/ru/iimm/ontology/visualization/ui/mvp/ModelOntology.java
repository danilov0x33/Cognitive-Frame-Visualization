package ru.iimm.ontology.visualization.ui.mvp;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public interface ModelOntology
{

	/**
	 * @return
	 */
	OWLOntology getOWLOntology();

	/**
	 * @return
	 */
	OWLReasoner getOWLReasoner();

}
