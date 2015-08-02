package ru.iimm.ontology.visualization.tools.prefuse.render;

import java.awt.event.MouseEvent;
import java.util.Iterator;

import prefuse.controls.ControlAdapter;
import prefuse.visual.EdgeItem;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

/**
 * @author Danilov E.Y.
 *
 */
public class HighlightFartherNodeControl extends ControlAdapter
{
	private boolean highlightWithInvisibleEdge = false;

	public HighlightFartherNodeControl()
	{

	}

	public void itemEntered(VisualItem item, MouseEvent e)
	{
		if (item instanceof NodeItem)
			setNeighborHighlight((NodeItem) item, true);
	}

	/**
	 * @see prefuse.controls.Control#itemExited(prefuse.visual.VisualItem,
	 *      java.awt.event.MouseEvent)
	 */
	public void itemExited(VisualItem item, MouseEvent e)
	{
		if (item instanceof NodeItem)
			setNeighborHighlight((NodeItem) item, false);
	}

	/**
	 * Set the highlighted state of the neighbors of a node.
	 * 
	 * @param n
	 *            the node under consideration
	 * @param state
	 *            the highlighting state to apply to neighbors
	 */
	protected void setNeighborHighlight(NodeItem n, boolean state)
	{
		Iterator iter = n.edges();
		while (iter.hasNext())
		{
			EdgeItem eitem = (EdgeItem) iter.next();
			NodeItem nitem = eitem.getAdjacentItem(n);
			if (eitem.isVisible() || highlightWithInvisibleEdge)
			{
				eitem.setHighlighted(state);
				nitem.setHighlighted(state);
				nitem.setDOI(-1);
			}
		}
		/*
		 * if ( activity != null ) n.getVisualization().run(activity);
		 */
	}

	/**
	 * Indicates if neighbor nodes with edges currently not visible still get
	 * highlighted.
	 * 
	 * @return true if neighbors with invisible edges still get highlighted,
	 *         false otherwise.
	 */
	public boolean isHighlightWithInvisibleEdge()
	{
		return highlightWithInvisibleEdge;
	}

	/**
	 * Determines if neighbor nodes with edges currently not visible still get
	 * highlighted.
	 * 
	 * @param highlightWithInvisibleEdge
	 *            assign true if neighbors with invisible edges should still get
	 *            highlighted, false otherwise.
	 */
	public void setHighlightWithInvisibleEdge(boolean highlightWithInvisibleEdge)
	{
		this.highlightWithInvisibleEdge = highlightWithInvisibleEdge;
	}
}
