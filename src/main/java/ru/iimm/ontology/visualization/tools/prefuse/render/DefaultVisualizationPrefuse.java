package ru.iimm.ontology.visualization.tools.prefuse.render;

import prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.activity.Activity;
import prefuse.data.Graph;
import prefuse.render.DefaultRendererFactory;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import ru.iimm.ontology.visualization.tools.prefuse.tools.ConstantsPrefuse;
import ru.iimm.ontology.visualization.tools.prefuse.tools.DefaultGraphPrefuse;

/**
 * Визуализация для графа.
 * @author Danilov E.Y.
 *
 */
public class DefaultVisualizationPrefuse extends Visualization
{
	public DefaultVisualizationPrefuse()
	{
		super();
		
		//this.add(ConstantsPrefuse.GRAPH, new DefaultGraphPrefuse());
		
		this.init();
		
		
	}

	/**
	 * @param graph
	 */
	public DefaultVisualizationPrefuse(Graph graph)
	{
		super();
		
		this.add(ConstantsPrefuse.GRAPH, graph);
		
		this.init();
	}

	private void init()
	{
		//Добавляем граф в визуализацию
		//GraphPrefuse graphPrefuse = new GraphPrefuse();
		//graphPrefuse.randNode();
		//this.add(ConstantsPrefuse.GRAPH, graphPrefuse);
		
		//Создаем рендер для элементов
		DefaultNodeRender nodeRenderer = new DefaultNodeRender();
        //Создаем рендер для ребер
		DefaultEdgeRenderer edgeR = new DefaultEdgeRenderer();

		//Создаем фабрику рендеров
        DefaultRendererFactory drf = new DefaultRendererFactory();
        drf.setDefaultRenderer(nodeRenderer);
        drf.setDefaultEdgeRenderer(edgeR);
        
        this.setRendererFactory(drf);

        HighlightFartherNodeColor highlightFartherNodeColor = new HighlightFartherNodeColor(3, 
        		ColorLib.getInterpolatedPalette(10,ColorLib.rgb(255, 0, 0), ColorLib.rgb(0, 0, 255)));
        //ColorLib.rgb(0, 255, 127)
        //Цвец заполнения элемента
        int[] palette = {ColorLib.rgb(255, 191, 0), ColorLib.rgb(0,128, 255)}; 
        
        DataColorAction nFill = new DataColorAction(ConstantsPrefuse.NODES, ConstantsPrefuse.DATA_TARGET_CON_NODE, Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
    	//Цвет дальних элементов от выделяемого элемента
    	nFill.add(VisualItem.HIGHLIGHT, highlightFartherNodeColor);
    	//Цвет выделяемого элемента
    	nFill.add(VisualItem.FIXED, ColorLib.rgb(200, 130, 20));
    	
        //Цвет для текста в элементе
    	ColorAction nText = new ColorAction(ConstantsPrefuse.NODES, VisualItem.TEXTCOLOR,ColorLib.rgb(100, 0, 100));
    	//Цвет граней элемента
    	ColorAction nStroke = new ColorAction(ConstantsPrefuse.NODES, VisualItem.STROKECOLOR,ColorLib.rgb(30, 144, 255));
    	//Цвет ребер
    	ColorAction nEdges = new ColorAction(ConstantsPrefuse.EDGES, VisualItem.STROKECOLOR,ColorLib.gray(100));
    	//Цвет стрелки у ребер
    	ColorAction nArrow = new ColorAction(ConstantsPrefuse.EDGES, VisualItem.FILLCOLOR,ColorLib.rgb(200, 0, 0));

    	//Добовляем все созданные цвета
    	ActionList draw = new ActionList();
    	draw.add(nText);
    	draw.add(nStroke);
    	draw.add(nFill);
    	draw.add(nEdges);
    	draw.add(nArrow);

    	// Анимация/движения в графе
    	ActionList animate = new ActionList(Activity.INFINITY);
    	//Плавная анимация передвижения элементов (true - элементы уходят за пределы)
    	animate.add(new DefaultForceDirectedLayout(ConstantsPrefuse.GRAPH, false));
    	// Анимация при выделении элементов
    	animate.add(nFill);
    	// Рисует label на ребрах
    	animate.add(new EdgeLabelLayout(this,drf));
    	//Перерисовывание анимации
    	animate.add(new RepaintAction());
    	
    	this.putAction(ConstantsPrefuse.COLOR, draw);
    	this.putAction(ConstantsPrefuse.ANIMATE, animate);

		//this.run(ConstantsPrefuse.COLOR);
		//this.run(ConstantsPrefuse.ANIMATE);
    	//this.runAfter(ConstantsPrefuse.COLOR, ConstantsPrefuse.ANIMATE);
        
	}
}
