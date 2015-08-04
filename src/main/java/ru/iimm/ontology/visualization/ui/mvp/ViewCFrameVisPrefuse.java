package ru.iimm.ontology.visualization.ui.mvp;

import ru.iimm.ontology.visualization.tools.prefuse.DisplayPrefuse;
import ru.iimm.ontology.visualization.tools.prefuse.tools.DefaultGraphPrefuse;

/**
 * View с визуальной библиотекой Prefuse.
 * @author Danilov
 * @version 0.1
 */
public interface ViewCFrameVisPrefuse extends ViewVis
{
	/**Получить граф.*/
	DefaultGraphPrefuse getGraph();
	DisplayPrefuse getDisplay();
	
	void setPresenter(PresenterCFramePrefuseVisitor presenter);
	PresenterCFramePrefuseVisitor getPresenter();
}
