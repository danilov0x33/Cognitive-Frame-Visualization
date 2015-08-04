package ru.iimm.ontology.visualization.ui.mvp;

import org.graphstream.graph.implementations.MultiGraph;
/**
 *
 * @author Danilov
 * @version 0.1
 */
public interface ViewCFrameVisGS extends ViewCFrameVis
{ 
	MultiGraph getGraph();
}
