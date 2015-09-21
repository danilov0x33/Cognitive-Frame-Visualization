package ru.iimm.ontology.visualization.tools.piccolo2d.patters;

import org.piccolo2d.PNode;

import ru.iimm.ontology.visualization.patterns.SituationRealizationVis;

/**
 * Интерефейс для создания визуальных объектов в Piccolo2D по "шаблонам".
 * @author Danilov
 * @version 0.1
 */
public interface PPatternVisBuilderInt
{
	public PPatternVisBuilder setRealizationVis(SituationRealizationVis realizationVis);
	/**Визуализация реализации паттерна {@linkplain SituationRealizationVis}*/
	PNode buildSituationVis();
}
