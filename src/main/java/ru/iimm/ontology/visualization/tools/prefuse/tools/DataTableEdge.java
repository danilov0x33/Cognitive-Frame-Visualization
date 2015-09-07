package ru.iimm.ontology.visualization.tools.prefuse.tools;

import prefuse.data.Table;

/**
 * Таблица данных для граней.
 * @author Danilov E.Y.
 *
 */
public class DataTableEdge extends Table
{
	public DataTableEdge()
	{
		super();
		this.init();
	}


	private void init()
	{
		//Для связки элементов
        this.addColumn(ConstantsPrefuse.DATA_DEFAULT_SOURCE_KEY, int.class, new Integer(-1));
        this.addColumn(ConstantsPrefuse.DATA_DEFAULT_TARGET_KEY, int.class, new Integer(-1));
        //Для ярлыка на ребре
		this.addColumn(ConstantsPrefuse.DATA_LABEL_EDGE, ConstantsPrefuse.DATA_LABEL_EDGE_TYPE);
		//Для расстояния между связанными элементами
		this.addColumn(ConstantsPrefuse.DATA_LENGTH_EDGE, ConstantsPrefuse.DATA_LENGTH_EDGE_TYPE, ConstantsPrefuse.DATA_LENGTH_EDGE_DEF_VALUE);
	}
}