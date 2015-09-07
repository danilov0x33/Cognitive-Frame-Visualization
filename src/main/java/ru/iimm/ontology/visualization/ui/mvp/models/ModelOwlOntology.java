package ru.iimm.ontology.visualization.ui.mvp.models;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * Модель предстовляющая онтологию.
 * @author Danilov
 * @version 0.1
 */
public interface ModelOwlOntology
{
	OWLOntology getOWLOntology();
	OWLReasoner getOWLReasoner();
	OWLOntologyManager getOntologyManager();
}
