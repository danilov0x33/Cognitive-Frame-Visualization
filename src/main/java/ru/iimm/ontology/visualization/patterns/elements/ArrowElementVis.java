package ru.iimm.ontology.visualization.patterns.elements;

import java.awt.Color;

import org.semanticweb.owlapi.model.OWLObjectProperty;

import ru.iimm.ontology.visualization.patterns.ElementVis;

/**
 * Визуальный образ для связи.
 * @author Danilov
 * @version 0.1
 */
public class ArrowElementVis extends ElementVis
{
	private OWLObjectProperty property; 
	
	public ArrowElementVis(OWLObjectProperty property)
	{
		this.property = property;
		this.labelElement = property.getIRI().getFragment();
		this.backgroudColor = Color.LIGHT_GRAY;
	}
	/**
	 * @return the {@linkplain #property}
	 */
	public OWLObjectProperty getProperty()
	{
		return property;
	}
	/**
	 * @param property the {@linkplain #property} to set
	 */
	public void setProperty(OWLObjectProperty property)
	{
		this.property = property;
	}
}
