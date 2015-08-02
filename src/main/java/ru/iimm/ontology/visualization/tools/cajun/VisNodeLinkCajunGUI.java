package ru.iimm.ontology.visualization.tools.cajun;

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
import ru.iimm.ontology.visualization.tools.VisNodeLinkCajunVisitor;
import ru.iimm.ontology.visualization.ui.AbstractGUIVisualMethod;

/**
 * Интерфейс для визуального метода, построенный на библиотеке Cajun.
 * @author Danilov E.Y.
 *
 */
public class VisNodeLinkCajunGUI extends AbstractGUIVisualMethod
{

	/**Визуальный билдер.*/
	private VisNodeLinkCajunVisitor activCajunBuilder;
	/**Панель, на которой расположены*/
	private JPanel mainPanel;
	/**Панель, на которой размещается визуализация.*/
	private JPanel viewPanel;
	/**ToolBar с различными элементами управления графом.*/
	private JToolBar toolbar;
	
	public VisNodeLinkCajunGUI(VisNodeLinkCajunVisitor visNodeLinkCajunBuilder)
	{
		this.activCajunBuilder = visNodeLinkCajunBuilder;
		
		this.init();
		
		this.update();
		
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
	
	@Override
	public void update()
	{
		this.viewPanel.removeAll();
		
		if(this.activCajunBuilder == null)
			return;
		
		this.viewPanel.add(new PScrollPane(this.activCajunBuilder.getDefaultGraphModel().getGraph().getCanvas()),BorderLayout.CENTER);
		
		FlatGraph graph = this.activCajunBuilder.getDefaultGraphModel().getGraph();
		
		this.toolbar.removeAll();
		
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
	
	private JButton addToolBarAction(Action action) {
		JButton btn = this.toolbar.add(action);
		btn.setToolTipText((String) action.getValue(Action.NAME));
		return btn;
	}

	@Override
	public Component getGUIComponent()
	{
		return this.mainPanel;
	}

}
