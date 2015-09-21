package ru.iimm.ontology.visualization.patterns.elements;

import java.awt.Color;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.visualization.patterns.ElementVis;

/**
 * Визуальный образ для элементов паттерна ситуация.
 * @author Danilov
 * @version 0.1
 */
public class SituationElementVis extends ElementVis
{
	private OWLClass entity;
	/**
	 * {@linkplain SituationElementVis}
	 */
	public SituationElementVis(OWLClass entity)
	{
		this.entity = entity;
		this.labelElement = entity.getIRI().getFragment();
		this.backgroudColor = Color.WHITE;
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
}
