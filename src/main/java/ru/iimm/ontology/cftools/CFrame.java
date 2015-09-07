/**
 * 
 */
package ru.iimm.ontology.cftools;

import java.util.HashSet;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import rationals.converters.ToString;
import ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt;

/**
 * Интерфейс включает методы когнитивного фрейма.
 * @author Lomov P.A.
 *
 */
public interface CFrame
{
	
	
	/**
	 * Принимает посетителя для обработки К-фрейма.
	 * @param visitor
	 */
	void accept(CFrameVisitor visitor);
	
	public CFrameContent getContent();
	
	public CFrameVisualisationMethod getVmethod();
	public OWLNamedIndividual getTrgConcept();
	public OWLClass getCFrameOWLClass();
	public String toString();
	
	

}
