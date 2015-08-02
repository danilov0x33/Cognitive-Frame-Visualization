package ru.iimm.ontology.visualization.ui;

import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.visualization.tools.CFrameDecoratorInt;
import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;

/**
 * Интерфейс, определяющий методы для добавления, удаления и оповещения наблюдателей для списков классов из онтологии.
 * Реализует паттерн "Наблюдатель".
 * @author Danilov E.Y.
 *
 */
public interface ObservableOntClassListInt
{
	/**
	 * Добовляет слушателя для списков классов из онтологии.
	 * @param o - список классов из онтологии.
	 */
    void registerObserverOntClassList(ObserverOntClassListInt o);
    
    /**
     * Удаляет слушателя из списков классов из онтологии.
	 * @param o - список классов из онтологии.
     */
    void removeObserverOntClassList(ObserverOntClassListInt o);
    
    /**
     * Оповестить слушателей об изменении с CFrame.
     */
    void notifyCFrameObserverOntClassList(CFrameDecoratorInt cFrameDecorator);
    
    /**
     * Оповестить слушателей об изменении с OWLClass.
     */
    void notifyOWLClassObserverOntClassList(OWLClass owlClass);
    
    /**
     * Оповестить слушателей об изменении визуального метода.
     */
    void notifyVisualMethodObserverOntClassList(CFrameDecoratorInt cFrameDecorator, VisualMethodVisitorInt visualMethodBuilderInt);
}
