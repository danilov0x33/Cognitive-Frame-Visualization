package ru.iimm.ontology.visualization.ui.mvp.impl;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import ru.iimm.ontology.visualization.tools.CFrameDecorator;
import ru.iimm.ontology.visualization.ui.mvp.PresenterTreeNode;
import ru.iimm.ontology.visualization.ui.mvp.ViewTreeNode;

/**
 * Абстрактный класс для Presenter'ов для TreeNode.
 * @author Danilov
 * @version 0.1
 */
public abstract class BasePresenterTreeNode implements PresenterTreeNode
{
	/**Визуальный компонент.*/
	protected ViewTreeNode view;
	
	@Override
	public void updateSelectionMode()
	{
		this.view.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

	@Override
	public void updateCellRenderer()
	{
		this.view.setCellRenderer(new TreeCellRenderer()
		{
            private JLabel label = new JLabel();
     
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Object o = ((DefaultMutableTreeNode) value).getUserObject();
                if (o instanceof CFrameDecorator) 
                {
                	CFrameDecorator cFrameDecorator = (CFrameDecorator) o;

                    label.setText(cFrameDecorator.getTypeCFrame());
                } 
                else 
                {
                    label.setIcon(null);
                    label.setText("" + value);
                }
                
                if(selected)
                {
                	label.setForeground(Color.BLUE);
                }
                else
                {
                	label.setForeground(Color.BLACK);
                }
                
                return label;
            }
		});
	}
	
	@Override
	public ViewTreeNode getView()
	{
		return this.view;
	}

	@Override
	public void setView(ViewTreeNode view)
	{
		this.view = view;
	}
}
