package ru.iimm.ontology.visualization.ui.mvp.impl.models;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;
import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.CFrameOnt;
import ru.iimm.ontology.visualization.lang.Language;
import ru.iimm.ontology.visualization.tools.CFrameDecorator;
import ru.iimm.ontology.visualization.ui.mvp.models.ModelCFrameOntology;

/**
 * Реализация интерфейса {@linkplain ModelCFrameOntology}.
 * @author Danilov
 * @version 0.1
 */
public class ModelCFrameOntologyImpl implements ModelCFrameOntology
{
	/**Онтология с КФ.*/
	private CFrameOnt ont;
	/**Список с CFrame-обертками.*/
	private ArrayList<CFrameDecorator> cFrameList;
	
	/**
	 * {@linkplain ModelCFrameOntologyImpl}
	 */
	public ModelCFrameOntologyImpl(CFrameOnt ont)
	{
		this.ont = ont;
		this.cFrameList = new ArrayList<CFrameDecorator>();
		this.init();
	}
	
	private void init()
	{
		for(CFrame frame : this.ont.getCframes())
		{
			this.cFrameList.add(new CFrameDecorator(ont, frame));
		}
	}
	
	@Override
	public ArrayList<CFrameDecorator> getCframes()
	{
		return this.cFrameList;
	}

	@Override
	public String getLabel(CFrame frame)
	{
		return this.ont.getLabel(frame.getTrgConcept().getIRI(), Language.UPO_LANG);
	}

	@Override
	public CFrameOnt getOntology()
	{
		return this.ont;
	}

	@Override
	public void setOntology(CFrameOnt ont)
	{
		this.ont = ont;
	}

	@Override
	public String getLabel(OWLNamedIndividual owlName)
	{
		return this.ont.getLabel(owlName.getIRI(), Language.UPO_LANG);
	}

	@Override
	public String getAnnotationValue(IRI iri)
	{
		return this.ont.getAnnotationValue(iri, IRI.create(ConstantsOntConverter.UPO_TITLE_LABEL));
	}
}
