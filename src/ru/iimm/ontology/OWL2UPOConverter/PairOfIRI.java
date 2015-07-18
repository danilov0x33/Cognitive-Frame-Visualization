package ru.iimm.ontology.OWL2UPOConverter;
import org.semanticweb.owlapi.model.IRI;

	/**
	 * Внутренний класс - пара <IRI свойства -- IRI значения> ;
	 * @author lomov
	 */
public class PairOfIRI
	{
		private IRI fIRI;
		private IRI sIRI;
		
		public PairOfIRI(IRI fIRI, IRI sIRI)
		{
			this.setSecond(fIRI);
			this.setSecond(sIRI);
		}

		/**
		 * @return the fIRI
		 */
		public IRI getFirst()
		{
			return fIRI;
		}

		/**
		 * @param fIRI the fIRI to set
		 */
		public void setFirst(IRI fIRI)
		{
			this.fIRI = fIRI;
		}

		/**
		 * @param sIRI the sIRI to set
		 */
		public void setSecond(IRI sIRI)
		{
			this.sIRI = sIRI;
		}
	}
