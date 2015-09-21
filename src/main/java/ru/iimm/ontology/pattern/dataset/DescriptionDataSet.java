package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.DescriptionRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain DescriptionRealization}.
 */
public class DescriptionDataSet extends DataSet
{
	private OWLClass description;
	private OWLClass concept;
	
	/**
	 * {@linkplain DescriptionDataSet}
	 */
	public DescriptionDataSet(OWLClass description, OWLClass concept)
	{
		this.description = description;
		this.concept = concept;
	}

	/**
	 * @return the {@linkplain #description}
	 */
	public OWLClass getDescription()
	{
		return description;
	}

	/**
	 * @param description the {@linkplain #description} to set
	 */
	public void setDescription(OWLClass description)
	{
		this.description = description;
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
}
