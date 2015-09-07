package ru.iimm.ontology.visualization.ui.mvp.presenters;

import ru.iimm.ontology.visualization.ui.mvp.models.ModelMultiOntology;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewSettingFrame;

/**
 * Presenter для окна с настройками.
 * @author Danilov
 * @version 0.1
 */
public interface PresenterSettingFrame extends PresenterFrame
{
	void setView(ViewSettingFrame view);
	ViewSettingFrame getView();
	
	void setModel(ModelMultiOntology model);
	ModelMultiOntology getModel();
}
