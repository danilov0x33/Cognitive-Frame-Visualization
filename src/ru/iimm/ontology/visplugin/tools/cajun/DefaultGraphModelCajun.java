package ru.iimm.ontology.visplugin.tools.cajun;

import ru.iimm.ontology.visplugin.tools.cajun.render.tools.ConstantCajun;
import ca.uvic.cs.chisel.cajun.graph.DefaultGraphModel;
import ca.uvic.cs.chisel.cajun.graph.FlatGraph;
import ca.uvic.cs.chisel.cajun.graph.arc.DefaultGraphArcStyle;
import ca.uvic.cs.chisel.cajun.graph.arc.GraphArc;
import ca.uvic.cs.chisel.cajun.graph.node.DefaultGraphNodeStyle;
import ca.uvic.cs.chisel.cajun.graph.node.GraphNode;

/**
 * Оболочка для cajun-графа.
 * @author Danilov E.Y.
 *
 */
public class DefaultGraphModelCajun extends DefaultGraphModel
{
	/**Граф.*/
	private FlatGraph graph;
	/**ID для нового элемента.*/
	private int nextNodeID;
	/**ID для новой дуги.*/
	private int nextArcID;
	
	public DefaultGraphModelCajun()
	{
		super();
		this.graph = new FlatGraph(this);
		
		this.nextNodeID = 0;
		this.nextArcID = 0; 
	}
	
	/**
	 * Добавить элемент.
	 * @param userObject - пользовательский объект.
	 * @param label - имя элемента
	 * @param target - является ли элемент комцептом.
	 * @return
	 */
	public GraphNode addNode(Object userObject, String label, boolean target)
	{
		GraphNode node = null;
		if(target)
		{
			node = super.addNode(userObject, label, null, ConstantCajun.NODE_TYPE_TARGET);
		}
		else
		{
			node =  super.addNode(userObject, label, null, ConstantCajun.NODE_TYPE_DEFAULT);
		}
		 
		
		return node;
	}
	
	/**
	 * Добавить элемент.
	 * @param label - имя элемента
	 * @return
	 */
	public GraphNode addNode(String label)
	{
		GraphNode node = this.addNode(this.nextNodeID(), label, false);
		
		return node;
	}
	
	/**
	 * Добавить ребро.
	 * @param userObject - пользовательский объект. 
	 * @param source
	 * @param target
	 * @return
	 */
	public GraphArc addArc(Object userObject, GraphNode source, GraphNode target)
	{
		GraphArc arc = this.addArc(userObject, source, target, null);
		
		return arc;
	}
	
	public GraphArc addArc( GraphNode source, GraphNode target)
	{
		GraphArc arc = this.addArc(this.nextArcID(), source, target, null);
		
		return arc;
	}
	
	public void setGraphNodeStyle(DefaultGraphNodeStyle nodeStyle)
	{
		this.graph.setGraphNodeStyle(nodeStyle);
	}
	
	public void setGraphArcStyle(DefaultGraphArcStyle arcStyle)
	{
		this.graph.setGraphArcStyle(arcStyle);
	}
	
	private Long nextNodeID() {
		Long id = new Long(this.nextNodeID);
		this.nextNodeID++;
		return id;
	}
	
	private Long nextArcID() {
		Long id = new Long(this.nextArcID);
		this.nextArcID++;
		return id;
	}
	
	public FlatGraph getGraph()
	{
		return this.graph;
	}

}
