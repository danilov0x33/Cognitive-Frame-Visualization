package ru.iimm.ontology.visualization.tools.piccolo2d.patters;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Polygon;

import org.piccolo2d.PNode;
import org.piccolo2d.extras.swing.SwingLayoutNode;
import org.piccolo2d.extras.swing.SwingLayoutNode.Anchor;
import org.piccolo2d.extras.util.PFixedWidthStroke;
import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

import ru.iimm.ontology.visualization.patterns.elements.ArrowElementVis;
import ru.iimm.ontology.visualization.patterns.elements.DescriptionElementVis;
import ru.iimm.ontology.visualization.patterns.elements.DottedLine;
import ru.iimm.ontology.visualization.patterns.elements.SituationElementVis;

/**
 * Билдер для создания визуальных образов Piccolo2D по шаблонам.
 * @author Danilov
 * @version 0.2
 */
public class PElementVisBuilder implements PElementVisBuilderInt
{
	private SituationElementVis entity;
	private DescriptionElementVis concept;
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
		PPath returnNode = PPath.createRectangle(0, 0, width, 50);
		if(this.entity.getDottedLine() == DottedLine.DASHED)
		{
			returnNode.setStroke(new PFixedWidthStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f,
	                new float[] { 6.0f }, 1.0f));
		}
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
    	
    	PPath arrow = new PPath.Double(new Polygon(xpointsNew, ypointsNew, 8));
    	arrow.setPaint(this.arrow.getBackgroudColor());

    	if(this.arrow.getDottedLine() == DottedLine.DASHED)
    	{
    		arrow.setStroke(new PFixedWidthStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f,
                    new float[] { 6.0f }, 1.0f));
    	}
    	
		return arrow;
	}

	public PNode buildSituationElementWithArrow()
	{		
		//Через билдер создаем виз. объект: стрела
		PNode arrow = this.buildArrowElementVis();
		//Поворачиваем стрелу на 90 градусов
		arrow.rotate(Math.toRadians(90));
		//Получем размер высоты (т.к. в будущем она повернется на 90)
		double width = arrow.getHeight();
		//Через билдер создаем виз. образ элемента паттерна.
		PNode visElem = this.buildSituationElementVis();
		//Создаем менеджер компоновки...
		SwingLayoutNode arrowNode = new SwingLayoutNode(new BorderLayout(0,0));
		//и добовляем объекты
		arrowNode.setAnchor(Anchor.WEST);
		arrowNode.addChild(arrow,BorderLayout.SOUTH);
		arrowNode.setAnchor(Anchor.CENTER);
		arrowNode.addChild(visElem,BorderLayout.CENTER);
		//стрелку по центру объекта паттерна
		arrow.setOffset(visElem.getXOffset() + visElem.getWidth()/2, arrow.getYOffset() - 5);
		//Задаем ширину менеджера компоновки с учетом самого большого виз. объекта из 2-х
		//т.к. он не увеличивается при добовлении объектов
		arrowNode.setWidth(visElem.getWidth() > width ? visElem.getWidth() : width);
		
		return arrowNode;
	}

	@Override
	public PElementVisBuilder setDescriptionElementVis(DescriptionElementVis concept)
	{
		this.concept = concept;
		return this;
	}

	@Override
	public PNode buildDescriptionElementVis()
	{
		//Создаем лейбл.
		PText label = new PText(this.concept.getLabelElement());
		double width = label.getWidth() < 60 ? 60 : label.getWidth();
		//Создаем каркас элемента.
		PPath returnNode = PPath.createRectangle(0, 0, width, 50);
		if(this.concept.getDottedLine() == DottedLine.DASHED)
		{
			returnNode.setStroke(new PFixedWidthStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f,
	                new float[] { 6.0f }, 1.0f));
		}
		returnNode.setPaint(concept.getBackgroudColor());
		returnNode.addChild(label);
		//ставил лейбл по-центру.
		label.setOffset((returnNode.getWidth()/2 - label.getWidth()/2),
				(returnNode.getHeight()/2 - label.getHeight()/2));
		
		return returnNode;
	}

	@Override
	public PNode buildDescriptionElementWithArrow()
	{
		//Через билдер создаем виз. объект: стрела
		PNode arrow = this.buildArrowElementVis();
		//Поворачиваем стрелу на 90 градусов
		arrow.rotate(Math.toRadians(270));
		//Получем размер высоты
		double width = arrow.getHeight();
		//Через билдер создаем виз. образ элемента паттерна.
		PNode visElem = this.buildDescriptionElementVis();
		//Создаем менеджер компоновки...
		SwingLayoutNode arrowNode = new SwingLayoutNode(new BorderLayout(0,0));
		//и добовляем объекты
		arrowNode.setAnchor(Anchor.CENTER);
		arrowNode.addChild(visElem,BorderLayout.CENTER);
		arrowNode.setAnchor(Anchor.WEST);
		arrowNode.addChild(arrow,BorderLayout.NORTH);
		//стрелку по центру объекта паттерна
		arrow.setOffset(visElem.getXOffset() + visElem.getWidth()/2,
				visElem.getYOffset() );
		//Задаем ширину менеджера компоновки с учетом самого большого виз. объекта из 2-х
		//т.к. он не увеличивается при добовлении объектов
		arrowNode.setWidth(visElem.getWidth() > width ? visElem.getWidth() : width);
		
		return arrowNode;
	}
	
}
