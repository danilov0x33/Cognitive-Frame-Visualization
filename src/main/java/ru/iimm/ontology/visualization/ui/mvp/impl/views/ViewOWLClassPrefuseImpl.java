package ru.iimm.ontology.visualization.ui.mvp.impl.views;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterOwlClassPrefuse;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewOwlClassVisPrefuse;

/**
 * View для визуализации иерарзии OWLClass'ов с помощью Prefuse.
 * @author Danilov
 * @version 0.1
 */
public class ViewOWLClassPrefuseImpl extends BaseViewVisPrefuse implements ViewOwlClassVisPrefuse
{
	private PresenterOwlClassPrefuse presenter;
	
	@Override
	public void setPresenter(PresenterOwlClassPrefuse presenter)
	{
		this.presenter = presenter;
	}

	@Override
	public PresenterOwlClassPrefuse getPresenter()
	{
		return this.presenter;
	}
	
	@Override
	public void open()
	{
		super.open();
		
		this.presenter.updateGraphFromModel();
	}
}
