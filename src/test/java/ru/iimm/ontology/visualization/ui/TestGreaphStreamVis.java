package ru.iimm.ontology.visualization.ui;

import javax.swing.JFrame;

import ru.iimm.ontology.visualization.tools.CFrameDecorator;
import ru.iimm.ontology.visualization.tools.OntologyManager;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultModelCFrame;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultPresenterCFrameGSVisitor;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultViewCFrameVisGS;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public class TestGreaphStreamVis
{
	public static void main(String[] args)
	{
		OntologyManager ont = new OntologyManager("CognitiveFrameVisualization\\network-tech-ont.owl\\rez-cfo\\", "ontUPO.owl", false, false, true);
		
		DefaultModelCFrame model = new DefaultModelCFrame(ont.getCongitiveFrameOntology());
		DefaultViewCFrameVisGS view = new DefaultViewCFrameVisGS();
		
		DefaultPresenterCFrameGSVisitor presenter = new DefaultPresenterCFrameGSVisitor();
		presenter.setModel(model);
		presenter.setView(view);
		
		view.open();
		
		CFrameDecorator cFrame = model.getCframes().get(0);
		
		cFrame.accept(presenter);
		
		JFrame frame = new JFrame();
		
		frame.getContentPane().add(view.getViewComponent());
		
		frame.setSize(800, 600);
		frame.setVisible(true);
		
		cFrame = model.getCframes().get(1);
		
		cFrame.accept(presenter);
		
	}
}
