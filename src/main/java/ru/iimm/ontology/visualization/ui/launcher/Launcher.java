package ru.iimm.ontology.visualization.ui.launcher;

import javax.swing.SwingUtilities;

import ru.iimm.ontology.visualization.ui.mvp.impl.presenters.PresenterMainFrameImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewMainFrameImpl;

/**
 * Лаунчер приложения. Запускает главное окно.
 * @author Danilov E.Y.
 */
public class Launcher
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				PresenterMainFrameImpl presenter = new PresenterMainFrameImpl();
				ViewMainFrameImpl view = new ViewMainFrameImpl();
				presenter.setView(view);
				view.setPresenter(presenter);
				
				presenter.show();
			}
		});
	}
}
