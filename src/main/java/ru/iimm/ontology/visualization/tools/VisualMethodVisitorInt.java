package ru.iimm.ontology.visualization.tools;

import ru.iimm.ontology.cftools.DependencyCFrame;
import ru.iimm.ontology.cftools.PartonomyCFrame;
import ru.iimm.ontology.cftools.SpecialCFrame;
import ru.iimm.ontology.cftools.TaxonomyCFrame;

/**
 * Интерфейс для визуального метода.
 * @author Danilov E.Y.
 */
public interface VisualMethodVisitorInt
{	
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
}
