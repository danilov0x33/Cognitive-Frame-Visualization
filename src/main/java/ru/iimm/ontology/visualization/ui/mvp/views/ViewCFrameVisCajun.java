package ru.iimm.ontology.visualization.ui.mvp.views;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterCFrameCajunVisitor;

/**
 * View с Cajun визуализацией CFrame.
 * @author Danilov
 * @version 0.1
 */
public interface ViewCFrameVisCajun
{
	public PresenterCFrameCajunVisitor getPresenter();
	public void setPresenter(PresenterCFrameCajunVisitor presenter);
}
