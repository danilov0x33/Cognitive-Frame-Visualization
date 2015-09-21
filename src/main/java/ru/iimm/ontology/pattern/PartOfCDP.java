package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class PartOfCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/partof.owl";
	static String BASE_IRI = ONT_IRI + "#";	
	static String FILENAME = "partof.owl";
	
	/* Properties names */
	static String HAS_PART_IRI = BASE_IRI + "hasPart";
	static String IS_PART_OF_IRI = BASE_IRI + "isPartOf";

	private OWLObjectProperty hasPart;
	private OWLObjectProperty isPartOf;
	
	private void init() 
	{
		this.isPartOf = df.getOWLObjectProperty(IRI.create(HAS_PART_IRI));
		this.hasPart = df.getOWLObjectProperty(IRI.create(IS_PART_OF_IRI));		
	}

	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public PartOfCDP(String dirPath) 
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);	
		init();
	}
	
	/**
	 * @return the {@linkplain #hasPart}
	 */
	public OWLObjectProperty getHasPart()
	{
		return hasPart;
	}

	/**
	 * @return the {@linkplain #isPartOf}
	 */
	public OWLObjectProperty getIsPartOf()
	{
		return isPartOf;
	}
	

}
