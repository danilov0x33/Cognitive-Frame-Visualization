package ru.iimm.ontology.visualization.ui.mvp;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.CFrameOnt;
import ru.iimm.ontology.visualization.tools.CFrameDecorator;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public interface ModelCFrameOntology
{
	
	/**
	 * @return
	 */
	ArrayList<CFrameDecorator> getCframes();

	/**
	 * @param o1
	 * @return
	 */
	String getLabel(CFrame cframe);
	
	CFrameOnt getOntology();
	void setOntology(CFrameOnt ont);

	/**
	 * @param owlName
	 * @return
	 */
	String getLabel(OWLNamedIndividual owlName);

	/**
	 * @param iri
	 * @return
	 */
	String getAnnotationValue(IRI iri);
}
