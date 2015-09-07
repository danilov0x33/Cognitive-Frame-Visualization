package ru.iimm.ontology.OWL2UPOConverter;

import ru.iimm.ontology.ontAPI.Ontology;

/**
 * Для тестовых прогонов
 * For tests
 * @author Lomov P. A.
 *
 */
public class run
{
	public static void main(String[] args)
	{
		String dir="D://Working//_Eclipse//_test-ont//CF-ont//owl-ont", 
				file="network-tech-ont.owl";
		
		String dir2="D://Working//_Eclipse//_test-ont//CF-ont//empty-upo-cfo", 
				file2="ontUPO.owl";
		

		OWLont.init(dir, file, true, true);
		UPOont.init(dir2, file2, true);
		 
		
			
		new OWL2UPOConverter(OWLont.getOWLont(), UPOont.getUPOont()); 
		

	}

}
