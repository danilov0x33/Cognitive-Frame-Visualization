package ru.iimm.ontology.visualization.ui.mvp.presenters;

import ru.iimm.ontology.visualization.ui.mvp.models.ModelMultiOntology;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewMainFrame;

/**
 * View для главной формы.
 * @author Danilov
 * @version 0.1
 */
public interface PresenterMainFrame extends PresenterFrame
{
	void setView(ViewMainFrame view);
	ViewMainFrame getView();
	
	void setModel(ModelMultiOntology model);
	ModelMultiOntology getModel();
	
	/**Загрузить отнологию из файла выбранного пользователем.*/
	void loadOntologyFromFileChooser();
	/**Инициализация визуализации.*/
	void initVisualization();
	/**Открыть окно с настройками.*/
	void showSettingFrame();
	/**Закрыть приложение.*/
	void closeApplication();
}
