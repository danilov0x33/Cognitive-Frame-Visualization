package ru.iimm.ontology.visualization.tools;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.CFrameContent;
import ru.iimm.ontology.cftools.CFrameOnt;
import ru.iimm.ontology.cftools.CFrameVisitor;
import ru.iimm.ontology.cftools.CFrameVisualisationMethod;
import ru.iimm.ontology.cftools.DependencyCFrame;
import ru.iimm.ontology.cftools.PartonomyCFrame;
import ru.iimm.ontology.cftools.SpecialCFrame;
import ru.iimm.ontology.cftools.TaxonomyCFrame;
import ru.iimm.ontology.visualization.lang.Language;

/**
 * Реализация оболочки для CFrame.
 * @author Danilov E.Y.
 *
 */
public class CFrameDecorator implements CFrameDecoratorInt, ContentVisualizationViewerInt
{
	private static final Logger log = LoggerFactory.getLogger(CFrameDecorator.class);

	/**CFrame для которой создаем оболочку.*/
	private CFrame cframe;
	/**Онтология с CFrame.*/
	private CFrameOnt cfOnt;
	/**Имя концепта CFrame.*/
	private String targetName;
	/**Название типа CFrame.*/
	private String typeCFrame;
	
	/**
	 * {@linkplain CFrameDecorator}
	 * @param cfOnt - {@linkplain #cfOnt}
	 * @param cframe - {@linkplain #cframe}
	 */
	public CFrameDecorator(CFrameOnt cfOnt,CFrame cframe)
	{
		this.cfOnt = cfOnt;
		this.setCframe(cframe);
	}

	@Override
    public void accept(VisualMethodVisitorInt visitor)
    {
		if (cframe instanceof PartonomyCFrame)
		{
			visitor.visit((PartonomyCFrame) cframe);
		} else
		{
			if (cframe instanceof TaxonomyCFrame)
			{
				visitor.visit((TaxonomyCFrame) cframe);
			} else
			{
				if (cframe instanceof DependencyCFrame)
				{
					visitor.visit((DependencyCFrame) cframe);
				} else
				{
					if (cframe instanceof SpecialCFrame)
					{
						visitor.visit((SpecialCFrame) cframe);
					}
				}
			}
		}
    }

	//=========Методы CFrame=============
    public void accept(CFrameVisitor visitor)
    {
    	this.cframe.accept(visitor);
    }
	
	@Override
	public CFrameVisualisationMethod getVisualMethod()
	{
		return this.cframe.getVmethod();
	}
    
	public CFrameContent getContent()
	{
		return this.cframe.getContent();
	}
	
	public CFrameVisualisationMethod getVmethod()
	{
		return this.cframe.getVmethod();
	}
	public OWLNamedIndividual getTrgConcept()
	{
		return this.cframe.getTrgConcept();
	}
	public OWLClass getCFrameOWLClass()
	{
		return this.cframe.getCFrameOWLClass();
	}

	public CFrame getCframe() 
	{
		return cframe;
	}
	
	
	public String toString()
	{		
		return this.targetName + " - " + typeCFrame;
	}

	public CFrameOnt getCfOnt()
	{
		return cfOnt;
	}

	/**Получить название концепта CFrame.*/
	public String getTargetName()
	{
		return targetName;
	}
	/**Получить название типа CFrame.*/
	public String getTypeCFrame()
	{
		return typeCFrame;
	}

	public void setCframe(CFrame cframe)
	{
		this.cframe = cframe;
		
		targetName = cfOnt.getLabel(this.cframe.getTrgConcept().getIRI(), Language.UPO_LANG);
		if(targetName.equals("_EMPTY_"))
		{
			targetName=this.cframe.getTrgConcept().getIRI().getFragment();
		}
		
		if(cframe instanceof PartonomyCFrame)
		{
			typeCFrame=Language.PARTONOMY_COGNITIVE_FRAME;
		}
		else
		{
			if(cframe instanceof TaxonomyCFrame)
			{
				typeCFrame=Language.TAXONOMY_COGNITIVE_FRAME;
			}
			else
			{
				if(cframe instanceof DependencyCFrame)
				{
					typeCFrame=Language.DEPENDENCY_COGNITIVE_FRAME;
				}
				else
				{
					if(cframe instanceof SpecialCFrame)
					{
						typeCFrame=Language.SPECIAL_COGNITIVE_FRAME;
					}
				}
			}
		}		
	}
}
