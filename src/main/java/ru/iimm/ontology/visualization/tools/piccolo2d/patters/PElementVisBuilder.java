package ru.iimm.ontology.visualization.tools.piccolo2d.patters;

import java.awt.Polygon;

import org.piccolo2d.PNode;
import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

import ru.iimm.ontology.visualization.patterns.elements.ArrowElementVis;
import ru.iimm.ontology.visualization.patterns.elements.SituationElementVis;

/**
 * Билдер для создания визуальных образов Piccolo2D по шаблонам.
 * @author Danilov
 * @version 0.1
 */
public class PElementVisBuilder implements PElementVisBuilderInt
{
	private SituationElementVis entity;
	private ArrowElementVis arrow;
	
	public PElementVisBuilder setSituationElementVis(SituationElementVis entity)
	{
		this.entity = entity;
		return this;
	}
	
	public PElementVisBuilder setArrowElement(ArrowElementVis arrow)
	{
		this.arrow = arrow;
		return this;
	}
	
	@Override
	public PNode buildSituationElementVis()
	{
		//Создаем лейбл.
		PText label = new PText(entity.getLabelElement());
		double width = label.getWidth() < 60 ? 60 : label.getWidth();
		//Создаем каркас элемента.
		PNode returnNode = PPath.createRectangle(0, 0, width, 50);
		returnNode.setPaint(entity.getBackgroudColor());
		returnNode.addChild(label);
		//ставил лейбл по-центру.
		label.setOffset((returnNode.getWidth()/2 - label.getWidth()/2),
				(returnNode.getHeight()/2 - label.getHeight()/2));
		
		return returnNode;
	}


	@Override
	public PNode buildArrowElementVis()
	{
		int width = 30;//ширина и
		int height = 20;//высота основания
		int edgeW = 15; //ширана и
		int edgeH = 20; //высота "наконечника"
		//Создание стрелки полигонами по кругу восьми точками по X и Y
		//    >>>>>>>>>>>>> 
		//    ^      |\    V 
		//    ^ |----| \   V
		// start|       \  V
		//finish|       /  V
		//     ^|----| /   V
		//     ^     |/    V
		//     ^<<<<<<<<<<<
		int[] xpointsNew = {0,       0, 0+width,       0+width,0+width+edgeW,       0+width, 0+width,0,0};
    	int[] ypointsNew = {0,0-height,0-height,0-height-edgeH,            0,0+height+edgeH,0+height,0+height};
    	
    	PNode arrow = new PPath.Double(new Polygon(xpointsNew, ypointsNew, 8));
    	arrow.setPaint(this.arrow.getBackgroudColor());

		return arrow;
	}

}
