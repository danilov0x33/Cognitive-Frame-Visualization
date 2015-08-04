package ru.iimm.ontology.visualization.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import ru.iimm.ontology.visualization.tools.CFrameDecorator;
import ru.iimm.ontology.visualization.tools.OntologyManager;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultModelCFrame;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultPresenterCFrameCajunVisitor;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultPresenterCFrameGSVisitor;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultPresenterCFramePrefuseVisitor;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultViewCFrameVisCajun;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultViewCFrameVisGS;
import ru.iimm.ontology.visualization.ui.mvp.impl.DefaultViewCFrameVisPrefuse;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public class MultiView
{
	public static void main(String[] args)
	{
		OntologyManager ont = new OntologyManager("CognitiveFrameVisualization\\network-tech-ont.owl\\rez-cfo\\", "ontUPO.owl", false, false, true);
		
		DefaultModelCFrame model = new DefaultModelCFrame(ont.getCongitiveFrameOntology());
		
		DefaultViewCFrameVisCajun viewCajun = new DefaultViewCFrameVisCajun();
		DefaultPresenterCFrameCajunVisitor presenterCajun = new DefaultPresenterCFrameCajunVisitor();
		presenterCajun.setModel(model);
		presenterCajun.setView(viewCajun);	
		viewCajun.setPresenter(presenterCajun);
		viewCajun.open();
		
		DefaultViewCFrameVisGS viewGS = new DefaultViewCFrameVisGS();
		DefaultPresenterCFrameGSVisitor presenterGS = new DefaultPresenterCFrameGSVisitor();
		presenterGS.setModel(model);
		presenterGS.setView(viewGS);
		viewGS.setPresenter(presenterGS);
		viewGS.open();
		
		DefaultViewCFrameVisPrefuse viewPrefuse = new DefaultViewCFrameVisPrefuse();	
		DefaultPresenterCFramePrefuseVisitor presenterPrefuse = new DefaultPresenterCFramePrefuseVisitor();
		presenterPrefuse.setModel(model);
		presenterPrefuse.setView(viewPrefuse);
		viewPrefuse.setPresenter(presenterPrefuse);
		viewPrefuse.open();
		
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab(presenterCajun.getNameVisualMethod(), viewCajun.getViewComponent());
		tabbedPane.addTab(presenterGS.getNameVisualMethod(), viewGS.getViewComponent());
		tabbedPane.addTab(presenterPrefuse.getNameVisualMethod(), viewPrefuse.getViewComponent());
		
		CFrameDecorator cFrame = model.getCframes().get(1);
		
		cFrame.accept(presenterCajun);
		cFrame.accept(presenterGS);
		cFrame.accept(presenterPrefuse);
		
		JFrame frame = new JFrame();
		
		frame.getContentPane().add(tabbedPane);
		
		frame.setSize(800, 600);
		frame.setVisible(true);
		
		cFrame = model.getCframes().get(0);
		
		cFrame.accept(presenterCajun);
		cFrame.accept(presenterGS);
		cFrame.accept(presenterPrefuse);
	}
}
