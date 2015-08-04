package ru.iimm.ontology.visualization.ui.mvp;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

/**
 * View, который отображает коллекцию в виде дерава.
 * @author Danilov
 * @version 0.1
 */
public interface ViewTreeNode extends View
{
	/**
	 * Рендер элементов в дереве.
	 * @param render - Рендер.
	 */
	void setCellRenderer(TreeCellRenderer render);
	/**
	 * Задает режим выбора элементов в дереве.
	 * @param mode
	 */
	void setSelectionMode(int mode);
	/**
	 * Возвращяет root-элемент из дерева.
	 * @return - Root-элемент.
	 */
	DefaultMutableTreeNode getTopNode();

	public PresenterTreeNode getPresenter();
	public void setPresenter(PresenterTreeNode presenter);
	
	

}
