package ru.iimm.ontology.visplugin.tools.prefuse;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import prefuse.data.Node;
import prefuse.visual.NodeItem;
import ru.iimm.ontology.visplugin.tools.prefuse.render.patterns.PatternVisualizationPrefuse;
import ru.iimm.ontology.visplugin.tools.prefuse.tools.ConstantsPrefuse;
import ru.iimm.ontology.visplugin.tools.prefuse.tools.PatternGraphPrefuse;

public class startPrefuse
{
	public static void main(String[] argv)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			
			@Override
			public void run()
			{				
				JFrame frame = new JFrame("prefuse!");

		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        
		        PatternGraphPrefuse gr = new PatternGraphPrefuse();
		        //gr.randNode();
		        
		        PatternVisualizationPrefuse vis = new PatternVisualizationPrefuse(gr);

		        Node node = gr.addObject("Формирование маршрута");
		        Node node2 = gr.addObject("Выбор подходящего маршрута");
		        
		        gr.addEdge(node, node2);
		        
		        int idEvent = gr.createEventObject("Действие по определение маршрута");
		        
		        gr.addObjectToEvent(idEvent, (NodeItem) vis.getVisualItem(ConstantsPrefuse.NODES,node));
		        gr.addObjectToEvent(idEvent, (NodeItem) vis.getVisualItem(ConstantsPrefuse.NODES,node2));
		        
		        Node taskNode = gr.addTaskObject("Задача сетевой маршрутизации");
		        
		        gr.addTaskToEvent(idEvent, taskNode);
		        
		        Node node3 = gr.addProcessObject("Исполняемый программный процесс маршрутизации");
		        
		        Node node4 = gr.addObject("Логическая сеть");
		        Node node5 = gr.addObject("Таблица маршрутизации");
		        
		        gr.addEdgeObjectToEvent(idEvent, node3);
		        gr.addEdgeObjectToEvent(idEvent, node4);
		        gr.addEdgeObjectToEvent(idEvent, node5);
		        
		        final DisplayPrefuse displayPrefuse = new DisplayPrefuse(vis,gr);
		        
		        frame.setContentPane(displayPrefuse);
		        
		        displayPrefuse.start();
		        
		        frame.pack();           

		        frame.setVisible(true); 
		      
			}
		});
	}
}
