/**
 * 
 */
package ru.iimm.ontology.cftools;

/**
 * @author Lomov P. A.
 *
 */
abstract class CFrameVisualisatorVisitor implements CFrameVisitor
{
	/**
	 * Создает и отображает визуальный компонент, отражающий 
	 * к-фрейм.
	 * TODO Если визуальный компонент будет у всех визиторов одного
	 * класса - то лучше сделать чтобы данный метод возврящал именно его.
	 * ну а потом визуализировать этот компонен где-нибудь...
	 */
	abstract void getCFrameView();
	
	
	/*
	 * TODO Реализовать визит-методы для каждого типа к-фреймов
	 * 
	 */

}
