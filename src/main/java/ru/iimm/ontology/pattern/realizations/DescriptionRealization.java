package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.DescriptionCDP;
import ru.iimm.ontology.pattern.ODPRealization;

public class DescriptionRealization extends ODPRealization
{
	private OWLClass description;
	private OWLClass concept;
	
	/**
	 * {@linkplain DescriptionRealization}
	 */
	private DescriptionRealization(){}

	public OWLClass getDescription()
	{
		return description;
	}

	public OWLClass getConcept()
	{
		return concept;
	}	
	
	public static Builder newBuilder(DescriptionCDP pattern)
	{
		return new DescriptionRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private DescriptionCDP pattern;
		
		private Builder(DescriptionCDP pattern)
		{
			this.pattern = pattern;
		}
		
		public Builder setConcept(OWLClass concept)
		{
			DescriptionRealization.this.concept = concept;
			return this;
		}

		public Builder setDescription(OWLClass description)
		{
			DescriptionRealization.this.description = description;
			return this;
		}
		
		public DescriptionRealization build()
		{	
			//Создаем новый объект
			DescriptionRealization realization = new DescriptionRealization();			
			realization.concept = DescriptionRealization.this.concept;
			realization.description = DescriptionRealization.this.description;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			structuralAxList.add(df.getOWLSubClassOfAxiom(concept, pattern.getConcept()));
			structuralAxList.add(df.getOWLSubClassOfAxiom(description, pattern.getDescription()));
			
			OWLObjectSomeValuesFrom defines = df.getOWLObjectSomeValuesFrom(pattern.getDefines(), concept);
			OWLObjectSomeValuesFrom isDefinesIn = df.getOWLObjectSomeValuesFrom(pattern.getIsDefinesIn(), description);
			OWLObjectSomeValuesFrom usesConcept = df.getOWLObjectSomeValuesFrom(pattern.getUsesConcept(), concept);
			OWLObjectSomeValuesFrom isConceptUsedIn = df.getOWLObjectSomeValuesFrom(pattern.getIsConceptUsedIn(), description);
			structuralAxList.add(df.getOWLSubClassOfAxiom(concept, defines));
			structuralAxList.add(df.getOWLSubClassOfAxiom(description, isDefinesIn));
			structuralAxList.add(df.getOWLSubClassOfAxiom(concept, usesConcept));
			structuralAxList.add(df.getOWLSubClassOfAxiom(description, isConceptUsedIn));

			return realization;
		}
	}
}
