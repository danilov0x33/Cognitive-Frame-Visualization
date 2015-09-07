package ru.iimm.ontology.visualization.tools.prefuse.render;

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import prefuse.Visualization;
import prefuse.action.layout.Layout;
import prefuse.data.Schema;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.PrefuseLib;
import prefuse.visual.DecoratorItem;
import prefuse.visual.EdgeItem;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;
import ru.iimm.ontology.visualization.tools.prefuse.tools.ConstantsPrefuse;

/**
 * Поведение для ребра: имя для ребра отображается в центре ребра.
 * @author Danilov E.Y.
 *
 */
public class EdgeLabelLayout extends Layout
{
	private DefaultRendererFactory drf;
	
    private static final int textColor = ColorLib.rgb(0, 200, 0);

    private static final Font textFont = FontLib.getFont("Tahoma", 11); 
	
	public EdgeLabelLayout( Visualization vis,DefaultRendererFactory drf)
	{
		super(ConstantsPrefuse.EDGE_LABEL_DEC);
		
		this.setVisualization(vis);
		
		this.drf = drf;
		
		this.init();
	}
	

	private void init()
	{
		//Создаем поведение для элемента/ребра
		Schema EDGE_DECORATOR_SCHEMA = PrefuseLib.getVisualItemSchema();

		//Отключаем передвижение/перетаскивание ярлыка
		EDGE_DECORATOR_SCHEMA.setDefault(VisualItem.INTERACTIVE, false); // noninteractive
		//Цвет
		EDGE_DECORATOR_SCHEMA.setDefault(VisualItem.TEXTCOLOR, textColor);
		//Шрифт
		EDGE_DECORATOR_SCHEMA.setDefault(VisualItem.FONT, textFont);
		//Добовляем поведение
		m_vis.addDecorators(ConstantsPrefuse.EDGE_LABEL_DEC, ConstantsPrefuse.EDGES, EDGE_DECORATOR_SCHEMA);
		//Добовляем в рендер
		drf.add(new InGroupPredicate(ConstantsPrefuse.EDGE_LABEL_DEC), new LabelRenderer(ConstantsPrefuse.DATA_LABEL_EDGE));
	}

	public void run(double frac)
	{
		
		Iterator iter = m_vis.items(m_group);
		while (iter.hasNext())
		{
			DecoratorItem decorator = (DecoratorItem) iter.next();
			EdgeItem decoratedEdgeItem = (EdgeItem) decorator
					.getDecoratedItem();

			if (decoratedEdgeItem.isVisible())
			{
				decorator.setVisible(true);
				Rectangle2D bounds = decoratedEdgeItem.getBounds();
				this.setX(decorator, null, this.getX(decoratedEdgeItem, bounds));
				this.setY(decorator, null, this.getY(decoratedEdgeItem, bounds));
			} 
			else
			{
				decorator.setVisible(false);
			}
		}

    }

	private double getX(VisualItem decoratedItem, Rectangle2D bounds)
	{
		return bounds.getCenterX();
	}

	private double getY(VisualItem decoratedItem, Rectangle2D bounds)
	{
		return bounds.getCenterY();
	}

}
