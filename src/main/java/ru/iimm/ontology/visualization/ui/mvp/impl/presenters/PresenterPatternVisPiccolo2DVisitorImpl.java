package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import org.piccolo2d.PNode;

import ru.iimm.ontology.visualization.patterns.SituationRealizationVis;
import ru.iimm.ontology.visualization.tools.piccolo2d.patters.PPatternVisBuilder;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewPatternVisPiccolo2DImpl;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterPatternVisPiccolo2DVisitor;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisPiccolo2D;

/**
 * Реализация интерфейса {@linkplain PresenterPatternVisPiccolo2DVisitor}.
 * @author Danilov
 * @version 0.1
 */
public class PresenterPatternVisPiccolo2DVisitorImpl implements PresenterPatternVisPiccolo2DVisitor
{
	private ViewVisPiccolo2D view;
	
	public PresenterPatternVisPiccolo2DVisitorImpl()
	{
		ViewPatternVisPiccolo2DImpl view = new ViewPatternVisPiccolo2DImpl();
		view.setPresenter(this);
		this.view = view;
	}
	
	@Override
	public void visit(SituationRealizationVis realization)
	{
		PNode vis =  new PPatternVisBuilder()
				.setRealizationVis(realization)
				.buildSituationVis();

		this.view.getLayer().removeAllChildren();
		this.view.getLayer().addChild(vis);
	}

	@Override
	public ViewVisPiccolo2D getView()
	{
		return this.view;
	}

	@Override
	public void setView(ViewVisPiccolo2D view)
	{
		this.view = view;
	}

}
