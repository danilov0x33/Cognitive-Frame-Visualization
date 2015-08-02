package ru.iimm.ontology.visualization.ui;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTabbedPane;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.visualization.tools.AbstractVisualMethodVisitor;
import ru.iimm.ontology.visualization.tools.CFrameDecoratorInt;
import ru.iimm.ontology.visualization.tools.OWLModelManagerDecorator;
import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;

/**
 * Контейенер с графическими элементами для визуальных методов.
 * @author Danilov E.Y.
 *
 */
public class OntVisView extends AbstractGUIVisualMethod implements ObserverOntClassListInt
{
	/**Панель для визуализации.*/
	private JTabbedPane visTabPanel;
	
	public OntVisView()
	{
		this.init();
	}

	private void init()
	{	
		this.visTabPanel = new JTabbedPane();
		this.visTabPanel.setPreferredSize(new Dimension(500, 500));
	}
	
	@Override
    public void update(VisualMethodVisitorInt visualMethodBuilderInt)
    {
		this.visTabPanel.removeAll();
		
		this.addVisualMethodTab(visualMethodBuilderInt);
    }
	
	@Override
	public void update(CFrameDecoratorInt cfDec)
	{		
		int selectTab = this.visTabPanel.getSelectedIndex();
		
		if(selectTab == -1)
			selectTab = 0;
		
		this.visTabPanel.removeAll();
		
		for (VisualMethodVisitorInt visB : cfDec.getVisualMethodList())
		{
			this.addVisualMethodTab(visB);
		}
		
		this.visTabPanel.setSelectedIndex(selectTab);
	}
	
	@Override
	public void update(OWLClass owlClass, OWLModelManagerDecorator owlModelManagerDecorator)
	{		
		this.visTabPanel.removeAll();
		
		for (AbstractVisualMethodVisitor visB : owlModelManagerDecorator.getVisualMethodList())
		{
			this.addVisualMethodTab(visB);
		}
	}

	public Component getGUIComponent()
	{
		return this.visTabPanel;
	}

	/**
	 * Добовляет визуализацию во вкладку
	 * 
	 * @param visualMethod
	 */
	private void addVisualMethodTab(VisualMethodVisitorInt visualMethod)
	{
		this.visTabPanel.addTab(visualMethod.getNameVisualMethod(), visualMethod.getVisualization().getGUIComponent());
	}

	@Override
    public void update()
    {
		this.visTabPanel.updateUI();
    }

}
