package ru.iimm.ontology.visualization.lang;

import ru.iimm.ontology.OWL2UPOConverter.ConstantsOntConverter;

/**
 * Язык по умолчанию (Russian).
 * @author Danilov E.Y.
 *
 */
public class LanguageRU extends Language
{
	public void initializeLang()
	{
		Language.NAME_INIT_LANG = "Russian";
		
		Language.TITLE_MAIN_FRAME = "Визуализатор когнитивных фреймов";
		
		Language.BUTTON_SETTING_ONTOLOGY = "Настройка онтологии";
		Language.BUTTON_SETTING_VIS = "Настройка визуализации";
		
		Language.LABEL_CONVERT_OWL_ONT_TO_COGNITIVE_FRAME = "Сформировать список с КФ";
		Language.LABEL_ADD_IN_UPO_COGNITIVE_FRAME = "Добавить в ОПП КФ";
		Language.LABEL_LOAD_UPO_WITH_COGNITIVE_FRAME = "Загрузить ОПП с КФ";
		Language.LABEL_LOAD_EMPTY_ONTOLOGY = "Загрузить пустую онтологию";
		Language.LABEL_COGNITIVE_FRAME_CLASS = "Список когнитивных фреймов";
		Language.LABEL_PROTEGE_OWLCLASS_LIST = "Список OWL классов";
		Language.LABEL_HIGTH_QUALITY = "Высокое качество";
		Language.LABEL_FILTER_IRI = "Фильтр IRI";
		Language.LABEL_LOADING = "Загрузка...";
		
		Language.UPO_LANG = ConstantsOntConverter.LANG_RU;
		Language.COGNITIVE_FRAME = "Когнитивный фрейм";
		Language.TAXONOMY_COGNITIVE_FRAME = "Таксономический когнитивный фрейм";
		Language.PARTONOMY_COGNITIVE_FRAME = "Партономический когнитивный фрейм";
		Language.DEPENDENCY_COGNITIVE_FRAME = "Зависимый когнитивный фрейм";
		Language.SPECIAL_COGNITIVE_FRAME = "Специальный когнитивный фрейм";
		
		Language.REDUCTION_COGNITIVE_FRAME = "КФрейм";
		Language.REDUCTION_TAXONOMY_COGNITIVE_FRAME = "Таксономический КФрейм";
		Language.REDUCTION_PARTONOMY_COGNITIVE_FRAME = "Партономический КФрейм";
		Language.REDUCTION_DEPENDENCY_COGNITIVE_FRAME = "Зависимый КФрейм";
		Language.REDUCTION_SPECIAL_COGNITIVE_FRAME = "Специальный КФрейм";
	}
}
