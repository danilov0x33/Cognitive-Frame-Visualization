package ru.iimm.ontology.visualization.ui.mvp.views;

import org.piccolo2d.PNode;

/**
 * View для визуализации с помощью Piccolo2D.
 * @author Danilov
 * @version 0.1
 */
public interface ViewVisPiccolo2D extends ViewVis
{
	/**Получить контейнер для добавления визуальных образов.*/
	PNode getLayer();
}
