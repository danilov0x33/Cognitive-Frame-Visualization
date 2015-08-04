package ru.iimm.ontology.visualization.ui.mvp;

import ru.iimm.ontology.visualization.tools.cajun.DefaultGraphModelCajun;

/**
 * View с визуальной библиотекой Cajun.
 * @author Danilov
 * @version 0.1
 */
public interface ViewCFrameVisCajun extends ViewVis
{
	/**Получить граф.*/
	DefaultGraphModelCajun getGraph();
	
	void setPresenter(PresenterCFrameCajunVisitor presenter);
	PresenterCFrameCajunVisitor getPresenter();
}
