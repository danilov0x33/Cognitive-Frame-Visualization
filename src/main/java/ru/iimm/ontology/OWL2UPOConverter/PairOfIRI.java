package ru.iimm.ontology.OWL2UPOConverter;
import org.semanticweb.owlapi.model.IRI;

	/**
	 * Внутренний класс - пара <IRI свойства -- IRI значения> ;
	 * @author lomov
	 */
public class PairOfIRI
	{
		IRI fIRI;
		IRI sIRI;
		
		public PairOfIRI(IRI fIRI, IRI sIRI)
		{
			this.fIRI=fIRI;
			this.sIRI=sIRI;
		}
	}
