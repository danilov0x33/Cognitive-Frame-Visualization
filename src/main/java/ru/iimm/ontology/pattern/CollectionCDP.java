package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class CollectionCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/collectionentity.owl";
	static String BASE_IRI = ONT_IRI + "#";
	static String FILENAME = "collectionentity.owl";
	
	/* Class names */
	static String COLLECTION_IRI = BASE_IRI + "Collection";
	
	/* Properties names */
	static String HAS_MEMBER_IRI 		= BASE_IRI + "hasMember";
	static String IS_MEMBER_OF_IRI 	= BASE_IRI + "isMemberOf";
	
	private OWLClass collection;
	
	private OWLObjectProperty hasMember;
	private OWLObjectProperty isMemberOf;
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public CollectionCDP(String dirPath)
	{
		super(IRI.create(BASE_IRI), dirPath, FILENAME);
		this.init();
	}

	private void init()
	{
		this.collection = df.getOWLClass(IRI.create(COLLECTION_IRI));
		
		this.hasMember = df.getOWLObjectProperty(IRI.create(HAS_MEMBER_IRI));
		this.isMemberOf = df.getOWLObjectProperty(IRI.create(IS_MEMBER_OF_IRI));
	}

	/**
	 * @return the {@linkplain #collection}
	 */
	public OWLClass getCollection()
	{
		return collection;
	}

	/**
	 * @return the {@linkplain #hasMember}
	 */
	public OWLObjectProperty getHasMember()
	{
		return hasMember;
	}

	/**
	 * @return the {@linkplain #isMemberOf}
	 */
	public OWLObjectProperty getIsMemberOf()
	{
		return isMemberOf;
	}
}
