package ru.iimm.ontology.visplugin.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.renderer.OWLIconProviderImpl;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.CFrameOnt;
import ru.iimm.ontology.ontAPI.Ontology;
import ru.iimm.ontology.visplugin.lang.Language;
import ru.iimm.ontology.visplugin.tools.CFrameDecorator;
import ru.iimm.ontology.visplugin.tools.CFrameDecoratorInt;
import ru.iimm.ontology.visplugin.tools.OWLModelManagerDecorator;
import ru.iimm.ontology.visplugin.tools.VisualMethodVisitorInt;

/**
 * Различные списки классов из онтологий.
 * 
 * @author Danilov E.Y.
 *
 */
public class ObservableOntologyClassListView extends JTabbedPane implements ObservableOntClassListInt,GUIElementInt
{
	private static final long serialVersionUID = -2074230643605784512L;
	/**Список слушателей.*/
	private ArrayList<ObserverOntClassListInt> observerList;
	/**Оболочка для OWLModelManager.*/
	private OWLModelManagerDecorator owlModelManagerDecorator;
	/**UPO.*/
	private CFrameOnt cfOnt;
	/**Активная онтология в Protege.*/
	private OWLEditorKit owlEditorKit;
	/**Активная онтология.*/
	private Ontology ontology;
	/**Панель, в которой содержится список с CFrame.*/
	private JPanel cfOntClassListPanel;
	/**Панель, в которой содержится список с OWLCLass из Protege.*/
	private JPanel owlEditorKitClassListPanel;
	/**Панель, в которой содержится список с OWLCLass.*/
	private JPanel owlClassListPanel;
	/**Дерево для OWLClass.*/
	private JTree owlClassTree;
	/**Дерево для OWLClass.*/
	private JTree cfOntClassTree;

	/**
	 * {@linkplain ObservableOntologyClassListView}
	 * @param cfOnt - {@linkplain #cfOnt}
	 */
	public ObservableOntologyClassListView(CFrameOnt cfOnt)
	{
		this(cfOnt, null, null);
	}
	
	/**
	 * {@linkplain ObservableOntologyClassListView}
	 * @param owlEditorKit - {@linkplain #owlEditorKit}
	 */
	public ObservableOntologyClassListView(OWLEditorKit owlEditorKit)
	{
		this(null, owlEditorKit);
	}
	/**
	 * {@linkplain ObservableOntologyClassListView}
	 * @param cfOnt - {@linkplain #cfOnt}
	 * @param owlEditorKit - {@linkplain #owlEditorKit}
	 */
	public ObservableOntologyClassListView(CFrameOnt cfOnt, OWLEditorKit owlEditorKit)
	{
		this(cfOnt, null, owlEditorKit);
	}
	/**
	 * {@linkplain ObservableOntologyClassListView}
	 * @param cfOnt - {@linkplain #cfOnt}
	 * @param ontology - {@linkplain #ontology}
	 */
	public ObservableOntologyClassListView(CFrameOnt cfOnt, Ontology ontology)
	{
		this(cfOnt, ontology, null);
	}
	/**
	 * {@linkplain ObservableOntologyClassListView}
	 * @param cfOnt - {@linkplain #cfOnt}
	 * @param ontology - {@linkplain #ontology}
	 * @param owlEditorKit - {@linkplain #owlEditorKit}
	 */
	public ObservableOntologyClassListView(CFrameOnt cfOnt, Ontology ontology,OWLEditorKit owlEditorKit)
    {
		super();
		this.cfOnt = cfOnt;
		this.owlEditorKit = owlEditorKit;
		this.ontology = ontology;
		this.observerList = new ArrayList<ObserverOntClassListInt>();
		
		if(this.cfOnt != null)
			this.initCFrameTreeOnt();	
		
		if(this.ontology != null)
			this.initOWLClassList();
		
		if(this.owlEditorKit != null)
			this.initOWLEditorKitList();
    }

	/**
	 * Рекурсивный метод для добовления в дерево потомков OWLClass.
	 * @param top 
	 * @param owlClass
	 */
	private void addOwlClassTree(DefaultMutableTreeNode top, OWLClass owlClass, OWLReasoner owlRes)
	{		
		for (OWLClass childrenClass : owlRes.getSubClasses(owlClass, true).getFlattened())
		{
			if (childrenClass.isOWLNothing()) continue;
			
			DefaultMutableTreeNode tree = new DefaultMutableTreeNode(childrenClass);
			this.addOwlClassTree(tree, childrenClass, owlRes);
			top.add(tree);
		}
	}
	
	/**
	 * Инициализация для списка OWLClass'ов из Progete.
	 */
	private void initOWLClassList()
	{
		this.owlClassListPanel = new JPanel(new BorderLayout());
		this.owlClassListPanel.setPreferredSize(new Dimension(300, 100));
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode();
		
		OWLOntology onto = this.ontology.ontInMem;
		
		OWLClass owlClass = onto.getOWLOntologyManager().getOWLDataFactory().getOWLThing();
		top =new DefaultMutableTreeNode(owlClass);
		this.addOwlClassTree(top, owlClass, this.ontology.reas);
		
        
		this.owlClassTree = new JTree(top);
        this.owlClassTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        //Отображение элементов в дереве.
        this.owlClassTree.setCellRenderer(new TreeCellRenderer()
		{
            private JLabel label = new JLabel();
     
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Object o = ((DefaultMutableTreeNode) value).getUserObject();
                if (o instanceof OWLClass) {
                	OWLClass owlClass = (OWLClass) o;
            		
                    //label.setIcon(icon);
                    label.setText(owlClass.getIRI().getFragment());
                    
                } 
                else 
                {
                    label.setIcon(null);
                    label.setText("" + value);
                }
                
                //Font f = label.getFont();
                if(selected)
                {
                	label.setForeground(Color.BLUE);
                	//label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
                }
                else
                {
                	label.setForeground(Color.BLACK);
                	//label.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
                }
                
                return label;
            }
		});
        
        //При нажатии на элемеет (OWLClass из дерева) оповещаем слушателей.
		this.owlClassTree.addTreeSelectionListener(new TreeSelectionListener()
		{
			@Override
			public void valueChanged(TreeSelectionEvent arg0)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) owlClassTree.getLastSelectedPathComponent();

				if (node == null)
					return;

				ObservableOntologyClassListView.this.notifyOWLClassObserverOntClassList((OWLClass)node.getUserObject());
			}
		});
		
		this.owlClassTree.expandRow(0);
		
		final JScrollPane northScroll = new JScrollPane(this.owlClassTree);		
		
		//Создаем оболочку с визуализацией для OWLModelManager
		this.owlModelManagerDecorator = new OWLModelManagerDecorator(this.ontology.mng, this.ontology.ontInMem, this.ontology.reas);
		
		this.owlClassListPanel.add(northScroll,BorderLayout.CENTER);
		
		this.addTab(Language.LABEL_OWLCLASS_LIST, this.owlClassListPanel);
	}
	
	/**
	 * Инициализация для списка OWLClass'ов из Progete.
	 */
	private void initOWLEditorKitList()
	{
		this.owlEditorKitClassListPanel = new JPanel(new BorderLayout());
		this.owlEditorKitClassListPanel.setPreferredSize(new Dimension(300, 100));
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode();
		
		OWLOntology onto =this.owlEditorKit.getModelManager().getActiveOntology();
		
		OWLClass owlClass = onto.getOWLOntologyManager().getOWLDataFactory().getOWLThing();
		top =new DefaultMutableTreeNode(owlClass);
		this.addOwlClassTree(top, owlClass, (new StructuralReasonerFactory()).createReasoner(onto));
		
        
		this.owlClassTree = new JTree(top);
        this.owlClassTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        //Отображение элементов в дереве.
        this.owlClassTree.setCellRenderer(new TreeCellRenderer()
		{
            private JLabel label = new JLabel();
     
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Object o = ((DefaultMutableTreeNode) value).getUserObject();
                if (o instanceof OWLClass) {
                	OWLClass owlClass = (OWLClass) o;
                	
                	OWLIconProviderImpl iconProvider = new OWLIconProviderImpl(owlEditorKit.getModelManager());
            		Icon icon = iconProvider.getIcon(owlClass);
            		
                    label.setIcon(icon);
                    label.setText(owlEditorKit.getOWLModelManager().getRendering(owlClass));
                    
                } 
                else 
                {
                    label.setIcon(null);
                    label.setText("" + value);
                }
                
                //Font f = label.getFont();
                if(selected)
                {
                	label.setForeground(Color.BLUE);
                	//label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
                }
                else
                {
                	label.setForeground(Color.BLACK);
                	//label.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
                }
                
                return label;
            }
		});
        
        //При нажатии на элемеет (OWLClass из дерева) оповещаем слушателей.
		this.owlClassTree.addTreeSelectionListener(new TreeSelectionListener()
		{
			@Override
			public void valueChanged(TreeSelectionEvent arg0)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) owlClassTree.getLastSelectedPathComponent();

				if (node == null)
					return;

				ObservableOntologyClassListView.this.notifyOWLClassObserverOntClassList((OWLClass)node.getUserObject());
			}
		});
		
		this.owlClassTree.expandRow(0);
		
		final JScrollPane northScroll = new JScrollPane(this.owlClassTree);		
		
		OWLOntologyManager manager = this.owlEditorKit.getModelManager().getOWLOntologyManager();
		OWLOntology ontology = this.owlEditorKit.getModelManager().getActiveOntology();
		OWLReasoner reasoner = this.owlEditorKit.getModelManager().getReasoner();
		
		//Создаем оболочку с визуализацией для OWLModelManager
		this.owlModelManagerDecorator = new OWLModelManagerDecorator(manager, ontology, reasoner);
		
		this.owlEditorKitClassListPanel.add(northScroll,BorderLayout.CENTER);
		
		this.addTab(Language.LABEL_PROTEGE_OWLCLASS_LIST, this.owlEditorKitClassListPanel);
	}
	
	/**Инициализация списка CFrame с виде дерева.*/
	private void initCFrameTreeOnt()
	{
		this.cfOntClassListPanel = new JPanel();
		this.cfOntClassListPanel.removeAll();
		this.cfOntClassListPanel.setLayout(new BorderLayout());
		this.cfOntClassListPanel.setPreferredSize(new Dimension(300, 100));

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Thing");
		
		this.cfOntClassTree = new JTree(top);
		this.cfOntClassTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		ArrayList<CFrame> cFrameList = cfOnt.getCframes();

		Collections.sort(cFrameList, new Comparator<CFrame>()
		{

			@Override
			public int compare(CFrame o1, CFrame o2)
			{
				String label1 = cfOnt.getLabel(o1.getTrgConcept().getIRI(), Language.UPO_LANG);
				if(label1.equals("_EMPTY_"))
				{
					label1=o1.getTrgConcept().getIRI().getFragment();
				}
				
				String label2 = cfOnt.getLabel(o2.getTrgConcept().getIRI(), Language.UPO_LANG);
				if(label2.equals("_EMPTY_"))
				{
					label2=o2.getTrgConcept().getIRI().getFragment();
				}
				
				return label1.compareTo(label2);
			}

		});
		
		for (CFrame cframe : cFrameList)
		{
			CFrameDecorator cFrameViewImpl = new CFrameDecorator(cfOnt, cframe);
			boolean hasTreeNode = false;

			for(int chilID = 0; chilID<top.getChildCount(); chilID++)
			{
				DefaultMutableTreeNode childNodeFrame = ((DefaultMutableTreeNode)top.getChildAt(chilID));
				
				if(childNodeFrame.toString().equals(cFrameViewImpl.getTargetName()))
				{
					DefaultMutableTreeNode childNodeTargetFrame = new DefaultMutableTreeNode(cFrameViewImpl.getTypeCFrame());
					childNodeTargetFrame.setUserObject(cFrameViewImpl);
					
					for(VisualMethodVisitorInt vis : cFrameViewImpl.getVisualMethodList())
					{
						DefaultMutableTreeNode visMethodTargetFrame = new DefaultMutableTreeNode(vis.getNameVisualMethod());
						visMethodTargetFrame.setUserObject(vis);
						
						childNodeTargetFrame.add(visMethodTargetFrame);
					}
					
					childNodeFrame.add(childNodeTargetFrame);
					
					hasTreeNode = true;
					break;
				}
			}
			
			if(!hasTreeNode)
			{
				DefaultMutableTreeNode childNodeFrame = new DefaultMutableTreeNode(cFrameViewImpl.getTargetName());

				DefaultMutableTreeNode childNodeTargetFrame = new DefaultMutableTreeNode(cFrameViewImpl.getTypeCFrame());
				childNodeTargetFrame.setUserObject(cFrameViewImpl);
				
				for(VisualMethodVisitorInt vis : cFrameViewImpl.getVisualMethodList())
				{
					DefaultMutableTreeNode visMethodTargetFrame = new DefaultMutableTreeNode(vis.getNameVisualMethod());
					visMethodTargetFrame.setUserObject(vis);
					
					childNodeTargetFrame.add(visMethodTargetFrame);
				}
				
				childNodeFrame.add(childNodeTargetFrame);
				
				top.add(childNodeFrame);
			}
			
		}
		
		this.cfOntClassTree.expandRow(0);
		
        //Отображение элементов в дереве.
        this.cfOntClassTree.setCellRenderer(new TreeCellRenderer()
		{
            private JLabel label = new JLabel();
     
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Object o = ((DefaultMutableTreeNode) value).getUserObject();
                if (o instanceof CFrameDecorator) 
                {
                	CFrameDecorator cFrameDecorator = (CFrameDecorator) o;

                    label.setText(cFrameDecorator.getTypeCFrame());
                } 
                else 
                {
                    label.setIcon(null);
                    label.setText("" + value);
                }
                
                if(selected)
                {
                	label.setForeground(Color.BLUE);
                }
                else
                {
                	label.setForeground(Color.BLACK);
                }
                
                return label;
            }
		});
        
        //При нажатии на элемеет (OWLClass из дерева) оповещаем слушателей.
		this.cfOntClassTree.addTreeSelectionListener(new TreeSelectionListener()
		{
			@Override
			public void valueChanged(TreeSelectionEvent arg0)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) cfOntClassTree.getLastSelectedPathComponent();

				if (node == null)
					return;

                if (node.getUserObject() instanceof CFrameDecorator) 
                {                	
    				ObservableOntologyClassListView.this.notifyCFrameObserverOntClassList((CFrameDecorator)node.getUserObject());
                }
                else
                {
					if (node.getUserObject() instanceof VisualMethodVisitorInt) 
	                {                
						DefaultMutableTreeNode nodePar = (DefaultMutableTreeNode) node.getParent();
						
						ObservableOntologyClassListView.this.notifyVisualMethodObserverOntClassList((CFrameDecoratorInt)nodePar.getUserObject(),(VisualMethodVisitorInt)node.getUserObject());
	                }
                }
			}
		});
		
		
		JScrollPane northScroll = new JScrollPane(this.cfOntClassTree);
		
		this.cfOntClassListPanel.add(northScroll,BorderLayout.CENTER);
		
		this.cfOntClassListPanel.updateUI();
		
		this.addTab(Language.LABEL_COGNITIVE_FRAME_CLASS, this.cfOntClassListPanel);
	}
	
	public void update()
	{
		this.initCFrameTreeOnt();
	}


	@Override
	public void registerObserverOntClassList(ObserverOntClassListInt o)
	{		
		this.observerList.add(o);
	}


	@Override
	public void removeObserverOntClassList(ObserverOntClassListInt o)
	{
		this.observerList.remove(o);
	}


	@Override
	public void notifyCFrameObserverOntClassList(CFrameDecoratorInt cFrameDecorator)
	{
		cFrameDecorator.builderViewer();

		for (ObserverOntClassListInt o : this.observerList)
		{
			o.update(cFrameDecorator);
		}

	}

	@Override
	public void notifyOWLClassObserverOntClassList(OWLClass owlClass)
	{
		this.owlModelManagerDecorator.builderViewer();
		
		for (ObserverOntClassListInt o : this.observerList)
		{
			o.update(owlClass,this.owlModelManagerDecorator);
		}
	}

	@Override
    public void notifyVisualMethodObserverOntClassList(CFrameDecoratorInt cFrameDecorator, VisualMethodVisitorInt visualMethodBuilderInt)
    {
		cFrameDecorator.builderViewer();
		
		for (ObserverOntClassListInt o : this.observerList)
		{
			o.update(visualMethodBuilderInt);
		}
    }
}
