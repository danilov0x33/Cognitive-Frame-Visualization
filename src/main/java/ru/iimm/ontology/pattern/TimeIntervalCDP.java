package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;

public class TimeIntervalCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/timeinterval.owl";
	static String BASE_IRI = ONT_IRI + "#";
	static String FILENAME = "timeinterval.owl";

	/* Class names */
	static String TIME_INTEVAL_IRI = BASE_IRI + "TimeInterval";

	/* Properties names */
	static String HAS_INTERVAL_DATA_IRI = BASE_IRI + "hasIntervalData";
	static String HAS_INTERVAL_END_DATA_IRI = BASE_IRI + "hasIntervalEndData";
	static String HAS_INTERVAL_START_DATA_IRI = BASE_IRI + "hasIntervalStartData";

	private OWLClass intervalDate;

	private OWLDataProperty hasIntervalData;
	private OWLDataProperty hasIntervalEndData;
	private OWLDataProperty hasIntervalStartData;

	/**
	 * Создает паттерн + подружает его онтологию.
	 */
	public TimeIntervalCDP(String dirPath)
	{
		super(IRI.create(BASE_IRI), dirPath, FILENAME);
		this.init();
	}

	void init()
	{
		this.intervalDate = df.getOWLClass(IRI.create(TIME_INTEVAL_IRI));

		this.hasIntervalData = df.getOWLDataProperty(IRI.create(HAS_INTERVAL_DATA_IRI));
		this.hasIntervalEndData = df
		        .getOWLDataProperty(IRI.create(HAS_INTERVAL_END_DATA_IRI));
		this.hasIntervalStartData = df
		        .getOWLDataProperty(IRI.create(HAS_INTERVAL_START_DATA_IRI));
	}

	/**
	 * @return the {@linkplain #intervalDate}
	 */
	public OWLClass getIntervalDate()
	{
		return intervalDate;
	}

	/**
	 * @param intervalDate the {@linkplain #intervalDate} to set
	 */
	public void setIntervalDate(OWLClass intervalDate)
	{
		this.intervalDate = intervalDate;
	}

	/**
	 * @return the {@linkplain #hasIntervalData}
	 */
	public OWLDataProperty getHasIntervalData()
	{
		return hasIntervalData;
	}

	/**
	 * @param hasIntervalData the {@linkplain #hasIntervalData} to set
	 */
	public void setHasIntervalData(OWLDataProperty hasIntervalData)
	{
		this.hasIntervalData = hasIntervalData;
	}

	/**
	 * @return the {@linkplain #hasIntervalEndData}
	 */
	public OWLDataProperty getHasIntervalEndData()
	{
		return hasIntervalEndData;
	}

	/**
	 * @param hasIntervalEndData the {@linkplain #hasIntervalEndData} to set
	 */
	public void setHasIntervalEndData(OWLDataProperty hasIntervalEndData)
	{
		this.hasIntervalEndData = hasIntervalEndData;
	}

	/**
	 * @return the {@linkplain #hasIntervalStartData}
	 */
	public OWLDataProperty getHasIntervalStartData()
	{
		return hasIntervalStartData;
	}

	/**
	 * @param hasIntervalStartData the {@linkplain #hasIntervalStartData} to set
	 */
	public void setHasIntervalStartData(OWLDataProperty hasIntervalStartData)
	{
		this.hasIntervalStartData = hasIntervalStartData;
	}
}
