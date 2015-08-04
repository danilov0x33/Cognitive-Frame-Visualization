package ru.iimm.ontology.visualization.ui.mvp;


/**
 *
 * @author Danilov
 * @version 0.1
 */
public interface ViewCFrameVis extends ViewVis
{
	void setPresenter(PresenterCFrameCajunVisitor presenter);
	PresenterCFrameCajunVisitor getPresenter();
}
