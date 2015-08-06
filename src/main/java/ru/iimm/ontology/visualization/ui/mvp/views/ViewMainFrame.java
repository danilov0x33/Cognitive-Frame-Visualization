package ru.iimm.ontology.visualization.ui.mvp.views;

import java.awt.Component;

import javax.swing.JFrame;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterMainFrame;

/**
 * View для главного окна.
 * @author Danilov
 * @version 0.1
 */
public interface ViewMainFrame
{
	void setPresenter(PresenterMainFrame presenter);
	PresenterMainFrame getPresenter();
	/**Получить объект главного окна.*/
	JFrame getFrame();
	/**Установить значение активности кнопки "загрузить онтологию."*/
	void setEnableMenuItemOntology(boolean value);
	/**Задать tilte окна.*/
	void setTitle(String title);
	/**Заполнить контент окна.*/
	void setContentPane(Component component);
}
