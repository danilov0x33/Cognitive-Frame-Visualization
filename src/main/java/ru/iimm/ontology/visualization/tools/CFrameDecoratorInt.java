package ru.iimm.ontology.visualization.tools;

import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.CFrameVisualisationMethod;

/**
 * Интерфейс оболочки для CFrame.
 * @author Danilov E.Y.
 *
 */
public interface CFrameDecoratorInt
{
	/**Возвращает CFrame.*/
	public CFrame getCframe();
	public void setCframe(CFrame cframe);
	public CFrameVisualisationMethod getVisualMethod();
}
