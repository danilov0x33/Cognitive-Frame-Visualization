package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.CriterionCPD;
import ru.iimm.ontology.pattern.ODPRealization;

public class CriterionRealization extends ODPRealization
{
	private OWLClass entity;
	private OWLClass criterion;
	
	/**
	 * {@linkplain CriterionRealization}
	 */
	private CriterionRealization(){}

	public OWLClass getEntity()
	{
		return entity;
	}

	public OWLClass getCriterion()
	{
		return criterion;
	}
	
	public static Builder newBuilder(CriterionCPD pattern)
	{
		return new CriterionRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private CriterionCPD pattern;
		
		private Builder(CriterionCPD pattern)
		{
			this.pattern = pattern;
		}

		public Builder setCriterion(OWLClass criterion)
		{
			CriterionRealization.this.criterion = criterion;
			return this;
		}

		public Builder setEntity(OWLClass entity)
		{
			CriterionRealization.this.entity = entity;
			return this;
		}
		
		public CriterionRealization build()
		{	
			//Создаем новый объект
			CriterionRealization realization = new CriterionRealization();			
			realization.entity = CriterionRealization.this.entity;
			realization.criterion = CriterionRealization.this.criterion;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			structuralAxList.add(df.getOWLSubClassOfAxiom(criterion, pattern.getCriterion()));
			
			OWLObjectSomeValuesFrom hasСriterion = df.getOWLObjectSomeValuesFrom(pattern.getHasCriterion(), entity);
			OWLObjectSomeValuesFrom isСriterionFor = df.getOWLObjectSomeValuesFrom(pattern.getIsCriterionFor(), criterion);
			structuralAxList.add(df.getOWLSubClassOfAxiom(criterion, hasСriterion));
			structuralAxList.add(df.getOWLSubClassOfAxiom(entity, isСriterionFor));

			return realization;
		}
	}
}
