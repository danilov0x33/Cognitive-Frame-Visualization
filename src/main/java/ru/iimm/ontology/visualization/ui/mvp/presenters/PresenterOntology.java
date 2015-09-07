package ru.iimm.ontology.visualization.ui.mvp.presenters;

import ru.iimm.ontology.visualization.ui.mvp.models.ModelOwlOntology;

/**
 * Presenter-а для взаимодействия с моделью ModelOntology.
 * @author Danilov
 * @version 0.1
 */
public interface PresenterOntology
{
	void setModel(ModelOwlOntology model);
	ModelOwlOntology getModel();
}
