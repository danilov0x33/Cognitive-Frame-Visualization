/**
 * 
 */
package ru.iimm.ontology.ontAPI;




import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;

//import org.coode.owlapi.owlxml.renderer.



import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

 
/**
 * Класс, представляющий SKOS онтологию.
 * @param параметры
 * @return параметры
 * thesaurusAPI.SkosOnt.java
 * @author Lomov P. A.
 * @version 0
 */
public class SkosOnt extends Ontology implements ConstantsOntAPI
{
	
	/**
	 * Карта: ключ - целевой концепт, значение - концепты, которые шире целевого.
	 * Используется для кэширования рез-в метода getBroader/NarrowerConcepts
	 */
	private HashMap<OWLNamedIndividual, HashSet<OWLNamedIndividual>> broaderConceptMap;

	/**
	 * Карта: ключ - целевой концепт, значение - концепты, 
	 * которые уже (специфичнее) целевого.
	 * Используется для кэширования рез-в метода getBroader/NarrowerConcepts
	 */
	private HashMap<OWLNamedIndividual, HashSet<OWLNamedIndividual>> narrowerConceptMap;
	
	
	protected SkosOnt()
	{
		super();
		this.broaderConceptMap = new HashMap<OWLNamedIndividual, HashSet<OWLNamedIndividual>>();
		this.narrowerConceptMap = new HashMap<OWLNamedIndividual, HashSet<OWLNamedIndividual>>();
	}
	
	
	/**
	 * Создает SKOS онтологию. Следует использовать, если SKOS онтология будет 
	 * загружаться из одного файла.
	 * @param параметры
	 * @return параметры
	 * @author Lomov P. A.
	 * @version 0
	 */	 
	 public SkosOnt(IRI skosIRI, String filePath)
	 {			
		 super(skosIRI, filePath);
		 this.broaderConceptMap = new HashMap<OWLNamedIndividual, HashSet<OWLNamedIndividual>>();
		 this.narrowerConceptMap = new HashMap<OWLNamedIndividual, HashSet<OWLNamedIndividual>>();

	 }

	/**
	 * Создает SKOS онтологию. Следует использовать, если SKOS 
	 * онтология будет загружаться из несколких файлов.
	 * @param параметры
	 * @return параметры
	 * @author Lomov P. A.
	 * @version 0
	 */	 
	 public SkosOnt(String dir, String fileName, Boolean mergeImportedOntology,  Boolean makeConsitnenseCheck)
	 {			
		 super(dir, fileName, mergeImportedOntology, makeConsitnenseCheck);
		 this.broaderConceptMap = new HashMap<OWLNamedIndividual, HashSet<OWLNamedIndividual>>();
		 this.narrowerConceptMap = new HashMap<OWLNamedIndividual, HashSet<OWLNamedIndividual>>();
		 
	 }
	 
	  

	 private void addHierarchy(Ontology ont )
	 {		 
	 }
	
	 /**
	 * Добавляет объект, как гипоним (боллее конкретный) другого объекта.
	 * Если он уже есть - создает связь гипонимии и возвращает ссылку на него.
	 * Если связь повторная, то она не будет дублироваться.
	 * @param newObjIRI
	 * @param giperonymObjIRI
	 * @return
	 */
	public OWLNamedIndividual addObjectAsGyponym(String newObjIRI, String giperonymObjIRI)
	{
		// Создаем концепт тезауруса
		OWLNamedIndividual newObj = addConcept(newObjIRI);
		
		//Берем его будущий объект-гипероним
		OWLNamedIndividual giperObj = df.getOWLNamedIndividual(IRI.create(giperonymObjIRI));
		
		//Берем связь broader - гипонимии из тезауруса
		OWLObjectProperty hasBroader = df.getOWLObjectProperty(IRI.create(SKOS_BROADER));
		
		//Связываем гипонимией концепт с объектом
		setObjectPrpBetween(newObj, hasBroader, giperObj);
		
		return newObj;
		
	}
	
	/**
	 * Добавляет SKOS свойства в SKOS онтологию
	 * @param prpIRI
	 * @return
	 * @todo надо доделать связывание СВОЙСТВА с ОБЪЕКТАМИ
	 * @todo check
	 */
	/*
	public OWLNamedIndividual addObjProperty(String prpIRI, String domainObj, String rangeObj)
	{
		// Добавляем свойство, как гипоним главного СВОЙСТВА
		OWLNamedIndividual ind = addObjectAsGyponym(prpIRI, ths_skos_Property);
		// Связываем свойство с объектом-доменом
		
		// Связываем свойство с объектом-диапазоном
		return ind;
	}
	*/
	
	
	 
	/**
	 * Добавляет корневой объект тезауруса, непосредственным гипонимом которого является
	 * топовый концепт - Object. Если он там уже существует, то возвращаем ссылку на него. 
	 * @param objIRI
	 */
	/*
	public OWLNamedIndividual addRootObject(String objIRI)
	{
		// Создаем концепт тезауруса
		OWLNamedIndividual newObj = addConcept(objIRI);
		
		//Берем гипероним всех объектов тезауруса - Object
		OWLNamedIndividual object = df.getOWLNamedIndividual(IRI.create(ths_skos_Object));
		
		//Берем связь broader - гипонимии из тезауруса
		OWLObjectProperty hasBroader = df.getOWLObjectProperty(IRI.create(SKOS_BROADER));
		
		//Связываем гипонимией концепт с объектом
		setObjectPrpBetween(newObj, hasBroader, object);
		
		return newObj;
	}
*/
	/**
	 * Добавляет коллекцию значений или атомарную коллекцию в тезаурус, 
	 * как член наибольшей коллекции - value.
	 * @param valIRI
	 * @return
	 */
	/*
	public OWLNamedIndividual addValue(String valIRI)
	{
		// Создаем SKOS коллекцию в виде экземпляра класса Collection.
		OWLClass collectionClass = df.getOWLClass(IRI.create(SKOS_COLLECTION));
		OWLNamedIndividual newCol = addInd(collectionClass,valIRI);
				
		//Берем гипероним всех значений-коллекций - VAlue
		OWLNamedIndividual value = df.getOWLNamedIndividual(IRI.create(ths_skos_prpValue));
		
		//Берем связь членства из тезауруса
		OWLObjectProperty hasMemeber = df.getOWLObjectProperty(IRI.create(SKOS_HAS_MEMBER));
		
		//Связываем глобальную коллекцию с мелкой
		setObjectPrpBetween(value, hasMemeber, newCol);
		
		return newCol;
	}
	*/

	/**
	 * Определяет наличие явного отношения между экземплярами тезауруса.
	 * Отношение существует, если определено хотя бы в одну сторону.
	 * @param indIRI
	 * @param prpIRI
	 * @param ind2IRI
	 * @return
	 */
	/*
	public boolean isRelationExist(String indIRI, String prpIRI , String ind2IRI)
	{
		OWLNamedIndividual ind = df.getOWLNamedIndividual(IRI.create(indIRI));
		OWLNamedIndividual ind2 = df.getOWLNamedIndividual(IRI.create(ind2IRI));		
		OWLObjectProperty prp = df.getOWLObjectProperty(IRI.create(prpIRI));
		
		return isRelationExist(prp, ind, ind2);
	}
	*/
	
	/**
	 * Добавляет новый объект и делает его синонимом уже существующего.
	 * Метод не используйте в отношении объектов, т.к. объект может быть добавлен только как 
	 * гипоним другого для сохранения строгой иерархии -
	 * в этом случае используйте makeSynonymOf для этой цели.
	 */
	/*
	public OWLObjectProperty addSynonymOf(String newObjIRI, String exsObjIRI, int weight)
	{
		// Получаем объекты 
		OWLNamedIndividual ind = df.getOWLNamedIndividual(IRI.create(newObjIRI));
		OWLNamedIndividual exsInd = df.getOWLNamedIndividual(IRI.create(exsObjIRI));

		//Создаем IRI - нового объекта-синонима в онтологии
		//IRI (если уже такой есть в тезаурусе) будет в виде: IRI исходного + "-" + число
		//т.к. уже могут быть другие объекты - то ищем свободный IRI
		int i=0;
		while (isExistInOntology(ind));
		{						
			IRI iriInd = IRI.create(newObjIRI+"-"+i++);
			ind= df.getOWLNamedIndividual(iriInd);			
		}
		
		// Добавляем будущий объект-синоним
		addConcept(ind.getIRI().toString());
		
		// Проверяем существование второго элемента		
		if (!(isExistInOntology(exsInd)))
		{
			System.out.println("!!! No twin, existed in thesaurus: " + exsObjIRI); 
			System.exit(9);
		}
		
		// Берем связь синонимии из тезауруса
		// OWLObjectProperty synonymOf = df.getOWLObjectProperty(IRI.create(ths_skos_synonymOf));
		
		// Создаем новое подсвойство свойства синонимия в тезаурусе
		OWLObjectProperty newSynOf = addObjPrpAsSubprp(ths_skos_synonymOf, ths_skos_synonymOf , true);
		
		// Создаем связь
		setObjectPrpBetween(ind, newSynOf, exsInd);		
		
		// Указываем вес связи
		Integer w = new Integer(weight);
		OWLLiteral txtLit = df.getOWLStringLiteral(w.toString());
		
		addAnnotation(ths_skos_hasWeight, txtLit,newSynOf);
		
		System.out.println("Make synonyn" + ind.getIRI()+	 " of existed: "+exsObjIRI);			
		return newSynOf;
	}
	*/
	
	
	/**
	 * Создаем связь синонимии между двумя уже существующими элементами. Синонимия 
	 * задается следующим образом:
	 * - создается связь синонимии synonum-of между экземплярами
	 * - создается дополнительная связь синонимии synonum-of-XX, которая
	 * является подсвойством synonum-of и на нее лейблом накладывается вес.
	 * Это вызвано тем, что вес должен быть уникальным у каждой 
	 * связи двух объектов, а связь синонимии задана только одна. 
	 * @param newObjIRI
	 * @param exsObjIRI
	 * @param weight
	 * @return OWLObjectProperty
	 * @todo check
	 */
	/*
	public OWLObjectProperty makeSynonymOf(String newObjIRI, String exsObjIRI, 
											int weight)
	{	
		// Создаем обычную связь синонимии между элементами 
		if (makeObjPrpOBetweenElements(newObjIRI, ths_skos_synonymOf, 
				exsObjIRI, true) == null)
			{
				System.out.println("!!! Link Synonum EXISTED: "+newObjIRI+
						" & " +exsObjIRI);
				return null;
			};
			
			
		// Создаем новое подсвойство свойства синонимия в тезаурусе		
		// @todo проверить - правиль ли генериься IRI
		OWLObjectProperty newSynOf = addObjPrpAsSubprp(ths_skos_synonymOf, ths_skos_synonymOf, true);

		// Получаем объекты 
		OWLNamedIndividual ind = df.getOWLNamedIndividual(IRI.create(newObjIRI));
		OWLNamedIndividual exsInd = df.getOWLNamedIndividual(IRI.create(exsObjIRI));
		
		// Создаем связь синонимии с весом, как гипонимии связи синонимии без веса
		OWLObjectPropertyAssertionAxiom asrt = setObjectPrpBetween(ind, newSynOf, exsInd);		
		
		// Указываем вес связи
		Integer w = new Integer(weight);
		OWLLiteral txtLit = df.getOWLStringLiteral(w.toString());
		addAnnotation(ths_skos_hasWeight, txtLit,newSynOf);
		
		System.out.println("Make synonum: " + ind.getIRI()+	 "  with : "+exsObjIRI);
		
		return newSynOf;
	}
	*/
	
	
	/**
	 * Добавляет SKOS-концепт (объект или свойство) в SKOS-онтологию.
	 * Если он уже есть возвращает ссылку на него.  
	 * @param conceptIRI
	 * @return
	 */
	public OWLNamedIndividual addConcept(String conceptIRI)
	{
		//conceptID - служебное имя элемента тезауруса
		//берем класс CONCEPT + создаем экземпляр
		//связываем экземпляр и класс аксиомой 
		OWLClass conceptClass = df.getOWLClass(IRI.create(SKOS_CONCEPT));
		OWLNamedIndividual conInd = addInd(conceptClass,conceptIRI);
		return conInd;
	}
	

	/**
	 * Делает одну коллекцию или SKOS-концепт членом другой коллекции.
	 * @param memberIRI
	 * @param bigColIRI
	 * @return
	 */
	public OWLObjectPropertyAssertionAxiom makeMemberOfCollection(String memberIRI,
																	String bigColIRI)
	{
		return makeObjPrpOBetweenElements(bigColIRI, SKOS_HAS_MEMBER, memberIRI, false);
	}
	
	/**
	 * Создает объектную связь между элементами SKOS (концептами, коллекциями).
	 * @param firstElIRI
	 * @param prpIRI
	 * @param secondElIRI
	 * @param checkIfLinkExisted при true проверяет существует ли связь в ту или 
	 * другую сторону между элементами.
	 * @return
	 */
	public OWLObjectPropertyAssertionAxiom makeObjPrpOBetweenElements(String firstElIRI, 
			String prpIRI ,String secondElIRI, boolean checkIfLinkExisted)
	{
	
		return makeObjPrpOBetweenElements(
							IRI.create(firstElIRI), 
								IRI.create(prpIRI), 
									IRI.create(secondElIRI), 
										checkIfLinkExisted);
	}

	/**
	 * Создает объектную связь между элементами SKOS (концептами, коллекциями).
	 * @param firstElIRI
	 * @param prpIRI
	 * @param secondElIRI
	 * @param checkIfLinkExisted при true проверяет существует ли связь в ту или 
	 * другую сторону между элементами.
	 * @return
	 */
	public OWLObjectPropertyAssertionAxiom makeObjPrpOBetweenElements(IRI firstElIRI, IRI prpIRI, IRI secondElIRI, boolean checkIfLinkExisted)
	{
		// Получаем элементы 
		OWLNamedIndividual firstInd = df.getOWLNamedIndividual(firstElIRI);
		OWLNamedIndividual seconInd = df.getOWLNamedIndividual(secondElIRI);

		// Проверяем существование связываемых элементов		
		if (  !(isExistInOntology(firstInd)) | !(isExistInOntology(seconInd)))
		{
			System.out.println("!!! ERROR - Some elements not existed in Ontology: "); 
			System.out.println("!!! " + firstElIRI + " - " + isExistInOntology(firstInd));
			System.out.println("!!! " + secondElIRI + " - " + isExistInOntology(seconInd));
			System.exit(9);
		}
		
		// Берем связь по указанному IRI
		OWLObjectProperty objPrp = df.getOWLObjectProperty(prpIRI);
		
		if (checkIfLinkExisted)
			// Проверяем наличие hasValue между объектами
			// в одну или другую сторону
			if (isRelationExist(objPrp, firstInd, seconInd))
				{
				System.out.println("WRN: hasValue link existed: " + firstElIRI +	
						"  with : "+secondElIRI);
				return null;
				}
		
		// Создаем связь 
		OWLObjectPropertyAssertionAxiom asrt1 = setObjectPrpBetween(firstInd, objPrp, seconInd);				
	
//		System.out.println("Make relation: "+ Thesaurus.getNameFromIRI(firstElIRI) + 
//					" "+ Thesaurus.getNameFromIRI(prpIRI)+ 
//					" "+ Thesaurus.getNameFromIRI(secondElIRI));		
		return asrt1;
	}
	
	

	

	/**
	 * Возвращает IRI соответствующего элемента из OWL модуля.
	 * @param ent
	 * @return IRI,
	 * 		   null если ссылка на близнеца отсутствует.
	 * @deprecated
	 */
	
	public IRI getOWLTwinIRI(OWLEntity ent)
	{		
		//String strIRI = getAnnotationValue(ent, IRI.create(owl_hasSKOSTwin) );
		String strIRI ="vvvv";// getAnnotationValue(ent, IRI.create(skos_hasOWLTwin) );
		
		if (strIRI.compareTo(EMPTY)==0) 
		{
			System.out.println("!!! Warning in getOWLTwinIRI:" +
					"\n Entity: " + ent + "\n hasnt twin in OWL (" +
					strIRI+")"+"\n!!! =============================");
		}
		
		return strIRI.compareTo(EMPTY)==0? null: IRI.create(strIRI);   
		//IRI.create(getAnnotationValue(ent, IRI.create(owl_hasSKOSTwin)) );
	}
	
	
	/**
	 * Устанавливает скрытый лейбел SKOS концепту.
	 * @param concept
	 * @param labelValue
	 */
	public void setHiddenLabel(OWLIndividual concept, String labelValue, String lang)
	{		
		addAnnotation(SKOS_HIDDEN_LABEL, this.df.getOWLStringLiteral(labelValue, lang), (OWLEntity) concept);
	}

	/**
	 * Устанавливает видимый лейбел SKOS концепту.
	 * @param concept
	 * @param labelValue
	 */
	public void setPrefferedLabel(OWLIndividual concept, String labelValue, String lang)
	{
		addAnnotation(SKOS_PREF_LABEL, this.df.getOWLStringLiteral(labelValue, lang), (OWLEntity) concept);
	}
	
	/**
	 * Возвращает IRI skos концептов, которые являются прямыми гипонимами
	 * указанного.
	 * @param indIRI
	 * @return массив IRI
	 */
	public ArrayList<IRI> getNarrowerConcepts(IRI indIRI)
	{
		ArrayList<IRI> list = new ArrayList<IRI>();
		/* Добавляем экземпляры, которые уже (narrower) чем указанный */
		list.addAll(this.getValueOfprp(indIRI, IRI.create(SKOS_NARROWER), true));
		
		/* Добавляем экземпляры, для которых указанный шире(broader) */
		list.addAll(this.getValueOfprp(indIRI, IRI.create(SKOS_BROADER), false));
		
		return list;
	}
	
	/**
	 * Возвращает концепты, которые шире указанного.
	 * @param targetConcept
	 * @param directOnly вернуть только непосредственные
	 * @param useCache использовать по-возможности рез-ты из кэша
	 * @return
	 */
	public HashSet<OWLNamedIndividual> getBroaderConcepts(OWLNamedIndividual targetConcept, 
			boolean directOnly, boolean useCache)
	{

		if (useCache && this.broaderConceptMap.containsKey(targetConcept))
		{
			return this.broaderConceptMap.get(targetConcept); 
		}
		
		HashSet<OWLNamedIndividual> prevSet = new HashSet<OWLNamedIndividual>();
		HashSet<OWLNamedIndividual> nextSet = new HashSet<OWLNamedIndividual>();
		HashSet<OWLNamedIndividual> resultSet = new HashSet<OWLNamedIndividual>();
		
		OWLObjectProperty broaderPrp = this.df.getOWLObjectProperty(IRI.create(SKOS_BROADER));
		OWLObjectProperty narrowerPrp = this.df.getOWLObjectProperty(IRI.create(SKOS_NARROWER));
		
		prevSet.add(targetConcept);
		
		/**
		 * На каждом витке цикла в набор resultSet собираются концепты следующего 
		 * уровня. Цикл пройдет хотя бы один раз и завершиться если на новом уровне нет ничего
		 * или дальнейшие уровни рассматривать не нужно.
		 */
		do
		{
			nextSet.clear();
			for (OWLNamedIndividual concept : prevSet)
			{
				/* Добавляем экземпляры, которые шире (broader) чем указанный 
				 * [ concept --broader--> ??? ]*/
				nextSet.addAll(this.getValueOfprp(concept, broaderPrp, true));
				
				/* Добавляем экземпляры, для которых указанный уже (narrower)
				 * [ ??? --narrower--> concept ] */
				nextSet.addAll(this.getValueOfprp(concept, narrowerPrp, false));
			}
			prevSet.clear();
			prevSet.addAll(nextSet);
			resultSet.addAll(nextSet);
		}
		while (directOnly==false & nextSet.size()>0);
		
		/*Записываем к кэш*/
		this.broaderConceptMap.put(targetConcept, resultSet);
		
		return resultSet;
	}
	
	/**
	 * Возвращает концепты, которые уже указанного.
	 * @param targetConcept
	 * @param directOnly вернуть только непосредственные
	 * @param useCache useCache использовать по-возможности рез-ты из кэша
	 * @return
	 */
	public HashSet<OWLNamedIndividual> getNarrowerConcepts(OWLNamedIndividual targetConcept, 
			boolean directOnly, boolean useCache)
	{
		if (useCache && this.narrowerConceptMap.containsKey(targetConcept))
		{
			return this.narrowerConceptMap.get(targetConcept); 
		}
		
		HashSet<OWLNamedIndividual> prevSet = new HashSet<OWLNamedIndividual>();
		HashSet<OWLNamedIndividual> nextSet = new HashSet<OWLNamedIndividual>();
		HashSet<OWLNamedIndividual> resultSet = new HashSet<OWLNamedIndividual>();
		
		OWLObjectProperty broaderPrp = this.df.getOWLObjectProperty(IRI.create(SKOS_BROADER));
		OWLObjectProperty narrowerPrp = this.df.getOWLObjectProperty(IRI.create(SKOS_NARROWER));
		
		prevSet.add(targetConcept);
		
		/**
		 * На каждом витке цикла в набор resultSet собираются концепты следующего 
		 * уровня. Цикл пройдет хотя бы один раз и завершиться если на ноыом уроыне нет ничего
		 * или дальнейшие кровни рассматривать не нужно.
		 */
		do
		{
			nextSet.clear();
			for (OWLNamedIndividual concept : prevSet)
			{
				/* Добавляем экземпляры, для которых указанный шире (broader) 
				 * [ ??? --broader--> concept ]*/
				nextSet.addAll(this.getValueOfprp(concept, broaderPrp, false));
				
				/* Добавляем экземпляры, для которые уже (narrower) указанного 
				 * [ concept --narrower--> ??? ] */
				nextSet.addAll(this.getValueOfprp(concept, narrowerPrp, true));
			}
			prevSet.clear();
			prevSet.addAll(nextSet);
			resultSet.addAll(nextSet);
		}
		while (directOnly==false & nextSet.size()>0);
		
		/*Записываем к кэш*/
		this.narrowerConceptMap.put(targetConcept, resultSet);
		
		return resultSet;
	}

	
}