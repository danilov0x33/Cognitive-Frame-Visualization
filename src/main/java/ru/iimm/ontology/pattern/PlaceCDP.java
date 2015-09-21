package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class PlaceCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/place.owl";
	static String BASE_IRI = ONT_IRI + "#";
	static String FILENAME = "place.owl";
	
	/* Class names */
	static String PLACE_IRI = BASE_IRI + "Place";
	
	/* Properties names */
	static String HAS_LOCATION_IRI 		= BASE_IRI + "hasLocation";
	static String IS_LOCATION_OF_IRI 	= BASE_IRI + "isLocationOf";
	
	private OWLClass place;
	
	private OWLObjectProperty hasLocation;
	private OWLObjectProperty isLocationOf;
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public PlaceCDP(String dirPath)
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);
		this.init();
	}

	private void init()
	{
		this.place = df.getOWLClass(IRI.create(PLACE_IRI));
		
		this.hasLocation = df.getOWLObjectProperty(IRI.create(HAS_LOCATION_IRI));
		this.isLocationOf = df.getOWLObjectProperty(IRI.create(IS_LOCATION_OF_IRI));
	}
	
	/**
	 * @return the {@linkplain #place}
	 */
	public OWLClass getPlace()
	{
		return place;
	}

	/**
	 * @return the {@linkplain #hasLocation}
	 */
	public OWLObjectProperty getHasLocation()
	{
		return hasLocation;
	}

	/**
	 * @return the {@linkplain #isLocationOf}
	 */
	public OWLObjectProperty getIsLocationOf()
	{
		return isLocationOf;
	}

}
