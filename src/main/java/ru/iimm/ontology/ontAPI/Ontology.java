/**
 * 
 */
package ru.iimm.ontology.ontAPI;


import org.semanticweb.HermiT.Reasoner;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;




/**
 * Класс, представляющий онтологию.
 * @author Lomov P. A.
 * @version 0
 */


public class Ontology implements ConstantsOntAPI
{
    /**
     * Адрес онтологии - название пространства имен онтологии
     */
	String IRIofOnt;

    /**
     * Адрес онтологии - ссылка на файл с онтологией.
     */
	String IRIPhysical;
	
	
    /**
     * Модель для хранения онтологии
    */	
	public OWLOntology ontInMem; 

    /**
     * Менеджер онтологии для выполнения операций над ней.
    */	
	public OWLOntologyManager mng; 
	
	/**
	 * Машина вывода, сопряженная с онтологией.
	 */	
	//PelletReasoner reas;
	public OWLReasoner reas;
	
	/**
	 * Тупая машина вывода, сопряженная с онтологией.
	 */
	public	OWLReasoner sreas;
	//InferredEquivalentClassAxiomGenerator ccc;
	
	/**
	 * 
	 */
	public OWLDataFactory df;
	
	private static final Logger log = LoggerFactory.getLogger(Ontology.class);
	
	static int DEBUG;
	
	/**
	 * Создает пустую онтологию.
	 */
	protected Ontology()
	{										
		mng = OWLManager.createOWLOntologyManager();
		df = mng.getOWLDataFactory();
	}

	
	/**
	 * Загружает немодульную онтологию по некоторому IRI. 
	 * Предполагается, что онтология скачается по IRI.
	 * @param ontIRI указывается с протоколом - например (file:///W://WorkingHDD//_Eclipse//ccc.owl)
	 * @param filePath
	 */
	public Ontology(IRI ontWebIRI)
	{													
		/* Создаем менеджер */
		mng = OWLManager.createOWLOntologyManager();
		df = mng.getOWLDataFactory();
		
		/* Создаетм маппер для IRI документа и онтологии - предполагаются, что они совпадают */
		SimpleIRIMapper iriMap = new SimpleIRIMapper(ontWebIRI, ontWebIRI);
		
		/* Загружаем онтологию */
		ontInMem = this.loadOntologyByIRI(ontWebIRI, this.mng, iriMap);

		/* Назначаем тупой резанер - для вывода явных фактов */
		sreas = (new StructuralReasonerFactory()).createReasoner(ontInMem);
        
		/* Назначаем резанер */
		setReasoner(true);
	}
	
	/**
	 * Загружает онтологию по указаннову IRI, используя переданный 
	 * менеджер и маппер.
	 * @param ontLocationIRI
	 * @param ontManager
	 * @param filledMapper
	 * @return
	 */
	private OWLOntology loadOntologyByIRI(IRI ontLocationIRI, OWLOntologyManager ontManager, OWLOntologyIRIMapper filledMapper)
	{
		/* Допавляем мэппер, в котором уже указаны мэпируемые IRI, к менеджеру онтологии */
		ontManager.addIRIMapper(filledMapper);
		
		OWLOntology ont = null;
		try
		{
			ont = mng.loadOntology(ontLocationIRI);
		}
		catch (OWLOntologyCreationException e)
		{
			log.error("!!! The ontology could not be loaded/created: "
					+ "\n!!! " + e.getMessage());
		}
		
		return ont;
	}
	
	/**
	 * Загружает немодульную онтологию с некоторым IRI из файла. 
	 * @param ontIRI
	 * @param filePath
	 */
	public Ontology(IRI ontIRI,String filePath)
	{
		/* Создаем менеджер */
		mng = OWLManager.createOWLOntologyManager();
		df = mng.getOWLDataFactory();
		
		/* Создаетм маппер для IRI онтологии и документа (в котором она храниться) */
		SimpleIRIMapper iriMap = new SimpleIRIMapper(ontIRI, IRI.create("file:/"+filePath));
		
		/* Загружаем онтологию */
		ontInMem = this.loadOntologyByIRI(ontIRI, this.mng, iriMap);

		/* Назначаем тупой резанер - для вывода явных фактов */
		sreas = (new StructuralReasonerFactory()).createReasoner(ontInMem);
        
		/* Назначаем резанер */
		setReasoner(true);
	}
	
	
	/**
	 * Загружает много-модульную онтологию по указанному пути.
	 * @param dir
	 * @param fileName
	 * 
	 */
	public Ontology(String dir, 
			String fileName, 
			Boolean mergeImportedOnt, 
			Boolean makeConsitnenseCheck)
	{
		/* Создаем менеджер */
		mng = OWLManager.createOWLOntologyManager();
		df = mng.getOWLDataFactory();

		/* Создаем автоматический мэппер, чтобы все модули-файлы загрузились из папки, 
		 * если онтология модульная + все модули должны быть в одной папке и ее подпапках */
		AutoIRIMapper mapperThs = new AutoIRIMapper(new File(dir), true);

		/* Формируем путь к основному файлу онтологии,
		 * меняем разделите на джавовский, если надо */
		String path = (dir + File.separator + fileName);
		if (File.separator.compareTo("//") != 0) path = path.replace(File.separator, "//");
		IRI locationIRI = IRI.create("file:/" +path);
				
		/* Загружаем онтологию */
		ontInMem = this.loadOntologyByIRI(locationIRI, this.mng, mapperThs);

		/* Назначаем тупой резанер - для вывода явных фактов */
		sreas = (new StructuralReasonerFactory()).createReasoner(ontInMem);
        
		/* Назначаем резанер */
		setReasoner(true);
		
		//Объединяем импортированные онтологии вместе с основной в одной модели
		if (mergeImportedOnt)	mergeImportedOntologies(ontInMem);

		log.info("===END - Load ontology:================================\n");
	}
	
	/**
	 * Назначает машину вывода. 
	 */
	private void setReasoner(Boolean consistenCheck)
	{
		// Назначаем hermit resaner
		Reasoner hermit=new Reasoner(ontInMem);
		reas = hermit;
		log.info("   ===setReasoner=====");
		log.info("   For ont: "+ontInMem.toString());
		log.info("   set resoner:" + reas.getReasonerName()+" "+
		reas.getReasonerVersion().getBuild()+"."+
		reas.getReasonerVersion().getMajor()+"."+
		reas.getReasonerVersion().getMinor()+"."+
		reas.getReasonerVersion().getPatch());
				
		if (consistenCheck)	
		{
			log.info("   Check consistent:");
			if (reas.isConsistent())
				log.info(" OK");
			else
				log.info(" !!! IS NOT CONSISTENT !!!");
		}	
		log.info("   ===END-setReasoner======");
	}	
	
	public Ontology addInferredAxiomToOntology(Ontology ont)
	{
        // To generate an inferred ontology we use implementations of inferred axiom generators
        // to generate the parts of the ontology we want (e.g. subclass axioms, equivalent classes
        // axioms, class assertion axiom etc. - see the org.semanticweb.owlapi.util package for more
        // implementations).  
        // Set up our list of inferred axiom generators
        List<InferredAxiomGenerator<? extends OWLAxiom>> gens = 
        		new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
        
        gens.add(new InferredSubClassAxiomGenerator());
        /*
        gens.add(new InferredEquivalentClassAxiomGenerator<? extends OWLAxiom>());
        gens.add(new InferredEquivalentClassAxiomGenerator());
        */
/*
        // Put the inferred axioms into a fresh empty ontology - note that there
        // is nothing stopping us stuffing them back into the original asserted ontology
        // if we wanted to do this.
        OWLOntology infOnt = man.createOntology();
*/
        
        // Now get the inferred ontology generator to generate some inferred axioms
        // for us (into our fresh ontology).  We specify the reasoner that we want
        // to use and the inferred axiom generators that we want to use.
        InferredOntologyGenerator iog = new InferredOntologyGenerator(this.reas, gens);
        
        
        
        iog.fillOntology(this.mng, this.ontInMem);
		return ont;
	}
	
	
	/**
	 * Связывает один экземпляр с другим объектным свойством.
	 * Если связь уже существует, то дополнительная не добавляется. Предполагается, что
	 * экземпляры существуют.
	 * @param domInd
	 * @param prp
	 * @param rngInd
	 * @todo Возможно стоит проверять существование экземпляров пред связыванием.
	 */
	public OWLObjectPropertyAssertionAxiom setObjectPrpBetween
		(OWLIndividual domInd, OWLObjectProperty prp ,OWLIndividual rngInd)
	{
		 
		OWLObjectPropertyAssertionAxiom asrt = 
				df.getOWLObjectPropertyAssertionAxiom(prp, domInd, rngInd);
		mng.addAxiom(ontInMem, asrt);
		return asrt;
	}


	/**
	 * Устанавливает значение типизированного свойства (datatype property)
	 * у экземпляра
	 * @param ind
	 * @param prp
	 * @param value значение как строка
	 * @param type указывает OWL2Datatype значения (необходимо для генерации OWLliterala - значения свойства)
	 * @return OWLPropertyAssertionAxiom
	 * @todo Возможно стоит проверять существование экземпляров пред связыванием.
	 */
	public OWLPropertyAssertionAxiom setDataPrpValue
		(OWLIndividual ind, OWLDataProperty prp, String value, OWL2Datatype type)
	{
		OWLLiteral valLiteral = df.getOWLLiteral(value, type);
		OWLDataPropertyAssertionAxiom asrt = df.getOWLDataPropertyAssertionAxiom(prp, ind, valLiteral);
		mng.addAxiom(ontInMem, asrt);
		return asrt;
	}
	
	/**
	 * Возвращает значените типизированного свойства заданного экземпляра.
	 * @param ind
	 * @param prp
	 * @return
	 */
	public OWLLiteral getDataPrpValue(OWLNamedIndividual ind, OWLDataProperty prp)
	{
		OWLDataProperty tprp = null;
		
		for (OWLDataPropertyAssertionAxiom ax : this.ontInMem.getDataPropertyAssertionAxioms(ind))
		{
			if (!ax.getProperty().isAnonymous())
			{
				tprp = ax.getProperty().asOWLDataProperty();
				if	(ind.equals(ax.getSubject()) & tprp.equals(prp))
				{
					return ax.getObject();
				}
			}
		}
		log.warn(	"!! There is no dataproperty of value:" 
				+ "\n!! Subject: "+ getShortIRI(ind)
				+ "\n!! DataProperty: "+ getShortIRI(prp));
		
		return null;
	}	
	
	/**
	 * Связывает один класс с другим объектным свойством.
	 * Если связь уже существует, то дополнительная не добавляется.
	 * @param domInd
	 * @param prp
	 * @param rngInd
	 * @todo Возможно стоит проверять существование классов пред связыванием.
	 */	
	public void setObjectPrpBetween(OWLClass domCls, OWLObjectProperty prp , OWLClass rngCls)
	{
		OWLObjectPropertyDomainAxiom asrtD = df.getOWLObjectPropertyDomainAxiom(prp, domCls);
		OWLObjectPropertyRangeAxiom asrtR  = df.getOWLObjectPropertyRangeAxiom(prp, rngCls);
		mng.addAxiom(ontInMem, asrtD);
		mng.addAxiom(ontInMem, asrtR);
	}
	
	/**
	 * Добавляет экземпляр класса X в онтологию - IRI равен IRI
	 * класса X. Если элемент с таким IRI есть - возвращает ссылку на него.
	 * @param clsOfInd
	 * @return OWLNamedIndividual
	 */
	public OWLNamedIndividual addInd(OWLClass clsOfInd, String IRIOfInd)
	{
		if (IRIOfInd.isEmpty())
		{
			log.error("!!! IRI is not set - can't add without IRI.");
			System.exit(9);
		}
		
		//Создаем IRI - идентификатор объекта в онтологии
		//генерим IRI пока не попадется новый.
		IRI iriInd = IRI.create(IRIOfInd);
		OWLNamedIndividual ind= df.getOWLNamedIndividual(iriInd);			
		
		
		//Проверяем если ли в онтологии элемент с таким же IRI
		//Если есть не добавляем а возвращаем ссылку на него
		if (isExistInOntology(ind))
		{
			return ind;
		}
		else
		{
			//Добавляем аксиому в онтологию
			OWLDeclarationAxiom dAxiom = df.getOWLDeclarationAxiom((OWLEntity)ind);
			mng.addAxiom(ontInMem, dAxiom);
			
			//System.out.println(">>>IND:"+ind.getIRI());
			
			// @todo проверить существование класса
			
			//Создаем аксиому членства экземпляра в классе
			OWLIndividualAxiom iAxiom = df.getOWLClassAssertionAxiom(clsOfInd, ind);
			mng.addAxiom(ontInMem, iAxiom);
			
			return ind;		
		}
		
	}
	
	/**
	 * Добавляет свойство с некоторым IRI, как подсвойство другого в онтологию.
	 * Если там есть свойство с таким IRI - генерирует похожий и использует его.
	 * @param superPrpIRI
	 * @param IRIOfPrp
	 * @param generateNewIRI true - будем генерировать новый IRI на основе уже 
	 * существующего.
	 * @return добавленное или уже существующее свойство.
	 */
	public OWLObjectProperty addObjPrpAsSubprp(String superPrpIRI, 
													String IRIOfPrp,
													boolean generateNewIRI)
	{
		//Проверяем указан ли IRI
		if (IRIOfPrp==null)
		{
			log.info("!!! IRI is not set - can't add without IRI.");
			System.exit(9);
		}
		
		//Создаем IRI - идентификатор объекта в онтологии из предложенного.
		IRI iriInd = IRI.create(IRIOfPrp);
		OWLObjectProperty prp = df.getOWLObjectProperty(IRI.create(IRIOfPrp));			
		
		// Обработка ситуации - если уже есть такое свойство
		if (isExistInOntology(prp))
		{
			log.info("Obj proprty EXISTED: "+IRIOfPrp);

			if (generateNewIRI)
			{//Если такой IRI уже есть генерим пока не получим новый						
				int i=0;				
				while (isExistInOntology(prp))
				{						
				IRI iri = IRI.create(IRIOfPrp+"-"+i++);
				prp = df.getOWLObjectProperty(iri);		
				}
			}
		}
		
						
		//Добавляем аксиому в онтологию
		OWLDeclarationAxiom dAxiom = df.getOWLDeclarationAxiom((OWLEntity)prp);
		mng.addAxiom(ontInMem, dAxiom);
					
		//Создаем аксиому свойства и его подсвойства
		OWLObjectProperty superPrp = df.getOWLObjectProperty(IRI.create(superPrpIRI));
		OWLSubObjectPropertyOfAxiom iAxiom = 
			df.getOWLSubObjectPropertyOfAxiom(prp, superPrp);		
		mng.addAxiom(ontInMem, iAxiom);
			
		return prp;					
	}
	
	////////////////////
	/**
	 * Добавляет объектное свойство с некоторым IRI в онтологию.
	 * Если там есть свойство с таким IRI - генерирует похожий и использует его.
	 * TODO подумать стоит ли применять генерацию
	 * @param IRIOfPrp
	 * @param generateNewIRI true - будем генерировать новый IRI на основе уже 
	 * существующего.
	 * @return добавленное или уже существующее свойство.
	 */
	public OWLObjectProperty addObjPrp(String IRIOfPrp, boolean generateNewIRI)
	{
		//Проверяем указан ли IRI
		if (IRIOfPrp==null)
		{
			log.info("!!! IRI is not set - can't add without IRI.");
			System.exit(9);
		}
		
		//Создаем IRI - идентификатор объекта в онтологии из предложенного.
		IRI iriInd = IRI.create(IRIOfPrp);
		OWLObjectProperty prp = df.getOWLObjectProperty(IRI.create(IRIOfPrp));			
		
		//System.out.println("vvv:" + prp);
		// Обработка ситуации - если уже есть такое свойство
		if (isExistInOntology(prp))
		{
			log.info("Obj proprty EXISTED: "+IRIOfPrp);
			if (generateNewIRI)
			{//Если такой IRI уже есть генерим пока не получим новый						
				int i=0;				
				while (isExistInOntology(prp))
				{						
				IRI iri = IRI.create(IRIOfPrp+"-"+i++);
				prp = df.getOWLObjectProperty(iri);		
				}
			}
		}
						
		//Добавляем аксиому в онтологию
		OWLDeclarationAxiom dAxiom = df.getOWLDeclarationAxiom((OWLEntity)prp);
		mng.addAxiom(ontInMem, dAxiom);
					
		return prp;					
	}

	/**
	 * Добавляет объектное свойство с некоторым IRI в онтологию.
	 * Если там есть свойство с таким IRI - генерирует похожий и использует его.
	 * TODO подумать стоит ли применять генерацию
	 * @param IRIOfPrp
	 * @param generateNewIRI создавать ли свойство с похожим IRI в онтологии, если
	 * исходное уже существует.
	 * @return
	 */
	 
	public OWLDataProperty addDtpPrp(String IRIOfPrp, boolean generateNewIRI)
	{
		//Проверяем указан ли IRI
		if (IRIOfPrp==null || IRIOfPrp.isEmpty())
		{
			log.info("!!! IRI is not set - can't add without IRI.");
			System.exit(9);
		}
		
		//Создаем IRI - идентификатор объекта в онтологии из предложенного.
		OWLDataProperty prp = df.getOWLDataProperty(IRI.create(IRIOfPrp));			
		
		// Обработка ситуации - если уже есть такое свойство
		if (isExistInOntology(prp))
		{
			log.info("Dtp proprty EXISTED: "+IRIOfPrp);
			if (generateNewIRI)
			{//Если такой IRI уже есть генерим пока не получим новый
				IRI iri = this.getNewIRI(prp.getIRI());
				prp = df.getOWLDataProperty(iri);
/*
				int i=0;				
				while (isExistInOntology(prp))
				{						
				IRI iri = IRI.create(IRIOfPrp+ConstantsOntAPI.DELIMITER_NAME+i++);
				prp = df.getOWLDataProperty(iri);		
				}
*/				
			}
			else return prp;
		}
						
		//Добавляем аксиому в онтологию
		OWLDeclarationAxiom dAxiom = df.getOWLDeclarationAxiom((OWLEntity)prp);
		mng.addAxiom(ontInMem, dAxiom);
					
		return prp;					
	}

	
	/**
	 * Определяет наличие отношения между экземплярами.
	 * 
	 * @param hasPrp отношение
	 * @param ind1
	 * @param ind2
	 * @return true если отношение определено или в одну или в другую сторону. 
	 */
	public boolean isRelationExist (OWLObjectProperty hasPrp, 
										OWLNamedIndividual ind1, 
											OWLNamedIndividual ind2)
	{
		
		OWLObjectPropertyAssertionAxiom axs = df.getOWLObjectPropertyAssertionAxiom(hasPrp, ind1, ind2);
		OWLObjectPropertyAssertionAxiom axs2 = df.getOWLObjectPropertyAssertionAxiom(hasPrp, ind2, ind1);
		return (ontInMem.containsAxiom(axs)||ontInMem.containsAxiom(axs2));
	}
	
	/**
	 * Добавляет аннотацию к элементу онтологии.
	 * @param anotPrpIRI аннатационное свойство
	 * @param txt текст в аннотационном свойстве
	 * @param entity элемент онтологии
	 */
	public void addAnnotation(String anotPrpIRI, OWLLiteral txt, OWLEntity entity)
	{
		// Проверяем не является ли аннотируемый объект свойством и приводим его если это так
		// Это необходимо для вызова верной разновидности метода IsExistinOntology		
		// Создаем текст
		OWLLiteral txtLit = txt;
		
		// Получаем аннотационное свойство
		OWLAnnotationProperty prp = df.getOWLAnnotationProperty(IRI.create(anotPrpIRI));
		
		// Создаем аннотацию - соединяем свойство и текст
		OWLAnnotation anot = df.getOWLAnnotation(prp, txtLit);
		
		// Проверяем существование элемента для аннотирования		
		if (!(isExistInOntology(entity)))
		{
			log.error("!!! No entity: " + entity.getIRI() + " in "+IRIofOnt); 
			log.error("!!! Ontlogy.addAnnotation");
			System.exit(9);
		}
		
		// Создаем аксиому, привязывающую аннотацию к элементу по его IRI  
		OWLAxiom ax = df.getOWLAnnotationAssertionAxiom(entity.getIRI(), anot);
		
		// Добавляем аксиому
		mng.addAxiom(ontInMem, ax);				
	}

	
	/**
	 * Возвращает значение label свойства (первого - если их несколько) у
	 * элемента онтологии
	 * @param ent
	 * @return
	 */
	public String getLabel(OWLEntity ent)
	{
		return getAnnotationValueOfEntity(ent, IRI.create(RDF_LABEL) );
	}
	
	/**
	 * Возвращает значение label свойства (первого - если их несколько) у
	 * элемента онтологии
	 * @param ent
	 * @param landID
	 * @return
	 */
	public String getLabel(OWLEntity ent, String landID)
	{
		return getAnnotationValue(ent.getIRI(), IRI.create(RDF_LABEL), landID );
	}
	
	/**
	 * Возвращает значение label свойства (первого - если их несколько) у
	 * элемента онтологии
	 * @param ent
	 * @param landID
	 * @return
	 */
	public String getLabel(IRI entIRI, String landID)
	{
		return getAnnotationValue(entIRI, IRI.create(RDF_LABEL), landID );
	}
	
	
	

	/**
	 * Возвращает значение аннотационного свойства элемента онтологии.
	 * @param ent сущность, у которой береться аннотационное свойство.
	 * @param iri IRI свойства, значение которого надо взять.
	 * @return строковое значение без типа, 
	 * 		   null если свойства не найдено.
	 */
	public String getAnnotationValueOfEntity(OWLEntity ent, IRI iri)
	{
		
		for (OWLAnnotation annot :  getAnnotList(ent))
		{
			log.debug("-----\nCompare IRI:" + 
					annot.getProperty().getIRI().toString().equals(iri.toString())  
					+ " for: \n"+iri.toString() + "\n"
								+annot.getProperty().getIRI().toString()+"\n-----" );
	
			if ( annot.getProperty().getIRI().equals(iri) )
			{				
				log.info(((OWLLiteral) annot.getValue()).getLang()+"||"+((OWLLiteral) annot.getValue()).getLiteral());
				return ((OWLLiteral) annot.getValue()).getLiteral().toString();
			} 
		}

		return null;
	}


	
	/**
	 * Возвращает значение аннотационного свойства элемента онтологии.
	 * @param entIRI IRI элемента, у которого береться аннотационное свойство.
	 * @param iri IRI свойства, значение которого надо взять.
	 * @return строковое значение без типа, 
	 * 		   null если свойства не найдено.
	 */
	public String getAnnotationValueOfEntityByIRI(IRI entIRI, IRI iriPrp)	
	{
		OWLEntity ent = this.getEntityByIRI(entIRI);
		if (ent!=null)
		{
			return this.getAnnotationValueOfEntity(ent, iriPrp);
		}
		return null;
		
	}
	/**
	 * Находит элемент онтологии по IRI.
	 * @param entIRI
	 * @return
	 */
	public OWLEntity getEntityByIRI(IRI entIRI)
	{
		Set<OWLEntity> entSet = this.ontInMem.getEntitiesInSignature(entIRI, true);

		if (entSet.size()==0){
			log.warn( "!!! There is no entity with IRI:/n"+
					"!!! "+entIRI+
					"!!! return NULL");
			return null;
		}

		if (entSet.size()>1) {
			log.warn( 			"!!! Exist more that one entity with IRI:/n"+
								"!!! "+entIRI+
								"!!! Get only first one.");
		}
			
		return (OWLEntity) entSet.iterator().next();
	}

	
	
	
	/**
	 * Возвращает значение аннотационного свойства именованного экземпляра.
	 * @param indIRI IRI экземпляра, у которого береться аннотационное свойство.
	 * @param iri IRI свойства, значение которого надо взять.
	 * @return строковое значение без типа, 
	 * 		   null если свойства не найдено.
	 */
	public String getAnnotationValue(IRI indIRI, IRI iri)
	{
		OWLEntity ent = this.df.getOWLNamedIndividual(indIRI);
		for (OWLAnnotation annot :  getAnnotList(ent))
		{
			log.debug("-----\nCompare IRI:" + 
					annot.getProperty().getIRI().toString().equals(iri.toString())  
					+ " for: \n"+iri.toString() + "\n"
								+annot.getProperty().getIRI().toString()+"\n-----" );
	
			if ( annot.getProperty().getIRI().equals(iri) )
			{				
				//return annot.getValue().toString();
				//System.out.println(" return: "+((OWLLiteral) annot.getValue()).getLiteral().toString());
				return ((OWLLiteral) annot.getValue()).getLiteral().toString();
			} 
		}
		/*if (DEBUG>95) debuger.Debug.printSetOfent(getAnnotList(ent), 
				"Annotation list for:"+ent.getIRI().toString());
		*/		
		//System.out.println(" return: "+ null);
		return EMPTY;
	}
	
	/**
	 * Возвращает значение аннотационного свойства именованного экземпляра.
	 * @param indIRI IRI экземпляра, у которого береться аннотационное свойство.
	 * @param iri IRI свойства, значение которого надо взять.
	 * @param langID идентификатор языка
	 * @return строковое значение без типа, 
	 * 		   null если свойства не найдено.
	 */
	public String getAnnotationValue(IRI indIRI, IRI iri, String langID)
	{
		OWLEntity ent = this.df.getOWLNamedIndividual(indIRI);
		for (OWLAnnotation annot :  getAnnotList(ent))
		{
			log.debug("-----\nCompare IRI:" + 
					annot.getProperty().getIRI().toString().equals(iri.toString())  
					+ " for: \n"+iri.toString() + "\n"
								+annot.getProperty().getIRI().toString()+"\n-----" );
	
			if ( annot.getProperty().getIRI().equals(iri) )
			{	
				OWLLiteral valLiteral = ((OWLLiteral) annot.getValue());			
				if (valLiteral.getLang().equals(langID))
					return ((OWLLiteral) annot.getValue()).getLiteral().toString();
			} 
		}
		return EMPTY;
	}
	
	
	
	/**
	 * Возвращает набор аннотаций у экземпляра, класса или свойства.
	 * @param ent
	 * @return Set<OWLAnnotation> или null если не удалось определить тип 
	 * сущности.
	 */
	private Set<OWLAnnotation> getAnnotList(OWLEntity ent)
	{
		// Определяем что это - класс, свойство или экземпляр
		// Получаем список аннотаций
		if (ent.isOWLNamedIndividual()) 
			return (ent.asOWLNamedIndividual()).getAnnotations(ontInMem);
		if (ent.isOWLClass()) 
			return (ent.asOWLClass()).getAnnotations(ontInMem);
		if (ent.isOWLObjectProperty()) 
			return (ent.asOWLObjectProperty()).getAnnotations(ontInMem);
		if (ent.isOWLDataProperty()) 
			return (ent.asOWLDataProperty()).getAnnotations(ontInMem);

		return null; 
	}
		
	/**
	 * Проверяет по IRI существует ли элемент-аргумент в онтологии.
	 * @param ind
	 * @return
	 */
	public boolean isExistInOntology(OWLEntity ind)
	{
//		System.out.println("Check of existation: " + ind.toString());
//		System.out.println("		 in: " + ontInMem);
		//System.out.println(ontInMem.getSignature());
		//System.out.println(ontInMem.containsEntityInSignature(ind));
		if (ind==null) 
		{log.error("!!! Error - Ontology.isExistInOntology");
		log.error("!!! Desired entity is empty:"+ind);
		 System.exit(9);}
		
		return ontInMem.containsEntityInSignature(ind);		
	}
		
	/**
	 * Метод, сохраняющий онтологию по URL в RDF-XML формате.
	 * Не сохраняет импортированные онтологии.	
	 *
	 * @param filePath URL онтологии или путь к файлу C://windows//temp//MyOnt.owl
	 * @author Lomov P. A.
	 * @version 1.0
	 * @deprecated use instead {@link Ontology#saveOntologies(String)}
	 */
	public void saveOntologyInRDFXML(String filePath) 
	{
	     try 
	     {	    	 
	    	 IRI physIRI = IRI.create("file:/"+filePath);
	    	 mng.saveOntology(ontInMem, new RDFXMLOntologyFormat(), physIRI);
	    	 mng.removeOntology(ontInMem);	     
		 }
	     catch (OWLOntologyStorageException e) 
	     {
	    	 log.error("!!! The ontology could not be saved: " + e.getMessage());
	     }
	     
	     log.info("Ontology from IRI:"+filePath+"succesfuly saved");     
	
	}
	
	/**
	 * Сохраняет онтологию и импортированные в нее онтологии в указанной папке.
	 * @param directoryPath
	 */
	public void saveOntologies(String directoryPath) 
	{
		/* Формируем набор онтологий (основная и те, что импортирваны в нее) для сохранения */
		Set<OWLOntology> onts = this.mng.getImportsClosure(ontInMem);
		directoryPath = "file:/"+directoryPath+"/";
	     try 
	     {	    	 
	    	 for (OWLOntology ont : onts)
			{
	    		 String fileName = directoryPath + getShortIRI(mng.getOntologyDocumentIRI(ont));
		    	 IRI physIRI = IRI.create(fileName);
		    	 mng.saveOntology(ont, new RDFXMLOntologyFormat(), physIRI);
		    	 //mng.removeOntology(ontInMem);	     
			     log.info("Saved ontology: <{}>", fileName);				
			}
		 }
	     catch (Exception e) 
	     {
	    	 log.error("!!! The ontology could not be saved: " + e.getMessage());
	     }
	}
	
	
		
	/**
	 * Возвращает список подклассов Thing.
	 * @return
	 */
	public Set<OWLClass> getTopClasses()
	{
		
		//df.getOWLClass(IRI.create(IRI_Thing));
		return getNamedSubclassesOfNamedClass(df.getOWLClass(IRI.create(OWL_THING)),  true);
	}
	
	/**
	 * Возвращает именованные подклассы указанного класса.
	 * @param cl
	 * @param useReasoner использовать ли машину вывода
	 * @param onlyDirect выводит только непосредственные(прямые)
	 * @return
	 */
	public Set<OWLClass> getNamedSubclassesOfNamedClass (OWLClass cl,  boolean onlyDirect)
	{
		Set<OWLClass> classSet = reas.getSubClasses((OWLClassExpression)cl,onlyDirect).getFlattened();
		OWLClass nothing = this.df.getOWLClass(IRI.create(ConstantsOntAPI.OWL_NOTHING));
		classSet.remove(nothing);
		
		return classSet;
		
	}
	
	/**
	 * Возвращает именованные суперклассы указанного класса.
	 * @param cl
	 * @param useReasoner использовать ли машину вывода
	 * @param onlyDirect выводит только непосредственные(прямые)
	 * @return
	 */
	public Set<OWLClass> getNamedSuperclassesOfNamedClass (OWLClass cl, boolean useReasoner, boolean onlyDirect)
	{
		// false - выводи по всей иерархии
		return useReasoner ? reas.getSuperClasses( (OWLClassExpression)cl, onlyDirect ).getFlattened()
							: sreas.getSuperClasses( (OWLClassExpression)cl, onlyDirect ).getFlattened();
	}	
	
	/*
	private Set<OWLClass> getAllSureclasses(Set<OWLClass> setCls)
	{
		for (OWLClass cls : setCls)
		{
			getAllSureclasses( sreas.getSuperClasses(cls, true).getFlattened());
		}
	}
	*/
	/**
	 * Генерирует новый IRI на основании имеющегося, для того чтобы он не
	 * совпадал с IRI существующего в онтологии элемента.
	 * @param oldIRI
	 * @return
	 */
	public String getNewIRI(String oldIRI)
	{
		//Создаем IRI - идентификатор объекта в онтологии
		//генерим IRI пока не попадется новый.
				
		int i=0;		
		IRI checkingIRI=IRI.create(oldIRI);
		while (ontInMem.containsEntityInSignature(checkingIRI))
		{		
			checkingIRI = IRI.create(oldIRI+ ConstantsOntAPI.DELIMITER_NAME  + i++);			
		}
								
		return checkingIRI.toString();
		//return oldIRI+"-"+i;
	}
	
	/**
	 * Генерирует новый IRI на основании имеющегося, для того чтобы он не
	 * совпадал с IRI существующего в онтологии элемента.
	 * @param oldIRI
	 * @return
	 */
	public IRI getNewIRI(IRI oldIRI)
	{
		//Создаем IRI - идентификатор объекта в онтологии
		//генерим IRI пока не попадется новый.
				
		int i=0;		
		IRI checkingIRI=oldIRI;
		while (ontInMem.containsEntityInSignature(checkingIRI))
		{		
			checkingIRI = IRI.create(oldIRI.toString()+ ConstantsOntAPI.DELIMITER_NAME  + i++);			
		}
								
		return checkingIRI;
		//return oldIRI+"-"+i;
	}


	/**
	 * Возвращает суперклассы заданного класса
	 * @param clIRI
	 * @param onlyDirect
	 * @return
	 */
	public Set<OWLClass> getSuperclasesOfClass(String clIRI, Boolean onlyDirect)
	{
		/*
		 * Из строкового IRI делаем OWLClassExpression, 
		 * так как метод для получения супер классов только его принимает 
		 */
		OWLClassExpression exp = (OWLClassExpression) df.getOWLClass(IRI.create(clIRI));

		// Получаем не только прямые (false) или только их суперклассы через резонер
		if (onlyDirect)
		{
			return reas.getSuperClasses(exp, false).getFlattened();
		}
		else
		{
			return reas.getSuperClasses(exp, true).getFlattened();
		}
	}
	

	/**
	 * Выбирает типизированные свойства класса из онтологии
	 * @param clIRI
	 * @param withSuperClPrp брать ли свойства, наследуемые от суперклассов
	 * @return набор свойств класса
	 */
	public Set<OWLDataProperty> getDatatypesProperiesOfClass(String clIRI, Boolean withSuperClPrp)
	{
		Set<OWLDataProperty> prpSet = new HashSet<OWLDataProperty>();
		OWLClass cl = df.getOWLClass(IRI.create(clIRI));
		Set<OWLClass> clList = new HashSet<OWLClass>();
		Set<OWLDataPropertyDomainAxiom> axList = new HashSet<OWLDataPropertyDomainAxiom>();
		// Добавляем в список классов указанный
		clList.add(cl);
		// Добавляем (если указано) суперклассы в список классов
		if (withSuperClPrp) 
			clList.addAll(getSuperclasesOfClass(clIRI, false));
		
		// Получаем список аксиом - доменов из онтологии
		axList.addAll(ontInMem.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN));
		/*
		 * Перебираем списки - если класс или его суперклассы в домене свойства, 
		 * то заносим свойство в список свойств класса
		 */
		OWLDataProperty prp;
		for (OWLDataPropertyDomainAxiom ax : axList)
		{
			Boolean isAdded = false;
			for (OWLClass superClass : clList)
			{
				
				if ( ax.getClassesInSignature().contains(superClass))
				{				
					//domainRestr = ((OWLRestriction) ax);
					//prp = (OWLDataProperty) domainRestr.getProperty();
					prp = (OWLDataProperty) ax.getProperty();
//					System.out.println(cl+" =sp= "+prp);
					
					prpSet.add(prp);
					isAdded=true;
				}				
				// класс или его суперкласс был найден - свойство добавлено
				// остальные суперклассы не рассматриваем
				if (isAdded) break;
			}	
		}			
		return prpSet;
	}
	
	/**
	 * Выбирает объектные свойства класса из онтологии
	 * @param clIRI
	 * @param withSuperClPrp брать ли свойства, наследуемые от суперклассов
	 * @return набор свойств класса
	 */
	public Set<OWLObjectProperty> getObjectProperiesOfClass(String clIRI, Boolean withSuperClPrp) 
			
	{
		Set<OWLObjectProperty> prpSet = new HashSet<OWLObjectProperty>();
		OWLClass cl = df.getOWLClass(IRI.create(clIRI));
		Set<OWLClass> clList = new HashSet<OWLClass>();
		Set<OWLObjectPropertyDomainAxiom> axList = new HashSet<OWLObjectPropertyDomainAxiom>();
		// Добавляем в список классов указанный
		clList.add(cl);
		// Добавляем (если указано) суперклассы в список классов
		if (withSuperClPrp) 
			clList.addAll(getSuperclasesOfClass(clIRI, false));
		// Получаем список аксиом - доменов из онтологии
		axList.addAll(ontInMem.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN));
		/*
		 * Перебираем списки - если класс или его суперклассы в домене свойства, 
		 * то заносим свойство в список свойств класса
		 */
		OWLObjectProperty prp;
		for (OWLObjectPropertyDomainAxiom ax : axList)
		{
			Boolean isAdded = false;
			// Если класс или его суперклассы в домене свойства, то заносим свойство в список свойств класса
			for (OWLClass superClass : clList)
			{
				if ( ax.getClassesInSignature().contains(superClass))
				{									
					prp = (OWLObjectProperty) ax.getProperty();
					prpSet.add(prp);
					isAdded=true;
				}				
				// класс или его суперкласс был найден - свойство добавлено
				// остальные суперклассы не рассматриваем
				if (isAdded) break;
			}			
		}				
		return prpSet;
	}
	

	/**
	 * Возвращает набор объектных свойств класса.
	 * @param clIRI искомый класс
	 * @param infered включать ли свойства суперклассов
	 * @return Set<OWLObjectProperty> набор свойств класса.
	 * @deprecated
	 * !!! Не понял работу метода reas.getObjectPropertyDomains((OWLObjectPropertyExpression) prp, false)
	 * он возвращает суперклассы того класса, что в домене - а я думал что подклассы т.к. свойство наследуется.
	 */
	public Set<OWLObjectProperty> getObjProperiesOfClass2(String clIRI, Boolean infered)
	{	
		OWLClass cl = df.getOWLClass(IRI.create(clIRI));
		Set<OWLObjectProperty> prpSet = new HashSet<OWLObjectProperty>();
		
		log.info("==Seek property for class:\n" + "  "+ clIRI);
	// 1 вариант с учетом свойств суперкласса
	if (infered)
	{
		OWLClass dcls = df.getOWLClass(IRI.create(clIRI));
		// Пробегаем по аксиомам-свойств
		for (OWLAxiom ax : ontInMem.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN))
		{
			// Из аксиомы берем свойства. По идее в каджой аксиоме по одному свойству, но т.к. возвращается
			// список - то пробегаем по нему
			for (OWLObjectProperty prp : ax.getObjectPropertiesInSignature())
			{
				log.info("====Check property:\n" + "    " + prp);
				// получаем все классы из домена свойства
				Iterator<OWLClass> iterDom =
						reas.getObjectPropertyDomains((OWLObjectPropertyExpression) prp, false)
						.getFlattened().iterator();
				log.info("======Class in domain:");
				// ищем среди классов из домена искомый (из агрумента clIRI)
				// есди нашли - добавляем свойство в список класса				
				while (iterDom.hasNext())
				{
					OWLClass domCls = (OWLClass) iterDom.next();
					log.info("      " + domCls);
					if (domCls.getIRI().equals(dcls.getIRI()) )
					{
						prpSet.add(prp);
						log.info("===Add property:" + prp);
						break;
					}	
				}
			} 
		}				
	}
	//	2 вариант без учета свойств суперклассов - только те у которых класс в домене
	else
	{
		for (OWLAxiom ax : ontInMem.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN))
		{			
			if (ax.getClassesInSignature().contains(cl))
			{				
				for (OWLObjectProperty prp : ax.getObjectPropertiesInSignature())
				{					
					prpSet.add(prp);
				} 
			}
		}		
	}	
		
	/* if (DEBUG>95) debuger.Debug.printSetOfent(prpSet, "\n>>> getObjectProperiesOfClass: "+clIRI+":"); */
	log.debug("=== end of getProperiesOfClass "+clIRI+"=======");
		return prpSet;	
	}

	
	
	/**
  	 * Возвращает именованные классы из области значений (RANGE)
	 * объектного свойства 
	 * @param prpIRI IRI искомого свойства
	 * @return набор классов из RANGE
	 */
	public Set<OWLClass> getClassFromRange(String prpIRI)
	{
		OWLObjectProperty prp = df.getOWLObjectProperty(IRI.create(prpIRI));
		Set<OWLClass> clSet = new HashSet<OWLClass>();

		/*
		 * Взять аксиомы типа RANGE. Из каждой выдернуть набор 
		 * именованных классов, занести их в список.
		 */
		for (OWLAxiom ax : ontInMem.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE))
		{			
			if (ax.getObjectPropertiesInSignature().contains(prp))
			{				
				for (OWLClass cl : ax.getClassesInSignature())
				{					
					clSet.add(cl);
				} 
			}
		}
		
/*		if (DEBUG>95) debuger.Debug.printSetOfent(clSet, "\n>>> getClassFromRange: "+prpIRI+":"); */
		log.debug("=== end of getClassFromRange "+prpIRI+"=======");
		
		return clSet;
	}
	
	/**
	 * Возвращает типы данных (хотя вроде он только один) 
	 * типизированного свойства. Если тип не указан, то возврящается
	 * тип по-умолчанию (Liteal).
	 * @param prpIRI
	 */
	public Set<OWLDatatype> getDatatypesFromRange(String prpIRI)
	{
		OWLDataProperty prp = df.getOWLDataProperty(IRI.create(prpIRI));
		Set<OWLDatatype> datatypeSet = new HashSet<OWLDatatype>();
		
		/*
		 * Взять аксиомы типа RANGE. Из каждой выдернуть набор 
		 * типов, занести их в список.
		 */
		for (OWLAxiom ax : ontInMem.getAxioms(AxiomType.DATA_PROPERTY_RANGE))
		{			
			if (ax.getDataPropertiesInSignature().contains(prp))
			{				
				for (OWLDatatype datatype : ax.getDatatypesInSignature())
				{					
					datatypeSet.add(datatype);
				} 
			}
		}
		
		if (datatypeSet.isEmpty()) datatypeSet.add(df.getOWLDatatype(IRI.create(DEFAULT_DATATYPE)));
/*		if (DEBUG>95) debuger.Debug.printSetOfent(datatypeSet, "\n>>datatypeSetromRange: "+prpIRI+":"); */
		log.debug("=== end of getDatatypeSetFromRange "+prpIRI+"=======");
		
		return datatypeSet;
	}

	/**
	 * Возвращает тип данных (первый найденный) 
	 * типизированного свойства. Если тип не указан, то возврящается
	 * тип по-умолчанию (Liteal).
	 * @param prpIRI
	 */
	public OWLDatatype getDatatypeFromRange(String prpIRI)
	{
		OWLDataProperty prp = df.getOWLDataProperty(IRI.create(prpIRI));
		Set<OWLDatatype> datatypeSet = new HashSet<OWLDatatype>();
		
		/*
		 * Взять аксиомы типа RANGE. Из каждой выдернуть набор 
		 * типов, занести их в список.
		 */
		for (OWLAxiom ax : ontInMem.getAxioms(AxiomType.DATA_PROPERTY_RANGE))
		{			
			if (ax.getDataPropertiesInSignature().contains(prp))
			{		
				Set<OWLDatatype> tList = ax.getDatatypesInSignature(); 
				if (tList.isEmpty())
				{
					log.error("!!! No types for property:"+
							"\n!!! " + prp.getIRI()+ "\n!!! add default type" );
					return df.getOWLDatatype(IRI.create(DEFAULT_DATATYPE));
				}
				else
				{
					return (OWLDatatype) tList.iterator().next();
				}
					
			}
		}
		if (DEBUG>95) System.out.println("=== end of getDatatypeSetFromRange "+prpIRI+"=======");
		
		return null;
	}

	
	/**
	 * Добавляет аксиомы из одной онтологии в другую.
	 * @param ontDst онтология, в которуюю будет осуществлен перенос.
	 * @param ontSrc онтология - источник аксиом.

	 */
	public void mergeOntology(OWLOntology ontDst, OWLOntology ontSrc )
	{
		// Создаем менеджер
		OWLOntologyManager mngDst = OWLManager.createOWLOntologyManager();
		
		if (DEBUG > 90) 
			System.out.println("=== Merge ont SRC:" + ontSrc.toString() + 
							  "\n=== with DST: " + ontDst.toString());
		
		for (OWLAxiom ax : ontSrc.getAxioms())
		{
			if (DEBUG > 90) 
				System.out.println("    Add axion: "+ ax.toString());
			mngDst.addAxiom(ontDst, ax);
		}
	} 
	
	/**
	 * Получает список именованных классов.	
	 * @return
	 * @todo убрать дебаг
	 */
	public HashSet<OWLClass> getNamedClassList()
	{
		HashSet<OWLClass> listCLS = new HashSet<OWLClass>();
		
/*		
		Iterator<OWLOntology> ont = ontInMem.getImportsClosure().iterator();
		while (ont.hasNext())
		{
			OWLOntology owlOntology = (OWLOntology) ont.next();
			System.out.println(" Ont: "+ owlOntology.toString());
		}
*/		

		/*
		 * Взять аксиомы типа DECLARATION
		 * Из каждой выдернуть набор именованных классов
		 * Классы занести в список.
		 */
		for (OWLAxiom ax : ontInMem.getAxioms(AxiomType.DECLARATION))
		{		
			//System.out.println("AX:" + ax.toString());
			Iterator<OWLClass> iter = ax.getClassesInSignature().iterator();
			while (iter.hasNext())
			{
				OWLClass cls = (OWLClass) iter.next();
				listCLS.add(cls);
				//System.out.println(" Class: "+ cls);
			}
		}
		return listCLS;		
	}
	
	/**
	 * Получает список объектных свойств.
	 * @return
	 */
	public HashSet<OWLObjectProperty> getObjPropertyList()
	{
		HashSet<OWLObjectProperty> listPRP = new HashSet<OWLObjectProperty>();
		
		for (OWLAxiom ax : 
			ontInMem.getAxioms(AxiomType.DECLARATION))
		{		
//			System.out.println("OBJ AX:" + ax.toString());
			Iterator<OWLObjectProperty> iter = ax.getObjectPropertiesInSignature().iterator();
			while (iter.hasNext())
			{
				OWLObjectProperty prp = (OWLObjectProperty) iter.next();
				listPRP.add(prp);
//				System.out.println(" Class: "+ cls);
			}
		}		
		return listPRP;		
	}
	
	/**
	 * Получает список типизированных свойств.
	 * @return
	 */
	public HashSet<OWLDataProperty> getDtpPropertyList()
	{
		HashSet<OWLDataProperty> listPRP = new HashSet<OWLDataProperty>();
		
		for (OWLAxiom ax : 
			ontInMem.getAxioms(AxiomType.DECLARATION))
		{		
//			System.out.println("OBJ AX:" + ax.toString());
			Iterator<OWLDataProperty> iter = ax.getDataPropertiesInSignature().iterator();
			while (iter.hasNext())
			{
				OWLDataProperty prp = (OWLDataProperty) iter.next();
				listPRP.add(prp);
//				System.out.println(" Class: "+ cls);
			}
		}		
		return listPRP;		
	}
	
	
	/**
	 * Возвращает набор литератьных свойств класса.
	 * @param clIRI искомый класс
	 * @return Set<OWLObjectProperty> набор свойств класса.
	 */
	public Set<OWLDataProperty> getDtpProperiesOfClass(String clIRI)
	{
		if (DEBUG>95) System.out.println("\n>>> getDatatypeProperiesOfClass: "+clIRI+":");
		
		OWLClass cl = df.getOWLClass(IRI.create(clIRI));
		Set<OWLDataProperty> prpSet = new HashSet<OWLDataProperty>();

		// Взять аксиомы типа DOMAIN
		// Выбирать из них те, где есть искомый класс		
		// Выбирать из аксиом названия свойств
		for (OWLAxiom ax : ontInMem.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN))
		{			
			if (ax.getClassesInSignature().contains(cl))
			{				
				for (OWLDataProperty prp : ax.getDataPropertiesInSignature())
				{
					if (DEBUG>95) System.out.println("  -"+prp.getIRI());
					prpSet.add(prp);
				} 
			}
		}
		if (DEBUG>95) System.out.println("=== end of getProperiesOfClass "+clIRI+"=======");
		return prpSet;	
	}
	
	
	
	public boolean isEqualIRI (String fIri, String sIri)
	{
		IRI aIRI = IRI.create(fIri);
		IRI bIRI = IRI.create(sIri);
		return aIRI.equals(bIRI);
	}
	
	/* не нужен - equal используем
	public boolean isEqualIRI (IRI fIri, IRI sIri)
	{
		if (DEBUG>98) System.out.println("\n Compare IRI:" +
				fIri.toString().trim() + " & " + sIri.toString().trim()+"\n===========");
		//return fIri.toString().trim().equals(sIri.toString().trim());	
		return fIri.toString().trim().compareTo(sIri.toString().trim()) == 0;
	}
	*/
	
	/**
	 * Добавляет класс как подкласс в онтологию.
	 * @param clsIRI
	 * @param superClsIRI
	 * @return
	 */
	public OWLClass addClassAsSubclass(String clsIRI, String superClsIRI)
	{
		// Проверки существования
		if (superClsIRI.isEmpty() | clsIRI.isEmpty() )
		{
			System.out.println("!!! IRI is not set !!! addClassAsSubclass");
		}
		
		OWLClass superCls = df.getOWLClass(IRI.create(superClsIRI));
		OWLClass newCls = df.getOWLClass(IRI.create(clsIRI));

		if (!isExistInOntology(superCls))
		{
			System.out.println("!!! Super class:" + 
					superClsIRI + 
					" NOT exist.  addClassAsSubclass");
		}
		
		// Получаем и добавляем аксиому в онтологию
		mng.addAxiom(ontInMem, df.getOWLSubClassOfAxiom(newCls, superCls) );
		return newCls;
	}
	
	/**
	 * Удалаяет классы из онтологии по их базовому IRI. IRI указывается без # на конце.
	 * @param entIRI
	 */
	public void removeClassesByIRI(String entIRI)
	{
		 // создаем ремовер
		 OWLEntityRemover remover = new OWLEntityRemover(mng, Collections.singleton(ontInMem));
         //System.out.println("Number of individuals: " + ontInMem.getIndividualsInSignature().size());
    	 System.out.println("=== Entity to remove === ");
         // отправляем каждый элемент онтологии на ремовер
         for (OWLClass cls : ontInMem.getClassesInSignature())
         {
        	 //System.out.println("=== Entity to remove === ");
        	 if (entIRI.equals(getBaseIRI(cls)))
        	 {
        		 System.out.println(" > " + cls.getIRI().toString());
        		 cls.accept(remover);
        	 }	 
         }
         
         mng.applyChanges(remover.getChanges());         
         // At this point, if we wanted to reuse the entity remover, we would have to reset it
         remover.reset();         
	}
	
	/**
	 * Удалаяет объектные свойства из онтологии по их базовому IRI. IRI указывается без # на конце.
	 * @param entIRI
	 */
	public void removeObjPropertiesByIRI(String entIRI)
	{
		 // создаем ремовер
		 OWLEntityRemover remover = new OWLEntityRemover(mng, Collections.singleton(ontInMem));
         //System.out.println("Number of individuals: " + ontInMem.getIndividualsInSignature().size());
    	 System.out.println("=== Entity to remove === ");
         // отправляем каждый элемент онтологии на ремовер
         for (OWLObjectProperty prp : ontInMem.getObjectPropertiesInSignature())
         {
        	 //System.out.println("=== Entity to remove === ");
        	 if (entIRI.equals(getBaseIRI(prp)))
        	 {
        		 System.out.println(" > " + prp.getIRI().toString());
        		 prp.accept(remover);
        	 }	 
         }
         
         mng.applyChanges(remover.getChanges());         
         // At this point, if we wanted to reuse the entity remover, we would have to reset it
         remover.reset();         
	}

	/**
	 * Удаляет литеральные свойства из онтологии по их базовому IRI. IRI указывается без # на конце.
	 * @param entIRI
	 */
	public void removeDtpPropertiesByIRI(String entIRI)
	{
		 // создаем ремовер
		 OWLEntityRemover remover = new OWLEntityRemover(mng, Collections.singleton(ontInMem));
         //System.out.println("Number of individuals: " + ontInMem.getIndividualsInSignature().size());
    	 System.out.println("=== Entity to remove === ");   
         // отправляем каждый элемент онтологии на ремовер
         for (OWLDataProperty prp : ontInMem.getDataPropertiesInSignature())
         {
   //     	 System.out.println("=== Entity to remove === ");
        	 if (entIRI.equals(getBaseIRI(prp)))
        	 {
        		 System.out.println(" > " + prp.getIRI().toString());
        		 prp.accept(remover);
        	 }	 
         }
         
         mng.applyChanges(remover.getChanges());         
         // At this point, if we wanted to reuse the entity remover, we would have to reset it
         remover.reset();         
	}
	
	/**
	 * Удаляет экземпляры из онтологии по их базовому IRI. IRI указывается без # на конце.
	 * @param entIRI
	 */
	public void removeIndividualsByIRI(String entIRI)
	{
		 // создаем ремовер
		 OWLEntityRemover remover = new OWLEntityRemover(mng, Collections.singleton(ontInMem));
         //System.out.println("Number of individuals: " + ontInMem.getIndividualsInSignature().size());
    	 System.out.println("=== Entity to remove === ");        
         // отправляем каждый элемент онтологии на ремовер
         for (OWLNamedIndividual ind : ontInMem.getIndividualsInSignature())
         {

        	 if (entIRI.equals(getBaseIRI(ind)))
        	 {
        		 System.out.println(" > " + ind.getIRI().toString());
        		 ind.accept(remover);
        	 }	 
         }
         
         mng.applyChanges(remover.getChanges());         
         // At this point, if we wanted to reuse the entity remover, we would have to reset it
         remover.reset();         
	}
	
	/**
	 * Удаляет экземпляры, классы, свойства из онтологии по их базовому IRI. 
	 * IRI указывается без # на конце.
	 * @param entIRI
	 */
	public void removeEntitiesByIRI(String entIRI)
	{
		removeClassesByIRI(entIRI);
		removeDtpPropertiesByIRI(entIRI);		
		removeObjPropertiesByIRI(entIRI);
		removeIndividualsByIRI(entIRI);
	}


	
	/**
	 * Возвращает базовый IRI элемента онтологии.
	 * @param OWL entity
	 * @return Base IRI
	 */
	public static String getBaseIRI(OWLEntity ent)
	{		
		String strIRI = ent.getIRI().toString();
		// Базовый IRI		
		//System.out.println(" > " + strIRI.substring(0,strIRI.indexOf("#")));
		return strIRI.substring(0,strIRI.indexOf("#"));		
	}

	/**
	 * Возвращает экземпляры класса, непосредственные, а также выведенные.
	 * Метод использует резанер. 
	 * @param clsIRI
	 * @return
	 */
	public Set<OWLNamedIndividual> getIndividualOfClass(IRI clsIRI)
	{
		OWLClass cls = df.getOWLClass(clsIRI);
		//OWLClass cls = df.getOWLClass(IRI.create(ConstantsOntAPI.OWL_THING));
		
		Set<OWLNamedIndividual> indSet = null;
		
		try
		{
			indSet = reas.getInstances(cls,false).getFlattened();

		} catch (Exception e)
		{
			log.error("!! Error of getting individuals of class: <{}>", getShortIRI(clsIRI));
			log.error("!! Reasoner: <{}>", this.reas);
		}
		
		return indSet;
	}
	
	/**
	 * Возвращает пары "IRIсвойство - IRIэкземпляр" связанных с указанным, где указанный выступает
	 * как объект (значение свойства).
	 * @param indiv
	 * @return возвращает массив массивов (двумерных массив) - каждая строка - "свойство - экземпляр". 
	 */
	public ArrayList<ArrayList<IRI>> getLinkedSubjectIndividuals(OWLNamedIndividual indivObj)
	{
		IRI objIRI,subIRI;
		//HashMap<IRI, IRI> linkedIndMap = new HashMap<IRI, IRI>();
		ArrayList<ArrayList<IRI>> linkedInd = new ArrayList<ArrayList<IRI>>(); 
		
		for (OWLObjectPropertyAssertionAxiom ax : 
			ontInMem.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION))
		{
			/*
			System.out.println("IRI:" + ax.getObject().asOWLNamedIndividual().getIRI() +" == "
					+ax.getSubject().asOWLNamedIndividual().getIRI());
			*/
			objIRI = ax.getObject().asOWLNamedIndividual().getIRI();
			subIRI = ax.getSubject().asOWLNamedIndividual().getIRI();
			
			
			if ( objIRI.equals(indivObj.getIRI()) )
			{
				
				
				ArrayList<IRI> couple = new ArrayList<IRI>();
				couple.add(getObjProperty(ax).getIRI());
				couple.add(subIRI);				
				linkedInd.add(couple);
				
				//linkedIndMap.put(getObjProperty(ax).getIRI(), subIRI);
			}
		}	
		return linkedInd;
	}
	


	/**
	 * Возвращает пары "свойство - экземпляр" связанных с указанным, где указанный выступает
	 * как субъект (носитель свойства). 
	 * @param indiv
	 * @return возвращает массив массивов (двумерных массив) - каждая строка - "свойство - экземпляр".
	 */
	public ArrayList<ArrayList<IRI>> getLinkedObjectIndividuals(OWLNamedIndividual indivSub)
	{
		IRI objIRI,subIRI;
		//HashMap<IRI, IRI> linkedIndMap = new HashMap<IRI, IRI>();
		ArrayList<ArrayList<IRI>> linkedInd = new ArrayList<ArrayList<IRI>>();
		
		for (OWLObjectPropertyAssertionAxiom ax : 
			ontInMem.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION))
		{
			/*
			System.out.println("IRI:" + ax.getObject().asOWLNamedIndividual().getIRI() +" == "
					+ax.getSubject().asOWLNamedIndividual().getIRI());
			*/
			objIRI = ax.getObject().asOWLNamedIndividual().getIRI();
			subIRI = ax.getSubject().asOWLNamedIndividual().getIRI();
			
			if ( subIRI.equals(indivSub.getIRI())  )
			{
				
				//System.out.println("Add to map:" + ax);
				if (DEBUG>95)
					System.out.println("  Add to map of Subject: "+ indivSub  +
										"\n    Property: " +getObjProperty(ax).getIRI() + 
										"\n    ObjIndiv: " +objIRI);
				// || 				 isEqualIRI(subIRI, indiv.getIRI())
					ArrayList<IRI> couple = new ArrayList<IRI>();
					couple.add(getObjProperty(ax).getIRI());
					couple.add(objIRI);				
					linkedInd.add(couple);
					
				//linkedIndMap.put(getObjProperty(ax).getIRI(), objIRI);
			}
		}	
		return linkedInd;
	}
	

	
	/**
	 * Возвращает одно(первое) объектное свойство их аксиомы. Преполагается, что в аксиоме только 
	 * одно объектное свойство.
	 * @param objPrpAx
	 * @return
	 */
	public OWLObjectProperty getObjProperty(OWLObjectPropertyAssertionAxiom objPrpAx)
	{
		for (OWLObjectProperty prp : objPrpAx.getObjectPropertiesInSignature())
		{
			if (DEBUG>98) System.out.println("  Get object-prop:" + prp + "\n  from axiome:"
					+objPrpAx);
			
			return prp;			
		}
		
		return null;
	}
	
	

	/**
	 * Возвращает экземпляры связанные с указанным заданным объектным отношением.
	 * Направление отношения не учитывается т.е. указанный объект может быть
	 * субъектом или значением. 
	 * @param indIRI
	 */
	public ArrayList<IRI> getValueOfprp(IRI indIRI, IRI objPrpTrgIRI)
	{
		IRI objIRI, subIRI, objPrpIRI; 
		ArrayList<IRI> lst = new ArrayList<IRI>();
		
		for (OWLObjectPropertyAssertionAxiom ax : 
			ontInMem.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION))
		{
			objIRI = ax.getObject().asOWLNamedIndividual().getIRI();
			subIRI = ax.getSubject().asOWLNamedIndividual().getIRI();
			objPrpIRI = getObjProperty(ax).getIRI();
			
			if ( objPrpTrgIRI.equals(objPrpIRI) )
			{
				if ( objIRI.equals(indIRI) )
				{
//					log.debug(" Linked ind with: "+ indIRI  +
					lst.add(subIRI);
				}		
				else 
				if ( subIRI.equals(indIRI))
				{
						lst.add(objIRI);
				}
/*				
				log.debug(" Linked ind with: "+ indIRI  +
										"\n    Property: " +getObjProperty(ax).getIRI() + 
										"\n    ObjIndiv: " +objIRI);
										*/
			}
		}	
		return lst;		
	}
	
	/**
  	 * Возвращает IRI экземпляров, связанных с указанным заданным объектным отношением.
	 * С учетом направления отношения т.е. указанный объект может быть
	 * субъектом или значением. 
	 * @param indIRI
	 * @param objPrpTrgIRI
	 * @param whereIsSubject	true - те где указанный является субъектном,
	 * 							false - те где указанный является объектом.
	 * @return
	 */
	public ArrayList<IRI> getValueOfprp(IRI indIRI, IRI objPrpTrgIRI, Boolean whereIsSubject)
	{
		IRI objIRI, subIRI, objPrpIRI; 
		ArrayList<IRI> lst = new ArrayList<IRI>();
		
		for (OWLObjectPropertyAssertionAxiom ax : 
			ontInMem.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION))
		{
			objIRI = ax.getObject().asOWLNamedIndividual().getIRI();
			subIRI = ax.getSubject().asOWLNamedIndividual().getIRI();
			objPrpIRI = getObjProperty(ax).getIRI();
			
			if ( objPrpTrgIRI.equals(objPrpIRI) )
			{
				if (whereIsSubject & subIRI.equals(indIRI))
				{
						lst.add(objIRI);
						log.debug(" Linked ind with: "+ indIRI  +
												"\n    Property: " +getObjProperty(ax).getIRI() + 
												"\n    ObjIndiv: " +objIRI);
						
				}		
				else 
				if (!whereIsSubject &  objIRI.equals(indIRI))
				{
						lst.add(subIRI);
						log.debug(" Linked ind with: "+ indIRI  +
												"\n    Property: " +getObjProperty(ax).getIRI() + 
												"\n    ObjIndiv: " +subIRI);
				}

			}
		}	
		return lst;		
	}
	
	/**
  	 * Возвращает экземпляры связанные с указанным заданным объектным отношением.
	 * С учетом направления отношения т.е. указанный объект может быть
	 * субъектом или значением. 
	 * @param ind
	 * @param objPrpTrg
	 * @param whereIsSubject	true - те где указанный является субъектном,
	 * 							false - те где указанный является объектом.
	 * @return
	 */
	public ArrayList<OWLNamedIndividual> getValueOfprp(OWLNamedIndividual ind, OWLObjectProperty objPrpTrg, Boolean whereIsSubject)
	{
		OWLNamedIndividual obj, sub; 
		OWLObjectProperty objPrp; 
		ArrayList<OWLNamedIndividual> lst = new ArrayList<OWLNamedIndividual>();
		
		for (OWLObjectPropertyAssertionAxiom ax : 
			ontInMem.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION))
		{
			obj = ax.getObject().asOWLNamedIndividual();
			sub = ax.getSubject().asOWLNamedIndividual();
			objPrp = getObjProperty(ax);
			
			if ( objPrpTrg.equals(objPrp) )
			{
				if (whereIsSubject & sub.equals(ind))
				{
						lst.add(obj);
						log.debug(" Linked ind with: "+ ind  +
												"\n    Property: " +getObjProperty(ax).getIRI() + 
												"\n    ObjIndiv: " +obj);
						
				}		
				else 
				if (!whereIsSubject &  obj.equals(ind))
				{
						lst.add(sub);
						log.debug(" Linked ind with: "+ ind  +
												"\n    Property: " +getObjProperty(ax).getIRI() + 
												"\n    ObjIndiv: " +obj);
				}
			}
		}	
		return lst;		
	}
	

	/**
	 * Возвращает одно значение объектного свойтсва. Следует применять,
	 * когда не предполагается наличие других экземпляров-значений.
	 * @param ind
	 * @param objPrpTrg
	 * @return
	 */
	public OWLNamedIndividual getValueOfprp(OWLNamedIndividual ind, OWLObjectProperty objPrpTrg)
	{
		OWLNamedIndividual valInd=null;
		try {
			valInd = this.getValueOfprp(ind, objPrpTrg, true).get(0);
		} catch (Exception e) {
			log.error("!! Error during getting value (individual):"+
					"\n!! Subject/object ind: " + this.getShortIRI(ind) +
					"\n!! Property: " + this.getShortIRI(objPrpTrg));
		}
		return valInd;
	}

	/**
  	 * Возвращает экземпляры и отношения, через которые они связанные с указанным.
	 * С учетом направления отношения т.е. указанный объект может быть
	 * субъектом или значением. 
	 * @param indIRI
	 * @param whereIsSubject	true - те где указанный является субъектном,
	 * 							false - те где указанный является объектом.
	 * @return набор массивов-троек: субъект - свойство - объект
	 */
	
	public HashSet<ArrayList<IRI>> getRelatedPrpAndInd(IRI indIRI, Boolean whereIsSubject)
	{
		IRI objIRI, subIRI, objPrpIRI;
		HashSet<ArrayList<IRI>> tripleSet = new HashSet<ArrayList<IRI>>();
		
		for (OWLObjectPropertyAssertionAxiom ax : 
			ontInMem.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION))
		{
			ArrayList<IRI> triple = new ArrayList<IRI>();

			objIRI = ax.getObject().asOWLNamedIndividual().getIRI();
			subIRI = ax.getSubject().asOWLNamedIndividual().getIRI();
			objPrpIRI = getObjProperty(ax).getIRI();

			
			
			if (whereIsSubject & subIRI.equals(indIRI))
			{
					triple.add(indIRI); //Subject
					triple.add(objPrpIRI); //Prop
					triple.add(objIRI); //Object
					tripleSet.add(triple);
			}		
			else 
			if (!whereIsSubject &  objIRI.equals(indIRI))
				{
					triple.add(subIRI); //Subject
					triple.add(objPrpIRI); //Prop
					triple.add(indIRI); //Object
					tripleSet.add(triple);
				}
			
			
		}	
		return tripleSet;		
	}


	/**
  	 * Возвращает экземпляры и отношения, через которые они связанные с указанным.
	 * С учетом направления отношения т.е. указанный объект может быть
	 * субъектом или значением. 
	 * @param indIRI
	 * @param whereIsSubject	true - те где указанный является субъектном,
	 * 							false - те где указанный является объектом.
	 * @param filteredProperties массив IRI свойств, тройки с которыми не нужно включать в результаты
	 * @return набор массивов-троек: субъект - свойство - объект
	 */
	public HashSet<ArrayList<IRI>> getRelatedPrpAndInd(IRI indIRI, Boolean whereIsSubject, 
			HashSet<IRI> filteredProperties)
	{
		IRI objIRI, subIRI, objPrpIRI;
		HashSet<ArrayList<IRI>> tripleSet = new HashSet<ArrayList<IRI>>();
		
		log.info("Collect triple for <"+indIRI.getFragment()+">:");
		
		for (OWLObjectPropertyAssertionAxiom ax : 
			ontInMem.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION))
		{
			ArrayList<IRI> triple = new ArrayList<IRI>();

			objIRI = ax.getObject().asOWLNamedIndividual().getIRI();
			subIRI = ax.getSubject().asOWLNamedIndividual().getIRI();
			objPrpIRI = getObjProperty(ax).getIRI();
			
			if (whereIsSubject && subIRI.equals(indIRI) && !filteredProperties.contains(objPrpIRI))
			{
					log.info(" ["+indIRI.getFragment()+" --"+objPrpIRI.getFragment()+"--> "+objIRI.getFragment()+"]");
					triple.add(indIRI); //Subject
					triple.add(objPrpIRI); //Prop
					triple.add(objIRI); //Object
					tripleSet.add(triple);
			}		
			else 
			if (!whereIsSubject &&  objIRI.equals(indIRI) && !filteredProperties.contains(objPrpIRI))
				{
					log.info(" ["+subIRI.getFragment()+" --"+objPrpIRI.getFragment()+"--> "+indIRI.getFragment()+"]");
					triple.add(subIRI); //Subject
					triple.add(objPrpIRI); //Prop
					triple.add(indIRI); //Object
					tripleSet.add(triple);
				}
			
			
		}	
		return tripleSet;		
	}

	
	/**
	 * Возвращает которкое имя элемента из его IRI. Например:
	 * из http://www.iimm.ru/ont-library/network-device-ontology.owl#Repeater
	 * вернет Repeater
	 * @param ent
	 * @return которкое имя
	 * TODO вынести метод в специальный класс
	 */
	public static String getName(OWLEntity ent)
	{
		String fIRI = ent.getIRI().toString();
		int stopInd = fIRI.indexOf('#');
		fIRI = fIRI.substring(stopInd +1);
		return fIRI;
		
	}
	
	/**
	 * Добавляет аксиомы из импортируемых онтологий главной онтологии в нее
	 * @param mainOnt основная онтология, импортирующая другие онстологии
	 * @return пополненную основную онтологию
	 */
	public OWLOntology mergeImportedOntologies(OWLOntology mainOnt)
	{
		System.out.println("   ===mergeImportedOntologies==============");
		//Объединяем импортированные онтологии вместе с основной в одной модели
			Iterator<OWLOntology> importedOntIter = mainOnt.getImportsClosure().iterator();			
			
			if (mainOnt.getImportsClosure().size()==1)
				System.out.println("   No imported ontologies - nothing to merge!");
			//берем id основной онтологии
			OWLOntologyID idMain = mainOnt.getOntologyID();

			while (importedOntIter.hasNext())		
			{				
				OWLOntology importedOnt = (OWLOntology) importedOntIter.next();
				System.out.println("   getOntologyID:"+importedOnt.getOntologyID());
				if ( (idMain.compareTo(importedOnt.getOntologyID())) != 0 )
				{				
			
				System.out.println("   Merge main ont with ont: "+ importedOnt);
				mergeOntology(mainOnt, importedOnt);
				}
			}
		System.out.println("   ===END-mergeImportedOntologies==============");	
		return mainOnt;	
	} 
	/**
	 * Вызвращает IRI первой сущности, имеющий аннотационное свойство с некоторым значением.
	 * Т.е. если есть еще сущности, имеющие аннотационное свойство с некоторым значением, они не рассматриваются.
	 * @param requiredAnnotPropertyIRI IRI аннотационного свойства, значение которого рассматривается
	 * @param requiredAnnotValue желаемое значение аннотационного свойства в виде литерала.
	 * @return
	 */
	public IRI getEntityByAnnotationValue(IRI requiredAnnotPropertyIRI, OWLLiteral requiredAnnotValue)
	{
		IRI entIRI=null;
		
		
		for (OWLAnnotationAssertionAxiom annotAx : 
			ontInMem.getAxioms(AxiomType.ANNOTATION_ASSERTION))
		{//for-----------------------------------------------------------
			IRI annotPropertyIRI = annotAx.getProperty().getIRI();
	/*
			System.out.println("----------------");
			System.out.println("Subject:"+annotAx.getSubject());
			System.out.println("Property:"+annotAx.getProperty());
			System.out.println("Value:"+annotAx.getValue());
			System.out.println("Desired propr:"+requiredAnnotPropertyIRI);
			
			System.out.println("Examine value:"+valLiteral.toString());

			System.out.println("IS EQ:"+annotPropertyIRI+"<>"+requiredAnnotPropertyIRI+
					"\n"+isEqualIRI(annotPropertyIRI, requiredAnnotPropertyIRI));
	*/
			/* Находим свойство, значение которого надо сравнить */
			if ( annotPropertyIRI.equals(requiredAnnotPropertyIRI) )
			{
				/* Берем значение из найденного свойства как литерал */
				OWLLiteral valLiteral = (OWLLiteral) annotAx.getValue();
				
//-						convertAnnotationValueToTypedLiteral(annotAx.getValue());
	/*			
				System.out.println("Value:"+annotAx.getValue());
				System.out.println("Desired value:"+requiredAnnotValue.toString());
	*/			
				/* Сравниваем литерал значение с требуемым */
				if (valLiteral.equals(requiredAnnotValue))
				{
					OWLAnnotationSubject sub =	annotAx.getSubject();
					try
					{
						entIRI = (IRI) sub;
						return entIRI;
					}
					catch (Exception e)
					{
						return null;
					}
				}
			}
		}//for------------------------------------------------------------
		return entIRI;
	}
	
	/**
	 * Сравнивает два строковых литерала, без учета их языка
	 * @param litA
	 * @param litB
	 * @return
	 * @deprecated
	 */
	/*-
	public Boolean isEqualStringLiteralsIgnoreLang(OWLStringLiteral litA, OWLStringLiteral litB)
	{
		//OWLDatatype typeA = litA.getDatatype();
		//OWLDatatype typeB = litB.getDatatype();
		if ( litA.getLiteral().compareToIgnoreCase(litB.getLiteral())==0 ) 
			return true;
		else
			return false;
	}
	-*/

	/**
	 * Сравнивает два строковых литерала, с учетом их языка
	 * @param litA
	 * @param litB
	 * @return
	 * @deprecated
	 */
	/*-
	public Boolean isEqualStringLiterals(OWLStringLiteral litA, OWLStringLiteral litB)
	{
		//OWLDatatype typeA = litA.getDatatype();
		//OWLDatatype typeB = litB.getDatatype();
		if (litA.getLang().compareToIgnoreCase(litB.getLang())!=0) 
			return false;  
		
		if ( litA.getLiteral().compareToIgnoreCase(litB.getLiteral())==0 ) 
			return true;
		else
			return false;
	}
	-*/

	/**
	 * Сравнивает два типизированных литерала, с учетом их типов.
	 * @param litA
	 * @param litB
	 * @return
	 * @deprecated
	 */
	/*-
	public Boolean isEqualTypedLiterals(OWLTypedLiteral litA, OWLTypedLiteral litB)
	{
		//OWLDatatype typeA = litA.getDatatype();
		//OWLDatatype typeB = litB.getDatatype();

		if (!litA.getDatatype().equals(litB.getDatatype())  ) 
			return false;
		
		if ( litA.getLiteral().compareToIgnoreCase(litB.getLiteral())==0 )
			return true;
		else
			return false;
	}
	-*/
	/**
	 * Сравнивает два типизированных литерала 
	 * @param litA
	 * @param litB
	 * @return
	 */
	
	/*
	public Boolean isEqualLiterals(OWLLiteral litA, OWLLiteral litB, boolean ignoreLang, boolean ignoreType)
	{
		return litA.equals(litB);
	}
	
	
	/**
	 * Сравнивает два типизированных литерала 
	 * @param litA
	 * @param litB
	 * @param ignoreLang без учета языков
	 * @param ignoreType без учета их типов
	 * @return
	 */
/*-	
	public Boolean isEqualLiterals(OWLLiteral litA, OWLLiteral litB, boolean ignoreLang, boolean ignoreType)
	{		
		// Если оба - строковые
		if (litA.isOWLStringLiteral() & litB.isOWLStringLiteral() )
		{
			OWLStringLiteral slitA = (OWLStringLiteral) litA;
			OWLStringLiteral slitB = (OWLStringLiteral) litB;
			
			return ignoreLang ? 
					isEqualStringLiteralsIgnoreLang(slitA, slitB) :	isEqualStringLiterals(slitA, slitB);
		}

		// Если оба - типизированные
		if (litA.isOWLTypedLiteral() & litB.isOWLTypedLiteral() )
		{
			OWLTypedLiteral tlitA = (OWLTypedLiteral) litA;
			OWLTypedLiteral tlitB = (OWLTypedLiteral) litB;
			
			return isEqualTypedLiterals(tlitA, tlitB);
		}

		// Один тип-й а другой строковый
		if (litA.isOWLTypedLiteral() && litB.isOWLStringLiteral() )
		{
			OWLTypedLiteral tlitA = (OWLTypedLiteral) litA;
			OWLStringLiteral slitB = (OWLStringLiteral) litB;
		
			if (ignoreType)
			{
				return (tlitA.getLiteral().compareToIgnoreCase(slitB.getLiteral())==0); 
			}
			else
			{
				return isEqualStringAndTypedLiteral(slitB, tlitA);
			}
		}
		
		// ... и наоборот.		
		if (litA.isOWLStringLiteral() && litB.isOWLTypedLiteral() )
		{
			OWLTypedLiteral slitB = (OWLTypedLiteral) litB;
			OWLStringLiteral tlitA = (OWLStringLiteral) litA;
		
			if (ignoreType)
			{
				return (tlitA.getLiteral().compareToIgnoreCase(slitB.getLiteral())==0); 
			}
			else
			{
				return isEqualStringAndTypedLiteral(tlitA, slitB);
			}
		}
		
		return false;
	}
*/	
	/**
	 * Сравнивает типизированный и строковый литералы с учетом типа.
	 * @param slit
	 * @param tlit
	 * @return
	 * @deprecated
	 */
	/*-
	public boolean isEqualStringAndTypedLiteral(OWLStringLiteral slit, OWLTypedLiteral tlit)
	{
		if (tlit.getDatatype().equals(OWL2Datatype.XSD_STRING))
		{
			if (tlit.getLiteral().compareToIgnoreCase(slit.getLiteral())==0)
				return true;
			else
				return false;
		}
			return false;
		
	}
	-*/
	
	/**
	 * Конвертирует строку вида "abc"@langTag^^rdf:PlainLiteral или 
	 * "abc"@langTag или abc@langTag в литерал типа OWL2Datatype.XSD_STRING.
	 * @param str
	 * @return
	 */
	public OWLLiteral convertStringToOWLStringLiteral(String str)	
	{
		String lang="";
		//str=trimmQuotes(str);
		/* 1 - отсекаем тип - в строком литерале он все равно отсутсвуем */
		if (str.indexOf('^')>0) 
			{str = str.substring(0,str.indexOf('^'));}
		
		/* 1 - вырезаем язык из языкового тэга т.е. из @ru - берем ru */
		if (str.indexOf('@')>0)
			{	lang = str.substring(str.indexOf('@')+1, str.length());	
				str = str.substring(0, str.indexOf('@'));}
		
		/* 2 - вырезаем содержимо тэга - строку без кавычек */
		str=trimmQuotes(str);
		
/*		
		System.out.println("-> String:"+str);
		System.out.println("-> Lang:"+lang);
*/
		
		return df.getOWLLiteral(str, OWL2Datatype.XSD_STRING);
	}
	
	/**
	 * Конвертирует IRI в типизированный литерал.
	 * @param str
	 * @return
	 */
	public OWLLiteral convertIRIToOWLLiteral(IRI iri)	
	{
		//OWLDataFactory cc = 
		return df.getOWLLiteral(iri.toString(), OWL2Datatype.XSD_ANY_URI);
	}

	

	/**
	 * Пробует сконвертировать значение аннотационного свойства 
	 * в типизированный литерал
	 * @param val
	 * @return
	 */
	public OWLLiteral convertAnnotationValueToLiteral(OWLAnnotationValue val)
	{
		OWLLiteral lit = null;
		
		try
		{
			lit = (OWLLiteral) val;
		}
		catch (Exception e)
		{
			System.out.println("!!! WARN - convertAnnotationValueToLiteral");
			System.out.println("!!! Cannot convert val:"+val);
		}
		return lit;
	}
	
	/**
	 * Обрезает кавычки у строки.
	 */
	static public String trimmQuotes(String str)
	{
		str=str.trim();
		if (str.startsWith("\"")) str = str.substring(1,str.length());
		if (str.endsWith("\"")) str = str.substring(0,str.length()-1);
		return str;
	}
	
	/**
	 * Для выражения, не включающего аксиом subclass, evuvalent, enumeration,
	 * аналогичное варажение в ДНФ.
	 * @param ax
	 * @return
	 */
	public OWLClassExpression getDNF(OWLClassExpression ax)
	{
		if (!ax.isAnonymous()) return ax;		
		//HashSet<OWLClassExpression> expInDnfSet = new HashSet<OWLClassExpression>();
		ax=ax.getNNF();		
		/* ==== Если выражения - коньюнкция - смотрим есть ли среди ее элементов дизюнкции =================*/		
		if (ax.asConjunctSet().size()>1)
		{
			ArrayList<OWLClassExpression> conjunctInDnfList = new ArrayList<OWLClassExpression>();
			
			//System.out.println("ax.asConjunctSet()");
			/*1. Внутри цикла каждый элемент коньюнкции приводим к ДНФ и заносим в список*/
			for (OWLClassExpression conjunt : ax.asConjunctSet())
			{
				/* Если встретилась дизьюнкция OR - то: */
				if (conjunt.asDisjunctSet().size()>1)
				{
					ax=this.applyDisribytiveLaw(ax);
					//ax=this.getDNF(ax);
					return this.getDNF(ax);
				}
				
				//df.getOWLObjectIntersectionOf(conjunt).
				//System.out.println("  "+conjunt);
				conjunt=this.getDNF(conjunt);
				conjunctInDnfList.add(conjunt);
				
				//this.getDNF(ax);
				//ArrayList<OWLClassExpression> conjunctInDnfList = new ArrayList<OWLClassExpression>();
				
			}
			
			/*2. Создаем новую коньюнкцию (AND) - RС, все элементы которой в ДНФ*/
			OWLClassExpression [] mas = new OWLClassExpression[ax.asConjunctSet().size()];
			OWLClassExpression expInDNF = df.getOWLObjectIntersectionOf(conjunctInDnfList.toArray(mas));
			return expInDNF;
		}
		else
		/* ==== ... дизюнкция (OR) - каждый ее элемент приводим к днф ======================================*/
		if (ax.asDisjunctSet().size()>1)
		{
			ArrayList<OWLClassExpression> disjunctInDnfList = new ArrayList<OWLClassExpression>();
			/*1. Внутри цикла каждый элемент дизьюнкции приводим к ДНФ и заносим в список*/
			//System.out.println("ax.asDisjunctSet()=========================");
			for (OWLClassExpression disjunt : ax.asDisjunctSet())
			{
				disjunt=this.getDNF(disjunt);
				disjunctInDnfList.add(disjunt);
				//this.getDNF(ax);
			}
			
			/*2. Создаем новую дизюнкцию (OR) - RD*/			
			OWLClassExpression [] mas = new OWLClassExpression[disjunctInDnfList.size()];
			OWLClassExpression expInDNF = df.getOWLObjectUnionOf(disjunctInDnfList.toArray(mas));
			return expInDNF;
		}
		else
		/* ==== ... если это не дизюнкция и не коньюнкция - возвращаем без изменений ======================*/
		{
			return ax;
		}
	}
	
	/**
	 * Применение закона дистрибутивности к выражению. 
	 * @param expToApply
	 * @return результат преобразования
	 */
	private OWLClassExpression applyDisribytiveLaw(OWLClassExpression expToApply)
	{
		Set<OWLClassExpression> conjuncSet = expToApply.asConjunctSet();
		Set<OWLClassExpression> disjuncSet = null;
		/* Ищем дизьюнкцию в выражении-коньюнкции*/
		OWLClassExpression disjunct = null;
		for (OWLClassExpression conjunt : conjuncSet)
		{
			if (conjunt.asDisjunctSet().size()>1)
			{
				disjunct = conjunt;
				disjuncSet = disjunct.asDisjunctSet();
				break;
			}	
		}
		
		if (disjunct!=null)
		{
			/* Вырезаем дизьюнкцию из набора коньюнктов аксиомы */
			conjuncSet.remove(disjunct);

			/* Из оставшихся элементов набора создаем коньюнкции (AND) в которые также включаем по одному
			 * элементу вырезанной дизьюнкции. Из полученных коньюнкций создаем дизьюнкцию (их объединение) -
			 * ее и возвращаем.*/

			
			/* Для каждого из оставшихся элементов набора создаем коньюнкции (AND) с каждым
			 * элементом дизьюнкции. Из полученных коньюнкций создаем дизьюнкцию (их объединение) -
			 * ее и возвращаем.*/
			ArrayList<OWLClassExpression > newConjunctionSet = new ArrayList<OWLClassExpression>();			

			for (OWLClassExpression disjunctEl : disjuncSet)
			{
				ArrayList<OWLClassExpression> newConjunctElList = new ArrayList<OWLClassExpression>();
				newConjunctElList.add(disjunctEl);

				for (OWLClassExpression conjuntEl : conjuncSet)
				{ 
					newConjunctElList.add(conjuntEl);
				}
				
				OWLClassExpression [] mas = new OWLClassExpression[newConjunctElList.size()];
				OWLClassExpression newConjunction = df.getOWLObjectIntersectionOf(newConjunctElList.toArray(mas));
				newConjunctionSet.add(newConjunction);

			}			
			OWLClassExpression[] newConjinctionMas = new OWLClassExpression[newConjunctionSet.size()];
			newConjinctionMas = newConjunctionSet.toArray(newConjinctionMas);
			
			OWLClassExpression finalExp = df.getOWLObjectUnionOf(newConjinctionMas);
			return finalExp;

		}
		else
		{
			/* Если не нашли - выходим*/
			System.out.println("!!! WARN - applyDisribytiveLaw - NO disjunction (Union) in ax:" +
						"\n!!! " + expToApply);
			return expToApply;
		}
	}
	
	/**
	 * Определяет левую и правую часть аксиомы тождественности.
	 * @param ax
	 * @return список выраженией в левой и правой части аксиомы
	 */
	public static List<OWLClassExpression> getSidesOfOWLLogicalAxiom(OWLEquivalentClassesAxiom ax)  
			{
				return ax.getClassExpressionsAsList();
			}
	
	/**
	 * Определяет левую и правую часть аксиомы наследования.
	 * @param ax
	 * @return список выраженией в левой и правой части аксиомы
	 */
	public static List<OWLClassExpression> getSidesOfOWLLogicalAxiom(OWLSubClassOfAxiom ax)  
			{
				List<OWLClassExpression> sidesList = new ArrayList<OWLClassExpression>();
				sidesList.add(ax.getSubClass());
				sidesList.add(ax.getSuperClass());
				return sidesList;
			}
	
	/**
	 * Получает инверсивне объектные свойства указанного. Лог. вывода не производит - 
	 * поэтому выводит только непорсресвенно указанные в аксиоме.
	 * @param prp
	 * @return Set<OWLObjectProperty>
	 */
	public ArrayList<OWLObjectProperty> getInverseObjectProperties(OWLObjectProperty prp)
	{
		log.info("== Get inverse properties of <"+getShortIRI(prp.getIRI())+">:");

		ArrayList<OWLObjectProperty> set = new ArrayList<OWLObjectProperty>();
		
		Set<OWLObjectPropertyExpression> inversetSet = prp.getInverses(this.ontInMem);
		for (OWLObjectPropertyExpression prpExp : inversetSet)
		{
			log.info("   Inv. property: " + getShortIRI(prpExp.getNamedProperty().getIRI()));
			set.add(prpExp.getNamedProperty());
			
		}
		
		ArrayList<OWLObjectProperty> subPrpList= new ArrayList<OWLObjectProperty>();
		for (OWLObjectProperty invprp : set) 
		{
			subPrpList.addAll(this.getSubObjectProperties(invprp,false));
			//set.addAll();
		}
		
		set.addAll(subPrpList);

		//if (addPrimaryProperty) set.add(prp);
		/*
		Node<OWLObjectPropertyExpression> nodesPrp =	reas.getInverseObjectProperties(prp);
		

		for (OWLObjectPropertyExpression prpExp : nodesPrp.getEntities())
		{
			log.info("Inverse property:" + getShortIRI(prpExp.getNamedProperty().getIRI()));
		}
		*/
		log.info("== END - Get inverse properties of <"+getShortIRI(prp.getIRI())+">");
		return set;
	}

	/**
	 * Получает объектные подсвойства (непосредственные и опосредованные) указанного. 
	 * @param prp указанное свойство, для которого находятся подствойства.
	 * @param addPrimaryProperty включить в возвращаемый список указанное свойство.
	 * @return
	 */
	public ArrayList<OWLObjectProperty> getSubObjectProperties(OWLObjectProperty prp, boolean addPrimaryProperty)
	{
		log.info("== Get subproperties of <"+getShortIRI(prp.getIRI())+">:");

		ArrayList<OWLObjectProperty> set = new ArrayList<OWLObjectProperty>();
		
		Set<OWLObjectPropertyExpression> nodesPrp =	reas.getSubObjectProperties(prp, false).getFlattened();
		
		OWLObjectProperty tempPrp=null;
		
		for (OWLObjectPropertyExpression prpExp : nodesPrp)
		{		
			tempPrp = prpExp.isAnonymous() ? null : prpExp.asOWLObjectProperty(); 

			if (tempPrp!=null && !tempPrp.getIRI().equals(IRI.create(ConstantsOntAPI.OWL_BOTTOM_OBJ_PROPERTY)))
			{
				log.info("   SubProperty: " + getShortIRI(prpExp.getNamedProperty().getIRI()));
				set.add(prpExp.getNamedProperty());
			}
		}
		
		if (addPrimaryProperty) set.add(prp);
		//set.remove(this.getEntityByIRI(IRI.create(ConstantsOntAPI.OWL_BOTTOM_OBJ_PROPERTY)).asOWLObjectProperty());
		log.info("== END - Get subproperties of <"+getShortIRI(prp.getIRI())+">");
		return set;
	}

	
	/**
	.* Возвращает которкую форму заданного IRI.
	 * @param iri
	 * @return
	 */
	public static String getShortIRI(IRI iri)
	{
		SimpleIRIShortFormProvider prv = new SimpleIRIShortFormProvider();
		return prv.getShortForm(iri);
	}
	
	
	/**
	 * Возвращает которкую форму заданного IRI.
	 * @param ent
	 * @return
	 */
	public static String getShortIRI(OWLEntity ent)
	{
		SimpleIRIShortFormProvider prv = new SimpleIRIShortFormProvider();
		return prv.getShortForm(ent.getIRI());
	}


	/**
	 * Возвращяет классы, которым принадлежит экземпляр
	 * @param ind
	 * @return HashSet<OWLClass>
	 */
	public HashSet<OWLClass> getClassesOfIndividual(OWLNamedIndividual ind) 
	{
		HashSet<OWLClass> clsSet = new HashSet<OWLClass>();
		for (OWLClassAssertionAxiom ax : this.ontInMem.getClassAssertionAxioms(ind)) 
		{
			if (!ax.getClassExpression().isAnonymous()) 
				clsSet.add(ax.getClassExpression().asOWLClass());
		}
		return clsSet;
	}
	
	/**
	 * Возвращает класс онтологии по указанному IRI.
	 * @param classIRI
	 * @return
	 */
	public OWLClass getOWLClassByIRI(String classIRI)
	{
		OWLClass cl = null;
		try
		{
			cl = this.getEntityByIRI(IRI.create(classIRI)).asOWLClass();
		} catch (Exception e)
		{
			log.error("!! Check that OWLclass <{}> exist and is an OWLClass",getShortIRI(IRI.create(classIRI)));
		}
		return cl;
	}

	
}
