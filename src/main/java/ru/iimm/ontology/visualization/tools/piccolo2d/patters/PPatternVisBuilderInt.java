package ru.iimm.ontology.visualization.tools.piccolo2d.patters;

import org.piccolo2d.PNode;

import ru.iimm.ontology.visualization.patterns.DescriptionRealizationVis;
import ru.iimm.ontology.visualization.patterns.SituationRealizationVis;

/**
 * Интерефейс для создания визуальных объектов в Piccolo2D по "шаблонам".
 * @author Danilov
 * @version 0.2
 */
public interface PPatternVisBuilderInt
{
	public PPatternVisBuilder setSituationRealizationVis(SituationRealizationVis realizationVis);
	public PPatternVisBuilder setDescriptionRealizationVis(DescriptionRealizationVis realizationVis);

	/**Визуализация реализации паттерна {@linkplain SituationRealizationVis}*/
	PNode buildSituationVis();
	/**Визуализация реализации паттерна {@linkplain DescriptionRealizationVis}*/
	PNode buildDescriptionVis();
}
