package ru.iimm.ontology.visualization.tools;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import ru.iimm.ontology.cftools.DependencyCFrame;
import ru.iimm.ontology.cftools.PartonomyCFrame;
import ru.iimm.ontology.cftools.SpecialCFrame;
import ru.iimm.ontology.cftools.TaxonomyCFrame;
import ru.iimm.ontology.visualization.ui.GUIVisualMethodInt;

/**
 * Интерфейс для визуального метода.
 * @author Danilov E.Y.
 *
 */
public interface VisualMethodVisitorInt
{
	/**Возвращает компонент с визуализацией.*/
	//public GUIVisualMethodInt getVisualization();
	
	/**Завершает компонент с визуализацией.*/
	//public void disposeView();
	
	/**Возвращает значение, было ли произведено построение визуализации.*/
	//public boolean isVisitViewer();
	
	/**
	 * Устанавливает значение, было ли произведено построение визуализации.
	 * @param b - true - если было построение визуализации.
	 */
	//public void setVisitViewer(boolean b);
	
	public String getNameVisualMethod();
	public void setNameVisualMethod(String name);
	
	/**Создание визуальной части для Taxonomy CFrame.*/
	public void visit(TaxonomyCFrame tFrame);
	/**Создание визуальной части для Partonomy CFrame.*/
	public void visit(PartonomyCFrame pFrame);
	/**Создание визуальной части для Dependency CFrame.*/
	public void visit(DependencyCFrame dFrame);
	/**Создание визуальной части для Special CFrame.*/
	public void visit(SpecialCFrame sFrame);
	/**Создание визуальной части для OWLModelManager.*/
	//public void visit(OWLOntologyManager manager, OWLOntology ontology, OWLReasoner reasoner);
}
