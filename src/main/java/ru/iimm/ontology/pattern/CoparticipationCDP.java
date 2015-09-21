package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class CoparticipationCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/coparticipation.owl";
	static String BASE_IRI = ONT_IRI + "#";
	static String FILENAME = "coparticipation.owl";
	
	/* Properties names */
	static String HAS_COPARTICIPATES_WITH_IRI 		= BASE_IRI + "coparticipatesWith";
	
	private OWLObjectProperty coparticipatesWith;
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public CoparticipationCDP(String dirPath)
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);
		this.init();
	}

	private void init()
	{		
		this.coparticipatesWith = df.getOWLObjectProperty(IRI.create(HAS_COPARTICIPATES_WITH_IRI));
	}

	/**
	 * @return the {@linkplain #coparticipatesWith}
	 */
	public OWLObjectProperty getCoparticipatesWith()
	{
		return coparticipatesWith;
	}
}
