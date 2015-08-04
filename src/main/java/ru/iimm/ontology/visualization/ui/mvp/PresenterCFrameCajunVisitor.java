package ru.iimm.ontology.visualization.ui.mvp;

import ru.iimm.ontology.cftools.DependencyCFrame;
import ru.iimm.ontology.cftools.PartonomyCFrame;
import ru.iimm.ontology.cftools.SpecialCFrame;
import ru.iimm.ontology.cftools.TaxonomyCFrame;
import ru.iimm.ontology.visualization.tools.VisualMethodVisitorInt;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public interface PresenterCFrameCajunVisitor extends PresenterCFrame, VisualMethodVisitorInt
{
	/**Создание визуальной части для Taxonomy CFrame.*/
	//void visit(TaxonomyCFrame tFrame);
	/**Создание визуальной части для Partonomy CFrame.*/
	//void visit(PartonomyCFrame pFrame);
	/**Создание визуальной части для Dependency CFrame.*/
	//void visit(DependencyCFrame dFrame);
	/**Создание визуальной части для Special CFrame.*/
	//void visit(SpecialCFrame sFrame);
	
	void setView(ViewCFrameVisCajun view);
	ViewCFrameVisCajun getView();
}
