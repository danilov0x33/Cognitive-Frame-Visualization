package ru.iimm.ontology.visualization.tools.cajun.render;

import java.awt.Color;
import java.awt.GradientPaint;

import ca.uvic.cs.chisel.cajun.graph.node.DefaultGraphNodeStyle;
import ru.iimm.ontology.visualization.tools.cajun.ConstantCajun;

/**
 * Стандарт стиль для элементов.
 * @author Danilov E.Y.
 *
 */
public class DefaultNodeStyleCajun extends DefaultGraphNodeStyle
{

	public DefaultNodeStyleCajun()
	{
		super();
		
		this.nodeTypeToPaint.put(ConstantCajun.NODE_TYPE_TARGET, 
				new GradientPaint(0, 0, Color.ORANGE, 0, 20, Color.ORANGE, true));
		
		this.nodeTypeToPaint.put(ConstantCajun.NODE_TYPE_DEFAULT, 
				new GradientPaint(0, 0, Color.WHITE, 0, 20, Color.WHITE, true));
		
	}
}
