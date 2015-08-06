package ru.iimm.ontology.visualization.ui.mvp.presenters;

import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisGS;

/**
 * Presenter-Visitor для визуализации фрейма библиотекой Graph Stream. 
 * @author Danilov
 * @version 0.1
 */
public interface PresenterCFrameGSVisitor extends PresenterCFrame, VisualMethodVisitorInt
{
	void setView(ViewVisGS view);
	ViewVisGS getView();
}
