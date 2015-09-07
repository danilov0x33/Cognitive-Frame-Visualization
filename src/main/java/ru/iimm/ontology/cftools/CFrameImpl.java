/**
 * 
 */
package ru.iimm.ontology.cftools;

import java.util.ArrayList;
import java.util.HashSet;

import javax.naming.BinaryRefAddr;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.ontAPI.Ontology;


/**
 * Абстрактый к-фрейм - содержит поля, метод и конструктор,
 * общие для всех видов фреймов.
 * @author Lomov P.A.
 *
 */
public abstract class CFrameImpl implements CFrame
{
	/**
	 * Фрагмент онтологии для визуализации.
	 */
	protected CFrameContent content;
	
	/**
	 * Способ структурирования содержания для визуализации.
	 */
	protected CFrameVisualisationMethod vmethod;
	
	/**
	 * Целевое понятие (то, что описывает
	 * данный к-фрейм)
	 */
	protected OWLNamedIndividual trgConcept; 
	
	/**
	 * Онтология пользовательского представления, которой принадлежит
	 * целевое понятие.
	 */
	protected CFrameOnt ontCFR;
	
	/**
	 * Класс в ОПП, в который входит экземпляр, соответвующий данному к-фрейму.
	 * По-сути данный класс это тип к-фрейма.
	 */
	protected OWLClass cframeOWLClass;
	
	/**
	 * Экземпляр в ОПП, котоый соответствует данному к-фрейму.
	 */
	protected OWLNamedIndividual cframeInd;
	
	
	private static final Logger log = LoggerFactory.getLogger(CFrameImpl.class);
	
	/**
	 * 
	 * @param trgConceptIRI
	 */
	protected CFrameImpl(OWLNamedIndividual trgConcept, CFrameOnt ontCFR, String cframeOWLClassIRI)
	{
		this.trgConcept=trgConcept;
		this.ontCFR=ontCFR;
		this.cframeOWLClass=ontCFR.getOWLClassByIRI(cframeOWLClassIRI);
	}
	
	protected CFrameImpl(CFrameOnt ontCFR, String cframeOWLClassIRI)
	{		
		this.ontCFR=ontCFR;
		this.cframeOWLClass=ontCFR.getOWLClassByIRI(cframeOWLClassIRI);
	}
	

	/**
	 * @return the content
	 */
	public CFrameContent getContent()
	{
		return content;
	}

	/**
	 * @return the vmethod
	 */
	public CFrameVisualisationMethod getVmethod()
	{
		return vmethod;
	}

	/**
	 * @return the trgConcept
	 */
	public OWLNamedIndividual getTrgConcept()
	{
		return trgConcept;
	}

	/**
	 * @return the cframeOWLClass
	 */
	public OWLClass getCFrameOWLClass()
	{
		return cframeOWLClass;
	}

	/**
	 * @return the cframeInd
	 */
	public OWLNamedIndividual getCframeInd()	
	{
		if (this.cframeInd == null)
		{
			log.warn("! Cframe <{}> has no appropriative individual.");
			log.warn("! You should add it into ontology to set it");
		}
		
		return cframeInd;
	}

	/**
	 * @param cframeInd the cframeInd to set
	 */
	void setCframeInd(OWLNamedIndividual cframeInd)
	{
		this.cframeInd = cframeInd;
	}

	public boolean isEmpty()
	{
		if ( this.content==null || this.content.getBranchQuality()==0)		
			return true;
		else 
			return false;
		
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{	
		String rez = 	"\n===" + this.getClass() + " of concept <"+ Ontology.getShortIRI(this.trgConcept) + ">" +
						"\nCframe ontology individual: "+ Ontology.getShortIRI(this.cframeInd) +
						"\n-Branch's quality: " + this.content.getBranchQuality()+
						"\n-Concept's quality: " + this.content.getConcepts().size()+
						"\n"+this.content.toString() +
						"\n"+this.vmethod.toString();
		return rez;
	}
	
	
	
	
	
	


}
