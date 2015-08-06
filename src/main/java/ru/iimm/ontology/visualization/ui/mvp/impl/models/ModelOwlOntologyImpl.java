package ru.iimm.ontology.visualization.ui.mvp.impl.models;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import ru.iimm.ontology.visualization.ui.mvp.models.ModelOwlOntology;

/**
 * Реализация интерфейса {@linkplain ModelOwlOntology}
 * @author Danilov
 * @version 0.1
 */
public class ModelOwlOntologyImpl implements ModelOwlOntology
{
	private OWLOntology owlOntology;
	private OWLReasoner owlReas;
	private OWLOntologyManager ontMg;
	
	/**
	 * {@linkplain ModelOwlOntologyImpl}
	 */
	public ModelOwlOntologyImpl(OWLOntologyManager ontMg, OWLOntology owlOntology, OWLReasoner owlReas)
	{
		this.owlOntology = owlOntology;
		this.owlReas = owlReas;
		this.ontMg = ontMg;
	}
	
	@Override
	public OWLOntology getOWLOntology()
	{
		return this.owlOntology;
	}

	@Override
	public OWLReasoner getOWLReasoner()
	{
		return this.owlReas;
	}

	@Override
	public OWLOntologyManager getOntologyManager()
	{
		return this.ontMg;
	}

}
