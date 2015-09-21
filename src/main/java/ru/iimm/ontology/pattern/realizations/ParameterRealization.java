package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.pattern.ParameterCDP;

public class ParameterRealization extends ODPRealization
{
	private OWLNamedIndividual concept;
	private OWLNamedIndividual parameter;
	private OWLLiteral parameterDataValue;
	/**
	 * {@linkplain ParameterRealization}
	 */
	private ParameterRealization(){}

	public OWLNamedIndividual getConcept()
	{
		return concept;
	}

	public OWLNamedIndividual getParameter()
	{
		return parameter;
	}

	public OWLLiteral getParameterDataValue()
	{
		return parameterDataValue;
	}
	
	public static Builder newBuilder(ParameterCDP pattern)
	{
		return new ParameterRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private ParameterCDP pattern;
		
		private Builder(ParameterCDP pattern)
		{
			this.pattern = pattern;
		}

		public Builder setParameterDataValue(OWLLiteral parameterDataValue)
		{
			ParameterRealization.this.parameterDataValue = parameterDataValue;
			return this;
		}

		public Builder setParameter(OWLNamedIndividual parameter)
		{
			ParameterRealization.this.parameter = parameter;
			return this;
		}

		public Builder setConcept(OWLNamedIndividual concept)
		{
			ParameterRealization.this.concept = concept;
			return this;
		}
		
		public ParameterRealization build()
		{	
			//Создаем новый объект
			ParameterRealization realization = new ParameterRealization();			
			realization.parameter = ParameterRealization.this.parameter;
			realization.concept = ParameterRealization.this.concept;
			realization.parameterDataValue = ParameterRealization.this.parameterDataValue;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			OWLDataPropertyAssertionAxiom parameterDataValueAx = df.getOWLDataPropertyAssertionAxiom(pattern.getHasParameterDataValue(), parameter, parameterDataValue);

			structuralAxList.add(df.getOWLClassAssertionAxiom(pattern.getParameter(), parameter));
			structuralAxList.add(df.getOWLClassAssertionAxiom(pattern.getConcept(), concept));
			
			OWLObjectPropertyAssertionAxiom hasParameter = df.getOWLObjectPropertyAssertionAxiom(pattern.getHasParameter(), concept, parameter);
			OWLObjectPropertyAssertionAxiom isParameterFor = df.getOWLObjectPropertyAssertionAxiom(pattern.getIsParameterFor(), parameter, concept);
			structuralAxList.add(hasParameter);
			structuralAxList.add(isParameterFor);
			
			structuralAxList.add(parameterDataValueAx);

			return realization;
		}
	}
}
