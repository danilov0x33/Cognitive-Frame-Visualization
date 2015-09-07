package ru.iimm.ontology.visualization.ui.mvp.impl.views;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterCFrameCajunVisitor;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewCFrameVisCajun;

/**
 * View для визуализации CFrame с помощью Cajun.
 * @author Danilov
 * @version 0.1
 */
public class ViewCFrameVisCajunImpl extends BaseViewVisCajun implements ViewCFrameVisCajun
{
	private PresenterCFrameCajunVisitor presenter;
	
	public PresenterCFrameCajunVisitor getPresenter()
	{
		return this.presenter;
	}
	
	public void setPresenter(PresenterCFrameCajunVisitor presenter)
	{
		this.presenter = presenter;
	}
}
