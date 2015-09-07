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
 * К-фрейм отражающий понятия, от которых зависит
 * целевое.
 * @author Lomov P. A.
 *
 */
public class DependencyCFrame extends CFrameImpl
{

	
	/**
	 * @param ontCFR
	 */
	DependencyCFrame(CFrameOnt ontCFR) {
		super(ontCFR, ConstantCFTools.CFRAME_ONT_IRI_DEPENDENCY_CFRAME);
		// TODO Auto-generated constructor stub
	}

	private static final Logger log = LoggerFactory.getLogger(DependencyCFrame.class);
	/**
	 * 
	 */
	public DependencyCFrame(OWLNamedIndividual concept, CFrameOnt ontUPO)
	{
		super(concept, ontUPO, ConstantCFTools.CFRAME_ONT_IRI_DEPENDENCY_CFRAME);
		log.info("====== Create DEPENDENCY Cframe for:" + Ontology.getShortIRI(concept.getIRI()));
		
		/*Формируем содержание*/
		ContentBuilderVisitor contentVst = new ContentBuilderVisitor(ontUPO);
		this.accept(contentVst);
		
		/*Определение способа визуализации*/
		VMethodDefenitionVisitor vmethodDefVst = new VMethodDefenitionVisitor();
		this.accept(vmethodDefVst);
		
		this.content=contentVst.getContent();
		this.vmethod=vmethodDefVst.getVisMet();

		log.info("====== END - Create DEPENDENCY Cframe for:" + Ontology.getShortIRI(concept.getIRI()));
		
	}

	@Override
	public void accept(CFrameVisitor visitor)
	{
		visitor.visitDependencyFrame(this);
	}


}
