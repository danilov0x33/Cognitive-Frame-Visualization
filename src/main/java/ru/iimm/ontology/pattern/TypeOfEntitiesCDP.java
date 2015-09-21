package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

public class TypeOfEntitiesCDP extends ContentDesingPattern

{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/typesofentities.owl";
	static String BASE_IRI = ONT_IRI + "#";	
	static String FILENAME = "typesofentities.owl";
	
	/* Class names */
	static String ENTITY_IRI = BASE_IRI + "Entity";
	static String ABSTRACT_IRI = BASE_IRI + "Abstract";
	static String EVENT_IRI = BASE_IRI + "Event";
	static String QUALITY_IRI = BASE_IRI + "Quality";
	static String OBJECT_IRI = BASE_IRI + "Object";
	
	
	private OWLClass Entity;
	private OWLClass Abstract;
	private OWLClass Event; 
	private OWLClass Quality; 
	private OWLClass Object; 

	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public TypeOfEntitiesCDP(String dirPath) 
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);	
		this.init();
	}

	private void init() 
	{
		this.Entity = df.getOWLClass(IRI.create(ENTITY_IRI));
		this.Abstract = df.getOWLClass(IRI.create(ABSTRACT_IRI));
		this.Event = df.getOWLClass(IRI.create(EVENT_IRI));
		this.Quality = df.getOWLClass(IRI.create(QUALITY_IRI));
		this.Object = df.getOWLClass(IRI.create(OBJECT_IRI));
	}
	
	/**
	 * @return the {@linkplain #entity}
	 */
	public OWLClass getEntity()
	{
		return Entity;
	}

	/**
	 * @return the {@linkplain #abstract}
	 */
	public OWLClass getAbstract()
	{
		return Abstract;
	}

	/**
	 * @return the {@linkplain #event}
	 */
	public OWLClass getEvent()
	{
		return Event;
	}

	/**
	 * @return the {@linkplain #quality}
	 */
	public OWLClass getQuality()
	{
		return Quality;
	}

	/**
	 * @return the {@linkplain #object}
	 */
	public OWLClass getObject()
	{
		return Object;
	}

}
