package ru.iimm.ontology.visualization.ui.mvp;

import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public interface PresenterCFrameCajunVisitor extends PresenterCFrame, VisualMethodVisitorInt
{	
	void setView(ViewCFrameVisCajun view);
	ViewCFrameVisCajun getView();
}
