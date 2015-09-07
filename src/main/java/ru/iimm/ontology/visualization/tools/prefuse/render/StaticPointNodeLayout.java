package ru.iimm.ontology.visualization.tools.prefuse.render;

import java.util.Iterator;

import prefuse.action.layout.Layout;
import prefuse.visual.NodeItem;

/**
 * Layout для фиксации элементов в графе.
 * @author Danilov E.Y.
 *
 */
public class StaticPointNodeLayout extends Layout
{
	public StaticPointNodeLayout(String group) {
        super(group);
    }

	@Override
	public void run(double frac)
	{
		Iterator iter = m_vis.items(m_group);
		while (iter.hasNext())
		{
			NodeItem aNodeItem = (NodeItem) iter.next();

			//aNodeItem.setX( aNodeItem.getFloat(ConstantsPrefuse.DATA_X_POINT_NODE));
			//aNodeItem.setY( aNodeItem.getFloat(ConstantsPrefuse.DATA_Y_POINT_NODE));
			//aNodeItem.setDouble(ConstantsPrefuse.DATA_LENGTH_NODE, aNodeItem.getEndSize());

		}
	}
}
