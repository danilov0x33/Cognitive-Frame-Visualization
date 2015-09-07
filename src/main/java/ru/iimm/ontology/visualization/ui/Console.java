package ru.iimm.ontology.visualization.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Вывод из System.out.
 * @author Danilov
 * @version 0.1
 */
public class Console extends OutputStream
{
	
	protected static final PrintStream DEFAULT_OUT = System.out;
	
	/**
	 * {@linkplain Console}
	 */
	public Console()
	{		
		PrintStream printStream = new PrintStream(this);
		 
		System.setOut(printStream);
		System.setErr(printStream);		 
	}
	
	@Override
	public void write(int b) throws IOException
	{
		DEFAULT_OUT.write(b);
	}
}
