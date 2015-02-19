package ru.iimm.ontology.visplugin.tools.cajun;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ru.iimm.ontology.visplugin.tools.cajun.render.DefaultArcStyleCajun;
import edu.umd.cs.piccolox.swing.PScrollPane;

public class startCajun
{
	public static void main(String[] argc)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			
			@Override
			public void run()
			{				
				JFrame frame = new JFrame("prefuse!");
				frame.setPreferredSize(new Dimension(800, 600));

		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        
		        DefaultGraphModelCajun graph = new DefaultGraphModelCajun();
		        
		        graph.setGraphArcStyle(new DefaultArcStyleCajun());
		        //graph.setGraphNodeStyle(new TestNodeStyleCajun());
		        
		        
		        graph.addNode("Lal");
		        
		        //DefaultViewCajun view = new DefaultViewCajun(new PScrollPane(graph.getGraph().getCanvas()););
		        
		        frame.setContentPane(new PScrollPane(graph.getGraph().getCanvas()));
		        
		        frame.pack();           

		        frame.setVisible(true); 
		      
			}
		});
	}
}
