/**
 * 
 */
package ru.iimm.ontology.cftools;

import org.protege.editor.core.ui.view.View;

/**
 * Предпологается, что для каждого метода визуализации, 
 * будет отдельный класс
 */
public interface CFrameVisualisationMethod
{
	/**
	 * Получить панель с визуализатором
	 * @return
	 */
	public View getView();
}
