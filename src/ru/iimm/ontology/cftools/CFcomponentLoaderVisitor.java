/**
 * 
 */
package ru.iimm.ontology.cftools;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.ontAPI.ConstantsOntAPI;
import ru.iimm.ontology.ontAPI.Ontology;

/**
 * @author Lomov P. A.
 *
 */
public class CFcomponentLoaderVisitor implements CFrameVisitor
{
	//private OWLNamedIndividual cframeInd;
	//private CFrameOnt ontCFR;
	private OWLNamedIndividual trgConcept;
	private CFrameVisualisationMethod visMethod;
	private CFrameContent content;
	
	
	
	private static final Logger log = LoggerFactory.getLogger(CFcomponentLoaderVisitor.class);


	@Override
	public void visitPatromomyFrame(PartonomyCFrame frame) 
	{
		this.trgConcept = this.getTargetConcept(frame);
		this.content = loadContent(frame, this.trgConcept);
		this.visMethod = loadVisMethod(frame);
		
	}

	@Override
	public void visitDependencyFrame(DependencyCFrame frame) {
		this.trgConcept = this.getTargetConcept(frame);
		this.content = loadContent(frame, this.trgConcept);
		this.visMethod = loadVisMethod(frame);
	}

	@Override
	public void visitTaxonomyFrame(TaxonomyCFrame frame) {
		this.trgConcept = this.getTargetConcept(frame);
		this.content = loadContent(frame, this.trgConcept);
		this.visMethod = loadVisMethod(frame);
	}
	
	@Override
	public void visitSpecialFrame(SpecialCFrame frame)
	{
		this.trgConcept = this.getTargetConcept(frame);
		this.content = loadContent(frame, this.trgConcept);
		this.visMethod = loadVisMethod(frame);
	}


	/**
	 * @return the visMethod
	 */
	public CFrameVisualisationMethod getVisMethod() {
		return visMethod;
	}

	/**
	 * @return the content
	 */
	public CFrameContent getContent() {
		return content;
	}
	
	/**
	 * @return the trgConcept
	 */
	public OWLNamedIndividual getTrgConcept()
	{
		return trgConcept;
	}

	/**
	 * Загружает содержание к-фрейма.
	 * @param frame
	 * @param trgConcept
	 * @return
	 */
	private CFrameContent loadContent (CFrameImpl frame, OWLNamedIndividual trgConcept) 
	{
		OWLNamedIndividual contentInd = this.getContentInd(frame);  
		
		CFrameContent content = new CFrameContent(trgConcept);
		
		ArrayList<Branch> branches = new ArrayList<Branch>();
		
		/* Для кажого экземпляра дуги из онтологии...*/
		for (OWLNamedIndividual brInd : this.getBrachesInd(contentInd, frame.ontCFR)) 
		{
			/*.. получаем дугу и добавляем ее в содержимое*/
			content.addBranch(this.getBranchFromInd(brInd, frame.ontCFR));
			
		}
		
		return content;
	}
	
	/**
	 * Возвращает набор экземпляров-дуг, свзанных с 
	 * некоторым экземпляром-содержанием. 
	 * @param contentInd
	 * @param ontCFR ОПП, в которой ищутся дуги.
	 * @return
	 */
	private ArrayList<OWLNamedIndividual> getBrachesInd(OWLNamedIndividual contentInd, CFrameOnt ontCFR)
	{
		OWLObjectProperty memberPrp = ontCFR.getEntityByIRI(
				IRI.create(ConstantsOntAPI.SKOS_HAS_MEMBER)).asOWLObjectProperty();
		
		return ontCFR.getValueOfprp(contentInd, memberPrp, true);
	}

	/**
	 * Возвращает дугу, соответвующую указанному экземпляру онтологии,
	 * @param brInd
	 * @return
	 */
	private Branch getBranchFromInd(OWLNamedIndividual brInd, CFrameOnt ontCFR)
	{
		/* Получем свойства, связывающие дугу с ее компонентами */
		OWLObjectProperty hasObjectPrp = ontCFR.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_OBJPRP_HAS_OBJ_CONCEPT)).asOWLObjectProperty();
		OWLObjectProperty hasSubjectPrp = ontCFR.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_OBJPRP_HAS_SUBJ_CONCEPT)).asOWLObjectProperty();
		OWLDataProperty hasRelationPrp = ontCFR.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_DTPPRP_HAS_BRANCH_PRP)).asOWLDataProperty();
		
		/*Получаем компоненты*/
		OWLNamedIndividual obj = ontCFR.getValueOfprp(brInd, hasObjectPrp);
		OWLNamedIndividual sub = ontCFR.getValueOfprp(brInd, hasSubjectPrp);
		OWLLiteral prpIRI = ontCFR.getDataPrpValue(brInd, hasRelationPrp);

		IRI branchIRI = brInd.getIRI();
		
		/* Получаем отношение, представляемое дугой, по IRI */
		OWLObjectProperty prp = ontCFR.getEntityByIRI(
				IRI.create(prpIRI.getLiteral())).asOWLObjectProperty();
		
		return new Branch(sub, prp, obj, branchIRI); //, branchIRI
	}
	
	
	/**
	 * Возвращает экземпляр содержания указанного к-фрейма.
	 * @param cfInd
	 * @param ontCFR
	 * @return
	 */
	private OWLNamedIndividual getContentInd(CFrameImpl frame) 
	{
		OWLObjectProperty hasContent = frame.ontCFR.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_OBJPRP_HASCONTENT)).asOWLObjectProperty();
		
		OWLNamedIndividual cfContentInd =	frame.ontCFR.getValueOfprp(frame.cframeInd, hasContent);

		if (cfContentInd==null)
		{
			log.error("Cframe <"+frame.ontCFR.getShortIRI(frame.cframeInd)+" has no content!");
		}
		return cfContentInd;
	}
	
	/**
	 * Возвращает экземпляр, соответвующий целевому понятию, указанного к-фрейма.
	 * @param cfInd
	 * @return
	 */
	private OWLNamedIndividual getTargetConcept(CFrameImpl fr)
	{
		OWLObjectProperty hasTargetConcept = fr.ontCFR.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_OBJPRP_HAS_TRG_CONCEPT)).asOWLObjectProperty();
		
		OWLNamedIndividual trgConcept =	fr.ontCFR.getValueOfprp(fr.cframeInd, hasTargetConcept);

		if (trgConcept==null)
		{
			log.error("!! Cframe <"+Ontology.getShortIRI(fr.cframeInd)+" has no target concept!");
		}
		return trgConcept;
	}
	
	private CFrameVisualisationMethod loadVisMethod(CFrameImpl fr)
	{
		OWLObjectProperty hasVisualMethod = fr.ontCFR.getEntityByIRI(
				IRI.create(ConstantCFTools.CFRAME_ONT_OBJPRP_HASVISWAY)).asOWLObjectProperty();
		
		OWLNamedIndividual visMetInd =	fr.ontCFR.getValueOfprp(fr.cframeInd, hasVisualMethod);

		if (visMetInd==null)
		{
			log.error("!! Cframe <"+Ontology.getShortIRI(fr.cframeInd)+" has no visualisation method!");
		}
		
		/*TODO на основе экземпляра-метода визуализации надо выбрать необходимый конструктор
		 * метода визуалзации. Пока оставим единственный который есть NodeLinkVisualizationMethod();
		 */
		CFrameVisualisationMethod vmet = new NodeLinkVisualizationMethod();
		
		return vmet;
	}



}
