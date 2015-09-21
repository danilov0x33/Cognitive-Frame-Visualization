package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.TaskExecutionRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain TaskExecutionRealization}.
 */
public class TaskExecutionDataSet extends DataSet
{
	private OWLClass action;
	private OWLClass task;
	
	/**
	 * {@linkplain TaskExecutionDataSet}
	 */
	public TaskExecutionDataSet(OWLClass action, OWLClass task)
	{
		this.action = action;
		this.task = task;
	}

	/**
	 * @return the {@linkplain #action}
	 */
	public OWLClass getAction()
	{
		return action;
	}

	/**
	 * @param action the {@linkplain #action} to set
	 */
	public void setAction(OWLClass action)
	{
		this.action = action;
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
}
