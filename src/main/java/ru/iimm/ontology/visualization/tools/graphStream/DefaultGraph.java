package ru.iimm.ontology.visualization.tools.graphStream;

import java.util.ArrayList;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
/**
 * Пользовательский граф.
 * @author Danilov
 *
 */
public class DefaultGraph extends MultiGraph
{
	/**
	 * Список всех Node.
	 */
	private ArrayList<GraphNode> nodeList;
	
	/**
	 * Список всех связей.
	 */
	private ArrayList<GraphEdge> edgeList;
	
	public DefaultGraph(String id)
	{
		super(id, true,false);
		nodeList=new ArrayList<GraphNode>();
		edgeList=new ArrayList<GraphEdge>();
	}
	
	public DefaultGraph(String id, boolean strictChecking, boolean autoCreate,
			int initialNodeCapacity, int initialEdgeCapacity)
	{
		super(id, strictChecking, autoCreate, initialNodeCapacity, initialEdgeCapacity);
	}

	public DefaultGraph(String id, boolean strictChecking, boolean autoCreate)
	{
		super(id, strictChecking, autoCreate);
	}
	
	/*public AbstractEdge[] getGraphEdge()
	{
		return this.edgeArray;
	}
	
	public AbstractNode[] getGraphNode()
	{
		return this.nodeArray;
	}*/
	
	public void removeAllNode()
	{
		for(GraphNode node : this.nodeList)
		{
			this.removeNode(node.getId());
		}
	}
	
	public void removeAllEdge()
	{		
		for(GraphEdge edge : this.edgeList)
		{
			this.removeEdge(edge.getId());
		}
	}
	
	public void addNode(ArrayList<GraphNode> nodeList)
	{
		for(GraphNode node : nodeList)
		{
			this.addNode(node);
		}
	}
	
	public void addEdge(ArrayList<GraphEdge> edgeList)
	{
		for(GraphEdge edge : edgeList)
		{
			this.addEdge(edge);
		}
	}
	
	public Node addNode(GraphNode graphNode)
	{
		for(GraphNode node : this.nodeList)
		{
			if(node.getId().equals(graphNode.getId()))
			{
				return graphNode;
			}
		}
		
		this.addNodeCallback(graphNode);
		nodeList.add(graphNode);
		
		return graphNode;
	}
	
	public Edge addEdge(GraphEdge graphEdge)
	{
		this.addEdgeCallback(graphEdge);
		
		edgeList.add(graphEdge);
		
		return graphEdge;
	}
	
	public ArrayList<GraphNode> getNodeList()
	{
		return this.nodeList;
	}
	
	public ArrayList<GraphEdge> getEdgeList()
	{
		return this.edgeList;
	}
	

}
