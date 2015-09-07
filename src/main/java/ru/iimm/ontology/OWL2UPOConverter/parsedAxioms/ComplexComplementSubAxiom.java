package ru.iimm.ontology.OWL2UPOConverter.parsedAxioms;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;

public class ComplexComplementSubAxiom extends ComplementSubAxiom
{
    /**
     * 
     */
    ComplexComplementSubAxiom()
    {
	super();
	// TODO Auto-generated constructor stub
    }

    ComplexComplementSubAxiom(ComplexSubAxiom sbAx)
    {
	super();
	this.setBindedClassList(sbAx.getBindedClassList());
	this.setClsList(sbAx.getClsList());
	this.setDtpPrpList(sbAx.getDtpPrpList());
	this.setObjPrpList(sbAx.getObjPrpList());
	this.setPrpFromAxList(sbAx.getPrpFromAxList());
    }
    
    

}
