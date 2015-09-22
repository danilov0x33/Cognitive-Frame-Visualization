package ru.iimm.ontology.visualization.tools.piccolo2d.patters;

import org.piccolo2d.PNode;

import ru.iimm.ontology.visualization.patterns.elements.ArrowElementVis;
import ru.iimm.ontology.visualization.patterns.elements.DescriptionElementVis;
import ru.iimm.ontology.visualization.patterns.elements.SituationElementVis;

/**
 * Интерфейс создание визуальных элементов в Piccolo2D по шаблонам.
 * @author Danilov
 * @version 0.2
 */
public interface PElementVisBuilderInt
{
	public PElementVisBuilder setSituationElementVis(SituationElementVis entity);
	public PElementVisBuilder setArrowElement(ArrowElementVis arrow);
	public PElementVisBuilder setDescriptionElementVis(DescriptionElementVis concept);
	
	/**Создать визуальный элемент паттерна описание.*/
	PNode buildDescriptionElementVis();
	/**Создать визуальный элемент паттерна ситуациия.*/
	PNode buildSituationElementVis();
	/**Создать визуальны образ стрелки.*/
	PNode buildArrowElementVis();
	/**Создать визуальную комбинацию из элемента {@link #buildSituationElementVis()}
	 * и {@link #buildArrowElementVis()}*/
	PNode buildSituationElementWithArrow();
	/**Создать визуальную комбинацию из элемента {@link #buildDescriptionElementVis()}
	 * и {@link #buildArrowElementVis()}*/
	PNode buildDescriptionElementWithArrow();
}
