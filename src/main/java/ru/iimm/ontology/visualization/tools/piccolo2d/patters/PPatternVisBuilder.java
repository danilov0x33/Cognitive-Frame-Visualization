package ru.iimm.ontology.visualization.tools.piccolo2d.patters;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import org.piccolo2d.PNode;
import org.piccolo2d.extras.swing.SwingLayoutNode;
import org.piccolo2d.extras.swing.SwingLayoutNode.Anchor;
import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

import ru.iimm.ontology.visualization.patterns.SituationRealizationVis;
import ru.iimm.ontology.visualization.patterns.elements.SituationElementVis;

/**
 * Билдер для создания визуальных объектов в Piccolo2D по "шаблонам".
 * @author Danilov
 * @version 0.1
 */
public class PPatternVisBuilder implements PPatternVisBuilderInt
{	
	private SituationRealizationVis realizationVis;
	
	public PPatternVisBuilder setRealizationVis(SituationRealizationVis realizationVis)
	{
		this.realizationVis = realizationVis;
		return this;
	}
	
	@Override
	public PNode buildSituationVis()
	{
		if(realizationVis == null)
			return null;
		//Рисуем лейб с именем ситуации
		PText label = new PText(realizationVis.getLabelElement());
		//создаем визуальных каркас (рамку) для ситуации
		PNode node = PPath.createRectangle(0, 0, label.getWidth()+10, 50);
		node.setPaint(realizationVis.getBackgroudColor());
		//добовляем лейб в рамку
		node.addChild(label);
		//Расстояние между элементами ситуации
		double hGrap = 0; 
		//Элемент не имеющий визуальных образу,
		//но способный расставить элементы учитывая используемый менеджер компоновки
		SwingLayoutNode flowLayoutNode = new SwingLayoutNode(new FlowLayout(FlowLayout.LEFT,(int) hGrap,0));
		//Билдер для создания визуальных элементов паттерна
		PElementVisBuilder entityBuilder = new PElementVisBuilder();
		
		for(SituationElementVis elem : realizationVis.getElements())
		{
			//Через билдер создаем виз. объект: стрела
			PNode arrow = entityBuilder.setArrowElement(realizationVis.getBinder()).buildArrowElementVis();
			//Поворачиваем стрелу на 90 градусов
			arrow.rotate(Math.toRadians(90));
			//Получем размер высоты (т.к. в будущем она повернется на 90)
			double width = arrow.getHeight();
			//Через билдер создаем виз. образ элемента паттерна.
			PNode visElem = entityBuilder.setSituationElementVis(elem).buildSituationElementVis();
			//Создаем менеджер компоновки...
			SwingLayoutNode arrowNode = new SwingLayoutNode(new BorderLayout(0,0));
			//и добовляем объекты
			arrowNode.setAnchor(Anchor.CENTER);
			arrowNode.addChild(visElem,BorderLayout.CENTER);
			arrowNode.setAnchor(Anchor.WEST);
			arrowNode.addChild(arrow,BorderLayout.SOUTH);
			//стрелку по центру объекта паттерна
			arrow.setOffset(visElem.getXOffset() + visElem.getWidth()/2, arrow.getYOffset());
			//Задаем ширину менеджера компоновки с учетом самого большого виз. объекта из 2-х
			//т.к. он не увеличивается при добовлении объектов
			arrowNode.setWidth((visElem.getWidth() > width ? visElem.getWidth() : width) + hGrap);
			//добовляем виз. комбинацию из виз. объект паттерна и стрелки
			flowLayoutNode.addChild(arrowNode);
			//и увеличиваем ширину
			flowLayoutNode.setWidth(flowLayoutNode.getWidth() + arrowNode.getWidth());
		}
		//Ставим значение ширины рамки = ширине "панели" с виз. элементами
		node.setWidth(flowLayoutNode.getWidth());
		//ставим лейб в центре рамки
		label.setOffset((node.getWidth()/2 - label.getWidth()/2),
				(node.getHeight()/2 - label.getHeight()/2));
		//объединяем рамку и объекты
		SwingLayoutNode borderLayoutNode = new SwingLayoutNode(new BorderLayout(0,0));
        borderLayoutNode.setAnchor(Anchor.CENTER);
        borderLayoutNode.addChild(flowLayoutNode, BorderLayout.NORTH);
        borderLayoutNode.setAnchor(Anchor.WEST);
        borderLayoutNode.addChild(node, BorderLayout.SOUTH);
        
        return borderLayoutNode;
	}
}
