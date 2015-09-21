package ru.iimm.ontology.visualization.patterns;

import java.util.HashMap;
import java.util.Map;

import ru.iimm.ontology.pattern.ODPRealization;

/**
 * Визуальный образ для реализаций CPD.
 * @author Danilov
 * @version 0.1
 */
public abstract class RealizationVis<K,V> extends ElementVis
{
	/**Список элементов.*/
	protected Map<K, V> elementsMap;
	/**Реализация паттерна.*/
	protected ODPRealization realization;
	
	public RealizationVis()
	{
		elementsMap = new HashMap<K,V>();
	}
	/**
	 * Добавить ключ и элемент.
	 * @param key - ключ.
	 * @param value - элемент.
	 */
	protected void putElement(K key, V value)
	{
		elementsMap.put(key, value);
	}

	/**
	 * @return the {@linkplain #realization}
	 */
	public ODPRealization getRealization()
	{
		return realization;
	}

	/**
	 * @param realization the {@linkplain #realization} to set
	 */
	public void setRealization(ODPRealization realization)
	{
		this.realization = realization;
	}
}
