package ru.iimm.ontology.visplugin.tools;

import java.awt.BorderLayout;
import java.util.HashSet;
import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.ui.layout.HierarchicalLayout;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;
import ru.iimm.ontology.cftools.Branch;
import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.DependencyCFrame;
import ru.iimm.ontology.cftools.PartonomyCFrame;
import ru.iimm.ontology.cftools.SpecialCFrame;
import ru.iimm.ontology.cftools.TaxonomyCFrame;
import ru.iimm.ontology.visplugin.GUI.GUIVisualMethodInt;
import ru.iimm.ontology.visplugin.lang.Language;
import ru.iimm.ontology.visplugin.tools.graphStream.DefShortcutManager;
import ru.iimm.ontology.visplugin.tools.graphStream.DefaultGraph;
import ru.iimm.ontology.visplugin.tools.graphStream.GraphEdge;
import ru.iimm.ontology.visplugin.tools.graphStream.GraphNode;
import ru.iimm.ontology.visplugin.tools.graphStream.VisNodeLinkGraphStreamGUI;
import ru.iimm.ontology.visplugin.tools.graphStream.style.Style;

/**
 * Билдер для визуальной библиотеки Graph Stream.
 * @author Danilov E.Y.
 *
 */
public class VisNodeLinkGraphStreamVisitor extends AbstractVisualMethodVisitor
{
	private static final Logger log = LoggerFactory.getLogger(VisNodeLinkGraphStreamVisitor.class);
	
	/**Граф со всеми элементами.*/
	private DefaultGraph graph;
	
	private VisNodeLinkGraphStreamGUI gsGUI;
	
	/**Список baseIRI*/
	private HashSet<String> baseIRIList;
	
	private Viewer viewer;
	
	private View view;
	

	private void initGUI()
	{
		this.gsGUI = new VisNodeLinkGraphStreamGUI(this);
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
	
	public GUIVisualMethodInt getVisualization()
	{			
		return this.gsGUI;
	}

	@Override
	public void disposeView()
	{
		this.view.setEnabled(false);
		this.view.setMouseManager(null);
	}
	
	private void defaultVisit(CFrame frame) 
	{
		System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		this.graph = new DefaultGraph("GraphForCFrame");
		
		new Style(graph);

		this.baseIRIList = new HashSet<String>();
		
		//Получение всех элементов и формирование из них визуального части.
		for(OWLNamedIndividual owlName : frame.getContent().getConcepts())
		{
			baseIRIList.add(owlName.getIRI().getStart());
			
			GraphNode node = new GraphNode(graph,owlName.getIRI().toString());
			
			String label = this.cfOnt.getLabel(owlName.getIRI(), Language.UPO_LANG);
			if(label.equals("_EMPTY_"))
			{
				label=owlName.getIRI().getFragment().toString();
				/*OWLClass cls = this.cfOnt.df.getOWLClass(owlName.getIRI());
				OWLAnnotationProperty labelRDF = this.cfOnt.df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());
				for (OWLAnnotation annotation : cls.getAnnotations(this.cfOnt.ontInMem, labelRDF)) {
					  if (annotation.getValue() instanceof OWLLiteral) {
					    OWLLiteral val = (OWLLiteral) annotation.getValue();
					    // look for portuguese labels - can be skipped
					      if (val.hasLang(Language.UPO_LANG)) {
					        //Get your String here
					    	  label = new String(val.getLiteral());
					    	  //System.out.println(cls + " labelled " + val.getLiteral());
					    	  break;
					      }
					   }
					}*/
				
			}
			
			node.setLabel(label);
			node.setOWLNamedIndividual(owlName);
			node.setGroupEntLeavNode(false);
			
			if(owlName.getIRI().equals(frame.getTrgConcept().getIRI()))
			{
				node.setAttribute("ui.class", "concept");
			}

			graph.addNode(node);
		}
		
		//Связывает все элементы линиями.
		for (Branch br : frame.getContent().getBranches()) 
		{
			GraphNode nodeSub=null;
			GraphNode nodeObj=null;
			
			for(GraphNode node : graph.getNodeList())
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
			GraphEdge edge = new GraphEdge(br.getBrachIndIRI().toString(),nodeSub, nodeObj, true);
			
			String labelEdge = this.cfOnt.getAnnotationValue(br.getPrp().getIRI(), IRI.create(ConstantsOntConverter.UPO_TITLE_LABEL));
			
			if(labelEdge.equals("_EMPTY_"))
			{
				labelEdge=br.getPrp().getIRI().getFragment().toString();
			} 
			
			edge.setLabel(labelEdge);


			graph.addEdge(edge);

			/*String branch=br.getSubject().getIRI().getFragment().toString()
					+"==="
					+br.getPrp().getIRI().getFragment().toString()
					+"===>"
					+br.getObject().getIRI().getFragment().toString();
			
			borderList.addElement(branch);*/
		}
		
		//Визуальная огранизация элементов начинается с "любого" элемента.
		/*for(GraphNode node : graph.getNodeList())
		{
			//log.info("OrganizationNode=BEGIN");
			organizatorNodes(node);
			//log.info("OrganizationNode=END");
			break;
		}*/
		
		this.viewer = new Viewer(graph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		this.viewer.enableAutoLayout(new HierarchicalLayout());

		this.view = this.viewer.addDefaultView(false);
		view.setLayout(new BorderLayout());

		/*view.setMouseManager(new DefMouseManager(
				this.informationCFramePanel));*/
		
		this.view.setShortcutManager(new DefShortcutManager());
		
		this.view.setEnabled(false);

	}
	
	/**
	 * Рекурсивный метод для визуальной огранизации элементов.
	 * @param mainNodeID - ID элемента с которого перешли на другой. 
	 * Для того, чтобы недопустить бесконечный цикл.
	 * @param node - Элемент для визуального построения.
	 * @param x - Координата X.
	 * @param y - Координата Y.
	 */
	@SuppressWarnings("unused")
	private void organizatorNodes(GraphNode node)
	{
		double dist = 0.5;		
		
		node.setGroupEntLeavNode(true);
		
		groupNode(node.getEnteringNodeList());
		
		//Получение списка всех входящих елементов.
		for(GraphNode entNode : node.getEnteringNodeList())
		{		
			//Если он уже сформирован, то нужно его пропустить.			
			if(entNode.isGroupEntLeavNode())
			{
				continue;				
			}
			
			log.info("Node: "+ node.getAttribute("ui.label") + " <== "+ entNode.getAttribute("ui.label"));
			
			//Вызываем этот же метод для след. элемента.			
			organizatorNodes(entNode);
		}
		
		groupNode(node.getLeavingNodeList());
		
	  //Получение списка всех исходящих елементов.
		for(GraphNode leaveNode : node.getLeavingNodeList())
		{	
			//Если он уже сформирован, то нужно его пропустить.			
			if(leaveNode.isGroupEntLeavNode())
			{
				continue;				
			}
			
			log.info("Node: "+ node.getAttribute("ui.label") + " ==> "+ leaveNode.getAttribute("ui.label"));
			
			//Вызываем этот же метод для след. элемента.			
			organizatorNodes(leaveNode);
		}
		
		
	}
	
	
	/**
	 * Группирует элементы.
	 * @param nodeList
	 */
	private void groupNode(HashSet<GraphNode> nodeList)
	{
		if(nodeList.size()<2)
			return;
		
		Iterator<GraphNode> iterNode = nodeList.iterator();
		
		
		GraphNode node1=null;
		GraphNode node2=iterNode.next();
		
		while(iterNode.hasNext())
		{
			node1=node2;
			
			node2=iterNode.next();
			//System.out.println("Group: " + node1.getId()+"_GroupNode_"+node2.getId());
			try
			{
			Edge edge = graph.addEdge(node1.getId()+"_GroupNode_"+node2.getId(),node1.getId(), node2.getId(), false);
			edge.addAttribute("ui.class", "group");

			double weight = 0.1;
			//weight+=(node1.getLabel().length()*0.005)+(node2.getLabel().length()*0.005);
			edge.addAttribute("layout.weight", weight);
			}
			catch(IdAlreadyInUseException ex)
			{
				log.error(ex.getMessage());
			}
			catch(EdgeRejectedException ex)
			{
				log.error(ex.getMessage());
			}
		}
		
	}

	/**
	 * @return
	 */
	public DefaultGraph getGraph()
	{
		return this.graph;
	}

	public HashSet<String> getBaseIRIList()
	{
		return baseIRIList;
	}

	public View getView()
	{
		return this.view;
	}

}
