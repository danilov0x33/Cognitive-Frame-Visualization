package ru.iimm.ontology.visualization.ui.mvp;

import ru.iimm.ontology.visualization.tools.cajun.DefaultGraphModelCajun;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public interface ViewCFrameVisCajun extends ViewCFrameVis
{
	DefaultGraphModelCajun getGraph();
}
