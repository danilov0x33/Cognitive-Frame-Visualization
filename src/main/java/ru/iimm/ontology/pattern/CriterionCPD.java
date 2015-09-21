package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class CriterionCPD extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://criteria-modeling.googlecode.com/svn/trunk/criterion.owl";
	static String BASE_IRI = ONT_IRI + "#";
	static String FILENAME = "criterion.owl";
	
	/* Class names */
	static String CRITERION_IRI = BASE_IRI + "Criterion";
	
	/* Properties names */
	static String HAS_CRITERION_IRI 		= BASE_IRI + "hasCriterion";
	static String IS_CRITERION_FOR_IRI 	= BASE_IRI + "isCriterionFor";
	
	private OWLClass criterion;
	
	private OWLObjectProperty hasCriterion;
	private OWLObjectProperty isCriterionFor;
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public CriterionCPD(String dirPath)
	{
		super(IRI.create(BASE_IRI), dirPath, FILENAME);
		this.init();
	}

	private void init()
	{
		this.criterion = df.getOWLClass(IRI.create(CRITERION_IRI));
		
		this.hasCriterion = df.getOWLObjectProperty(IRI.create(HAS_CRITERION_IRI));
		this.isCriterionFor = df.getOWLObjectProperty(IRI.create(IS_CRITERION_FOR_IRI));
	}

	/**
	 * @return the {@linkplain #criterion}
	 */
	public OWLClass getCriterion()
	{
		return criterion;
	}

	/**
	 * @return the {@linkplain #hasCriterion}
	 */
	public OWLObjectProperty getHasCriterion()
	{
		return hasCriterion;
	}

	/**
	 * @return the {@linkplain #isCriterionFor}
	 */
	public OWLObjectProperty getIsCriterionFor()
	{
		return isCriterionFor;
	}
}
