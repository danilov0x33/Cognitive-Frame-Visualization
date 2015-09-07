package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import ru.iimm.ontology.visualization.tools.CFrameDecorator;
import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;

/**
 * Presenter - перенаправляющий event'ы из TreeNode в presenter'ы визуализаций.
 * @author Danilov
 * @version 0.1
 */
public class RedirectingPresenterCFrameTreeNode extends PresenterCFrameTreeNodeImpl
{
	/**Список presenter'ов визуализаций.*/
	private ArrayList<VisualMethodVisitorInt> visList;
	
	/**
	 * {@linkplain RedirectingPresenterCFrameTreeNode}
	 */
	public RedirectingPresenterCFrameTreeNode()
	{
		this.visList = new ArrayList<VisualMethodVisitorInt>();
	}
	
	@Override
	public void selectedTreeNode(TreeSelectionEvent event)
	{
		super.selectedTreeNode(event);

        if (event.getSource() instanceof JTree) 
        {         
        	JTree jtree = (JTree) event.getSource();
        	
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode)jtree.getLastSelectedPathComponent();

        	if (node == null) return;

        	Object nodeInfo = node.getUserObject();
        	
        	if(nodeInfo instanceof CFrameDecorator)
        	{
        		CFrameDecorator cframe = (CFrameDecorator) nodeInfo;

        		for(VisualMethodVisitorInt vis : this.visList)
        		{
        			cframe.accept(vis);
        		}
        	}
        }
	}
	
	public void addVisualMethod(VisualMethodVisitorInt vis)
	{
		this.visList.add(vis);
	}
	
	public void removeVisualMethod(VisualMethodVisitorInt vis)
	{
		this.visList.remove(vis);
	}
}
