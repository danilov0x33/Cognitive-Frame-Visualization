package ru.iimm.ontology.visualization.ui.mvp.impl.views;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterCFramePrefuseVisitor;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewCFrameVisPrefuse;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public class ViewCFrameVisPrefuseImpl extends BaseViewVisPrefuse implements ViewCFrameVisPrefuse
{
	/**Presenter.*/
	private PresenterCFramePrefuseVisitor presenter;
	
	@Override
	public void setPresenter(PresenterCFramePrefuseVisitor presenter)
	{
		this.presenter = presenter;
	}

	@Override
	public PresenterCFramePrefuseVisitor getPresenter()
	{
		return this.presenter;
	}
	
}
