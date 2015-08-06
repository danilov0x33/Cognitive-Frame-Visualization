	package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterOwlClassPrefuse;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public class RedirectingPresenterOwlClassTreeNode extends PresenterOwlClassTreeNodeImpl
{
	private PresenterOwlClassPrefuse presenter;
	
	/**
	 * {@linkplain RedirectingPresenterOwlClassTreeNode}
	 */
	public RedirectingPresenterOwlClassTreeNode(PresenterOwlClassPrefuse presenter)
	{
		this.presenter = presenter;
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
        	
        	if(nodeInfo instanceof OWLClass)
        	{
        		OWLClass owlClass = (OWLClass) nodeInfo;

        		this.presenter.centerOwlClass(owlClass);
        	}
        }
	}
}
