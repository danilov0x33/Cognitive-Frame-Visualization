/**
 * 
 */
package ru.iimm.ontology.cftools;

/**
 * @author Lomov P. A.
 *
 */
public interface CFrameVisitor
{
	void visitPatromomyFrame(PartonomyCFrame frame);
	void visitDependencyFrame(DependencyCFrame frame);
	void visitTaxonomyFrame(TaxonomyCFrame frame);
	void visitSpecialFrame(SpecialCFrame frame);
}
