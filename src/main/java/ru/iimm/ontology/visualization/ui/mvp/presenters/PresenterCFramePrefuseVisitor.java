package ru.iimm.ontology.visualization.ui.mvp.presenters;

import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisPrefuse;

/**
 * Presenter-Visitor для визуализации фрейма библиотекой Prefuse. 
 * @author Danilov
 * @version 0.1
 */
public interface PresenterCFramePrefuseVisitor extends PresenterCFrame, VisualMethodVisitorInt
{
	void setView(ViewVisPrefuse view);
	ViewVisPrefuse getView();
}
