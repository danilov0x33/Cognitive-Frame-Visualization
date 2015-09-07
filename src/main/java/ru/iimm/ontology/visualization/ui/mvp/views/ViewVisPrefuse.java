package ru.iimm.ontology.visualization.ui.mvp.views;

import ru.iimm.ontology.visualization.tools.prefuse.DisplayPrefuse;
import ru.iimm.ontology.visualization.tools.prefuse.tools.DefaultGraphPrefuse;

/**
 * View с визуальной библиотекой Prefuse.
 * @author Danilov
 * @version 0.1
 */
public interface ViewVisPrefuse extends ViewVis
{
	/**Получить граф.*/
	DefaultGraphPrefuse getGraph();
	DisplayPrefuse getDisplay();
}
