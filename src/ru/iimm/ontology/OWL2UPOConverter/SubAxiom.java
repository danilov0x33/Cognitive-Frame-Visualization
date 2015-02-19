package ru.iimm.ontology.OWL2UPOConverter;
import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

import ru.iimm.ontology.ontAPI.ConstantsOntAPI;
import ru.iimm.ontology.ontAPI.Ontology;

/**
 * 
 */

/**
 * Субаксиома - часть составной аксиомы. Субаксиома может быть либо 
 * простой - состоять из одного именованного класса или составной - 
 * коньюнкция классов и других classExpressions.
 * Субаксиома представляется в ОПП отдельным экземпляром и связывается 
 * с субаксиомами (простыми и сложными), включенными в нее.
 * Субаксима является отражением OWLClassExpression из OWLapi.
 * @author Lomov P.A. 
 *
 */
public abstract class SubAxiom implements OWLClassExpression
//public interface SubAxiom extends ShareInf_thsAPI
{
	

	/**
	 * Название концепта-аксиомы.
	 */
	private String title;
	
	/**
	 * Переменная, соответвующая данной субаксиоме. 
	 */
	private String sparqlVar;
	
	/**
	 * Фрагмент SPARQL запроса, соответствующий данной аксиоме.
	 */
	private String sparqlQueryPart;
	
	
	/**
	 * Список именованных классов в аксиоме.
	 */
	private ArrayList<OWLClass> clsList;
	
	
	
	/**
	 * OWL выражение, из которого создается данный экземпляр 
	 * субаксиомы. 
	 */
	private OWLClassExpression subAxOWL;

	

	
	/**
	 * @param title
	 * @param sparqlVar
	 * @param sparqlQueryPart
	 * @param clsList
	 * @param subAxOWL
	 */
	public SubAxiom(String title, String sparqlVar, String sparqlQueryPart,
		ArrayList<OWLClass> clsList, OWLClassExpression subAxOWL)
	{
	    super();
	    this.title = title;
	    this.sparqlVar = sparqlVar;
	    this.sparqlQueryPart = sparqlQueryPart;
	    this.clsList = clsList;
	    this.subAxOWL = subAxOWL;
	}

	

//========================================================//
//////////////////////Методы////////////////////////////////
//========================================================//


	
	/**
	 * 
	 */
	SubAxiom()
	{
	    super();
	    // TODO Auto-generated constructor stub
	}



	/**
	 * 
	 * @return
	 */
	public String getTitle()
	{
	    if (this.title==null) this.title=this.generateTitle();		
	    return this.title;
	}
	

	
	
	/**
	 * Создает название для субаксиомы из ее элементов.
	 * @return
	 */
	abstract protected String generateTitle();



	/**
	 * Определяет сложная субаксиома или простая передана в качестве аргумента.
	 * @param subAx
	 * @return
	 */
	public static boolean isComplexSubAx(OWLClassExpression subAx)
	{
		return subAx.isAnonymous();
	}
	

	

	@Override
	public void accept(OWLObjectVisitor arg0)
	{
	    this.subAxOWL.accept(arg0);
	    
	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> arg0)
	{
	    
	    return this.subAxOWL.accept(arg0);
	}

	@Override
	public Set<OWLAnonymousIndividual> getAnonymousIndividuals()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getAnonymousIndividuals();
	}

	@Override
	public Set<OWLClass> getClassesInSignature()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getClassesInSignature();
	}

	@Override
	public Set<OWLDataProperty> getDataPropertiesInSignature()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getDataPropertiesInSignature() ;
	}

	@Override
	public Set<OWLDatatype> getDatatypesInSignature()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getDatatypesInSignature() ;
	}

	@Override
	public Set<OWLNamedIndividual> getIndividualsInSignature()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getIndividualsInSignature() ;
	}

	@Override
	public Set<OWLClassExpression> getNestedClassExpressions()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getNestedClassExpressions() ;
	}

	@Override
	public Set<OWLObjectProperty> getObjectPropertiesInSignature()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getObjectPropertiesInSignature() ;
	}


	
	@Override
	public Set<OWLEntity> getSignature()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getSignature() ;
	}

	@Override
	public boolean isBottomEntity()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.isBottomEntity() ;
	}

	@Override
	public boolean isTopEntity()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.isTopEntity() ;
	}

	@Override
	public int compareTo(OWLObject arg0)
	{
	    
	    return this.subAxOWL.compareTo(arg0);
	}

	@Override
	public void accept(OWLClassExpressionVisitor arg0)
	{
	    this.subAxOWL.accept(arg0);
	    
	}

	@Override
	public <O> O accept(OWLClassExpressionVisitorEx<O> arg0)
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.accept(arg0) ;
	}

	@Override
	public Set<OWLClassExpression> asConjunctSet()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.asConjunctSet() ;
	}

	@Override
	public Set<OWLClassExpression> asDisjunctSet()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.asDisjunctSet() ;
	}

	@Override
	public OWLClass asOWLClass()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.asOWLClass() ;
	}

	@Override
	public boolean containsConjunct(OWLClassExpression arg0)
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.containsConjunct(arg0) ;
	}

	@Override
	public ClassExpressionType getClassExpressionType()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getClassExpressionType() ;
	}

	@Override
	public OWLClassExpression getComplementNNF()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getComplementNNF() ;
	}

	@Override
	public OWLClassExpression getNNF()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getNNF() ;
	}

	@Override
	public OWLClassExpression getObjectComplementOf()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.getObjectComplementOf() ;
	}

	@Override
	public boolean isAnonymous()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.isAnonymous() ;
	}

	@Override
	public boolean isClassExpressionLiteral()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.isClassExpressionLiteral() ;
	}

	@Override
	public boolean isOWLNothing()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.isOWLNothing() ;
	}

	@Override
	public boolean isOWLThing()
	{
	    // TODO Auto-generated method stub
	    return this.subAxOWL.isOWLThing() ;
	}

	public String getSparqlVar()
	{
		return sparqlVar;
	}

	public String getSparqlQueryPart()
	{
		return sparqlQueryPart;
	}

	public ArrayList<OWLClass> getClsList()
	{
		return clsList;
	}

	public OWLClassExpression getSubAxOWL()
	{
		return subAxOWL;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setSparqlVar(String sparqlVar)
	{
		this.sparqlVar = sparqlVar;
	}

	public void setSparqlQueryPart(String sparqlQueryPart)
	{
		this.sparqlQueryPart = sparqlQueryPart;
	}

	public void setClsList(ArrayList<OWLClass> clsList)
	{
		this.clsList = clsList;
	}

	public void setSubAxOWL(OWLClassExpression subAxOWL)
	{
		this.subAxOWL = subAxOWL;
	}
	
	abstract public OWLNamedIndividual addSubaxiom(UserPresenOnt upo);

	abstract public OWLNamedIndividual addRelatedSubAxiom
	(OWLNamedIndividual mainSubaxInd, IRI relationIRI, UserPresenOnt upo);
	
	
}
