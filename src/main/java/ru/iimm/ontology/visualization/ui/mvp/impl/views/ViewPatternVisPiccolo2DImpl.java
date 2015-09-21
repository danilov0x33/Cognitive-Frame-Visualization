package ru.iimm.ontology.visualization.ui.mvp.impl.views;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterPatternVisPiccolo2DVisitor;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewPatternVisPiccolo2D;

/**
 * View для визуализации CDP с помощью Piccolo2D. 
 * @author Danilov
 * @version 0.1
 */
public class ViewPatternVisPiccolo2DImpl extends BaseViewVisPiccolo2D implements ViewPatternVisPiccolo2D
{
	private PresenterPatternVisPiccolo2DVisitor presenter;
	
	@Override
	public PresenterPatternVisPiccolo2DVisitor getPresenter()
	{
		return this.presenter;
	}

	@Override
	public void setPresenter(PresenterPatternVisPiccolo2DVisitor presenter)
	{
		this.presenter = presenter;
	}

}
