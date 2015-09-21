package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.pattern.TimeIntervalCDP;

public class TimeIntervalRealization extends ODPRealization
{
	private OWLNamedIndividual timeInterval;
	private OWLLiteral timeStart;
	private OWLLiteral timeEnd;
	
	/**
	 * {@linkplain TimeIntervalRealization}
	 */
	private TimeIntervalRealization(){}

	public OWLNamedIndividual getTimeInterval()
	{
		return timeInterval;
	}

	public OWLLiteral getTimeStart()
	{
		return timeStart;
	}

	public OWLLiteral getTimeEnd()
	{
		return timeEnd;
	}
	
	public static Builder newBuilder(TimeIntervalCDP pattern)
	{
		return new TimeIntervalRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private TimeIntervalCDP pattern;
		
		private Builder(TimeIntervalCDP pattern)
		{
			this.pattern = pattern;
		}

		public Builder setTimeInterval(OWLNamedIndividual timeInterval)
		{
			TimeIntervalRealization.this.timeInterval = timeInterval;
			return this;
		}
		public Builder setTimeStart(OWLLiteral timeStart)
		{
			TimeIntervalRealization.this.timeStart = timeStart;
			return this;
		}
		public Builder setTimeEnd(OWLLiteral timeEnd)
		{
			TimeIntervalRealization.this.timeEnd = timeEnd;
			return this;
		}
		
		public TimeIntervalRealization build()
		{	
			//Создаем новый объект
			TimeIntervalRealization realization = new TimeIntervalRealization();			
			realization.timeEnd = TimeIntervalRealization.this.timeEnd;
			realization.timeInterval = TimeIntervalRealization.this.timeInterval;
			realization.timeStart = TimeIntervalRealization.this.timeStart;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			OWLDataPropertyAssertionAxiom timeStartAx = df.getOWLDataPropertyAssertionAxiom(
					pattern.getHasIntervalStartData(), timeInterval,
			        timeStart);
			OWLDataPropertyAssertionAxiom timeEndAx = df.getOWLDataPropertyAssertionAxiom(
					pattern.getHasIntervalEndData(), timeInterval, timeEnd);

			structuralAxList.add(df.getOWLClassAssertionAxiom(pattern.getIntervalDate(),
			        timeInterval));

			structuralAxList.add(df.getOWLSubDataPropertyOfAxiom(pattern.getHasIntervalData(),
					pattern.getHasIntervalStartData()));
			structuralAxList.add(df.getOWLSubDataPropertyOfAxiom(pattern.getHasIntervalData(),
					pattern.getHasIntervalEndData()));

			structuralAxList.add(timeStartAx);
			structuralAxList.add(timeEndAx);
			
			return realization;
		}
	}
}

