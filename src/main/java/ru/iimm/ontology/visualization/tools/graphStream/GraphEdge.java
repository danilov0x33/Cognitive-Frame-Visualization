package ru.iimm.ontology.visualization.tools.graphStream;

import org.graphstream.graph.implementations.AbstractEdge;


/**
 * Елемент типа линия/связь/Edge.
 * @author Danilov E.Y.
 *
 */
public class GraphEdge extends AbstractEdge
{	
	/*public GraphEdge(String id, AbstractNode source, AbstractNode target,boolean directed)
	{
		super(id, source, target, directed);
	}*/
	
	public GraphEdge(String id, GraphNode source, GraphNode target,boolean directed)
	{
		super(id, source, target, directed);
		source.addLeaveNode(target);
		target.addEnterNode(source);
	}
	
	public String getLabel()
	{
		return this.getAttribute("ui.label");
	}

	public void setLabel(String label)
	{
		this.setAttribute("ui.label", label);
	}
	
}
