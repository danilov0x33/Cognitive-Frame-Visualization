package ru.iimm.ontology.visualization.tools.piccolo2d.patters;

import org.piccolo2d.PNode;

import ru.iimm.ontology.visualization.patterns.elements.ArrowElementVis;
import ru.iimm.ontology.visualization.patterns.elements.SituationElementVis;

/**
 * Интерфейс создание визуальных элементов в Piccolo2D по шаблонам.
 * @author Danilov
 * @version 0.1
 */
public interface PElementVisBuilderInt
{
	public PElementVisBuilder setSituationElementVis(SituationElementVis entity);
	public PElementVisBuilder setArrowElement(ArrowElementVis arrow);
	/**Создать визуальный элемент паттерна ситуациия.*/
	PNode buildSituationElementVis();
	/**Создать визуальны образ стрелки.*/
	PNode buildArrowElementVis();
}
