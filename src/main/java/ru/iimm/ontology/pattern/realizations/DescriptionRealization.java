package ru.iimm.ontology.pattern.realizations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import ru.iimm.ontology.pattern.DescriptionCDP;
import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.visualization.patterns.DescriptionRealizationVis;
import ru.iimm.ontology.visualization.patterns.elements.ArrowElementVis;
import ru.iimm.ontology.visualization.patterns.elements.DescriptionElementVis;
import ru.iimm.ontology.visualization.patterns.elements.DottedLine;

public class DescriptionRealization extends ODPRealization
{
	private OWLClass description;
	private ArrayList<OWLClass> concepts;
	
	/**
	 * {@linkplain DescriptionRealization}
	 */
	private DescriptionRealization()
	{
		this.concepts = new ArrayList<OWLClass>();
	}

	public OWLClass getDescription()
	{
		return description;
	}

	public ArrayList<OWLClass> getConcept()
	{
		return this.concepts;
	}	
	
	public static Builder newBuilder(DescriptionCDP pattern)
	{
		return new DescriptionRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private DescriptionCDP pattern;
		
		private Builder(DescriptionCDP pattern)
		{
			this.pattern = pattern;
		}
		
		public Builder setConcept(OWLClass concept)
		{
			DescriptionRealization.this.concepts.add(concept);
			return this;
		}
		public Builder setConcepts(OWLClass ... concepts)
		{
			for(OWLClass concept : concepts)
			{
				DescriptionRealization.this.concepts.add(concept);
			}
			return this;
		}
		public Builder setConcepts(Collection<OWLClass> concepts)
		{
			for(OWLClass concept : concepts)
			{
				DescriptionRealization.this.concepts.add(concept);
			}
			return this;
		}
		public Builder setDescription(OWLClass description)
		{
			DescriptionRealization.this.description = description;
			return this;
		}
		
		public DescriptionRealizationVis buildVisualization()
		{
			DescriptionRealization description = this.build();
			
			DescriptionRealizationVis desVis = new DescriptionRealizationVis(description.getDescription());
			desVis.setLabelElement(description.getDescription().getIRI().getFragment());
			desVis.setDottedLine(DottedLine.DASHED);
			
			ArrowElementVis arrow = new ArrowElementVis(this.pattern.getDefines());
			arrow.setBackgroudColor(Color.WHITE);
			arrow.setDottedLine(DottedLine.DASHED);
			
			desVis.setBinder(arrow);
			
			for(OWLClass concept : description.getConcept())
			{
				DescriptionElementVis visElem = new DescriptionElementVis(concept);
				visElem.setLabelElement(concept.getIRI().getFragment());
				visElem.setDottedLine(DottedLine.DASHED);
				
				desVis.addElement(visElem);
			}
			
			return desVis;
		}
		
		public DescriptionRealization build()
		{	
			//Создаем новый объект
			DescriptionRealization realization = new DescriptionRealization();			
			realization.concepts = DescriptionRealization.this.concepts;
			realization.description = DescriptionRealization.this.description;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();	
			structuralAxList.add(df.getOWLSubClassOfAxiom(description, pattern.getDescription()));
			
			for(OWLClass concept : concepts)
			{
				structuralAxList.add(df.getOWLSubClassOfAxiom(concept, pattern.getConcept()));
				
				OWLObjectSomeValuesFrom defines = df.getOWLObjectSomeValuesFrom(pattern.getDefines(), concept);
				OWLObjectSomeValuesFrom isDefinesIn = df.getOWLObjectSomeValuesFrom(pattern.getIsDefinesIn(), description);
				OWLObjectSomeValuesFrom usesConcept = df.getOWLObjectSomeValuesFrom(pattern.getUsesConcept(), concept);
				OWLObjectSomeValuesFrom isConceptUsedIn = df.getOWLObjectSomeValuesFrom(pattern.getIsConceptUsedIn(), description);
				structuralAxList.add(df.getOWLSubClassOfAxiom(description, defines));
				structuralAxList.add(df.getOWLSubClassOfAxiom(concept, isDefinesIn));
				structuralAxList.add(df.getOWLSubClassOfAxiom(description, usesConcept));
				structuralAxList.add(df.getOWLSubClassOfAxiom(concept, isConceptUsedIn));
			}

			return realization;
		}
	}
}
