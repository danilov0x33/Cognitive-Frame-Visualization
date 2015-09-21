package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.pattern.SequenceCDP;

public class SequenceRealization extends ODPRealization
{
	private OWLClass first;
	private OWLClass next;
	
	/**
	 * {@linkplain SequenceRealization}
	 */
	private SequenceRealization(){}

	public OWLClass getFirst()
	{
		return first;
	}

	public OWLClass getNext()
	{
		return next;
	}
	
	public static Builder newBuilder(SequenceCDP pattern)
	{
		return new SequenceRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private SequenceCDP pattern;
		
		private Builder(SequenceCDP pattern)
		{
			this.pattern = pattern;
		}

		public Builder setNext(OWLClass next)
		{
			SequenceRealization.this.next = next;
			return this;
		}
		
		public Builder setFirst(OWLClass first)
		{
			SequenceRealization.this.first = first;
			return this;
		}

		
		public SequenceRealization build()
		{	
			//Создаем новый объект
			SequenceRealization realization = new SequenceRealization();			
			realization.next = SequenceRealization.this.next;
			realization.first = SequenceRealization.this.first;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			OWLObjectSomeValuesFrom follow = df.getOWLObjectSomeValuesFrom(pattern.getFollows(), first);
			OWLObjectSomeValuesFrom precede = df.getOWLObjectSomeValuesFrom(pattern.getPrecedes(), next);
			structuralAxList.add(df.getOWLSubClassOfAxiom(next, follow));
			structuralAxList.add(df.getOWLSubClassOfAxiom(follow, precede));
			
			return realization;
		}
	}
}
