package ru.iimm.ontology.visualization.ui.mvp.impl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import ca.uvic.cs.chisel.cajun.graph.arc.GraphArc;
import ca.uvic.cs.chisel.cajun.graph.node.GraphNode;
import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;
import ru.iimm.ontology.cftools.Branch;
import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.DependencyCFrame;
import ru.iimm.ontology.cftools.PartonomyCFrame;
import ru.iimm.ontology.cftools.SpecialCFrame;
import ru.iimm.ontology.cftools.TaxonomyCFrame;
import ru.iimm.ontology.visualization.lang.Language;
import ru.iimm.ontology.visualization.tools.cajun.DefaultGraphModelCajun;
import ru.iimm.ontology.visualization.ui.mvp.ModelCFrameOntology;
import ru.iimm.ontology.visualization.ui.mvp.PresenterCFrameCajunVisitor;
import ru.iimm.ontology.visualization.ui.mvp.ViewCFrameVis;
import ru.iimm.ontology.visualization.ui.mvp.ViewCFrameVisCajun;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public class DefaultPresenterCFrameCajunVisitor implements PresenterCFrameCajunVisitor
{
	private String nameVisMethod = "Cajun";
	private ModelCFrameOntology model;
	private ViewCFrameVisCajun view;
	
	@Override
	public void setModel(ModelCFrameOntology model)
	{
		this.model = model;
	}

	@Override
	public ModelCFrameOntology getModel()
	{
		return this.model;
	}

	@Override
	public String getNameVisualMethod()
	{
		return this.nameVisMethod;
	}

	@Override
	public void setNameVisualMethod(String name)
	{
		this.nameVisMethod = name;
	}
	
	private void defaultVisit(CFrame cframe)
	{
		DefaultGraphModelCajun graph = this.view.getGraph();
		graph.clear();
		
		for(OWLNamedIndividual owlName : cframe.getContent().getConcepts())
		{
			String label = this.model.getLabel(owlName);
			if(label.equals("_EMPTY_"))
			{
					label=owlName.getIRI().getFragment().toString();
			}
			
			if(owlName.getIRI().equals(cframe.getTrgConcept().getIRI()))
			{
				graph.addNode(owlName, label,true);
			}
			else
			{
				graph.addNode(owlName, label,false);
			}
		}
		
		for (Branch branch : cframe.getContent().getBranches())
		{
			GraphNode sub = null;
			GraphNode obj = null;
			
			for(GraphNode node : graph.getAllNodes())
			{
				
				OWLNamedIndividual owlNamedIndividual = (OWLNamedIndividual) node.getUserObject();
				
				if(owlNamedIndividual.equals(branch.getSubject()))
				{
					sub = node;
				}
				else
				{
					if(owlNamedIndividual.equals(branch.getObject()))
					{
						obj = node;
					}
				}
				
				if(sub != null && obj != null)
				{					
					String labelEdge = this.model.getAnnotationValue(branch.getPrp().getIRI());
					
					if(labelEdge.equals("_EMPTY_"))
					{
						labelEdge=branch.getPrp().getIRI().getFragment().toString();
					} 
				
					graph.addArc(sub, obj);
					
					break;
				}
			}
		}
	}
	
	@Override
	public void visit(TaxonomyCFrame tFrame)
	{
		this.defaultVisit(tFrame);
	}

	@Override
	public void visit(PartonomyCFrame pFrame)
	{
		this.defaultVisit(pFrame);
	}

	@Override
	public void visit(DependencyCFrame dFrame)
	{
		this.defaultVisit(dFrame);
	}

	@Override
	public void visit(SpecialCFrame sFrame)
	{
		this.defaultVisit(sFrame);
	}

	@Override
	public void setView(ViewCFrameVisCajun view)
	{
		this.view = view;
	}

	@Override
	public ViewCFrameVisCajun getView()
	{
		return this.view;
	}

}
