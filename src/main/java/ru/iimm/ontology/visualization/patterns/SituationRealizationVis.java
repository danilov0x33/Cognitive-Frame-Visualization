package ru.iimm.ontology.visualization.patterns;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.SituationRealization;
import ru.iimm.ontology.visualization.patterns.elements.ArrowElementVis;
import ru.iimm.ontology.visualization.patterns.elements.SituationElementVis;

/**
 * Реализация для визуального образа: {@linkplain SituationRealization}.
 * @author Danilov
 * @version 0.1
 */
public class SituationRealizationVis extends RealizationVis<Integer, SituationElementVis>
{
	/**Элемент из онтологии.*/
	private OWLClass situation;
	/**Визуальный образ для связи.*/
	private ArrowElementVis binder;
	
	public SituationRealizationVis(OWLClass situation)
	{
		this.situation = situation;
	}
	/**Добавить элемент.*/
	public void addElement(SituationElementVis element)
	{
		this.elementsMap.put(this.elementsMap.size(), element);
	}
	
	public ArrayList<SituationElementVis> getElements()
	{
		ArrayList<SituationElementVis> elem = new ArrayList<SituationElementVis>();
		for(int i=0; i< this.elementsMap.size(); i++)
			elem.add(this.elementsMap.get(i));
		
		return elem;
	}
	
	/**
	 * @return the {@linkplain #situation}
	 */
	public OWLClass getData()
	{
		return situation;
	}

	/**
	 * @param situation the {@linkplain #situation} to set
	 */
	public void setData(OWLClass situation)
	{
		this.situation = situation;
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
