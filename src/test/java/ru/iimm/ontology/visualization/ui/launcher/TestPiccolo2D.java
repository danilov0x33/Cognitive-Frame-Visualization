package ru.iimm.ontology.visualization.ui.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.piccolo2d.PNode;
import org.piccolo2d.extras.pswing.PSwing;
import org.piccolo2d.extras.pswing.PSwingCanvas;
import org.piccolo2d.extras.swing.SwingLayoutNode;
import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

/**
 *
 * @author Danilov
 * @version 0.1
 */
public class TestPiccolo2D extends JFrame 
{
	/**
	 * {@linkplain }
	 */
	public TestPiccolo2D()
	{
		PSwingCanvas canvas;

        // Set up basic frame
        setBounds(50, 50, 750, 750);
        setResizable(true);
        setBackground(null);
        setVisible(true);
        canvas = new PSwingCanvas();
        canvas.setPanEventHandler(null);
        getContentPane().add(canvas);
        validate();
        
        SwingLayoutNode flowLayoutNode = new SwingLayoutNode(new FlowLayout());
        flowLayoutNode.addChild(new PText("1+1"));
        flowLayoutNode.addChild(new PText("2+2"));
        flowLayoutNode.setOffset(200, 200);
        canvas.getLayer().addChild(flowLayoutNode);
        
        //canvas.getLayer().addChild(transform);
        //canvas.getLayer().addChild(PPath.createEllipse(0, 0, 10, 10));
        
        
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new TestPiccolo2D();
	}

}
