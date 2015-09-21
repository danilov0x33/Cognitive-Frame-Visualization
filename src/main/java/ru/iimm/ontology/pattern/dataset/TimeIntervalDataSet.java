package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import ru.iimm.ontology.pattern.realizations.TimeIntervalRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain TimeIntervalRealization}.
 */
public class TimeIntervalDataSet extends DataSet
{
	private OWLNamedIndividual timeInterval;
	private OWLLiteral timeStart;
	private OWLLiteral timeEnd;
	
	/**
	 * {@linkplain TimeIntervalDataSet}
	 */
	public TimeIntervalDataSet(OWLNamedIndividual timeInterval, OWLLiteral timeStart, OWLLiteral timeEnd)
	{
		this.timeInterval = timeInterval;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
	}

	/**
	 * @return the {@linkplain #timeInterval}
	 */
	public OWLNamedIndividual getTimeInterval()
	{
		return timeInterval;
	}

	/**
	 * @param timeInterval the {@linkplain #timeInterval} to set
	 */
	public void setTimeInterval(OWLNamedIndividual timeInterval)
	{
		this.timeInterval = timeInterval;
	}

	/**
	 * @return the {@linkplain #timeStart}
	 */
	public OWLLiteral getTimeStart()
	{
		return timeStart;
	}

	/**
	 * @param timeStart the {@linkplain #timeStart} to set
	 */
	public void setTimeStart(OWLLiteral timeStart)
	{
		this.timeStart = timeStart;
	}

	/**
	 * @return the {@linkplain #timeEnd}
	 */
	public OWLLiteral getTimeEnd()
	{
		return timeEnd;
	}

	/**
	 * @param timeEnd the {@linkplain #timeEnd} to set
	 */
	public void setTimeEnd(OWLLiteral timeEnd)
	{
		this.timeEnd = timeEnd;
	}
}
