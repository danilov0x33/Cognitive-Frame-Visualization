package ru.iimm.ontology.visualization.ui.mvp.models;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.CFrameOnt;
import ru.iimm.ontology.visualization.tools.CFrameDecorator;

/**
 * Модель предстовляющий отологию с КФ.
 * @author Danilov
 * @version 0.1
 */
public interface ModelCFrameOntology
{
	
	/**
	 * Полулчить список с CFrame-обортками.
	 */
	ArrayList<CFrameDecorator> getCframes();

	/**
	 * Получить ярлык CFrame.
	 * @param cframe - CFrame.
	 * @return - Ярлык в виде строки.
	 */
	String getLabel(CFrame cframe);
	/**Получить онтологию с КФ.*/
	CFrameOnt getOntology();
	/**Задать онтологию с КФ.*/
	void setOntology(CFrameOnt ont);

	/**
	 * Получить ярлык OWLNamedIndividual.
	 * @param owlName - OWLNamedIndividual.
	 * @return - Ярлык в виде строки.
	 */
	String getLabel(OWLNamedIndividual owlName);

	/**
	 * Получить анотацию у IRI.
	 */
	String getAnnotationValue(IRI iri);
}
