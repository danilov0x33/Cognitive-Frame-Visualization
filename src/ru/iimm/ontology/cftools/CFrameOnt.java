/**
 * 
 */
package ru.iimm.ontology.cftools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt;
import ru.iimm.ontology.ontAPI.ConstantsOntAPI;
import ru.iimm.ontology.ontAPI.Ontology;

/**
 * @author Lomov P.A.
 *
 */
public class CFrameOnt extends UserPresenOnt
{
	
	/**
	 * Набор добавленных в ОПП дуг - нужен для 
	 * предотвращения добвления одинаковых дуг.
	 */
	//private HashSet<Branch> addedBranches;
	
	/**
	 * Карта добавленных дуг - нужна для предотвращения добвления одинаковых дуг.
	 * Формат: ключ (дуга) - значение (экземпляр, соответвующей дуге)
	 */
	private HashMap<Branch, OWLNamedIndividual> addedBranchesMap;

	/**
	 * Карта к-фреймов онтологии.
	 * Формат: ключ (понятие онтологии) - значение (список к-фреймов)
	 */
	//TODO надо чтобы карта запонялалсь также при формировании фреймов
	// а не только при загрузке онтологии
	private HashMap<OWLNamedIndividual, ArrayList<CFrame>> cframesMap;
	
	
	private static final Logger log = LoggerFactory.getLogger(CFrameOnt.class);
	
	/**
	 * Создаем <br>пустую</br> ОПП с к-фреймами на основе исходной ОПП.
	 * @param ontUPO
	 */
	protected CFrameOnt(UserPresenOnt ontUPO)
	{
		super();
		this.df=ontUPO.df;
		this.mng=ontUPO.mng;
		this.ontInMem=ontUPO.ontInMem;
		this.reas=ontUPO.reas;
		this.sreas=ontUPO.reas;
		
		this.addedBranchesMap=new HashMap<Branch, OWLNamedIndividual>();
		this.cframesMap = new HashMap<OWLNamedIndividual, ArrayList<CFrame>>();
	}

	/**
	 * Загружает ОПП с к-фреймами.
	 * @param dir
	 * @param fileName
	 * @param mergeImportedOntology
	 */
	public CFrameOnt(String dir, String fileName, Boolean mergeImportedOntology)
	{
		super(dir, fileName, mergeImportedOntology);
		
		
		this.addedBranchesMap=new HashMap<Branch, OWLNamedIndividual>();
		/*Заполняем карту (this.cframesMap) к-фреймами к*/
		this.cframesMap = new HashMap<OWLNamedIndividual, ArrayList<CFrame>>();
		this.loadCFramesMap();
	}

	/**
	 * Добавляет к-фрейм в ОПП - создает в онтологии экземляры, соответвующие к-фрейму
	 * и его компонентам и связывает их.
	 * @param ontUPO
	 * @return IRI добавленного концепта, соответствующего добавленному к-фрейму.
	 */

	public OWLNamedIndividual addCFrame (CFrameImpl fr)
	{
		/* Создаем имя концепта - к-фрейм */		

		String nameInd = this.getNewIRI(Ontology.getShortIRI(fr.cframeOWLClass).toLowerCase()+"-");
		
		//String nameInd = this.getNewIRI(ConstantCFTools.CFRAME_ONT_TEMPL_DEPENDENCY_CFRAME);
		
		/* Добавляем экземпляр-кфрейм к классу CognitiveFrame */
//		OWLClass cfClass = this.getEntityByIRI(
//				IRI.create(ConstantCFTools.CFRAME_ONT_IRI_CFRAME)).asOWLClass();
		OWLNamedIndividual cframeInd = this.addInd(fr.getCFrameOWLClass(), nameInd);
		fr.setCframeInd(cframeInd);
		
		/* Связываем экземпляр к-фрейма с его целевым понятием  */
		OWLObjectProperty hasTargetConceptPrp = this.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_OBJPRP_HAS_TRG_CONCEPT)).asOWLObjectProperty();
		this.setObjectPrpBetween(cframeInd, hasTargetConceptPrp, fr.getTrgConcept());

		/* Создаем в ОПП экземпляр класса Frame-content - содержание фрейма 
		 * и связываем его с экземпляром фрейма отношением frameComponent*/
		OWLNamedIndividual contentInd = this.addCFrameContent(fr.getContent());
		OWLObjectProperty hasFrameComponentPrp = this.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_OBJPRP_HASCONTENT)).asOWLObjectProperty();
		this.setObjectPrpBetween(cframeInd, hasFrameComponentPrp, contentInd);
		
		/* Создаем в ОПП экземпляр класса VisualisWay -  
		 * и связываем его с экземпляром фрейма отношением frameComponent*/
		OWLNamedIndividual visWayInd = addVisualWay(fr.getVmethod());
		OWLObjectProperty hasVisWay = this.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_OBJPRP_HASVISWAY)).asOWLObjectProperty();
		this.setObjectPrpBetween(cframeInd, hasVisWay, visWayInd);
		
		return cframeInd;
	}
	
	
	private OWLNamedIndividual addCFrameContent(CFrameContent content)
	{	
		/* Создаем имя концепта - содержания */		
		String nameInd = this.getNewIRI(ConstantCFTools.CFRAME_ONT_TEMPL_CFRAME_CONTENT);
		
		/* Добавляем экземпляр-содержание Фрейма к классу CFrame-content */
		OWLClass cfcontentClass = this.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_IRI_CFRAME_CONTENT)).asOWLClass();
		OWLNamedIndividual cfContentInd = this.addInd(cfcontentClass, nameInd);
		
		/*Добавляем в ОПП дуги, входящие в содержимое к-фрейма
		 * + связываем их с экземпляром содержимого*/
		
		this.addContentBranches(content.getBranches(), cfContentInd);
		
		return cfContentInd;
	}
	
	private void addContentBranches(ArrayList<Branch> brList, OWLNamedIndividual cfContentInd)
	{
		OWLNamedIndividual tempBrInd;
		for (Branch br : brList)
		{
			// Если дуги нет в карте добавленных...
			if (!this.addedBranchesMap.containsKey(br)) 
			{
				/*... добавляем экземпляр-дуги в онтологи и карту добавленных дуг */
				tempBrInd = this.addBranch(br);
				this.addedBranchesMap.put(br, tempBrInd);
			}
			else
			{	/* ...если есть, достаем экземпляр оттуда*/
				tempBrInd = this.addedBranchesMap.get(br);
			}	
				
			/*Связываем полученный экз-р дуги с экземпляром содержания фрейма*/
			OWLObjectProperty memberPrp = this.getEntityByIRI(
					IRI.create(ConstantsOntAPI.SKOS_HAS_MEMBER)).asOWLObjectProperty();
			this.setObjectPrpBetween(cfContentInd, memberPrp, tempBrInd);

			
		}
	}
	
	/**
	 * Добавляет экземпляр, соответвующий дуге, в ОПП, а также связывает 
	 * данный экземпляр с компонентами дуги.
	 * @param br дуга
	 * @return OWLNamedIndividual соданный экземпляр дуги.
	 */
	private OWLNamedIndividual addBranch(Branch br)
	{
		/* Создаем IRI экземпляра - дуги */
		String nameInd = this.getNewIRI(ConstantCFTools.CFRAME_ONT_TEMPL_BRANCH);
		
		/* Добавляем экземпляр-дугу в класс Branch */
		OWLClass branchClass = this.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_IRI_BRANCH)).asOWLClass();
		OWLNamedIndividual branchInd = this.addInd(branchClass, nameInd);
		
		/* Связываем экземпляр дуги с субъектом, объектом и отношением: */
		/* - берем свойства из онтологии*/
		OWLObjectProperty subjPrp = this.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_OBJPRP_HAS_SUBJ_CONCEPT)).asOWLObjectProperty();
		OWLObjectProperty objPrp = this.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_OBJPRP_HAS_OBJ_CONCEPT)).asOWLObjectProperty();

		/* - определеяем отношения(свойства) между экземплярами: 
		 * дуга ---имеет_субъект---> экземпляр-субъект
		 * дуга ---имеет_объект---> экземпляр-объект*/
		this.setObjectPrpBetween(branchInd, subjPrp, br.getSubject());
		this.setObjectPrpBetween(branchInd, objPrp, br.getObject());
		
		/* - связываем экземляр дуги с IRI объектного 
		 *	отношения (object property), которое дуга представляет*/	
		OWLDataProperty branchPrp = this.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_DTPPRP_HAS_BRANCH_PRP)).asOWLDataProperty();
		this.setDataPrpValue(branchInd, branchPrp, 
				br.getPrp().getIRI().toString(), OWL2Datatype.XSD_ANY_URI);
		
		/* Добавляем дугу в список добавленых - чтобы не добавить ее повторно*/
		this.addedBranchesMap.put(br, branchInd);
		return branchInd;
	}
	
	private OWLNamedIndividual addVisualWay(CFrameVisualisationMethod vmet) 
	{
		/* Создаем IRI экземпляра - дуги */
		String visWayName = this.getNewIRI(ConstantCFTools.CFRAME_ONT_TEMPL_VISWAY);
		
		/* Добавляем экземпляр-способ_визуализации к классу VisWay */
		OWLClass visWayClass = this.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_IRI_VISWAY)).asOWLClass();
		OWLNamedIndividual visWayInd = this.addInd(visWayClass, visWayName);

		return visWayInd;
	}
	

	/**
	 * Загружает набор к-фреймов для понятия из ОПП. 
	 * @param conceptIRI IRI понятия
	 * @param ontUPO онтология ОПП
	 * @return HashSet<CFrame>
	 */
	ArrayList<CFrame> loadCframeForConcept(IRI conceptIRI)
	{
		ArrayList<CFrame> cframes=null;
		if (this.cframesMap.isEmpty())
		{
			log.warn("! WARNING - theres is no any CFrame in ontology:<{}>",this);
			log.warn("! You should make some CFrames by UPOFrameFormer");
		}
		else
		{
			OWLNamedIndividual concept = this.getEntityByIRI(conceptIRI).asOWLNamedIndividual();
			cframes = this.cframesMap.get(concept);
		}
		
		return cframes;
	}
	/**
	 * Загружает к-фреймы из онтологии в карту.
	 * @return
	 */
	private void loadCFramesMap()
	{
		
		CFrameCreator cfCreator = new CFrameCreator();
		log.info("START LOAGING OF CFRAMES=====================================");
		Set<OWLNamedIndividual> cframeIndividualSet = this.getIndividualOfClass(IRI.create(ConstantCFTools.CFRAME_ONT_IRI_CFRAME));
		
		log.info("QUALITY of CFrames individuals:" + cframeIndividualSet.size());
		for (OWLNamedIndividual	cframeInd : cframeIndividualSet)
		{
			CFrame cframe = cfCreator.loadFrame(cframeInd, this);
			this.addCFrameToMap(cframe);

		}
		log.info("END of LOAGING OF CFRAMES=====================================");
		log.info("Total Cframes quantity: "+this.cframesMap.size() );
		
	}
	
	/**
	 * Добавляем к-фрейм в карту в массив фреймов для
	 * некоторого понятия.
	 * @param fr
	 */
	private void addCFrameToMap(CFrame fr)
	{
		/* Смотрим есть ли в карте ключ - целевое понятие указанного к-фрейма, то...*/
		if (this.cframesMap.containsKey(fr.getTrgConcept()))
		{	/*... если есть - берем соответвующий ему список фреймов и добавляем туда */
			ArrayList<CFrame> cframes = this.cframesMap.get(fr.getTrgConcept());
			cframes.add(fr);
		}
		else
		{	/*... если нет - создаем список фреймов и добавляем туда и далее в карту */
			ArrayList<CFrame> cframes = new ArrayList<CFrame>();
			cframes.add(fr);
			this.cframesMap.put(fr.getTrgConcept(), cframes);
		}
	}
	
	
	public ArrayList<CFrame> getCframesForConcepts(OWLNamedIndividual con)
	{
		return this.cframesMap.get(con);
	}
	
	public ArrayList<CFrame> getCframesForConcepts(IRI conIRI)
	{
		OWLEntity ent = this.getEntityByIRI(conIRI);
		return  ent.isOWLNamedIndividual() ? this.cframesMap.get(ent.asOWLNamedIndividual()) : null; 
		
	}
	
	public ArrayList<CFrame> getCframes()
	{		
		ArrayList<CFrame> rezList = new ArrayList<CFrame>();
		for (ArrayList<CFrame> cframesList : this.cframesMap.values())
		{
			rezList.addAll(cframesList);
		}
		return rezList; 
	}


}
