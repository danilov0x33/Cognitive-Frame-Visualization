package ru.iimm.ontology.visualization.ui.mvp.impl.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ru.iimm.ontology.visualization.lang.Language;
import ru.iimm.ontology.visualization.lang.LanguageEN;
import ru.iimm.ontology.visualization.lang.LanguageRU;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterSettingFrame;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewSettingFrame;

/**
 * Окно с настройками.
 * @author Danilov E.Y.
 *
 */
public class ViewSettingFrameImpl implements ViewSettingFrame
{

    private JDialog jDialog;
    private PresenterSettingFrame presenter;
    
    /**
     * {@linkplain ViewSettingFrameImpl}
     */
    public ViewSettingFrameImpl(JFrame parentFrame)
    {
    	this.jDialog = new JDialog(parentFrame, Language.TITLE_SETTING_FRAME, true);
    	
    	this.init();
    }

    private void init()
    {
    		
    	this.jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	JButton okBut = new JButton(Language.BUTTON_OK);
    	JButton cancelBut = new JButton(Language.BUTTON_CANCEL);
    	
    	JLabel setLang = new JLabel(Language.LABEL_SELECT_LANGUAGE+":   ");
		
		String[] langList = { "English", "Russian"};
		
		final JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gridCon = new GridBagConstraints();
		final JComboBox<String> comboBox = new JComboBox<String>(langList);

		comboBox.setSelectedItem(Language.NAME_INIT_LANG);
    	
		JPanel cont = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cont.add(okBut);
		cont.add(cancelBut);
		
		gridCon.fill = GridBagConstraints.HORIZONTAL;
		gridCon.gridx = 0;
		gridCon.gridy = 0;
		mainPanel.add(setLang, gridCon);
		
		gridCon.gridx = 1;
		gridCon.gridy = 0;
		mainPanel.add(comboBox, gridCon);
		
		gridCon.gridx = 1;
		gridCon.gridy = 1;
		mainPanel.add(cont, gridCon);
		
		this.jDialog.add(mainPanel,BorderLayout.CENTER);
		
		this.jDialog.pack();
		
		this.jDialog.setLocationRelativeTo(null);
    	
    	okBut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Language lang;
				switch(comboBox.getSelectedIndex())
				{
					case 0 : 
					{
						lang = new LanguageEN();
						lang.initializeLang();
						break;
					}
					case 1 : 
					{
						lang = new LanguageRU();
						lang.initializeLang();
						break;
					}
				}
				
				jDialog.dispose();
			}
		});
    	cancelBut.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jDialog.dispose();
			}
		});
    }

	@Override
	public void setPresenter(PresenterSettingFrame presenter)
	{
		this.presenter = presenter;
	}

	@Override
	public PresenterSettingFrame getPresenter()
	{
		return this.presenter;
	}

	@Override
	public JDialog getFrame()
	{
		return this.jDialog;
	}
	
}
