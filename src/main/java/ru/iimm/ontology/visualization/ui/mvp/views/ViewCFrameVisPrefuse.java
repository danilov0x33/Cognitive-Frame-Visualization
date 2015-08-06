package ru.iimm.ontology.visualization.ui.mvp.views;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterCFramePrefuseVisitor;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public interface ViewCFrameVisPrefuse
{
	void setPresenter(PresenterCFramePrefuseVisitor presenter);
	PresenterCFramePrefuseVisitor getPresenter();
}
