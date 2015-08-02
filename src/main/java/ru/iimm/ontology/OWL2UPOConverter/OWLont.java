/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter;

import org.semanticweb.owlapi.model.IRI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.ontAPI.Ontology;

/**
 * Исходная OWL-онтология.
 * Класс реализует паттерн "Одиночка"
 * @author Lomov P. A.
 *
 */
public class OWLont extends Ontology
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OWLont.class);
    
    private static OWLont instance;

    /**
     * @param dir
     * @param fileName
     * @param mergeImportedOntology
     * @param makeConsitnenseCheck
     */
    private OWLont(String dir, String fileName,
	    Boolean mergeImportedOntology, Boolean makeConsitnenseCheck)
    {
	super(dir, fileName, mergeImportedOntology, makeConsitnenseCheck);
	// TODO Auto-generated constructor stub
    }

    /**
     * 
     * @return
     */
    public static OWLont getOWLont()
    {
        if (instance == null)
        {
            LOGGER.error("!ERROR! Run OWLont first!");	    	    
        }
        return instance;
    }
    
    /**
     * Загружает онтологию.
     * @param dir
     * @param fileName
     * @param mergeImportedOntology
     * @param makeConsitnenseCheck
     */
    public static void init(String dir, String fileName,
	    Boolean mergeImportedOntology, Boolean makeConsitnenseCheck)
    {
        if (instance == null)
        {
            instance = new OWLont(dir, fileName, mergeImportedOntology, makeConsitnenseCheck);
        }
	
    }

}
