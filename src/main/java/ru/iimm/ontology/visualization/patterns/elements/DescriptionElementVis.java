package ru.iimm.ontology.visualization.patterns.elements;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.visualization.patterns.ElementVis;

/**
 * Визуальный образ для концептов паттерна описание.
 * @author Danilov
 * @version 0.1
 */
public class DescriptionElementVis extends ElementVis
{
	private OWLClass concept;
	/**
	 * {@linkplain DescriptionElementVis}
	 * @param concept
	 */
	public DescriptionElementVis(OWLClass concept)
	{
		this.concept = concept;
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
