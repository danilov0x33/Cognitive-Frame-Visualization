package ru.iimm.ontology.visualization.ui.mvp.impl.views;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

import org.piccolo2d.PNode;
import org.piccolo2d.extras.pswing.PSwingCanvas;

import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisPiccolo2D;

/**
 * Реализация интерфейса View для визуализации с помощью Piccolo2D.
 * @author Danilov
 * @version 0.1
 */
public abstract class BaseViewVisPiccolo2D implements ViewVisPiccolo2D
{
	private JPanel mainPanel;
	private PSwingCanvas piccoloGraphic;
	
	/**
	 * {@linkplain BaseViewVisPiccolo2D}
	 */
	public BaseViewVisPiccolo2D()
	{
		this.mainPanel = new JPanel(new BorderLayout());
		this.piccoloGraphic = new PSwingCanvas();
	}
	
	@Override
	public PNode getLayer()
	{
		return this.piccoloGraphic.getLayer();
	}
	
	@Override
	public void open()
	{
		this.mainPanel.add(this.piccoloGraphic, BorderLayout.CENTER);
	}

	@Override
	public void close()
	{
		this.mainPanel.remove(piccoloGraphic);
		this.piccoloGraphic.getLayer().removeAllChildren();
	}

	@Override
	public Component getViewComponent()
	{
		return this.mainPanel;
	}

}
