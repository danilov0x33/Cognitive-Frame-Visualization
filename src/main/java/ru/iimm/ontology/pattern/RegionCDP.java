package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class RegionCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/region.owl";
	static String BASE_IRI = ONT_IRI + "#";
	static String FILENAME = "region.owl";
	
	/* Class names */
	static String REGION_IRI = BASE_IRI + "Region";
	
	/* Properties names */
	static String HAS_REGION_IRI 		= BASE_IRI + "hasRegion";
	static String IS_REGION_FOR_IRI 	= BASE_IRI + "isRegionFor";
	static String IS_REGION_DATA_VALUE_IRI 	= BASE_IRI + "hasRegionDataValue";
	
	private OWLClass region;
	
	private OWLObjectProperty hasRegion;
	private OWLObjectProperty isRegionFor;
	private OWLDataProperty hasRegionDataValue;
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public RegionCDP(String dirPath)
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);
		this.init();
	}

	void init()
	{
		this.region = df.getOWLClass(IRI.create(REGION_IRI));
		
		this.hasRegion = df.getOWLObjectProperty(IRI.create(HAS_REGION_IRI));
		this.isRegionFor = df.getOWLObjectProperty(IRI.create(IS_REGION_FOR_IRI));
		
		this.hasRegionDataValue = df.getOWLDataProperty(IRI.create(IS_REGION_DATA_VALUE_IRI));
	}

	/**
	 * @return the {@linkplain #region}
	 */
	public OWLClass getRegion()
	{
		return region;
	}

	/**
	 * @return the {@linkplain #hasRegion}
	 */
	public OWLObjectProperty getHasRegion()
	{
		return hasRegion;
	}

	/**
	 * @return the {@linkplain #isRegionFor}
	 */
	public OWLObjectProperty getIsRegionFor()
	{
		return isRegionFor;
	}

	/**
	 * @return the {@linkplain #hasRegionDataValue}
	 */
	public OWLDataProperty getHasRegionDataValue()
	{
		return hasRegionDataValue;
	}
}
