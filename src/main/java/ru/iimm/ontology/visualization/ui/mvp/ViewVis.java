package ru.iimm.ontology.visualization.ui.mvp;

/**
 * Интерфейс для view, в которых содержится визуализация.
 * @author Danilov
 * @version 0.1
 */
public interface ViewVis extends View
{
	/**Запустить визуализацию.*/
	void open();
	/**Завершить визуализацию.*/
	void close();
}
