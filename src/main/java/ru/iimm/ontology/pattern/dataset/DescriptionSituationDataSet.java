package ru.iimm.ontology.pattern.dataset;

import ru.iimm.ontology.pattern.ODPRealization;


public class DescriptionSituationDataSet extends DataSet
{
	private ODPRealization description;
	private ODPRealization situation;

	public ODPRealization getDescription()
	{
		return description;
	}

	public void setDescription(ODPRealization description)
	{
		this.description = description;
	}

	public ODPRealization getSituation()
	{
		return situation;
	}

	public void setSituation(ODPRealization situation)
	{
		this.situation = situation;
	}
}
