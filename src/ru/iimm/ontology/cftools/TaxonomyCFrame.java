/**
 * 
 */
package ru.iimm.ontology.cftools;

import java.util.HashSet;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt;
import ru.iimm.ontology.ontAPI.Ontology;

/**
 * К-фрейм, отражающий отношения таксономии целевого понятия.
 * @author Lomov P. A.
 *
 */
public class TaxonomyCFrame extends CFrameImpl
{

	
	/**
	 * @param ontCFR
	 */
	TaxonomyCFrame(CFrameOnt ontCFR) {
		super(ontCFR, ConstantCFTools.CFRAME_ONT_IRI_TAXONOMY_CFRAME);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = LoggerFactory.getLogger(TaxonomyCFrame.class);
	/**
	 * 
	 */
	TaxonomyCFrame(OWLNamedIndividual concept, CFrameOnt ontCFR)
	{
		super(concept, ontCFR, ConstantCFTools.CFRAME_ONT_IRI_TAXONOMY_CFRAME);
		
		log.info("====== Create TAXONOMY Cframe for:" + Ontology.getShortIRI(concept.getIRI()));
		/*Формируем содержание*/
		ContentBuilderVisitor contentVst = new ContentBuilderVisitor(ontCFR);
		this.accept(contentVst);
		
		/*Определение способа визуализации*/
		VMethodDefenitionVisitor vmethodDefVst = new VMethodDefenitionVisitor();
		this.accept(vmethodDefVst);
		
		this.content=contentVst.getContent();
		this.vmethod=vmethodDefVst.getVisMet();
		log.info("====== END - Create TAXONOMY Cframe for:" + Ontology.getShortIRI(concept.getIRI()));
		
	}
	

	/**
	 * Приглашает посетителя для обработки.
	 */
	@Override
	public void accept(CFrameVisitor visitor)
	{
		visitor.visitTaxonomyFrame(this);
	}


}
