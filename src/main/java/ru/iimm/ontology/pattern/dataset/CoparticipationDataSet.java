package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.CoparticipationRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain CoparticipationRealization}
 */
public class CoparticipationDataSet extends DataSet
{
	private OWLClass objectCoparticipatesWith;
	private OWLClass object;
	
	/**
	 * {@linkplain CoparticipationDataSet}
	 */
	public CoparticipationDataSet(OWLClass objectCoparticipatesWith, OWLClass object)
	{
		this.objectCoparticipatesWith = objectCoparticipatesWith;
		this.object = object;
	}

	/**
	 * @return the {@linkplain #objectCoparticipatesWith}
	 */
	public OWLClass getObjectCoparticipatesWith()
	{
		return objectCoparticipatesWith;
	}

	/**
	 * @param objectCoparticipatesWith the {@linkplain #objectCoparticipatesWith} to set
	 */
	public void setObjectCoparticipatesWith(OWLClass objectCoparticipatesWith)
	{
		this.objectCoparticipatesWith = objectCoparticipatesWith;
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
}
