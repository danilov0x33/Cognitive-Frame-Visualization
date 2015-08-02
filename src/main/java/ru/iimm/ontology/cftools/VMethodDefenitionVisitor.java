/**
 * 
 */
package ru.iimm.ontology.cftools;

/**
 * Визитор определяет концепцию 
 * визуализации (таблица, граф, картинка и др.)
 * @author Lomov P. A.
 *
 */
public class VMethodDefenitionVisitor implements CFrameVisitor
{
	
	private CFrameVisualisationMethod visMet;

	@Override
	public void visitPatromomyFrame(PartonomyCFrame pframe)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void visitDependencyFrame(DependencyCFrame dframe)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitTaxonomyFrame(TaxonomyCFrame tframe)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visitSpecialFrame(SpecialCFrame frame)
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return the visMet
	 */
	public CFrameVisualisationMethod getVisMet() 
	{
		return visMet;
	}


	


	

}
