package ru.iimm.ontology.visplugin.tools.prefuse.render;

import prefuse.Constants;
import prefuse.render.EdgeRenderer;

/**
 * Стандартный стиль ребра.
 * @author Danilov E.Y.
 *
 */
public class DefaultEdgeRenderer extends EdgeRenderer
{
	public DefaultEdgeRenderer()
	{
		super();
		this.init();
	}

	private void init()
	{
		this.setEdgeType(Constants.EDGE_TYPE_LINE);
		this.setArrowType(Constants.EDGE_ARROW_FORWARD);
		this.setArrowHeadSize(20, 20);
		this.setDefaultLineWidth(2);
	}
}
