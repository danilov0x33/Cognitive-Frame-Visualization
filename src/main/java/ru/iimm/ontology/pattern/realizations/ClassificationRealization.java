package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.ClassificationCDP;
import ru.iimm.ontology.pattern.ODPRealization;

public class ClassificationRealization extends ODPRealization
{
	private OWLClass entity;
	private OWLClass concept;
	
	/**
	 * {@linkplain ClassificationRealization}
	 */
	private ClassificationRealization(){}
	
	/**
	 * @return the {@linkplain #entity}
	 */
	public OWLClass getEntity()
	{
		return entity;
	}

	/**
	 * @return the {@linkplain #concept}
	 */
	public OWLClass getConcept()
	{
		return concept;
	}
	
	public static Builder newBuilder(ClassificationCDP pattern)
	{
		return new ClassificationRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private ClassificationCDP pattern;
		
		private Builder(ClassificationCDP pattern)
		{
			this.pattern = pattern;
		}
		
		public Builder setEntity(OWLClass entity)
		{
			ClassificationRealization.this.entity = entity;			
			return this;
		}

		public Builder setConcept(OWLClass concept)
		{
			ClassificationRealization.this.concept = concept;
			return this;
		}
		
		public ClassificationRealization build()
		{	
			//Создаем новый объект
			ClassificationRealization realization = new ClassificationRealization();			
			realization.concept = ClassificationRealization.this.concept;
			realization.entity = ClassificationRealization.this.entity;
			realization.setPattern(this.pattern);
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			structuralAxList.add(pattern.getOWLDataFactory().getOWLSubClassOfAxiom(concept, pattern.getConcept()));
			
			OWLObjectSomeValuesFrom hasСlassifies = pattern.getOWLDataFactory().getOWLObjectSomeValuesFrom(pattern.getClassifies(), entity);
			OWLObjectSomeValuesFrom isClassifiesBy = pattern.getOWLDataFactory().getOWLObjectSomeValuesFrom(pattern.getIsClassifiedBy(), concept);
			structuralAxList.add(pattern.getOWLDataFactory().getOWLSubClassOfAxiom(concept, hasСlassifies));
			structuralAxList.add(pattern.getOWLDataFactory().getOWLSubClassOfAxiom(entity, isClassifiesBy));

			return realization;
		}
	}

}
