package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.ParticipationRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain ParticipationRealization}.
 */
public class ParticipationDataSet extends DataSet
{
	private OWLClass object;
	private OWLClass event;
	
	/**
	 * {@linkplain ParticipationDataSet}
	 */
	public ParticipationDataSet(OWLClass object, OWLClass event)
	{
		this.object = object;
		this.event = event;
	}

	/**
	 * @return the {@linkplain #object}
	 */
	public OWLClass getObject()
	{
		return object;
	}

	/**
	 * @param object the {@linkplain #object} to set
	 */
	public void setObject(OWLClass object)
	{
		this.object = object;
	}

	/**
	 * @return the {@linkplain #event}
	 */
	public OWLClass getEvent()
	{
		return event;
	}

	/**
	 * @param event the {@linkplain #event} to set
	 */
	public void setEvent(OWLClass event)
	{
		this.event = event;
	}
}
