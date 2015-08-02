package ru.iimm.ontology.visplugin.GUI;

import java.awt.Component;

/**
 * Интерфейс для GUI визуального метода.
 * @author Danilov E.Y.
 *
 */
public interface GUIVisualMethodInt extends GUIElementInt
{
	/**Возвращает компонент с визуализацией и GUI элементами.*/
	public Component getGUIComponent();
}
