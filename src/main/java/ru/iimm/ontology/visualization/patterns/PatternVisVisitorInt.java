package ru.iimm.ontology.visualization.patterns;

/**
 * Интерфейс для создания визуализации по визуальным образам.
 * @author Danilov
 * @version 0.1
 */
public interface PatternVisVisitorInt
{
	void visit(SituationRealizationVis realization);
	void visit(DescriptionRealizationVis realization);
}
