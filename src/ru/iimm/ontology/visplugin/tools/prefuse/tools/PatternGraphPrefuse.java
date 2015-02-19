package ru.iimm.ontology.visplugin.tools.prefuse.tools;

import java.awt.Polygon;
import java.util.Iterator;
import java.util.Random;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.visual.NodeItem;
import profusians.zonemanager.zone.Zone;
import profusians.zonemanager.zone.shape.PolygonalZoneShape;
import ru.iimm.ontology.visplugin.tools.prefuse.render.patterns.PatternZoneObject;

/**
 * @author Danilov E.Y.
 *
 */
public class PatternGraphPrefuse extends Graph
{	
	private PatternZoneObject zoneManager;
	
	public PatternGraphPrefuse()
	{   
		//Таблица данных для элементов и ребер
		super(new DataTableNode(), new DataTableEdge(),true);        
	}


	public Node addObject(String label)
	{
		Node node = addNode();
		node.setString(ConstantsPrefuse.DATA_LABEL_NODE,label);
		node.set(ConstantsPrefuse.DATA_X_POINT_NODE, 0);
		node.set(ConstantsPrefuse.DATA_Y_POINT_NODE, 0);
		
    	return node;
	}
	
	public Node addTaskObject(String label)
	{
		Node node = this.addObject(label);
		node.set(ConstantsPrefuse.DATA_IMAGE_ICON_NODE,PatternGraphPrefuse.class.getResource("/taskIcon.png").toString());
		node.setInt(ConstantsPrefuse.DATA_TYPE_NODE, 2);
    	return node;
	}
	
	public Node addActionObject(String label)
	{
		Node node = this.addObject(label);
		node.set(ConstantsPrefuse.DATA_IMAGE_ICON_NODE,PatternGraphPrefuse.class.getResource("/actionIcon.png").toString());
		
    	return node;
	}
	
	public Node addProcessObject(String label)
	{
		Node node = this.addObject(label);
		node.set(ConstantsPrefuse.DATA_IMAGE_ICON_NODE,PatternGraphPrefuse.class.getResource("/processIcon.png").toString());
		node.setInt(ConstantsPrefuse.DATA_TYPE_NODE, 3);
    	return node;
	}
	

	
	public void addTaskToEvent(int idEvent, Node node)
	{		
		Zone zone = zoneManager.getZone(idEvent);
		PolygonalZoneShape poli = (PolygonalZoneShape)zone.getShape();
		
		node.setFloat(ConstantsPrefuse.DATA_X_POINT_NODE, poli.getPolygon().xpoints[2]+100);
		node.setFloat(ConstantsPrefuse.DATA_Y_POINT_NODE, zone.getCenterY());
	}
	
	public void addObjectToEvent(int idEvent, NodeItem node)
	{
		Zone zone = zoneManager.getZone(idEvent);
		PolygonalZoneShape poli = (PolygonalZoneShape)zone.getShape();
		
		String labelName = node.getString(ConstantsPrefuse.DATA_LABEL_NODE);
		
		int addSize = labelName.length() *3 ;		
		
		poli.getPolygon().xpoints[1]+=addSize*3;
		poli.getPolygon().xpoints[2]+=addSize*3;
		poli.getPolygon().xpoints[3]+=addSize*3;
		
		node.setFloat(ConstantsPrefuse.DATA_X_POINT_NODE, poli.getPolygon().xpoints[1]-addSize*2);
		node.setFloat(ConstantsPrefuse.DATA_Y_POINT_NODE, zone.getCenterY()+10);
		
		Polygon p = new Polygon(poli.getPolygon().xpoints, poli.getPolygon().ypoints, poli.getPolygon().npoints);
		
		poli.setPolygon(p);
		
		Iterator iter = zoneManager.getVis().items(ConstantsPrefuse.NODES);
		while (iter.hasNext())
		{
			NodeItem aNodeItem = (NodeItem) iter.next();
			if(aNodeItem.getString(ConstantsPrefuse.DATA_LABEL_NODE).equals(zone.getName()))
			{
				aNodeItem.setFloat(ConstantsPrefuse.DATA_X_POINT_NODE, (float) poli.getPolygon().getBounds().getCenterX());
				break;
			}
		}
		
		zoneManager.addItemToZone(node, idEvent);
	}
	
	public int createEventObject(String label)
	{
		Node labNode = this.addActionObject(label);
		labNode.setInt(ConstantsPrefuse.DATA_TYPE_NODE, 1);
		int idZone = zoneManager.createEventObject(label);
		
		Zone zone = zoneManager.getZone(idZone);
		PolygonalZoneShape poli = (PolygonalZoneShape)zone.getShape();

		int addSize = label.length() * 7;		
		
		
		poli.getPolygon().xpoints[1]+=addSize;
		poli.getPolygon().xpoints[2]+=addSize;
		poli.getPolygon().xpoints[3]+=addSize;
		
		Polygon p = new Polygon(poli.getPolygon().xpoints, poli.getPolygon().ypoints, poli.getPolygon().npoints);
		
		poli.setPolygon(p);
		
		labNode.setFloat(ConstantsPrefuse.DATA_X_POINT_NODE, (float) poli.getPolygon().getBounds().getCenterX());
		labNode.setFloat(ConstantsPrefuse.DATA_Y_POINT_NODE, poli.getPolygon().ypoints[0]-80);
		
		return idZone;
	}
	
	public Edge addEdgeObjectToEvent(int idEvent, Node node)
	{
		Zone zone = zoneManager.getZone(idEvent);
		PolygonalZoneShape poli = (PolygonalZoneShape)zone.getShape();
		

		Iterator nodes = this.nodeRows();
		while(nodes.hasNext())
		{
			int idNode = (int) nodes.next();
			
			Node nodeGet = this.getNode(idNode);
			
			if(nodeGet==null)
				return null;
			
			if(nodeGet.getString(ConstantsPrefuse.DATA_LABEL_NODE).equals(zone.getName()))
			{
				node.setFloat(ConstantsPrefuse.DATA_X_POINT_NODE, poli.getPolygon().xpoints[0] + new Random().nextInt(300));
				node.setFloat(ConstantsPrefuse.DATA_Y_POINT_NODE, poli.getPolygon().ypoints[4]-70);
				return this.addEdge(node, nodeGet);
				//break;
			}
		}
		
		return null;
	}
	
	/**
	 * Добавить ребро.
	 * @param source - к какому элементу пойдет ребро.
	 * @param target - от какого элемента пойдет ребро.
	 * @return - добавленное ребро.
	 */
	public Edge addEdge(Node source, Node target)
	{
		Edge edge = super.addEdge(source, target);
		
		return edge;
	}
	/**
	 * Добавить ребро.
	 * @param label - текст на ребре.
	 * @param source - к какому элементу пойдет ребро.
	 * @param target - от какого элемента пойдет ребро.
	 * @return - добавленное ребро.
	 */
	public Edge addEdge(String label, Node source, Node target)
	{
		Edge edge = this.addEdge(source, target,ConstantsPrefuse.DATA_LENGTH_EDGE_DEF_VALUE);
		edge.set(ConstantsPrefuse.DATA_LABEL_EDGE, label);
		
		return edge;
	}
	
	/**
	 * Добавить ребро.
	 * @param source - к какому элементу пойдет ребро.
	 * @param target - от какого элемента пойдет ребро.
	 * @param lengthValue - длина ребра.
	 * @return - добавленное ребро.
	 */
	public Edge addEdge(Node source, Node target, float lengthValue)
	{
		Edge edge = this.addEdge(source, target);
		edge.setFloat(ConstantsPrefuse.DATA_LENGTH_EDGE,lengthValue);
		
		
		return edge;
	}
	
	
	public PatternZoneObject getZoneManager()
	{
		return zoneManager;
	}

	public void setZoneManager(PatternZoneObject zoneManager)
	{
		this.zoneManager = zoneManager;
	}

	public void randNode()
	{
		 int numNodes = 10;
		 Random rand = new Random();
	        for (int i = 0; i < numNodes; i++)
	        {
	        	Node n = addNode();
	        	n.setString(ConstantsPrefuse.DATA_LABEL_NODE,"Node: " + String.valueOf(i));
	        	n.setString(ConstantsPrefuse.DATA_IMAGE_ICON_NODE,DefaultGraphPrefuse.class.getResource("/actionIcon.png").toString());
	    		n.setFloat(ConstantsPrefuse.DATA_X_POINT_NODE, rand.nextInt(300));
	    		n.setFloat(ConstantsPrefuse.DATA_Y_POINT_NODE, rand.nextInt(300));
	        }
	        
	        for(int i = 0; i < numNodes; i++)
	        {
		        	int first = rand.nextInt(numNodes);
		        	int second = rand.nextInt(numNodes);
		        	int idEdge = addEdge(first, second);
		        	Edge edge = this.getEdge(idEdge);
		        	edge.set(ConstantsPrefuse.DATA_LABEL_EDGE, String.valueOf(first) + " -> " + String.valueOf(second));
		        	
	        }
	}

	
}
