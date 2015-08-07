package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import javax.swing.tree.TreeSelectionModel;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterTreeNode;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewTreeNode;

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
