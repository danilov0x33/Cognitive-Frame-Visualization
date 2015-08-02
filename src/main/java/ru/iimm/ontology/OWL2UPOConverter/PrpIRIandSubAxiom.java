package ru.iimm.ontology.OWL2UPOConverter;
import org.semanticweb.owlapi.model.IRI;

	/**
	 * класс - пара <IRI-свойства -- субаксиома>
	 * @author lomov
	 */
	public class PrpIRIandSubAxiom
	{

		IRI propIRI;
		SubAxiom subax;
		
		public PrpIRIandSubAxiom(IRI propIRI, SubAxiom subax)
		{
			this.propIRI=propIRI;
			this.subax=subax;
		}
		
		public IRI getPropIRI()
		{
			return propIRI;
		}

		public SubAxiom getSubax()
		{
			return subax;
		}
		
		
	}
