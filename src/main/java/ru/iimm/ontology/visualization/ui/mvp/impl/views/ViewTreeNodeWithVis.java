package ru.iimm.ontology.visualization.ui.mvp.impl.views;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import ru.iimm.ontology.visualization.ui.mvp.views.View;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewVis;

/**
 * View содержит в себе множество деревьев объектов (CFrame, OWLClass) и визуализации.
 * @author Danilov
 * @version 0.1
 */
public class ViewTreeNodeWithVis implements View
{
	/**Панель, на которой размещается список классов из онтологии и панель с визуализацией.*/
	private JPanel mainPanel;
	private JSplitPane spPanel;
	private JTabbedPane classListTabPanel;
	private JTabbedPane visTabPanel;
	
	/**
	 * {@linkplain ViewTreeNodeWithVis}
	 */
	public ViewTreeNodeWithVis()
	{
		this.init();
	}
	
	private void init()
	{
		this.mainPanel = new JPanel(new BorderLayout());
		
		this.classListTabPanel = new JTabbedPane();
		this.visTabPanel = new JTabbedPane();
		
		this.spPanel = new JSplitPane();
		this.spPanel.setLeftComponent(this.classListTabPanel);
		this.spPanel.setRightComponent(this.visTabPanel);
		this.spPanel.setResizeWeight (0);
		this.spPanel.setDividerLocation (300);
		
		this.mainPanel.add(spPanel,BorderLayout.CENTER);
	}

	@Override
	public Component getViewComponent()
	{
		return this.mainPanel;
	}
	
	public void addTreeNode(Component comp, String tabName)
	{
		this.classListTabPanel.addTab(tabName, comp);
	}
	
	public void addVisualization(ViewVis view, String tabName)
	{
		view.open();

		this.visTabPanel.add(tabName, view.getViewComponent());
	}

	public void removeVisualization(ViewVis view, String tabName)
	{
		view.close();
		
		this.visTabPanel.remove(this.visTabPanel.indexOfTab(tabName));
	}
	
	
}

