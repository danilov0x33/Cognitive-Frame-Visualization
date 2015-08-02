package ru.iimm.ontology.OWL2UPOConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;






import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNaryClassAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.ontAPI.ConstantsOntAPI;
import ru.iimm.ontology.ontAPI.Ontology;

//import com.clarkparsia.owlapiv3.OWL;

/**
 * Аксиома OWL, состоящая из левой и правой части, которые представлены субаксиомами.
 * @author lomov
 *
 */
public class ParsedAxiom extends SplitAxiom
{
    	
	private static final Logger LOGGER = LoggerFactory
		.getLogger(ParsedAxiom.class);
    
	/**
	 * Левая распарсенная часть аксиомы в виде списка субаксиом.
	 */
	private ArrayList<SubAxiom> lSideOfAx;
	
	/**
	 * Правая распарсенная часть аксиомы в виде списка субаксиом.
	 */
	private ArrayList<SubAxiom> rSideOfAx;
	
	/**
	 * IRI свойства-отношения с определяемым объектом,
	 * определяется, исходя из отношения между
	 * левой и правой частью аксиомы.
	 */
	private IRI relationIRI;

	
	
	private static final Logger log = LoggerFactory.getLogger(ParsedAxiom.class);

/*************************************************************
 **************************** Методы *************************
 *************************************************************/
	
	protected ParsedAxiom(SplitAxiom originalAxiom)
	{
		super(originalAxiom);
		this.leftOWLclassExpression=originalAxiom.getLeftOWLclassExpression();
		this.rightOWLclassExpression=originalAxiom.getRightOWLclassExpression();
		
	}	
	
	


/**
 * 
 */
ParsedAxiom()
{
    super();
    // TODO Auto-generated constructor stub
}




public ArrayList<SubAxiom> getlSideOfAx()
{
	return lSideOfAx;
}



public ArrayList<SubAxiom> getrSideOfAx()
{
	return rSideOfAx;
}



public IRI getRelationIRI()
{
	return relationIRI;
}



public void setlSideOfAx(ArrayList<SubAxiom> lSideOfAx)
{
	this.lSideOfAx = lSideOfAx;
}



public void setrSideOfAx(ArrayList<SubAxiom> rSideOfAx)
{
	this.rSideOfAx = rSideOfAx;
}



public void setRelationIRI(IRI relationTypeIRI)
{
	this.relationIRI = relationTypeIRI;
}

/**
 * Добавляет сложную аксиому в ОПП.
 * Пример: A = B and hasPart-some C
 * @param ax
 */
public void addParsedAxiom (UserPresenOnt upo)
{
    /* Добавляем простую субаксиому из левой части аксиомы */
    OWLNamedIndividual leftInd = this.getlSideOfAx().get(0).addSubaxiom(upo);
    //-OWLNamedIndividual leftInd = this.addSubAxiom( (SimpleSubAxiom) ax.getlSideOfAx().get(0));

    /* Добавляем сложные субаксиомы из правой части аксиомы, 
     * как привязанные к левой части */
    
    

    for (SubAxiom sbAx : this.getrSideOfAx())
    {
	//LOGGER.info("==>"+this.getrSideOfAx().size()+" -- "+ sbAx);--
	LOGGER.info("====Add SUBAXIOM: {}",sbAx);
	LOGGER.info(" SbAx title: "+sbAx.getTitle());
	LOGGER.info(" SbAx queryPart: "+sbAx.getSparqlQueryPart());
	LOGGER.info(" SbAx sparqlVar:"+sbAx.getSparqlVar());
	LOGGER.info(" SbAx class:"+sbAx.getClass().getName());

	//OWLNamedIndividual rightInd = this.addSubAxiom(sbAx);
//--	OWLNamedIndividual rightInd = this.addRelatedSubAxiom(leftInd, sbAx, IRI.create(ConstantsOntAPI.SKOS_RELATED));
	OWLNamedIndividual rightInd = sbAx.addRelatedSubAxiom(leftInd, IRI.create(ConstantsOntAPI.SKOS_RELATED), upo);
	//this.makeObjPrpOBetweenElements(leftInd.getIRI(), ax.getRelationIRI() , rightInd.getIRI(), false);

	LOGGER.info("====END-Add SUBAXIOM==========");
    }
}



/**
 * Возвращает копию аксиомы. Метод используется для создания копии аксиомы
 * для понятия-наследника, т.е. наследнику приписывается копия аксиомы.
 * @param ax
 * @return
 * Override
 */
/*
ParsedAxiom getCloneAxiom()
{
	ParsedAxiom dubAxiom = new ParsedAxiom(OWLont.getOWLont(), UPOont.getUPOont(), this.lSideOfAx, this.rSideOfAx, this.leftOWLclassExpression, 
			this.rightOWLclassExpression, this.isCGI, this.axType, this.visType, this.relationTypeIRI);
	return dubAxiom;	
}
*/

/**
 * Добавляет субаксиомы в список субаксиом 
 * левой или правой части аксиомы.
 * @deprecated 
 * @param sb
 * @param listAx
 */
public void addSubaxToList(SubAxiom sb, ArrayList<SubAxiom> listAx)
{
	listAx.add(sb);
}





/* (non-Javadoc)
 * @see ru.iimm.ontology.OWL2UPOConverter.AbstractAxiom#clone()
 */
@Override
public ParsedAxiom clone() throws CloneNotSupportedException
{

	ParsedAxiom cloneAx = (ParsedAxiom) super.clone();
	
	cloneAx.lSideOfAx = (ArrayList<SubAxiom>) this.lSideOfAx.clone();
	cloneAx.rSideOfAx = (ArrayList<SubAxiom>) this.rSideOfAx.clone();
	
	return cloneAx;
}

///////////////////////////////////////////////////////////////////////
}
