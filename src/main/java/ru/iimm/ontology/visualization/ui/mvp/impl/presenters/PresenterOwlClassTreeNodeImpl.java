package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

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
}
