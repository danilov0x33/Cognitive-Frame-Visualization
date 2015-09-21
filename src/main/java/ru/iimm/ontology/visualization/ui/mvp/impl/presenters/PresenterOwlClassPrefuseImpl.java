package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import java.util.Iterator;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import prefuse.data.Node;
import prefuse.data.Tuple;
import prefuse.data.tuple.TupleSet;
import prefuse.visual.VisualItem;
import ru.iimm.ontology.visualization.tools.prefuse.tools.ConstantsPrefuse;
import ru.iimm.ontology.visualization.tools.prefuse.tools.DefaultGraphPrefuse;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewOWLClassPrefuseImpl;
import ru.iimm.ontology.visualization.ui.mvp.models.ModelOwlOntology;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterOwlClassPrefuse;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisPrefuse;

/**
 * Реализация интерфейса {@linkplain PresenterOwlClassPrefuse}
 * @author Danilov
 * @version 0.2
 */
public class PresenterOwlClassPrefuseImpl implements PresenterOwlClassPrefuse
{
	private ModelOwlOntology model;
	private ViewVisPrefuse view;
	
	/**
	 * {@linkplain PresenterOwlClassPrefuseImpl}
	 */
	public PresenterOwlClassPrefuseImpl()
	{
		ViewOWLClassPrefuseImpl viewOwlPrefuse = new ViewOWLClassPrefuseImpl();
		viewOwlPrefuse.setPresenter(this);
		this.view = viewOwlPrefuse;
	}
	
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
	public void setView(ViewVisPrefuse view)
	{
		this.view = view;
	}

	@Override
	public ViewVisPrefuse getView()
	{
		return this.view;
	}

	@Override
	public void centerOwlClass(OWLClass owlClass)
	{
		if(view == null)
			return;
		
		TupleSet set = this.view.getGraph().getNodes();
		
		@SuppressWarnings("rawtypes")
		Iterator nodes = set.tuples();
		while(nodes.hasNext())
		{
			Tuple node = (Tuple) nodes.next();
			OWLClass owlCl = (OWLClass)node.get(ConstantsPrefuse.DATA_OWL_CLASS_NODE);
			//Находим нужный элемент
			if(owlCl.equals(owlClass))
			{
				VisualItem vItem = this.view.getDisplay().getVisualization().getVisualItem(ConstantsPrefuse.NODES, node);
				//Фокусируем камеру на нем
				this.view.getDisplay().centerNode(vItem);
				break;
			}
		}
	}

	
	
	private void addNodeWithOWLClass(Node root, OWLClass owlClass, OWLReasoner owlRes)
	{		
		for (OWLClass parentCls : owlRes.getSubClasses(owlClass, true).getFlattened())
		{
			if (parentCls.isOWLNothing()) continue;
			
			String label = parentCls.getIRI().getFragment();
			
			Node node = this.view.getGraph().addNode(label);	
			node.set(ConstantsPrefuse.DATA_OWL_CLASS_NODE, parentCls);
			
			this.view.getGraph().addEdge(root, node);
			
			this.addNodeWithOWLClass(node, parentCls,owlRes);
		}
	}


	@Override
	public void updateGraphFromModel()
	{
		DefaultGraphPrefuse graph = this.view.getGraph();

		OWLClass owlClass = this.model.getOntologyManager().getOWLDataFactory().getOWLThing();
		
		String label = owlClass.getIRI().getFragment();
		Node root = graph.addNode(label);
		root.set(ConstantsPrefuse.DATA_OWL_CLASS_NODE, owlClass);
		//Создаем элемены
		this.addNodeWithOWLClass(root, owlClass,this.model.getOWLReasoner());
		
		this.view.getDisplay().start();
	}
	
}
