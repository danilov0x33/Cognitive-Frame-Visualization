package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class TaskRoleCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/taskrole.owl";
	static String BASE_IRI = ONT_IRI + "#";	
	static String FILENAME = "taskrole.owl";
	
	/* Class names */
	static String ROLE_IRI = BASE_IRI + "Role";
	static String TASK_IRI = BASE_IRI + "Task";
	
	/* Properties names */
	static String HAS_TASK_IRI = BASE_IRI + "hasTask";
	static String IS_TASK_OF_IRI = BASE_IRI + "isTaskOf";
	
	private OWLClass task;
	private OWLClass role; 
	
	private OWLObjectProperty hasTask;
	private OWLObjectProperty isTaskOf;

	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public TaskRoleCDP(String dirPath) 
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);	
		this.init();
	}
	
	private void init() 
	{
		this.task = df.getOWLClass(IRI.create(TASK_IRI));
		this.role = df.getOWLClass(IRI.create(ROLE_IRI));
		this.isTaskOf = df.getOWLObjectProperty(IRI.create(IS_TASK_OF_IRI));
		this.hasTask = df.getOWLObjectProperty(IRI.create(HAS_TASK_IRI));		
	}

	/**
	 * @return the {@linkplain #task}
	 */
	public OWLClass getTask()
	{
		return task;
	}

	/**
	 * @return the {@linkplain #role}
	 */
	public OWLClass getRole()
	{
		return role;
	}

	/**
	 * @return the {@linkplain #hasTask}
	 */
	public OWLObjectProperty getHasTask()
	{
		return hasTask;
	}

	/**
	 * @return the {@linkplain #isTaskOf}
	 */
	public OWLObjectProperty getIsTaskOf()
	{
		return isTaskOf;
	}

}
