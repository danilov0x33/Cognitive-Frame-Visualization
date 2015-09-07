/**
 * 
 */
package ru.iimm.ontology.OWL2UPOConverter.parsedAxioms;

//import org.apache.lucene.analysis.Analyzer;
import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNaryClassAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.OWL2UPOConverter.ClassExpressionParserVisitor;
import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;
import ru.iimm.ontology.OWL2UPOConverter.ElementCollectorVisitor;
import ru.iimm.ontology.OWL2UPOConverter.OWL2UPOConverter;
import ru.iimm.ontology.OWL2UPOConverter.OWLont;
import ru.iimm.ontology.OWL2UPOConverter.PairOfIRI;
import ru.iimm.ontology.OWL2UPOConverter.SplitAxiomVisitor;
import ru.iimm.ontology.OWL2UPOConverter.UPOont;
import ru.iimm.ontology.OWL2UPOConverter.UserPresenOnt;
import ru.iimm.ontology.ontAPI.ConstantsOntAPI;
import ru.iimm.ontology.ontAPI.Ontology;

/**
 * Отвечает за создание экземпляров классов в данном пакедже. Реализует паттерн
 * "Одиночка" Singleton.
 * 
 * @author Lomov P. A.
 *
 */
public class DataFactory
{

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DataFactory.class);

	private static DataFactory instance;

	private DataFactory()
	{
	}

	/**
	 * Возвращает экземпляр фабрики.
	 * 
	 * @return
	 */
	public static DataFactory getFactory()
	{
		if (instance == null)
		{
			instance = new DataFactory();
		}
		return instance;
	}

	/**
	 * Возвращает разделенную аксиому из исходной.
	 * 
	 * @param ax
	 * @param ontOWL
	 * @param ontUPO
	 * @return
	 */
	public SplitAxiom getSplitAxiom(OWLLogicalAxiom ax, Ontology ontOWL,
			UserPresenOnt ontUPO)
	{
		SplitAxiomVisitor vis = new SplitAxiomVisitor();
		ax.accept(vis);

		if (vis.isSuccesfull())
		{
			return new SplitAxiom(vis.getLeftOWLclassExpression(),
					vis.getRightOWLclassExpression(), ax);

		} else
		{
			return null;
		}

	}

	/**
	 * Определяет составная ли аксиома передана в качестве аргумента
	 * 
	 * @param ax
	 * @return
	 */
	static public boolean isCompound(OWLAxiom ax)
	{
		// Boolean compound = false;

		if (!ax.isLogicalAxiom())
			return false;

		/* == Для подкласс аксиом */
		if (ax.getAxiomType().equals(AxiomType.SUBCLASS_OF))
		{
			OWLSubClassOfAxiom subClAx = (OWLSubClassOfAxiom) ax;
			return subClAx.getSuperClass().isAnonymous();
		}

		/* == Для тождественных аксиом */
		if (ax.getAxiomType().equals(AxiomType.EQUIVALENT_CLASSES))
		{
			OWLNaryClassAxiom naryAx = (OWLNaryClassAxiom) ax;
			return naryAx.getClassExpressionsAsList().get(1).isAnonymous();
		}

		return false;
	}



	/**
	 * @param dt
	 * @param ontOWL
	 * @param ontUPO
	 * @return
	 */
	
	public AbstractAxiom getCloneAxiom(AbstractAxiom ax)
	{
		AbstractAxiom clone = null;
		try
		{
			clone = ax.clone();
		} catch (Exception e)
		{
			LOGGER.error("!!!Error - can't clone axiom" + ax);
			System.exit(9);
		}

		return clone;
	}

	/**
	 * 
	 * @param restrExp
	 * @return
	 */
	public SimpleObjectRestrictionSubAxiom getSimpleObjectRestrictionSubAxiom(
			OWLQuantifiedRestriction restrExp)
	{
		SimpleObjectRestrictionSubAxiom restSbax = new SimpleObjectRestrictionSubAxiom();

		OWLClass range = (OWLClass) restrExp.getFiller();
		OWLObjectProperty prp = (OWLObjectProperty) restrExp.getProperty();
		ArrayList<OWLClass> clsList = new ArrayList<OWLClass>();
		clsList.add(range);

		restSbax.setProperty(prp);
		restSbax.setRestrictionRange(range);
		restSbax.setClsList(clsList);

		/* TODO доделать фрагменты запроса */
		restSbax.setTitle("-not-done");
		restSbax.setSparqlVar("-not-done");
		restSbax.setSparqlQueryPart("-not-done");
		restSbax.setSubAxOWL(restrExp);

		return restSbax;
	}

	/**
	 * Делает субаксиому из выражения, не включающего объединения других
	 * выражений.
	 * 
	 * @param clExp
	 * @param ontOWL
	 * @param ontUPO
	 * @return
	 */
	public ComplexSubAxiom getComplexSubaxiom(OWLClassExpression clExp)
	{
		ComplexSubAxiom sb = new ComplexSubAxiom();

		// Выбираем именованные классы из субаксиомы
		sb.setClsList(getNamedClassFromExp(clExp));

		// Определяем привязку именнованных классов к аксиоме
		sb.setBindedClassList(getBindedClassesMap(sb.getClsList(), clExp,
				OWLont.getOWLont()));

		// Выбираем из онтологии свойства именованных классов для привязки к
		// аксиоме
		sb.setDtpPrpList(getDatatypePropertiesOfNamedClass(clExp,
				OWLont.getOWLont()));
		sb.setObjPrpList(getObjectPropertiesOfNamedClass(clExp,
				OWLont.getOWLont()));

		/*--Выбираем свойства (точнее ограничения на них), из субаксиомы при помощи визитора..*/
		/*
		 * TODO внутрь цикла можно добавить вызовы нескольких визиторов
		 * собирающих компоненты субаксиомы
		 */

		Set<OWLClassExpression> axСNSet = clExp.asConjunctSet();
		ElementCollectorVisitor objColvis = new ElementCollectorVisitor();

		for (OWLClassExpression classExp : axСNSet)
		{
			classExp.accept(objColvis);
		}
		/*
		 * TODO визитор возвращает также список ТИПИЗИРОВАННых свойств из
		 * субаксиомы но вроде этот список нигде не используется, хотя эти
		 * свойства нужно (возможно) также привязать к аксиоме
		 */
		sb.setPrpFromAxList(objColvis.getObjectPropList());
		sb.setDtpPrpList(objColvis.getDatypePropList());

		/* Удаляем дублрование свойст из простых списков */
		sb.setObjPrpList(removeDublicatesOfPropertiesFromList(
				sb.getObjPrpList(), sb.getPrpFromAxList()));
		sb.setDtpPrpList(removeDublicatesOfPropertiesFromList(
				sb.getDtpPrpList(), sb.getPrpFromAxList()));

		// title=getTitleOfSubAxiom(subAx);
		// sb.setTitle(getNewTitle(clExp, sb.getPrpFromAxList()));
		String ss = sb.generateTitle();
		sb.setTitle(sb.generateTitle());

		/* Создание заголовка */

		/*
		 * TODO левой части тут никакой не будет - надо гдето в другом месте
		 * генерить кусти запросов - может в Аксиоме
		 * sparqlVar=getSparqlVar(leftSideSubax);
		 * sparqlQueryPart=getSparqlQueryPart(clsList, ontUPO, sparqlVar);
		 */

		sb.setSparqlVar("not_done_");
		sb.setSparqlQueryPart("not_done");

		return sb;
	}

	/**
	 * Создает субаксиомы из простых типизированных ограничений: DataSome
	 * datatype DataAll datatype DataMin/Max/Exact-cardinality datatype
	 * 
	 * @param restrExp
	 * @return
	 */
	public SimpleDataRestrictionSubaxiom getSimpleDataRestrictionSubaxiom(
			OWLQuantifiedRestriction restrExp)
	{
		SimpleDataRestrictionSubaxiom restSbax = new SimpleDataRestrictionSubaxiom();

		OWLDatatype range = (OWLDatatype) restrExp.getFiller();
		OWLDataProperty prp = (OWLDataProperty) restrExp.getProperty();
		ArrayList<OWLClass> clsList = new ArrayList<OWLClass>();

		restSbax.setRangeType(range);
		restSbax.setProperty(prp);
		restSbax.setClsList(clsList);

		/* TODO доделать фрагменты запроса */
		restSbax.setTitle("-not-done");
		restSbax.setSparqlVar("-not-done");
		restSbax.setSparqlQueryPart("-not-done");
		restSbax.setSubAxOWL(restrExp);

		return restSbax;
	}

	/**
	 * Парсит аксиому - разбивает ее субаксиомы.
	 * 
	 * @param ax
	 * @return
	 */
	public ParsedAxiom getParsedAxiom(SplitAxiom ax)
	{
		ParsedAxiom parserAx = new ParsedAxiom(ax);

		ClassExpressionParserVisitor lSideParserVisitor = new ClassExpressionParserVisitor();
		ClassExpressionParserVisitor rSideParserVisitor = new ClassExpressionParserVisitor();

		/* Парсим 2 части аксиомы */
		LOGGER.info("== START - parse left side: [" + ax.leftOWLclassExpression
				+ "]");
		ax.getLeftOWLclassExpression().accept(lSideParserVisitor);
		parserAx.setlSideOfAx(lSideParserVisitor.getSbList());
		LOGGER.info("== END - parse left side: Subax quantity:"
				+ parserAx.getlSideOfAx().size());

		LOGGER.info("== START - parse right side: ["
				+ ax.rightOWLclassExpression + "]");
		ax.getRightOWLclassExpression().accept(rSideParserVisitor);
		parserAx.setrSideOfAx(rSideParserVisitor.getSbList());
		LOGGER.info("== END - parse right side: Subax quantity:"
				+ parserAx.getrSideOfAx().size());

		/*
		 * Если визитор не смог распарсить части из-за неучитывания некоторых
		 * classExpression и венул пустые списки субаксиом, то не создаем
		 * субаксиому
		 */
		if (parserAx.getrSideOfAx().isEmpty()
				|| parserAx.getlSideOfAx().isEmpty())
		{
			LOGGER.info("!!! WARN !!! Axiom {} are not parsed",
					ax.originalAxiom);
			return null;
		}

		/*
		 * Анализируем распарсенную аксиому и Вызываем (если требуется) создание
		 * более специфической аксиомы
		 */
		/*
		 * if (this.isSimpleRestrictionAxiom(parserAx)) return
		 * getSimpleRestrictionAxiom(parserAx);
		 */

		/* Определяем тип IRI между частями */
		parserAx.setRelationIRI(getSKOSRelationType(parserAx));

		return parserAx;
	}

	/**
	 * Опредляет тип SKOS-связи, которая будет образована между концептом и
	 * концептами-субаксиомами, определяющими его.
	 * 
	 * @param axType
	 * @return
	 */
	private IRI getSKOSRelationType(ParsedAxiom ax)
	{

		/*
		 * TODO Может будут еще какие-нибудь способы определения типа связи в
		 * скос. Пока возвращает только related.
		 */
		return IRI.create(ConstantsOntAPI.SKOS_RELATED);
	}

	/**
	 * Выбирает из выражения именованные классы.
	 * 
	 * @param exp
	 * @return
	 * 
	 */
	private ArrayList<OWLClass> getNamedClassFromExp(OWLClassExpression exp)
	{
		/*
		 * Из субаксиомы, составленной из классов, связанных коньюнкцией, или
		 * просто одного класса выделяем именованные классы и заносим их в
		 * список.
		 */
		ArrayList<OWLClass> listOfClasses = new ArrayList<OWLClass>();

		Set<OWLClassExpression> axСNSet = exp.asConjunctSet();
		// LOGGER.info("   ===getNamedClassFromExp==");
		for (OWLClassExpression clasExp : axСNSet)
		{
			if (!clasExp.isAnonymous())
			{
				LOGGER.info("Get class from SB: "
						+ clasExp.asOWLClass().getIRI().getFragment());
				listOfClasses.add(clasExp.asOWLClass());
			}
		}
		// LOGGER.info("   ===END-getNamedClassFromExp==");
		return listOfClasses;
	}

	/**
	 * Получаем список пар: свойство --> именованных класс. Для привязки
	 * последних к субаксиоме.
	 * 
	 * @param clsList
	 *            именованные классы из субаксиомы
	 * @return массив массивов с записями вида <IRI свойства --> IRI класса>
	 */
	private ArrayList<ArrayList<IRI>> getBindedClassesMap(
			ArrayList<OWLClass> clsList, OWLClassExpression exp, Ontology ontOWL)
	{
		ArrayList<ArrayList<IRI>> resList = new ArrayList<ArrayList<IRI>>();
		ArrayList<IRI> turple = null;
		OWLObjectProperty prp = null;

		for (OWLClass cls : clsList)
		{
			prp = defineClassRelationType(cls, exp, ontOWL);

			turple = new ArrayList<IRI>();
			turple.add(prp.getIRI());
			turple.add(cls.getIRI());

			resList.add(turple);
			LOGGER.info(" Bind internal class <" + cls.getIRI().getFragment()
					+ "> " + "by relation <" + prp.getIRI().getFragment() + ">");
		}

		return resList;
	}

	/**
	 * Определяет отношение, которым следует соединить переданный именованный
	 * класс и субаксиому, в которою он входит.
	 * 
	 * @param cls
	 * @param exp
	 * @param ontOWL
	 * @return
	 */
	private OWLObjectProperty defineClassRelationType(OWLClass cls,
			OWLClassExpression exp, Ontology ontOWL)
	{
		Boolean isFollow;
		OWLObjectProperty definedPrp = null;
		OWLObjectProperty broader = UPOont.getUPOont().df
				.getOWLObjectProperty(IRI
						.create(ConstantsOntConverter.SKOS_BROADER));
		OWLObjectProperty related = UPOont.getUPOont().df
				.getOWLObjectProperty(IRI
						.create(ConstantsOntConverter.SKOS_RELATED));

		/*
		 * ..для проверки вхождения - делаем аксиомы о вложенности и проверяем
		 * какая из них выводится взависимости от этого подбираем отношение
		 */
		OWLSubClassOfAxiom classIsBroaderThanSubaxiom = ontOWL.df
				.getOWLSubClassOfAxiom(exp, cls);
		isFollow = ontOWL.reas.isEntailed(classIsBroaderThanSubaxiom);
		if (isFollow)
		{
			definedPrp = UPOont.getUPOont().df.getOWLObjectProperty(IRI
					.create(ConstantsOntConverter.SKOS_BROADER));
		}

		OWLSubClassOfAxiom subaxiomIsBroaderThanClass = ontOWL.df
				.getOWLSubClassOfAxiom(cls, exp);
		isFollow = ontOWL.reas.isEntailed(classIsBroaderThanSubaxiom);
		if (isFollow)
		{
			definedPrp = UPOont.getUPOont().df.getOWLObjectProperty(IRI
					.create(ConstantsOntConverter.SKOS_NARROWER));
		}

		/* если отношение не определено оно будет - SKOS:related */
		definedPrp = (definedPrp == null) ? UPOont.getUPOont().df
				.getOWLObjectProperty(IRI
						.create(ConstantsOntConverter.SKOS_RELATED))
				: definedPrp;

		return definedPrp;
	}

	/**
	 * Возвращает список свойств и значений, присущих именованным классам в
	 * субаксиоме.
	 * 
	 * @param ontOWL
	 *            онтология, в которой ищутся свойства - исходная онтология
	 * @param subAx
	 *            субаксиома для анализа
	 * @return
	 */

	public ArrayList<PairOfIRI> getObjectPropertiesOfNamedClass(
			OWLClassExpression subAx, Ontology ontOWL)
	{
		ArrayList<PairOfIRI> prpList = new ArrayList<PairOfIRI>();
		Set<OWLClassExpression> axСNSet = subAx.asConjunctSet();

		LOGGER.info("   ===Get Object properties and val of Named class of SB=======================");

		for (OWLClassExpression clasExp : axСNSet)
		{// for----------------------------------------------------
			if (!clasExp.isAnonymous())
			{
				// для каждого класса получаем список его свойств с учетом
				// свойств суперклассов
				OWLClass owlClass = clasExp.asOWLClass();
				LOGGER.info("   Class:" + owlClass);

				Set<OWLObjectProperty> objPrpSet = ontOWL
						.getObjectProperiesOfClass(
								owlClass.getIRI().toString(), true);

				for (OWLObjectProperty prp : objPrpSet)
				{
					Set<OWLClass> clsRANGEset = ontOWL.getClassFromRange(prp
							.getIRI().toString());
					// формируем пару <свойство -- значение> и добавляем ее в
					// список.
					for (OWLClass classInRange : clsRANGEset)
					{
						PairOfIRI newPrp_Val = new PairOfIRI(prp.getIRI(),
								classInRange.getIRI());
						prpList.add(newPrp_Val);
						LOGGER.info("   Prp=val:" + prp + " = " + classInRange);
					}
				}
			}
		}// for----------------------------------------------------
		LOGGER.info("   ===END - Get property and val of Named class of SB===================");
		return prpList;
	}

	/**
	 * Возвращает список типизированных свойств и их типов, присущих именованным
	 * классам в субаксиоме.
	 * 
	 * @param ontOWL
	 *            онтология, в которой ищутся свойства - исходная онтология
	 * @param subAx
	 *            субаксиома для анализа
	 * @return TODO надо доделать чтобы еще типизированные свойства брались
	 */

	public ArrayList<PairOfIRI> getDatatypePropertiesOfNamedClass(
			OWLClassExpression subAx, Ontology ontOWL)
	{
		ArrayList<PairOfIRI> prpList = new ArrayList<PairOfIRI>();
		Set<OWLClassExpression> axСNSet = subAx.asConjunctSet();

		LOGGER.info("   ===Get Datatype properties and val of Named class of SB=======================");

		for (OWLClassExpression clasExp : axСNSet)
		{
			if (!clasExp.isAnonymous())
			{
				// для каждого класса получаем список его свойств с учетом
				// свойств суперклассов
				OWLClass owlClass = clasExp.asOWLClass();
				LOGGER.info("   Class:" + owlClass);

				Set<OWLDataProperty> dtpPrpSet = ontOWL
						.getDatatypesProperiesOfClass(owlClass.getIRI()
								.toString(), true);

				// для каждого свойства находим его RANGE
				for (OWLDataProperty prp : dtpPrpSet)
				{
					Set<OWLDatatype> datatypeSet = ontOWL
							.getDatatypesFromRange(prp.getIRI().toString());
					// формируем пару <свойство -- значение> и добавляем ее в
					// список.
					for (OWLDatatype datatype : datatypeSet)
					{
						PairOfIRI newPrp_Val = new PairOfIRI(prp.getIRI(),
								datatype.getIRI());
						prpList.add(newPrp_Val);
						LOGGER.info("   Prp = type:" + prp + " = " + datatype);
					}
				}
			}
		}// for
		LOGGER.info("   ===END - Get property and val of Named class of SB===================");
		return prpList;
	}

	/**
	 * Удаляет из списков свойств (от именованных классов) субаксиомы, те что
	 * уже есть в самой аксиоме.
	 * 
	 * @param dtpPrpList
	 * @return список без свойств, встретивщихся в субаксиоме.
	 */
	private ArrayList<PairOfIRI> removeDublicatesOfPropertiesFromList(
			ArrayList<PairOfIRI> prpList,
			ArrayList<PrpIRIandSubAxiom> prpFromAxList)
	{
		LOGGER.info("   === Remove Dublicates Of Properties From List============================");
		ArrayList<PairOfIRI> shortPrpList = new ArrayList<PairOfIRI>();
		shortPrpList.addAll(prpList);

		for (PrpIRIandSubAxiom prpFromSubAX : prpFromAxList)
		{
			for (PairOfIRI prpFromList : prpList)
			{
				if (prpFromSubAX.propIRI.equals(prpFromList.getFirst()))
				{
					shortPrpList.remove(prpFromList);
					LOGGER.info("   Rem: " + prpFromList.getFirst());
				}

			}
		}
		LOGGER.info("   === END - Remove Dublicates Of Properties From List============================");
		return shortPrpList;
	}

	/**
	 * Создает простую субаксиому из экземпляра ОПП
	 * 
	 * @param classIRI
	 *            IRI класса, которому соответвует экземпляр в ОПП.
	 * @param upo
	 * @return
	 */
	public SimpleSubAxiom getSimpleSubAxiom(IRI classIRI, UserPresenOnt upo)
	{
		SimpleSubAxiom sbax = new SimpleSubAxiom();

		IRI upoIndividualIRI = upo.getConceptByIRIinAnnotationValue(
				IRI.create(ConstantsOntConverter.SKOS_HIDDEN_LABEL), classIRI);

		/* проверяем существование экземпляра, соответвующего классу */
		if (upoIndividualIRI == null)
		{
			LOGGER.error(
					"!!! Error !!! Individual for class [] not found in UPO",
					classIRI);
			return null;
		}

		/* запоняем поля субаксиомы */
		OWLClass cls = upo.df.getOWLClass(classIRI);
		ArrayList<OWLClass> clsList = new ArrayList<OWLClass>(1);
		clsList.add(cls);
		sbax.setClsList(clsList);

		// TODO Подумать над лейбелами отрицания
		sbax.setSparqlQueryPart("not-done");
		sbax.setSparqlVar("not-done");
		sbax.setTitle(classIRI.getFragment());
		sbax.setSubAxIndividual(upo.df.getOWLNamedIndividual(upoIndividualIRI));

		// Добавляем использованну SPARQL перемнную в набор ОПП
		// TODO добавление пренести в метод генерации имени
		// -UPOont.getUPOont().usedSparqlVarNames.add(sbax.getSparqlVar());
		return sbax;
	}

	/**
	 * Создает субаксиому-отрицание одного именованнго класса.
	 * 
	 * @param classIRI
	 * @param upo
	 * @return
	 */
	public SimpleComplementSubAxiom getSimpleComplementSubAxiom(IRI classIRI,
			UserPresenOnt upo)
	{
		/* Создаем субаксиому отрицание на основе простой субаксиомы */
		SimpleComplementSubAxiom sbax = new SimpleComplementSubAxiom(
				this.getSimpleSubAxiom(classIRI, upo));

		/*
		 * Заполняем пустыми списками поля (субаксиома простая поэтому
		 * большинство списков пустые)
		 */
		sbax.setBindedClassList(new ArrayList<ArrayList<IRI>>());
		sbax.setDtpPrpList(new ArrayList<PairOfIRI>());
		sbax.setObjPrpList(new ArrayList<PairOfIRI>());
		sbax.setPrpFromAxList(new ArrayList<PrpIRIandSubAxiom>());

		// TODO скорректировать заголовки
		sbax.setSparqlQueryPart("not-done");
		sbax.setSparqlVar("not-done");
		sbax.setTitle(sbax.generateTitle());

		// Добавляем использованну SPARQL перемнную в набор ОПП
		// TODO в методе геперации добавлять надо
		// UPOont.getUPOont().usedSparqlVarNames.add(sbax.getSparqlVar());
		return sbax;

	}

	/**
	 * Создает субаксиому - отрицание сложного выражения.
	 * 
	 * @param operandExp
	 * @param upo
	 * @return
	 */
	public ComplexComplementSubAxiom getComplexComplementSubAxiom(
			OWLClassExpression operandExp, UserPresenOnt upo)
	{
		ComplexComplementSubAxiom sbax = new ComplexComplementSubAxiom(
				this.getComplexSubaxiom(operandExp));

		// TODO Назначить лейблы
		sbax.setSparqlQueryPart("not-done");
		sbax.setSparqlVar("not-done");

		String tempTitle = null;
		for (OWLClass cls : operandExp.getClassesInSignature())
			tempTitle = tempTitle + "-" + Ontology.getName(cls);
		sbax.setTitle("[" + ConstantsOntConverter.UPO_NEGATION_LABEL_TEMPLATE
				+ tempTitle + "]");

		return sbax;
	}

}
