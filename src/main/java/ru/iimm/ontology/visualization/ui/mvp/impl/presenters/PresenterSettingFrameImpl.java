package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import ru.iimm.ontology.visualization.ui.mvp.models.ModelMultiOntology;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterSettingFrame;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewSettingFrame;

/**
 * Реализация presenter'а для окна с настройками
 * @author Danilov
 * @version 0.1
 */
public class PresenterSettingFrameImpl implements PresenterSettingFrame
{
	private ViewSettingFrame view;
	private ModelMultiOntology model;
	
	@Override
	public void show()
	{
		this.view.getFrame().setVisible(true);
	}

	@Override
	public void setView(ViewSettingFrame view)
	{
		this.view = view;
	}

	@Override
	public ViewSettingFrame getView()
	{
		return this.view;
	}

	@Override
	public void setModel(ModelMultiOntology model)
	{
		this.model = model;
	}

	@Override
	public ModelMultiOntology getModel()
	{
		return this.model;
	}

}
