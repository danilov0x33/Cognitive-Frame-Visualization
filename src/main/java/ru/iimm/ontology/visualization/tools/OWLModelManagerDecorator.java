package ru.iimm.ontology.visualization.tools;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

/**
 * Реализация оболочки для OWLOnlotogy.
 * @author Danilov E.Y.
 *
 */
public class OWLModelManagerDecorator implements OWLModelManagerDecoratorInt, ContentVisualizationViewerInt
{
	/**Список визуализиций для CFrame.*/
	private ArrayList<AbstractVisualMethodVisitor> visualMethodList;
	/**Менеджен для OWLClass'ов.*/
	private OWLOntologyManager manager;
	private OWLOntology ontology;
	private OWLReasoner reasoner;
	
	public OWLModelManagerDecorator(OWLOntologyManager manager, OWLOntology ontology, OWLReasoner reasoner)
	{
		this.visualMethodList = new ArrayList<AbstractVisualMethodVisitor>();
		
		this.manager = manager;
		this.ontology = ontology;
		this.reasoner = (new StructuralReasonerFactory()).createReasoner(ontology);
		
		this.initVisualization();
	}
	
	private void initVisualization()
	{
		//Визуализация Prefuse
		AbstractVisualMethodVisitor visualMethodVisitor = new VisNodeLinkPrefuseVisitor();
		visualMethodVisitor.setNameVisualMethod("Prefuse");
		
		this.visualMethodList.add(visualMethodVisitor);
	}

	@Override
	public void builderViewer()
	{
		for (AbstractVisualMethodVisitor visualMethodVisitor : this.visualMethodList)
		{
			this.accept(visualMethodVisitor);
		}
	}


	@Override
    public void accept(VisualMethodVisitorInt visitor)
    {
		if (!visitor.isVisitViewer())
		{
			visitor.visit(this.manager, this.ontology, this.reasoner);
			
			visitor.setVisitViewer(true);
		}
    }

	
	public ArrayList<AbstractVisualMethodVisitor> getVisualMethodList()
	{
		return this.visualMethodList;
	}

	public void setVisualMethodList(ArrayList<AbstractVisualMethodVisitor> visualMethodList)
	{
		this.visualMethodList.clear();
		
		if(visualMethodList==null)
			return;
		for(AbstractVisualMethodVisitor vis : visualMethodList)
			this.visualMethodList.add(vis);
	}

	public OWLOntologyManager getManager()
	{
		return this.manager;
	}

	public void setManager(OWLOntologyManager manager)
	{
		this.manager = manager;
	}

	public OWLOntology getOntology()
	{
		return this.ontology;
	}

	public void setOntology(OWLOntology ontology)
	{
		this.ontology = ontology;
	}

	public OWLReasoner getReasoner()
	{
		return this.reasoner;
	}

	public void setReasoner(OWLReasoner reasoner)
	{
		this.reasoner = reasoner;
	}
}
