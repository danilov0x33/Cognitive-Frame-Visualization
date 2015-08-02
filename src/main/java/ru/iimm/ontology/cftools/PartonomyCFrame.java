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
 * К-фрейм, отражающий отношения партономии понятия.
 * @author Lomov P. A.
 *
 */
public class PartonomyCFrame extends CFrameImpl
{
	
	
	/**
	 * @param ontCFR
	 */
	PartonomyCFrame(CFrameOnt ontCFR) 
	{
		super(ontCFR, ConstantCFTools.CFRAME_ONT_IRI_PARTONOMY_CFRAME);
		// TODO Auto-generated constructor stub
	}

	private static final Logger log = LoggerFactory.getLogger(PartonomyCFrame.class);

	/**
	 * 
	 */
	public PartonomyCFrame(OWLNamedIndividual concept, CFrameOnt ontCFR)
	{
		super(concept, ontCFR, ConstantCFTools.CFRAME_ONT_IRI_PARTONOMY_CFRAME);
		log.info("====== Create PARTONOMY Cframe for:" + Ontology.getShortIRI(concept.getIRI()));
		
		/*Формируем содержание*/
		ContentBuilderVisitor contentVst = new ContentBuilderVisitor(ontCFR);
		this.accept(contentVst);
		
		/*Определение способа визуализации*/
		VMethodDefenitionVisitor vmethodDefVst = new VMethodDefenitionVisitor();
		this.accept(vmethodDefVst);
		
		this.content=contentVst.getContent();
		this.vmethod=vmethodDefVst.getVisMet();

		
		log.info("====== END - Create PARTONOMY Cframe for:" + Ontology.getShortIRI(concept.getIRI()));
		
	}

	/**
	 * 
	 */
	@Override
	public void accept(CFrameVisitor visitor)
	{
		visitor.visitPatromomyFrame(this);
	}


}
