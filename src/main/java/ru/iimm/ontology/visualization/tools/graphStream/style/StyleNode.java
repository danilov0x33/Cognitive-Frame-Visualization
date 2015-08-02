package ru.iimm.ontology.visualization.tools.graphStream.style;

public class StyleNode
{
	/**Стандартный стиль для элемента.*/
	public static final String NODE = "node {" 
			+ StyleNode.NODE_TEMP_STYLE
			+"z-index: 2;"
			+ "}"
			+ StyleNode.NODE_CONCEPT
			+ StyleNode.NODE_CLICKED
			+ StyleNode.NODE_SELECTED
			+ StyleNode.SPRITE;

	
	public static final String NODE_STYLE_STARS = ""
			+ "shape: circle;"
			+ "size: 18px;"
			+ "fill-mode: gradient-radial;"
			+ "fill-color: #FFFA, #FFF0;"
			+ "stroke-mode: none; "
			+ "shadow-mode: gradient-radial;"
			+ "shadow-color: #FFF9, #FFF0;"
			+ "shadow-width: 10px;"
			+ "shadow-offset: 0px, 0px;"
			+ "text-background-color: #00BFFF;"
			+ "text-color: balck;"
			+ "text-padding: 5px, 4px;"
			+ "text-style: bold;"
			+ "text-size: 15px;"
			+ "text-alignment: under;"
			+ "text-background-mode: rounded-box;"
			+ "text-offset: 0px, 10px;"
			/*+ "shape: circl	e;"
			+ "size-mode: dyn-size;"
			+ "size: 10px;"
			+ "fill-mode: gradient-radial;"
			+ "fill-color: #FFFC, #FFF0;"
			+ "stroke-mode: none; "
			+ "shadow-mode: gradient-radial;"
			+ "shadow-color: #FFF5, #FFF0;"
			+ "shadow-width: 5px;"
			+ "shadow-offset: 0px, 0px;"*/
			+"";
	
	public static final String NODE_TEMP_STYLE = ""
			+ "shape: circle;"
			+ "size: 18px;"
			+ "fill-mode: gradient-radial;"
			+ "fill-color: #271CBC, #271CBC;"
			+ "stroke-mode: none; "
			+ "shadow-mode: gradient-radial;"
			+ "shadow-color: #1100FF, #FFF0;"
			+ "shadow-width: 10px;"
			+ "shadow-offset: 0px, 0px;"
			+ "text-background-color: #007F77;"
			+ "text-color: balck;"
			+ "text-padding: 5px, 4px;"
			+ "text-style: bold;"
			+ "text-size: 15px;"
			+ "text-alignment: under;"
			+ "text-background-mode: rounded-box;"
			+ "text-offset: 0px, 10px;"
			+"";
	
	public static final String NODE_DEFAULT_STYLE = ""
			+ "size-mode: fit;" 
			+ "fill-color: #92F;" 
			+ "stroke-mode: plain;"
			+ "size: 18px;"
			+ "text-background-color: #111C;"
			+ "text-color: white;"
			+ "text-padding: 3px, 2px;"
			+ "text-style: bold;"
			+ "text-size: 20px;"
			+ "text-alignment: under;"
			+ "text-background-mode: rounded-box;"
			+ "text-offset: 0px, 5px;"
			/*text-alignment: under;
			 *  text-color: white;
			 *   text-style: bold;
			 *    text-background-mode: rounded-box;
			 *     text-background-color: #222C;
			 *      text-padding: 5px, 4px;
			 *       text-offset: 0px, 5px;*/
			//+ "text-background-mode: rounded-box;"
			//+ "text-background-color: #222C;"
			//+ "text-offset: 0px, 5px;"
			
			//+ "padding: 10px, 5px;"
			+ "";
	
	/**Стиль, при котором элемент нажат.*/
	public static final String NODE_CLICKED = "node:clicked {"
			//+ "fill-color: green;"
			+ "fill-color: #F00A, #F000;"
			+ "}";
	
	/**Стиль, при котором элемент выделен.*/
	public static final String NODE_SELECTED = "node:selected {"
           // + "fill-color: red;"
			+"fill-color: #00FA, #00F0;"
            + "}";
	
	/**Стиль для концепта фарейма.*/
	public static final String NODE_CONCEPT = "node.concept {"
			+ "fill-color: #FFFF00, #F000;"
            + "text-background-color: #FF9500;"
            + "text-color: black;"
            + "}";
	
	public static final String SPRITE =" sprite {"
			//+"fill-color: #DED; size: 200px; stroke-mode: plain; shape: rounded-box; stroke-width: 6px; stroke-color: #585;"
			+"size: 200px;"
			+"shape: jcomponent;"
			+"jcomponent: text-field;"
			+ "text-padding: 30px, 20px;"
			+ "text-mode: truncated ;"
			+ "text-padding: 30px, 20px;"
			+ "text-style: bold;"
			+ "text-size: 20px;"
			+ "padding: 10px, 5px;"
			+"z-index: 0;"
		    + "}";
	
}
