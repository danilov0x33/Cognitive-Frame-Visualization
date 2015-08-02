package ru.iimm.ontology.cftools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt;
import ru.iimm.ontology.ontAPI.ConstantsOntAPI;
import ru.iimm.ontology.ontAPI.Ontology;
import scala.collection.parallel.ParIterableLike.Forall;

/**
 * Принимает на вход UPO - анализирует - формирует с помощью контрукторов CognitiveFrame КФ - 
 * на выходе файл онтологии UPO с экземплярами когнитивных фреймов.
 * 
 * Хранит переменные, отражающие состояние процесса создание к-фреймов для
 * онтологии.
 * 
 */
public class UPOCFrameFormer
{
	/**
	 * Онтология пользовательского представления
	 */
	//private CFrameOnt ontCFR;
	
	private ArrayList<CFrame> createdCFramesList;
		
	private static final Logger log = LoggerFactory.getLogger(UPOCFrameFormer.class);
	
	public UPOCFrameFormer()
	{
		this.createdCFramesList=new ArrayList<CFrame>();
		

	}
	
	
	
	/**
	 * Заполняет ОПП к-фреймами.
	 * @param ontCFR
	 */
	public CFrameOnt getCFramesUserPresentationOntology(UserPresenOnt ontUPO)
	{
		log.info("== Create frame in ontology: "+ontUPO.ontInMem.getOntologyID()+" =========");
		
		CFrameOnt ontCFR = new CFrameOnt(ontUPO);
		
		CFrameCreator creator = new CFrameCreator();
		ArrayList<CFrameImpl> createdCframes = new ArrayList<CFrameImpl>();
		
		Set<OWLNamedIndividual> conceptSet = ontCFR.getIndividualOfClass(IRI.create(ConstantsOntAPI.SKOS_CONCEPT));
		
		log.info("All concept quality: {}", conceptSet.size());
		
		for (OWLNamedIndividual concept : conceptSet) 
		{
	
		/* Создаем к-фреймы для концепта */
		if (isConceptOfSubjectDomainOntology(concept))
			{
				createdCframes.addAll(creator.createCFrame(concept, ontCFR));
			}	
		}

		/* Сохраняем созданные к-фреймы в ОПП */
		for (CFrameImpl fr : createdCframes) 
			ontCFR.addCFrame(fr);	
		
		log.info("==  END = Creation of CFrames finished ========");
		return ontCFR;
	}
	
	
	/**
	 * Определяет принадлежит ли концепт прикладной онтологии, чтобы строить к-фрейм для него. 
	 * @param concept
	 * @return
	 */
	static private boolean isConceptOfSubjectDomainOntology(OWLNamedIndividual concept)
	{
		/*
		 * Массив IRI онтологий, для концептов которых на формировать к-фреймы
		 * 
		 * TODO надо как-то узнать для каких онтологих надо строить к-фреймы
		 * вероятно - получать от пользвателя
		 */
		ArrayList<IRI> SubjectDomainOntologiesIRIs = new ArrayList<IRI>();
		SubjectDomainOntologiesIRIs.add(IRI.create("http://www.iimm.ru/ontlib/network-ont-terms"));
		
		for (IRI iri : SubjectDomainOntologiesIRIs) 
		{
			if (concept.getIRI().toString().startsWith(iri.toString()))
				return true;
		}
		return false;
		
	}
	
	
	
	
	
}
