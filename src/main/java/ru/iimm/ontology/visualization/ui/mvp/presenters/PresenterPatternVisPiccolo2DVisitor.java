package ru.iimm.ontology.visualization.ui.mvp.presenters;

import ru.iimm.ontology.visualization.patterns.PatternVisVisitorInt;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisPiccolo2D;

/**
 * Представтель для визуализации CDP с помощью Picoolo2D.
 * @author Danilov
 * @version 0.1
 */
public interface PresenterPatternVisPiccolo2DVisitor extends PatternVisVisitorInt
{
	ViewVisPiccolo2D getView();
	void setView(ViewVisPiccolo2D view);
}
