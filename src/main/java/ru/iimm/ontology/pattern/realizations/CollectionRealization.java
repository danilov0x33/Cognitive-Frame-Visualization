package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.CollectionCDP;
import ru.iimm.ontology.pattern.ODPRealization;

public class CollectionRealization extends ODPRealization
{
	private OWLClass entity;
	private OWLClass collection;
	
	/**
	 * {@linkplain CollectionRealization}
	 */
	private CollectionRealization(){}

	public OWLClass getEntity()
	{
		return entity;
	}

	public OWLClass getCollection()
	{
		return collection;
	}
	
	public static Builder newBuilder(CollectionCDP pattern)
	{
		return new CollectionRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private CollectionCDP pattern;
		
		private Builder(CollectionCDP pattern)
		{
			this.pattern = pattern;
		}
		
		public Builder setEntity(OWLClass entity)
		{
			CollectionRealization.this.entity = entity;			
			return this;
		}

		public Builder setCollection(OWLClass concept)
		{
			CollectionRealization.this.collection = concept;
			return this;
		}
		
		public CollectionRealization build()
		{	
			//Создаем новый объект
			CollectionRealization realization = new CollectionRealization();			
			realization.collection = CollectionRealization.this.collection;
			realization.entity = CollectionRealization.this.entity;
			realization.setPattern(this.pattern);
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			structuralAxList.add(pattern.getOWLDataFactory().getOWLSubClassOfAxiom(collection, pattern.getCollection()));
			
			OWLObjectSomeValuesFrom hasMember = pattern.getOWLDataFactory().getOWLObjectSomeValuesFrom(pattern.getHasMember(), entity);
			OWLObjectSomeValuesFrom isMemberOf = pattern.getOWLDataFactory().getOWLObjectSomeValuesFrom(pattern.getIsMemberOf(), collection);
			structuralAxList.add(pattern.getOWLDataFactory().getOWLSubClassOfAxiom(collection, hasMember));
			structuralAxList.add(pattern.getOWLDataFactory().getOWLSubClassOfAxiom(entity, isMemberOf));

			return realization;
		}
	}
	
}
