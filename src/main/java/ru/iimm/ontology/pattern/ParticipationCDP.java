package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public  class ParticipationCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/participation.owl";
	static String BASE_IRI = ONT_IRI + "#";	
	static String FILENAME = "participation.owl";
	
	/* Class names */
	static String EVENT_IRI = BASE_IRI + "Event";
	static String OBJECT_IRI = BASE_IRI + "Object";
	
	/* Properties names */
	static String HAS_PARTICIPANT_IRI = BASE_IRI + "hasParticipant ";
	static String IS_PARTICIPANT_OF_IRI = BASE_IRI + "isParticipantIn ";
	
	private OWLClass event;
	private OWLClass object;
	
	private OWLObjectProperty hasParticipant;
	private OWLObjectProperty isParticipantIn;
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public ParticipationCDP(String dirPath)
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);
		this.init();
	}

	private void init()
	{
		this.event = df.getOWLClass(IRI.create(EVENT_IRI));
		this.object = df.getOWLClass(IRI.create(OBJECT_IRI));
		
		this.hasParticipant = df.getOWLObjectProperty(IRI.create(HAS_PARTICIPANT_IRI));
		this.isParticipantIn = df.getOWLObjectProperty(IRI.create(IS_PARTICIPANT_OF_IRI));
	}

	/**
	 * @return the {@linkplain #event}
	 */
	public OWLClass getEvent()
	{
		return event;
	}

	/**
	 * @return the {@linkplain #object}
	 */
	public OWLClass getObject()
	{
		return object;
	}

	/**
	 * @return the {@linkplain #hasParticipant}
	 */
	public OWLObjectProperty getHasParticipant()
	{
		return hasParticipant;
	}

	/**
	 * @return the {@linkplain #isParticipantIn}
	 */
	public OWLObjectProperty getIsParticipantIn()
	{
		return isParticipantIn;
	}
}
