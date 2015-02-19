/**
 * 
 */
package ru.iimm.ontology.cftools;

import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;
import ru.iimm.ontology.ontAPI.ConstantsOntAPI;


/**
 * @author Lomov P.A.
 *
 */
public interface ConstantCFTools 
{

	public static final String CFRAME_ONT_IRI = "http://www.iimm.ru/ont-library/Congnitive-frame.owl";
	public static final String CFRAME_ONT_IRI_BASE = CFRAME_ONT_IRI + "#";
	
	
///////////////////////////////////////////////////////////////////////////
//// IRI элементов и их шаблонов ОПП, для которых не строятся когнитивные фреймы
//////////////////////////////////////////////////////////////////////////	
// TODO Как хранить список IRI сущностей, для которых КФ не создаются.	
	
////////////////////////////////////////////////////////////////////////////	
/////// IRI основных классов ОПП ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////	
	public static final String CFRAME_ONT_IRI_CFRAME_CONTENT = CFRAME_ONT_IRI_BASE + "CFrame-content";
	public static final String CFRAME_ONT_IRI_BRANCH = CFRAME_ONT_IRI_BASE +"Branch";
	public static final String CFRAME_ONT_IRI_CFRAME = CFRAME_ONT_IRI_BASE +"Cognitive-frame";
	public static final String CFRAME_ONT_IRI_VISWAY = CFRAME_ONT_IRI_BASE +"Visualization-way";

	
	public static final String CFRAME_ONT_IRI_TAXONOMY_CFRAME = CFRAME_ONT_IRI_BASE +"Taxnonomy-CFrame";
	public static final String CFRAME_ONT_IRI_DEPENDENCY_CFRAME = CFRAME_ONT_IRI_BASE +"Dependency-CFrame";
	public static final String CFRAME_ONT_IRI_PARTONOMY_CFRAME = CFRAME_ONT_IRI_BASE +"Partonomy-CFrame";
	public static final String CFRAME_ONT_IRI_SPECIAL_CFRAME = CFRAME_ONT_IRI_BASE +"Special-CFrame";
	
	public static final String CFRAME_ONT_OBJPRP_HASCOMPONENT = CFRAME_ONT_IRI_BASE +"hasCFrameComponent";
	public static final String CFRAME_ONT_OBJPRP_HASCONTENT = CFRAME_ONT_IRI_BASE +"hasContent";
	public static final String CFRAME_ONT_OBJPRP_HASVISWAY = CFRAME_ONT_IRI_BASE +"hasVisualizationWay";
	public static final String CFRAME_ONT_OBJPRP_HAS_OBJ_CONCEPT = CFRAME_ONT_IRI_BASE +"hasObjectConcept";
	public static final String CFRAME_ONT_OBJPRP_HAS_SUBJ_CONCEPT = CFRAME_ONT_IRI_BASE +"hasSubjectConcept";
	public static final String CFRAME_ONT_OBJPRP_HAS_TRG_CONCEPT = CFRAME_ONT_IRI_BASE +"hasTargetConcept";
	public static final String CFRAME_ONT_DTPPRP_HAS_BRANCH_PRP = CFRAME_ONT_IRI_BASE +"hasBranchProperty";
	public static final String CFRAME_ONT_OBJPRP_HAS_BRANCH = CFRAME_ONT_IRI_BASE +"hasBranch";
	
//////////////////////////////////////////////////////////////////////////
////// Шаблоны имен для создаваемых экземпляров, соответвующих 
////// к-фреймам и их компонентам	
//////////////////////////////////////////////////////////////////////////

	//TODO дописать javadoc аннотации к константам
	/**
	 * Шаблон для содержимого к-фрейма.
	 * Имя = шаблон-имяПонятия-номер
	 */
	public static final String CFRAME_ONT_TEMPL_CFRAME_CONTENT = CFRAME_ONT_IRI_BASE + "cf-content-";
	
	public static final String TEMPL_CFRAME_BASE = CFRAME_ONT_IRI_BASE + "cframe-";
	/**
	 * Шаблон для к-фрейма.
	 * Имя = шаблон-короткий_IRI_онтологии-имяПонятия-номер. 
	 */
	public static final String CFRAME_ONT_TEMPL_TAXONOMY_CFRAME = TEMPL_CFRAME_BASE+"tax-"  ;
	public static final String CFRAME_ONT_TEMPL_DEPENDENCY_CFRAME = TEMPL_CFRAME_BASE+"dep-";
	public static final String CFRAME_ONT_TEMPL_PARTONOMY_CFRAME =  TEMPL_CFRAME_BASE+"part-";
	public static final String CFRAME_ONT_TEMPL_SPECIAL_CFRAME = TEMPL_CFRAME_BASE+"spec-";

	/**
	 * Шаблон для дуги.
	 * имя = шаблон + номер	
	 */
	public static final String CFRAME_ONT_TEMPL_BRANCH = "branch-";
	
	/**
	 * Шаблон для способа визуализации.
	 * имя = шаблон + номер	
	 */
	public static final String CFRAME_ONT_TEMPL_VISWAY = CFRAME_ONT_IRI_BASE + "vis-way-";

 /////////////////////////////////////////////////////////////////////
	
	
	
	
	
}
