package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.SequenceRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain SequenceRealization}.
 */
public class SequenceDataSet extends DataSet
{
	private OWLClass first;
	private OWLClass next;
	
	/**
	 * {@linkplain SequenceDataSet}
	 */
	public SequenceDataSet(OWLClass first, OWLClass next)
	{
		this.first = first;
		this.next = next;
	}

	/**
	 * @return the {@linkplain #first}
	 */
	public OWLClass getFirst()
	{
		return first;
	}

	/**
	 * @param first the {@linkplain #first} to set
	 */
	public void setFirst(OWLClass first)
	{
		this.first = first;
	}

	/**
	 * @return the {@linkplain #next}
	 */
	public OWLClass getNext()
	{
		return next;
	}

	/**
	 * @param next the {@linkplain #next} to set
	 */
	public void setNext(OWLClass next)
	{
		this.next = next;
	}
}
