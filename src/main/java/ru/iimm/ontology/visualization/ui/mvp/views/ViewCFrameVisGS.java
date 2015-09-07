package ru.iimm.ontology.visualization.ui.mvp.views;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterCFrameGSVisitor;

/**
 * View с Graph Stream визуализацией CFrame.
 * @author Danilov
 * @version 0.1
 */
public interface ViewCFrameVisGS
{
	public void setPresenter(PresenterCFrameGSVisitor presenter);
	public PresenterCFrameGSVisitor getPresenter();
}
