package ru.iimm.ontology.visualization.ui.mvp.impl.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.layout.HierarchicalLayout;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import ru.iimm.ontology.visualization.lang.Language;
import ru.iimm.ontology.visualization.tools.graphStream.DefMouseManager;
import ru.iimm.ontology.visualization.tools.graphStream.DefShortcutManager;
import ru.iimm.ontology.visualization.tools.graphStream.style.Style;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewVisGS;

/**
 * Реализация интерфейса View для визуализации CFrame с помощью Graph Stream.
 * @author Danilov
 * @version 0.1
 */
public abstract class BaseViewVisGS implements ViewVisGS
{	
	/**Граф со всеми элементами.*/
	private MultiGraph graph;
	/**Панель с контентом (GUI + визуализация)*/
	private JSplitPane contentPanel;
	/**Панель с настройками.*/
	private JPanel panelSetting;
	/**Панель, на которой размещяется контент-панель.*/
	private JPanel mainPanel;
	/**Окно с визуальными настройками.*/
	private JFrame settingVisFrame;
	/**Окно с настройками онтологии.*/
	private JFrame settingOntologyFrame;
	/**Кнопка, по которой открывается фрейм с визуальными настройками.*/
	private JButton visSettingBut;
	/**Кнопка, по которой открывается фрейм с настройками онтологии.*/
	private JButton ontoSettingBut;
	/**Панель для отображения информации по элементам.*/
	private JPanel informationCFramePanel;
	private Viewer viewer;
	private View view;
	
	/**
	 * {@linkplain BaseViewVisGS}
	 */
	public BaseViewVisGS()
	{
		this.init();
	}
	
	private void init()
	{
		this.mainPanel = new JPanel(new BorderLayout()); 
		
		this.panelSetting =new JPanel();
		this.panelSetting.setLayout(new GridLayout(1, 2));
		this.panelSetting.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.panelSetting.setBackground(Color.WHITE);
		
		informationCFramePanel = new JPanel();
		informationCFramePanel.setBackground(Color.white);
		informationCFramePanel.setLayout(new BoxLayout(informationCFramePanel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollInformationCFramePanel = new JScrollPane(informationCFramePanel);
		
		this.contentPanel = new JSplitPane();
		this.contentPanel.setRightComponent(scrollInformationCFramePanel);
		this.contentPanel.setLeftComponent(new JPanel());
		this.contentPanel.setPreferredSize(new Dimension(250,200));
		this.contentPanel.setResizeWeight (1);
		this.contentPanel.setDividerLocation (700);
		
		this.settingVisFrame = this.getSettingVisFrame();
		this.settingOntologyFrame = this.getSettingOntologyFrame();
		
		this.visSettingBut =new JButton(Language.BUTTON_SETTING_VIS);
		this.visSettingBut.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{	
						try
						{
							settingVisFrame.setVisible(true);
						} 
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		this.ontoSettingBut = new JButton(Language.BUTTON_SETTING_ONTOLOGY);	
		this.ontoSettingBut.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						try
						{
							settingOntologyFrame.setVisible(true);
						} 
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					
				});
			}
		});
		
		this.panelSetting.add(this.visSettingBut);
		this.panelSetting.add(this.ontoSettingBut);
		
		this.mainPanel.add(this.panelSetting,BorderLayout.NORTH);
		this.mainPanel.add(this.contentPanel,BorderLayout.CENTER);
	}
	
	/**
	 * Панель со списком IRI для визуального фильтра.
	 * @return  - панель со списком IRI.
	 */
	private JPanel getGroupBoxFiltreIRI()
	{
		JPanel groupBoxFiltreIRI = new JPanel();
		groupBoxFiltreIRI.setBorder(BorderFactory.createTitledBorder(Language.LABEL_FILTER_IRI));
		groupBoxFiltreIRI.setLayout(new BoxLayout(groupBoxFiltreIRI, BoxLayout.Y_AXIS));
		
		//Добавляем IRI в список фильтра IRI
		/*for(String iri : this.gsBuilder.getBaseIRIList())
		{
			JCheckBox iriCBox = new JCheckBox(iri);
			iriCBox.setSelected(true);
			iriCBox.addItemListener(new ItemListener()
			{
				
				@Override
				public void itemStateChanged(ItemEvent arg0)
				{
					JCheckBox cBox = (JCheckBox)arg0.getSource();
					for(GraphNode node : graph.getNodeList())
					{
						//Находим все элементы, содержащие объекты c одинаковыми baseIRI
						if(node.getOWLNamedIndividual().getIRI().getStart().equals(cBox.getText()))
						{
							//Если выбран baseIRI
							if(cBox.isSelected())
							{
								//то показываем элемент
								node.setVisible(true);
								//и его связи
								for(GraphEdge edge : graph.getEdgeList())
								{
									if(edge.getNode0().getId().equals(node.getId()) || edge.getNode1().getId().equals(node.getId()))
									{
										graph.getEdge(edge.getId()).removeAttribute("ui.hide");
									}
								}
								
							}
							else
							{
								node.setVisible(false);
								for(GraphEdge edge : graph.getEdgeList())
								{
									if(edge.getNode0().getId().equals(node.getId()) || edge.getNode1().getId().equals(node.getId()))
									{
										graph.getEdge(edge.getId()).addAttribute("ui.hide");
									}
								}
							}
						}
					}
				}
			});
			
			groupBoxF qiltreIRI.add(iriCBox);
		}*/
		
		return groupBoxFiltreIRI;
	}
	
	/**
	 * Окно с настройками для онтологии.
	 * @return - фрейм с настройками.ы
	 */
	private JFrame getSettingOntologyFrame()
	{
		JFrame settintFrame =new JFrame(Language.BUTTON_SETTING_ONTOLOGY);
		settintFrame.setVisible(false);
		settintFrame.setSize(new Dimension(300,200));
		settintFrame.setLocationRelativeTo(null);
		
		JPanel mainPanelOntologySettingFrame = new JPanel();
		mainPanelOntologySettingFrame.setBackground(Color.white);
		
		mainPanelOntologySettingFrame.add(this.getGroupBoxFiltreIRI());
		
		settintFrame.add(mainPanelOntologySettingFrame,BorderLayout.CENTER);
		settintFrame.pack();
		return settintFrame;
	}
	
	/**
	 * Окно с настройками для визуализации.
	 * @return - фрейм с настройками.
	 */
	private JFrame getSettingVisFrame()
	{
		JFrame settingVisFrame =new JFrame(Language.BUTTON_SETTING_VIS);
		settingVisFrame.setSize(new Dimension(100,200));
		settingVisFrame.setLocationRelativeTo(null);
		
		JPanel mainPanelSettingFrame = new JPanel();
		mainPanelSettingFrame.setLayout(new BoxLayout(mainPanelSettingFrame, BoxLayout.Y_AXIS));
		
		//=====Setting======
		final JCheckBox checkBoxGraph = new JCheckBox(Language.LABEL_HIGTH_QUALITY);
		checkBoxGraph.setSelected(true);
		
		checkBoxGraph.addItemListener(new ItemListener()
		{
			
			@Override
			public void itemStateChanged(ItemEvent arg0)
			{
				if(checkBoxGraph.isSelected())
				{
				    graph.addAttribute("ui.quality");
				    graph.addAttribute("ui.antialias");
				}
				else
				{
					graph.removeAttribute("ui.quality");
					graph.removeAttribute("ui.antialias");
				}
			}
		});
		
		mainPanelSettingFrame.add(checkBoxGraph);
		settingVisFrame.add(mainPanelSettingFrame,BorderLayout.CENTER);
		settingVisFrame.pack();
		return settingVisFrame;
	}

	@Override
	public void open()
	{
		System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		this.graph = new MultiGraph("GraphForCFrame",true,false);
		
		new Style(graph);

		this.viewer = new Viewer(graph,Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		
		this.viewer.enableAutoLayout(new HierarchicalLayout());

		this.view = this.viewer.addDefaultView(false);
		this.view.setLayout(new BorderLayout());

		this.view.setMouseManager(new DefMouseManager(this.informationCFramePanel));
		
		this.view.setShortcutManager(new DefShortcutManager());
		
		this.settingOntologyFrame = this.getSettingOntologyFrame();
		
		JPanel vPanel = new JPanel(new BorderLayout());
		vPanel.add(view,BorderLayout.CENTER);
		
		this.contentPanel.remove(this.contentPanel.getLeftComponent());
		this.contentPanel.setLeftComponent(vPanel);
		
		this.mainPanel.updateUI();
	}
	
	@Override
	public void close()
	{
		this.view.setEnabled(false);
		this.view.setMouseManager(null);
		
		this.viewer.close();
	}

	@Override
	public Component getViewComponent()
	{
		return this.mainPanel;
	}

	@Override
	public MultiGraph getGraph()
	{
		return this.graph;
	}

}
