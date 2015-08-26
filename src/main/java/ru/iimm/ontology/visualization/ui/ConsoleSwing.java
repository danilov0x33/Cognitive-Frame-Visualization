package ru.iimm.ontology.visualization.ui;

import java.awt.Component;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 * Вывод из консоли в JTextArea.
 * @author Danilov
 * @version 0.1
 */
public class ConsoleSwing extends Console
{
	private JTextArea textArea;
	private JScrollPane scrollPanel;
	
	public ConsoleSwing()
	{
		this.init();
	}
	
	private void init()
	{
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		
		this.scrollPanel = new JScrollPane(this.textArea);
	}
	
	@Override
	public void write(int b) throws IOException
	{
		super.write(b);
		
		if(textArea.getLineCount() == 10000)
		{
			int end = 0;
			try
			{
				end = textArea.getLineEndOffset(0);
			}
			catch(BadLocationException e)
			{
				e.printStackTrace();
			}
			textArea.replaceRange("", 0, end);
		}
		
        textArea.append(String.valueOf((char)b));
        
        textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	public Component getConsole()
	{
		return this.scrollPanel;
	}
	
}
