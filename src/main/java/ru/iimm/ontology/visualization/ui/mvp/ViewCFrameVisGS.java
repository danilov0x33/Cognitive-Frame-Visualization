package ru.iimm.ontology.visualization.ui.mvp;

import org.graphstream.graph.implementations.MultiGraph;
/**
 * View с визуальной библиотекой Graph Stream.
 * @author Danilov
 * @version 0.1
 */
public interface ViewCFrameVisGS extends ViewVis
{ 
	/**Получить граф.*/
	MultiGraph getGraph();
	
	void setPresenter(PresenterCFrameGSVisitor presenter);
	PresenterCFrameGSVisitor getPresenter();
}
