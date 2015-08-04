package ru.iimm.ontology.visualization.ui.mvp;

import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;

/**
 * Presenter-Visitor для визуализации фрейма библиотекой Prefuse. 
 * @author Danilov
 * @version 0.1
 */
public interface PresenterCFramePrefuseVisitor extends PresenterCFrame, VisualMethodVisitorInt
{
	void setView(ViewCFrameVisPrefuse view);
	ViewCFrameVisPrefuse getView();
}
