package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.pattern.ObjectRoleCDP;

public class ObjectRoleRealization extends ODPRealization
{
	private OWLClass subObject;
	private OWLClass subRole;
	
	/**
	 * {@linkplain ObjectRoleRealization}
	 */
	private ObjectRoleRealization(){}

	public OWLClass getSubObject()
	{
		return subObject;
	}

	public OWLClass getSubRole()
	{
		return subRole;
	}
	
	public static Builder newBuilder(ObjectRoleCDP pattern)
	{
		return new ObjectRoleRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private ObjectRoleCDP pattern;
		
		private Builder(ObjectRoleCDP pattern)
		{
			this.pattern = pattern;
		}

		public Builder setSubRole(OWLClass subRole)
		{
			ObjectRoleRealization.this.subRole = subRole;
			return this;
		}

		public Builder setSubObject(OWLClass subObject)
		{
			ObjectRoleRealization.this.subObject = subObject;
			return this;
		}
		
		public ObjectRoleRealization build()
		{	
			//Создаем новый объект
			ObjectRoleRealization realization = new ObjectRoleRealization();			
			realization.subObject = ObjectRoleRealization.this.subObject;
			realization.subRole = ObjectRoleRealization.this.subRole;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			structuralAxList.add(df.getOWLSubClassOfAxiom(subObject, pattern.getObject()));
			structuralAxList.add(df.getOWLSubClassOfAxiom(subRole, pattern.getRole()));
			
			OWLObjectSomeValuesFrom hasSomeRole = df.getOWLObjectSomeValuesFrom(pattern.getHasRole(), subRole);
			OWLObjectSomeValuesFrom isRoleOfSomeObject = df.getOWLObjectSomeValuesFrom(pattern.getIsRoleOf(), subObject);
			structuralAxList.add(df.getOWLSubClassOfAxiom(subObject, hasSomeRole));
			structuralAxList.add(df.getOWLSubClassOfAxiom(subRole, isRoleOfSomeObject));

			return realization;
		}
	}
}
