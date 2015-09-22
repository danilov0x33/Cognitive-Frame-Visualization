package ru.iimm.ontology.visualization.patterns;

import java.awt.Color;

import ru.iimm.ontology.visualization.patterns.elements.DottedLine;
/**
 * Визуальный образ элемента.
 * @author Danilov
 * @version 0.1
 */
public abstract class ElementVis
{
	/**Имя элемента.*/
	protected String labelElement;
	/**Цвет.*/
	protected Color backgroudColor;
	/**Пунктир.*/
	protected DottedLine dottedLine;

	public ElementVis()
	{
		this.labelElement = "unknow_elem";
		this.backgroudColor = Color.WHITE;
		this.dottedLine = DottedLine.SOLID;
	}
	
	/**
	 * @return the {@linkplain #backgroudColor}
	 */
	public Color getBackgroudColor()
	{
		return backgroudColor;
	}

	/**
	 * @param backgroudColor the {@linkplain #backgroudColor} to set
	 */
	public void setBackgroudColor(Color backgroudColor)
	{
		this.backgroudColor = backgroudColor;
	}
	
	/**
	 * @return the {@linkplain #labelElement}
	 */
	public String getLabelElement()
	{
		return labelElement;
	}

	/**
	 * @param labelElement the {@linkplain #labelElement} to set
	 */
	public void setLabelElement(String labelElement)
	{
		this.labelElement = labelElement;
	}

	/**
	 * @return the {@linkplain #dottedLine}
	 */
	public DottedLine getDottedLine()
	{
		return dottedLine;
	}

	/**
	 * @param dottedLine the {@linkplain #dottedLine} to set
	 */
	public void setDottedLine(DottedLine dottedLine)
	{
		this.dottedLine = dottedLine;
	}	
}
