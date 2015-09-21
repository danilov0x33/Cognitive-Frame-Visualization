package ru.iimm.ontology.pattern.realizations;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import ru.iimm.ontology.pattern.ODPRealization;
import ru.iimm.ontology.pattern.TypeOfEntitiesCDP;

public class TypeOfEntitiesRealization extends ODPRealization
{
	private OWLClass entity;
	private OWLClass typeClass;
	
	/**
	 * {@linkplain TypeOfEntitiesRealization}
	 */
	private TypeOfEntitiesRealization(){}
	
	public OWLClass getEntity()
	{
		return entity;
	}

	public OWLClass getTypeClass()
	{
		return typeClass;
	}

	public static Builder newBuilder(TypeOfEntitiesCDP pattern)
	{
		return new TypeOfEntitiesRealization().new Builder(pattern);
	}
	
	public class Builder
	{
		private TypeOfEntitiesCDP pattern;
		
		private Builder(TypeOfEntitiesCDP pattern)
		{
			this.pattern = pattern;
		}

		public Builder setTypeClass(OWLClass typeClass)
		{
			TypeOfEntitiesRealization.this.typeClass = typeClass;
			return this;
		}

		public Builder setEntity(OWLClass entity)
		{
			TypeOfEntitiesRealization.this.entity = entity;
			return this;
		}
		
		public TypeOfEntitiesRealization build()
		{	
			//Создаем новый объект
			TypeOfEntitiesRealization realization = new TypeOfEntitiesRealization();			
			realization.typeClass = TypeOfEntitiesRealization.this.typeClass;
			realization.entity = TypeOfEntitiesRealization.this.entity;
			realization.setPattern(this.pattern);
			
			OWLDataFactory df = pattern.getOWLDataFactory();
			
			//Заполняем аксиомами
			Set<OWLLogicalAxiom> structuralAxList = realization.getStructualAxiomSet();
			structuralAxList.add(df.getOWLSubClassOfAxiom(entity, typeClass));
			
			return realization;
		}
	}
}
