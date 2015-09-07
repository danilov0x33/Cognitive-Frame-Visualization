package ru.iimm.ontology.visualization.lang;

import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;

/**
 * Язык по умолчанию (English).
 * @author Danilov E.Y.
 *
 */
public class LanguageEN extends Language
{
	public void initializeLang()
	{
		Language.NAME_INIT_LANG = "English";
		
		//=====================MENU BAR========================
		Language.MENU_FILE = "File";
		Language.MENU_ITEM_EXIT = "Exit";
		Language.MENU_ITEM_LOAD_ONT = "Load Ontology...";
		Language.MENU_ITEM_SETTING = "Setting";
		//=====================BUTTONS=========================
		Language.BUTTON_SETTING_ONTOLOGY = "Setting Ontology";
		Language.BUTTON_SETTING_VIS = "Setting Visualization";
		Language.BUTTON_OK = "OK";
		Language.BUTTON_CANCEL = "Cancel";
		
		//=====================LABELS==========================
		Language.LABEL_CONVERT_OWL_ONT_TO_COGNITIVE_FRAME = "Convert OWL Ontology to CFrame";
		Language.LABEL_GET_COGNITIVE_FRAME_VISUALIZATION = "Get Cognitive frame visualization";
		Language.LABEL_ADD_IN_UPO_COGNITIVE_FRAME = "Add in UPO CFrame";
		Language.LABEL_LOAD_UPO_WITH_COGNITIVE_FRAME = "Load UPO with CFrame";
		Language.LABEL_LOAD_EMPTY_ONTOLOGY = "Load empty ontology";
		Language.LABEL_COGNITIVE_FRAME_CLASS = "Cognitive Frame Class List";
		Language.LABEL_PROTEGE_OWLCLASS_LIST = "Protege OWLClass List";
		Language.LABEL_OWLCLASS_LIST = "OWLClass List";
		Language.LABEL_HIGTH_QUALITY = "Higth quality";
		Language.LABEL_FILTER_IRI = "Filter IRI";
		Language.LABEL_LOADING = "Loading...";
		Language.LABEL_SELECT_LANGUAGE = "Select language";
		Language.LABEL_COGNITIVE_FRAME_VISUALIZATION = "Cognitive Frame Visualization";
		Language.LABEL_FILE_OWL_ONTOLOGY = "OWL Ontology";
		Language.LABEL_FILE_UPO = "User Presentation Ontology";
		Language.LABEL_FILE_CF_ONTOLOGY = "Сognitive Frames Ontology";
		
		//=====================TITLE==========================
		Language.TITLE_MAIN_FRAME = LABEL_COGNITIVE_FRAME_VISUALIZATION;
		Language.TITLE_SETTING_FRAME = "Setting";
		Language.TITLE_LOAD_ONTOLOGY_DIALOG= "Load Ontology";
		
		//=====================OTHER===========================
		Language.UPO_LANG = ConstantsOntConverter.LANG_EN;
		Language.COGNITIVE_FRAME = "Cognitive Frame";
		Language.TAXONOMY_COGNITIVE_FRAME = "Taxonomy cognitive frame";
		Language.PARTONOMY_COGNITIVE_FRAME = "Partonomy cognitive frame";
		Language.DEPENDENCY_COGNITIVE_FRAME = "Dependency cognitive frame";
		Language.SPECIAL_COGNITIVE_FRAME = "Special cognitive frame";
		Language.MESSAGE_NOT_FOUND_FILE = "Not loading UPO ontology!";
		
		//====================REDUCTION========================
		Language.REDUCTION_COGNITIVE_FRAME = "CFrame";
		Language.REDUCTION_TAXONOMY_COGNITIVE_FRAME = "Taxonomy CFrame";
		Language.REDUCTION_PARTONOMY_COGNITIVE_FRAME = "Partonomy CFrame";
		Language.REDUCTION_DEPENDENCY_COGNITIVE_FRAME = "Dependenty CFrame";
		Language.REDUCTION_SPECIAL_COGNITIVE_FRAME = "Special CFrame";
	}
}
