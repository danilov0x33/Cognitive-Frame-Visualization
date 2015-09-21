package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import ru.iimm.ontology.cftools.Branch;
import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.DependencyCFrame;
import ru.iimm.ontology.cftools.PartonomyCFrame;
import ru.iimm.ontology.cftools.SpecialCFrame;
import ru.iimm.ontology.cftools.TaxonomyCFrame;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewCFrameVisGSImpl;
import ru.iimm.ontology.visualization.ui.mvp.models.ModelCFrameOntology;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterCFrameGSVisitor;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisGS;

/**
 * Реализация интерфейса {@linkplain PresenterCFrameGSVisitor}.
 * @author Danilov
 * @version 0.2
 */
public class PresenterCFrameGSVisitorImpl implements PresenterCFrameGSVisitor
{
	/**Название визуализации*/
	private String nameVisMethod = "Graph Stream";
	/**Модель.*/
	private ModelCFrameOntology model;
	/**Визуальный компонент.*/
	private ViewVisGS view;
	
	/**
	 * {@linkplain PresenterCFrameGSVisitorImpl}
	 */
	public PresenterCFrameGSVisitorImpl()
	{
		ViewCFrameVisGSImpl viewGSCF = new ViewCFrameVisGSImpl();
		viewGSCF.setPresenter(this);
		this.view = viewGSCF;
	}
	
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

	private void defaultVisit(CFrame frame) 
	{		
		MultiGraph graph = this.view.getGraph();
		graph.getNodeSet().clear();
		graph.getEdgeSet().clear();
		
		//this.baseIRIList = new HashSet<String>();
		
		//Получение всех элементов и формирование из них визуального части.
		for(OWLNamedIndividual owlName : frame.getContent().getConcepts())
		{			
			String label = this.model.getLabel(owlName);
			
			if(label.equals("_EMPTY_"))
			{
				label=owlName.getIRI().getFragment().toString();				
			}
			
			Node node = graph.addNode(owlName.getIRI().toString());
			node.addAttribute("OWLNamedIndividual", owlName);
			node.addAttribute("ui.label",  label);
			
			if(owlName.getIRI().equals(frame.getTrgConcept().getIRI()))
			{
				node.setAttribute("ui.class", "concept");
			}

		}
		
		//Связывает все элементы линиями.
		for (Branch br : frame.getContent().getBranches()) 
		{
			Node nodeSub = null;
			Node nodeObj = null;
			
			for(Node node : graph.getNodeSet())
			{
				
				if(node.getId().equals(br.getSubject().getIRI().toString()))
				{
					nodeSub=node;
					continue;
				}
				else
				{
					if(node.getId().equals(br.getObject().getIRI().toString()))
					{
						nodeObj=node;
						continue;
					}
				}
				
				if(nodeSub!=null && nodeObj!=null)
				{
					break;
				}
			}
			
			//Создает элемент линия для двух node.
			Edge edge = graph.addEdge(br.getBrachIndIRI().toString(), nodeSub, nodeObj, true);
			
			String labelEdge = this.model.getAnnotationValue(br.getPrp().getIRI());
			
			if(labelEdge.equals("_EMPTY_"))
			{
				labelEdge=br.getPrp().getIRI().getFragment().toString();
			} 
			
			edge.addAttribute("ui.label",  labelEdge);
		}

	}
	
	@Override
	public void setView(ViewVisGS view)
	{
		this.view = view;
	}

	@Override
	public ViewVisGS getView()
	{
		return this.view;
	}

}
