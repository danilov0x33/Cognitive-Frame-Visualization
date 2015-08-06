package ru.iimm.ontology.visualization.ui.mvp.models;

import org.protege.editor.owl.OWLEditorKit;

import ru.iimm.ontology.OWL2UPOConverter.OWLont;
import ru.iimm.ontology.OWL2UPOConverter.UPOont;
import ru.iimm.ontology.cftools.CFrameOnt;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterOntology;

/**
 * Модель содержит несколько видов онтологий.
 * @author Danilov
 * @version 0.1
 */
public interface ModelMultiOntology
{
	void setPresenter(PresenterOntology presenter);
	PresenterOntology getPresenter();
	
	public UPOont getUPOOnt();
	
	public OWLont getOWLOnt();

	public OWLEditorKit getProtegeOWLEditor();

	public CFrameOnt getCongitiveFrameOntology();

	public String getFileActivOntology();

	public String getPathDirOntology();
}
