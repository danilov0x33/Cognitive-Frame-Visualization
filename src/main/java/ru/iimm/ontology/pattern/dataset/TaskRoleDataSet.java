package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.TaskRoleRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain TaskRoleRealization}.
 */
public class TaskRoleDataSet extends DataSet
{
	private OWLClass task;
	private OWLClass role;
	
	/**
	 * {@linkplain TaskRoleDataSet}
	 */
	public TaskRoleDataSet(OWLClass task, OWLClass role)
	{
		this.task = task;
		this.role = role;
	}

	/**
	 * @return the {@linkplain #task}
	 */
	public OWLClass getTask()
	{
		return task;
	}

	/**
	 * @param task the {@linkplain #task} to set
	 */
	public void setTask(OWLClass task)
	{
		this.task = task;
	}

	/**
	 * @return the {@linkplain #role}
	 */
	public OWLClass getRole()
	{
		return role;
	}

	/**
	 * @param role the {@linkplain #role} to set
	 */
	public void setRole(OWLClass role)
	{
		this.role = role;
	}
}
