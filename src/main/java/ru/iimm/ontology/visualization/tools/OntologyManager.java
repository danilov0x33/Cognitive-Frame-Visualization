package ru.iimm.ontology.visualization.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.iimm.ontology.OWL2UPOConverter.DataFactory;
import ru.iimm.ontology.OWL2UPOConverter.OWLont;
import ru.iimm.ontology.OWL2UPOConverter.UPOont;
import ru.iimm.ontology.cftools.CFrameOnt;
import ru.iimm.ontology.cftools.UPOCFrameFormer;

/**
 * Менеджер онтологий.
 * @author Danilov E.Y.
 *
 */
public class OntologyManager
{
	private static final Logger log = LoggerFactory.getLogger(OntologyManager.class);
	
	/**Папка для хранения файлов онтологии (пустая онтология, upo и cfo): {@value}*/
	public static final String DIR_COGNITIVE_FRAME_VIS = "CognitiveFrameVisualization";
	/**Папка для хранения пустой онтологии: {@value}*/
	public static final String DIR_RESOURCES= "resources";
	/**Папка для хранения пустой онтологии: {@value}*/
	public static final String DIR_EMPTY_ONTOLOGY = "empty-upo-cfo";
	public static final String EMPTY_ONT_UPO = "ontUPO.owl";
	public static final String EMPTY_ONT_UPO_CF = "ontUPO-CognitiveFrame.owl";
	public static final String EMPTY_SKOS =  "skos.rdf";
	public static final String FULL_DIR_EMPTY_ONTOLOGY = DIR_COGNITIVE_FRAME_VIS + "/" + DIR_EMPTY_ONTOLOGY;
	public static final String FULL_EMPTY_ONT_UPO = FULL_DIR_EMPTY_ONTOLOGY + "/" + EMPTY_ONT_UPO;
	public static final String FULL_EMPTY_ONT_UPO_CF = FULL_DIR_EMPTY_ONTOLOGY + "/" + EMPTY_ONT_UPO_CF;
	public static final String FULL_EMPTY_SKOS =  FULL_DIR_EMPTY_ONTOLOGY + "/" + EMPTY_SKOS;
	
	/**Менеджер онтологий из Protege.*/
	private OWLEditorKit protegeOWLEditor;
	/**Онтология с КФ.*/
	private CFrameOnt congitiveFrameOntology;
	/**Имя OWL-онтологии.*/
	private String fileActivOntology;
	/**Путь к OWL-онтологии.*/
	private String pathDirOntology;
	
	/**
	 * {@linkplain OntologyManager}
	 * @param protegeEditor - {@linkplain #protegeOWLEditor}
	 * @param convertToUPO - Произвести конвертацию OWL Ontology -> UPO.
	 * @param convertToCognitiveFrame - Произвести конвертацию UPO -> CF
	 * @param loadCF - Загрузить CF.
	 */
	public OntologyManager(OWLEditorKit protegeEditor, boolean convertToUPO, boolean convertToCognitiveFrame, boolean loadCF)
	{
		this.protegeOWLEditor = protegeEditor;
		
		OWLOntologyManager manager = this.protegeOWLEditor.getOWLModelManager().getOWLOntologyManager();
		OWLOntology ontology = this.protegeOWLEditor.getOWLModelManager().getActiveOntology();

		IRI pathFile = manager.getOntologyDocumentIRI(ontology);

		String pFileOWLOnt = pathFile.toString().substring(6);
		
		this.pathDirOntology = pFileOWLOnt.replaceAll(pathFile.getFragment().toString(), "");
		this.fileActivOntology = pathFile.getFragment().toString();
		
		this.initEmptyOntology();
		this.init(convertToUPO, convertToCognitiveFrame, loadCF);
	}
	/**
	 * {@linkplain OntologyManager}
	 * @param pathDirOntology - Путь к папке с онтологией.
	 * @param fileOntopogy - Имя файла с онтологией.
	 * @param convertToUPO - Произвести конвертацию OWL Ontology -> UPO.
	 * @param convertToCognitiveFrame - Произвести конвертацию UPO -> CF
	 * @param loadCF - Загрузить CF.
	 */
	public OntologyManager(String pathDirOntology, String fileOntopogy, boolean convertToUPO, boolean convertToCognitiveFrame, boolean loadCF)
	{
		this.fileActivOntology = fileOntopogy;
		this.pathDirOntology = pathDirOntology;
		
		this.initEmptyOntology();
		this.init(convertToUPO, convertToCognitiveFrame, loadCF);
	}
	
	/**
	 * Загрузка и конвертация онтологии.	
	 * @param convertToUPO - Произвести конвертацию OWL Ontology -> UPO.
	 * @param convertToCognitiveFrame - Произвести конвертацию UPO -> CF
	 * @param loadCF - Загрузить CF.
	 */
	private void init(boolean convertToUPO, boolean convertToCognitiveFrame, boolean loadCF)
	{
		if(convertToUPO)
		{
			// Загружаем пустую UPO + c CFO
			UPOont.init(new File(FULL_DIR_EMPTY_ONTOLOGY).getAbsolutePath(), EMPTY_ONT_UPO, false);
			
			// Загружаем пустую онтологию для конвертации
			OWLont.init(this.pathDirOntology ,this.fileActivOntology, true, true);
			
			// Конвертируем OWL в ОПП и сохраняем ОПП
			DataFactory.getFactory().getOWL2UPOConverter(OWLont.getOWLont(), UPOont.getUPOont());
			
			// Чистим резанер т.к. UPO поменялась - иначе он будет выводить по старой UPO
			UPOont.getUPOont().reas.flush();
			
			File saveUPOFile = new File(DIR_COGNITIVE_FRAME_VIS+"/"+this.fileActivOntology+"/rez-upo");
			saveUPOFile.mkdirs();
			
			// Сохраняем полученную UPO
			UPOont.getUPOont().saveOntologies(saveUPOFile.getAbsolutePath().replace('\\', '/'));
		}
		
		if(convertToCognitiveFrame && loadCF)
		{
			// Загружаем UPO
			UPOont.init(this.pathDirOntology ,this.fileActivOntology, false);
						
			// Добавление в онтологию к-фреймов
			UPOCFrameFormer crt = new UPOCFrameFormer();
			this.congitiveFrameOntology = crt.getCFramesUserPresentationOntology(UPOont.getUPOont());
			
			File saveCFFile = new File(DIR_COGNITIVE_FRAME_VIS+"/"+this.fileActivOntology+"/rez-cfo");
			saveCFFile.mkdirs();
			
			// Сохранение онтологии с к-фреймами
			this.congitiveFrameOntology.saveOntologies(saveCFFile.getAbsolutePath().replace('\\', '/'));
			
			// загрузка онтологии с к-фреймами
			this.congitiveFrameOntology = new CFrameOnt(new File(DIR_COGNITIVE_FRAME_VIS+"/"+this.fileActivOntology+"/rez-cfo").getAbsolutePath(), EMPTY_ONT_UPO, false);
		}
		else
		{
			if(!convertToCognitiveFrame && loadCF)
			{
				// загрузка онтологии с к-фреймами
				this.congitiveFrameOntology = new CFrameOnt(this.pathDirOntology ,this.fileActivOntology, false);
			}
		}
	}
	
	/**
	 * Инициализация пустой онтологии. Чтение файлов пустой онтологии
	 * и записи ее во внешнюю директорю {@value #DIR_EMPTY_ONTOLOGY}.
	 */
	private void initEmptyOntology()
	{
		//Чтение пустой онтологии из ресурсов.
		InputStream ontUpoInputStream = OntologyManager.class.getResourceAsStream("/" + DIR_EMPTY_ONTOLOGY + "/" + EMPTY_ONT_UPO);
		InputStream ontUpoCfInputStream = OntologyManager.class.getResourceAsStream("/" + DIR_EMPTY_ONTOLOGY + "/" + EMPTY_ONT_UPO_CF);
		InputStream ontUpoSKOSInputStream = OntologyManager.class.getResourceAsStream("/" + DIR_EMPTY_ONTOLOGY + "/" + EMPTY_SKOS);
		
		File dirCFVis = new File(DIR_COGNITIVE_FRAME_VIS);
		File emptyDir = new File(FULL_DIR_EMPTY_ONTOLOGY);
		File ontUpoFile = new File(FULL_EMPTY_ONT_UPO);
		File ontUpoCf = new File(FULL_EMPTY_ONT_UPO_CF);
		File ontUpoSKOS = new File(FULL_EMPTY_SKOS);
		try 
		{
			//Подготовка и создание директории и файлов для записи.
			dirCFVis.mkdirs();
			emptyDir.mkdirs();
			ontUpoFile.createNewFile(); 
			ontUpoCf.createNewFile(); 
			ontUpoSKOS.createNewFile();
			
			//Запись пустой онтологии.
			FileOutputStream fileOut = new FileOutputStream(ontUpoFile, false);				
			int data = ontUpoInputStream.read();
			while(data != -1) 
			{
				fileOut.write(data);
				data = ontUpoInputStream.read();
			}
			fileOut.close();
			ontUpoInputStream.close();
			
			//Запись онтологии для фреймов.
			fileOut = new FileOutputStream(ontUpoCf, false);				
			data = ontUpoCfInputStream.read();
			while(data != -1) 
			{
				fileOut.write(data);
				data = ontUpoCfInputStream.read();
			}
			fileOut.close();
			ontUpoCfInputStream.close();
			
			//Запись skos файла.
			fileOut = new FileOutputStream(ontUpoSKOS, false);				
			data = ontUpoSKOSInputStream.read();
			while(data != -1) 
			{
				fileOut.write(data);
				data = ontUpoSKOSInputStream.read();
			}
			fileOut.close();
			ontUpoSKOSInputStream.close();	
			
		} 
		catch (IOException ex) 
		{
			log.error("Не удалось создать пустую онтологию");
			System.exit(1);
		}
	}
	
	public UPOont getUPOOnt()
	{
		return UPOont.getUPOont();
	}
	
	public OWLont getOWLOnt()
	{
		return OWLont.getOWLont();
	}

	public OWLEditorKit getProtegeOWLEditor()
	{
		return this.protegeOWLEditor;
	}

	public CFrameOnt getCongitiveFrameOntology()
	{
		return this.congitiveFrameOntology;
	}

	public String getFileActivOntology()
	{
		return this.fileActivOntology;
	}

	public String getPathDirOntology()
	{
		return this.pathDirOntology;
	}
	
}
