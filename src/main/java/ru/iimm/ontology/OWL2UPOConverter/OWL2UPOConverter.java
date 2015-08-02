package ru.iimm.ontology.OWL2UPOConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.ontAPI.ConstantsOntAPI;
import ru.iimm.ontology.ontAPI.Ontology;




/**
 * Конвертер OWL онтологии в онтологию пользовательского представления (ОПП)
 * @author Lomov P. A.
 *
 */
public class OWL2UPOConverter implements ConstantsOntAPI, ConstantsOntConverter
{
	
	/**
	 * Исходная онтология.
	 */
	Ontology ontOWL;
	
	/**
	 * Онтология пользовательского представления.
	 */
	UserPresenOnt ontUPO;
	
	
	/**
	 * Набор аксиом из онтологии для обработки.
	 */
	Set<OWLLogicalAxiom> AxiomSet;
	
	/**
	 * Набор аксиом из онтологии для обработки.
	 */
	Set<OWLLogicalAxiom> SkippedAxiomSet;

	
	
	private static final Logger LOGGER = LoggerFactory
		.getLogger(OWL2UPOConverter.class);
	
	//int findingComplexSubaxiomCounter=0;
	
	OWL2UPOConverter(Ontology ont, UserPresenOnt ontUPO)
	{
		this.ontOWL = ont;
		this.ontUPO = ontUPO;

		this.AxiomSet=ont.ontInMem.getLogicalAxioms();
		this.SkippedAxiomSet=new HashSet<OWLLogicalAxiom>();
	
		//== Переносим классы в ОПП
		moveNamedClassToUPO(); 

		//== Переносим объектные свойства в ОПП
		moveObjectPropertiesToUPO();

		//== Добавляем привязку объектных свойств
	//	moveObjectPropertyBinding();
		
		//== Переносим типизированные свойства в ОПП
		moveDatatypePropertiesToUPO();

		//== Добавляем привязку типизированных свойств -- 
	//	moveDatatypePropertyBinding();

		//== Добавляем иерархию на концептах в UPO
		//Берем класс THING		
		OWLClass root = ont.df.getOWLThing();
		formHierarchy(root);

		//== Перенос сложных аксиом ======
		moveCompoundAxiom();


	}
	/**
	 * Рекурсивно формирует иерархию в ОПП - вызывает сам себя пока не закончатся подклассы
	 * @param root
	 */
	private String formHierarchy(OWLClass root)
	{
		Set<OWLClass> subclassSet = formSubclassRelation(root);		
		// каждому SKOS-концепту - подклассу приделываем свойства суперкласса
		//, Set<OWLObjectProperty> prpSet
		
		// если в списке подклассов NOTHING то выходим - больше подклассов нет
		// удалил if ( ((OWLClass) subclassSet.iterator().next()).isOWLNothing()) return "NO_SUBCLASSES";
		// добавил
		if ( subclassSet.iterator().hasNext())
		{
			if ( ((OWLClass) subclassSet.iterator().next()).isOWLNothing()) return "NO_SUBCLASSES";
		}
		else
			return "NO_SUBCLASSES";
		// Пробегаем по полученным подклассам - для каждого запускаем построение
		// иерархии с его подклассами
		for (OWLClass cls : subclassSet)
			{
			// 
				formHierarchy(cls);
			}
		return "END_OF_SUBCLASSES_LIST";	
	}
	
	/**
	 * Формируем иерархию между контептом-корнем и его подконцептами
	 * в соответствии с иерархией в онтологии
	 * 
	 * @param root
	 * @return набор подклассов класса-корня, переданного в виде аргумента
	 * @todo добавить проверку существования концепта-корняы
	 */
	private Set<OWLClass> formSubclassRelation(OWLClass root)
	{
		//1 Берем подклассы
		Set<OWLClass> subclSet =
				ontOWL.getNamedSubclassesOfNamedClass(root,  true);

		/*2  находим корень в ОПП
		 *  но зачем искать если нам известно IRI - такое как у соответствующего класса 
		 *  это не всегда - например для Thing создается другой реальный IRI*/
		OWLLiteral rootIriLit = ontUPO.convertIRIToOWLLiteral(root.getIRI());
		IRI rootIRI = ontUPO.getEntityByAnnotationValue(IRI.create(SKOS_HIDDEN_LABEL), rootIriLit);
		OWLNamedIndividual rootConcept =
				this.ontUPO.df.getOWLNamedIndividual(rootIRI);
		//2 Вариант без поиска в ОПП
/*-		OWLNamedIndividual rootConcept = 
				ontUPO.df.getOWLNamedIndividual(root.getIRI());
-*/

		//3 получаем свойство broader - аналог subclassOf
		OWLObjectProperty broader = 
				ontUPO.df.getOWLObjectProperty(IRI.create(SKOS_BROADER)); 
		
		//4 связываем подклассы с корнем в ОПП	
		for (OWLClass owlClass : subclSet)
		{
			// если в списке подклассов NOTHING то выходим - больше подклассов нет
			if (owlClass.isOWLNothing()) break;
			
			// находим подклассы в ОПП
			OWLNamedIndividual subConcept = 
					ontUPO.df.getOWLNamedIndividual(owlClass.getIRI());
			
			// связываем
//			LOGGER.info("=== Link concept :=====================");
//			LOGGER.info(subConcept + " BROADER " +rootConcept);
			ontUPO.setObjectPrpBetween(subConcept, broader, rootConcept);
//			LOGGER.info("======================================");
			//System.exit(0);
		}
		
		return subclSet;
	}

//=============================================================================================
//====================PRIVATE METHODS==========================================================
//=============================================================================================	
	
	
	/**
	 * Используется в OWLConverter(). Переносит именованные классы в ОПП.
	 * @param ontOWL исходная онтология
	 * @param ontUPO ОПП
	 * @return число перенесенных классов
	 * @todo не удаляет аксиомы классов
	 */
	private int moveNamedClassToUPO()
	{
		int numberOfClasse=0;
		
		// Получаем список аксиом с именованными классами
		HashSet<OWLClass> clsList =  ontOWL.getNamedClassList();

		// Для каждого класса создаем SKOS-концепт
		for (OWLClass owlClass : clsList)
		{
			LOGGER.info(">>==="+Ontology.getName(owlClass));
			ontUPO.addConceptToUPO(owlClass.getIRI().toString());
			numberOfClasse++;
		}
		return numberOfClasse;
	}
	
	/**
	 * Используется в OWLConverter(). Переносит объектные свойства в ОПП. 
	 * @param ontOWL исходная онтология
	 * @param ontUPO ОПП
	 * @return кол-во перенесенных свойств.
	 */
	private int moveObjectPropertiesToUPO()
	{
		int numberOfObjProp=0;
		LOGGER.info("==moveObjectPropertiesToUPO ========================================");
		// Добавляем объектные свойства в ОПП
		HashSet<OWLObjectProperty> prpObjList =  ontOWL.getObjPropertyList();
		LOGGER.info("  Added object properties:");		
		for (OWLObjectProperty owlObjectProperty : prpObjList)
		{	
			LOGGER.info("   "+ owlObjectProperty);
			ontUPO.addObjectPropertyToUPO(owlObjectProperty.getIRI().toString());
			numberOfObjProp++;
		}

		/*Переносим аксиомы, задающие иерархию на свойствах*/
		ontUPO.mng.addAxioms(ontUPO.ontInMem, ontOWL.ontInMem.getAxioms(AxiomType.SUB_OBJECT_PROPERTY));
		
		LOGGER.info("  TOTAL QUALITY: "+numberOfObjProp);
		LOGGER.info("==END-moveDatatypePropertiesToUPO ========================================");
		return numberOfObjProp;
	}

	/**
	 * Используется в OWLConverter(). Переносит типизированные свойства в ОПП. 
	 * @param ontOWL исходная онтология
	 * @param ontUPO ОПП
	 * @return кол-во перенесенных свойств.
	 */
	private int moveDatatypePropertiesToUPO()
	{
		int numberOfObjProp=0;
		LOGGER.info("==moveDatatypePropertiesToUPO ========================================");
		// Добавляем типизированные свойства в ОПП
		HashSet<OWLDataProperty> dtpPrpSet =  ontOWL.getDtpPropertyList();
		
		/* для каждого свойства:
		 * 1. добавляем его в ОПП  */
		LOGGER.info("  Added datatype properties:");
		for (OWLDataProperty prp : dtpPrpSet)
		{
			OWLObjectProperty prpAdd = ontUPO.addDataTypePropertyToUPO(prp.getIRI().toString());
			LOGGER.info("   "+ Ontology.getShortIRI(prpAdd));			
			numberOfObjProp++;
		}
		
		/*2. Переносим аксиомы, задающие иерархию на свойствах*/
		ontUPO.mng.addAxioms(ontUPO.ontInMem, ontOWL.ontInMem.getAxioms(AxiomType.SUB_DATA_PROPERTY));
		
		LOGGER.info("  Total quality: "+numberOfObjProp);
		LOGGER.info("==END-moveDatatypePropertiesToUPO ========================================");
		
		return numberOfObjProp;
	}
	
	/**
	 * Используется в OWLConverter(). Переносит привязку типизированных свойств. 
	 * Привязка свойства наследуется подклассами классов из домена свойства .
	 * т.е. если A subClass B и  А имеетИмя С то B имеетИмя С. 
	 * @param ontOWL исходная онтология
	 * @param ontUPO ОПП
	 * 
	 */
	private void moveDatatypePropertyBinding()
	{
	    LOGGER.info("==moveDatatypePropertyBinding=================================");
	    // <Берем список классов исходной онтологии
	    HashSet<OWLClass> clsList =  ontOWL.getNamedClassList();
	    for (OWLClass owlClass : clsList)
		{
			// для каждого класса получаем список его свойств с учетом свойств суперклассов
			Set<OWLDataProperty> prpSet = ontOWL.getDatatypesProperiesOfClass(owlClass.getIRI().toString(),true);
			
			// найденые свойства класса - находим в ОПП и связываем с его SKOS-концептом класса
			for (OWLDataProperty prp : prpSet)
			{
				// для каждого свойства находим его RANGE - берем оттуда типы
				Set<OWLDatatype> datatypeSet = 
						ontOWL.getDatatypesFromRange(prp.getIRI().toString());
				
				for (OWLDatatype datatype : datatypeSet)			
				{	// добавляем типы в ОПП как SKOS концепты			
				OWLNamedIndividual rangeInd = 
						this.ontUPO.addDatatypeRangeToUPO(datatype.getIRI(),prp.getIRI());
				// связываем SKOS концепт класса свойством со SKOS концептом - его типом
						this.ontUPO.makeObjPrpOBetweenElements(owlClass.getIRI().toString() , 
							prp.getIRI().toString(), rangeInd.getIRI().toString(), true);
						
					LOGGER.info("  Link datatype prop between: ===================" +
							"\n  Dom:" + Ontology.getShortIRI(owlClass) +"\n  Prp:" + Ontology.getShortIRI(prp) + "\n  Rng:" +
							Ontology.getShortIRI(datatype) +	"\n  ================================================");
												
				}
			}
		}
		//////////////
		LOGGER.info("==END-moveDatatypePropertyBinding=================================");
	}
	
	/**
	 * Используется в OWLConverter(). Переносит привязку свойств. Привязка свойства наследуется по домену свойства .
	 * т.е. если A subClass B и  А имеетИмя С то B имеетИмя С. 
	 * @param ontOWL исходная онтология
	 * @param ontUPO ОПП
	 * TODO !!! Если объединение или пересечение
	 */
	private void moveObjectPropertyBinding()
	{
		LOGGER.info("==moveObjectPropertyBinding=================================");		
		// Получаем список аксиом с именованными классами
		HashSet<OWLClass> clsList =  ontOWL.getNamedClassList();

		// <Берем список классов исходной онтологии
		for (OWLClass owlClass : clsList)
		{
			// для каждого класса получаем список его свойств с учетом свойств суперклассов
			Set<OWLObjectProperty> prpSet = ontOWL.getObjectProperiesOfClass(owlClass.getIRI().toString(),true);
			
			// найденые свойства класса - находим в ОПП и связываем с его SKOS-концептом
			for (OWLObjectProperty prp : prpSet)
			{
				// для каждого свойства находим его RANGE - берем оттуда классы
				Set<OWLClass> clsRANGEset = 
						ontOWL.getClassFromRange(prp.getIRI().toString());
				
				for (OWLClass clsRange : clsRANGEset)
				{
					
					LOGGER.info("  Link obj prop between: ===================" +
							"\n  Dom:" + owlClass +"\n  Prp:" + prp.getIRI().toString() + "\n  Rng:" + clsRange +
							"\n  ================================================");
							
					ontUPO.makeObjPrpOBetweenElements
						(owlClass.getIRI().toString(), prp.getIRI().toString(), clsRange.getIRI().toString(), false);
				}
			}
		}
		LOGGER.info("==END-moveObjectPropertyBinding=================================");
	}

	private void moveCompoundAxiom()
	{
		/* Просматриваем все аксиомы
		 * отправляет каждую на разбор - получаем разобранную аксиому - формируем их массив
		 * Просматриваем массив разобранных аксиом 
		 * отправляем каждую на вставку в ОПП */

	    	ArrayList<SplitAxiom> splitAxList = new ArrayList<SplitAxiom>();
		
		LOGGER.info("=======================================");
		for (OWLLogicalAxiom ax :	this.AxiomSet)
		{
		    /** Получаем разделенную аксиому **/
		    	SplitAxiom spAx = DataFactory.getFactory().getSplitAxiom(ax, ontOWL, ontUPO);		    
		    	
		    /** Проверяем стоит ли ее обрабатывать 
		     * парсим ее и добавляем в список **/
		    if (spAx!=null && 
		    	!spAx.isCgiAxiom() &&
		    	!spAx.isSimpleAxiom())
			{
		    	    splitAxList.add(spAx);
		    	    //axiomList(spAx);
			} else
			{
			    //SkippedAxiomSet.add(ax);
			}

			
		}

		/** Новый Расширяем список аксиом - добавляем аксиомы с наследниками*/
	    	splitAxList = this.getInheritableAxiomsList(splitAxList);
	    	
	    	/* Заводим список добавленных аксиом - addedAxList
	    	 * Вызываем методы для детектирования паттернов...
	    	 * В эти методы отправляем список аксиом + список добавленных аксиом (он в этих метода пополняется) 
	    	 * Внутри методов - собираются аксиомы, соответвующие паттернам и добавляются в ОПП*/
	    	
	    	/* ----- Тут идут методы для поиска и добавления паттернов -----------*/

	    	/* Пробегаем еще раз список аксиом + каждую аксиому парсим и добавляем в ОПП*/
	    	for (SplitAxiom splitAxiom : splitAxList)
		{
	    	    LOGGER.info("==== START - Parse axion: "+ 
	    		    splitAxiom.getLeftOWLclassExpression().toString() + " -- "  + 
	    		    splitAxiom.getRightOWLclassExpression());	    	    
	    	    
	    	    ParsedAxiom parsedAx = DataFactory.getFactory().getParsedAxiom(splitAxiom);
	    	    LOGGER.info("==== END - Parse axion =========================== "); 
	    	    if (parsedAx == null) 
	    	    {
	    		SkippedAxiomSet.add(splitAxiom);
	    		continue;
	    	    }
	    	    
		    LOGGER.info("==== START - Add AXIOM - vis type: "+ parsedAx.getClass().getName());
	    	    parsedAx.addParsedAxiom(this.ontUPO);
	    	    LOGGER.info("==== END - Add AXIOM ===========================");
		}
	    	
		LOGGER.info("==============================================");
		LOGGER.info("======= List of added axion - COMPLETE");
		LOGGER.info("======= Quantity of ax: "+(splitAxList.size()+1));
		LOGGER.info("======= Quantity of SKIPPED ax: "+(SkippedAxiomSet.size()+1));
		LOGGER.info("==============================================");					
	}
	
	

	/**
	 * Возвращает расширенный список аксиомы, куда входят аксиомы наследуемые подклассами
	 * классов из исходного списка
	 * @param axList исходный список.
	 * @return
	 */
	private ArrayList<SplitAxiom> getInheritableAxiomsList(ArrayList<SplitAxiom> axList)
	{
		LOGGER.info("== Add Inheritance of Axioms =======================");
		/*Временный список аксиом, который будет расширяться на каждом шаге наследуемыми аксиомами*/
		ArrayList<SplitAxiom> extendedAxiomList=new ArrayList<SplitAxiom>();
		extendedAxiomList.addAll(axList);		
		OWLClass axMleftClass;
		/* Из каждой аксиомы (axM) данного списка...*/
		for (SplitAxiom axM : axList)
		{
		    /*.. берем класс из левой части аксиомы ахМ - класс М*/		  
		    if (axM.leftOWLclassExpression.isAnonymous()) continue;		
		    else axMleftClass = axM.leftOWLclassExpression.asOWLClass();
			
		    LOGGER.info(" --- Checking inheritance from: "+axMleftClass.getIRI().getFragment());
		    LOGGER.info(" Inheritable ax: "+axM.rightOWLclassExpression);
		    
		    /*Для М находим все именованные подклассы - возможные наследники аксиомы AxM*/
		    Set<OWLClass> successorSet = ontOWL.getNamedSubclassesOfNamedClass(axMleftClass, false);
		    
		    Boolean isInherited = true; /* указывает будет ли наследоваться аксиома AxM*/
			
		    /* Для каждого возможного наследника... */
		    for (OWLClass possibleSuc : successorSet)
		    { 
			/*.. для каждой аксиомы, описывающей этого наследника */
			isInherited = true;
			for (SplitAxiom succAx : this.getAxiomsRelevantToClass(possibleSuc, axList))
			{/*==.. проверяем подразумевает ли она (succAx) аксиому AxM*/
			    if (isEntailedAxiomFrom(succAx.rightOWLclassExpression, /* ==> */ axM.rightOWLclassExpression))
			    {
				isInherited = false;
				break;
			    } 
				
			}/*======*/
			/*... наследуем аксиому AxM, если ни одна из (succAx) ее не подразумевала  */
			if (isInherited)
			{
			    extendedAxiomList.add(this.getInheretedAxiom(axM, possibleSuc));
			} 
			LOGGER.info(" Is inherited: "+isInherited+" by "+ possibleSuc.getIRI().getFragment());
		    }
		}

		LOGGER.info("== END - Add Inheritance of Axioms =======================");
		return extendedAxiomList;
	}
	
	/**
	 * Возвращает список аксиом, описывающих класс.
	 * @param cls
	 * @param splitAxList
	 * @return
	 */
	private ArrayList<SplitAxiom> getAxiomsRelevantToClass(OWLClass cls, ArrayList<SplitAxiom> splitAxList)	
	{
	    ArrayList<SplitAxiom> relevantAxList = new ArrayList<SplitAxiom>();
	    /*... выбираем аксиомы, где он стоит в левой части (т.е. те, что его описывают).
	     * Каждую такую аксиому проверяем на "вложенность" в аксиому ахМ.
	     * Если ни одна из них не входит в ахМ, то ахМ наследуется данным наследником.*/					
	    
	    for (SplitAxiom ax : relevantAxList)
	    {
		if (!ax.leftOWLclassExpression.isAnonymous() && 
			ax.leftOWLclassExpression.asOWLClass().equals(cls) )
		{
		    relevantAxList.add(ax);
		} 
	    }
	    
	    return relevantAxList;	    
	}
	
	/**
	 * Определяет выводиться ли одно утверждение из другого.
	 * @param initialAx исходное утверждение
	 * @param entailedAx утверждение, которое предположительно следует из исходного.
	 * @return
	 */
	private boolean isEntailedAxiomFrom(OWLClassExpression initialAx, OWLClassExpression entailedAx)
	{
	    /* ..для проверки вхождения - делаем аксиому о вложенности и проверяем выводиться ли она */
	    OWLSubClassOfAxiom ax_SubclassOf_axM =
				ontOWL.df.getOWLSubClassOfAxiom(initialAx, entailedAx);
		
	    return ontOWL.reas.isEntailed(ax_SubclassOf_axM);
	}
	
	/**
	 * Возврящает копию переданной аксиомы, приписанной классу
	 * @param ax аксиома
	 * @param successorClass класс - наследник аксиомы
	 * @return
	 */
	private SplitAxiom getInheretedAxiom(SplitAxiom ax, OWLClass successorClass)
	{
		/* получаем клон ахМ - меняем в нем левую часть */
		SplitAxiom newAx = (SplitAxiom) DataFactory.getFactory().getCloneAxiom(ax);
		//SimpleSubAxiom successorSubAx = this.dataFactory.getSimpleSubAxiom(successorClass.getIRI(), this.ontUPO);
		//SimpleSubAxiom successorSubAx = this.dataFactory.getSimpleSubAxiom(successorClass, this.ontOWL, this.ontUPO);
		newAx.leftOWLclassExpression = successorClass;	
		return newAx;
	}

	/**
	 * Возращает карту с наборами аксиом, описывающих классы онтологии.
	 * @param axList исходный набор аксиом
	 * @return карта (класс онтологии -- список аксиом, определяющих этот класс)
	 */
	private HashMap<OWLClass, ArrayList<SplitAxiom>> getAxiomMap(ArrayList<SplitAxiom> axList)
	{
	    LOGGER.info("----- Create map (OWLclass - Axiom List)-------");

	    HashMap<OWLClass, ArrayList<SplitAxiom>> axMap = new HashMap<OWLClass, ArrayList<SplitAxiom>>();
	    ArrayList<SplitAxiom> classAxiomList; 
	    OWLClass cls;
	
	    /* Для каждой аксиомы... */
	    for (SplitAxiom splitAxiom : axList)
	    {
		/*.. выбираем описываемых ею класс и находим/создаем набор набор аксиом
		 * для этого класса в карте*/
		cls = splitAxiom.leftOWLclassExpression.isAnonymous() ? 
			null : splitAxiom.leftOWLclassExpression.asOWLClass();		
		classAxiomList = axMap.containsKey(cls) ?  axMap.get(cls): new ArrayList<SplitAxiom>();
		
		/*.. добавляем аксиому в набор, а сам набор в карту */
		if (cls != null)
		{
		    classAxiomList.add(splitAxiom);
		    axMap.put(cls, classAxiomList);
		    LOGGER.info(" Class:"+cls.getIRI().getFragment()+" ==> "+splitAxiom.rightOWLclassExpression);
		}
	    }
	    LOGGER.info("------Class quality"+axMap.size());
	    LOGGER.info("----- END - Create map (OWLclass - Axiom List)-------");
	    
	    return axMap;
	}
	
}

