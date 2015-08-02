package ru.iimm.ontology.visualization.ui.launcher;

import javax.swing.SwingUtilities;

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
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
			}
		});
	}
}
