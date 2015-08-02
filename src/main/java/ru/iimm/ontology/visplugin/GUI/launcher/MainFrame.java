package ru.iimm.ontology.visplugin.GUI.launcher;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.visplugin.GUI.ContentView;
import ru.iimm.ontology.visplugin.GUI.GUIElementInt;
import ru.iimm.ontology.visplugin.lang.Language;
import ru.iimm.ontology.visplugin.tools.OntologyManager;

/**
 * Главное окно приложения.
 * @author Danilov E.Y.
 *
 */
public class MainFrame extends JFrame implements GUIElementInt
{
    private static final long serialVersionUID = 5058632394014445889L;
    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);
	/**Главная панель окна*/
    private JPanel contentPanel;
    
    /**
     * {@linkplain MainFrame}
     */
    public MainFrame()
    {
    	this.init();
    }
    
    private void init()
    {
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setTitle(Language.TITLE_MAIN_FRAME);
    	this.setPreferredSize(new Dimension(800, 600));
    	//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	
    	//=====Главная панель окна=======
    	this.contentPanel = new JPanel(new BorderLayout());
    	
    	//=====Меню бар=====
    	JMenuBar menuBar = new JMenuBar();
    	JMenu menuFile = new JMenu(Language.MENU_FILE);
    	JMenuItem menuItemExit = new JMenuItem(Language.MENU_ITEM_EXIT);
    	final JMenuItem menuItemLoadOntology = new JMenuItem(Language.MENU_ITEM_LOAD_ONT);
    	JMenuItem menuItemSetting = new JMenuItem(Language.MENU_ITEM_SETTING);
    	
    	menuItemLoadOntology.addActionListener(new ActionListener() 
    	{	
			public void actionPerformed(ActionEvent e) 
			{
				final JFileChooser ontologyFile = new JFileChooser();
				
				FileFilter allFile = ontologyFile.getChoosableFileFilters()[0];
				ontologyFile.removeChoosableFileFilter(allFile);
				ontologyFile.addChoosableFileFilter(new OntologyFilter(Language.LABEL_FILE_OWL_ONTOLOGY));
				ontologyFile.addChoosableFileFilter(new OntologyFilter(Language.LABEL_FILE_UPO));
				ontologyFile.addChoosableFileFilter(new OntologyFilter(Language.LABEL_FILE_CF_ONTOLOGY));
				ontologyFile.addChoosableFileFilter(allFile);
				
				int butIdChooser = ontologyFile.showDialog(MainFrame.this, Language.TITLE_LOAD_ONTOLOGY_DIALOG);
				
				if(butIdChooser == JFileChooser.APPROVE_OPTION)
				{
					final File ontFile = ontologyFile.getSelectedFile();
					
					if(ontFile == null)
						return;
					
					
					new Thread(new Runnable() 
					{
						public void run() 
						{
							menuItemLoadOntology.setEnabled(false);
							
							MainFrame.this.setTitle(Language.TITLE_MAIN_FRAME + " - " + Language.LABEL_LOADING);
							
							OntologyManager ontologyManager = null;
							
							//В зависимости от типа онтологии, будет производиться определенная конвертация/загрузка
							if(ontologyFile.getFileFilter().getDescription().equals(Language.LABEL_FILE_UPO))
							{
								ontologyManager = new OntologyManager(ontFile.getAbsolutePath().substring(0, ontFile.getAbsolutePath().length() - ontFile.getName().length()), ontFile.getName(), 
										false, true, true);
							}
							else if(ontologyFile.getFileFilter().getDescription().equals(Language.LABEL_FILE_CF_ONTOLOGY))
							{
								ontologyManager = new OntologyManager(ontFile.getAbsolutePath().substring(0, ontFile.getAbsolutePath().length() - ontFile.getName().length()), ontFile.getName(), 
										false, false, true);
							}
							else
							{
								ontologyManager = new OntologyManager(ontFile.getAbsolutePath().substring(0, ontFile.getAbsolutePath().length() - ontFile.getName().length()), ontFile.getName(), 
										true, true, true);
							}
							
							ContentView content = new ContentView(ontologyManager.getCongitiveFrameOntology(), ontologyManager.getOWLOnt());
							
							MainFrame.this.setContentPane(content.getContentView());
							
							MainFrame.this.setTitle(Language.TITLE_MAIN_FRAME + " - (" + ontologyManager.getPathDirOntology() + ontologyManager.getFileActivOntology() + ")");
							
						}
					}).start();
				}
			}
		});
    	
    	menuItemSetting.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SettingFrame frame = new SettingFrame(MainFrame.this);
				frame.setVisible(true);
				MainFrame.this.update();
			}
		});
    	
    	menuFile.add(menuItemLoadOntology);
    	menuFile.add(new JSeparator());
    	menuFile.add(menuItemSetting);
    	menuFile.add(new JSeparator());
    	menuFile.add(menuItemExit);
    	
    	menuBar.add(menuFile);
    	
    	
    	this.setJMenuBar(menuBar);
    	this.getContentPane().add(this.contentPanel, BorderLayout.CENTER);
    	this.pack();
    	this.setLocationRelativeTo(null);
    }
    
    public void setContentPane(Container contentPane) 
    {
    	this.contentPanel.add(contentPane, BorderLayout.CENTER);
    	this.contentPanel.updateUI();
    }

    public void update()
    {
		this.init();
    }
}

/**
 * Фильтр онтологий для файлов. 
 * @author Danilov E.Y.
 */
class OntologyFilter extends FileFilter
{
	/**Название с онтологиями.*/
	private String ontologyName;
	/**
	 * {@linkplain OntologyFilter}
	 * @param ontologyName - {@linkplain #ontologyName}
	 */
	public OntologyFilter(String ontologyName) 
	{
		this.ontologyName = ontologyName;
	}
	
	public boolean accept(File arg0) 
	{
		//Если это файл...
		if(arg0.isFile())
		{
			int dotPos = arg0.getName().lastIndexOf(".");
			
			if(dotPos == -1)
				return false;
			//Находим расширение файла
			String ext = arg0.getName().substring(dotPos);
			
			if(ext.equals(".owl") || ext.equals(".rdf") || ext.equals(".xml"))
				return true;
			else
				return false;
		}
		else
			return true;
	}

	public String getDescription() 
	{
		return this.ontologyName;
	}
	
}
