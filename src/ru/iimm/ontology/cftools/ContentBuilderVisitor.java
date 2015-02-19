/**
 * 
 */
package ru.iimm.ontology.cftools;

import org.omg.PortableServer.ForwardRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;
import ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt;
import ru.iimm.ontology.ontAPI.ConstantsOntAPI;
import ru.iimm.ontology.ontAPI.Ontology;
import ru.iimm.ontology.ontAPI.SkosOnt;




/**
 * Выбирает из ОПП понятия и отношения, которые 
 * будут отображаться во к-фрейме.
 * @author Lomov P. A.
 *
 */

public class ContentBuilderVisitor implements CFrameVisitor
{
	
	protected ContentBuilderVisitor(UserPresenOnt ontUPO)
	{
		super();
		this.ontUPO = ontUPO;
	}

	/**
	 * Содержимое к-фрейма, формируемое посетителем.
	 */
	private CFrameContent content;
	
	
	/**
	 * ОПП, на основе которой формируется содержание к-фреймов.
	 * */
	private UserPresenOnt ontUPO;
	
	
	private static final Logger log = LoggerFactory.getLogger(ContentBuilderVisitor.class);
	
	@Override
	public void visitPatromomyFrame(PartonomyCFrame pframe)
	{
		int maxConceptsQuality = 7;
		
		this.content=new CFrameContent(pframe.trgConcept);
		/* Для отношения получаем его подотношения, а также обратные отношения*/
		//TODO Надо где-то хранить/получать конкретныей IRI общих отношений для разных онтологий

		OWLObjectProperty hasPart = pframe.ontCFR.getEntityByIRI(
				IRI.create("http://www.loa-cnr.it/ontologies/DOLCE-Lite.owl#part")).asOWLObjectProperty();
		
		/* Формируем 0-окружение, включающее только целевое понятие */
		ConceptLocation nullLoc = new ConceptLocation(pframe.trgConcept, pframe.ontCFR, hasPart);
		
		/* Формируем основу кфрейма из окрестностей*/
		Boolean contentIsNotFull = false;
			
		ConceptLocation floc = nullLoc.getFrontLocation();
		ConceptLocation bloc = nullLoc.getBackLocation();
		
		//Удалить из окрестностей ненужное
		floc = this.getFilteredLocation(this.content, floc, true, true, true);
		bloc = this.getFilteredLocation(this.content, bloc, true, true, true);
		this.content.addLocation(floc, maxConceptsQuality*100);
		this.content.addLocation(bloc, maxConceptsQuality*100);
		
		
		/*------------*/
		ConceptLocation backFloc = floc.getBackLocation();
		if (!backFloc.isEmpty())
		{
			backFloc = this.getFilteredLocation(this.content, backFloc, true, true, true);
			this.content.addLocation(backFloc, maxConceptsQuality*100);
			
		}

		ConceptLocation frontBloc = bloc.getFrontLocation();
		if (!frontBloc.isEmpty())
		{			
			frontBloc =this.getFilteredLocation(this.content, frontBloc, true, true, true);
			this.content.addLocation(frontBloc,maxConceptsQuality*100);

		}
			
		/* Если после формирования содержимое не заполнено - расширяем основание 
		 * до заполнения содержимого */
		log.info("--- Base of CFrame is build --------");
		Boolean isFilled=this.content.isFilled(7);
		while (!isFilled & (!floc.isEmpty() | !bloc.isEmpty()) )			
		{
			log.info("Concept quantity in content: "+this.content.getConcepts().size());	
			
			floc = floc.getFrontLocation();
			floc = this.getFilteredLocation(this.content, floc, true, true, true);
			if (!floc.isEmpty()) isFilled=this.content.addLocation(floc, maxConceptsQuality);
			if (isFilled) break;
			
			bloc = bloc.getBackLocation();
			bloc = this.getFilteredLocation(this.content, bloc, true, true, true);						
			if (!bloc.isEmpty()) isFilled=this.content.addLocation(bloc, maxConceptsQuality);
			if (isFilled) break;
		
			backFloc = floc.getBackLocation();
			backFloc = this.getFilteredLocation(this.content, backFloc, true, true, true);			
			if (!backFloc.isEmpty()) isFilled=this.content.addLocation(backFloc, maxConceptsQuality);
			if (isFilled) break;

			frontBloc = bloc.getFrontLocation();
			frontBloc = this.getFilteredLocation(this.content, frontBloc, true, true, true);			
			if (!frontBloc.isEmpty()) isFilled=this.content.addLocation(frontBloc, maxConceptsQuality);
			if (isFilled) break;
		}

		log.info("Content concept  quality: {} ", this.content.getConcepts().size());
		log.info("Content branches quality: {} ", this.content.getBranchQuality());
	}

	@Override
	public void visitDependencyFrame(DependencyCFrame dframe)
	{			
		int maxConceptsQuality = 7;
		this.content=new CFrameContent(dframe.trgConcept);		
			
		OWLObjectProperty dependedFrom = dframe.ontCFR.getEntityByIRI(
				IRI.create("http://www.loa-cnr.it/ontologies/DOLCE-Lite.owl#generic-dependent")).asOWLObjectProperty();
		
		/* Формируем 0-окружение, включающее только целевое понятие */
		ConceptLocation nullLoc = new ConceptLocation(dframe.trgConcept, dframe.ontCFR, dependedFrom);
		
		/* Формируем основу кфрейма из окрестностей*/
		Boolean contentIsNotFull = false;
			
		ConceptLocation floc = nullLoc.getFrontLocation();
		ConceptLocation bloc = nullLoc.getBackLocation();
		
		/* Удалить из окрестностей ненужное */
		floc=this.getFilteredLocation(this.content, floc, true, true, true);
		bloc=this.getFilteredLocation(this.content, bloc, true, true, true);
				
		this.content.addLocation(floc, maxConceptsQuality*100);
		this.content.addLocation(bloc, maxConceptsQuality*100);
		
		/*------------*/
		ConceptLocation backFloc = floc.getBackLocation();
		if (!backFloc.isEmpty())
		{
			backFloc=this.getFilteredLocation(this.content, backFloc, true, true, true);
			this.content.addLocation(backFloc, maxConceptsQuality*100);
		}

		ConceptLocation frontBloc = bloc.getFrontLocation();
		if (!frontBloc.isEmpty())
		{
			frontBloc=this.getFilteredLocation(this.content, frontBloc, true, true, true);
			this.content.addLocation(frontBloc,maxConceptsQuality*100);
		}
			
		/* Если после формирования содержимое не заполнено - расширяем основание 
		 * до заполнения содержимого */
		log.info("--- Base of CFrame is build --------");
		Boolean isFilled=this.content.isFilled(7);
		while (!isFilled & (!floc.isEmpty() | !bloc.isEmpty()) )			
		{
			log.info("Concept quantity in content: "+this.content.getConcepts().size());	
			
			floc = floc.getFrontLocation();
			floc = this.getFilteredLocation(this.content, floc, true, true, true);
			if (!floc.isEmpty()) isFilled=this.content.addLocation(floc, maxConceptsQuality);
			if (isFilled) break;
			
			bloc = bloc.getBackLocation();
			bloc = this.getFilteredLocation(this.content, bloc, true, true, true);						
			if (!bloc.isEmpty()) isFilled=this.content.addLocation(bloc, maxConceptsQuality);
			if (isFilled) break;
		
			backFloc = floc.getBackLocation();
			backFloc = this.getFilteredLocation(this.content, backFloc, true, true, true);			
			if (!backFloc.isEmpty()) isFilled=this.content.addLocation(backFloc, maxConceptsQuality);
			if (isFilled) break;

			frontBloc = bloc.getFrontLocation();
			frontBloc = this.getFilteredLocation(this.content, frontBloc, true, true, true);			
			if (!frontBloc.isEmpty()) isFilled=this.content.addLocation(frontBloc, maxConceptsQuality);
			if (isFilled) break;
		}

		log.info("Content concept  quality: {} ", this.content.getConcepts().size());
		log.info("Content branches quality: {} ", this.content.getBranchQuality());		
	}

	@Override
	public void visitTaxonomyFrame(TaxonomyCFrame tframe)
	{
		int maxConceptsQuality = 7;
		this.content=new CFrameContent(tframe.trgConcept);		
		
		OWLObjectProperty broader = tframe.ontCFR.getEntityByIRI
				(IRI.create(ConstantsOntAPI.SKOS_BROADER)).asOWLObjectProperty();
		
		ConceptLocation nullLoc = new ConceptLocation(tframe.trgConcept, tframe.ontCFR, broader);
		
		/* Формируем основу кфрейма из окрестностей*/
		ConceptLocation floc = nullLoc.getFrontLocation();
		ConceptLocation bloc = nullLoc.getBackLocation();
		
		/* Удалить из окрестностей ненужное */
		floc =this.getFilteredLocation(this.content, floc, false, false, true);
		bloc =this.getFilteredLocation(this.content, bloc, false, false, true);
		
		this.content.addLocation(floc, maxConceptsQuality*100);
		this.content.addLocation(bloc, maxConceptsQuality*100);
		
		/*------------*/
		ConceptLocation backFloc = floc.getBackLocation();
		if (!backFloc.isEmpty())
		{
			backFloc =this.getFilteredLocation(this.content, backFloc, false, false, true);			
			this.content.addLocation(backFloc, maxConceptsQuality*100);

		}

		ConceptLocation frontBloc = bloc.getFrontLocation();
		if (!frontBloc.isEmpty())
		{
			frontBloc = this.getFilteredLocation(this.content, frontBloc, false, false, true);
			this.content.addLocation(frontBloc,maxConceptsQuality*100);
		}
		

		
		/* Если после формирования содержимое не заполнено - расширяем основание 
		 * до заполнения содержимого */
		//while (!this.content.isFilled(maxConceptsQuality) & (!floc.isEmpty() | !bloc.isEmpty()) )
		log.info("--- Base of CFrame is build --------");
		Boolean isFilled=this.content.isFilled(7);
		while (!isFilled & (!floc.isEmpty() | !bloc.isEmpty()) )			
		{
			log.info("Concept quantity in content: "+this.content.getConcepts().size());	
			
			floc = floc.getFrontLocation();
			floc = this.getFilteredLocation(this.content, floc, false, false, true);
			if (!floc.isEmpty()) isFilled=this.content.addLocation(floc, maxConceptsQuality);
			if (isFilled) break;
			
			bloc = bloc.getBackLocation();
			bloc = this.getFilteredLocation(this.content, bloc, false, false, true);						
			if (!bloc.isEmpty()) isFilled=this.content.addLocation(bloc, maxConceptsQuality);
			if (isFilled) break;
		
			backFloc = floc.getBackLocation();
			backFloc = this.getFilteredLocation(this.content, backFloc, false, false, true);			
			if (!backFloc.isEmpty()) isFilled=this.content.addLocation(backFloc, maxConceptsQuality);
			if (isFilled) break;

			frontBloc = bloc.getFrontLocation();
			frontBloc =this.getFilteredLocation(this.content, frontBloc, false, false, true);			
			if (!frontBloc.isEmpty()) isFilled=this.content.addLocation(frontBloc, maxConceptsQuality);
			if (isFilled) break;
		}

		log.info("Content concept  quality: {} ", this.content.getConcepts().size());
		log.info("Content branches quality: {} ", this.content.getBranchQuality());		
	}
	
	@Override
	public void visitSpecialFrame(SpecialCFrame spframe)
	{
		int maxConceptsQuality = 7;
		this.content=new CFrameContent(spframe.trgConcept); //TODO перенести в супер конструктор
		
		OWLObjectProperty related = spframe.ontCFR.getEntityByIRI
				(IRI.create(ConstantsOntAPI.SKOS_RELATED)).asOWLObjectProperty();
		
		/* Добавляем все субаксиомы связанные с целевым концептом в окружение 
		 * + формируем список найденых субаксиом */
		ConceptLocation nullLoc = new ConceptLocation(spframe.trgConcept, spframe.ontCFR, related);
		ConceptLocation floc = nullLoc.getFrontLocation();
		this.content.addLocation(floc, maxConceptsQuality*100);
		
		/* Для каждой субаксиомы, связанной с целевым понятием
		 * собираем ее свойства + связи с другими субаксиомами*/
		for (OWLNamedIndividual sbAx : floc.getConcepts())
		{
			HashSet<OWLNamedIndividual> analysedSbaxSet = new HashSet<>();
			this.content.addBranches(this.getSbaxBranches(sbAx, analysedSbaxSet));
		}
				
		
		/* Задаем списки свойств дуги с которыми не будут рассматриваться - далее, 
		 * т.к. эти дуги есть в составе других типов фреймов  
		 * TODO может надо дополнительно брать обратные и подсвойства*/
		HashSet<IRI> filteredTrgConceptProperties = new HashSet<IRI>();
		filteredTrgConceptProperties.add(IRI.create(ConstantsOntAPI.SKOS_RELATED));
		filteredTrgConceptProperties.add(IRI.create(ConstantsOntAPI.SKOS_NARROWER));
		filteredTrgConceptProperties.add(IRI.create(ConstantsOntAPI.SKOS_BROADER));
		filteredTrgConceptProperties.add(IRI.create("http://www.loa-cnr.it/ontologies/DOLCE-Lite.owl#generic-dependent"));
		filteredTrgConceptProperties.add(IRI.create("http://www.loa-cnr.it/ontologies/DOLCE-Lite.owl#part"));
		
		/*получаем дуги с концептами, привязанными к целевому концепту*/
		HashSet<ArrayList<IRI>> branchesIRI = 
				this.ontUPO.getRelatedPrpAndInd(this.content.getTrgConcept().getIRI(), true, filteredTrgConceptProperties);
		
		
		/* Переписываем концепты существующего окружение во временный 
		 * набор (для устранения ошибки concurrent modification), перебираем его
		 * и добавляем в окружение все связанные с его концептами концепты*/
		
		log.info("Content concept  quality: {} ", this.content.getConcepts().size());
		log.info("Content branches quality: {} ", this.content.getBranchQuality());		
	}


	 
	/**
	 * @return the content
	 */
	public CFrameContent getContent() 
	{
		return this.content;
	}
	
	
	/**
	 * Возвращает набор ребер к n-ой окрестности концепта по указанному онтошению.
	 * @param n номер окрестности
	 * @param trgConcept понятие, для которого определяется окрестность
	 * @param prp отношение
	 * @param ontCFR 
	 * @return ArrayList<Branch> массив ребер, связывающих понятия n-1 окрестности
	 * с понятиями n-ой окрестности.
	 */
	private ArrayList<Branch> getBranchToNlocation(int n, OWLNamedIndividual trgConcept, OWLObjectProperty prp,
			CFrameOnt ontCFR)
	{
		ArrayList<Branch> location = new ArrayList<Branch>();
		
		HashSet<OWLNamedIndividual> allLocation, nLocation, tempLocation; 
		ArrayList<Branch> branchNLocation;
		/* allLocation хранит понятия всех сформированных окрестностей*/
		/* nLocation хранит понятия текущей (сформированной) окрестности*/
		/* в tempLocation добавляются ребра фомируемой n+1 окресности */
		branchNLocation = new ArrayList<Branch>();
		nLocation  = new HashSet<OWLNamedIndividual>();
		allLocation = new HashSet<OWLNamedIndividual>();
		tempLocation = new HashSet<OWLNamedIndividual>();
		
		nLocation.add(trgConcept);
		allLocation.add(trgConcept);
		
		for (int i = 0; i < n; i++) 
		{
			/* Для каждого концепта из текущей окрестности - nLocation... */
			for (Iterator iter = nLocation.iterator(); iter.hasNext();)
			{
				OWLNamedIndividual concept = (OWLNamedIndividual) iter.next();
				branchNLocation.clear();

				/* Получаем массив концептов, связанных с указанным - concept, 
				 * отношением - prp. Перебираем - если концепт не был добавлен на прошлых шагах,
				 * то добавляем его в allLocation и tempLocation + связанную с ним ветку
				 * в branchNLocation.
				 * */
				ArrayList<OWLNamedIndividual> connectedConcepts = ontCFR.getValueOfprp(concept, prp, true);
				for (OWLNamedIndividual conct : connectedConcepts) 
				{
					if (!allLocation.contains(conct))
					{
						allLocation.add(conct);	
						tempLocation.add(conct);
						branchNLocation.add(new Branch(concept, prp, conct));
					}					
				}
			}

			/* Собранные концепты (tempLocation) переписываем в текущую окрестность (nLocation)
			 * и переходим к формированию следующей окрестности или завершаем*/
			nLocation.clear();
			nLocation.addAll(tempLocation);
			tempLocation.clear();
		}
		
	return branchNLocation;
	}
	
	
	/**
	 *  Возвращает окресности понятия до достижении максимума понятий.
	 * @param max максимум элементов для собираемых окружений.
	 * @param trgConcept понятие с которого начинается построение окружений.
	 * @param prp
	 * @param ontCFR
	 * @return
	 */
	private ArrayList<Branch> getMaxlocation(int max, OWLNamedIndividual trgConcept, OWLObjectProperty prp,
			CFrameOnt ontCFR)
	{
		log.info("== Get locations on relation <" + Ontology.getShortIRI(prp.getIRI()) 
				+ "> for <" + Ontology.getShortIRI(trgConcept.getIRI())+">"  );
		//log.info("==Get locations on concept" + trgConcept);
		/* branchNLoc - собранные к текущему шагу ветки 
		 * branchNextLoc - собираемые на текущем шаги ветки*/
		ArrayList<Branch> branchNLoc = new ArrayList<Branch>();
		ArrayList<Branch> branchNextLoc = new ArrayList<Branch>();
		
		HashSet<OWLNamedIndividual> allLocation, nLocation, tempLocation; 
		/* allLocation хранит понятия всех сформированных окрестностей*/
		/* nLocation хранит понятия текущей (сформированной) окрестности*/
		/* в tempLocation добавляются ребра фомируемой n+1 окресности */

		nLocation  = new HashSet<OWLNamedIndividual>();
		allLocation = new HashSet<OWLNamedIndividual>();
		tempLocation = new HashSet<OWLNamedIndividual>();
		
		nLocation.add(trgConcept);
		allLocation.add(trgConcept);
		
		do /*на каждом шаге рассматриваем новую окрестность - до того как соберем максимум элементов*/
		{
			tempLocation.clear();// обнуляем т.к. далее будет собирать ребра новой окресности
			/* Для каждого концепта из текущей окрестности - nLocation... */
			for (Iterator<OWLNamedIndividual> iter = nLocation.iterator(); iter.hasNext();)
			{
				OWLNamedIndividual concept = iter.next();

				/* ...получаем массив концептов, связанных с указанным (concept), 
				 * отношением - prp. Перебираем - если концепт не был добавлен на прошлых шагах,
				 * то добавляем его в allLocation и tempLocation + связанную с ним ветку
				 * в branchNLocation.
				 * */
				//цикл по набору свойств				
				ArrayList<OWLNamedIndividual> connectedConcepts = ontCFR.getValueOfprp(concept, prp, true);
				for (OWLNamedIndividual conct : connectedConcepts) 
				{
					if (!allLocation.contains(conct))
					{
						log.info("   Add concept \"{}\" to location", Ontology.getShortIRI(conct.getIRI()) );
						allLocation.add(conct);	
						tempLocation.add(conct);
						branchNextLoc.add(new Branch(concept, prp, conct));
					}					
				}
			}

			/* Подсчитываем число концептов с учетом полученных на этом шаге (из данной окрестности)
			 * Если их больше максимума - не добавляем то, что получили на данном шаге
			 * - завершаем */
			if (allLocation.size() <= max) 
			{
				branchNLoc.addAll(branchNextLoc);
				branchNextLoc.clear();
			}
			
			/* Собранные концепты (tempLocation) переписываем в текущую окрестность (nLocation)
			 * и переходим к формированию следующей окрестности или завершаем*/
			nLocation.clear();
			nLocation.addAll(tempLocation);
			
		} 
		/*если ничего не собрали, то окресность пустая и следующей не будет - завершаем
		 * если превысили максимум - также завершаем*/
		while ((!tempLocation.isEmpty()) & (allLocation.size() <= max) );
		log.info("== END Get locations rel. <" + Ontology.getShortIRI(prp.getIRI())+ ">, concept <" + 
		Ontology.getShortIRI(trgConcept.getIRI())+">"  );
	return branchNLoc;
	}
	
	/**
	 *  Возвращает окресности понятия до достижении максимума понятий + 
	 *  учитывает "близкие" по сыслу отношения + добавляет понятия как объекты 
	 *  так и субъекты по отношению в целевому.
	 * @param max максимум элементов для собираемых окружений.
	 * @param trgConcept понятие с которого начинается построение окружений.
	 * @param prp
	 * @param ontCFR
	 * @return
	 */
	private ArrayList<Branch> getMaxlocationOnClosestRelation(int max, OWLNamedIndividual trgConcept, 
			ArrayList<OWLObjectProperty> prpList,
			CFrameOnt ontCFR)
	{
		log.info("== Get locations on closest relations");
		
		HashSet<OWLNamedIndividual> allLocation, nLocation, tempLocation; 
		/* allLocation хранит понятия всех сформированных окрестностей*/
		/* nLocation хранит понятия текущей (сформированной, n-й) окрестности*/
		/* в tempLocation добавляются понятия фомируемой n+1 окресности */

		nLocation  = new HashSet<OWLNamedIndividual>();
		allLocation = new HashSet<OWLNamedIndividual>();
		tempLocation = new HashSet<OWLNamedIndividual>();

		/* branchNLoc - собранные к текущему шагу ветки, состоящие из понятий всех окрестностей  
		 * branchNextLoc - собираемые на текущем шаги ветки из формируемой окрестности*/
		ArrayList<Branch> branchNLoc = new ArrayList<Branch>();
		ArrayList<Branch> branchNextLoc = new ArrayList<Branch>();
		
		/*полагаем что в нулевой (текущей) окрестности лежит целевое понятие*/
		nLocation.add(trgConcept);
		allLocation.add(trgConcept);
		
		do /*на каждом шаге рассматриваем новую окрестность - до того как соберем максимум элементов*/
		{
			tempLocation.clear();// обнуляем т.к. далее будет собирать ребра новой окресности
			/* Для каждого концепта из текущей окрестности - nLocation... */
			for (Iterator<OWLNamedIndividual> iter = nLocation.iterator(); iter.hasNext();)
			{
				OWLNamedIndividual concept = iter.next();
				/* ... и для каждого свойства prp из prpList.. */
				for (OWLObjectProperty prp : prpList)
				{
					/*
					 * ... получаем массив концептов, связанных с указанным (concept), 
					 * отношением - prp. Выбираем те, что выступают как объекты по отношению 
					 * Перебираем их - если концепт не был добавлен на прошлых шагах,
					 * то добавляем его в allLocation и tempLocation (формируемую окресность) 
					 * + связанную с ним ветку в branchNLocation.
					 */
					for (Branch br : this.getBranchesToObjectConcept(prp, concept, ontCFR))
					{
						if (!allLocation.contains(br.getObject()))
						{
							log.info("   Add object-concept <"+Ontology.getShortIRI(br.getObject().getIRI())+ "> to location: " + br);
							allLocation.add(br.getObject());
							tempLocation.add(br.getObject());
							branchNextLoc.add(br);							
							
						}
					}
					/* .. теперь тоже самое для концептов-субъектов по отношению...		 */
					for (Branch br : this.getBranchesToSubjectConcept(prp, concept, ontCFR))
					{
						if (!allLocation.contains(br.getSubject()))
						{
							log.info("   Add subject-concept <"+Ontology.getShortIRI(br.getObject().getIRI())+ "> to location: " + br);
							allLocation.add(br.getSubject());
							tempLocation.add(br.getSubject());
							branchNextLoc.add(br);							
							
						}
					}
				}
			}/* Окрестность сформирована */

			/* Подсчитываем число концептов с учетом полученных на этом шаге (из сформированной только, что окрестности)
			 * Если их больше максимума - не добавляем то, что получили на данном шаге
			 * - завершаем (т.к. цикл с постусловием - этот шаг последний)*/
			if (allLocation.size() <= max) 
			{
				branchNLoc.addAll(branchNextLoc);
				branchNextLoc.clear();				
			}

			/* Собранные концепты (tempLocation) переписываем в nLocation (текущую окрестность на следующем шаге)
			 * и переходим к формированию следующей окрестности или завершаем*/
			nLocation.clear();
			nLocation.addAll(tempLocation);
			
		} 
		/*если ничего не собрали, то окресность пустая и следующей не будет - завершаем
		 * если превысили максимум - также завершаем*/
		while ((!tempLocation.isEmpty()) & (allLocation.size() <= max) );
		log.info("   Cframe total quality of concept: "+ ( allLocation.size() - tempLocation.size() ) );
		log.info("== END Get locations rel. + closest relation.");
	return branchNLoc;
	}

	
	/**
	 * FBxFB
	 * @param max максимум элементов для собираемых окружений.
	 * @param trgConcept понятие с которого начинается построение окружений.
	 * @param prp
	 * @param ontCFR
	 * @return
	 */
	private ArrayList<Branch> getZZZZ(int max, OWLNamedIndividual trgConcept, 
			ArrayList<OWLObjectProperty> prpList,
			CFrameOnt ontCFR)
	{
		log.info("== Get FBxFB locations");
		
		HashSet<OWLNamedIndividual> allLocation, nLocation, tempLocation; 
		/* allLocation хранит понятия всех сформированных окрестностей*/
		/* nLocation хранит понятия текущей (сформированной, n-й) окрестности*/
		/* в tempLocation добавляются понятия фомируемой n+1 окресности */

		nLocation  = new HashSet<OWLNamedIndividual>();
		allLocation = new HashSet<OWLNamedIndividual>();
		tempLocation = new HashSet<OWLNamedIndividual>();

		/* branchNLoc - собранные к текущему шагу ветки, состоящие из понятий всех окрестностей  
		 * branchNextLoc - собираемые на текущем шаги ветки из формируемой окрестности*/
		ArrayList<Branch> branchNLoc = new ArrayList<Branch>();
		ArrayList<Branch> branchNextLoc = new ArrayList<Branch>();
		
		/*полагаем что в нулевой (текущей) окрестности лежит целевое понятие*/
		nLocation.add(trgConcept);
		allLocation.add(trgConcept);
		
		do /*на каждом шаге рассматриваем новую окрестность - до того как соберем максимум элементов*/
		{
			tempLocation.clear();// обнуляем т.к. далее будет собирать ребра новой окресности
			/* Для каждого концепта из текущей окрестности - nLocation... */
			for (Iterator<OWLNamedIndividual> iter = nLocation.iterator(); iter.hasNext();)
			{
				OWLNamedIndividual concept = iter.next();
				/* ... и для каждого свойства prp из prpList.. */
				for (OWLObjectProperty prp : prpList)
				{
					/*
					 * ... получаем массив концептов, связанных с указанным (concept), 
					 * отношением - prp. Выбираем те, что выступают как объекты по отношению 
					 * Перебираем их - если концепт не был добавлен на прошлых шагах,
					 * то добавляем его в allLocation и tempLocation (формируемую окресность) 
					 * + связанную с ним ветку в branchNLocation.
					 */
					for (Branch br : this.getBranchesToObjectConcept(prp, concept, ontCFR))
					{
						if (!allLocation.contains(br.getObject()))
						{
							log.info("   Add object-concept <"+Ontology.getShortIRI(br.getObject().getIRI())+ "> to location: " + br);
							allLocation.add(br.getObject());
							tempLocation.add(br.getObject());
							branchNextLoc.add(br);							
							
						}
					}
					
					/* .. теперь тоже самое для концептов-субъектов по отношению...		 */
					for (Branch br : this.getBranchesToSubjectConcept(prp, concept, ontCFR))
					{
						if (!allLocation.contains(br.getSubject()))
						{
							//TODO chesk
							log.info("   Add subject-concept <"+Ontology.getShortIRI(br.getSubject().getIRI())+ "> to location: " + br);
							allLocation.add(br.getSubject());
							tempLocation.add(br.getSubject());
							branchNextLoc.add(br);							
							
						}
					}
				}
			}/* Окрестность сформирована */

			/* Подсчитываем число концептов с учетом полученных на этом шаге (из сформированной только, что окрестности)
			 * Если их больше максимума - не добавляем то, что получили на данном шаге
			 * - завершаем (т.к. цикл с постусловием - этот шаг последний)*/
			if (allLocation.size() <= max) 
			{
				branchNLoc.addAll(branchNextLoc);
				branchNextLoc.clear();				
			}

			/* Собранные концепты (tempLocation) переписываем в nLocation (текущую окрестность на следующем шаге)
			 * и переходим к формированию следующей окрестности или завершаем*/
			nLocation.clear();
			nLocation.addAll(tempLocation);
			
		} 
		/*если ничего не собрали, то окресность пустая и следующей не будет - завершаем
		 * если превысили максимум - также завершаем*/
		while ((!tempLocation.isEmpty()) & (allLocation.size() <= max) );
		log.info("   Cframe total quality of concept: "+ ( allLocation.size() - tempLocation.size() ) );
		log.info("== END Get locations rel. + closest relation.");
	return branchNLoc;
	}

	

	

	/**
	 * Собирает отношения близкие к указанному. Это его subproperty, inverse property.
	 * @param prp
	 * @param ont
	 * @return ArrayList<OWLObjectProperty>
	 */
	private ArrayList<OWLObjectProperty> getClosestRelation(OWLObjectProperty prp, Ontology ont)
	{
		ArrayList<OWLObjectProperty> prpList = new ArrayList<OWLObjectProperty>();
		prpList.addAll(ont.getInverseObjectProperties(prp));
		prpList.addAll(ont.getSubObjectProperties(prp,false));
		prpList.add(prp);
		return prpList;
	}
	
	
	
	private void getBackLocationOfConcept() 
	{
		
	}
	
	
	/**
	 * Формируем фронтальную (по прямым отношениям) окресность указанных понятий, в нее входят дуги вида:
	 * trgConcept --hasPart--> something и (обратные им) something --isPartOf--> trgConcept
	 * @param allCollectedConcepts понятия, которые уже входят в другие окресности (чтобы их повторно не добавлять)
	 * @param nLocationConcepts понятия исходной окресности.
	 * @param prp прямое свойствоб по которому формируется окресность.
	 * @param ontCFR онтология, с которой работает метод.
	 * @return
	 */
	private  ArrayList<Branch> getFrontLocationOfConcept(HashSet<OWLNamedIndividual> allCollectedConcepts, 
			HashSet<OWLNamedIndividual> nLocationConcepts, OWLObjectProperty prp, CFrameOnt ontCFR) 
	{
		ArrayList<Branch> locationBranches = new ArrayList<Branch>();
		/* Для каждого концепта из переданной окрестности... */
		for (OWLNamedIndividual concept : nLocationConcepts) 
		{
			/* .. и указанного свойства и его подсвойств...*/
			for (OWLObjectProperty prpTemp : ontCFR.getSubObjectProperties(prp, true)) 
			{//====
				/* .. и каждой дуги вида conceptp --prpTemp--> something	 */
				for (Branch br : this.getBranchesToObjectConcept(prpTemp, concept, ontCFR))
				{/* если дуга ведет в еще недобавленный концепт - то добавляем ее*/
					if (!allCollectedConcepts.contains(br.getObject()))
					{
						log.info("   Add object-concept <"+Ontology.getShortIRI(br.getObject().getIRI())+ "> to location: " + br);
						allCollectedConcepts.add(br.getObject());
						locationBranches.add(br);							
						
					}
				}
			}//=====
			
			/*.. теперь тоже самое (но наоборот) для обратных свойств*/
			for (OWLObjectProperty prpTemp : ontCFR.getInverseObjectProperties(prp)) 
			{//-----
				/*.. и обратных дуг вида: conceptp <--INVERVEprpTemp-- something 				 */
				for (Branch br : this.getBranchesToSubjectConcept(prpTemp, concept, ontCFR))
				{
					if (!allCollectedConcepts.contains(br.getSubject()))
					{
						log.info("   Add subject-concept <"+Ontology.getShortIRI(br.getSubject().getIRI())+ "> to location: " + br);
						allCollectedConcepts.add(br.getSubject());
						locationBranches.add(br);							
						
					}
				}
			}//-------

		}
		return locationBranches;
	}
	
	/**
	 * Формируем заднюю (по обратным отношениям) окресность указанных понятий, в нее входят дуги вида:
	 * something --hasPart-->trgConcept  и (обратные им) trgConcept --isPartOf--> something  
	 * @param allCollectedConcepts понятия, которые уже входят в другие окресности (чтобы их повторно не добавлять)
	 * @param nLocationConcepts понятия исходной окресности.
	 * @param prp обраное свойство по которому формируется окресность.
	 * @param ontCFR онтология, с которой работает метод.
	 * @return
	 */
	private  ArrayList<Branch> getBackLocationOfConcept(HashSet<OWLNamedIndividual> allCollectedConcepts, 
			HashSet<OWLNamedIndividual> nLocationConcepts, OWLObjectProperty prp, CFrameOnt ontCFR) 
	{
		ArrayList<Branch> locationBranches = new ArrayList<Branch>();
		/* Для каждого концепта из переданной окрестности... */
		for (OWLNamedIndividual concept : nLocationConcepts) 
		{
			/* .. и указанного свойства и его подсвойств...*/
			for (OWLObjectProperty prpTemp : ontCFR.getSubObjectProperties(prp,false)) 
			{//====
				/* .. и каждой дуги вида something --prpTemp--> conceptp	 */
				for (Branch br : this.getBranchesToSubjectConcept(prpTemp, concept, ontCFR))
				{/* если дуга ведет в еще недобавленный концепт - то добавляем ее*/
					if (!allCollectedConcepts.contains(br.getSubject()))
					{
						log.info("   Add subject-concept <"+Ontology.getShortIRI(br.getSubject().getIRI())+ "> to location: " + br);
						allCollectedConcepts.add(br.getSubject());
						locationBranches.add(br);							
						
					}
				}
			}//=====
			
			/*.. теперь тоже самое (но наоборот) для обратных свойств*/
			for (OWLObjectProperty prpTemp : ontCFR.getInverseObjectProperties(prp)) 
			{//-----
				/*.. и обратных дуг вида: something <--INVERVEprpTemp-- conceptp 				 */
				for (Branch br : this.getBranchesToObjectConcept(prpTemp, concept, ontCFR))
				{
					if (!allCollectedConcepts.contains(br.getObject()))
					{
						log.info("   Add object-concept <"+Ontology.getShortIRI(br.getObject().getIRI())+ "> to location: " + br);
						allCollectedConcepts.add(br.getObject());
						locationBranches.add(br);							
					}
				}
			}//-------

		}
		return locationBranches;
	}

	/**
	 * Формирует сначала фронтальную n-ую окрестность, проверяет можно ли формровать далее, если да - 
	 * то формирует n-ю тыловую, провряет... затем n+1 фронтальную и т.д.
	 * @param trgConcept
	 * @param prp
	 * @param ontCFR
	 * @param maxConceptsQuality
	 * @return
	 */
	private ArrayList<Branch> getFrontAndBackLocations(OWLNamedIndividual trgConcept, String prpIRIstr, 
			CFrameOnt ontCFR, int maxConceptsQuality) 
	{
		/* Для получем отношение из онтооогии*/
		OWLObjectProperty broader = ontCFR.getEntityByIRI(IRI.create(prpIRIstr)).asOWLObjectProperty();
		
		/*Получаем понятия из окрестностей по отншению*/
		HashSet<OWLNamedIndividual> allCollectedConcepts = new HashSet<OWLNamedIndividual>();
		HashSet<OWLNamedIndividual> nLocationConcepts = new HashSet<OWLNamedIndividual>();
		ArrayList<Branch> locationBranches=new ArrayList<Branch>();
		
		nLocationConcepts.add(trgConcept);
		allCollectedConcepts.add(trgConcept);
		
		do
		{
			locationBranches.addAll(
					this.getFrontLocationOfConcept(allCollectedConcepts, nLocationConcepts, broader, ontCFR));
			
			nLocationConcepts.clear();
			for (Branch br : locationBranches) 
				nLocationConcepts.add(br.getObject());
			
			
			if (allCollectedConcepts.size()>maxConceptsQuality) continue;
			
			locationBranches.addAll(
					this.getBackLocationOfConcept(allCollectedConcepts,nLocationConcepts, broader, ontCFR));			

		}
		while (allCollectedConcepts.size()<=maxConceptsQuality & !locationBranches.isEmpty());
		
		
		return locationBranches;
	}

	
	/**
	 * Возвращает набор дуг, ведущих к концептам, выступающих объектами 
	 * по определенным отношениям.
	 * Пример дуги: mainConcept ----prp---> concept 
	 * @param prp
	 * @param mainConcept
	 * @param ontCFR
	 * @return ArrayList<Branch>
	 */
	private ArrayList<Branch> getBranchesToObjectConcept(OWLObjectProperty prp, 
			OWLNamedIndividual mainConcept, CFrameOnt ontCFR)
	{
		ArrayList<Branch> branchList = new ArrayList<Branch>();
		ArrayList<OWLNamedIndividual> conceptsAsObject = ontCFR
				.getValueOfprp(mainConcept, prp, true);
		
		for (OWLNamedIndividual objectConc : conceptsAsObject)
		{
			Branch br = new Branch(mainConcept, prp, objectConc);
			branchList.add(br);
		}
		
		return branchList;
	}
	
	/**
	 * Возвращает набор дуг, ведущих к концептам, выступающих субъектами 
	 * по определенным отношениям.
	 * Пример дуги: concept ----prp---> mainConcept
	 * @param prp
	 * @param mainConcept
	 * @param ontCFR
	 * @return ArrayList<Branch>
	 */
	private ArrayList<Branch> getBranchesToSubjectConcept(OWLObjectProperty prp, 
			OWLNamedIndividual mainConcept, CFrameOnt ontCFR)
	{
		ArrayList<Branch> branchList = new ArrayList<Branch>();
		ArrayList<OWLNamedIndividual> conceptsAsSubject = ontCFR
				.getValueOfprp(mainConcept, prp, false);
		
		for (OWLNamedIndividual subjectConc : conceptsAsSubject)
		{
			Branch br = new Branch(subjectConc, prp, mainConcept);
			branchList.add(br);
		}
		
		return branchList;
	}
	
	/**
	 * Формирует основу кфрейма:
	 * front <-- back --> целевое --> front <-- back 
	 * @param nullLocation
	 * @param maxConceptsQuality
	 * @return
	 */
	private ArrayList<ConceptLocation> addBaseContentLocations(ConceptLocation nullLocation, int maxConceptsQuality )
	{
		ArrayList<ConceptLocation> firstFrontAndBackLoc = null;
		Boolean contentIsNotFull = false;
		
		ConceptLocation floc = nullLocation.getFrontLocation();
		ConceptLocation bloc = nullLocation.getBackLocation();
		this.content.addLocation(floc, maxConceptsQuality*100);
		this.content.addLocation(bloc, maxConceptsQuality*100);		
		////////////////
		ConceptLocation backFloc = floc.getBackLocation();
		this.content.addLocation(backFloc, maxConceptsQuality );
		
		ConceptLocation frontBloc = bloc.getFrontLocation();
		contentIsNotFull = this.content.addLocation(frontBloc,maxConceptsQuality);
		
		/*Если содержимое не заполнено - возвращаем массив окружений для дальнейшего
		 * построения следующх окружений и добавления их в содержимое */
		if (contentIsNotFull)
		{
			firstFrontAndBackLoc = new ArrayList<ConceptLocation>();
			firstFrontAndBackLoc.add(floc);
			firstFrontAndBackLoc.add(bloc);
		}
		
		return firstFrontAndBackLoc;
	}
	
	/**
	 * Формирует и добавляет следующие окрестности на основе 2х исходных (передней и задней).
	 * 
	 * @param locations 2 исходные окрестности
	 * @param maxConceptsQuality
	 */
	private void addNextLocation(ArrayList<ConceptLocation> locations, int maxConceptsQuality )
	{
		ConceptLocation frontBloc, floc = locations.get(0); 
		ConceptLocation backFloc,  bloc = locations.get(1);		
		
		while (!this.content.isFilled(maxConceptsQuality) & (!floc.isEmpty() | !bloc.isEmpty()) ) 
		{
			floc = floc.getFrontLocation();
			if (!floc.isEmpty()) this.content.addLocation(floc, maxConceptsQuality);

			bloc = bloc.getBackLocation();
			if (!bloc.isEmpty()) this.content.addLocation(bloc, maxConceptsQuality);
		
			backFloc = floc.getBackLocation();
			if (!backFloc.isEmpty()) this.content.addLocation(backFloc, maxConceptsQuality);
			
			frontBloc = bloc.getFrontLocation();
			if (!frontBloc.isEmpty()) this.content.addLocation(backFloc, maxConceptsQuality);
		}

		
	}
	
	/**
	 * Удаляет из переданной окрестности некоторые концепты (и соответствующие им дуги)
	 * по некоторому признаку.
	 * @param content
	 * @param location
	 * TODO доработать - фильтры включать/выключать дополнительными агрументами
	 */
	private ConceptLocation getFilteredLocation(CFrameContent content, ConceptLocation location, 
			Boolean applyAfilter, Boolean applyBfilter, Boolean applyCfilter)
	{
		//HashSet<OWLNamedIndividual> tmpSet = new HashSet<OWLNamedIndividual>();
		HashSet<OWLNamedIndividual> removedConcept = new HashSet<OWLNamedIndividual>();
		
		for (OWLNamedIndividual locationConcept : location.getConcepts())
		{
		//////////////Фильтры - BEGIN //////////////////////////////////////////////////

			//////////////Фильтры только окружения //////////////////////////////////////////
			/*=== Убираем из окруженя концепты-подклассы концепта, также 
			 * уже существующего в этом окружении*/
			if (applyAfilter && !removedConcept.contains(locationConcept) )
			{
				HashSet<OWLNamedIndividual> narrowerConceptSet = 
						this.ontUPO.getNarrowerConcepts(locationConcept, false, true);
				
				/* Оставляем в narrowerConceptSet те только концепты, которые также есть в окружении
				 * - их и надо удалить */
				narrowerConceptSet.retainAll(location.getConcepts());
				
				for (OWLNamedIndividual rmConcept : narrowerConceptSet)
				{
					log.info("Filter A "+ " for <"+rmConcept+">");
					removedConcept.add(rmConcept);
				}
			}

			//////////////Фильтры окружения с учетом контента ( т.е. суммы предыдущик окружений)////
			for (OWLNamedIndividual contentConcept : content.getConcepts())
			{
				/*=== Убираем из окружения концепты - под-над-классы концептов из контента (т.е. 
				 * уже добавленных на предыдущих шагах) */
				HashSet<OWLNamedIndividual> broaderAndNarrowerConceptSet = 
						this.ontUPO.getBroaderConcepts(contentConcept, false, true);
				broaderAndNarrowerConceptSet.addAll(this.ontUPO.getNarrowerConcepts(contentConcept, false, true));
				
				if (applyBfilter && !removedConcept.contains(locationConcept) && broaderAndNarrowerConceptSet.contains(locationConcept))
				{
					log.info("Filter B "+ " for <"+locationConcept+">");
					removedConcept.add(locationConcept);
				}
				
				
				/*=== Убираем концепты-субаксиомы */
				if ( applyCfilter && !removedConcept.contains(locationConcept) && 
						locationConcept.getIRI().toString().startsWith(this.ontUPO.UPO_TEMPLATE_IRI_OF_COMLEXSBAXIOM))
				{
					Boolean aaa = !removedConcept.contains(locationConcept);
					Boolean ccc = locationConcept.getIRI().toString().startsWith(this.ontUPO.UPO_TEMPLATE_IRI_OF_COMLEXSBAXIOM);
					log.info("==>"+ aaa +" & "+  ccc.toString() );
					
					log.info("Filter C "+ " for <"+locationConcept+">");
					removedConcept.add(locationConcept);
				}
			}
		//////////////Фильтры - END//////////////////////////////////////////////////
		}
		/*Удаляем выбранные концепты из окрестности*/
		for (OWLNamedIndividual rmCocept : removedConcept)
		{
			location.removeConcept(rmCocept);
		}
		return location;
	}
	
	/**
	 * Рекурсивно собирает дуги у текущей и связанных с ней субаксиом.
	 * @param sbax
	 * @param analysedSbaxSet
	 * @return
	 */
	private ArrayList<Branch> getSbaxBranches(OWLNamedIndividual sbax, HashSet<OWLNamedIndividual> analysedSbaxSet)
	{
		ArrayList<Branch> brList = new ArrayList<>();
		if (analysedSbaxSet.contains(sbax))	return brList;
		else analysedSbaxSet.add(sbax);
		
		/* собираем все дуги, соединяющие субаксиому с другими понятиями 
		 * и субаксиомами, кроме related, т.к. она соединяет с целевым понятием*/
		HashSet<IRI> filteredSubAxProperties = new HashSet<IRI>();
		filteredSubAxProperties.add(IRI.create(ConstantsOntAPI.SKOS_RELATED));
		HashSet<ArrayList<IRI>> subaxNeibours=null;

		/*...для всех дуг где с другой стороны стоит другая субаксиома 
		 * и она еще не была обработана запускаем рекурсивно эту процедуру.*/
		subaxNeibours=this.ontUPO.getRelatedPrpAndInd(sbax.getIRI(), true, filteredSubAxProperties);
		for (ArrayList<IRI> brachIRI : subaxNeibours)
		{
			Branch br = new Branch(brachIRI.get(0), brachIRI.get(1), brachIRI.get(2), this.ontUPO);
			
			if (br.getObject().getIRI().toString().startsWith(ConstantsOntConverter.UPO_TEMPLATE_IRI_OF_COMLEXSBAXIOM))
			{
				OWLNamedIndividual nextSbax = br.getObject();
				if (!analysedSbaxSet.contains(nextSbax))
				{
					brList.add(br);
					brList.addAll(getSbaxBranches(br.getObject(), analysedSbaxSet));
				}
			}
			else brList.add(br);
		}
		
		
//		subaxNeibours.addAll(this.ontUPO.getRelatedPrpAndInd(subax.getIRI(), false, filteredSubAxProperties));
		return brList;
	}

	
}
