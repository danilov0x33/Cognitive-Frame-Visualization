package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.pattern.TaskRoleCDP;

public class TaskRoleRealization extends ODPRealization
{
	private OWLClass task;
	private OWLClass role;
	
	/**
	 * {@linkplain TaskRoleRealization}
	 */
	private TaskRoleRealization(){}

	public OWLClass getTask()
	{
		return task;
	}

	public OWLClass getRole()
	{
		return role;
	}
	
	public static Builder newBuilder(TaskRoleCDP pattern)
	{
		return new TaskRoleRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private TaskRoleCDP pattern;
		
		private Builder(TaskRoleCDP pattern)
		{
			this.pattern = pattern;
		}

		public Builder setTask(OWLClass task)
		{
			TaskRoleRealization.this.task = task;
			return this;
		}

		public Builder setRole(OWLClass role)
		{
			TaskRoleRealization.this.role = role;
			return this;
		}
		
		public TaskRoleRealization build()
		{	
			//Создаем новый объект
			TaskRoleRealization realization = new TaskRoleRealization();			
			realization.task = TaskRoleRealization.this.task;
			realization.role = TaskRoleRealization.this.role;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			structuralAxList.add(df.getOWLSubClassOfAxiom(task, pattern.getTask()));
			structuralAxList.add(df.getOWLSubClassOfAxiom(role, pattern.getRole()));
			
			OWLObjectSomeValuesFrom hasTask = df.getOWLObjectSomeValuesFrom(pattern.getHasTask(), task);
			OWLObjectSomeValuesFrom isTaskOf = df.getOWLObjectSomeValuesFrom(pattern.getIsTaskOf(), role);
			structuralAxList.add(df.getOWLSubClassOfAxiom(task, hasTask));
			structuralAxList.add(df.getOWLSubClassOfAxiom(role, isTaskOf));
			
			return realization;
		}
	}
}
