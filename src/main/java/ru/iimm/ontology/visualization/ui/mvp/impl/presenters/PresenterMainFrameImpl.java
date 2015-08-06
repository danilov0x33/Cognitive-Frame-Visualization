package ru.iimm.ontology.visualization.ui.mvp.impl.presenters;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import ru.iimm.ontology.visualization.lang.Language;
import ru.iimm.ontology.visualization.ui.mvp.impl.models.ModelCFrameOntologyImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.models.ModelMultiOntologyImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.models.ModelOwlOntologyImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewCFrameVisCajunImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewCFrameVisGSImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewCFrameVisPrefuseImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewOWLClassPrefuseImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewSettingFrameImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewTreeNodeImpl;
import ru.iimm.ontology.visualization.ui.mvp.impl.views.ViewTreeNodeWithVis;
import ru.iimm.ontology.visualization.ui.mvp.models.ModelMultiOntology;
import ru.iimm.ontology.visualization.ui.mvp.presenters.PresenterMainFrame;
import ru.iimm.ontology.visualization.ui.mvp.views.ViewMainFrame;

/**
 * Реализация интерфейса {@linkplain PresenterMainFrame}
 * @author Danilov
 * @version 0.1
 */
public class PresenterMainFrameImpl implements PresenterMainFrame
{
	private ViewMainFrame view;
	private ModelMultiOntology model;
	
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
					
					//Presenter который перенаправляет event из TreeNode в  визуализации
					RedirectingPresenterCFrameTreeNode redPresenter = new RedirectingPresenterCFrameTreeNode();
					redPresenter.setModel(new ModelCFrameOntologyImpl(multiModelOntology.getCongitiveFrameOntology()));
					//Дерево с фреймами
					ViewTreeNodeImpl viewTreeNodeCFrame = new ViewTreeNodeImpl();
					redPresenter.setView(viewTreeNodeCFrame);
					viewTreeNodeCFrame.setPresenter(redPresenter);
					
					ViewTreeNodeImpl viewTreeNodeOwl = new ViewTreeNodeImpl();
					

					PresenterOwlClassPrefuseImpl presenterOwlPref = new PresenterOwlClassPrefuseImpl();				
					ViewOWLClassPrefuseImpl viewOwlPrefuse = new ViewOWLClassPrefuseImpl();
					presenterOwlPref.setModel(new ModelOwlOntologyImpl(multiModelOntology.getOWLOnt().mng ,multiModelOntology.getOWLOnt().ontInMem, multiModelOntology.getOWLOnt().reas));
					presenterOwlPref.setView(viewOwlPrefuse);
					viewOwlPrefuse.setPresenter(presenterOwlPref);
					
					RedirectingPresenterOwlClassTreeNode redPresOnt = new RedirectingPresenterOwlClassTreeNode(presenterOwlPref);
					redPresOnt.setModel(presenterOwlPref.getModel());
					redPresOnt.setView(viewTreeNodeOwl);
					viewTreeNodeOwl.setPresenter(redPresOnt);
					
					
					//Presenter и view визуализаций
					PresenterCFrameCajunVisitorImpl presCajun = new PresenterCFrameCajunVisitorImpl();
					PresenterCFrameGSVisitorImpl presGS = new PresenterCFrameGSVisitorImpl();
					PresenterCFramePrefuseVisitorImpl presPref = new PresenterCFramePrefuseVisitorImpl();
					ViewCFrameVisCajunImpl viewCajunCF = new ViewCFrameVisCajunImpl();
					ViewCFrameVisGSImpl viewGSCF = new ViewCFrameVisGSImpl();
					ViewCFrameVisPrefuseImpl viewPref = new ViewCFrameVisPrefuseImpl();
					
					presCajun.setModel(redPresenter.getModel());
					presGS.setModel(redPresenter.getModel());
					presPref.setModel(redPresenter.getModel());
					
					presPref.setView(viewPref);
					presCajun.setView(viewCajunCF);
					presGS.setView(viewGSCF);
					
					viewPref.setPresenter(presPref);
					viewCajunCF.setPresenter(presCajun);
					viewGSCF.setPresenter(presGS);
										
					redPresenter.addVisualMethod(presPref);
					redPresenter.addVisualMethod(presGS);
					redPresenter.addVisualMethod(presCajun);
					
					//Все добовляем в контейнер
					ViewTreeNodeWithVis multiView = new ViewTreeNodeWithVis();
					multiView.addTreeNode(viewTreeNodeCFrame.getViewComponent(), "Tree CFrames");
					multiView.addTreeNode(viewTreeNodeOwl.getViewComponent(), "Ontology tree");
					multiView.addVisualization(viewPref, presPref.getNameVisualMethod());
					multiView.addVisualization(viewCajunCF, presCajun.getNameVisualMethod());
					multiView.addVisualization(viewGSCF, presGS.getNameVisualMethod());
					multiView.addVisualization(viewOwlPrefuse, "Prefuse - Ontology");
					
					view.setContentPane(multiView.getViewComponent());
					
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
