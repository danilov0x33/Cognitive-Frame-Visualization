/**
 * 
 */
package ru.iimm.ontology.cftools;

import java.util.ArrayList;
import java.util.HashSet;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt;
import ru.iimm.ontology.ontAPI.Ontology;


/**
 * Фабричный метод для создания различных видов к-фреймов.
 * @author Lomov P.A.
 *
 */
public class CFrameCreator 
{
	
	private static final Logger log = LoggerFactory.getLogger(CFrameCreator.class);
	
	/**
	 * Список созданных к-фреймов.
	 */
	private ArrayList<CFrameImpl> frameList;
	
	public ArrayList<CFrameImpl> createCFrame(OWLNamedIndividual conceptUPO, CFrameOnt ontUPO)
	{
		
	log.info("===== Create CFrames for concept:"+conceptUPO);
	frameList = new ArrayList<CFrameImpl>();
	
	/*Определяем какие к-фреймы создавать*/
	
	/*Вызываем конструкторы выбранных к-фреймов*/

	CFrameImpl frame;
	frame = new TaxonomyCFrame(conceptUPO, ontUPO);
	this.addNonEmptyCFrame(frame);
	frame = new PartonomyCFrame(conceptUPO, ontUPO);
	this.addNonEmptyCFrame(frame);
	frame = new DependencyCFrame(conceptUPO, ontUPO);
	this.addNonEmptyCFrame(frame);
	
	frame = new SpecialCFrame(conceptUPO, ontUPO);
	this.addNonEmptyCFrame(frame);
	
	log.info("===== END - Create CFrame for concept:"+conceptUPO);
	
	return frameList;
	}
	
	
	private boolean addNonEmptyCFrame(CFrameImpl fr)
	{
		return fr.isEmpty() ? false : this.frameList.add(fr);
	}

	private OWLClass getCFrameClass(OWLNamedIndividual frInd, CFrameOnt ontCFR)
	{
		HashSet<OWLClass> cls =	ontCFR.getClassesOfIndividual(frInd);
		if (cls.size()>1 | cls.isEmpty()) 
		{
			log.warn("Cframe "+ Ontology.getShortIRI(frInd.getIRI()) + " has <"+ cls.size()+"> Types but only 1 expected");
		}
		return cls.iterator().next();
	}
	
	/**
	 * Возвращает к-фрейм, соответсвующий указанному экземпляру онтологии.
	 * @param cframeInd 
	 * @param ontCFR
	 * @return
	 */
	public CFrame loadFrame(OWLNamedIndividual cframeInd, CFrameOnt ontCFR) 
	{
		OWLClass frClass = this.getCFrameClass(cframeInd, ontCFR);
		
		/* Создаем пустой к-фрейм нужного типа */
		CFrameImpl loadedFrame;

		
		if (frClass.getIRI().equals(IRI.create(ConstantCFTools.CFRAME_ONT_IRI_TAXONOMY_CFRAME))) 
		{
			loadedFrame = new TaxonomyCFrame(ontCFR);
		}
		else
		if (frClass.getIRI().equals(IRI.create(ConstantCFTools.CFRAME_ONT_IRI_PARTONOMY_CFRAME))) 
		{
			loadedFrame = new PartonomyCFrame(ontCFR);
		}
		else
		if (frClass.getIRI().equals(IRI.create(ConstantCFTools.CFRAME_ONT_IRI_DEPENDENCY_CFRAME))) 
		{
			loadedFrame = new DependencyCFrame(ontCFR);
		}
		else 
			loadedFrame = new SpecialCFrame(ontCFR);
		//TODO в ИФах должны быть конструкторы всех типов фреймов - можут тут должен быть какой-нить патерн 
		
		/* Наполняем пустой к-фрейм */
		loadedFrame.ontCFR=ontCFR;
		loadedFrame.cframeOWLClass=frClass;
		loadedFrame.cframeInd=cframeInd;

		/* Натравливаем на фрейм визитор-загрузчик компонентов и далее собираем с него информацию */
		CFcomponentLoaderVisitor contLoaderVis = new CFcomponentLoaderVisitor();
		loadedFrame.accept(contLoaderVis);
		
		loadedFrame.content=contLoaderVis.getContent();
		loadedFrame.trgConcept=contLoaderVis.getTrgConcept();
		loadedFrame.vmethod=contLoaderVis.getVisMethod();
		
		
		//log.info("\n"+loadedFrame.toString()+				"\n--------------------");
		
		return loadedFrame;
	}
	

}
