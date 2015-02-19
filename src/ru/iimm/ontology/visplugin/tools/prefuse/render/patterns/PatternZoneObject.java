package ru.iimm.ontology.visplugin.tools.prefuse.render.patterns;

import prefuse.Visualization;
import prefuse.util.ColorLib;
import prefuse.util.force.DragForce;
import prefuse.util.force.ForceSimulator;
import prefuse.util.force.NBodyForce;
import prefuse.util.force.SpringForce;
import profusians.zonemanager.ZoneManager;
import profusians.zonemanager.zone.Zone;
import profusians.zonemanager.zone.attributes.ZoneAttributes;
import profusians.zonemanager.zone.colors.ZoneColors;
import profusians.zonemanager.zone.shape.DefaultZoneShape;
import profusians.zonemanager.zone.shape.PolygonalZoneShape;

/**
 * Менеджер зон(объектов) для визуализации паттернов онтологии.
 * @author Danilov E.Y.
 *
 */
public class PatternZoneObject extends ZoneManager
{
	
	public PatternZoneObject(Visualization vis)
	{
		super(vis, new ForceSimulator());

		this.init();
		
	}

	private void init()
	{
		float gravConstant = -0.0f; // -1.0f;
		float minDistance = 100f; // -1.0f;
		float theta = 0.1f; // 0.9f;

		float drag = 0.01f; // 0.01f;

		float springCoeff = 1E-9f; // 1E-4f;
		float defaultLength = 150f; // 50;

		ForceSimulator fsim;

		fsim = new ForceSimulator();

		fsim.addForce(new NBodyForce(gravConstant, minDistance, theta));
		fsim.addForce(new DragForce(drag));
		fsim.addForce(new SpringForce(springCoeff, defaultLength));

		this.setForceSimulator(fsim);
		

		
	}

	
	
	public int createEventObject(String name)
	{
		
		int[] xpoints = new int[] { -50, 0, 50, 0, -50 };
		int[] ypoints = new int[] {  50, 50,   0, -50, -50 };
		
		ZoneColors zoneColors = new ZoneColors(ColorLib.rgb(255, 255, 255), ColorLib
				.rgba(255,255,255,255));
		
		PolygonalZoneShape polig = new PolygonalZoneShape(xpoints, ypoints);
		
		ZoneAttributes zAttributes = new ZoneAttributes(DefaultZoneShape
				.getDefaultGravConst(polig));
		
		Zone zone = this.m_zoneFactory.getZone(polig, zoneColors, zAttributes);
		zone.setName(name);
		
		return 	this.addZone(zone);
		
	
	}
	
	public Visualization getVis()
	{
		return m_vis;
	}


	
}
