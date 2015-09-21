package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import ru.iimm.ontology.pattern.realizations.ParameterRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain ParameterRealization}.
 */
public class ParameterDataSet extends DataSet
{
	private OWLNamedIndividual concept;
	private OWLNamedIndividual parameter;
	private OWLLiteral parameterDataValue;
	/**
	 * {@linkplain ParameterDataSet}
	 */
	public ParameterDataSet(OWLNamedIndividual concept, OWLNamedIndividual parameter, OWLLiteral parameterDataValue)
	{
		this.concept = concept;
		this.parameter = parameter;
		this.parameterDataValue = parameterDataValue;
	}
	/**
	 * @return the {@linkplain #concept}
	 */
	public OWLNamedIndividual getConcept()
	{
		return concept;
	}
	/**
	 * @param concept the {@linkplain #concept} to set
	 */
	public void setConcept(OWLNamedIndividual concept)
	{
		this.concept = concept;
	}
	/**
	 * @return the {@linkplain #parameter}
	 */
	public OWLNamedIndividual getParameter()
	{
		return parameter;
	}
	/**
	 * @param parameter the {@linkplain #parameter} to set
	 */
	public void setParameter(OWLNamedIndividual parameter)
	{
		this.parameter = parameter;
	}
	/**
	 * @return the {@linkplain #parameterDataValue}
	 */
	public OWLLiteral getParameterDataValue()
	{
		return parameterDataValue;
	}
	/**
	 * @param parameterDataValue the {@linkplain #parameterDataValue} to set
	 */
	public void setParameterDataValue(OWLLiteral parameterDataValue)
	{
		this.parameterDataValue = parameterDataValue;
	}
}
