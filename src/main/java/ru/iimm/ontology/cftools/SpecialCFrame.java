/**
 * 
 */
package ru.iimm.ontology.cftools;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.ontAPI.Ontology;

/**
 * Специальный к-фрейм отображает сложные аксиомы, которые описывают понятие.
 * 
 * @author Lomov P.A.
 *
 */
public class SpecialCFrame extends CFrameImpl
{
	
	private static final Logger log = LoggerFactory.getLogger(SpecialCFrame.class);


	/**
	 * @param ontCFR
	 */
	public SpecialCFrame(CFrameOnt ontCFR)
	{
		super(ontCFR, ConstantCFTools.CFRAME_ONT_IRI_SPECIAL_CFRAME);
	}
	
	/**
	 * Создает к-фрейм для понятия из ОПП 
	 * @param concept
	 * @param ontUPO
	 */
	public SpecialCFrame(OWLNamedIndividual concept, CFrameOnt ontUPO)
	{
		super(concept, ontUPO, ConstantCFTools.CFRAME_ONT_IRI_SPECIAL_CFRAME);
		log.info("====== Create SPECIAL Cframe for:" + concept.getIRI().getFragment());
		
		/*Формируем содержание*/
		ContentBuilderVisitor contentVst = new ContentBuilderVisitor(ontUPO);
		this.accept(contentVst);
		
		/*Определение способа визуализации*/
		VMethodDefenitionVisitor vmethodDefVst = new VMethodDefenitionVisitor();
		this.accept(vmethodDefVst);
		
		this.content=contentVst.getContent();
		this.vmethod=vmethodDefVst.getVisMet();

		log.info("====== END - Create SPECIAL Cframe for:" + concept.getIRI().getFragment());
		
	}
	
	

	/* (non-Javadoc)
	 * @see ru.iimm.ontology.cftools.CFrame#accept(ru.iimm.ontology.cftools.CFrameVisitor)
	 */
	@Override
	public void accept(CFrameVisitor visitor)
	{
		visitor.visitSpecialFrame(this);
		

	}
	
	

}
