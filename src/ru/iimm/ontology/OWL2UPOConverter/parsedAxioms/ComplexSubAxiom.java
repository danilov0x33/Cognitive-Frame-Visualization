package ru.iimm.ontology.OWL2UPOConverter.parsedAxioms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
import org.semanticweb.owlapi.model.OWLRestriction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;
import ru.iimm.ontology.OWL2UPOConverter.OWLont;
import ru.iimm.ontology.OWL2UPOConverter.PairOfIRI;
import ru.iimm.ontology.OWL2UPOConverter.UPOont;
import ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt;

/**
 * Сложная субаксиомы - коньюнкция выражений или выражение, не 
 * являющееся имемнованным классам.
 * @author Lomov P. A.
 *
 */
public class ComplexSubAxiom extends SubAxiom
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ComplexSubAxiom.class);	
	
	
	/**
	 * @param title
	 * @param sparqlVar
	 * @param sparqlQueryPart
	 * @param clsList
	 * @param subAxOWL
	 */
	public ComplexSubAxiom(String title, String sparqlVar,
		String sparqlQueryPart, ArrayList<OWLClass> clsList,
		OWLClassExpression subAxOWL)
	{
	    super(title, sparqlVar, sparqlQueryPart, clsList, subAxOWL);
	    // TODO Auto-generated constructor stub
	}

	
	
	/**
	 * 
	 */
	ComplexSubAxiom()
	{
	    super();
	    // TODO Auto-generated constructor stub
	}



	/**
	 * Список объектных свойств, присущих именованных классам субаксиомы,
	 * в виде пар <IRI свойства -- IRI значения>
	 */
	private ArrayList<PairOfIRI> objPrpList;

	/**
	 * Список типизированных свойств, присущих именованных классам субаксиомы,
	 * в виде пар <IRI свойства -- IRI типа свойства>
	 */
	private ArrayList<PairOfIRI> dtpPrpList;
	
	
	/**
	 * Карта пар: свойство --> именованный класс (входящий в субаксиому
	 * и потому привязываемый к ней). 
	 */
	private ArrayList<ArrayList<IRI>> bindedClassList;
	
	
	/**
	 * Список объектных свойств, приведенных в самой аксиоме, 
	 * в виде пар <IRI-свойства -- субаксиома> 
	 */
	private ArrayList<PrpIRIandSubAxiom> prpFromAxList;
	

///////////////////////////////////////////////////////////
////////////Constructors///////////////////////////////////
///////////////////////////////////////////////////////////
	






	
	
	
	
/////////////////////////////////////////////////////////////	
////////////////METHODS//////////////////////////////////////
/////////////////////////////////////////////////////////////

	public ArrayList<ArrayList<IRI>> getBindedClassList()
	{
		return bindedClassList;
	}

	public void setBindedClassList(ArrayList<ArrayList<IRI>> bindedClassList)
	{
		this.bindedClassList = bindedClassList;
	}

	public void setObjPrpList(ArrayList<PairOfIRI> objPrpList)
	{
		this.objPrpList = objPrpList;
	}

	public void setDtpPrpList(ArrayList<PairOfIRI> dtpPrpList)
	{
		this.dtpPrpList = dtpPrpList;
	}

	public void setPrpFromAxList(ArrayList<PrpIRIandSubAxiom> prpFromAxList)
	{
		this.prpFromAxList = prpFromAxList;
	}

	/**
	 * Выбирает из субаксиомы именованные классы.	
	 * @param subAx
	 * @return
	 * @deprecated перенес в datafactory 
	 */
	public ArrayList<OWLClass> getNamedClassFromExp(OWLClassExpression subAx)
	{
		/*
		 *  Из субаксиомы, составленной из классов, связанных коньюнкцией, 
		 *  или просто одного класса выделяем именованные классы 
		 *  и заносим их в список.
		 */
		ArrayList<OWLClass> listOfClasses = new ArrayList<OWLClass>();
		
		Set<OWLClassExpression> axСNSet = subAx.asConjunctSet();
//		log.info("   ===getNamedClassFromExp==");
		for (OWLClassExpression clasExp : axСNSet)
		{		
			if (!clasExp.isAnonymous())
			{
				LOGGER.info("Get class from SB: "+clasExp.asOWLClass().getIRI().getFragment());
				listOfClasses.add(clasExp.asOWLClass());
			}
		}
		//log.info("   ===END-getNamedClassFromExp==");
		return listOfClasses;
	}
	
	

/**
 * Добавляет пару <свойство - значение> в 
 * список свойств классов, входящих в субаксиому.
 * @param prpIRI
 * @param valIRI
 */
public void addToPrpList(IRI prpIRI, IRI valIRI,  ArrayList<PairOfIRI> prpList)
{
	prpList.add(new PairOfIRI(prpIRI, valIRI));
}

public  Iterator<PairOfIRI> getIteratorOfDataPropertyList ()
{
	return dtpPrpList.iterator();
}

public  Iterator<PairOfIRI> getIteratorOfObjectPropertyList ()
{
	return objPrpList.iterator();
}


public Iterator<PrpIRIandSubAxiom> getIteratorOfprpFromAxList()
{
	return prpFromAxList.iterator();
}

/**
 * Добавляет пару <свойство - субаксиома> в 
 * список свойств в субаксиоме.
 * @param prpIRI
 * @param valIRI
 * @deprecated
 */
public void addToSubaxPropList(IRI prpIRI, SubAxiom valSubax)
{
	prpFromAxList.add(new PrpIRIandSubAxiom(prpIRI, valSubax));
}


/**
 * Возвращает classExp которое было в ограничении свойства.
 * @param prpExp ограничение на свойство, которое определяет неименованный класс
 * @return
 * @deprecated
 *  
 */
public static OWLClassExpression getObjectPrpopertyRestrictionRANGE(OWLClassExpression prpExp)
{
	/*
	 * Определяем тип ограничения на свойство, от этого зависит способ обработки.
	 * Приводим ограничение к узнанному типу, берем его внутреннюю часть и 
	 * приводим ее к classExp 
	 */
	LOGGER.info("   ====getObjectPrpopertyRestrictionRANGE ========================");
	LOGGER.info("   Restriction:"+ prpExp);	
	LOGGER.info("   Restrct type:" + prpExp.getClassExpressionType());
	OWLClassExpression rangeExp=null;
	
	//if ( ((OWLRestriction) prpExp).getClassExpressionType().equals(ClassExpressionType.DATA_HAS_VALUE) )
	if (prpExp.getClassExpressionType().equals(ClassExpressionType.OBJECT_HAS_VALUE) )
	{
		// TODO доделать обработку для ограничений типа has Value
		//((OWLHasValueRestriction) prpExp).getValue());
	}
	else
	{
		rangeExp = (OWLClassExpression) ((OWLQuantifiedRestriction) prpExp).getFiller();						
	}
	LOGGER.info("   Restrct RANGE:" + rangeExp);
	LOGGER.info("   ====END-getObjectPrpopertyRestrictionRANGE ========================");
	return rangeExp;
}

/**
 * Возвращает IRI типа данных, которое связано с этим свойством. Т.е. само ограничение игнорируется.
 * @param prpExp ограничение на свойство, которое определяет неименованный класс
 * @return
 * @deprecated 
 */
public OWLDatatype getDataPrpopertyRestrictionRANGE(OWLClassExpression prpExp)
{
	OWLDataProperty prp = (OWLDataProperty) ((OWLRestriction) prpExp).getProperty();

	// для каждого свойства находим его RANGE - берем оттуда типы
	OWLDatatype datatype = 
		OWLont.getOWLont().getDatatypeFromRange(prp.getIRI().toString());

	return datatype;
}



/**
 * Создаем фрагмент запроса из списка именованных классов аксиомы
 * и переданной sparql переменной, привоенной субаксиоме. 
 * 
 */
public String getSparqlQueryPart(ArrayList<OWLClass> clsList, String SxAxVar )
{
	LOGGER.info("   ===getSparqlQueryPart==");
	String fragmentOfQuery = "";
	for (OWLClass cls : clsList)
	{
		// Находи концепт в ОПП
		IRI conceptIRI = 
			UPOont.getUPOont().getConceptByIRIinAnnotationValue(IRI.create(ConstantsOntConverter.SKOS_HIDDEN_LABEL), 
						cls.getIRI());
		
		
		// Берем его переменную - нет переменная должна быть равна переменной субаксиомы
		//String var = ontUPO.getAnnotationValue(conceptIRI, IRI.create(UPO_SPARQL_VARIABLE_LABEL) );
		
		// Добавляем строку в запрос
		fragmentOfQuery = fragmentOfQuery + (SxAxVar +" "+ ConstantsOntConverter.IRI_RDF_TYPE_SHORT +" <"+conceptIRI+">" + ".\n");
		LOGGER.info("   ConceptIRI:"+conceptIRI);
		LOGGER.info("   Var of subAx:"+SxAxVar);
		LOGGER.info("   Fragment:"+fragmentOfQuery);
		
	}
	
	LOGGER.info("   ===END-getSparqlQueryPart==");
	return fragmentOfQuery;
}

/**
 * @TODO название сложной субаксиомы зависит от названия субаксиомы из другой части
 * + от отношения с ней, пока учитывается только 1-е, может 2-е и не
 * нужно
 */

public String getSparqlVar(SubAxiom subaxFromAnotherPart)
{
		return subaxFromAnotherPart.getSparqlVar();
}


public String getSparqlVar()
{
	return UPOont.getUPOont().genNewSparqlVar(this);
}

public ArrayList<PairOfIRI> getObjPrpList()
{
	return objPrpList;
}

public ArrayList<PairOfIRI> getDtpPrpList()
{
	return dtpPrpList;
}

public ArrayList<PrpIRIandSubAxiom> getPrpFromAxList()
{
	return prpFromAxList;
}






/* (non-Javadoc)
 * @see ru.iimm.ontology.OWL2UPOConverter.SubAxiom#getNewTitle()
 */
@Override
protected String generateTitle()
{
    LOGGER.info("=== Generate title for SubAxiom:\n"+this.getSubAxOWL());
    String namedTitle = "";
    
    for (OWLClass cls : this.getClsList())
    {
	namedTitle += cls.getIRI().getFragment()+ConstantsOntConverter.UPO_TITLE_DELIMITER;
    }
    
    /* Формируем карту: свойство --- массив субаксиом [] */
    HashMap<IRI, ArrayList<SubAxiom>> map = 
	    this.getPropSubaxMap(this.getPrpFromAxList()); 
    
    /* Из карты формируем подзаголовки - для сложных субаксиом */
    for (IRI propIRI : map.keySet())
    {
	
	for (SubAxiom subax : map.get(propIRI))
	{
	    namedTitle += subax.getTitle()+ConstantsOntConverter.UPO_TITLE_DELIMITER;
	}
	
    }
    /*Добавляем фрагмент имени от типизированных свойств */
    namedTitle = namedTitle + this.getDatatypePropNameFragment(this.getDtpPrpList());
    
    /* Отрезаем лишний делимитер (-) из конца аксиомы */
    namedTitle = namedTitle.length()>0 ? 
	namedTitle.substring(0, namedTitle.lastIndexOf(ConstantsOntConverter.UPO_TITLE_DELIMITER)) :   namedTitle;
	
    /* Обрамляем скобками субаксиому  */
//namedTitle = namedTitle.length()>0 ? "[" + namedTitle + "]" : null;
	namedTitle = namedTitle.length()>0 ? UPOont.getSBrackedString(namedTitle) : null;

	
    return namedTitle;
}


/**
 * Формирует карту:
 * свойство --- массив субаксиом []
 * для полседующего формирования имени субаксиомы.
 * @return
 */
private HashMap<IRI, ArrayList<SubAxiom>> getPropSubaxMap(ArrayList<PrpIRIandSubAxiom> prpAndSubaxList)
{
    HashMap<IRI, ArrayList<SubAxiom>> map = 
	    new HashMap<IRI, ArrayList<SubAxiom>>();
    ArrayList<SubAxiom> sbaxList;
    
    for (PrpIRIandSubAxiom pair : prpAndSubaxList)
    {
	if (map.containsKey(pair.propIRI) && map.get(pair.getPropIRI()) != null)
	{
	    sbaxList = map.get(pair.getPropIRI());
	    sbaxList.add(pair.getSubax());	    
	}
	else
	{
	    sbaxList = new ArrayList<SubAxiom>();
	    sbaxList.add(pair.subax);
	    map.put(pair.getPropIRI(), sbaxList);
	}
	
    }
    return map;
}

/**
 * Добавляет субаксиому в ОПП и связывает ее концепт с концентапи из
 * нее (другими субаксиомами, содержащимися в ней классами).
 * Метод добавляет субаксиому, как ComplexSubAxiom и должен использоваться
 * если нет более специфических способов добавления субаксиомы.
 * @param sbAx
 * @return
 */
@Override
public OWLNamedIndividual addSubaxiom(UserPresenOnt upo)
{
    //ComplexSubAxiom complexRightSubAx = (ComplexSubAxiom) subAx;
    OWLNamedIndividual sbAxIndividual = this.addConceptOfComplexSubAxiomToUPO(upo);
    this.bindingConceptOfComplexSubAxiomWithOther(sbAxIndividual, upo);
	    
    return sbAxIndividual;
}


/**
 * Добавление субаксиомы + привязка ее к передаваемому экземпляру,
 * переданным отношением.
 * @param ind
 * @param relationIRI
 * @return
 */
@Override
public OWLNamedIndividual addRelatedSubAxiom(OWLNamedIndividual mainSubaxInd, IRI relationIRI, UserPresenOnt upo)
{
    OWLNamedIndividual sbaxInd = this.addSubaxiom(upo);
	   
    /* Соединяем левую и правую субаксиому отношением 
     * (по умолчанию SKOS:Related) */
    upo.makeObjPrpOBetweenElements(mainSubaxInd.getIRI(), relationIRI, sbaxInd.getIRI() , false);
    return sbaxInd;
}


/**
 * Добавляет концепт, соответствующий субаксиоме, в ОПП.
 * @param sbAx
 * @return
 */
private OWLNamedIndividual addConceptOfComplexSubAxiomToUPO(UserPresenOnt upo)
{			                          
	
	/* Создаем концепт-субаксиому в ОПП */
	OWLNamedIndividual newConcept = upo.addConcept(upo.getNewComplexSbAxiomIRI(this).toString());
	
	/* Создаем соответвующую концепту переменную */
	String varSparql = this.getSparqlVar();

	/* Создаем соответвующий видимый Label - короткий IRI */
	String label = this.getTitle();
	
	/* Создаем соответвующий фрагмент запроса (типа ?var RDF:type "http:\\ontology#car) */
	String fragmentOfQuery = this.getSparqlQueryPart();
	
	// В вписываем инф-ю в SKOS-концепт
	OWLLiteral labelLiteral = upo.df.getOWLLiteral(label, "en");
	OWLLiteral fragmentOfQuerylLiteral = upo.df.getOWLLiteral(fragmentOfQuery, "en");
	//OWLTypedLiteral hiddenLabelLiteral = upo.df.getOWLTypedLiteral(IRIasString, OWL2Datatype.XSD_ANY_URI);
	OWLLiteral varlLiteral = upo.df.getOWLLiteral(varSparql, "en");
	
	upo.addAnnotation(ConstantsOntConverter.RDF_LABEL, labelLiteral, (OWLEntity) newConcept);
//	upo.addAnnotation(SKOS_Hidden_label, hiddenLabelLiteral, (OWLEntity) newConcept);
	upo.addAnnotation(ConstantsOntConverter.UPO_SPARQL_VARIABLE_LABEL, varlLiteral, (OWLEntity) newConcept);
	upo.addAnnotation(ConstantsOntConverter.UPO_SPARQL_QUERY_FRAGMENT_LABEL, fragmentOfQuerylLiteral, (OWLEntity) newConcept);
	

	return newConcept;
}

/**
 * Связывает концепт-субаксиому с другими концептами в ОПП.
 * @param sbAx суаксиома
 * @param sbAxInd экземпляр субаксиомы
 */
private void bindingConceptOfComplexSubAxiomWithOther(OWLNamedIndividual sbAxInd, UserPresenOnt upo)
{
	/*
	 * связать концепт субаксиомы с объектными и 
	 * типизированными свойствами классов субаксиомы
	 * разобраться с объектными свойсвами внутри аксиомы
	 * ! Решил убрать привязку объектных свойств классов в субаксиоме к ней
	 * т.к. ранее убрал привязку к классам (простым субаксиомам) по
	 * домену/радиусу свойства
	 */
	
	LOGGER.info("   ===bindingConceptOfComplexSubAxiomWithOther================");
	LOGGER.info("   Subaxiom:<" + this.getTitle()+">");
	/* Убираем привязку объектных свойств
	LOGGER.info("   =Binding sbAx with obj properties:");
	for (Iterator<PairOfIRI> it = sbAx.getIteratorOfObjectPropertyList(); it.hasNext();)
	{
		PairOfIRI pair = (PairOfIRI) it.next();
		LOGGER.info("   ObjPrp:"+ pair.fIRI + " Val:" + pair.sIRI);
		this.makeObjPrpOBetweenElements(sbAxInd.getIRI(), pair.fIRI, pair.sIRI, false);
	}
	*/
	LOGGER.info("   =Binding sbAx with dtp properties:");
	for (Iterator<PairOfIRI> it = this.getIteratorOfDataPropertyList(); it.hasNext();)
	{
		PairOfIRI pair = (PairOfIRI) it.next();
		LOGGER.info("   DtpPrp:"+ pair.getFirst() + " Val:" + pair.getFirst());
		/*
		 * Добавляем новый концеп-радиус т.к. старый (если существует)
		 * связан с другим СКОС концептом, а договорились что у радиусов
		 * будем генерить новый
		 */
		OWLNamedIndividual newRange =
				upo.addDatatypeRangeToUPO(pair.getFirst(), pair.getFirst());
		upo.makeObjPrpOBetweenElements(sbAxInd.getIRI(), 
				pair.getFirst(), newRange.getIRI(), false);
	}

	/* * Привязывем к субаксиоме ее именованные классы */
	for (ArrayList<IRI> prpAndCls: this.getBindedClassList())
	{
		//Карта пар: свойство --> именованный класс (входящий в субаксиому
		//		 * и потому привязываемый к ней).
		IRI clsIndIRI =	upo.getConceptOfSubAxiomFromUPObyIRIinHiddenLabel(prpAndCls.get(1));
		IRI prpIRI = prpAndCls.get(0);
		upo.makeObjPrpOBetweenElements(sbAxInd.getIRI(), prpIRI, clsIndIRI, false);
	}
	
	/* Привязываем субаксиомы*/
	LOGGER.info("   =Binding sbAx with other Subaxiom:");
	for (Iterator<PrpIRIandSubAxiom> it = this.getIteratorOfprpFromAxList(); it.hasNext();)
	{
		PrpIRIandSubAxiom pair = (PrpIRIandSubAxiom) it.next();
		LOGGER.info("   PRP:"+ pair.propIRI + " SbAx:" + pair.subax.getTitle());
		OWLNamedIndividual newSbAx = pair.subax.addRelatedSubAxiom(sbAxInd, pair.propIRI, upo);
/*-
		OWLNamedIndividual newSbAx = this.addSubAxiom(pair.subax);
		this.makeObjPrpOBetweenElements(sbAxInd.getIRI(), pair.propIRI, newSbAx.getIRI(), false);
*/			
		LOGGER.info("	Subaxiom:"+newSbAx.toString());   
	}
	LOGGER.info("   ===END-bindingConceptOfComplexSubAxiomWithOther================");
}


	/**
	 * Возвращает фрагмент названия субаксиомы из ее типизированных свойств 
	 * и из диапазонов.
	 * @param propDomainPairList
	 * @return
	 */
	private String getDatatypePropNameFragment(ArrayList<PairOfIRI> propDomainPairList)
	{
	    String fragment="";
	    for (PairOfIRI pairOfIRI : propDomainPairList)
	    {
		//fragment = fragment + pairOfIRI.fIRI.getFragment() +"{"+pairOfIRI.sIRI.getFragment()+"}"   + ConstantsOntConverter.UPO_TITLE_DELIMITER;
		fragment = fragment + pairOfIRI.getFirst().getFragment() + UPOont.getFBrackedString(pairOfIRI.getFirst().getFragment()) 
		+ ConstantsOntConverter.UPO_TITLE_DELIMITER;
	    }
	    return fragment;
	}


}
