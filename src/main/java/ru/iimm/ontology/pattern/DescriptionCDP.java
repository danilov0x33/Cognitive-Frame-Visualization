package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class DescriptionCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/description.owl";
	static String BASE_IRI = ONT_IRI + "#";	
	static String FILENAME = "description.owl";
	
	/* Class names */
	static String DESCRIPTION_IRI = BASE_IRI + "Description";
	static String CONCEPT_IRI = BASE_IRI + "Concept";
	
	/* Properties names */
	static String HAS_DEFINES_IRI = BASE_IRI + "defines";
	static String IS_DEFINES_IN_IRI = BASE_IRI + "isDefinesIn";
	/* Properties names */
	static String USES_CONCEPT_IRI = BASE_IRI + "usesConcept";
	static String IS_CONCEPT_IN_IRI = BASE_IRI + "isDefinesIn";
	
	private OWLClass concept;
	private OWLClass description; 
	
	private OWLObjectProperty defines;
	private OWLObjectProperty isDefinesIn;
	private OWLObjectProperty usesConcept;
	private OWLObjectProperty isConceptUsedIn;
	
	private void init() 
	{
		this.concept = df.getOWLClass(IRI.create(CONCEPT_IRI));
		this.description = df.getOWLClass(IRI.create(DESCRIPTION_IRI));
		
		this.isDefinesIn = df.getOWLObjectProperty(IRI.create(IS_CONCEPT_IN_IRI));
		this.defines = df.getOWLObjectProperty(IRI.create(USES_CONCEPT_IRI));
		this.usesConcept = df.getOWLObjectProperty(IRI.create(USES_CONCEPT_IRI));
		this.isConceptUsedIn = df.getOWLObjectProperty(IRI.create(IS_CONCEPT_IN_IRI));
	}
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public DescriptionCDP(String dirPath) 
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);	
		init();
	}

	/**
	 * @return the {@linkplain #concept}
	 */
	public OWLClass getConcept()
	{
		return concept;
	}

	/**
	 * @return the {@linkplain #description}
	 */
	public OWLClass getDescription()
	{
		return description;
	}

	/**
	 * @return the {@linkplain #defines}
	 */
	public OWLObjectProperty getDefines()
	{
		return defines;
	}

	/**
	 * @return the {@linkplain #isDefinesIn}
	 */
	public OWLObjectProperty getIsDefinesIn()
	{
		return isDefinesIn;
	}

	/**
	 * @return the {@linkplain #usesConcept}
	 */
	public OWLObjectProperty getUsesConcept()
	{
		return usesConcept;
	}

	/**
	 * @return the {@linkplain #isConceptUsedIn}
	 */
	public OWLObjectProperty getIsConceptUsedIn()
	{
		return isConceptUsedIn;
	}
}
