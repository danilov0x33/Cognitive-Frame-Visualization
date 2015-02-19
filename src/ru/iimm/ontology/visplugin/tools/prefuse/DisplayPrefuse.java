package ru.iimm.ontology.visplugin.tools.prefuse;

import java.awt.geom.Point2D;

import prefuse.Display;
import prefuse.action.assignment.ColorAction;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import profusians.zonemanager.util.display.ZoneBorderDrawing;
import ru.iimm.ontology.visplugin.tools.prefuse.render.HighlightFartherNodeControl;
import ru.iimm.ontology.visplugin.tools.prefuse.render.DefaultVisualizationPrefuse;
import ru.iimm.ontology.visplugin.tools.prefuse.render.patterns.PatternVisualizationPrefuse;
import ru.iimm.ontology.visplugin.tools.prefuse.tools.ConstantsPrefuse;

/**
 * Отображение графа.
 * @author Danilov E.Y.
 *
 */
public class DisplayPrefuse extends Display
{		
	private static final long serialVersionUID = 6983131568264809174L;

	private Graph graph;
	
	public DisplayPrefuse(DefaultVisualizationPrefuse vis, Graph graph)
	{		
		super(vis);
		
		this.graph = graph;
		
		this.init();
	}
	
	public DisplayPrefuse(PatternVisualizationPrefuse vis, Graph graph)
	{		
		super(vis);
		
		this.init();
		
		this.graph = graph;
		
		//Отображает зоны
		this.addPaintListener(new ZoneBorderDrawing(vis.getZoneManager()));
		
		ColorAction aFill = vis.getZoneManager().getZoneColorAction();
		vis.getDraw().add(aFill);
		
		vis.putAction(ConstantsPrefuse.COLOR, vis.getDraw());
	}
	

	private void init()
	{		
		this.pan(360, 250);

		this.setHighQuality(true);
        
		this.setSize(720, 500); 
		
		this.zoom(new Point2D.Double(getWidth() / 2, getHeight() / 2), 1.7);
        
		this.setBackground(ColorLib.getColor(59, 68, 75));
		
		this.setDamageRedraw(false);
        
		//Возможность перетаскивать/нажимать на элементы
		this.addControlListener(new DragControl());
		//Возможность перемещать камеру
		this.addControlListener(new PanControl()); 
		//Возможность приблежать/отдалять камеру
		this.addControlListener(new ZoomControl());
		//Возможность менять цвет у отдаленных элементов
		//this.addControlListener(new NeighborHighlightControl());
		
		this.addControlListener(new HighlightFartherNodeControl());
		//this.addControlListener(new ZoomToFitControl());
		//this.addControlListener(new FocusControl(1));  
	}
	
	public void stop()
	{
		this.m_vis.getAction(ConstantsPrefuse.ANIMATE).cancel();
		this.m_vis.getAction(ConstantsPrefuse.COLOR).cancel();
		this.setHighQuality(false);
		this.setVisible(false);
	}
	
	public void start()
	{
		this.m_vis.run(ConstantsPrefuse.COLOR);
		this.m_vis.run(ConstantsPrefuse.ANIMATE);
		this.setHighQuality(true);
		this.setVisible(true);
	}
	
	public void setGraph(Graph graph)
	{
		this.graph = graph;
		this.m_vis.removeGroup(ConstantsPrefuse.GRAPH);
		this.m_vis.add(ConstantsPrefuse.GRAPH, graph);
	}
	
	public void centerNode(VisualItem item)
	{

		double scale = this.getScale();
		double displayX = this.getDisplayX();
		double displayY = this.getDisplayY();
		double nodeX = item.getX() * scale;
		double nodeY = item.getY() * scale;
		double screenWidth = this.getWidth();
		double screenHeight = this.getHeight();
		double moveX = (nodeX * -1) + ((screenWidth / 2) + displayX);
		double moveY = (nodeY * -1) + ((screenHeight / 2) + displayY);

		this.animatePan(moveX, moveY, 1000);
	}

	public Graph getGraph()
	{
		return this.graph;
	}

}
