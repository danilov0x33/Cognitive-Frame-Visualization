package ru.iimm.ontology.visualization.patterns;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.DescriptionRealization;
import ru.iimm.ontology.visualization.patterns.elements.ArrowElementVis;
import ru.iimm.ontology.visualization.patterns.elements.DescriptionElementVis;

/**
 *Реализация для визуального образа: {@linkplain DescriptionRealization}.
 * @author Danilov
 * @version 0.1
 */
public class DescriptionRealizationVis extends RealizationVis<Integer, DescriptionElementVis>
{
	/**Элемент из онтологии.*/
	private OWLClass description;
	/**Визуальный образ для связи.*/
	private ArrowElementVis binder;
	/**
	 * {@linkplain DescriptionRealizationVis}
	 * @param description
	 */
	public DescriptionRealizationVis(OWLClass description)
	{
		this.description = description;
	}
	/**Добавить элемент.*/
	public void addElement(DescriptionElementVis element)
	{
		this.elementsMap.put(this.elementsMap.size(), element);
	}
	
	public ArrayList<DescriptionElementVis> getElements()
	{
		ArrayList<DescriptionElementVis> elem = new ArrayList<DescriptionElementVis>();
		for(int i=0; i< this.elementsMap.size(); i++)
			elem.add(this.elementsMap.get(i));
		
		return elem;
	}
	
	/**
	 * @return the {@linkplain #description}
	 */
	public OWLClass getData()
	{
		return description;
	}

	/**
	 * @param description the {@linkplain #description} to set
	 */
	public void setData(OWLClass description)
	{
		this.description = description;
	}

	/**
	 * @return the {@linkplain #binder}
	 */
	public ArrowElementVis getBinder()
	{
		return binder;
	}

	/**
	 * @param binder the {@linkplain #binder} to set
	 */
	public void setBinder(ArrowElementVis binder)
	{
		this.binder = binder;
	}
	
	
}
