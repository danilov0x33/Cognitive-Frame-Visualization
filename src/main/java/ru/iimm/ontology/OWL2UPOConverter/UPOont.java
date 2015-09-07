/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.ontAPI.Ontology;

/**
 * Онтология пользовательского представления.
 * Реализует паттерн "Одиночка".
 * @author Lomov P. A.
 *
 */
public class UPOont extends UserPresenOnt
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OWLont.class);
    
    private static UPOont instance;


    /**
     * @param dir
     * @param fileName
     * @param mergeImportedOntology
     * @param makeConsitnenseCheck
     */
    private UPOont(String dir, String fileName, Boolean mergeImportedOntology)
    {
    	super(dir, fileName, mergeImportedOntology);
    }

    /**
     * 
     * @return
     */
    public static UPOont getUPOont()
    {
        if (instance == null)
        {
            LOGGER.error("!ERROR! Run init() first!");	    	    
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
	    Boolean mergeImportedOntology)
    {
        if (instance == null)
        {
            instance = new UPOont(dir, fileName, mergeImportedOntology);
        }
	
    }
    
    /**
     * Возвращаетс строку в квадратных скобках. 
     * @param str
     * @return
     */
    public static String getSBrackedString(String str)
    {
	return "["+str+"]";
    }

    /**
     * Возвращаетс строку в фигурных скобках. 
     * @param str
     * @return
     */
    public static String getFBrackedString(String str)
    {
	return "{"+str+"}";
    }
    

}
