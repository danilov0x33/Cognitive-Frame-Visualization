package ru.iimm.ontology.visualization.ui.mvp.impl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import ru.iimm.ontology.visualization.ui.mvp.PresenterTreeNode;
import ru.iimm.ontology.visualization.ui.mvp.ViewTreeNode;

/**
 * Реализация ViewTreeNode.
 * @author Danilov
 * @version 0.1
 */
public class DefaultViewTreeNode implements ViewTreeNode
{
	/**Presenter.*/
	private PresenterTreeNode present;
	/**Дерево.*/
	private JTree cfOntClassTree;
	/**Панель с деревом.*/
	private JPanel mainPanel;
	
	/**
	 * {@linkplain }
	 */
	public DefaultViewTreeNode()
	{
		this.init();
	}
	
	private void init()
	{
		this.mainPanel = new JPanel();
		this.mainPanel.removeAll();
		this.mainPanel.setLayout(new BorderLayout());
		this.mainPanel.setPreferredSize(new Dimension(300, 100));

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Thing");
		
		this.cfOntClassTree = new JTree(top);
  
        //При нажатии на элемеет (OWLClass из дерева) оповещаем слушателей.
		this.cfOntClassTree.addTreeSelectionListener(new TreeSelectionListener()
		{
			@Override
			public void valueChanged(TreeSelectionEvent arg0)
			{
				present.selectedTreeNode(arg0);
			}
		});
		
		
		JScrollPane northScroll = new JScrollPane(this.cfOntClassTree);
		
		this.mainPanel.add(northScroll,BorderLayout.CENTER);
	}

	@Override
	public PresenterTreeNode getPresenter()
	{
		return this.present;
	}

	@Override
	public void setPresenter(PresenterTreeNode presenter)
	{
		this.present = presenter;
		
		this.present.updateTreeNodeFromModel();
		
		this.cfOntClassTree.expandRow(0);
		
		this.present.updateCellRenderer();
	}

	@Override
	public void setCellRenderer(TreeCellRenderer render)
	{
		this.cfOntClassTree.setCellRenderer(render);		
	}

	@Override
	public void setSelectionMode(int mode)
	{
		this.cfOntClassTree.getSelectionModel().setSelectionMode(mode);
	}

	@Override
	public Component getViewComponent()
	{
		return this.mainPanel;
	}

	@Override
	public DefaultMutableTreeNode getTopNode()
	{
		return (DefaultMutableTreeNode) this.cfOntClassTree.getModel().getRoot();
	}
}
