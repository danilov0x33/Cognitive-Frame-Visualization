package ru.iimm.ontology.visualization.tools.piccolo2d.patters;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import org.piccolo2d.PNode;
import org.piccolo2d.extras.swing.SwingLayoutNode;
import org.piccolo2d.extras.swing.SwingLayoutNode.Anchor;
import org.piccolo2d.extras.util.PFixedWidthStroke;
import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

import ru.iimm.ontology.visualization.patterns.DescriptionRealizationVis;
import ru.iimm.ontology.visualization.patterns.SituationRealizationVis;
import ru.iimm.ontology.visualization.patterns.elements.DescriptionElementVis;
import ru.iimm.ontology.visualization.patterns.elements.DottedLine;
import ru.iimm.ontology.visualization.patterns.elements.SituationElementVis;

/**
 * Билдер для создания визуальных объектов в Piccolo2D по "шаблонам".
 * @author Danilov
 * @version 0.2
 */
public class PPatternVisBuilder implements PPatternVisBuilderInt
{	
	private SituationRealizationVis situationRealVis;
	private DescriptionRealizationVis descriptionRealVis;
	
	public PPatternVisBuilder setSituationRealizationVis(SituationRealizationVis situationRealVis)
	{
		this.situationRealVis = situationRealVis;
		return this;
	}
	
	@Override
	public PPatternVisBuilder setDescriptionRealizationVis(DescriptionRealizationVis descriptionRealVis)
	{
		this.descriptionRealVis = descriptionRealVis;
		return this;
	}
	
	@Override
	public PNode buildSituationVis()
	{
		if(situationRealVis == null)
			return null;
		//Рисуем лейб с именем ситуации
		PText label = new PText(situationRealVis.getLabelElement());
		//создаем визуальных каркас (рамку) для ситуации
		PPath node = PPath.createRectangle(0, 0, label.getWidth()+10, 50);
		if(situationRealVis.getDottedLine() == DottedLine.DASHED)
		{
			node.setStroke(new PFixedWidthStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f,
	                new float[] { 6.0f }, 1.0f));
		}
		node.setPaint(situationRealVis.getBackgroudColor());
		//добовляем лейб в рамку
		node.addChild(label);
		//Расстояние между элементами ситуации
		double hGrap = 0; 
		//Элемент не имеющий визуальных образу,
		//но способный расставить элементы учитывая используемый менеджер компоновки
		SwingLayoutNode flowLayoutNode = new SwingLayoutNode(new FlowLayout(FlowLayout.LEFT,(int) hGrap,0));
		//Билдер для создания визуальных элементов паттерна
		PElementVisBuilder entityBuilder = new PElementVisBuilder();
		
		for(SituationElementVis elem : situationRealVis.getElements())
		{
			//Создаем визуальную комбинацию из элемента и стрелы
			PNode arrowNode = entityBuilder
					.setArrowElement(situationRealVis.getBinder())
					.setSituationElementVis(elem)
					.buildSituationElementWithArrow();
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

	@Override
	public PNode buildDescriptionVis()
	{
		if(this.descriptionRealVis == null)
			return null;
		
		//Рисуем лейб с именем ситуации
		PText label = new PText(descriptionRealVis.getLabelElement());
		//создаем визуальных каркас (рамку) для описания
		PPath node = PPath.createRectangle(0, 0, label.getWidth()+10, 50);
		if(descriptionRealVis.getDottedLine() == DottedLine.DASHED)
		{
			node.setStroke(new PFixedWidthStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f,
	                new float[] { 6.0f }, 1.0f));
		}
		node.setPaint(descriptionRealVis.getBackgroudColor());
		//добовляем лейб в рамку
		node.addChild(label);
		//Расстояние между концептами описания
		double hGrap = 0; 
		//Элемент не имеющий визуальный образ,
		//но способный расставить элементы учитывая используемый менеджер компоновки
		SwingLayoutNode flowLayoutNode = new SwingLayoutNode(new FlowLayout(FlowLayout.LEFT,(int) hGrap,0));
		//Билдер для создания визуальных элементов паттерна
		PElementVisBuilder entityBuilder = new PElementVisBuilder();
		
		for(DescriptionElementVis elem : descriptionRealVis.getElements())
		{
			//Создаем визуальную комбинацию из элемента и стрелы
			PNode arrowNode = entityBuilder
					.setArrowElement(descriptionRealVis.getBinder())
					.setDescriptionElementVis(elem)
					.buildDescriptionElementWithArrow();
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
        borderLayoutNode.addChild(flowLayoutNode, BorderLayout.SOUTH);
        borderLayoutNode.setAnchor(Anchor.WEST);
        borderLayoutNode.addChild(node, BorderLayout.NORTH);
        
        return borderLayoutNode;
	}
}
