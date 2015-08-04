package ru.iimm.ontology.visualization.ui.mvp;

import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;

/**
 * Presenter-Visitor для визуализации фрейма библиотекой Cajun. 
 * @author Danilov
 * @version 0.1
 */
public interface PresenterCFrameCajunVisitor extends PresenterCFrame, VisualMethodVisitorInt
{	
	void setView(ViewCFrameVisCajun view);
	ViewCFrameVisCajun getView();
}
