package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.pattern.RegionCDP;

public class RegionRealization extends ODPRealization
{
	private OWLNamedIndividual entity;
	private OWLNamedIndividual region;
	private OWLLiteral regionDataValue;
	
	/**
	 * {@linkplain RegionRealization}
	 */
	private RegionRealization(){}

	public OWLNamedIndividual getEntity()
	{
		return entity;
	}

	public OWLNamedIndividual getRegion()
	{
		return region;
	}
	
	public OWLLiteral getRegionDataValue()
	{
		return regionDataValue;
	}
	
	public static Builder newBuilder(RegionCDP pattern)
	{
		return new RegionRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private RegionCDP pattern;
		
		private Builder(RegionCDP pattern)
		{
			this.pattern = pattern;
		}

		public Builder setRegionDataValue(OWLLiteral regionDataValue)
		{
			RegionRealization.this.regionDataValue = regionDataValue;
			return this;
		}

		public Builder setRegion(OWLNamedIndividual region)
		{
			RegionRealization.this.region = region;
			return this;
		}
		
		public Builder setEntity(OWLNamedIndividual entity)
		{
			RegionRealization.this.entity = entity;
			return this;
		}
		
		public RegionRealization build()
		{	
			//Создаем новый объект
			RegionRealization realization = new RegionRealization();			
			realization.entity = RegionRealization.this.entity;
			realization.region = RegionRealization.this.region;
			realization.regionDataValue = RegionRealization.this.regionDataValue;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			OWLDataPropertyAssertionAxiom regionDataValueAx = df.getOWLDataPropertyAssertionAxiom(pattern.getHasRegionDataValue(),
					region, regionDataValue);
			structuralAxList.add(df.getOWLClassAssertionAxiom(pattern.getRegion(), region));
			
			OWLObjectPropertyAssertionAxiom hasRegion = df.getOWLObjectPropertyAssertionAxiom(pattern.getHasRegion(), entity, region);
			OWLObjectPropertyAssertionAxiom isRegionFor = df.getOWLObjectPropertyAssertionAxiom(pattern.getIsRegionFor(), region, entity);
			structuralAxList.add(hasRegion);
			structuralAxList.add(isRegionFor);
			
			structuralAxList.add(regionDataValueAx);
			
			return realization;
		}
	}
}
