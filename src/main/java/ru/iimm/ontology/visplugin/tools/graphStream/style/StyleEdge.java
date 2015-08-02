package ru.iimm.ontology.visplugin.tools.graphStream.style;

public class StyleEdge
{
	/**Стандартный стиль для граней.*/
	public static final String EDGE = "edge {" 
			+ StyleEdge.EDGE_TEMP_STYLE
			+ "z-index: 1;"
			+ "}"
			+ StyleEdge.EDGE_GROUP;
	
	public static final String EDGE_STYLE_STARS = ""
			+ "shape: line;"
			+ "size: 2px;"
			+ "fill-color: #FFF7;"
			+ "fill-mode: plain;"
			+ "arrow-shape: arrow;"
			+ "arrow-size: 17px, 10px;"
			+ "text-style: bold;"
			+ "text-size: 15px;"
			+ "text-color: yellow;"
			/*+"shape: L-square-line;"
			+"size: 1px;"
			+"fill-color: #FFF3;"
			+"fill-mode: plain;"
			+"arrow-shape: none;"*/
			+"";
	
	public static final String EDGE_TEMP_STYLE = ""
			+ "shape: line;"
			+ "size: 2px;"
			+ "fill-color: red;"
			+ "fill-mode: plain;"
			+ "arrow-shape: arrow;"
			+ "arrow-size: 17px, 10px;"
			+ "text-style: bold;"
			+ "text-size: 15px;"
			+ "text-color: black;"
			+"";
	
	public static final String EDGE_GROUP = "edge.group {"
			+ "shape: line;"
			+ "visibility-mode: hidden;"
			+ "fill-color: red;"
			+"}";
	
	public static final String EDGE_DEFAUILT_STYLE = ""
			+ "shape: line;"
            + "arrow-shape: arrow;"
            + "arrow-size: 20px, 10px;"
            + "size: 2px;"
            + "text-style: bold;"
			+ "text-size: 15px;"
			//+ "text-offset: 5px, 10px;"
            + "padding: 10px, 5px;"
			+ "fill-color: yellow;"
            +"";
	
}
