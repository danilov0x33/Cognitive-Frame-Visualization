package ru.iimm.ontology.ontAPI;



/**
 *    Интерфейс констант тезауруса.
 * thesaurusAPI.ShareConstOfTesaurus.java
 * @author Lomov P. A.
 * @version 1
 */
public interface ConstantsOntAPI
{
	int DEBUG=0;
	
	//Name of THING term
	static String CONST_THING_NAME = "Thing";

	/**
       *  Строка определяющая пустое значение
       */
	 final static String EMPTY = "_EMPTY_";
    
	 /**
       * IRI of OWL - стандартное пространство имен
       */
     final String IRI_OF_OWL = "http://www.w3.org/2002/07/owl#";
    
     /**
        * IRI of RDF SCHEMA
        */
    final String IRI_OF_RDFS = "http://www.w3.org/2000/01/rdf-schema#";
        
       /**
        * IRI of RDF syntax
        */
    final String IRI_OF_RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
       
       /**
        * IRI свойства, указывающего на принадлежность элемента к классу
        */
    final String IRI_RDF_TYPE =  IRI_OF_RDF + "type";

    /**
      * Короткий IRI свойства, указывающего на принадлежность элемента к классу
      */
    final String IRI_RDF_TYPE_SHORT =  "rdf:type";

    final static String RDF_LABEL=IRI_OF_RDFS+"label";
    
    //============= Элементы языка OWL ==============================
    //===============================================================   
    final static String IRI_OWL = "http://www.w3.org/2002/07/owl#";
    final static String OWL_TOP_OBJ_PROPERTY = IRI_OWL + "topObjectProperty";
    final static String OWL_BOTTOM_OBJ_PROPERTY = IRI_OWL + "bottomObjectProperty"; 
    
   	final static String OWL_THING =IRI_OWL+"Thing";// use getOWLThing() instead
   	final static String OWL_NOTHING =IRI_OWL+"Nothing";
   	
       
    //============= Элементы онтологии SKOS =========================
    //===============================================================   
	/*
	 * TODO заменил IRI скоса т.к. при сохранении в OWL API его элементы
	 * превращались в экземпляры - поменял обратно 
	 */
	final static String IRI_SKOS ="http://www.w3.org/2004/02/skos/core#";
	//final static String IRI_SKOS ="http://www.w3.org/2004/02/skos/main#";	
	final static String SKOS_HAS_MEMBER =IRI_SKOS+"member";
	final static String SKOS_BROADER =IRI_SKOS+"broader";
	final static String SKOS_NARROWER =IRI_SKOS+"narrower";
	final static String SKOS_RELATED = IRI_SKOS+"related";
	final static String SKOS_COLLECTION =IRI_SKOS+"Collection";
	final static String SKOS_CONCEPT =IRI_SKOS+"Concept";
	final static String SKOS_HIDDEN_LABEL =IRI_SKOS+"hiddenLabel";
	final static String SKOS_PREF_LABEL =IRI_SKOS+"prefLabel";
	
	//=============Специальные константы ===========================
	
	/**
	 * Тип по умолчанию у типизированных свойств. Он указывается,
	 * если явно в исходной онтологии тип не указан. 
	 */
	final static String DEFAULT_DATATYPE = IRI_OF_RDFS+ "Literal";

	/**
	 * Значение языкового тэга в литералах, указывающее на то, что
	 * значение литерала на английском языке
	 */
	final static String LANG_EN = "en";
	
	/**
	 * Разделитель, используемый в IRI элементов онтологии
	 */
	final static String DELIMITER_NAME="-";
	
	/**
	 * Разделитель, используемый в IRI элементов онтологии
	 */
	final static String IRI_SEPARATOR ="/";

	
}
