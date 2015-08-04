package ru.iimm.ontology.visualization.ui.mvp;

/**
 * Presenter-а для взаимодействия с моделью ModelOntology.
 * @author Danilov
 * @version 0.1
 */
public interface PresenterOntology
{
	void setModel(ModelOntology model);
	ModelOntology getModel();
}
