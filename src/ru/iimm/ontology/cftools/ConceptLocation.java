package ru.iimm.ontology.cftools;

import java.util.ArrayList;
import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.ontAPI.Ontology;
import ru.iimm.ontology.ontAPI.SkosOnt;

public class ConceptLocation
{

	/**
	 * Понятия, входящие в окрестность
	 */
	HashSet<OWLNamedIndividual> concepts;
	
	/**
	 * Дуги, ведущие в следующую фронтальную или тыловую окрестность.
	 */
	ArrayList<Branch> nextLocationBranches;
	
	/**
	 * Свойства, субъектами (носителями) которых выступают понятия данной окрестности. 
	 * Обычно это одно основное и его подсвойства.
	 * Т.е. дуги с этим свойствами ведут в следующую фронтальную окрестность
	 * Имею вид: текущаая окр --свойсвто--> фронтальная окр 
	 */
	ArrayList<OWLObjectProperty> subjectProp;

	/**
	 * Свойства, объектами (значениями) которых выступают понятия данной окрестности.
	 * Т.е. дуги с этим свойствами ведут в следующую тыловую окрестность 
	 * Имеют вид: тыловая окр --свойсвто--> текущаая окр  
	 */
	ArrayList<OWLObjectProperty> objectProp;
	
	/**
	 * Онтология окрестности.
	 */
	CFrameOnt ont;

	/**
	 * 
	 * @param trgConcept
	 * @param ontCFR
	 * @param mPrp
	 */
	public ConceptLocation(OWLNamedIndividual trgConcept, CFrameOnt ontCFR, OWLObjectProperty mPrp)
	{
		this.concepts = new HashSet<OWLNamedIndividual>();
		this.concepts.add(trgConcept);
		
		
		//this.frontBranches = new HashSet<Branch>();
		this.nextLocationBranches = new ArrayList<Branch>();
		this.subjectProp = ontCFR.getSubObjectProperties(mPrp, true);
		
		this.objectProp = ontCFR.getInverseObjectProperties(mPrp);
		this.ont = ontCFR;
	
	}
	
	private ConceptLocation(CFrameOnt ontCFR, ArrayList<OWLObjectProperty> subjectProp, ArrayList<OWLObjectProperty> objectProp)
	{
		this.concepts = new HashSet<OWLNamedIndividual>();
		//this.frontBranches = new HashSet<Branch>();
		this.nextLocationBranches = new ArrayList<Branch>();
		this.subjectProp = subjectProp;
		this.objectProp = objectProp;
		this.ont = ontCFR;
	
	}

	
	private static final Logger log = LoggerFactory.getLogger(ConceptLocation.class);
	
	public HashSet<OWLNamedIndividual> getConcepts()
	{
		return concepts;
	}

	public void setConcepts(HashSet<OWLNamedIndividual> concepts)
	{
		this.concepts = concepts;
	}

	/**
	 * Формируем фронтальную (по прямым отношениям) окресность указанных понятий, в нее входят дуги вида:
	 * trgConcept --hasPart--> something и (обратные им) something --isPartOf--> trgConcept
	 * @return
	 */
	public  ConceptLocation getFrontLocation()
	{
		ConceptLocation newLoc = new ConceptLocation(this.ont,this.subjectProp, this.objectProp );
		if (this.isEmpty()) return newLoc;
		/* Для каждого концепта из данной окрестности... */
		for (OWLNamedIndividual concept : this.concepts) 
		{
			/* .. и указанного свойства и его подсвойств...*/
			for (OWLObjectProperty prpTemp : this.subjectProp) 
			{//====
				/* .. и каждой дуги вида [conceptp --prpTemp--> something]	 */
				for (Branch br : this.getBranchesToObjectConcept(prpTemp, concept, ont))
				{/* если дуга ведет в еще недобавленный концепт (something) - то добавляем ее*/
					if (!newLoc.concepts.contains(br.getObject()))
					{
						log.info("   Add object-concept <"+Ontology.getShortIRI(br.getObject().getIRI())+ "> to location: " + br);
						newLoc.concepts.add(br.getObject());
						newLoc.nextLocationBranches.add(br);
					}
				}
			}//=====
			
			/*.. теперь тоже самое (но наоборот) для обратных свойств*/
			for (OWLObjectProperty prpTemp : this.objectProp) 
			{//-----
				/*.. и обратных дуг вида: conceptp <--INVERVEprpTemp-- something 				 */
				for (Branch br : this.getBranchesToSubjectConcept(prpTemp, concept, ont))
				{
					if (!newLoc.concepts.contains(br.getSubject()))
					{
						log.info("   Add subject-concept <"+Ontology.getShortIRI(br.getSubject().getIRI())+ "> to location: " + br);
						newLoc.concepts.add(br.getSubject());
						newLoc.nextLocationBranches.add(br);
					}
				}
			}//-------
		}
		return newLoc;
	}

	
	/**
	 * Формируем заднюю окресность указанных понятий, в нее входят дуги вида:
	 * something --hasPart-->trgConcept  и (обратные им) trgConcept --isPartOf--> something  
	 * @return
	 */
	public  ConceptLocation getBackLocation() 
	{
		ConceptLocation newLoc = new ConceptLocation(this.ont, this.subjectProp, this.objectProp);
		
		if (this.isEmpty()) return newLoc;
		/* Для каждого концепта из данной окрестности... */
		for (OWLNamedIndividual concept : this.concepts) 
		{
			/* .. и указанного свойства и его подсвойств... */
			for (OWLObjectProperty prpTemp : this.subjectProp) 
			{//====
				/* .. и каждой дуги вида something --prpTemp--> conceptp	 */
				for (Branch br : this.getBranchesToSubjectConcept(prpTemp, concept, ont))
				{/* если дуга ведет в еще недобавленный концепт - то добавляем ее*/
					if (!newLoc.concepts.contains(br.getSubject()))
					{
						log.info("   Add subject-concept <"+Ontology.getShortIRI(br.getSubject().getIRI())+ "> to location: " + br);
						//log.info("   ");
						newLoc.concepts.add(br.getSubject());
						//this.frontBranches.add(br);
						newLoc.nextLocationBranches.add(br);
					}
				}
			}//=====
			
			/*.. теперь тоже самое (но наоборот) для обратных свойств*/
			for (OWLObjectProperty prpTemp : this.objectProp) 
			{//-----
				/*.. и обратных дуг вида: something <--INVERVEprpTemp-- conceptp 				 */
				for (Branch br : this.getBranchesToObjectConcept(prpTemp, concept, ont))
				{
					if (!newLoc.concepts.contains(br.getObject()))
					{
						log.info("   Add object-concept <"+Ontology.getShortIRI(br.getObject().getIRI())+ "> to location: " + br);
						newLoc.concepts.add(br.getObject());
//						this.frontBranches.add(br);
						newLoc.nextLocationBranches.add(br);
					}
				}
			}//-------
		}
		return newLoc;
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

	public boolean isEmpty() 
	{
		return this.concepts.isEmpty();
	}
	
	/**
	 * Удаляет концепт и его дуги из окрестности.
	 * @param concept
	 * @return
	 */
	public boolean removeConcept(OWLNamedIndividual concept)
	{
		if (this.concepts.contains(concept))
		{
			this.concepts.remove(concept);
			int i = this.removeConceptBranches(concept);
			log.info("   Remove concept: " +concept.getIRI().getFragment());
			log.info("   Remove branch quality: " + i);
			return true;
		}
		else
			return false;
		
	}
	
	/**
	 * Удаляет из набора дуг окрестности, те которые содержат
	 * указанный концепт как объект и субъект:
	 * [??? --property--> concept]
	 * @param concept
	 * @return
	 */
	private int removeConceptBranches(OWLNamedIndividual concept)
	{
		ArrayList<Branch> remBranchs = new ArrayList<>();
		
		for (Branch br : this.nextLocationBranches)
		{
			if (br.getObject()==concept | br.getSubject()==concept )
			{
				remBranchs.add(br);
			}
		}
		
		this.nextLocationBranches.removeAll(remBranchs);
		
		return remBranchs.size();
	}
	
}
