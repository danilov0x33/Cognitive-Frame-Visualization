/**
 * 
 */
package ru.iimm.ontology.cftools;

import org.protege.editor.core.ui.view.View;

/**
 * Абстрактый класс инкапсулирующий общие переменные и 
 * методы конкретных классов-визуализаторов.
 * @author Lomov P.A.
 *
 */
public abstract class CFrameVisualisationMethodImpl implements CFrameVisualisationMethod
{

	/**
	 * 
	 */
	public CFrameVisualisationMethodImpl()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see ru.iimm.ontology.cftools.CFrameVisualisationMethod#getView()
	 */
	@Override
	public View getView()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		//return 	"CFrameVisualisationMethod: ";
		return 	"CFrameVisualisationMethod: " + this.getClass();

	}


}
