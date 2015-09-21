package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.CriterionRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain CriterionRealization}.
 */
public class CriterionDataSet extends DataSet
{
	private OWLClass entity;
	private OWLClass criterion;
	
	/**
	 * {@linkplain CriterionDataSet}
	 */
	public CriterionDataSet(OWLClass entity, OWLClass criterion)
	{
		this.entity = entity;
		this.criterion = criterion;
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
	 * @return the {@linkplain #criterion}
	 */
	public OWLClass getCriterion()
	{
		return criterion;
	}

	/**
	 * @param criterion the {@linkplain #criterion} to set
	 */
	public void setCriterion(OWLClass criterion)
	{
		this.criterion = criterion;
	}
}
