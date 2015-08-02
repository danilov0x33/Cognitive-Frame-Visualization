package ru.iimm.ontology.visualization.tools;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import ru.iimm.ontology.cftools.CFrameOnt;
import ru.iimm.ontology.cftools.PartonomyCFrame;
import ru.iimm.ontology.cftools.SpecialCFrame;
import ru.iimm.ontology.cftools.TaxonomyCFrame;
import ru.iimm.ontology.cftools.DependencyCFrame;

/**
 * Абстрактный класс для реализации визуального метода.
 * @author Danilov E.Y.
 *
 */
public abstract class AbstractVisualMethodVisitor implements VisualMethodVisitorInt
{	
	/**Онтология с КФ.*/
	protected CFrameOnt cfOnt;
	/**Была ли произведена построение визуализации.*/
	private boolean builderViewer = false;
	/**Название визуализации.*/
	protected String nameVisualMethod = "Visual Method";
	
	/**
	 * {@linkplain AbstractVisualMethodVisitor}
	 * @param cfOnt - {@linkplain #cfOnt}
	 */
	public AbstractVisualMethodVisitor(CFrameOnt cfOnt)
	{
		this.cfOnt=cfOnt;
	}
	/**
	 * {@linkplain AbstractVisualMethodVisitor}
	 */
	public AbstractVisualMethodVisitor()
	{
		this(null);
	}
	
	@Override
	public void visit(DependencyCFrame dFrame)
	{
		this.setVisitViewer(true);
	}
	
	@Override
	public void visit(OWLOntologyManager manager, OWLOntology ontology, OWLReasoner reasoner)
	{
		this.setVisitViewer(true);
	}
	
	@Override
	public void visit(PartonomyCFrame pFrame)
	{
		this.setVisitViewer(true);
	}
	
	@Override
	public void visit(SpecialCFrame sFrame)
	{
		this.setVisitViewer(true);
	}
	
	@Override
	public void visit(TaxonomyCFrame tFrame)
	{
		this.setVisitViewer(true);
	}
	
	public void setVisitViewer(boolean b)
	{
		this.builderViewer = true;
	}
	
	
	public boolean isVisitViewer()
	{
		return this.builderViewer;
	}

	public CFrameOnt getCfOnt()
	{
		return cfOnt;
	}

	public void setCfOnt(CFrameOnt cfOnt)
	{
		this.cfOnt = cfOnt;
	}

	public String getNameVisualMethod()
	{
		return nameVisualMethod;
	}

	public void setNameVisualMethod(String nameVisualMethod)
	{
		this.nameVisualMethod = nameVisualMethod;
	}
	
	@Override
	public String toString() 
	{
		return this.nameVisualMethod;
	}
}
