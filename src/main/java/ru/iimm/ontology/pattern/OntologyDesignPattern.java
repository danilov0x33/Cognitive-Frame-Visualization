package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.OWLDataFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public abstract class OntologyDesignPattern 
{
	protected OWLDataFactory df;

	public OntologyDesignPattern() 
	{
		this.df = new OWLDataFactoryImpl();
	}

	public OWLDataFactory getOWLDataFactory()
	{
		return df;
	}
}
