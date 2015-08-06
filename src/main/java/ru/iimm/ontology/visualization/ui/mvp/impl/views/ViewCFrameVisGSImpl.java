package ru.iimm.ontology.visualization.ui.mvp.impl.views;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterCFrameGSVisitor;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewCFrameVisGS;

/**
 * View для визуализации CFrame с помощью Graph Stream.
 * @author Danilov
 * @version 0.1
 */
public class ViewCFrameVisGSImpl extends BaseViewVisGS implements ViewCFrameVisGS
{
	/**Presenter.*/
	private PresenterCFrameGSVisitor presenter;

	@Override
	public void setPresenter(PresenterCFrameGSVisitor presenter)
	{
		this.presenter = presenter;
	}

	@Override
	public PresenterCFrameGSVisitor getPresenter()
	{
		return this.presenter;
	}
}
