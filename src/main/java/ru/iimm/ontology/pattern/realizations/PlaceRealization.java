package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.pattern.PlaceCDP;

public class PlaceRealization extends ODPRealization
{
	private OWLClass entity;
	private OWLClass place;
	
	/**
	 * {@linkplain PlaceRealization}
	 */
	private PlaceRealization(){}
	
	public OWLClass getEntity()
	{
		return entity;
	}
	
	public OWLClass getPlace()
	{
		return place;
	}
	
	public static Builder newBuilder(PlaceCDP pattern)
	{
		return new PlaceRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private PlaceCDP pattern;
		
		private Builder(PlaceCDP pattern)
		{
			this.pattern = pattern;
		}

		public Builder setPlace(OWLClass place)
		{
			PlaceRealization.this.place = place;
			return this;
		}

		public Builder setEntity(OWLClass entity)
		{
			PlaceRealization.this.entity = entity;
			return this;
		}
		
		public PlaceRealization build()
		{	
			//Создаем новый объект
			PlaceRealization realization = new PlaceRealization();			
			realization.entity = PlaceRealization.this.entity;
			realization.place = PlaceRealization.this.place;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			structuralAxList.add(df.getOWLSubClassOfAxiom(place, pattern.getPlace()));
			
			OWLObjectSomeValuesFrom hasLocation = df.getOWLObjectSomeValuesFrom(pattern.getHasLocation(), entity);
			OWLObjectSomeValuesFrom isLocationOf = df.getOWLObjectSomeValuesFrom(pattern.getIsLocationOf(), place);
			structuralAxList.add(df.getOWLSubClassOfAxiom(place, hasLocation));
			structuralAxList.add(df.getOWLSubClassOfAxiom(entity, isLocationOf));
			return realization;
		}
	}
}
