package ru.iimm.ontology.visualization.tools.prefuse.render;

import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.visual.EdgeItem;
import ru.iimm.ontology.visualization.tools.prefuse.tools.ConstantsPrefuse;

/**
 * Layout для фиксированной длинны ребер.
 * @author Danilov E.Y.
 *
 */
public class DefaultForceDirectedLayout extends ForceDirectedLayout
{

	public DefaultForceDirectedLayout(String group, boolean enforceBounds)
	{
		super(group, enforceBounds);
	}

	@Override
	protected float getSpringLength(EdgeItem e)
	{
		float ret = e.getFloat(ConstantsPrefuse.DATA_LENGTH_EDGE);
		return ret;
	}
}
