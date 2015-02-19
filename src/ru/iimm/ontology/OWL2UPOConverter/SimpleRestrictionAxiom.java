package ru.iimm.ontology.OWL2UPOConverter;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRestriction;

import ru.iimm.ontology.ontAPI.Ontology;

/**
 * Логическая аксиома, правая часть которой включает ограничесние на свойство,
 * с одним именованным классом. В левой части - именованный класс.
 * Например: A subclassOf hasSomething some B 
 * @deprecated не имеет смысла т.к. справа может стоять комбинация: 
 * сложная субаксиома, сложная субаксиома, простое ограничение = в этом случае это просто сложная аксиома
 * т.е. специфики добавления SimpleRestrictionAxiom нет, специфика присутсвует в добавлении
 * субаксиомы (простое ограничение) и учитывается в методе для добавления субаксиом.
 * @author Lomov P.A.
 *
 */
public class SimpleRestrictionAxiom extends ParsedAxiom 
{
    	//TODO а нужен ли он тут - может тот, что в суперклассе подойтет
	@Override
	public SimpleRestrictionAxiom clone() throws CloneNotSupportedException
	{

		SimpleRestrictionAxiom cloneAx = (SimpleRestrictionAxiom) super.clone();
		
		return cloneAx;
	}

	/**
	 * 
	 * @return
	 */
	public PropertyRestrictionSubAxiom getRightRestrictionSubaxiom()
	{
	    return (PropertyRestrictionSubAxiom) this.getrSideOfAx().get(0);
	}
	


}
