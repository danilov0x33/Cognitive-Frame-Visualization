package ru.iimm.ontology.visualization.ui.mvp.views;

import javax.swing.JDialog;

import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterSettingFrame;

/**
 * View формы с настройками.
 * @author Danilov
 * @version 0.1
 */
public interface ViewSettingFrame
{
	void setPresenter(PresenterSettingFrame presenter);
	PresenterSettingFrame getPresenter();
	/**Получить объект фрейма.*/
	JDialog getFrame();
}
