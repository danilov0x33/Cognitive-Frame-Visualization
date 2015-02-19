package ru.iimm.ontology.visplugin.tools.prefuse.render;

import prefuse.action.assignment.ColorAction;
import prefuse.data.Graph;
import prefuse.visual.VisualItem;

/**
 * 
 * @author Danilov E.Y.
 *
 */
public class HighlightFartherNodeColor extends ColorAction
{
	private int[] m_colors;

	public HighlightFartherNodeColor( int size, int[] colors) {
	    super(Graph.NODES, VisualItem.FILLCOLOR);

	    m_colors = colors;

	}

	public void setColors( int[] colors) {
	    m_colors = colors;
	}

	public int[] getColors() {
	    return m_colors;
	}

	public int getColor( VisualItem item) {
	    if (item == null) {
		return 0;
	    }
	    int moi = -1 * (int) item.getDOI() - 1;
	    if (item.isHighlighted() && (moi >= 0)) {
		moi = moi >= m_colors.length ? m_colors.length - 1 : moi;
		return m_colors[moi];
	    }

	    return 0;
	}

}
