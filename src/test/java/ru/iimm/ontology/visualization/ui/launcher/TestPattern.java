package ru.iimm.ontology.visualization.ui.launcher;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import ru.iimm.ontology.ontAPI.Ontology;
import ru.iimm.ontology.pattern.SituationCDP;
import ru.iimm.ontology.pattern.realizations.SituationRealization;
import ru.iimm.ontology.visualization.ui.mvp.impl.presenters.PresenterPatternVisPiccolo2DVisitorImpl;


/**
 *
 * @author Danilov
 * @version 0.1
 */
public class TestPattern extends JFrame 
{

    private static final long serialVersionUID = 1L;

    public TestPattern() 
    {
    	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	this.setLocationRelativeTo(null);
    	this.setSize(new Dimension(600, 600));
    	this.initialize();
    	this.setVisible(true);
    }


    public void initialize() {
    	String ontDirPath = "F://Ontology//Danilov_ont//";
		String patternOntDirPath = ontDirPath + "patterns//";
		String termsOnt = "danilov_ont.owl";
		
		///////////////////////////
		String baseOntTermIRI = "http://www.iimm.ru/ontlib/network-ont-terms#";
		
		Ontology ontTerms = new Ontology(ontDirPath, termsOnt ,false, true);

		String clsNameAAA;

		OWLClass object = ontTerms.getOWLClassByIRI("http://danilov.ru/danilov.owl#test");
				
		clsNameAAA = baseOntTermIRI+"AAA";

		OWLClass rl = ontTerms.getOWLClassByIRI(clsNameAAA); //.addClass(clsName, rusLabel, comment, "ru");
		OWLClass rl2 = ontTerms.getOWLClassByIRI("http://danilov.ru/danilov.owl#BBB");
		
		OWLNamedIndividual ind =  ontTerms.df.getOWLNamedIndividual(IRI.create("http://danilov.ru/danilov.owl#testIndivid"));

		SituationCDP situation = new SituationCDP(patternOntDirPath);

		SituationRealization.Builder builder = SituationRealization.newBuilder(situation);
		
		PresenterPatternVisPiccolo2DVisitorImpl pPresenter = new PresenterPatternVisPiccolo2DVisitorImpl();
		
		pPresenter.visit(builder.setEntity(object,rl2,object,rl2).setSituation(rl).buildVisualization());
		pPresenter.getView().open();
		this.getContentPane().add(pPresenter.getView().getViewComponent());
    }

    public static void main(final String[] args) throws InvocationTargetException, InterruptedException 
    {
    	SwingUtilities.invokeAndWait(new Runnable()
		{
			@Override
			public void run()
			{
				new TestPattern();
			}
		});
        
    }
}
