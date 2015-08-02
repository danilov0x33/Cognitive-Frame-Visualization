/**
 * 
 */
package ru.iimm.ontology.cftools;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.ontAPI.Ontology;

/**
 * Класс для хранения отношений между понятиями - 
 * т.е. ребер графовой структуры вида:
 * concept1 ---prp---> concept2
 * @author Lomov P. A.
 */
public class Branch 
{
	
	private static final Logger log = LoggerFactory.getLogger(Branch.class);
	
	private OWLNamedIndividual subject;
	private OWLObjectProperty prp;
	private OWLNamedIndividual object;
	private IRI brachIndIRI;

	
	/**
	 * Создает дугу для последующего добавления в ОПП из
	 * IRI ее составляющих
	 * @param subjectIRI
	 * @param prpIRI
	 * @param objectIRI
	 * @param ont
	 */
	Branch(IRI subjectIRI, IRI prpIRI,	IRI objectIRI, Ontology ont) 
	{
		super();
		OWLNamedIndividual subject = ont.df.getOWLNamedIndividual(subjectIRI);
		OWLObjectProperty prp = ont.df.getOWLObjectProperty(prpIRI);
		OWLNamedIndividual object  = ont.df.getOWLNamedIndividual(objectIRI);
		
		if (subject==null || object==null || prp==null)
		{
			log.error("!Error creating branch - individual does not exist:");
			log.error("![" + subject.getIRI().getFragment() +" --"+
			subject.getIRI().getFragment()+"--> "+object.getIRI().getFragment());
			System.exit(9);
		}
		
		this.subject = subject;
		this.prp = prp;
		this.object = object;
	}

	/**
	 * Создает дугу для последующего добавления в ОПП.
	 * @param subject
	 * @param prp
	 * @param object
	 */
	Branch(OWLNamedIndividual subject, OWLObjectProperty prp,
			OWLNamedIndividual object) 
	{
		super();
		this.subject = subject;
		this.prp = prp;
		this.object = object;
		//this.brachIndIRI = brachIndIRI;
	}
	
	/**
	 * Создает дугу из данных ее экземпляра-представителя в ОПП.
	 * @param subject
	 * @param prp
	 * @param object
	 */
	Branch(OWLNamedIndividual subject, OWLObjectProperty prp,
			OWLNamedIndividual object, IRI brachIndIRI) 
	{
		super();
		this.subject = subject;
		this.prp = prp;
		this.object = object;
		this.brachIndIRI = brachIndIRI;
	}
	
	
	@Override
	public String toString()
	{
		
		return "Branch ["+Ontology.getShortIRI(subject.getIRI())+ 
				" --" + Ontology.getShortIRI(prp.getIRI()) + 
				"-> "	+ Ontology.getShortIRI(object.getIRI()) + "]";
	}

	/* Возвращает хэш-код дуги на основе IRI ее елементов.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : this.object.getIRI().toString().hashCode());
		result = prime * result + ((prp == null) ? 0 : prp.getIRI().toString().hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.getIRI().toString().hashCode());
		return result;
	}

	/* Определяет одиноковость дуг на основе определения эквивалентности 
	 * IRI их элементов.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Branch other = (Branch) obj;
		
		
		
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.getIRI().equals(other.object.getIRI()))
			return false;
		if (prp == null) {
			if (other.prp != null)
				return false;
		} else if (!prp.getIRI().equals(other.prp.getIRI()))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.getIRI().equals(other.subject.getIRI()))
			return false;
		return true;
	}

	/**
	 * Дуги могут был по-смыслу равны при инверсивным свойствам.
	 * Например: человек --имеетИмя--> Вася, человек --имеетИмя--> Вася 
	 * @param br
	 * @param ont
	 * @return
	 */
	public boolean semanticEquals(Branch br, Ontology ont)
	{
		if (this.subject.equals(br.object) & this.object.equals(br.subject) )
		{
			for (OWLObjectProperty invPrp : ont.getInverseObjectProperties(this.prp))
			{
				if (this.prp.equals(invPrp)) return true; 
			} 
		}
		return false;
	}
	
	//=====Get & Set=====
	public OWLNamedIndividual getSubject()
	{
		return subject;
	}

	public void setSubject(OWLNamedIndividual subject)
	{
		this.subject = subject;
	}

	public OWLObjectProperty getPrp()
	{
		return prp;
	}

	public void setPrp(OWLObjectProperty prp)
	{
		this.prp = prp;
	}

	public OWLNamedIndividual getObject()
	{
		return object;
	}

	public void setObject(OWLNamedIndividual object)
	{
		this.object = object;
	}

	public IRI getBrachIndIRI()
	{
		return brachIndIRI;
	}

	public void setBrachIndIRI(IRI brachIndIRI)
	{
		this.brachIndIRI = brachIndIRI;
	}
	
}//===END= class BRANCH=====