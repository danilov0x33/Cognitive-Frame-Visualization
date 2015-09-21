package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.TypeOfEntitiesRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain TypeOfEntitiesRealization}.
 */
public class TypeOfEntitiesDataSet extends DataSet
{
	private OWLClass entity;
	private OWLClass typeClass;
	
	/**
	 * {@linkplain TypeOfEntitiesDataSet}
	 */
	public TypeOfEntitiesDataSet(OWLClass entity, OWLClass typeClass)
	{
		this.entity = entity;
		this.typeClass = typeClass;
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
	 * @return the {@linkplain #typeClass}
	 */
	public OWLClass getTypeClass()
	{
		return typeClass;
	}

	/**
	 * @param typeClass the {@linkplain #typeClass} to set
	 */
	public void setTypeClass(OWLClass typeClass)
	{
		this.typeClass = typeClass;
	}
}
