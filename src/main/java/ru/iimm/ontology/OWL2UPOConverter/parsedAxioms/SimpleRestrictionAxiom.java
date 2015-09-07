package ru.iimm.ontology.OWL2UPOConverter.parsedAxioms;


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
