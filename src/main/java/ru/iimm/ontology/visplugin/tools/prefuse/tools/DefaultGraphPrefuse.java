package ru.iimm.ontology.visplugin.tools.prefuse.tools;

import java.util.Random;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;

/**
 * Стандартный граф от префьюза.
 * @author Danilov E.Y.
 *
 */
public class DefaultGraphPrefuse extends Graph
{	
	
	public DefaultGraphPrefuse()
	{   
		//Таблица данных для элементов и ребер
		super(new DataTableNode(), new DataTableEdge(),true);
        this.init();
        
	}
	
	/**
	 * Добавить элемент.
	 * @return - добавленный элемент.
	 */
	public Node addNode()
	{
		Node node = super.addNode();
    	return node;
	}
	
	/**
	 * Добавить элемент.
	 * @param label - текст на элементе.
	 * @return - добавленный элемент.
	 */
	public Node addNode(String label)
	{
		Node node = this.addNode();
    	node.setString(ConstantsPrefuse.DATA_LABEL_NODE, label);
    	return node;
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
	
	/**
	 * Генерирует элементы и связи.
	 */
	public void randNode()
	{
        int numNodes = 10;
        
        for (int i = 0; i < numNodes; i++)
        {
        	Node n = addNode();
        	n.setString(ConstantsPrefuse.DATA_LABEL_NODE,"Node: " + String.valueOf(i));
        	n.setString(ConstantsPrefuse.DATA_IMAGE_ICON_NODE,DefaultGraphPrefuse.class.getResource("/actionIcon.png").toString());
        }
        
        Random rand = new Random();
        
        for(int i = 0; i < numNodes; i++)
        {
	        	int first = rand.nextInt(numNodes);
	        	int second = rand.nextInt(numNodes);
	        	int idEdge = addEdge(first, second);
	        	Edge edge = this.getEdge(idEdge);
	        	edge.set(ConstantsPrefuse.DATA_LABEL_EDGE, String.valueOf(first) + " -> " + String.valueOf(second));
	        	
        }
        
        
	}
	
	private void init()
	{

	}
}
