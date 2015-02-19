package ru.iimm.ontology.visplugin.tools;

/**
 * @author Danilov E.Y.
 *
 */
public interface ContentVisualizationViewerInt
{
	/**
	 * Принемает посетителя для обработки визуального метода.
	 * @param visitor
	 */
	public void accept(VisualMethodVisitorInt visitor);
}
