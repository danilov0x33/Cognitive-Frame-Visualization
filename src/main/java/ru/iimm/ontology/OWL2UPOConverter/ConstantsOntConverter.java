package ru.iimm.ontology.OWL2UPOConverter;
import ru.iimm.ontology.ontAPI.ConstantsOntAPI;

/** Общие константы
 * 
 */

/**
 * @author Lomov P. A.
 *
 */
public interface ConstantsOntConverter extends ConstantsOntAPI
{
   /**
    *  URI OWL - стандартное пространство имен
    */
   final String IRI_OWL = "http://www.w3.org/2002/07/owl";
   final String IRI_RDFS = "http://www.w3.org/2000/01/rdf-schema";
   final String IRI_RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns";

	
   ///////////////////////////////////////////////////////////////////////////
   //IRI элементов онтологии пользовательского представления./////////////////
   ///////////////////////////////////////////////////////////////////////////
   
	/**
	 * 
	 */
	final static String UPO_IRI = "http://www.iimm.ru/ont-library/ont-UPO.owl#";
	final static String UPO_HAS_TYPE = UPO_IRI+"has-Type";
	
	/**
	 * IRI корневой сущности Thing.
	 */
	final static String UPO_THING = UPO_IRI+"Thing";
	

	
	//final static String UPO_RELATED = UPO_IRI+"related";
	
	/**
	 * Шаблон IRI, соответствующих экземплярам сложных субаксиом. 
	 */
	final static String UPO_TEMPLATE_IRI_OF_COMLEXSBAXIOM = UPO_IRI + "compSbAxiom";
	
		
	/**
	 * Шаблон IRI, соответствующих экземплярам-диапозонам значений (простым типам). 
	 */
	final static String UPO_TEMPLATE_IRI_OF_DATARANGE = UPO_IRI + "range-";

	
	/**
	 * Шаблон лейбла label, соответствующего экземплярам субакиомам-отрицаниям. 
	 */
	final static String UPO_NEGATION_LABEL_TEMPLATE ="not";
	
	/** Фрагмент запроса */
	final static String UPO_SPARQL_QUERY_FRAGMENT_LABEL = UPO_IRI+"SPARQL-QueryFragment";

	
	
	/**
	.* Переменная
	 */
	final static String UPO_SPARQL_VARIABLE_LABEL = UPO_IRI+"SPARQL-Var";

	/**
	.* Название концепта
	 */
	final static String UPO_TITLE_LABEL = IRI_RDFS+"#label";
	/**
	 * Разделитель в названии сложной субаксиомы.
	 */
	final static String UPO_TITLE_DELIMITER = "--";

	/**
	 * Разделитель в названии сложной субаксиомы.
	 */
	final static String UPO_PROP_VAL_DELIMITER = "=>";


	
	/**
	 * Идентификатор англ. языка
	 */
	final static String LANG_EN = "en";
	
	/**
	 * Идентификатор великого и могучего
	 */
	final static String LANG_RU = "ru";
	
	
	//====== Типы аксиом в контексте визуализации для AnalysisVisitor ===========	
//	final static String VIS_TYPE_BASE_SIMPLE = "simple-axiom-base-vis";
	//final static String VIS_TYPE_BASE_COMPOUND = "compound-axiom-base-vis";
	
	/**
	 * Визуализация аксиом типа A [subclass,equvalent] hasValue [some,any,min,max] C
	 * как A ---hasValue---> C
	 */
//	final static String VIS_TYPE_SPEC_SIMPLE_RESTRICTION = "simple-restriction-axiom";
	
	/**
	 * Визуализация аксиом CGI. Типа АнонимныйКласс [subclass] hasValue [some,any,min,max] 
	 */
//	final static String VIS_TYPE_SPEC_CGI = "spec-cgi";
	
//	final static String VIS_TYPE_UNKNOWN = "not-def";
	
	
	//====== Другие константы ===================================
	
	/**
	 * 
	 */
	//final static String EMPTY = "-empty-";
	
	
	/**
	 * Указывает что аксиома простая. 
	 */
	final static String AX_SIMPLE = "-simple";
	
	/**
	 * Указывает что аксиома составная. 
	 */
	final static String AX_COMP = "-compound";

}
