package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.pattern.ParticipationCDP;

public class ParticipationRealization extends ODPRealization
{
	private OWLClass object;
	private OWLClass event;
	
	/**
	 * {@linkplain ParticipationRealization}
	 */
	private ParticipationRealization(){}

	public OWLClass getObject()
	{
		return object;
	}

	public OWLClass getEvent()
	{
		return event;
	}
	
	public static Builder newBuilder(ParticipationCDP pattern)
	{
		return new ParticipationRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private ParticipationCDP pattern;
		
		private Builder(ParticipationCDP pattern)
		{
			this.pattern = pattern;
		}
		
		public Builder setEvent(OWLClass event)
		{
			ParticipationRealization.this.event = event;
			return this;
		}

		public Builder setObject(OWLClass object)
		{
			ParticipationRealization.this.object = object;
			return this;
		}
		
		public ParticipationRealization build()
		{	
			//Создаем новый объект
			ParticipationRealization realization = new ParticipationRealization();			
			realization.event = ParticipationRealization.this.event;
			realization.object = ParticipationRealization.this.object;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			structuralAxList.add(df.getOWLSubClassOfAxiom(object, pattern.getObject()));
			structuralAxList.add(df.getOWLSubClassOfAxiom(event, pattern.getEvent()));
			
			OWLObjectSomeValuesFrom hasParticipant = df.getOWLObjectSomeValuesFrom(pattern.getHasParticipant(), object);
			OWLObjectSomeValuesFrom isParticipantIn = df.getOWLObjectSomeValuesFrom(pattern.getIsParticipantIn(), event);
			structuralAxList.add(df.getOWLSubClassOfAxiom(event, hasParticipant));
			structuralAxList.add(df.getOWLSubClassOfAxiom(object, isParticipantIn));

			return realization;
		}
	}
}
