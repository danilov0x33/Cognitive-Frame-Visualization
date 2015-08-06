package ru.iimm.ontology.visualization.ui.mvp.views;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterCFrameGSVisitor;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public interface ViewCFrameVisGS
{
	public void setPresenter(PresenterCFrameGSVisitor presenter);
	public PresenterCFrameGSVisitor getPresenter();
}
