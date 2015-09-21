package ru.iimm.ontology.pattern.dataset;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.pattern.realizations.ObjectRoleRealization;
/**
 * 
 *
 * @author Danilov
 * @version 0.1
 * @deprecated - Используй билдер в {@linkplain ObjectRoleRealization}.
 */
public class ObjectRoleDataSet extends DataSet
{
	private OWLClass subObject;
	private OWLClass subRole;
	
	/**
	 * {@linkplain ObjectRoleDataSet}
	 */
	public ObjectRoleDataSet(OWLClass subObject, OWLClass subRole)
	{
		this.subObject = subObject;
		this.subRole = subRole;
	}

	/**
	 * @return the {@linkplain #subObject}
	 */
	public OWLClass getSubObject()
	{
		return subObject;
	}

	/**
	 * @param subObject the {@linkplain #subObject} to set
	 */
	public void setSubObject(OWLClass subObject)
	{
		this.subObject = subObject;
	}

	/**
	 * @return the {@linkplain #subRole}
	 */
	public OWLClass getSubRole()
	{
		return subRole;
	}

	/**
	 * @param subRole the {@linkplain #subRole} to set
	 */
	public void setSubRole(OWLClass subRole)
	{
		this.subRole = subRole;
	}
}
