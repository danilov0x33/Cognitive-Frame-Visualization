package ru.iimm.ontology.visualization.ui.mvp.presenters;

import ru.iimm.ontology.visualization.ui.mvp.models.ModelCFrameOntology;

/**
 * Presenter для взаимодействия с моделью CFrameOntology.
 * @author Danilov
 * @version 0.1
 */
public interface PresenterCFrame
{
	void setModel(ModelCFrameOntology model);
	ModelCFrameOntology getModel();
}
