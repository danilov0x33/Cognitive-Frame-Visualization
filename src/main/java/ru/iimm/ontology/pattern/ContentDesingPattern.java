package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import ru.iimm.ontology.ontAPI.Ontology;

public abstract class ContentDesingPattern extends OntologyDesignPattern
{
	private Ontology CDPOntology;
	
	private IRI OntologyIRI;
	private String BaseIRI;
	
	public ContentDesingPattern(Ontology cDPOntology, IRI ontologyIRI) 
	{
		CDPOntology = cDPOntology;
		OntologyIRI = ontologyIRI;
		BaseIRI = ontologyIRI.toString()+"#";
	}

	public ContentDesingPattern(IRI ontologyIRI, String dirPath, String filename) 
	{
		OntologyIRI = ontologyIRI;
		CDPOntology = new Ontology(dirPath, filename, false, false);
		BaseIRI = ontologyIRI.toString()+"#";
	}
	
	public Ontology getCDPOntology() {
		return CDPOntology;
	}

	public void setCDPOntology(Ontology cDPOntology) {
		CDPOntology = cDPOntology;
	}

	/**
	 * @return the {@linkplain #ontologyIRI}
	 */
	public IRI getOntologyIRI()
	{
		return OntologyIRI;
	}

	/**
	 * @param ontologyIRI the {@linkplain #ontologyIRI} to set
	 */
	public void setOntologyIRI(IRI ontologyIRI)
	{
		OntologyIRI = ontologyIRI;
	}

	/**
	 * @return the {@linkplain #baseIRI}
	 */
	public String getBaseIRI()
	{
		return BaseIRI;
	}

	/**
	 * @param baseIRI the {@linkplain #baseIRI} to set
	 */
	public void setBaseIRI(String baseIRI)
	{
		BaseIRI = baseIRI;
	}	
}
