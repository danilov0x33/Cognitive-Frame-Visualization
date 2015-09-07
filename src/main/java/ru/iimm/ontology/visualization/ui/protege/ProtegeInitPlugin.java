package ru.iimm.ontology.visualization.ui.protege;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.protege.editor.owl.ui.view.cls.AbstractOWLClassViewComponent;
import org.semanticweb.owlapi.model.OWLClass;

import ru.iimm.ontology.visualization.lang.Language;
import ru.iimm.ontology.visualization.lang.LanguageRU;
import ru.iimm.ontology.visualization.ui.mvp.impl.models.ModelCFrameOntologyImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.models.ModelMultiOntologyImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.presenters.PresenterCFrameCajunVisitorImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.presenters.PresenterCFrameGSVisitorImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.presenters.PresenterCFramePrefuseVisitorImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.presenters.RedirectingPresenterCFrameTreeNode;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewCFrameVisCajunImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewCFrameVisGSImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewCFrameVisPrefuseImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewTreeNodeImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewTreeNodeWithVis;
import ru.iimm.ontology.visualization.ui.mvp.models.ModelMultiOntology;



/**
 * Главная вкладка плагина. В которой размещаем визуализатор и панель с настройками.
 * @author Danilov
 *
 */
public class ProtegeInitPlugin extends AbstractOWLClassViewComponent
{

	private static final long serialVersionUID = 2666595749891729845L;
	private Language lang;
	
	@Override
	public void initialiseClassView() throws Exception 
	{ 			
		
		this.setLayout(new BorderLayout());	
		
		JLabel setLang = new JLabel(Language.LABEL_SELECT_LANGUAGE+":   ");
		
		String[] langList = { "English", "Russian"};
		
		final JPanel mainPanel = new JPanel(new BorderLayout());
		
		final JComboBox<String> comboBox = new JComboBox<String>(langList);
		//comboBox.setPreferredSize(new Dimension(100, 30));
		//comboBox.setMaximumSize(new Dimension(100, 30));
		//comboBox.setMinimumSize(new Dimension(300, 300));
		comboBox.setSelectedIndex(0);
		
		final JButton butSelect = new JButton(Language.LABEL_GET_COGNITIVE_FRAME_VISUALIZATION);
		//butSelect.setPreferredSize(new Dimension(100, 30));
		//butSelect.setMaximumSize(new Dimension(100, 30));
		
		
		butSelect.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				Thread thread = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						butSelect.setEnabled(false);
						comboBox.setEnabled(false);
						butSelect.setText(Language.LABEL_LOADING);
						
						ModelMultiOntology ontMng = new ModelMultiOntologyImpl(getOWLEditorKit(), true, true, true);
						
						//Presenter который перенаправляет event из TreeNode в  визуализации
						RedirectingPresenterCFrameTreeNode redPresenter = new RedirectingPresenterCFrameTreeNode();
						redPresenter.setModel(new ModelCFrameOntologyImpl(ontMng.getCongitiveFrameOntology()));
						
						//Дерево с фреймами
						ViewTreeNodeImpl viewTreeNodeCFrame = new ViewTreeNodeImpl();
						redPresenter.setView(viewTreeNodeCFrame);
						viewTreeNodeCFrame.setPresenter(redPresenter);
						
						
						
						//Presenter и view визуализаций
						PresenterCFrameCajunVisitorImpl presCajun = new PresenterCFrameCajunVisitorImpl();
						ViewCFrameVisCajunImpl viewCajunCF = new ViewCFrameVisCajunImpl();
						presCajun.setModel(redPresenter.getModel());
						presCajun.setView(viewCajunCF);
						viewCajunCF.setPresenter(presCajun);
						redPresenter.addVisualMethod(presCajun);				
											
							

						PresenterCFramePrefuseVisitorImpl presPref = new PresenterCFramePrefuseVisitorImpl();
						ViewCFrameVisPrefuseImpl viewPref = new ViewCFrameVisPrefuseImpl();
						presPref.setModel(redPresenter.getModel());
						presPref.setView(viewPref);
						viewPref.setPresenter(presPref);
						redPresenter.addVisualMethod(presPref);
							
	
						PresenterCFrameGSVisitorImpl presGS = new PresenterCFrameGSVisitorImpl();			
						ViewCFrameVisGSImpl viewGSCF = new ViewCFrameVisGSImpl();
						presGS.setModel(redPresenter.getModel());
						presGS.setView(viewGSCF);
						viewGSCF.setPresenter(presGS);
						redPresenter.addVisualMethod(presGS);

									

						
						//Все добавляем в контейнер
						ViewTreeNodeWithVis multiView = new ViewTreeNodeWithVis();
						multiView.addTreeNode(viewTreeNodeCFrame.getViewComponent(), "Tree CFrames");
						multiView.addVisualization(viewGSCF, presGS.getNameVisualMethod());
						multiView.addVisualization(viewPref, presPref.getNameVisualMethod());
						multiView.addVisualization(viewCajunCF, presCajun.getNameVisualMethod());
						
						mainPanel.removeAll();
						mainPanel.add(multiView.getViewComponent(), BorderLayout.CENTER);
						mainPanel.updateUI();
						
						switch(comboBox.getSelectedIndex())
						{
							case 0 : break;
							case 1 : 
							{
								lang = new LanguageRU();
								lang.initializeLang();
								break;
							}
							default : break;
						}	
					}
				});
				
				thread.setDaemon(true);
				thread.start();
			}
		});
		
		JPanel cont = new JPanel(new FlowLayout());
		
		cont.add(setLang);
		cont.add(comboBox);
		cont.add(butSelect);
		
		mainPanel.add(cont,BorderLayout.NORTH);

		this.add(mainPanel,BorderLayout.CENTER);
	}
	
	/**
	 * Метод вызывается, когда выбирается один из классов(OWLClass) в списке классов.
	 */
	@Override
	protected OWLClass updateView(OWLClass selectedClass) 
	{
		return null;
	}

	@Override
	public void disposeView() {}

}
