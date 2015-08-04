package ru.iimm.ontology.visualization.tools;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import ca.uvic.cs.chisel.cajun.graph.arc.GraphArc;
import ca.uvic.cs.chisel.cajun.graph.node.GraphNode;
import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;
import ru.iimm.ontology.cftools.Branch;
import ru.iimm.ontology.cftools.CFrame;
import ru.iimm.ontology.cftools.DependencyCFrame;
import ru.iimm.ontology.cftools.PartonomyCFrame;
import ru.iimm.ontology.cftools.SpecialCFrame;
import ru.iimm.ontology.cftools.TaxonomyCFrame;
import ru.iimm.ontology.visualization.lang.Language;
import ru.iimm.ontology.visualization.tools.cajun.DefaultGraphModelCajun;
import ru.iimm.ontology.visualization.tools.cajun.VisNodeLinkCajunGUI;
import ru.iimm.ontology.visualization.tools.cajun.render.DefaultArcStyleCajun;
import ru.iimm.ontology.visualization.tools.cajun.render.DefaultNodeStyleCajun;
import ru.iimm.ontology.visualization.ui.GUIVisualMethodInt;

/**
 * Билдер для визуальной библиотеки Cajun.
 * @author Danilov E.Y.
 *
 */
public class VisNodeLinkCajunVisitor extends AbstractVisualMethodVisitor
{
	/**Стандартный граф cajun.*/
	private DefaultGraphModelCajun defaultGraphModel;
	
	private VisNodeLinkCajunGUI cajunGUI;
	
	private void initGUI()
	{
		this.cajunGUI = new VisNodeLinkCajunGUI(this);
	}
	
	private void defaultVisit(CFrame frame)
	{
		this.defaultGraphModel = new DefaultGraphModelCajun();
		this.defaultGraphModel.setGraphArcStyle(new DefaultArcStyleCajun());
		this.defaultGraphModel.setGraphNodeStyle(new DefaultNodeStyleCajun());
		
		for(OWLNamedIndividual owlName : frame.getContent().getConcepts())
		{
			String label = this.cfOnt.getLabel(owlName.getIRI(), Language.UPO_LANG);
			if(label.equals("_EMPTY_"))
			{
					label=owlName.getIRI().getFragment().toString();
			}
			
			GraphNode node = null;
			
			if(owlName.getIRI().equals(frame.getTrgConcept().getIRI()))
			{
				 node = this.defaultGraphModel.addNode(owlName, label,true);
			}
			else
			{
				 node = this.defaultGraphModel.addNode(owlName, label,false);
			}
			
			 
			
		}
		
		for (Branch branch : frame.getContent().getBranches())
		{
			GraphNode sub = null;
			GraphNode obj = null;
			
			for(GraphNode node : this.defaultGraphModel.getAllNodes())
			{
				
				OWLNamedIndividual owlNamedIndividual = (OWLNamedIndividual) node.getUserObject();
				
				if(owlNamedIndividual.equals(branch.getSubject()))
				{
					sub = node;
				}
				else
				{
					if(owlNamedIndividual.equals(branch.getObject()))
					{
						obj = node;
					}
				}
				
				if(sub != null && obj != null)
				{					
					String labelEdge = this.cfOnt.getAnnotationValue(branch.getPrp().getIRI(), IRI.create(ConstantsOntConverter.UPO_TITLE_LABEL));
					
					if(labelEdge.equals("_EMPTY_"))
					{
						labelEdge=branch.getPrp().getIRI().getFragment().toString();
					} 
				
					GraphArc edge = this.defaultGraphModel.addArc(sub, obj);
					
					break;
				}
			}
		}
	}
	
	/*@Override
	public GUIVisualMethodInt getVisualization()
	{
		return this.cajunGUI;
	}

	@Override
	public void disposeView()
	{
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public void visit(TaxonomyCFrame tFrame)
	{
		this.defaultVisit(tFrame);
		this.initGUI();
	}

	@Override
	public void visit(PartonomyCFrame pFrame)
	{
		this.defaultVisit(pFrame);
		this.initGUI();
	}

	@Override
	public void visit(DependencyCFrame dFrame)
	{
		this.defaultVisit(dFrame);
		this.initGUI();
	}

	@Override
	public void visit(SpecialCFrame sFrame)
	{
		this.defaultVisit(sFrame);
		this.initGUI();
	}

	public DefaultGraphModelCajun getDefaultGraphModel()
	{
		return this.defaultGraphModel;
	}

}
