package ru.iimm.ontology.visplugin.GUI;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.protege.editor.owl.OWLEditorKit;

import ru.iimm.ontology.cftools.CFrameOnt;
import ru.iimm.ontology.ontAPI.Ontology;

/**
 * Инициализация основной графической оболочки плагина.
 * @author Danilov E.Y.
 *
 */
public class ContentView
{
	/**Панель, на которой размещается список классов из онтологии и панель с визуализацией.*/
	private JPanel mainPanel;
	/**Панель с визуализации.*/
	private OntVisView ontVisView;
	/**Онтология с КФ.*/
	private CFrameOnt cfOnt;
	/**Менеджер онтологии из Protege.*/
	private OWLEditorKit owlEditorKit;
	/**Активная онтология.*/
	private Ontology ontology;
	/**Списки с классами онтологии.*/
	private ObservableOntologyClassListView ontologyClassListView;
	/**
	 * 
	 * {@linkplain ContentView}
	 * @param cfOnt - {@linkplain #cfOnt}
	 */
	public ContentView(CFrameOnt cfOnt)
	{
		this(cfOnt,null,null);
	}

	/**
	 * 
	 * {@linkplain ContentView}
	 * @param ontology - {@linkplain #ontology}
	 */
	public ContentView(Ontology ontology)
	{
		this(null,ontology);
	}
	/**
	 * 
	 * {@linkplain ContentView}
	 * @param cfOnt - {@linkplain #cfOnt}
	 * @param ontology - {@linkplain #ontology}
	 * @param owlEditorKit - {@linkplain #owlEditorKit}
	 */
	public ContentView(CFrameOnt cfOnt, Ontology ontology, OWLEditorKit owlEditorKit)
	{
		this.cfOnt = cfOnt;
		this.owlEditorKit = owlEditorKit;	
		this.ontology = ontology;
		
		this.init();
	}
	/**
	 * 
	 * {@linkplain ContentView}
	 * @param cfOnt - {@linkplain #cfOnt}
	 * @param owlEditorKit - {@linkplain #owlEditorKit}
	 */
	public ContentView(CFrameOnt cfOnt, OWLEditorKit owlEditorKit)
	{
		this(cfOnt,null,owlEditorKit);
	}
	/**
	 * 
	 * {@linkplain ContentView}
	 * @param cfOnt - {@linkplain #cfOnt}
	 * @param ontology - {@linkplain #ontology}
	 */
	public ContentView(CFrameOnt cfOnt, Ontology ontology)
	{
		this(cfOnt,ontology,null);
	}
	/**
	 * 
	 * {@linkplain ContentView}
	 * @param owlEditorKit - {@linkplain #owlEditorKit}
	 */
	public ContentView(OWLEditorKit owlEditorKit)
	{
		this(null,owlEditorKit);
	}


	private void init()
	{
		this.mainPanel = new JPanel(new BorderLayout());
		
		this.ontVisView = new OntVisView();
		
		this.ontologyClassListView = new ObservableOntologyClassListView(this.cfOnt, this.ontology, this.owlEditorKit);		
		this.ontologyClassListView.registerObserverOntClassList(this.ontVisView);
		
		JSplitPane spPanel = new JSplitPane();
		spPanel.setLeftComponent(this.ontologyClassListView);
		spPanel.setRightComponent(this.ontVisView.getGUIComponent());
		spPanel.setResizeWeight (0);
		spPanel.setDividerLocation (300);
		
		this.mainPanel.add(spPanel,BorderLayout.CENTER);
	}
	
	/**
	 * Возвращяет панель с контентом.
	 * @return {@linkplain #mainPanel}
	 */
	public JPanel getContentView()
	{
		return this.mainPanel;
	}

}
