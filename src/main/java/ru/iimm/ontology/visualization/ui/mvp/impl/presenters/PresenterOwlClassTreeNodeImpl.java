package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import ru.iimm.ontology.visualization.tools.CFrameDecorator;
import ru.iimm.ontology.visualization.ui.mvp.models.ModelOwlOntology;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterOntology;

/**
 * Реализация интерфейса Presenter для дерева с OWLClass'ами.
 * @author Danilov
 * @version 0.1
 */
public class PresenterOwlClassTreeNodeImpl extends BasePresenterTreeNode implements PresenterOntology
{
	/**Модель.*/
	private ModelOwlOntology model;

	@Override
	public void setModel(ModelOwlOntology model)
	{
		this.model = model;
	}

	@Override
	public ModelOwlOntology getModel()
	{
		return this.model;
	}

	@Override
	public void selectedTreeNode(TreeSelectionEvent event)
	{
		
	}

	@Override
	public void updateTreeNodeFromModel()
	{
		OWLOntology onto = this.model.getOWLOntology();
		
		OWLClass owlClass = onto.getOWLOntologyManager().getOWLDataFactory().getOWLThing();
		
		DefaultMutableTreeNode top = this.view.getTopNode();
		top.removeAllChildren();
		
		this.addOwlClassTree(top, owlClass, this.model.getOWLReasoner());
	}

	private void addOwlClassTree(DefaultMutableTreeNode top, OWLClass owlClass, OWLReasoner owlRes)
	{		
		for (OWLClass childrenClass : owlRes.getSubClasses(owlClass, true).getFlattened())
		{
			if (childrenClass.isOWLNothing()) continue;
			
			DefaultMutableTreeNode tree = new DefaultMutableTreeNode(childrenClass);
			this.addOwlClassTree(tree, childrenClass, owlRes);
			top.add(tree);
		}
	}

	@Override
	public void updateCellRenderer()
	{
		this.view.setCellRenderer(new TreeCellRenderer()
		{
			 private JLabel label = new JLabel();
		     
	            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
	                Object o = ((DefaultMutableTreeNode) value).getUserObject();
	                if (o instanceof OWLClass) {
	                	OWLClass owlClass = (OWLClass) o;
	            		
	                    //label.setIcon(icon);
	                    label.setText(owlClass.getIRI().getFragment());
	                    
	                } 
	                else 
	                {
	                    label.setIcon(null);
	                    label.setText("" + value);
	                }
	                
	                //Font f = label.getFont();
	                if(selected)
	                {
	                	label.setForeground(Color.BLUE);
	                	//label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
	                }
	                else
	                {
	                	label.setForeground(Color.BLACK);
	                	//label.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
	                }
	                
	                return label;
	            }
		});
	}
}
