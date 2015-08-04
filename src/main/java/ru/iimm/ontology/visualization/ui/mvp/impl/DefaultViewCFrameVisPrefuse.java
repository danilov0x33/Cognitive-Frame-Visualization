package ru.iimm.ontology.visualization.ui.mvp.impl;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

import ru.iimm.ontology.visualization.tools.prefuse.DisplayPrefuse;
import ru.iimm.ontology.visualization.tools.prefuse.render.DefaultVisualizationPrefuse;
import ru.iimm.ontology.visualization.tools.prefuse.tools.DefaultGraphPrefuse;
import ru.iimm.ontology.visualization.ui.mvp.PresenterCFramePrefuseVisitor;
import ru.iimm.ontology.visualization.ui.mvp.ViewCFrameVisPrefuse;

/**
 * Реализация интерфейса View для визуализации CFrame с помощью Prefuse.
 * @author Danilov
 * @version 0.1
 */
public class DefaultViewCFrameVisPrefuse implements ViewCFrameVisPrefuse
{
	/**Presenter.*/
	private PresenterCFramePrefuseVisitor presenter;
	
	/**Возвращаемая панель с визуальными компонентами.*/
	private JPanel mainPanel;
	/**Контейнер, в которой отображается граф.*/
	private DisplayPrefuse display;
	/**Граф префьюза.*/
	private DefaultGraphPrefuse graph;
	
	/**
	 * {@linkplain DefaultViewCFrameVisPrefuse}
	 */
	public DefaultViewCFrameVisPrefuse()
	{
		this.init();
	}
	
	private void init()
	{
		this.mainPanel = new JPanel(new BorderLayout());
		
		
	}

	@Override
	public void setPresenter(PresenterCFramePrefuseVisitor presenter)
	{
		this.presenter = presenter;
	}

	@Override
	public PresenterCFramePrefuseVisitor getPresenter()
	{
		return this.presenter;
	}

	@Override
	public void open()
	{
		this.graph = new DefaultGraphPrefuse();
		
		DefaultVisualizationPrefuse vis = new DefaultVisualizationPrefuse(graph);
		
		this.display = new DisplayPrefuse(vis,this.graph);
		
		this.mainPanel.add(this.display,BorderLayout.CENTER);
	}

	@Override
	public void close()
	{
		this.display.stop();
	}

	@Override
	public Component getViewComponent()
	{
		return this.mainPanel;
	}

	@Override
	public DefaultGraphPrefuse getGraph()
	{
		return this.graph;
	}

	@Override
	public DisplayPrefuse getDisplay()
	{
		return this.display;
	}

}
