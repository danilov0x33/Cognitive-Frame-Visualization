package ru.iimm.ontology.visplugin.tools.graphStream.style;

public class StyleGraph
{
	/**Стандартный стиль для графа.*/
	public static final String GRAPH = "graph{" 
		    + StyleGraph.GRAPH_STYLE_STARS
		    + "}";
	
	public static final String GRAPH_STYLE_DEFAULT = ""
			+ "padding: 60px;"
			+ "fill-color: black;"
			+"";
	
	public static final String GRAPH_STYLE_STARS = ""
		    + "canvas-color: black;"
		    + "fill-mode: gradient-vertical;"
		    + "fill-color: white, white;"
		    + "padding: 60px;"
			/*+ "canvas-color: black;"
		    + "fill-mode: gradient-vertical;"
		    + "fill-color: black, #004;"
		    + "padding: 20px;"*/
			+"";

}
