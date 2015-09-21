package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class SequenceCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/sequence.owl";
	static String BASE_IRI = ONT_IRI + "#";	
	static String FILENAME = "sequence.owl";
	
	/* Properties names */
	static String PRECEDES_IRI = BASE_IRI + "precedes";
	static String DIR_PRECEDES_IRI = BASE_IRI + "directlyPrecedes";
	static String FOLLOWS_IRI = BASE_IRI + "follows";
	static String DIR_FOLLOWS_IRI = BASE_IRI + "directlyFollows";	
	
	private OWLObjectProperty precedes;
	private OWLObjectProperty directlyPrecedes;
	private OWLObjectProperty follows;
	private OWLObjectProperty directlyFollows;
	

	private void init() 
	{
		this.precedes = df.getOWLObjectProperty(IRI.create(PRECEDES_IRI));
		this.directlyPrecedes = df.getOWLObjectProperty(IRI.create(DIR_PRECEDES_IRI));
		this.follows = df.getOWLObjectProperty(IRI.create(FOLLOWS_IRI));
		this.directlyFollows = df.getOWLObjectProperty(IRI.create(DIR_FOLLOWS_IRI));		
		
	}
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public SequenceCDP(String dirPath) 
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);	
		init();
	}

	/**
	 * @return the {@linkplain #pRECEDES_IRI}
	 */
	public static String getPRECEDES_IRI()
	{
		return PRECEDES_IRI;
	}

	/**
	 * @return the {@linkplain #precedes}
	 */
	public OWLObjectProperty getPrecedes()
	{
		return precedes;
	}

	/**
	 * @return the {@linkplain #directlyPrecedes}
	 */
	public OWLObjectProperty getDirectlyPrecedes()
	{
		return directlyPrecedes;
	}

	/**
	 * @return the {@linkplain #follows}
	 */
	public OWLObjectProperty getFollows()
	{
		return follows;
	}

	/**
	 * @return the {@linkplain #directlyFollows}
	 */
	public OWLObjectProperty getDirectlyFollows()
	{
		return directlyFollows;
	}
	
}
