package ru.iimm.ontology.visualization.tools.graphStream;

import java.io.IOException;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.Graph;



public class testGraphStream {
	/*public static void main(String args[]) 
	{
		System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		DefaultGraph graph = new DefaultGraph("Graph");
		Style style = new Style(graph);
		//GraphBuilder grBuilder = new GraphBuilder(graph);
		//SpriteManager sman = new SpriteManager(graph);
		//Sprite s = sman.addSprite("S1");
		//s.addAttribute("xyz",0,0,2);
		GraphNode node1 = new GraphNode(graph,"node1");
		GraphNode node2 = new GraphNode(graph,"node2");
		GraphNode node3 = new GraphNode(graph,"node3");
		GraphNode node4 = new GraphNode(graph,"node4");
		double wegth = 0.05;
		node1.addAttribute("layout.weight", wegth);
		node2.addAttribute("layout.weight", wegth);
		node3.addAttribute("layout.weight", wegth);
		node4.addAttribute("layout.weight", wegth);
		GraphEdge edge1 = new GraphEdge("12", node1, node2, true);
		GraphEdge edge2 = new GraphEdge("13", node1, node3, true);
		GraphEdge edge3 = new GraphEdge("14", node1, node4, true);
		double wegthEdge = 5;
		edge1.addAttribute("layout.weight", wegthEdge);
		edge2.addAttribute("layout.weight", wegthEdge);
		edge3.addAttribute("layout.weight", wegthEdge);
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addNode(node3);
		graph.addNode(node4);
		
		graph.addEdge(edge1);
		graph.addEdge(edge2);
		graph.addEdge(edge3);
		
		//graph.addAttribute("layout.force", 0);
		//graph.addAttribute("layout.quality", 0);
		//graph.addAttribute("layout.weight ", 5);
		
		//s.addAttribute("ui.label", "AA\nAA");
		//s.addAttribute("ui.long-text", 1);
		
		//s.attachToNode("node1");
		//s.setPosition(0);
		//s.detach();
		//node1.addAttribute("xyz", 1,1,0);
		//node2.addAttribute("xyz", 1,1,1);
		
		
		
		graph.display();


	}*/
	static String my_graph =
            "DGS004\n" +
            "my 0 0\n" +
            "an A \n" +
            "an B \n" +
            "an C \n" +
            "an D \n" +
            "an E \n" +
            "an F \n" +
            "an G \n" +
            "an Q \n" +
            "an W \n" +
            "ae AB A B \n" +
            "ae BC B C \n" +
            "ae CD C D \n" +
            "ae DE D E \n" +
            "ae DF D F \n" +
            "ae CQ C Q \n" +
            "ae CW C W \n" +
            "ae DG D G \n";

    public static void main(String[] args) throws IOException {
    	 DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();
         Graph graph = new DefaultGraph("Kruskal Test");

         String css = "edge .notintree {size:1px;fill-color:gray;} " +
                          "edge .intree {size:3px;fill-color:black;}";

         graph.addAttribute("ui.stylesheet", css);
         graph.display();

         gen.addEdgeAttribute("weight");
         gen.setEdgeAttributesRange(1, 100);
         gen.addSink(graph);
         gen.begin();
         for (int i = 0; i < 100 && gen.nextEvents(); i++)
                 ;
         gen.end();

         Kruskal kruskal = new Kruskal("ui.class", "intree", "notintree");

         kruskal.init(graph);
         kruskal.compute();
           // graph.display();
    }
	
	
}