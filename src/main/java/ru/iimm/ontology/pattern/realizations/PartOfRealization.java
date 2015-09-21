package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.pattern.PartOfCDP;

public class PartOfRealization extends ODPRealization
{
	private OWLClass part;
	private OWLClass whole;
	
	/**
	 * {@linkplain PartOfRealization}
	 */
	private PartOfRealization(){}

	public OWLClass getPart()
	{
		return part;
	}

	public OWLClass getWhole()
	{
		return whole;
	}
	
	public static Builder newBuilder(PartOfCDP pattern)
	{
		return new PartOfRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private PartOfCDP pattern;
		
		private Builder(PartOfCDP pattern)
		{
			this.pattern = pattern;
		}
		
		public Builder setPart(OWLClass part)
		{
			PartOfRealization.this.part = part;
			return this;
		}

		public Builder setWhole(OWLClass whole)
		{
			PartOfRealization.this.whole = whole;
			return this;
		}
		
		public PartOfRealization build()
		{	
			//Создаем новый объект
			PartOfRealization realization = new PartOfRealization();			
			realization.part = PartOfRealization.this.part;
			realization.whole = PartOfRealization.this.whole;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			OWLObjectSomeValuesFrom hasPart = df.getOWLObjectSomeValuesFrom(pattern.getHasPart(), part);
			OWLObjectSomeValuesFrom isPartOf = df.getOWLObjectSomeValuesFrom(pattern.getIsPartOf(), whole);
			structuralAxList.add(df.getOWLSubClassOfAxiom(whole, hasPart));
			structuralAxList.add(df.getOWLSubClassOfAxiom(part, isPartOf));

			return realization;
		}
	}
}
