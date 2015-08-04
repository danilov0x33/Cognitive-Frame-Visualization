package ru.iimm.ontology.visualization.ui.mvp.impl;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import ru.iimm.ontology.visualization.ui.mvp.ModelOntology;
import ru.iimm.ontology.visualization.ui.mvp.PresenterOntology;
import ru.iimm.ontology.visualization.ui.mvp.ViewTreeNode;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public class DefaultPresenterOwlClassTreeNode extends BasePresenterTreeNode implements PresenterOntology
{
	private ModelOntology model;
	private ViewTreeNode view;

	@Override
	public void setModel(ModelOntology model)
	{
		this.model = model;
	}

	@Override
	public ModelOntology getModel()
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
}
