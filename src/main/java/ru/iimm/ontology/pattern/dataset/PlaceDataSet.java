package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.PlaceRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain PlaceRealization}.
 */
public class PlaceDataSet extends DataSet
{
	private OWLClass entity;
	private OWLClass place;
	
	/**
	 * {@linkplain PlaceDataSet}
	 */
	public PlaceDataSet(OWLClass entity, OWLClass place)
	{
		this.entity = entity;
		this.place = place;
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
	 * @return the {@linkplain #place}
	 */
	public OWLClass getPlace()
	{
		return place;
	}

	/**
	 * @param place the {@linkplain #place} to set
	 */
	public void setPlace(OWLClass place)
	{
		this.place = place;
	}
}
