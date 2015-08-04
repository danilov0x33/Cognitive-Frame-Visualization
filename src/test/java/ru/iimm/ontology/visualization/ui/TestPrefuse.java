package ru.iimm.ontology.visualization.ui;

import javax.swing.JFrame;

import ru.iimm.ontology.visualization.tools.CFrameDecorator;
import ru.iimm.ontology.visualization.tools.OntologyManager;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultModelCFrame;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultPresenterCFramePrefuseVisitor;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultViewCFrameVisPrefuse;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public class TestPrefuse
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		OntologyManager ont = new OntologyManager("CognitiveFrameVisualization\\network-tech-ont.owl\\rez-cfo\\", "ontUPO.owl", false, false, true);
		
		DefaultModelCFrame model = new DefaultModelCFrame(ont.getCongitiveFrameOntology());
		DefaultViewCFrameVisPrefuse view = new DefaultViewCFrameVisPrefuse();
		
		DefaultPresenterCFramePrefuseVisitor presenter = new DefaultPresenterCFramePrefuseVisitor();
		presenter.setModel(model);
		presenter.setView(view);
		
		view.setPresenter(presenter);
		view.open();
		
		CFrameDecorator cFrame = model.getCframes().get(1);
		
		//cFrame.accept(presenter);
		
		JFrame frame = new JFrame();
		
		frame.getContentPane().add(view.getViewComponent());
		
		frame.setSize(800, 600);
		frame.setVisible(true);
		
		cFrame = model.getCframes().get(0);
		
		cFrame.accept(presenter);
	}

}
