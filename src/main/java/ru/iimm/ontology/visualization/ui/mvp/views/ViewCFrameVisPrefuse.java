package ru.iimm.ontology.visualization.ui.mvp.views;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterCFramePrefuseVisitor;

/**
 * View с Prefuse визуализацией CFrame.
 * @author Danilov
 * @version 0.1
 */
public interface ViewCFrameVisPrefuse
{
	void setPresenter(PresenterCFramePrefuseVisitor presenter);
	PresenterCFramePrefuseVisitor getPresenter();
}
