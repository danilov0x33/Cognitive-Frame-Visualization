/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter.parsedAxioms;

import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

import ru.iimm.ontology.ontAPI.Ontology;

/**
 * Абстрактная аксиома оперделяет основные поля и методы для других типов
 * аксиом. Обертка для OWLlogicalAxiom.
 * 
 * @author Lomov P.A.
 *
 */
public abstract class AbstractAxiom implements Cloneable, OWLLogicalAxiom
{

	/**
	 * Исходная OWL-аксиома.
	 */
	OWLLogicalAxiom originalAxiom;

	public AbstractAxiom(OWLLogicalAxiom originalAxiom)
	{
		super();
		this.originalAxiom = originalAxiom;

	}

	/**
	 * 
	 */
	AbstractAxiom()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Возвращает копию аксиомы. Метод используется для создания копии аксиомы
	 * для понятия-наследника, т.е. наследнику приписывается копия аксиомы.
	 * 
	 * @param ax
	 * @return
	 */
	public AbstractAxiom clone() throws CloneNotSupportedException
	{
		return (AbstractAxiom) super.clone();
	}

	@Override
	public void accept(OWLAxiomVisitor arg0)
	{

		this.originalAxiom.accept(arg0);

	}

	@Override
	public <O> O accept(OWLAxiomVisitorEx<O> arg0)
	{
		return this.originalAxiom.accept(arg0);
	}

	@Override
	public boolean equalsIgnoreAnnotations(OWLAxiom arg0)
	{
		return this.originalAxiom.equalsIgnoreAnnotations(arg0);
	}

	@Override
	public OWLAxiom getAnnotatedAxiom(Set<OWLAnnotation> arg0)
	{
		return this.originalAxiom.getAnnotatedAxiom(arg0);
	}

	@Override
	public Set<OWLAnnotation> getAnnotations()
	{

		return this.originalAxiom.getAnnotations();
	}

	@Override
	public Set<OWLAnnotation> getAnnotations(OWLAnnotationProperty arg0)
	{
		return this.originalAxiom.getAnnotations(arg0);
	}

	@Override
	public AxiomType<?> getAxiomType()
	{

		return this.originalAxiom.getAxiomType();
	}

	@Override
	public OWLAxiom getAxiomWithoutAnnotations()
	{

		return this.originalAxiom.getAxiomWithoutAnnotations();
	}

	@Override
	public OWLAxiom getNNF()
	{
		return this.originalAxiom.getNNF();
	}

	@Override
	public boolean isAnnotated()
	{
		return this.originalAxiom.isAnnotated();

	}

	@Override
	public boolean isAnnotationAxiom()
	{
		return this.originalAxiom.isAnnotationAxiom();

	}

	@Override
	public boolean isLogicalAxiom()
	{
		return this.originalAxiom.isLogicalAxiom();

	}

	@Override
	public boolean isOfType(AxiomType<?>... arg0)
	{
		return this.originalAxiom.isOfType(arg0);

	}

	@Override
	public boolean isOfType(Set<AxiomType<?>> arg0)
	{
		return this.originalAxiom.isOfType(arg0);

	}

	@Override
	public void accept(OWLObjectVisitor arg0)
	{
		this.originalAxiom.accept(arg0);

	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> arg0)
	{
		return this.originalAxiom.accept(arg0);

	}

	@Override
	public Set<OWLClass> getClassesInSignature()
	{
		return this.originalAxiom.getClassesInSignature();

	}

	@Override
	public Set<OWLDataProperty> getDataPropertiesInSignature()
	{
		return this.originalAxiom.getDataPropertiesInSignature();

	}

	@Override
	public Set<OWLDatatype> getDatatypesInSignature()
	{
		return this.originalAxiom.getDatatypesInSignature();

	}

	@Override
	public Set<OWLNamedIndividual> getIndividualsInSignature()
	{
		return this.originalAxiom.getIndividualsInSignature();

	}

	@Override
	public Set<OWLClassExpression> getNestedClassExpressions()
	{
		return this.originalAxiom.getNestedClassExpressions();

	}

	@Override
	public Set<OWLObjectProperty> getObjectPropertiesInSignature()
	{
		return this.originalAxiom.getObjectPropertiesInSignature();

	}

	@Override
	public Set<OWLEntity> getSignature()
	{
		return this.originalAxiom.getSignature();

	}

	@Override
	public boolean isBottomEntity()
	{
		return this.originalAxiom.isBottomEntity();

	}

	@Override
	public boolean isTopEntity()
	{
		return this.originalAxiom.isTopEntity();

	}

	@Override
	public int compareTo(OWLObject arg0)
	{
		return this.originalAxiom.compareTo(arg0);

	}
	
	@Override
	public Set<OWLAnonymousIndividual> getAnonymousIndividuals()
	{
	    return this.originalAxiom.getAnonymousIndividuals();
	}


}
