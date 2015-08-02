package ru.iimm.ontology.visplugin.tools.prefuse.tools;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import prefuse.util.PrefuseConfig;

/**
 * Константы для пакеджа ".prefuse"
 * @author Danilov E.Y.
 *
 */
public interface ConstantsPrefuse
{
	//===============Visual render==================
    public static final String GRAPH = "graph";
    public static final String NODES = "graph.nodes";
    public static final String EDGES = "graph.edges";   
    public static final String COLOR = "color";   
    public static final String ANIMATE = "animate";
    public static final String EDGE_LABEL_DEC = "edgeLabelDec";
    
    //==================Таблица с данными===========
    public static final String DATA_LABEL_NODE = "labelNode";
	public static final Class<String> DATA_LABEL_NODE_TYPE = String.class;
	public static final String DATA_OWL_NAMED_INDIVIDUAL_NODE = "OWLNamedIndividualNode";
	public static final Class<OWLNamedIndividual> DATA_OWL_NAMED_INDIVIDUAL_NODE_TYPE = OWLNamedIndividual.class;
	public static final String DATA_OWL_CLASS_NODE = "OWLClassNode";
	public static final Class<OWLClass> DATA_OWL_CLASS_NODE_TYPE = OWLClass.class;
	public static final String DATA_TARGET_CON_NODE = "targetConNode";
	public static final Class<Boolean> DATA_TARGET_CON_NODE_TYPE = boolean.class;
	public static final String DATA_IMAGE_ICON_NODE = "iconNode";
	public static final Class<String> DATA_IMAGE_ICON_NODE_TYPE = String.class;
	public static final String DATA_X_POINT_NODE = "xPointNode";
	public static final Class<Float> DATA_X_POINT_NODE_TYPE = float.class;
	public static final String DATA_Y_POINT_NODE = "yPointNode";
	public static final Class<Float> DATA_Y_POINT_NODE_TYPE = float.class;
	public static final String DATA_TYPE_NODE = "typeNode";
	public static final Class<Integer> DATA_TYPE_NODE_TYPE = int.class;
	public static final String DATA_LENGTH_NODE = "nodeLength";
	public static final Class<Double> DATA_LENGTH_NODE_TYPE = double.class;
	
	
	public static final String DATA_LABEL_EDGE = "labelEdge";
	public static final Class<String> DATA_LABEL_EDGE_TYPE = String.class;
	public static final String DATA_LENGTH_EDGE = "lengthEdge";
	public static final Class<Float> DATA_LENGTH_EDGE_TYPE = float.class;
	public static final float DATA_LENGTH_EDGE_DEF_VALUE = new Float(200F);
	
	public static final String DATA_DEFAULT_SOURCE_KEY = PrefuseConfig.get("data.graph.sourceKey");
	public static final String DATA_DEFAULT_TARGET_KEY = PrefuseConfig.get("data.graph.targetKey");
	
}

