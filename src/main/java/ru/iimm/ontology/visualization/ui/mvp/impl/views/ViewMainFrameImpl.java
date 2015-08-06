package ru.iimm.ontology.visualization.ui.mvp.impl.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.visualization.lang.Language;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterMainFrame;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewMainFrame;

/**
 * Главное окно приложения.
 * @author Danilov E.Y.
 *
 */
public class ViewMainFrameImpl implements ViewMainFrame
{
    private static final Logger log = LoggerFactory.getLogger(ViewMainFrameImpl.class);
    
    private PresenterMainFrame presenter;
	/**Главная панель окна*/
    private JPanel contentPanel;
    private JFrame mainFrame;
	private JMenuItem menuItemLoadOntology;
    
    /**
     * {@linkplain ViewMainFrameImpl}
     */
    public ViewMainFrameImpl()
    {
    	this.init();
    }
    
    private void init()
    {
    	this.mainFrame = new JFrame();
    	this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.mainFrame.setTitle(Language.TITLE_MAIN_FRAME);
    	this.mainFrame.setPreferredSize(new Dimension(800, 600));
    	//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	
    	//=====Главная панель окна========
    	this.contentPanel = new JPanel(new BorderLayout());
    	
    	//=====Меню бар=====
    	JMenuBar menuBar = new JMenuBar();
    	JMenu menuFile = new JMenu(Language.MENU_FILE);
    	JMenuItem menuItemExit = new JMenuItem(Language.MENU_ITEM_EXIT);
    	this.menuItemLoadOntology = new JMenuItem(Language.MENU_ITEM_LOAD_ONT);
    	JMenuItem menuItemSetting = new JMenuItem(Language.MENU_ITEM_SETTING);
    	
    	this.menuItemLoadOntology.addActionListener(new ActionListener() 
    	{	
			public void actionPerformed(ActionEvent e) 
			{
				presenter.loadOntologyFromFileChooser();
			}
		});
    	
    	menuItemSetting.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				presenter.showSettingFrame();
			}
		});
    	
    	menuFile.add(this.menuItemLoadOntology);
    	menuFile.add(new JSeparator());
    	menuFile.add(menuItemSetting);
    	menuFile.add(new JSeparator());
    	menuFile.add(menuItemExit);
    	
    	menuBar.add(menuFile);
    	
    	this.mainFrame.setJMenuBar(menuBar);
    	this.mainFrame.getContentPane().add(this.contentPanel, BorderLayout.CENTER);
    	this.mainFrame.pack();
    	this.mainFrame.setLocationRelativeTo(null);
    }
    
	@Override
	public void setPresenter(PresenterMainFrame presenter)
	{
		this.presenter = presenter;
	}

	@Override
	public PresenterMainFrame getPresenter()
	{
		return this.presenter;
	}

	@Override
	public JFrame getFrame()
	{
		return this.mainFrame;
	}

	@Override
	public void setEnableMenuItemOntology(boolean value)
	{
		this.menuItemLoadOntology.setEnabled(value);
	}

	@Override
	public void setTitle(String title)
	{
		this.mainFrame.setTitle(title);
	}

	@Override
	public void setContentPane(Component component)
	{
    	this.contentPanel.add(component, BorderLayout.CENTER);
    	this.contentPanel.updateUI();
	}
}
