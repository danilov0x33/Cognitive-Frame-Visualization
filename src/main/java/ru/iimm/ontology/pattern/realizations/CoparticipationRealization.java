package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.CoparticipationCDP;
import ru.iimm.ontology.pattern.ODPRealization;

public class CoparticipationRealization extends ODPRealization
{
	private OWLClass objectCoparticipatesWith;
	private OWLClass object;
	
	/**
	 * {@linkplain CoparticipationRealization}
	 */
	private CoparticipationRealization(){}

	public OWLClass getObjectCoparticipatesWith()
	{
		return objectCoparticipatesWith;
	}

	public OWLClass getObject()
	{
		return object;
	}
	
	public static Builder newBuilder(CoparticipationCDP pattern)
	{
		return new CoparticipationRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private CoparticipationCDP pattern;
		
		private Builder(CoparticipationCDP pattern)
		{
			this.pattern = pattern;
		}
		
		public Builder setObject(OWLClass object)
		{
			CoparticipationRealization.this.object = object;
			return this;
		}
		
		public Builder setObjectCoparticipatesWith(OWLClass objectCoparticipatesWith)
		{
			CoparticipationRealization.this.objectCoparticipatesWith = objectCoparticipatesWith;
			return this;
		}
		
		public CoparticipationRealization build()
		{	
			//Создаем новый объект
			CoparticipationRealization realization = new CoparticipationRealization();			
			realization.object = CoparticipationRealization.this.object;
			realization.objectCoparticipatesWith = CoparticipationRealization.this.objectCoparticipatesWith;
			realization.setPattern(this.pattern);
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			OWLObjectSomeValuesFrom coparticipatesWith = pattern.getOWLDataFactory().getOWLObjectSomeValuesFrom(pattern.getCoparticipatesWith(), object);
			structuralAxList.add(pattern.getOWLDataFactory().getOWLSubClassOfAxiom(objectCoparticipatesWith, coparticipatesWith));

			return realization;
		}
	}
}
