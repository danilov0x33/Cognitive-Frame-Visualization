package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;


public class ClassificationCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/classification.owl";
	static String BASE_IRI = ONT_IRI + "#";
	static String FILENAME = "classification.owl";
	
	/* Class names */
	static String CONCEPT_IRI = BASE_IRI + "Concept";
	
	/* Properties names */
	static String HAS_CLASSIFIES_IRI 		= BASE_IRI + "classifies";
	static String IS_CLASSIFIED_BY_IRI 	= BASE_IRI + "isClassifiedBy";
	
	private OWLClass concept;
	
	private OWLObjectProperty classifies;
	private OWLObjectProperty isClassifiedBy;
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public ClassificationCDP(String dirPath)
	{
		super(IRI.create(BASE_IRI), dirPath, FILENAME);
		this.init();
	}

	private void init()
	{
		this.concept = df.getOWLClass(IRI.create(CONCEPT_IRI));
		
		this.classifies = df.getOWLObjectProperty(IRI.create(HAS_CLASSIFIES_IRI));
		this.isClassifiedBy = df.getOWLObjectProperty(IRI.create(IS_CLASSIFIED_BY_IRI));
	}
	
	/**
	 * @return the {@linkplain #concept}
	 */
	public OWLClass getConcept()
	{
		return concept;
	}

	/**
	 * @return the {@linkplain #classifies}
	 */
	public OWLObjectProperty getClassifies()
	{
		return classifies;
	}

	/**
	 * @return the {@linkplain #isClassifiedBy}
	 */
	public OWLObjectProperty getIsClassifiedBy()
	{
		return isClassifiedBy;
	}

}
