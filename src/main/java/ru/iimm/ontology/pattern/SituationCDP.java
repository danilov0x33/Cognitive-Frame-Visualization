package ru.iimm.ontology.pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class SituationCDP extends ContentDesingPattern
{
	/* Common const */
	public static final String ONT_IRI = "http://www.ontologydesignpatterns.org/cp/owl/situation.owl";
	public static final String BASE_IRI = ONT_IRI + "#";
	public static final String FILENAME = "situation.owl";
	
	/* Class names */
	public static final String SITUATION_IRI = BASE_IRI + "Situation";
	
	/* Properties names */
	public static final String HAS_SETTING_IRI 		= BASE_IRI + "hasSetting";
	public static final String IS_SETTING_FOR_IRI 	= BASE_IRI + "isSettingFor";
	
	private OWLClass situation;
	
	private OWLObjectProperty hasSetting;
	private OWLObjectProperty isSettingFor;
	
	/**
	 * Создает паттерн + подружает его онтологию.
	 */
	public SituationCDP(String dirPath)
	{
		super(IRI.create(BASE_IRI), dirPath, FILENAME);
		this.init();
	}

	private void init()
	{
		this.situation = df.getOWLClass(IRI.create(SITUATION_IRI));
		
		this.hasSetting = df.getOWLObjectProperty(IRI.create(HAS_SETTING_IRI));
		this.isSettingFor = df.getOWLObjectProperty(IRI.create(IS_SETTING_FOR_IRI));
	}

	/**
	 * @return the {@linkplain #situation}
	 */
	public OWLClass getSituation()
	{
		return situation;
	}

	/**
	 * @param situation the {@linkplain #situation} to set
	 */
	public void setSituation(OWLClass situation)
	{
		this.situation = situation;
	}

	/**
	 * @return the {@linkplain #hasSetting}
	 */
	public OWLObjectProperty getHasSetting()
	{
		return hasSetting;
	}

	/**
	 * @param hasSetting the {@linkplain #hasSetting} to set
	 */
	public void setHasSetting(OWLObjectProperty hasSetting)
	{
		this.hasSetting = hasSetting;
	}

	/**
	 * @return the {@linkplain #isSettingFor}
	 */
	public OWLObjectProperty getIsSettingFor()
	{
		return isSettingFor;
	}

	/**
	 * @param isSettingFor the {@linkplain #isSettingFor} to set
	 */
	public void setIsSettingFor(OWLObjectProperty isSettingFor)
	{
		this.isSettingFor = isSettingFor;
	}

}
