package ru.iimm.ontology.visualization.ui.mvp;

import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;

/**
 * Presenter-Visitor для визуализации фрейма библиотекой Graph Stream. 
 * @author Danilov
 * @version 0.1
 */
public interface PresenterCFrameGSVisitor extends PresenterCFrame, VisualMethodVisitorInt
{
	void setView(ViewCFrameVisGS view);
	ViewCFrameVisGS getView();
}
