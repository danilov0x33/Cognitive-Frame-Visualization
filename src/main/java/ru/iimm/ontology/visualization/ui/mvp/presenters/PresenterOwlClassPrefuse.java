package ru.iimm.ontology.visualization.ui.mvp.presenters;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisPrefuse;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public interface PresenterOwlClassPrefuse extends PresenterOntology
{
	void setView(ViewVisPrefuse view);
	ViewVisPrefuse getView();
	
	void centerOwlClass(OWLClass owlClass);
	void updateGraphFromModel();
}
