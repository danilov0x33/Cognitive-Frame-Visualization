package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.CollectionRealization;

/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain CollectionRealization}.
 */
public class CollectionDataSet extends DataSet
{
	private OWLClass entity;
	private OWLClass collection;
	
	/**
	 * {@linkplain CollectionDataSet}
	 */
	public CollectionDataSet(OWLClass entity, OWLClass collection)
	{
		this.entity = entity;
		this.collection = collection;
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
	 * @return the {@linkplain #collection}
	 */
	public OWLClass getCollection()
	{
		return collection;
	}

	/**
	 * @param collection the {@linkplain #collection} to set
	 */
	public void setCollection(OWLClass collection)
	{
		this.collection = collection;
	}
}
