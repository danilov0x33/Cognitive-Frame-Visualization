package ru.iimm.ontology.pattern;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLLogicalAxiom;

public class ODPRealization
{
	private ContentDesingPattern pattern;
	
	private Set<OWLLogicalAxiom> structualAxiomSet;
	
	private Set<OWLLogicalAxiom> semanticAxiomSet;
	
	/**
	 * Реализация для паттерна, состоящая из наборов аксиом.
	 * @param pattern
	 * @param structualAxiomSet
	 * @param semanticAxiomSet
	 */
	public ODPRealization(ContentDesingPattern pattern,
			Set<OWLLogicalAxiom> structualAxiomSet,
			Set<OWLLogicalAxiom> semanticAxiomSet) 
	{
		super();
		this.pattern = pattern;
		this.structualAxiomSet = structualAxiomSet == null ? new HashSet<OWLLogicalAxiom>() : structualAxiomSet;
		this.semanticAxiomSet = semanticAxiomSet == null ? new HashSet<OWLLogicalAxiom>() : semanticAxiomSet;
	}
	
	public ODPRealization()
	{
		this(null, null, null);
	}

	/**
	 * Добавляет набор аксиом (реализацию) в реализации в онтологию.
	 */
	public void toPatternOnt() 
	{
		this.pattern.getCDPOntology().mng.addAxioms(pattern.getCDPOntology().ontInMem, 
				this.structualAxiomSet);
		this.pattern.getCDPOntology().mng.addAxioms(pattern.getCDPOntology().ontInMem, 
				this.semanticAxiomSet);

	}

	public ContentDesingPattern getPattern() {
		return this.pattern;
	}


	public void setPattern(ContentDesingPattern pattern) {
		this.pattern = pattern;
	}

	public Set<OWLLogicalAxiom> getStructualAxiomSet() {
		return structualAxiomSet;
	}

	public void setStructualAxiomSet(Set<OWLLogicalAxiom> structualAxiomSet) {
		this.structualAxiomSet = structualAxiomSet;
	}

	public Set<OWLLogicalAxiom> getSemanticAxiomSet() {
		return semanticAxiomSet;
	}

	public void setSemanticAxiomSet(Set<OWLLogicalAxiom> semanticAxiomSet) {
		this.semanticAxiomSet = semanticAxiomSet;
	}

	
	

	
}
