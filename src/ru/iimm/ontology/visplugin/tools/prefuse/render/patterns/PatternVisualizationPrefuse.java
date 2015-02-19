package ru.iimm.ontology.visplugin.tools.prefuse.render.patterns;

import prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.render.DefaultRendererFactory;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import profusians.zonemanager.action.ZoneGuardAction;
import ru.iimm.ontology.visplugin.tools.prefuse.render.EdgeLabelLayout;
import ru.iimm.ontology.visplugin.tools.prefuse.render.StaticPointNodeLayout;
import ru.iimm.ontology.visplugin.tools.prefuse.tools.ConstantsPrefuse;
import ru.iimm.ontology.visplugin.tools.prefuse.tools.PatternGraphPrefuse;

/**
 * Визуализация паттернов.
 * @author Danilov E.Y.
 *
 */
public class PatternVisualizationPrefuse extends Visualization
{
	
	private PatternZoneObject zoneManager;
	
	private ActionList draw;
	
	
	public PatternVisualizationPrefuse(PatternGraphPrefuse graph)
	{
		super();
		
		this.add(ConstantsPrefuse.GRAPH, graph);
		
		this.init();
		
		graph.setZoneManager(zoneManager);
	}

	private void init()
	{
		
		//Создаем рендер для элементов
		ObjectNodeWithIconRender nodeRenderer = new ObjectNodeWithIconRender();
        //Создаем рендер для ребер
		PatternEdgeRender edgeR = new PatternEdgeRender();

		//Создаем фабрику рендеров
        DefaultRendererFactory drf = new DefaultRendererFactory();
        drf.setDefaultRenderer(nodeRenderer);
        drf.setDefaultEdgeRenderer(edgeR);

        
        this.setRendererFactory(drf);
        
        this.zoneManager = new PatternZoneObject(this);
        
        this.zoneManager.addZoneRenderer(drf);
        //this.initZoneManager();
        
        //this.zoneManager.createEventObject();
        int[] palette = {ColorLib.rgb(0,128, 255),ColorLib.gray(255),ColorLib.rgb(255, 191, 0),ColorLib.rgb(100, 255, 0)}; 
        
        //Цвет элемента
    	DataColorAction nFill = new DataColorAction(ConstantsPrefuse.NODES, ConstantsPrefuse.DATA_TYPE_NODE,Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
    	//nFill.setDefaultColor(ColorLib.gray(255)); 
    	nFill.add(VisualItem.FIXED, ColorLib.rgb(200, 130, 20));
    	
        //Цвет для текста в элементе
    	ColorAction nText = new ColorAction(ConstantsPrefuse.NODES, VisualItem.TEXTCOLOR,ColorLib.rgb(100, 0, 100));
    	//Цвет граней элемента
    	DataColorAction nStroke = new DataColorAction(ConstantsPrefuse.NODES, ConstantsPrefuse.DATA_TYPE_NODE,Constants.NOMINAL, VisualItem.STROKECOLOR, palette);
    	//ColorAction nStroke = new ColorAction(ConstantsPrefuse.NODES, VisualItem.STROKECOLOR,ColorLib.rgb(30, 144, 255));
    	//Цвет ребер
    	ColorAction nEdges = new ColorAction(ConstantsPrefuse.EDGES, VisualItem.STROKECOLOR,ColorLib.gray(100));
    	//Цвет стрелки у ребер
    	ColorAction nArrow = new ColorAction(ConstantsPrefuse.EDGES, VisualItem.FILLCOLOR,ColorLib.gray(100));

    	//Добовляем все созданные цвета
    	draw = new ActionList();
    	draw.add(nText);
    	draw.add(nStroke);
    	draw.add(nFill);
    	draw.add(nEdges);
    	draw.add(nArrow);

    	
    	
    	
    	ForceDirectedLayout fdl = new ForceDirectedLayout(ConstantsPrefuse.GRAPH, zoneManager
    			.getForceSimulator(), false);
    	
    	// Анимация/движения в графе
    	ActionList animate = new ActionList(Activity.INFINITY);    	
    	animate.add(new ZoneGuardAction(zoneManager));
    	animate.add(fdl);
    	// Рисует label на ребрах
    	animate.add(new EdgeLabelLayout(this,drf));
    	animate.add(new StaticPointNodeLayout("graph.nodes"));
    	//Перерисовывание анимации
    	animate.add(new RepaintAction());
    	
    	
    	//nodeRenderer.getImageFactory().preloadImages(this.items(),ConstantsPrefuse.DATA_IMAGE_ICON_NODE);
    	
    	
    	
    	this.putAction(ConstantsPrefuse.ANIMATE, animate);

	}

	public PatternZoneObject getZoneManager()
	{
		return zoneManager;
	}

	public ActionList getDraw()
	{
		return draw;
	}

	public void setDraw(ActionList draw)
	{
		this.draw = draw;
	}
	
}
