package ru.iimm.ontology.visualization.ui;

import javax.swing.JFrame;

import ru.iimm.ontology.visualization.ui.mvp.impl.models.ModelMultiOntologyImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.models.ModelOwlOntologyImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.presenters.PresenterOwlClassPrefuseImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewOWLClassPrefuseImpl;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public class TestPrefuse
{
	public static void main(String[] argv)
	{
		ModelMultiOntologyImpl model = new ModelMultiOntologyImpl("CognitiveFrameVisualization/network-tech-ont.owl/rez-cfo/", "ontUPO.owl", false, false, true);
		
		PresenterOwlClassPrefuseImpl presenter = new PresenterOwlClassPrefuseImpl();
		ViewOWLClassPrefuseImpl view = new ViewOWLClassPrefuseImpl();
		
		model.setPresenter(presenter);
		presenter.setModel(new ModelOwlOntologyImpl(model.getCongitiveFrameOntology().mng, model.getCongitiveFrameOntology().ontInMem, model.getCongitiveFrameOntology().reas));
		presenter.setView(view);
		
		view.setPresenter(presenter);
		view.open();
		//presenter.updateGraphFromModel();
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.getContentPane().add(view.getViewComponent());
		
		frame.setVisible(true);
		
	}
}
