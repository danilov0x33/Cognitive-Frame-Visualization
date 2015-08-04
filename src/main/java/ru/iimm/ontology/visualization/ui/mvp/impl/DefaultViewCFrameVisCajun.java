package ru.iimm.ontology.visualization.ui.mvp.impl;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ca.uvic.cs.chisel.cajun.actions.ClearOrphansAction;
import ca.uvic.cs.chisel.cajun.actions.FocusOnHomeAction;
import ca.uvic.cs.chisel.cajun.actions.LayoutAction;
import ca.uvic.cs.chisel.cajun.actions.NoZoomAction;
import ca.uvic.cs.chisel.cajun.actions.ZoomInAction;
import ca.uvic.cs.chisel.cajun.actions.ZoomOutAction;
import ca.uvic.cs.chisel.cajun.graph.FlatGraph;
import edu.umd.cs.piccolox.swing.PScrollPane;
import ru.iimm.ontology.visualization.tools.cajun.DefaultGraphModelCajun;
import ru.iimm.ontology.visualization.tools.cajun.render.DefaultArcStyleCajun;
import ru.iimm.ontology.visualization.tools.cajun.render.DefaultNodeStyleCajun;
import ru.iimm.ontology.visualization.ui.mvp.PresenterCFrameCajunVisitor;
import ru.iimm.ontology.visualization.ui.mvp.ViewCFrameVisCajun;

/**
 * Реализация интерфейса View для визуализации CFrame с помощью Cajun.
 * @author Danilov
 * @version 0.1
 */
public class DefaultViewCFrameVisCajun implements ViewCFrameVisCajun
{
	
	private PresenterCFrameCajunVisitor presenter;
	/**Панель, на которой расположены.*/
	private JPanel mainPanel;
	/**Панель, на которой размещается визуализация.*/
	private JPanel viewPanel;
	/**ToolBar с различными элементами управления графом.*/
	private JToolBar toolbar;
	/**Граф.*/
	private DefaultGraphModelCajun defaultGraphModel;
	
	/**
	 * {@linkplain DefaultViewCFrameVisCajun}
	 */
	public DefaultViewCFrameVisCajun()
	{
		this.init();
	}
	
	private void init()
	{
		this.mainPanel = new JPanel(new BorderLayout());
		
		this.viewPanel = new JPanel(new BorderLayout());
		
		this.toolbar = new JToolBar(JToolBar.HORIZONTAL);
		this.toolbar.setFloatable(false);
		this.toolbar.setBorder(BorderFactory.createRaisedBevelBorder());
		
		this.mainPanel.add(this.toolbar,BorderLayout.NORTH);
		this.mainPanel.add(this.viewPanel,BorderLayout.CENTER);
		
	}
	
	private void addToolBarAction(Action action) 
	{
		JButton btn = this.toolbar.add(action);
		btn.setToolTipText((String) action.getValue(Action.NAME));
	}
	
	@Override
	public Component getViewComponent()
	{
		return this.mainPanel;
	}

	@Override
	public void open()
	{
		this.defaultGraphModel = new DefaultGraphModelCajun();
		this.defaultGraphModel.setGraphArcStyle(new DefaultArcStyleCajun());
		this.defaultGraphModel.setGraphNodeStyle(new DefaultNodeStyleCajun());
		
		this.viewPanel.add(new PScrollPane(this.defaultGraphModel.getGraph().getCanvas()),BorderLayout.CENTER);
		
		FlatGraph graph = this.defaultGraphModel.getGraph();
	
		addToolBarAction(new FocusOnHomeAction(graph.getAnimationHandler()));
		addToolBarAction(new ClearOrphansAction(graph.getModel(), graph));

		this.toolbar.addSeparator();

		// Layouts
		for (LayoutAction action : graph.getLayouts()) {
			addToolBarAction(action);
		}

		this.toolbar.addSeparator();

		// zoom
		addToolBarAction(new ZoomInAction(graph.getCamera()));
		addToolBarAction(new NoZoomAction(graph.getCamera()));
		addToolBarAction(new ZoomOutAction(graph.getCamera()));
	}

	

	@Override
	public void close()
	{

	}
	
	@Override
	public PresenterCFrameCajunVisitor getPresenter()
	{
		return this.presenter;
	}
	
	@Override
	public void setPresenter(PresenterCFrameCajunVisitor presenter)
	{
		this.presenter = presenter;
	}

	@Override
	public DefaultGraphModelCajun getGraph()
	{
		return this.defaultGraphModel;
	}

}
