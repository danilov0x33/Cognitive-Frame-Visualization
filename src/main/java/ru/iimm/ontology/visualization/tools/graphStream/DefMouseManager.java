package ru.iimm.ontology.visualization.tools.graphStream;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.graphicGraph.GraphicSprite;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.util.MouseManager;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * Обработчик событий для мыши.
 * @author Danilov E.Y.
 *
 */
public class DefMouseManager implements MouseManager 
{
	
	private final String statusText = "STATUS:";
	
	protected View view;
	
	private JPanel statusPanel;
	
	private JLabel statusLabel;

	protected GraphicGraph graph;

	private JPanel informationCFramePanel;

	public DefMouseManager(JPanel informationCFramePanel)
	{
		this.informationCFramePanel = informationCFramePanel;
	}
	
	public void init(GraphicGraph graph, View view) {
		this.view = view;
		this.graph = graph;
		this.view.setLayout(new BorderLayout());
		this.statusPanel = new JPanel();
		this.statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		this.statusPanel.setPreferredSize(new Dimension(0,20));
		
		statusLabel = new JLabel(statusText);
		
		
		this.statusPanel.add(statusLabel);
		
		view.add(statusPanel,BorderLayout.SOUTH);
		
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
	}
	
	public void release() {
		view.removeMouseListener(this);
		view.removeMouseMotionListener(this);
	}

	// Command

	protected void mouseButtonPress(MouseEvent event) {
		view.requestFocus();

		// Unselect all.
		
		informationCFramePanel.removeAll();
		informationCFramePanel.updateUI();
		
		if (!event.isShiftDown()) {
			for (Node node : graph) {
				if (node.hasAttribute("ui.selected"))
					node.removeAttribute("ui.selected");
				
			}

			for (GraphicSprite sprite : graph.spriteSet()) {
				if (sprite.hasAttribute("ui.selected"))
					sprite.removeAttribute("ui.selected");
			}
		}
	}

	protected void mouseButtonRelease(MouseEvent event,
			ArrayList<GraphicElement> elementsInArea) {
		for (GraphicElement element : elementsInArea) {
			if (!element.hasAttribute("ui.selected"))
				element.addAttribute("ui.selected");
		}
	}
	
	
	protected void mouseButtonPressOnElement(GraphicElement element,
			MouseEvent event) {
		view.freezeElement(element, true);
		if (event.getButton() == 3) {
			element.addAttribute("ui.selected");
		} else 
		{
			
			element.addAttribute("ui.clicked");
			
			informationCFramePanel.removeAll();
			informationCFramePanel.updateUI();
			
			Font fontLabel = new Font("Century Gothic", Font.BOLD, 14);
			JLabel labelNode = new JLabel("Label: ");
			labelNode.setFont(fontLabel);
			
			JLabel sourceLabelNode = new JLabel("<html>"+element.label+"</html>");
			
			JLabel iriLabel = new JLabel("IRI: ");
			String label = ((OWLNamedIndividual)element.getAttribute("OWLNamedIndividual")).getIRI().toString();
			iriLabel.setFont(fontLabel);
			
			JLabel sourceIRI = new JLabel("<html>"+iriLabel.getText()+label+"</html>");
			
			informationCFramePanel.add(labelNode);
			informationCFramePanel.add(sourceLabelNode);
			
			informationCFramePanel.add(iriLabel);
			informationCFramePanel.add(sourceIRI);
			
			statusLabel.setText(statusText+" X:"+element.getX()+" Y: "+element.getY());
		}
	}

	protected void elementMoving(GraphicElement element, MouseEvent event) 
	{
		view.moveElementAtPx(element, event.getX(), event.getY());		
		
		
	}

	protected void mouseButtonReleaseOffElement(GraphicElement element,
			MouseEvent event) {
		view.freezeElement(element, false);
		if (event.getButton() != 3) {
			element.removeAttribute("ui.clicked");
		} 
		else 
		{	
		}
	}

	// Mouse Listener

	protected GraphicElement curElement;

	protected float x1, y1;

	public void mouseClicked(MouseEvent event) 
	{
		
	}

	public void mousePressed(MouseEvent event) {
		curElement = view.findNodeOrSpriteAt(event.getX(), event.getY());

		if (curElement != null) {
			mouseButtonPressOnElement(curElement, event);
		} else {
			x1 = event.getX();
			y1 = event.getY();
			mouseButtonPress(event);
			view.beginSelectionAt(x1, y1);
		}
		
		/*double delta = view.getCamera().getGraphDimension() * 0.01f;

		Point3 p = view.getCamera().getViewCenter();
		
		view.getCamera().setViewCenter(p.x, p.y + delta, 0);*/
		
		
	}

	public void mouseDragged(MouseEvent event) {
		if (curElement != null) {
			elementMoving(curElement, event);
		} else {
			view.selectionGrowsAt(event.getX(), event.getY());
		}
	}

	public void mouseReleased(MouseEvent event) {
		if (curElement != null) {
			mouseButtonReleaseOffElement(curElement, event);
			curElement = null;
		} else {
			float x2 = event.getX();
			float y2 = event.getY();
			float t;

			if (x1 > x2) {
				t = x1;
				x1 = x2;
				x2 = t;
			}
			if (y1 > y2) {
				t = y1;
				y1 = y2;
				y2 = t;
			}

			mouseButtonRelease(event, view.allNodesOrSpritesIn(x1, y1, x2, y2));
			view.endSelectionAt(x2, y2);
		}
	}

	public void mouseEntered(MouseEvent event) {
		// NOP
	}

	public void mouseExited(MouseEvent event) {
		// NOP
	}

	public void mouseMoved(MouseEvent e) 
	{
		
	}
}
