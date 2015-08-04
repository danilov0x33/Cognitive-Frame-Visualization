package ru.iimm.ontology.visualization.tools;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import prefuse.data.Edge;
import prefuse.data.Node;
import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;
import ru.iimm.ontology.cftools.Branch;
import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.DependencyCFrame;
import ru.iimm.ontology.cftools.PartonomyCFrame;
import ru.iimm.ontology.cftools.SpecialCFrame;
import ru.iimm.ontology.cftools.TaxonomyCFrame;
import ru.iimm.ontology.visualization.lang.Language;
import ru.iimm.ontology.visualization.tools.prefuse.DisplayPrefuse;
import ru.iimm.ontology.visualization.tools.prefuse.VisNodeLinkPrefuseGUI;
import ru.iimm.ontology.visualization.tools.prefuse.render.DefaultVisualizationPrefuse;
import ru.iimm.ontology.visualization.tools.prefuse.tools.ConstantsPrefuse;
import ru.iimm.ontology.visualization.tools.prefuse.tools.DefaultGraphPrefuse;
import ru.iimm.ontology.visualization.ui.GUIVisualMethodInt;

/**
 * Билдер для визуальной библиотеки Prefuse.
 * @author Danilov E.Y.
 *
 */
public class VisNodeLinkPrefuseVisitor extends AbstractVisualMethodVisitor
{

	/**Контейнер, в которой отображается граф.*/
	private DisplayPrefuse display;
	/**Граф префьюза.*/
	private DefaultGraphPrefuse graph;
	
	private VisNodeLinkPrefuseGUI prefuseGUI;
	
	private void initGUI()
	{
		this.prefuseGUI = new VisNodeLinkPrefuseGUI(this);
	}

	@Override
	public void visit(TaxonomyCFrame tFrame)
	{
		this.defaultVisit(tFrame);
		this.initGUI();
	}

	@Override
	public void visit(PartonomyCFrame pFrame)
	{
		this.defaultVisit(pFrame);
		this.initGUI();
	}


	@Override
	public void visit(DependencyCFrame dFrame)
	{
		this.defaultVisit(dFrame);
		this.initGUI();
	}


	@Override
	public void visit(SpecialCFrame sFrame)
	{
		this.defaultVisit(sFrame);
		this.initGUI();
	}
	
	private void addNodeWithOWLClass(Node root, OWLClass owlClass, OWLReasoner owlRes)
	{		
		for (OWLClass parentCls : owlRes.getSubClasses(owlClass, true).getFlattened())
		{
			if (parentCls.isOWLNothing()) continue;
			
			String label = parentCls.getIRI().getFragment();
			Node node = this.graph.addNode(label);
			node.set(ConstantsPrefuse.DATA_OWL_CLASS_NODE, parentCls);
			this.graph.addEdge(root, node);
			
			this.addNodeWithOWLClass(node, parentCls,owlRes);
		}
	}
	
	/*@Override
	public void visit(OWLOntologyManager manager, OWLOntology ontology, OWLReasoner reasoner)
	{
		
		this.graph = new DefaultGraphPrefuse();
		OWLOntology onto =ontology;

		OWLClass owlClass = onto.getOWLOntologyManager().getOWLDataFactory().getOWLThing();
		
		String label = owlClass.getIRI().getFragment();
		Node root = this.graph.addNode(label);
		root.set(ConstantsPrefuse.DATA_OWL_CLASS_NODE, owlClass);
		//Создаем элемены
		this.addNodeWithOWLClass(root, owlClass,reasoner);
		
		DefaultVisualizationPrefuse vis = new DefaultVisualizationPrefuse(graph);
		
		this.display = new DisplayPrefuse(vis,this.graph);	
		
		this.initGUI();
	}*/
	
	private void defaultVisit(CFrame frame)
	{
		this.graph = new DefaultGraphPrefuse();
		//this.graph.randNode();
		
		for(OWLNamedIndividual owlName : frame.getContent().getConcepts())
		{
			String label = this.cfOnt.getLabel(owlName.getIRI(), Language.UPO_LANG);
			if(label.equals("_EMPTY_"))
			{
					label=owlName.getIRI().getFragment().toString();
			}
			Node node = this.graph.addNode(label);
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
			
			for(int i=0; i < this.graph.getNodeCount(); i++)
			{
				Node node = this.graph.getNode(i);
				
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
					String labelEdge = this.cfOnt.getAnnotationValue(branch.getPrp().getIRI(), IRI.create(ConstantsOntConverter.UPO_TITLE_LABEL));
					
					if(labelEdge.equals("_EMPTY_"))
					{
						labelEdge=branch.getPrp().getIRI().getFragment().toString();
					} 
					
					Edge edge = this.graph.addEdge(labelEdge,sub, obj);
					break;
				}
			}
		}
		
		DefaultVisualizationPrefuse vis = new DefaultVisualizationPrefuse(graph);
		
		this.display = new DisplayPrefuse(vis,this.graph);	
	}


	/*@Override
	public GUIVisualMethodInt getVisualization()
	{
		return this.prefuseGUI;
	}

	@Override
	public void disposeView()
	{
		this.display.stop();
	}*/

	public DisplayPrefuse getDisplay()
	{
		this.display.start();
		return this.display;
	}

}
