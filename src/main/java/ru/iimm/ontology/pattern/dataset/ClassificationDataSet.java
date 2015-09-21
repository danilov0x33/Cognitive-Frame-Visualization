package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.ClassificationRealization;

/**
 * 
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain ClassificationRealization}.
 */
public class ClassificationDataSet extends DataSet
{
	private OWLClass entity;
	private OWLClass concept;
	
	/**
	 * {@linkplain ClassificationDataSet}
	 */
	public ClassificationDataSet(OWLClass entity, OWLClass concept)
	{
		this.entity = entity;
		this.concept = concept;
	}

	/**
	 * @return the {@linkplain #entity}
	 */
	public OWLClass getEntity()
	{
		return entity;
	}

	/**
	 * @param entity the {@linkplain #entity} to set
	 */
	public void setEntity(OWLClass entity)
	{
		this.entity = entity;
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
