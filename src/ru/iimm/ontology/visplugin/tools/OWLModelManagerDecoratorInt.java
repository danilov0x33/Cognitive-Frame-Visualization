package ru.iimm.ontology.visplugin.tools;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * Интерфейс для оболочки OWLModelManager.
 * @author Danilov E.Y.
 *
 */
public interface OWLModelManagerDecoratorInt
{
	/**Создает визуализацию для OWLModelManager.*/
	void builderViewer();

	public ArrayList<AbstractVisualMethodVisitor> getVisualMethodList();
	
	public OWLOntologyManager getManager();
	public void setManager(OWLOntologyManager manager);
	public OWLOntology getOntology();
	public void setOntology(OWLOntology ontology);
	public OWLReasoner getReasoner();
	public void setReasoner(OWLReasoner reasoner);
	
	public void setVisualMethodList(ArrayList<AbstractVisualMethodVisitor> visualMethodList);
}
