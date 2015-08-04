package ru.iimm.ontology.visualization.ui.mvp;

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
