package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.filechooser.FileFilter;

import ru.iimm.ontology.visualization.lang.Language;
import ru.iimm.ontology.visualization.ui.mvp.impl.models.ModelCFrameOntologyImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.models.ModelMultiOntologyImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.models.ModelOwlOntologyImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewSettingFrameImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewTreeNodeWithVis;
import ru.iimm.ontology.visualization.ui.mvp.models.ModelMultiOntology;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterMainFrame;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewMainFrame;

/**
 * Реализация интерфейса {@linkplain PresenterMainFrame}
 * @author Danilov
 * @version 0.2
 */
public class PresenterMainFrameImpl implements PresenterMainFrame
{
	private ViewMainFrame view;
	private ModelMultiOntology model;
	
	/**
	 * {@linkplain PresenterMainFrameImpl}
	 */
	public PresenterMainFrameImpl()
	{

	}
	
	@Override
	public void loadOntologyFromFileChooser()
	{		
		final JFileChooser ontologyFile = new JFileChooser();
		
		FileFilter allFile = ontologyFile.getChoosableFileFilters()[0];
		ontologyFile.removeChoosableFileFilter(allFile);
		ontologyFile.addChoosableFileFilter(new OntologyFilter(Language.LABEL_FILE_OWL_ONTOLOGY));
		ontologyFile.addChoosableFileFilter(new OntologyFilter(Language.LABEL_FILE_UPO));
		ontologyFile.addChoosableFileFilter(new OntologyFilter(Language.LABEL_FILE_CF_ONTOLOGY));
		ontologyFile.addChoosableFileFilter(allFile);
		
		int butIdChooser = ontologyFile.showDialog(this.view.getFrame(), Language.TITLE_LOAD_ONTOLOGY_DIALOG);
		
		if(butIdChooser == JFileChooser.APPROVE_OPTION)
		{
			final File ontFile = ontologyFile.getSelectedFile();
			
			if(ontFile == null)
				return;
			
			this.view.setEnableMenuItemOntology(false);
			
			this.view.setTitle(Language.TITLE_MAIN_FRAME + " - " + Language.LABEL_LOADING);

			Thread ontLoad = new Thread(new Runnable() 
			{
				public void run() 
				{										
					
					ModelMultiOntology multiModelOntology = null;
					//В зависимости от типа онтологии, будет производиться определенная конвертация/загрузка
					if(ontologyFile.getFileFilter().getDescription().equals(Language.LABEL_FILE_UPO))
					{
						multiModelOntology = new ModelMultiOntologyImpl(ontFile.getAbsolutePath().substring(0, ontFile.getAbsolutePath().length() - ontFile.getName().length()), ontFile.getName(), 
								false, true, true);
					}
					else if(ontologyFile.getFileFilter().getDescription().equals(Language.LABEL_FILE_CF_ONTOLOGY))
					{
						multiModelOntology = new ModelMultiOntologyImpl(ontFile.getAbsolutePath().substring(0, ontFile.getAbsolutePath().length() - ontFile.getName().length()), ontFile.getName(), 
								false, false, true);
					}
					else
					{
						multiModelOntology = new ModelMultiOntologyImpl(ontFile.getAbsolutePath().substring(0, ontFile.getAbsolutePath().length() - ontFile.getName().length()), ontFile.getName(), 
								true, true, true);
					}
					
					setModel(multiModelOntology);
					
					initVisualization();
					
					view.setTitle(Language.TITLE_MAIN_FRAME + " - (" + multiModelOntology.getPathDirOntology() + multiModelOntology.getFileActivOntology() + ")");
				}
			});
			
			ontLoad.start();			
		}
	}

	@Override
	public void show()
	{
		this.view.getFrame().setVisible(true);
	}

	@Override
	public void setView(ViewMainFrame view)
	{
		this.view = view;
	}

	@Override
	public ViewMainFrame getView()
	{
		return this.view;
	}

	@Override
	public void setModel(ModelMultiOntology model)
	{
		this.model = model;
	}

	@Override
	public ModelMultiOntology getModel()
	{
		return this.model;
	}

	@Override
	public void showSettingFrame()
	{
		PresenterSettingFrameImpl presSetting = new PresenterSettingFrameImpl();
		ViewSettingFrameImpl viewSetting = new ViewSettingFrameImpl(this.view.getFrame());
		presSetting.setView(viewSetting);
		presSetting.setModel(this.model);
		
		viewSetting.setPresenter(presSetting);
		
		presSetting.show();
	}

	@Override
	public void initVisualization()
	{
		if(model==null)
			return;

		JMenu visItem = this.view.getMenuItemVisualization();
		
		JMenu visCFrame = new JMenu(Language.COGNITIVE_FRAME);
		JMenu visOWLClass = new JMenu(Language.LABEL_FILE_OWL_ONTOLOGY);
		
		final JCheckBoxMenuItem visCajunCFrame = new JCheckBoxMenuItem("Cajun");		
		final JCheckBoxMenuItem visPrefuseCFrame = new JCheckBoxMenuItem("Prefuse");	
		final JCheckBoxMenuItem visGraphStreamCFrame = new JCheckBoxMenuItem("GraphStream");
		
		final JCheckBoxMenuItem visPrefuseOWLClass = new JCheckBoxMenuItem("Prefuse");
		
		visCFrame.add(visGraphStreamCFrame);
		visCFrame.add(visPrefuseCFrame);
		visCFrame.add(visCajunCFrame);
		
		visOWLClass.add(visPrefuseOWLClass);
		
		visItem.add(visCFrame,0);
		visItem.add(visOWLClass,1);
		
		//Presenter который перенаправляет event из TreeNode в  визуализации
		final RedirectingPresenterCFrameTreeNode redPresenter = new RedirectingPresenterCFrameTreeNode();
		redPresenter.setModel(new ModelCFrameOntologyImpl(this.model.getCongitiveFrameOntology()));
		
		//Дерево с иерархией OWLClass'ов
		final PresenterOwlClassPrefuseImpl presenterOwlPref = new PresenterOwlClassPrefuseImpl();		
		presenterOwlPref.setModel(new ModelOwlOntologyImpl(model.getOWLOnt().mng ,model.getOWLOnt().ontInMem, model.getOWLOnt().reas));

		final RedirectingPresenterOwlClassTreeNode redPresOnt = new RedirectingPresenterOwlClassTreeNode(presenterOwlPref);
		redPresOnt.setModel(presenterOwlPref.getModel());
	
		//Presenter и view визуализаций
		
		
		//Все добавляем в контейнер
		final ViewTreeNodeWithVis multiView = new ViewTreeNodeWithVis();
		multiView.addTreeNode(redPresenter.getView().getViewComponent(), "Tree CFrames");
		multiView.addTreeNode(redPresOnt.getView().getViewComponent(), "Ontology tree");		
		
		visCajunCFrame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PresenterCFrameCajunVisitorImpl presCajun = new PresenterCFrameCajunVisitorImpl();				
				presCajun.setModel(redPresenter.getModel());
		
				if(visCajunCFrame.isSelected())
				{
					redPresenter.addVisualMethod(presCajun);
					
					multiView.addVisualization(presCajun.getView(), presCajun.getNameVisualMethod());
				}
				else
				{
					redPresenter.removeVisualMethod(presCajun);
					multiView.removeVisualization(presCajun.getView(), presCajun.getNameVisualMethod());
				}
			}
		});
		
		visPrefuseCFrame.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PresenterCFramePrefuseVisitorImpl presPref = new PresenterCFramePrefuseVisitorImpl();
				presPref.setModel(redPresenter.getModel());
				
				if(visPrefuseCFrame.isSelected())
				{
					redPresenter.addVisualMethod(presPref);
					
					multiView.addVisualization(presPref.getView(), presPref.getNameVisualMethod());
				}
				else
				{
					redPresenter.removeVisualMethod(presPref);
					multiView.removeVisualization(presPref.getView(),presPref.getNameVisualMethod());
				}
			}
		});
		
		visGraphStreamCFrame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{				
				PresenterCFrameGSVisitorImpl presGS = new PresenterCFrameGSVisitorImpl();
				presGS.setModel(redPresenter.getModel());

				if(visGraphStreamCFrame.isSelected())
				{
					
					redPresenter.addVisualMethod(presGS);

					multiView.addVisualization(presGS.getView(), presGS.getNameVisualMethod());
				}
				else
				{
					redPresenter.removeVisualMethod(presGS);
					multiView.removeVisualization(presGS.getView(), presGS.getNameVisualMethod());
				}
			}
		});
		
		visPrefuseOWLClass.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(visPrefuseOWLClass.isSelected())
				{					
					multiView.addVisualization(presenterOwlPref.getView(), "Prefuse - Ontology");
				}
				else
				{
					multiView.removeVisualization(presenterOwlPref.getView(), "Prefuse - Ontology");
				}
			}
		});
		
		view.setContentPane(multiView.getViewComponent());
	}

	@Override
	public void closeApplication()
	{
		System.exit(0);
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
