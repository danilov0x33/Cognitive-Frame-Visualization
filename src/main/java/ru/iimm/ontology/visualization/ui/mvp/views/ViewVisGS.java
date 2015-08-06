package ru.iimm.ontology.visualization.ui.mvp.views;

import org.graphstream.graph.implementations.MultiGraph;
/**
 * View с визуальной библиотекой Graph Stream.
 * @author Danilov
 * @version 0.1
 */
public interface ViewVisGS extends ViewVis
{ 
	/**Получить граф.*/
	MultiGraph getGraph();
}
