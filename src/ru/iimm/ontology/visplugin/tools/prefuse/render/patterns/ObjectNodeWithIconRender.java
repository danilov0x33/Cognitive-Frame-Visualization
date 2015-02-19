package ru.iimm.ontology.visplugin.tools.prefuse.render.patterns;

import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import prefuse.Constants;
import prefuse.render.LabelRenderer;
import prefuse.visual.VisualItem;
import ru.iimm.ontology.visplugin.tools.prefuse.tools.ConstantsPrefuse;

/**
 * Стандартный объект для визуализации паттернов онтологии.
 * @author Danilov E.Y.
 *
 */
public class ObjectNodeWithIconRender extends LabelRenderer
{
	public ObjectNodeWithIconRender()
	{
		super();
		
		this.setRoundedCorner(8, 8);
		this.setTextField(ConstantsPrefuse.DATA_LABEL_NODE);
		this.setImageField(ConstantsPrefuse.DATA_IMAGE_ICON_NODE);
		this.setHorizontalTextAlignment(Constants.RIGHT);
		this.setHorizontalImageAlignment(Constants.LEFT);
		//this.setHorizontalPadding(0);
		//this.setVerticalPadding(0);
		this.setMaxImageDimensions(32,32);		
	}
	
	@Override
	protected Shape getRawShape(VisualItem item)
	{
		//Shape s = super.getRawShape(item);
		
        if ( m_bbox instanceof RoundRectangle2D ) {
            RoundRectangle2D rr = (RoundRectangle2D)m_bbox;
            rr.setRoundRect(m_pt.getX(), m_pt.getY(), m_bbox.getWidth()+10, m_bbox.getHeight()+10,
                            2*m_arcWidth, 2*m_arcHeight);
        }
		
		return m_bbox;
	}
}
