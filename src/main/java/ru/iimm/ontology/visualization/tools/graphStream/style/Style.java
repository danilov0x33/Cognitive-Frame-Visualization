package ru.iimm.ontology.visualization.tools.graphStream.style;

import org.graphstream.graph.Graph;


/**
 * Стиль отображение элементов/гнаней в визуализаторе.
 * @author Danilov
 *
 */
public class Style
{
   public Style(Graph graph)
   {
	    graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("layout.force", 1);
		//graph.addAttribute("ui.log", "fps.log");
		graph.setAutoCreate(true);
		graph.setStrict(false);        	   

		graph.addAttribute("ui.stylesheet", StyleGraph.GRAPH+StyleNode.NODE+StyleEdge.EDGE);

		
   }
   
   
   
}
