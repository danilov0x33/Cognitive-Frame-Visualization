package ru.iimm.ontology.visualization.ui.mvp.presenters;

import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisCajun;

/**
 * Presenter-Visitor для визуализации фрейма библиотекой Cajun. 
 * @author Danilov
 * @version 0.1
 */
public interface PresenterCFrameCajunVisitor extends PresenterCFrame, VisualMethodVisitorInt
{	
	void setView(ViewVisCajun view);
	ViewVisCajun getView();
}
