package ru.iimm.ontology.visplugin.tools.prefuse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Iterator;

import javax.swing.JPanel;

import org.semanticweb.owlapi.model.OWLClass;

import prefuse.data.Tuple;
import prefuse.data.tuple.TupleSet;
import prefuse.visual.VisualItem;
import ru.iimm.ontology.visplugin.GUI.AbstractGUIVisualMethod;
import ru.iimm.ontology.visplugin.tools.VisNodeLinkPrefuseVisitor;
import ru.iimm.ontology.visplugin.tools.prefuse.tools.ConstantsPrefuse;
import ru.iimm.ontology.visplugin.tools.prefuse.tools.DefaultGraphPrefuse;

/**
 * Интерфейс для визуального метода, который построен на библиотеке Prefuse.
 * @author Danilov E.Y.
 *
 */
public class VisNodeLinkPrefuseGUI extends AbstractGUIVisualMethod
{
	/**Билдер для Prefuse.*/
	private VisNodeLinkPrefuseVisitor prefBuilder;
	/**Возвращаемая панель с визуальными компонентами.*/
	private JPanel mainPanel;
	
	public VisNodeLinkPrefuseGUI(VisNodeLinkPrefuseVisitor prefBuilder)
	{
		this.prefBuilder = prefBuilder;
		this.init();
		
		this.update();
	}
	

	private void init()
	{
		this.mainPanel = new JPanel(new BorderLayout());
		
		this.update();
	}

	@Override
	public void update()
	{
		BorderLayout layout = (BorderLayout) this.mainPanel.getLayout();
		Component comp = layout.getLayoutComponent(BorderLayout.CENTER);
		if(comp != null)
			this.mainPanel.remove(comp);
		
		if(this.prefBuilder != null)
		{			
			this.mainPanel.add(this.prefBuilder.getDisplay(),BorderLayout.CENTER);
		}
		
		this.mainPanel.updateUI();
	}

	@Override
	public Component getGUIComponent()
	{
		return this.mainPanel;
	}

	public void update(OWLClass owlClass) 
	{
		if(this.prefBuilder != null)
		{
			//Получаем дисплей из билдера
			DisplayPrefuse display = null;
			BorderLayout layout = (BorderLayout) this.mainPanel.getLayout();
			Component comp = layout.getLayoutComponent(BorderLayout.CENTER);
			if(comp != null)
				 display = (DisplayPrefuse)layout.getLayoutComponent(BorderLayout.CENTER);
			else
				return;
			
			DefaultGraphPrefuse graph = (DefaultGraphPrefuse) display.getGraph();
			TupleSet set = graph.getNodes();
			
			@SuppressWarnings("rawtypes")
			Iterator nodes = set.tuples();
			while(nodes.hasNext())
			{
				Tuple node = (Tuple) nodes.next();
				OWLClass owlCl = (OWLClass)node.get(ConstantsPrefuse.DATA_OWL_CLASS_NODE);
				//Находим нужный элемент
				if(owlCl.equals(owlClass))
				{
					VisualItem vItem = display.getVisualization().getVisualItem(ConstantsPrefuse.NODES, node);
					//Фокусируем камеру на нем
					display.centerNode(vItem);
					break;
				}
			}
		}
	}

}
