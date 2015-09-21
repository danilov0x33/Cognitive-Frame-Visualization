package ru.iimm.ontology.visualization.ui.mvp.views;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterPatternVisPiccolo2DVisitor;

/**
 * View для визуализации CDP с помощью Piccolo2D
 * @author Danilov
 * @version 0.1
 */
public interface ViewPatternVisPiccolo2D
{
	PresenterPatternVisPiccolo2DVisitor getPresenter();
	void setPresenter(PresenterPatternVisPiccolo2DVisitor presenter);
}
