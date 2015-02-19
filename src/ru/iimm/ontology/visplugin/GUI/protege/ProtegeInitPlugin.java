package ru.iimm.ontology.visplugin.GUI.protege;


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

import ru.iimm.ontology.visplugin.GUI.ContentView;
import ru.iimm.ontology.visplugin.lang.Language;
import ru.iimm.ontology.visplugin.lang.LanguageRU;
import ru.iimm.ontology.visplugin.tools.OntologyManager;



/**
 * Главная вкладка плагина. В которой размещаем визуализатор и панель с настройками.
 * @author Danilov
 *
 */
public class ProtegeInitPlugin extends AbstractOWLClassViewComponent
{
	
	private static final long serialVersionUID = 1L;
	
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

						OntologyManager ontMng = new OntologyManager(getOWLEditorKit(), true, true, true);
						
						switch(comboBox.getSelectedIndex())
						{
							case 0 : {
								ContentView ontologyView = new ContentView(ontMng.getCongitiveFrameOntology(), ontMng.getProtegeOWLEditor());
								
								mainPanel.removeAll();
								mainPanel.add(ontologyView.getContentView(), BorderLayout.CENTER);
								mainPanel.updateUI();
								break;
							}
							case 1 : {
								lang = new LanguageRU();
								lang.initializeLang();
								
								ContentView ontologyView = new ContentView(ontMng.getCongitiveFrameOntology(), ontMng.getProtegeOWLEditor());
								
								mainPanel.removeAll();
								mainPanel.add(ontologyView.getContentView(), BorderLayout.CENTER);
								mainPanel.updateUI();
								break;
								}
						}
							
					}
				});
				
				thread.setDaemon(true);
				thread.start();
			}
		});
		
		JPanel cont = new JPanel(new FlowLayout());
		//cont.setLayout(new BoxLayout(cont, BoxLayout.X_AXIS));
		//cont.setPreferredSize(new Dimension(300, 300));
		//cont.setMaximumSize(new Dimension(300, 300));
		//cont.setMinimumSize(new Dimension(300, 300));
		
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
