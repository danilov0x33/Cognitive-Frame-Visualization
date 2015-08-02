package ru.iimm.ontology.visualization.ui;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.visualization.tools.CFrameDecoratorInt;
import ru.iimm.ontology.visualization.tools.OWLModelManagerDecorator;
import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;

/**
 * Интерфейс, с помощью которого слушатель получает оповещение.
 * @author Danilov E.Y.
 *
 */
public interface ObserverOntClassListInt
{	
	/**
	 * Отправляет слушателям выбранную из списка оболочку для CFrame.
	 * @param cfDec
	 */
	public void update(CFrameDecoratorInt cfDec);
	
	/**
	 * Отправляет слушателям выбранную из списка визуальный метод.
	 * @param visualMethodBuilderInt
	 */
	public void update(VisualMethodVisitorInt visualMethodVisitorInt);
	
	/**
	 * Отправляет слушателям выбранную, из списка, OWLClass и оболочку OWLModelManager
	 * в котором содержится OWLClass.
	 * @param owlClass
	 * @param owlModelManagerDecorator
	 */
	public void update(OWLClass owlClass,OWLModelManagerDecorator owlModelManagerDecorator);
}