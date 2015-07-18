package ru.iimm.ontology.OWL2UPOConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.SimpleIRIShortFormProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.clarkparsia.sparqlowl.parser.antlr.SparqlOwlParser.string_return;



import ru.iimm.ontology.OWL2UPOConverter.parsedAxioms.SubAxiom;
import ru.iimm.ontology.ontAPI.ConstantsOntAPI;
import ru.iimm.ontology.ontAPI.Ontology;
import ru.iimm.ontology.ontAPI.SkosOnt;

/**
 * @author Lomov P. A.
 *
 */
public class UserPresenOnt extends SkosOnt implements ConstantsOntConverter
{
	/**
	 * Набор использованных имен sparql переменных. Нужен для того, чтобы
	 * избежать повторений при генерации новых имен при добавлении концептов в
	 * ОПП.
	 */
	//private HashSet<String> usedSparqlVarNames;
	private HashSet<StringBuffer> usedSparqlVarNames;

	/**
	 * Счетчик сложных субаксиом. Используется при генерации IRI их концептов.
	 */
	int addedComplexSubAxiomCounter = 0;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserPresenOnt.class);

	protected UserPresenOnt()
	{
		super();
	}

	// TODO Подумать над заданием ИРИ - может он фиксированный будет
	public UserPresenOnt(IRI skosIRI, String filePath)
	{
		super(skosIRI, filePath);
		// TODO в будущем надо загружать в список названия переменных из
		// существующей ОПП
		this.usedSparqlVarNames = new HashSet<StringBuffer>();

		/* Добавляем в пустую ОПП аналог класса Thing */
		this.addConceptToUPO(ConstantsOntConverter.OWL_THING,
				ConstantsOntConverter.UPO_IRI);
		// http://www.w3.org/2002/07/owl#Thing

	}

	/**
	 * Загружает пустую UPOnt из файлов (полагается, что она многомодульная).
	 * 
	 * @param dir
	 * @param fileName
	 * @param mergeImportedOntology
	 */
	public UserPresenOnt(String dir, String fileName,
			Boolean mergeImportedOntology)
	{
		super(dir, fileName, mergeImportedOntology, false);
		// TODO в будущем надо загружать в список названия переменных из
		// существующей ОПП
		this.usedSparqlVarNames = new HashSet<StringBuffer>();

		/* Добавляем в пустую ОПП аналог класса Thing */
		this.addConceptToUPO(ConstantsOntConverter.OWL_THING,
				ConstantsOntConverter.UPO_IRI);

	}

	/**
	 * Загружает непустую UPO из файла.
	 * 
	 * @param pathToUPO
	 */
	public static UserPresenOnt loadUPOfromFile(String pathToUPO)
	{
		UserPresenOnt UPO = null;
		String dir = "";
		String file = "";

		try
		{
			dir = pathToUPO.substring(0, pathToUPO.lastIndexOf(File.separator));
			file = pathToUPO
					.substring(pathToUPO.lastIndexOf(File.separator) + 1);
		} catch (java.lang.StringIndexOutOfBoundsException e)
		{
			dir = pathToUPO.substring(0, pathToUPO.lastIndexOf('/'));
			file = pathToUPO.substring(pathToUPO.lastIndexOf('/') + 1);
			// System.exit
		}

		UPO = new UserPresenOnt(dir, file, false);
		// TODO в будущем надо загружать в список названия переменных из
		// существующей ОПП
		UPO.usedSparqlVarNames = new HashSet<StringBuffer>();

		return UPO;
	}

	/**
	 * Добавляет SKOS концепт в ОПП даже в случае нахождения в ней концепта с
	 * указанным IRI - в этом случае генерируется новый IRI на основе исходного.
	 * 
	 * @param initialIRI
	 * @return
	 */
	/*
	 * public OWLNamedIndividual addInUPOAsSKOSConceptAnyway(IRI initialIRI) {
	 * String skosConceptIRI=null;
	 * 
	 * if (this.isExistInOntology(df.getOWLNamedIndividual(initialIRI))) {
	 * skosConceptIRI = this.genNewIRI(initialIRI.toString()); return
	 * this.addInUPOAsSKOSConcept(IRI.create(skosConceptIRI)); } else { return
	 * this.addInUPOAsSKOSConcept(initialIRI); }
	 * 
	 * }
	 */

	/**
	 * Добавляет SKOS концепт в ОПП и привязывает к нему сопроводительную инф-ю.
	 * 
	 * @param IRIasString
	 */
	public OWLNamedIndividual addConceptToUPO(String IRIasString)
	{
		OWLNamedIndividual newConcept;
		newConcept = addConcept(IRIasString);

		/* Создаем соответвующую концепту переменную */
		String varSparql = "?" + getShortIRI(IRIasString);

		/* Создаем соответвующий видимый Label - короткий IRI */
		String label = getShortIRI(IRIasString);

		/*
		 * Создаем соответвующий фрагмент запроса (типа ?var RDF:type
		 * "http:\\ontology#car)
		 */
		String fragmentOfQuery = varSparql + " " + IRI_RDF_TYPE_SHORT + " <"
				+ IRIasString + ">";

		// В вписываем инф-ю в SKOS-концепт
		OWLLiteral labelLiteral = this.df.getOWLLiteral(label, "en");
		OWLLiteral fragmentOfQuerylLiteral = this.df.getOWLLiteral(
				fragmentOfQuery, OWL2Datatype.XSD_STRING);
		/* IRI исходного класса вписываем в hidden label */
		OWLLiteral hiddenLabelLiteral = this.df.getOWLLiteral(IRIasString,
				OWL2Datatype.XSD_ANY_URI);
		OWLLiteral varlLiteral = this.df.getOWLLiteral(varSparql,
				OWL2Datatype.XSD_STRING);

		OWLClass cls = OWLont.getOWLont().df.getOWLClass(IRI
				.create(IRIasString));
		// ======label=======
		for (OWLAnnotation annotation : cls.getAnnotations(
				OWLont.getOWLont().ontInMem,
				OWLont.getOWLont().df.getRDFSLabel()))
		{
			if (annotation.getValue() instanceof OWLLiteral)
			{
				OWLLiteral val = (OWLLiteral) annotation.getValue();
				this.addAnnotation(OWLont.getOWLont().df.getRDFSLabel()
						.getIRI().toString(), val, (OWLEntity) newConcept);
			}
		}
		// =====comment======
		for (OWLAnnotation annotation : cls.getAnnotations(
				OWLont.getOWLont().ontInMem,
				OWLont.getOWLont().df.getRDFSComment()))
		{
			if (annotation.getValue() instanceof OWLLiteral)
			{
				OWLLiteral val = (OWLLiteral) annotation.getValue();
				this.addAnnotation(OWLont.getOWLont().df.getRDFSComment()
						.getIRI().toString(), val, (OWLEntity) newConcept);
			}
		}

		// this.addAnnotation(RDF_LABEL, labelLiteral, (OWLEntity) newConcept);
		this.addAnnotation(SKOS_HIDDEN_LABEL, hiddenLabelLiteral,
				(OWLEntity) newConcept);
		this.addAnnotation(UPO_SPARQL_VARIABLE_LABEL, varlLiteral,
				(OWLEntity) newConcept);
		this.addAnnotation(UPO_SPARQL_QUERY_FRAGMENT_LABEL,
				fragmentOfQuerylLiteral, (OWLEntity) newConcept);

		return newConcept;
	}

	/**
	 * Добавляет SKOS концепт в ОПП и привязывает к нему сопроводительную инф-ю.
	 * Меняет базовый IRI добавляемого концепта на указанный.
	 * 
	 * @param IRIasString
	 * @param newBaseIri
	 *            новый Base указывать с # - т.е. http:\\ontology#
	 * @return
	 */

	// public OWLNamedIndividual addInUPOAsSKOSConcept(IRI initialIRI)
	public OWLNamedIndividual addConceptToUPO(String IRIasString,
			String newBaseIri)
	{
		// IRI iri = IRI.create(IRIasString);
		OWLNamedIndividual newConcept;
		/* Меняем базовую часть IRI */
		String iriWithNewBase = newBaseIri + getShortIRI(IRIasString);
		newConcept = addConcept(iriWithNewBase);

		/* Создаем соответвующую концепту переменную */
		String varSparql = "?" + getShortIRI(IRIasString);

		/* Создаем соответвующий видимый Label - короткий IRI */
		String label = getShortIRI(IRIasString);

		/*
		 * Создаем соответвующий фрагмент запроса (типа ?var RDF:type
		 * "http:\\ontology#car)
		 */
		String fragmentOfQuery = varSparql + " " + IRI_RDF_TYPE_SHORT + " "
				+ IRIasString;

		// В вписываем инф-ю в SKOS-концепт
		OWLLiteral labelLiteral = this.df.getOWLLiteral(label,
				OWL2Datatype.XSD_STRING);
		OWLLiteral fragmentOfQuerylLiteral = this.df.getOWLLiteral(
				fragmentOfQuery, OWL2Datatype.XSD_STRING);
		// OWLLiteral hiddenLabelLiteral =
		// this.df.getOWLStringLiteral(IRIasString, "en");
		OWLLiteral hiddenLabelLiteral = this.df.getOWLLiteral(IRIasString,
				OWL2Datatype.XSD_ANY_URI);
		OWLLiteral varlLiteral = this.df.getOWLLiteral(varSparql,
				OWL2Datatype.XSD_STRING);

		// Добавляем использованну SPARQL перемнную в набор ОПП
		// это делается при ее генерации в genNewSparql...()
		//this.usedSparqlVarNames.add(varSparql);

		this.addAnnotation(RDF_LABEL, labelLiteral, (OWLEntity) newConcept);
		this.addAnnotation(SKOS_HIDDEN_LABEL, hiddenLabelLiteral,
				(OWLEntity) newConcept);
		this.addAnnotation(UPO_SPARQL_VARIABLE_LABEL, varlLiteral,
				(OWLEntity) newConcept);
		this.addAnnotation(UPO_SPARQL_QUERY_FRAGMENT_LABEL,
				fragmentOfQuerylLiteral, (OWLEntity) newConcept);

		return newConcept;
	}

	/**
	 * Добавляет объектное свойство в ОПП и привязывает к нему сопроводительную
	 * инф-ю.
	 * 
	 * @param IRIasString
	 */
	//
	// public OWLNamedIndividual addInUPOAsSKOSConcept(IRI initialIRI)
	public OWLObjectProperty addObjectPropertyToUPO(String propIRIasString)
	{
		/* создаем свойство */
		OWLObjectProperty prpObj = this.addObjPrp(propIRIasString, false);

		/* Создаем соответвующий видимый SKOS:prefLabel - короткий IRI */
		String label = getShortIRI(propIRIasString);

		/*
		 * Создаем соответвующий фрагмент запроса (типа
		 * "http://ont.com#имеетПроизводителя"
		 */
		String fragmentOfQuery = propIRIasString;

		// В вписываем инф-ю в SKOS-концепт
		OWLLiteral labelLiteral = this.df.getOWLStringLiteral(label, "en");
		OWLLiteral fragmentOfQuerylLiteral = this.df.getOWLStringLiteral(
				fragmentOfQuery, "en");

		this.addAnnotation(UPO_SPARQL_QUERY_FRAGMENT_LABEL,
				fragmentOfQuerylLiteral, (OWLEntity) prpObj);

		// OWLClass cls =
		// OWLont.getOWLont().df.getOWLClass(IRI.create(IRIasString));
		// ======label=======
		for (OWLAnnotation annotation : prpObj.getAnnotations(
				OWLont.getOWLont().ontInMem,
				OWLont.getOWLont().df.getRDFSLabel()))
		{
			if (annotation.getValue() instanceof OWLLiteral)
			{
				OWLLiteral val = (OWLLiteral) annotation.getValue();
				this.addAnnotation(OWLont.getOWLont().df.getRDFSLabel()
						.getIRI().toString(), val, (OWLEntity) prpObj);
			}
		}
		// =====comment======
		for (OWLAnnotation annotation : prpObj.getAnnotations(
				OWLont.getOWLont().ontInMem,
				OWLont.getOWLont().df.getRDFSComment()))
		{
			if (annotation.getValue() instanceof OWLLiteral)
			{
				OWLLiteral val = (OWLLiteral) annotation.getValue();
				this.addAnnotation(OWLont.getOWLont().df.getRDFSComment()
						.getIRI().toString(), val, (OWLEntity) prpObj);
			}
		}

		// this.addAnnotation(RDF_LABEL, labelLiteral, (OWLEntity) prpObj);

		return prpObj;
	}

	/**
	 * Добавляет типизированное свойство в ОПП и привязывает к нему
	 * сопроводительную инф-ю.
	 * 
	 * @param IRIasString
	 */
	public OWLObjectProperty addDataTypePropertyToUPO(String propIRIasString)
	{
		/* создаем свойство */
		OWLObjectProperty prp = this.addObjPrp(propIRIasString, false);

		/* Создаем соответствующий видимый SKOS:prefLabel - короткий IRI */
		String label = getShortIRI(propIRIasString);

		/*
		 * Создаем соответствующий фрагмент запроса (типа
		 * "http://ont.com#имеетПроизводителя"
		 */
		String fragmentOfQuery = propIRIasString;

		// В вписываем инф-ю в SKOS-концепт
		OWLLiteral labelLiteral = this.df.getOWLLiteral(label, "en");
		OWLLiteral fragmentOfQuerylLiteral = this.df.getOWLLiteral(
				fragmentOfQuery, "en");

		this.addAnnotation(UPO_SPARQL_QUERY_FRAGMENT_LABEL,
				fragmentOfQuerylLiteral, (OWLEntity) prp);
		// this.addAnnotation(RDF_LABEL, labelLiteral, (OWLEntity) prp);
		// ======label=======
		for (OWLAnnotation annotation : prp.getAnnotations(
				OWLont.getOWLont().ontInMem,
				OWLont.getOWLont().df.getRDFSLabel()))
		{
			if (annotation.getValue() instanceof OWLLiteral)
			{
				OWLLiteral val = (OWLLiteral) annotation.getValue();
				this.addAnnotation(OWLont.getOWLont().df.getRDFSLabel()
						.getIRI().toString(), val, (OWLEntity) prp);
			}
		}
		// =====comment======
		for (OWLAnnotation annotation : prp.getAnnotations(
				OWLont.getOWLont().ontInMem,
				OWLont.getOWLont().df.getRDFSComment()))
		{
			if (annotation.getValue() instanceof OWLLiteral)
			{
				OWLLiteral val = (OWLLiteral) annotation.getValue();
				this.addAnnotation(OWLont.getOWLont().df.getRDFSComment()
						.getIRI().toString(), val, (OWLEntity) prp);
			}
		}

		return prp;
	}

	/**
	 * Добавляет область значения типизированного свойства (RANGE) в ОПП как
	 * SKOS концепт и привязывает к нему сопроводительную инф-ю.
	 * 
	 * @param rangeIRI
	 * @param propertyIRI
	 * @return
	 */
	public OWLNamedIndividual addDatatypeRangeToUPO(IRI rangeIRI,
			IRI propertyIRI)
	{
		// IRI iri = IRI.create(IRIasString);
		OWLNamedIndividual newConcept;

		/*
		 * Проверяем сущестсвование концепта-радиусу в ОПП, если с таки iri уже
		 * есть - генерим новый iri Новый радиус-концепт нужен т.к. у него
		 * другая сопроводительная инф-я
		 */
		String skosConceptIRI = null;
		String realIRI = rangeIRI.toString();
		rangeIRI = IRI.create(changeBaseIRI(rangeIRI,
				UPO_TEMPLATE_IRI_OF_DATARANGE));

		if (this.isExistInOntology(df.getOWLNamedIndividual(rangeIRI)))
		{
			skosConceptIRI = this.getNewIRI(rangeIRI.toString());
		} else
		{
			skosConceptIRI = rangeIRI.toString();
		}

		/* Создаем концепт-радиус свойства */
		newConcept = addConcept(skosConceptIRI);

		/*
		 * Создаем соответвующую концепту переменную в данном случае она равна
		 * сокращенному iri свойства
		 */
		String varSparql = "?" + getShortIRI(propertyIRI);

		// Добавляем использованну SPARQL перемнную в набор ОПП
		// Делается при генерации 
		//this.usedSparqlVarNames.add(varSparql);

		/* Создаем соответвующий видимый Label - короткий IRI */
		String label = getShortIRI(rangeIRI);

		/* Создаем соответвующий скрытый Label - IRI из исходной онтологии */
		OWLLiteral hiddenLabelLiteral = this.df.getOWLLiteral(realIRI,
				OWL2Datatype.XSD_ANY_URI);

		// В вписываем инф-ю в SKOS-концепт
		OWLLiteral labelLiteral = this.df.getOWLLiteral(label,
				OWL2Datatype.XSD_STRING);
		OWLLiteral varlLiteral = this.df.getOWLLiteral(varSparql,
				OWL2Datatype.XSD_STRING);

		this.addAnnotation(RDF_LABEL, labelLiteral, (OWLEntity) newConcept);
		this.addAnnotation(UPO_SPARQL_VARIABLE_LABEL, varlLiteral,
				(OWLEntity) newConcept);
		this.addAnnotation(SKOS_HIDDEN_LABEL, hiddenLabelLiteral,
				(OWLEntity) newConcept);
		return newConcept;
	}

	/**
	 * Меняет у IRI основу на заданный
	 * 
	 * @param iriToChange
	 * @param newBase
	 * @return
	 */
	public static String changeBaseIRI(IRI iriToChange, String newBase)
	{
		return newBase + getShortIRI(iriToChange.toString());
	}

	/**
	 * Меняет у IRI основу на заданный
	 * 
	 * @param iriToChange
	 * @param newBase
	 * @return
	 */
	public static String changeBaseIRI(String iriToChange, String newBase)
	{
		return newBase + getShortIRI(iriToChange);
	}

	/**
	 * Возвращает IRI SKOS концепта из ОПП по указанному аннотационному
	 * свойству, значением которого является IRI
	 * 
	 * @param annotIRI
	 * @param valueAsIRI
	 * @param ontUPO
	 * @return
	 */
	public IRI getConceptByIRIinAnnotationValue(IRI annotIRI, IRI valueAsIRI)
	{
		OWLLiteral valLit = this.convertIRIToOWLLiteral(valueAsIRI);

		IRI conceprIRI = this.getEntityByAnnotationValue(
				IRI.create(SKOS_HIDDEN_LABEL), valLit);

		if (conceprIRI == null)
		{
			LOGGER.error("!!! ERROR - getConceptOfSubAxiomFromUPObyHiddenLabelValue "
					+ "\n!!! Concept not found in UPO"
					+ "\n!!! Concept hidden label:" + valLit);
			System.exit(9);
		}

		return conceprIRI;
	}

	/**
	 * Генерит новый IRI для сложной субаксиомы на основе счетчика.
	 * 
	 * @param sbAx
	 * @return
	 */
	public IRI getNewComplexSbAxiomIRI(SubAxiom sbAx)
	{
		this.addedComplexSubAxiomCounter++;
		IRI iri = IRI.create(UPO_TEMPLATE_IRI_OF_COMLEXSBAXIOM + "-"
				+ this.addedComplexSubAxiomCounter);
		return iri;
	}

	public IRI getConceptOfSubAxiomFromUPObyIRIinHiddenLabel(
			IRI hiddenLabelValue)
	{
		IRI conceprIRI = this.getConceptByIRIinAnnotationValue(
				IRI.create(SKOS_HIDDEN_LABEL), hiddenLabelValue);
		return conceprIRI;
	}

	/**
	 * Возвращает которкую форму заданного IRI.
	 * 
	 * @param iri
	 * @return
	 */
	public static String getShortIRI(String iri)
	{
		SimpleIRIShortFormProvider prv = new SimpleIRIShortFormProvider();
		return prv.getShortForm(IRI.create(iri));
	}

	/**
	 * Возвращает которкую форму заданного IRI.
	 * 
	 * @param iri
	 * @return
	 * @TODO в спецкласс перенести
	 */
	public static String getShortIRI(IRI iri)
	{
		SimpleIRIShortFormProvider prv = new SimpleIRIShortFormProvider();
		return prv.getShortForm(iri);
	}

	/**
	 * Возвращает которкую форму IRI заданной сущности.
	 * 
	 * @param iri
	 * @return
	 * @TODO в спецкласс перенести
	 */
	public static String getShortIRI(OWLEntity ent)
	{
		SimpleIRIShortFormProvider prv = new SimpleIRIShortFormProvider();
		return prv.getShortForm(ent.getIRI());
	}

	/**
	 * Возвращает SparqlVar у указанного экземпляра ОПП.
	 * 
	 * @param conceptIRI
	 * @return
	 */
	public String getSparqlVarOfSubAxiomFromConcept(IRI conceptIRI)
	{
		return this.getAnnotationValue(conceptIRI,
				IRI.create(UPO_SPARQL_VARIABLE_LABEL));
	}

	public String getSparqlQueryPartOfSubAxiomFromConcept(IRI conceptIRI)
	{
		return this.getAnnotationValue(conceptIRI,
				IRI.create(UPO_SPARQL_QUERY_FRAGMENT_LABEL));
	}
	
	/**
	 * Генерит новую SPARQL переменную, которой еще нет в UPO.
	 * @param sbAx субаксиома, для которой надо сгенерить переменную
	 * @param ontUPO
	 * @return
	 * @TODO после генерации вроде ее нужно добавить в список
	 */
	public String genNewSparqlVar(SubAxiom sbAx)
	{
		
		int index=0;
		StringBuffer newVar= new StringBuffer().append('?'+sbAx.getTitle());
		//String newVar="?"+sbAx.getTitle();
		if (this.usedSparqlVarNames.contains(newVar))
		{
			newVar.append('-'+index++);			
			while ( this.usedSparqlVarNames.contains(newVar) )
			{
				//newVar = "?"+sbAx.getTitle()+"-"+index++;
				newVar.delete(newVar.lastIndexOf("-"), newVar.length());
				newVar.append('-'+index++);			
 			}
				 
		}
		return newVar.toString();
	}

}
