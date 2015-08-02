package ru.iimm.ontology.visplugin.tools.graphStream;

import java.util.HashSet;

import org.graphstream.graph.implementations.SingleNode;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
/**
 * Элемент Node для графа.
 * @author Danilov
 *
 */
public class GraphNode extends SingleNode
{	
	/**Список всех входящих элементов.*/
	private HashSet<GraphNode> enteringNodeList;
	
	/**Список всех исходящих элементов.*/
	private HashSet<GraphNode> leavingNodeList;
	
	private DefaultGraph graph;
	
	public GraphNode(DefaultGraph graph,String id)
	{
		super(graph,id);
		this.graph = graph;
		this.setLabel(id);
		enteringNodeList = new HashSet<>();
		leavingNodeList = new HashSet<>();
	}
	
	public void setOWLNamedIndividual(OWLNamedIndividual owlNamedIndividual)
	{
		this.addAttribute("OWLNamedIndividual", owlNamedIndividual);
	}
	
	public OWLNamedIndividual getOWLNamedIndividual()
	{
		return this.getAttribute("OWLNamedIndividual");
	}
	
	/*public GraphNode(String id)
	{
		super(new TempGraph("Graph_"+id),id);
	}
	*/
	
	public void addEnterNode(GraphNode node)
	{
		this.enteringNodeList.add(node);
	}
	
	public HashSet<GraphNode> getEnteringNodeList()
	{
		return this.enteringNodeList;
	}
	
	public void addLeaveNode(GraphNode node)
	{
		this.leavingNodeList.add(node);
	}
	
	public HashSet<GraphNode> getLeavingNodeList()
	{
		return this.leavingNodeList;
	}
	
	public void setGroupEntLeavNode(boolean bool)
	{
		this.addAttribute("isOrganization", bool);
	}
	
	public boolean isGroupEntLeavNode()
	{
		return (boolean)this.getAttribute("isOrganization");
	}
	
	public void setLabel(String label)
	{
		
		this.addAttribute("ui.label",  label);
	}
	
	public String getLabel()
	{
		return this.getAttribute("ui.label");
	}
	
	public void setVisible(boolean visible)
	{
		if(!visible)
			this.graph.getNode(id).addAttribute("ui.hide");
		else
			this.graph.getNode(id).removeAttribute("ui.hide");
	}
	
	public boolean isVisible()
	{
		return this.hasArray("ui.hide");
	}
	
}




/*class TempGraph extends AbstractGraph
{

	public TempGraph(String id)
	{
		super(id);
	}

	@Override
	public <T extends Node> T getNode(String id)
	{
		return null;
	}

	@Override
	public <T extends Node> T getNode(int index)
	{
		return null;
	}

	@Override
	public <T extends Edge> T getEdge(String id)
	{
		return null;
	}

	@Override
	public <T extends Edge> T getEdge(int index)
	{
		return null;
	}

	@Override
	public int getNodeCount()
	{
		return 0;
	}

	@Override
	public int getEdgeCount()
	{
		return 0;
	}

	@Override
	public <T extends Node> Iterator<T> getNodeIterator()
	{
		return null;
	}

	@Override
	public <T extends Edge> Iterator<T> getEdgeIterator()
	{
		return null;
	}

	@Override
	protected void addNodeCallback(AbstractNode node)
	{
		
	}

	@Override
	protected void addEdgeCallback(AbstractEdge edge)
	{		
	}

	@Override
	protected void removeNodeCallback(AbstractNode node)
	{

	}

	@Override
	protected void removeEdgeCallback(AbstractEdge edge)
	{

	}

	@Override
	protected void clearCallback()
	{

	}
	
}*/
