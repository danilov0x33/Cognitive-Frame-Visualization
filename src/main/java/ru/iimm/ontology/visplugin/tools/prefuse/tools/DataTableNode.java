package ru.iimm.ontology.visplugin.tools.prefuse.tools;

import prefuse.data.Table;

/**
 * Таблица с данными для элементов.
 * @author Danilov E.Y.
 *
 */
public class DataTableNode extends Table
{
	public DataTableNode()
	{
		super();
		this.init();
	}


	private void init()
	{
		//Для ярлыка на элементе
		this.addColumn(ConstantsPrefuse.DATA_LABEL_NODE, ConstantsPrefuse.DATA_LABEL_NODE_TYPE);
		//Для хранения OWLNamedIndividual
		this.addColumn(ConstantsPrefuse.DATA_OWL_NAMED_INDIVIDUAL_NODE, ConstantsPrefuse.DATA_OWL_NAMED_INDIVIDUAL_NODE_TYPE);
		//Для хранения OWLClass
		this.addColumn(ConstantsPrefuse.DATA_OWL_CLASS_NODE, ConstantsPrefuse.DATA_OWL_CLASS_NODE_TYPE);
		//Является ли элемент target'ом
		this.addColumn(ConstantsPrefuse.DATA_TARGET_CON_NODE, ConstantsPrefuse.DATA_TARGET_CON_NODE_TYPE,false);
		//Иконка для элемента
		this.addColumn(ConstantsPrefuse.DATA_IMAGE_ICON_NODE, ConstantsPrefuse.DATA_IMAGE_ICON_NODE_TYPE,null);
		//Для статических позиций
		this.addColumn(ConstantsPrefuse.DATA_X_POINT_NODE, ConstantsPrefuse.DATA_X_POINT_NODE_TYPE,new Float(100f));
		this.addColumn(ConstantsPrefuse.DATA_Y_POINT_NODE, ConstantsPrefuse.DATA_Y_POINT_NODE_TYPE,new Float(100f));
		
		this.addColumn(ConstantsPrefuse.DATA_TYPE_NODE, ConstantsPrefuse.DATA_TYPE_NODE_TYPE,0);
		
		this.addColumn(ConstantsPrefuse.DATA_LENGTH_NODE, ConstantsPrefuse.DATA_LENGTH_NODE_TYPE);
		
	}
}
