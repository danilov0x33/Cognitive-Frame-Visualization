package ru.iimm.ontology.visualization.tools.prefuse.render;

import prefuse.render.LabelRenderer;
import ru.iimm.ontology.visualization.tools.prefuse.tools.ConstantsPrefuse;

/**
 * Стандартный стил отображения элемента.
 * @author Danilov E.Y.
 *
 */
public class DefaultNodeRender extends LabelRenderer
{
	public DefaultNodeRender()
	{
		super();
		this.setRoundedCorner(8, 8);
		this.setTextField(ConstantsPrefuse.DATA_LABEL_NODE);
	}
	

}
