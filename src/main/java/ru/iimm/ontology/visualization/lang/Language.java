package ru.iimm.ontology.visualization.lang;

import javax.swing.Icon;

import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;

/**
 * Язык по умолчанию.
 * @author Danilov E.Y.
 *
 */
public abstract class Language
{
	
	/**Инициализация языка.*/
	public abstract void initializeLang();
	
	public static String NAME_INIT_LANG = "Default";
	//=====================MENU BAR========================
	public static String MENU_FILE = "File";
	public static String MENU_ITEM_EXIT = "Exit";
	public static String MENU_ITEM_LOAD_ONT = "Load Ontology...";
	public static String MENU_ITEM_SETTING = "Setting";
	public static String MENU_ITEM_WINDOW = "Window";
	public static String MENU_ITEM_VISUALIZATIONS = "Visualizations";
	public static String MENU_ITEM_CONSOLE = "Console";
	//=====================BUTTONS=========================
	public static String BUTTON_SETTING_ONTOLOGY = "Setting Ontology";
	public static String BUTTON_SETTING_VIS = "Setting Visualization";
	public static String BUTTON_OK = "OK";
	public static String BUTTON_CANCEL = "Cancel";
	
	//=====================LABELS==========================
	public static String LABEL_CONVERT_OWL_ONT_TO_COGNITIVE_FRAME = "Convert OWL Ontology to CFrame";
	public static String LABEL_GET_COGNITIVE_FRAME_VISUALIZATION = "Get Cognitive frame visualization";
	public static String LABEL_ADD_IN_UPO_COGNITIVE_FRAME = "Add in UPO CFrame";
	public static String LABEL_LOAD_UPO_WITH_COGNITIVE_FRAME = "Load UPO with CFrame";
	public static String LABEL_LOAD_EMPTY_ONTOLOGY = "Load empty ontology";
	public static String LABEL_COGNITIVE_FRAME_CLASS = "Cognitive Frame Class List";
	public static String LABEL_PROTEGE_OWLCLASS_LIST = "Protege OWLClass List";
	public static String LABEL_OWLCLASS_LIST = "OWLClass List";
	public static String LABEL_HIGTH_QUALITY = "Higth quality";
	public static String LABEL_FILTER_IRI = "Filter IRI";
	public static String LABEL_LOADING = "Loading...";
	public static String LABEL_SELECT_LANGUAGE = "Select language";
	public static String LABEL_COGNITIVE_FRAME_VISUALIZATION = "Cognitive Frame Visualization";
	public static String LABEL_FILE_OWL_ONTOLOGY = "OWL Ontology";
	public static String LABEL_FILE_UPO = "User Presentation Ontology";
	public static String LABEL_FILE_CF_ONTOLOGY = "Сognitive Frames Ontology";
	
	//=====================TITLE==========================
	public static String TITLE_MAIN_FRAME = LABEL_COGNITIVE_FRAME_VISUALIZATION;
	public static String TITLE_SETTING_FRAME = "Setting";
	public static String TITLE_LOAD_ONTOLOGY_DIALOG= "Load Ontology";
	
	//=====================OTHER===========================
	public static String UPO_LANG = ConstantsOntConverter.LANG_EN;
	public static String COGNITIVE_FRAME = "Cognitive Frame";
	public static String TAXONOMY_COGNITIVE_FRAME = "Taxonomy cognitive frame";
	public static String PARTONOMY_COGNITIVE_FRAME = "Partonomy cognitive frame";
	public static String DEPENDENCY_COGNITIVE_FRAME = "Dependency cognitive frame";
	public static String SPECIAL_COGNITIVE_FRAME = "Special cognitive frame";
	public static String MESSAGE_NOT_FOUND_FILE = "Not loading UPO ontology!";
	
	//====================REDUCTION========================
	public static String REDUCTION_COGNITIVE_FRAME = "CFrame";
	public static String REDUCTION_TAXONOMY_COGNITIVE_FRAME = "Taxonomy CFrame";
	public static String REDUCTION_PARTONOMY_COGNITIVE_FRAME = "Partonomy CFrame";
	public static String REDUCTION_DEPENDENCY_COGNITIVE_FRAME = "Dependenty CFrame";
	public static String REDUCTION_SPECIAL_COGNITIVE_FRAME = "Special CFrame";
	
	
	
}
