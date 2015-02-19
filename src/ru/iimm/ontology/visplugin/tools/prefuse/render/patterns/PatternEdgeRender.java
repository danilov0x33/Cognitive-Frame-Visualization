package ru.iimm.ontology.visplugin.tools.prefuse.render.patterns;

import prefuse.Constants;
import prefuse.render.EdgeRenderer;

/**
 * Рендер ребер для визуализации паттернов онтологии.
 * @author Danilov E.Y.
 *
 */
public class PatternEdgeRender extends EdgeRenderer
{
	public PatternEdgeRender()
	{
		this.setEdgeType(Constants.EDGE_TYPE_LINE);
		this.setArrowType(Constants.EDGE_ARROW_FORWARD);
		this.setArrowHeadSize(10, 5);
		this.setDefaultLineWidth(10);
	}
}
