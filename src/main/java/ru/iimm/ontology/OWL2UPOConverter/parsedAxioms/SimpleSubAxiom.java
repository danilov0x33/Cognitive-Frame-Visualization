package ru.iimm.ontology.OWL2UPOConverter.parsedAxioms;
import java.util.ArrayList;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;
import ru.iimm.ontology.OWL2UPOConverter.UPOont;
import ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt;
import ru.iimm.ontology.ontAPI.Ontology;

/**
 * @author lomov
 *
 */
public class SimpleSubAxiom extends SubAxiom implements ConstantsOntConverter
{
	    
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SimpleSubAxiom.class);
    
    /**
     * Экземпляр из ОПП, соответствующий данной простой субаксиоме. 
     */
    private OWLNamedIndividual subAxIndividual;
    
    /**
     * @return the subAxIndividual
     */
    public OWLNamedIndividual getSubAxIndividual()
    {
	if (this.subAxIndividual==null)
	{
	    IRI individualIRI = UPOont.getUPOont().getConceptByIRIinAnnotationValue
	    (IRI.create(ConstantsOntConverter.SKOS_HIDDEN_LABEL), this.getClsList().get(0).getIRI());
	    
	    this.subAxIndividual = UPOont.getUPOont().df.getOWLNamedIndividual(individualIRI);
	} 
	
	return subAxIndividual;
    }

    /**
     * @param subAxIndividual the subAxIndividual to set
     */
    public void setSubAxIndividual(OWLNamedIndividual subAxIndividual)
    {
        this.subAxIndividual = subAxIndividual;
    }

    /**
     * 
     */
    SimpleSubAxiom()
    {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @param title
     * @param sparqlVar
     * @param sparqlQueryPart
     * @param clsList
     * @param subAxOWL
     */
    SimpleSubAxiom(String title, String sparqlVar, String sparqlQueryPart,
	    ArrayList<OWLClass> clsList, OWLClassExpression subAxOWL)
    {
	super(title, sparqlVar, sparqlQueryPart, clsList, subAxOWL);
	// TODO Auto-generated constructor stub
    }
    


	@Override
	public OWLNamedIndividual addSubaxiom(UserPresenOnt upo)
	{
	    /* Если субаксиома простая - то она уже существует в ОПП - (уже добавлена 
		на предыдущих фазах) и надо вернуть соответвующий ей концепт */
	    /* Получаем IRI класса по-которому будет искать в ОПП экземпляр,
	     * где этот IRI указан в hiddden_label*/
	    IRI classIRI = this.getClsList().get(0).getIRI();
		    
	    /* находиv нужный экземпляр (его IRI)*/
	    IRI upoIndividualIRI = upo.getConceptByIRIinAnnotationValue(
		    IRI.create(ConstantsOntConverter.SKOS_HIDDEN_LABEL), classIRI);

	    return upo.df.getOWLNamedIndividual(upoIndividualIRI);
	}

	@Override
	public OWLNamedIndividual addRelatedSubAxiom(
		OWLNamedIndividual mainSubaxInd, IRI relationIRI, UserPresenOnt upo)
	{
	    OWLNamedIndividual sbaxInd = this.addSubaxiom(upo);
		   
	    /* Соединяем левую и правую субаксиому отношением 
	     * (по умолчанию SKOS:Related) */
	    upo.makeObjPrpOBetweenElements(mainSubaxInd.getIRI(), relationIRI, sbaxInd.getIRI() , false);
	    return sbaxInd;	    
	}

	/**
	 * Создает имя простой субаксиомы из выражения-именованного класса.
	 * Имя вида: [имя_класса]
	 */	
	@Override
	protected String generateTitle()
	{
	    if (!this.isAnonymous())
	    {
		return "["+this.asOWLClass().getIRI().getFragment()+"]";
	    } else
	    {
		LOGGER.error(" Shold be simple subaxio:{}", this);
		return null;
	    }
	    

	}

	
	
	

	


}
