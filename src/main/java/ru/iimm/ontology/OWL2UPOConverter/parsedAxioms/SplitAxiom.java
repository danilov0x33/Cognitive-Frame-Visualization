/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter.parsedAxioms;

import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;

import ru.iimm.ontology.ontAPI.Ontology;
import scala.collection.mutable.Publisher;

/**
 * Аксиома, разделенная на левую и правую часть.
 * @author Lomov P. A.
 *
 */
public class SplitAxiom extends AbstractAxiom 
{
	
	/**
	 * Левая часть аксиомы в виде OWLclassExpression.
	 */
	OWLClassExpression leftOWLclassExpression;

	/**
	 * Правая часть аксиомы в виде OWLclassExpression.
	 */
	OWLClassExpression rightOWLclassExpression;

	
	
	
	/**
	 * @param originalAxiom
	 * @param ontOWL
	 * @param ontUPO
	 */
	protected SplitAxiom(OWLLogicalAxiom originalAxiom)
	{
	    super(originalAxiom);
	}


	

	/**
	 * 
	 */
	SplitAxiom()
	{
	    super();
	    // TODO Auto-generated constructor stub
	}




	public SplitAxiom(OWLClassExpression leftOWLclassExpression, 
		OWLClassExpression rightOWLclassExpression, OWLLogicalAxiom ax) 
	{
		super(ax);
		this.leftOWLclassExpression = leftOWLclassExpression;
		this.rightOWLclassExpression = rightOWLclassExpression;
	}




	public OWLClassExpression getLeftOWLclassExpression() 
	{
		return leftOWLclassExpression;
	}




	public void setLeftOWLclassExpression(OWLClassExpression leftOWLclassExpression) 
	{
		this.leftOWLclassExpression = leftOWLclassExpression;
	}




	public OWLClassExpression getRightOWLclassExpression()
	{
		return rightOWLclassExpression;
	}




	public void setRightOWLclassExpression(OWLClassExpression rightOWLclassExpression) 
	{
		this.rightOWLclassExpression = rightOWLclassExpression;
	}
	
	
	public Boolean isCgiAxiom()
	{
	    return this.leftOWLclassExpression.isAnonymous();
	}
	
	public Boolean isSimpleAxiom()
	{
	    return (!this.leftOWLclassExpression.isAnonymous() && !this.rightOWLclassExpression.isAnonymous());
	}






}
