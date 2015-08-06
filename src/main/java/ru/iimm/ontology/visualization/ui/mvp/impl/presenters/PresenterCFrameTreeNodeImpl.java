package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import ru.iimm.ontology.visualization.tools.CFrameDecorator;
import ru.iimm.ontology.visualization.ui.mvp.models.ModelCFrameOntology;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterCFrame;

/**
 * Реализация интерфейса Presenter для дерева CFram'ов.
 * @author Danilov
 * @version 0.1
 */
public class PresenterCFrameTreeNodeImpl extends BasePresenterTreeNode implements PresenterCFrame
{
	/**Модель*/
	private ModelCFrameOntology model;

	@Override
	public void selectedTreeNode(TreeSelectionEvent event)
	{
		
	}

	@Override
	public void updateTreeNodeFromModel()
	{
		ArrayList<CFrameDecorator> cFrameList = this.model.getCframes();

		Collections.sort(cFrameList, new Comparator<CFrameDecorator>()
		{

			@Override
			public int compare(CFrameDecorator o1, CFrameDecorator o2)
			{
				String label1 = model.getLabel(o1.getCframe());
				if(label1.equals("_EMPTY_"))
				{
					label1=o1.getTrgConcept().getIRI().getFragment();
				}
				
				String label2 = model.getLabel(o2.getCframe());
				if(label2.equals("_EMPTY_"))
				{
					label2=o2.getTrgConcept().getIRI().getFragment();
				}
				
				return label1.compareTo(label2);
			}

		});
		
		DefaultMutableTreeNode top = this.view.getTopNode();
		top.setUserObject("Thing");
		top.removeAllChildren();
		
		for (CFrameDecorator cFrameViewImpl : cFrameList)
		{
			boolean hasTreeNode = false;

			for(int chilID = 0; chilID<top.getChildCount(); chilID++)
			{
				DefaultMutableTreeNode childNodeFrame = ((DefaultMutableTreeNode)top.getChildAt(chilID));
				
				if(childNodeFrame.toString().equals(cFrameViewImpl.getTargetName()))
				{
					DefaultMutableTreeNode childNodeTargetFrame = new DefaultMutableTreeNode(cFrameViewImpl.getTypeCFrame());
					childNodeTargetFrame.setUserObject(cFrameViewImpl);
					
					/*for(VisualMethodVisitorInt vis : cFrameViewImpl.getVisualMethodList())
					{
						DefaultMutableTreeNode visMethodTargetFrame = new DefaultMutableTreeNode(vis.getNameVisualMethod());
						visMethodTargetFrame.setUserObject(vis);
						
						childNodeTargetFrame.add(visMethodTargetFrame);
					}*/
					
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
				
				/*for(VisualMethodVisitorInt vis : cFrameViewImpl.getVisualMethodList())
				{
					DefaultMutableTreeNode visMethodTargetFrame = new DefaultMutableTreeNode(vis.getNameVisualMethod());
					visMethodTargetFrame.setUserObject(vis);
					
					childNodeTargetFrame.add(visMethodTargetFrame);
				}*/
				
				childNodeFrame.add(childNodeTargetFrame);
				
				top.add(childNodeFrame);
			}
			
		}
		
		
	}

	@Override
	public void setModel(ModelCFrameOntology model)
	{
		this.model = model;
	}

	@Override
	public ModelCFrameOntology getModel()
	{
		return this.model;
	}

}
