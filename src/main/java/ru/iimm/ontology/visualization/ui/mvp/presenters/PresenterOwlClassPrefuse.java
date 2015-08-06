package ru.iimm.ontology.visualization.ui.mvp.presenters;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisPrefuse;

/**
 * Presenter для отображение OWLClass'ов в Prefuse.
 * @author Danilov
 * @version 0.1
 */
public interface PresenterOwlClassPrefuse extends PresenterOntology
{
	void setView(ViewVisPrefuse view);
	ViewVisPrefuse getView();
	/**Найти объект в визуализации и поставить его в центре экрана.*/
	void centerOwlClass(OWLClass owlClass);
	/**Обновить граф из модели.*/
	void updateGraphFromModel();
}
