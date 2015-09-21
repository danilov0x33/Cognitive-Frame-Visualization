package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class ObjectRoleCDP extends ContentDesingPattern
{	
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl";
	static String BASE_IRI = ONT_IRI + "#";	
	static String FILENAME = "objectrole.owl";
	
	/* Class names */
	static String CONCEPT_IRI = BASE_IRI + "Concept";
	static String OBJECT_IRI = BASE_IRI + "Object";
	static String ROLE_IRI = BASE_IRI + "Role";
	
	/* Properties names */
	static String ROLE_OF_IRI = BASE_IRI + "isRoleOf";
	static String HAS_ROLE_IRI = BASE_IRI + "hasRole";
	
	private OWLClass Object;
	private OWLClass Role; 
	
	private OWLObjectProperty IsRoleOf;
	private OWLObjectProperty HasRole;
	
	private void init() 
	{
		this.Object = df.getOWLClass(IRI.create(OBJECT_IRI));
		this.Role = df.getOWLClass(IRI.create(ROLE_IRI));
		this.IsRoleOf = df.getOWLObjectProperty(IRI.create(ROLE_OF_IRI));
		this.HasRole = df.getOWLObjectProperty(IRI.create(HAS_ROLE_IRI));		
	}

	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public ObjectRoleCDP(String dirPath) 
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);	
		init();
	}

	/**
	 * @return the {@linkplain #object}
	 */
	public OWLClass getObject()
	{
		return Object;
	}

	/**
	 * @return the {@linkplain #role}
	 */
	public OWLClass getRole()
	{
		return Role;
	}

	/**
	 * @return the {@linkplain #isRoleOf}
	 */
	public OWLObjectProperty getIsRoleOf()
	{
		return IsRoleOf;
	}

	/**
	 * @return the {@linkplain #hasRole}
	 */
	public OWLObjectProperty getHasRole()
	{
		return HasRole;
	}
}
