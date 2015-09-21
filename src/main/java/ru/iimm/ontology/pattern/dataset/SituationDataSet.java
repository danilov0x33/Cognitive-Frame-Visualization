package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.SituationRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain SituationRealization}.
 */
public class SituationDataSet extends DataSet
{
	private OWLClass entity;
	private OWLClass situation;
	
	/**
	 * {@linkplain SituationDataSet}
	 */
	public SituationDataSet(OWLClass entity, OWLClass situation)
	{
		this.entity = entity;
		this.situation = situation;
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
	 * @return the {@linkplain #situation}
	 */
	public OWLClass getSituation()
	{
		return situation;
	}

	/**
	 * @param situation the {@linkplain #situation} to set
	 */
	public void setSituation(OWLClass situation)
	{
		this.situation = situation;
	}
}
