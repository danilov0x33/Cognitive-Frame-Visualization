package ru.iimm.ontology.visualization.tools;

import java.util.ArrayList;

import ru.iimm.ontology.cftools.CFrame;

/**
 * Интерфейс оболочки для CFrame.
 * @author Danilov E.Y.
 *
 */
public interface CFrameDecoratorInt
{
	/**Создает визуализацию для CFrame.*/
	public void builderViewer();
	
	public ArrayList<VisualMethodVisitorInt> getVisualMethodList();
	
	/**Возвращает CFrame.*/
	public CFrame getCframe();
	public void setCframe(CFrame cframe);
}
