package ru.iimm.ontology.visualization.ui.mvp.views;

import ru.iimm.ontology.visualization.tools.cajun.DefaultGraphModelCajun;

/**
 * View с визуальной библиотекой Cajun.
 * @author Danilov
 * @version 0.1
 */
public interface ViewVisCajun extends ViewVis
{
	/**Получить граф.*/
	DefaultGraphModelCajun getGraph();
}
