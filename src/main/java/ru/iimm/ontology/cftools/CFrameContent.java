package ru.iimm.ontology.cftools;

import java.util.ArrayList;
import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import ru.iimm.ontology.ontAPI.Ontology;
import scala.collection.parallel.ParIterableLike.Foreach;


/**
 * Содержание фрейма понятия, которые надо показывать во 
 * к-фрейме определенного типа. 
 * 
 */

/*
 * TODO Может вместо класса, сделать интерфейс. 
 * И для каждого отношения сделать класс.  
 * L:Сделаем, если у нас будет разное содержание для
 * разных типов фреймов.
 */

public class CFrameContent
{
	/**
	 * Понятия к-фрейма.
	 * Первое понятие является целевым. 
	 */
	//TODO Сделать hash set чтобы не повторялись понятия
	private HashSet<OWLNamedIndividual> concepts;
	
	private OWLNamedIndividual trgConcept;
	
	/**
	 * Отношения между понятиями к-фрейма.
	 */
	private ArrayList<Branch> branches;

	
	private static final Logger log = LoggerFactory.getLogger(CFrameContent.class);
	
	/**
	 * Создает содержание с целевым понятием к-фрейма.
	 */
	CFrameContent(OWLNamedIndividual trgConcept)
	{
		this.concepts = new HashSet<OWLNamedIndividual>();
		this.branches = new ArrayList<Branch>();
		this.trgConcept = trgConcept;
		this.concepts.add(trgConcept);
	}
	
	/**
	 * @param concepts
	 * @param branches
	 */
	CFrameContent(HashSet<OWLNamedIndividual> concepts,
			ArrayList<Branch> branches, OWLNamedIndividual trgConcept) 
	{
		super();
		this.concepts = concepts;
		this.branches = branches;
		this.trgConcept = trgConcept;
	}


	/**
	 * Добавляет ребро и понятия из него к содержанию.
	 * @param concept1
	 * @param prp
	 * @param concept2
	 */
	public void addBranch(OWLNamedIndividual concept1, OWLObjectProperty prp,
			OWLNamedIndividual concept2) 
	{
		Branch br = new Branch(concept1, prp, concept2);
		this.branches.add(br);
		this.concepts.add(concept1);
		this.concepts.add(concept2);
	}
	
	/**
	 * Добавляет ребро к содержанию.
	 * @param br
	 */
	public void addBranch(Branch br) 
	{
		this.branches.add(br);
		this.concepts.add(br.getSubject());
		this.concepts.add(br.getObject());
	}

	/**
	 * Добавляет ребра к содержанию.
	 * @param brList
	 */
	public void addBranches(ArrayList<Branch> brList) 
	{
		for (Branch br : brList) addBranch(br);	
	}
	
	
	/**
	 * @return the consepts
	 */
	public HashSet<OWLNamedIndividual> getConcepts() {
		return concepts;
	}



	/**
	 * @return the branches
	 */
	public ArrayList<Branch> getBranches() {
		return branches;
	}
	
	/**
	 * Возвращает число дуг в содержимом.
	 * @return
	 */
	public int getBranchQuality()
	{
		//log.warn("-->>"+branches.size());
		return this.branches.size();
	}

	/**
	 * @return the trgConcept
	 */
	public OWLNamedIndividual getTrgConcept()
	{
		return trgConcept;
	}

	/**
	 * @param trgConcept the trgConcept to set
	 */
	public void setTrgConcept(OWLNamedIndividual trgConcept)
	{
		this.trgConcept = trgConcept;
		this.concepts.add(trgConcept);
	}
	
	/**
	 * Добавляет новые дуги из окружения.
	 * @param loc
	 * @param maxConceptQuanity
	 * @param ontCFR
	 * @return true если добавляемое окружение не переваливает за указанный максимум понятий.
	 */
	public boolean addLocation (ConceptLocation loc, int maxConceptQuanity) 
	{
		/*Считаем сколько всего понятий будет в содержимом - если добавить туда понятия окрестности*/
		if ((loc.concepts.size()+this.concepts.size())>=maxConceptQuanity)
		{/* если больше максимума, то выходим*/
			return false;}
		
		ArrayList<Branch> addedBrances= new ArrayList<Branch>();
		HashSet<OWLNamedIndividual> addedConcepts= new HashSet();
		Boolean addNewBranch = true;
		
		/* для каждой дуги из окружения...*/
		for (Branch newBr : loc.nextLocationBranches) 
		{/* и дуг имеющихся в содержимом...*/
			for (Branch br : this.branches) 
			{/*.. сравниваем их - если они не равны ..*/
				if (newBr.equals(br) | newBr.semanticEquals(br, loc.ont) )
					addNewBranch=false;
			}
			
			/*- добавляем дугу из окружения в содержимое*/
			if (addNewBranch)
			{
				this.addBranch(newBr);
			}
			else 
				addNewBranch = true;
		}
			return true;
	}
	
	boolean isFilled(int maxConceptsQuality)
	{
		return this.concepts.size()>=maxConceptsQuality;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return 	"CFrameContent: " +
				"\n -concepts: " + this.getConceptsAsString() + 
				"\n -branches: " + this.getBranchesAsString();
	}
	
	private String getConceptsAsString()
	{
		String rez = "[";
		for (OWLNamedIndividual con : this.concepts)
		{
			rez = rez + "<"+ Ontology.getShortIRI(con) + "> ";
		}
		return rez + "]";
	}
	
	private String getBranchesAsString()
	{
		String rez="";
		for (Branch br : this.branches)
		{
			rez = rez + "\n " + br.toString();
		}
		return rez;
	}




	//=============================================================================	

}
