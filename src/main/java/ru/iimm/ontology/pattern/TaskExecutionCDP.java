package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class TaskExecutionCDP extends ContentDesingPattern
{
	/* Common const */
	static String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/taskexecution.owl";
	static String BASE_IRI = ONT_IRI + "#";
	static String BASE_TASKROLE_IRI = "http://www.ontologydesignpatterns.org/cp/owl/taskrole.owl#";
	static String BASE_OBJECTROLE_IRI = "http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl#";
	static String BASE_AGENTROLE_IRI = "http://www.ontologydesignpatterns.org/cp/owl/agentrole.owl#";	
	static String FILENAME = "taskexecution.owl";
	
	/* Class names */
	static String ROLE_TASKROLE_IRI		 	= BASE_TASKROLE_IRI + "Role";
	static String ROLE_OBJECTROLE_IRI		= BASE_OBJECTROLE_IRI + "Role";
	static String ACTION_IRI 				= BASE_IRI + "Action";
	static String AGENT_AGENTROLE_IRI		= BASE_AGENTROLE_IRI + "Agent";
	static String OBJECT_OBJECTROLE_IRI 	= BASE_OBJECTROLE_IRI + "Object";
	static String TASK_TASKROLE_IRI 		= BASE_TASKROLE_IRI + "Task";
	
	
	
	/* Properties names */
	static String HAS_TASK_IRI 		= BASE_TASKROLE_IRI + "hasTask";
	static String IS_TASK_OF_IRI 	= BASE_TASKROLE_IRI + "isTaskOf";
	
	static String IS_EXECUTED_IN = BASE_IRI + "isExecutedIn";
	static String EXECUTES_TASK_IRI = BASE_IRI + "executesTask";
	
	static String IS_ROLE_OF_IRI = BASE_OBJECTROLE_IRI + "isRoleOf";
	static String HAS_ROLE_IRI = BASE_OBJECTROLE_IRI + "hasRole";

	

	private OWLClass roleObjectRole;
	private OWLClass roleTaskRole;
	private OWLClass action; 
	private OWLClass agentAgentRole;
	private OWLClass objectObjectRole;
	private OWLClass taskTaskRole;

	
	private OWLObjectProperty hasTask;
	private OWLObjectProperty isTaskOf;
	
	private OWLObjectProperty isExecutedIn;
	private OWLObjectProperty executesTask;

	private OWLObjectProperty isRoleOf;
	private OWLObjectProperty hasRole;

	
	private void init() 
	{
		this.roleObjectRole = df.getOWLClass(IRI.create(ROLE_OBJECTROLE_IRI));
		this.roleTaskRole = df.getOWLClass(IRI.create(ROLE_TASKROLE_IRI));
		this.taskTaskRole = df.getOWLClass(IRI.create(TASK_TASKROLE_IRI));
		this.action = df.getOWLClass(IRI.create(ACTION_IRI));
		this.agentAgentRole = df.getOWLClass(IRI.create(AGENT_AGENTROLE_IRI));
		this.objectObjectRole = df.getOWLClass(IRI.create(OBJECT_OBJECTROLE_IRI));
		this.taskTaskRole = df.getOWLClass(IRI.create(TASK_TASKROLE_IRI));

		this.isTaskOf = df.getOWLObjectProperty(IRI.create(IS_TASK_OF_IRI));
		this.hasTask = df.getOWLObjectProperty(IRI.create(HAS_TASK_IRI));
		
		this.isExecutedIn = df.getOWLObjectProperty(IRI.create(IS_EXECUTED_IN));
		this.executesTask = df.getOWLObjectProperty(IRI.create(EXECUTES_TASK_IRI));		
	
		this.isRoleOf = df.getOWLObjectProperty(IRI.create(IS_ROLE_OF_IRI));
		this.hasRole = df.getOWLObjectProperty(IRI.create(HAS_ROLE_IRI));			
	}
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */	
	public TaskExecutionCDP(String dirPath) 
	{
		super(IRI.create(ONT_IRI), dirPath, FILENAME);	
		init();
	}
	/**
	 * @return the {@linkplain #roleObjectRole}
	 */
	public OWLClass getRoleObjectRole()
	{
		return roleObjectRole;
	}

	/**
	 * @return the {@linkplain #roleTaskRole}
	 */
	public OWLClass getRoleTaskRole()
	{
		return roleTaskRole;
	}

	/**
	 * @return the {@linkplain #action}
	 */
	public OWLClass getAction()
	{
		return action;
	}

	/**
	 * @return the {@linkplain #agentAgentRole}
	 */
	public OWLClass getAgentAgentRole()
	{
		return agentAgentRole;
	}

	/**
	 * @return the {@linkplain #objectObjectRole}
	 */
	public OWLClass getObjectObjectRole()
	{
		return objectObjectRole;
	}

	/**
	 * @return the {@linkplain #taskTaskRole}
	 */
	public OWLClass getTaskRole()
	{
		return taskTaskRole;
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

	/**
	 * @return the {@linkplain #isExecutedIn}
	 */
	public OWLObjectProperty getIsExecutedIn()
	{
		return isExecutedIn;
	}

	/**
	 * @return the {@linkplain #executesTask}
	 */
	public OWLObjectProperty getExecutesTask()
	{
		return executesTask;
	}

	/**
	 * @return the {@linkplain #isRoleOf}
	 */
	public OWLObjectProperty getIsRoleOf()
	{
		return isRoleOf;
	}

	/**
	 * @return the {@linkplain #hasRole}
	 */
	public OWLObjectProperty getHasRole()
	{
		return hasRole;
	}
}
