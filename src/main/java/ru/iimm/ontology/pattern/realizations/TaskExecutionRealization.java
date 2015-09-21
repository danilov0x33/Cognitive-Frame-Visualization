package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.pattern.TaskExecutionCDP;

public class TaskExecutionRealization extends ODPRealization
{
	private OWLClass action;
	private OWLClass task;
	
	/**
	 * {@linkplain TaskExecutionRealization}
	 */
	private TaskExecutionRealization(){}

	public OWLClass getAction()
	{
		return action;
	}

	public OWLClass getTask()
	{
		return task;
	}
	
	public static Builder newBuilder(TaskExecutionCDP pattern)
	{
		return new TaskExecutionRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private TaskExecutionCDP pattern;
		
		private Builder(TaskExecutionCDP pattern)
		{
			this.pattern = pattern;
		}

		public Builder setTask(OWLClass task)
		{
			TaskExecutionRealization.this.task = task;
			return this;
		}
		
		public Builder setAction(OWLClass action)
		{
			TaskExecutionRealization.this.action = action;
			return this;
		}
		
		public TaskExecutionRealization build()
		{	
			//Создаем новый объект
			TaskExecutionRealization realization = new TaskExecutionRealization();			
			realization.task = TaskExecutionRealization.this.task;
			realization.action = TaskExecutionRealization.this.action;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			structuralAxList.add(df.getOWLSubClassOfAxiom(task, pattern.getTaskRole()));
			structuralAxList.add(df.getOWLSubClassOfAxiom(action, pattern.getAction()));
			
			OWLObjectSomeValuesFrom isExecutedIn = df.getOWLObjectSomeValuesFrom(pattern.getIsExecutedIn(), action);
			OWLObjectSomeValuesFrom executesTask = df.getOWLObjectSomeValuesFrom(pattern.getExecutesTask(), task);
			structuralAxList.add(df.getOWLSubClassOfAxiom(task, isExecutedIn));
			structuralAxList.add(df.getOWLSubClassOfAxiom(action, executesTask));
			
			return realization;
		}
	}
}
