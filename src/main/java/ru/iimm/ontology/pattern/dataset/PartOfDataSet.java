package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.PartOfRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain PartOfRealization}.
 */
public class PartOfDataSet extends DataSet
{
	private OWLClass part;
	private OWLClass whole;
	
	/**
	 * {@linkplain PartOfDataSet}
	 */
	public PartOfDataSet(OWLClass part, OWLClass whole)
	{
		this.part = part;
		this.whole = whole;
	}

	/**
	 * @return the {@linkplain #part}
	 */
	public OWLClass getPart()
	{
		return part;
	}

	/**
	 * @param part the {@linkplain #part} to set
	 */
	public void setPart(OWLClass part)
	{
		this.part = part;
	}

	/**
	 * @return the {@linkplain #whole}
	 */
	public OWLClass getWhole()
	{
		return whole;
	}

	/**
	 * @param whole the {@linkplain #whole} to set
	 */
	public void setWhole(OWLClass whole)
	{
		this.whole = whole;
	}
}
