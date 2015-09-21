package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class ParameterCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/parameter.owl";
	static String BASE_IRI = ONT_IRI + "#";
	static String FILENAME = "parameter.owl";
	
	/* Class names */
	static String PARAMETER_IRI = BASE_IRI + "Parameter";
	static String CONCEPT_IRI = BASE_IRI + "Concept";
	
	/* Properties names */
	static String HAS_PARAMETER_IRI 		= BASE_IRI + "hasParameter";
	static String IS_PARAMETER_FOR_IRI 	= BASE_IRI + "isParameterFor";
	static String IS_PARAMETER_DATA_VALUE_IRI 	= BASE_IRI + "hasParameterDataValue";
	
	private OWLClass parameter;
	private OWLClass concept;
	
	private OWLObjectProperty hasParameter;
	private OWLObjectProperty isParameterFor;
	private OWLDataProperty hasParameterDataValue;
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public ParameterCDP(String dirPath)
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);
		this.init();
	}

	private void init()
	{
		this.parameter = df.getOWLClass(IRI.create(PARAMETER_IRI));
		this.concept = df.getOWLClass(IRI.create(CONCEPT_IRI));
		
		this.hasParameter = df.getOWLObjectProperty(IRI.create(HAS_PARAMETER_IRI));
		this.isParameterFor = df.getOWLObjectProperty(IRI.create(IS_PARAMETER_FOR_IRI));
		
		this.hasParameterDataValue = df.getOWLDataProperty(IRI.create(IS_PARAMETER_DATA_VALUE_IRI));
	}

	/**
	 * @return the {@linkplain #parameter}
	 */
	public OWLClass getParameter()
	{
		return parameter;
	}

	/**
	 * @param parameter the {@linkplain #parameter} to set
	 */
	public void setParameter(OWLClass parameter)
	{
		this.parameter = parameter;
	}

	/**
	 * @return the {@linkplain #concept}
	 */
	public OWLClass getConcept()
	{
		return concept;
	}

	/**
	 * @param concept the {@linkplain #concept} to set
	 */
	public void setConcept(OWLClass concept)
	{
		this.concept = concept;
	}

	/**
	 * @return the {@linkplain #hasParameter}
	 */
	public OWLObjectProperty getHasParameter()
	{
		return hasParameter;
	}

	/**
	 * @param hasParameter the {@linkplain #hasParameter} to set
	 */
	public void setHasParameter(OWLObjectProperty hasParameter)
	{
		this.hasParameter = hasParameter;
	}

	/**
	 * @return the {@linkplain #isParameterFor}
	 */
	public OWLObjectProperty getIsParameterFor()
	{
		return isParameterFor;
	}

	/**
	 * @param isParameterFor the {@linkplain #isParameterFor} to set
	 */
	public void setIsParameterFor(OWLObjectProperty isParameterFor)
	{
		this.isParameterFor = isParameterFor;
	}

	/**
	 * @return the {@linkplain #hasParameterDataValue}
	 */
	public OWLDataProperty getHasParameterDataValue()
	{
		return hasParameterDataValue;
	}

	/**
	 * @param hasParameterDataValue the {@linkplain #hasParameterDataValue} to set
	 */
	public void setHasParameterDataValue(OWLDataProperty hasParameterDataValue)
	{
		this.hasParameterDataValue = hasParameterDataValue;
	}
}
