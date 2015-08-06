package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import org.semanticweb.owlapi.model.OWLNamedIndividual;

import prefuse.data.Node;
import ru.iimm.ontology.cftools.Branch;
import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.DependencyCFrame;
import ru.iimm.ontology.cftools.PartonomyCFrame;
import ru.iimm.ontology.cftools.SpecialCFrame;
import ru.iimm.ontology.cftools.TaxonomyCFrame;
import ru.iimm.ontology.visualization.tools.prefuse.tools.ConstantsPrefuse;
import ru.iimm.ontology.visualization.tools.prefuse.tools.DefaultGraphPrefuse;
import ru.iimm.ontology.visualization.ui.mvp.models.ModelCFrameOntology;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterCFramePrefuseVisitor;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisPrefuse;

/**
 * Реализация интерфейса {@linkplain PresenterCFramePrefuseVisitor}.
 * @author Danilov
 * @version 0.1
 */
public class PresenterCFramePrefuseVisitorImpl implements PresenterCFramePrefuseVisitor
{
	/**Название визуализации*/
	private String nameVisMethod = "Prefuse";
	/**Модель.*/
	private ModelCFrameOntology model;
	/**Визуальный компонент.*/
	private ViewVisPrefuse view;
	
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

	private void defaultVisit(CFrame frame)
	{
		this.view.getDisplay().stop();
		
		DefaultGraphPrefuse graph = view.getGraph();

		graph.clear();
		
		for(OWLNamedIndividual owlName : frame.getContent().getConcepts())
		{
			String label = this.model.getLabel(owlName);
			if(label.equals("_EMPTY_"))
			{
					label=owlName.getIRI().getFragment().toString();
			}
			Node node = graph.addNode(label);
			node.set(ConstantsPrefuse.DATA_OWL_NAMED_INDIVIDUAL_NODE, owlName);
			
			if(owlName.getIRI().equals(frame.getTrgConcept().getIRI()))
			{
				node.set(ConstantsPrefuse.DATA_TARGET_CON_NODE,true);
			}
			
		}
		
		for (Branch branch : frame.getContent().getBranches())
		{
			Node sub = null;
			Node obj = null;
			
			for(int i=0; i < graph.getNodeCount(); i++)
			{
				Node node = graph.getNode(i);
				
				OWLNamedIndividual owlNamedIndividual = (OWLNamedIndividual) node.get(ConstantsPrefuse.DATA_OWL_NAMED_INDIVIDUAL_NODE);
				
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
					
					graph.addEdge(labelEdge,sub, obj);
					
					break;
				}
			}
		}
		
		this.view.getDisplay().start();
		
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
	public void setView(ViewVisPrefuse view)
	{
		this.view = view;
	}

	@Override
	public ViewVisPrefuse getView()
	{
		return this.view;
	}

}
