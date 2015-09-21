package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import ru.iimm.ontology.pattern.realizations.RegionRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain RegionRealization}.
 */
public class RegionDataSet extends DataSet
{
	private OWLNamedIndividual entity;
	private OWLNamedIndividual region;
	private OWLLiteral regionDataValue;
	
	/**
	 * {@linkplain RegionDataSet}
	 */
	public RegionDataSet(OWLNamedIndividual entity, OWLNamedIndividual region, OWLLiteral regionDataValue)
	{
		this.region = region;
		this.entity = entity;
		this.regionDataValue = regionDataValue;
	}

	/**
	 * @return the {@linkplain #entity}
	 */
	public OWLNamedIndividual getEntity()
	{
		return entity;
	}

	/**
	 * @param entity the {@linkplain #entity} to set
	 */
	public void setEntity(OWLNamedIndividual entity)
	{
		this.entity = entity;
	}

	/**
	 * @return the {@linkplain #region}
	 */
	public OWLNamedIndividual getRegion()
	{
		return region;
	}

	/**
	 * @param region the {@linkplain #region} to set
	 */
	public void setRegion(OWLNamedIndividual region)
	{
		this.region = region;
	}

	/**
	 * @return the {@linkplain #regionDataValue}
	 */
	public OWLLiteral getRegionDataValue()
	{
		return regionDataValue;
	}

	/**
	 * @param regionDataValue the {@linkplain #regionDataValue} to set
	 */
	public void setRegionDataValue(OWLLiteral regionDataValue)
	{
		this.regionDataValue = regionDataValue;
	}
}
