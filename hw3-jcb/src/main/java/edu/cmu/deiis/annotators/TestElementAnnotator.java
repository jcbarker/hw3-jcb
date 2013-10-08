package edu.cmu.deiis.annotators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Question;

public class TestElementAnnotator extends JCasAnnotator_ImplBase {

  private Pattern questionPattern;
  private Pattern answerPattern;
  
  // Get question and answer regular expressions
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    // Get config. parameter values
    questionPattern = Pattern.compile((String) aContext.getConfigParameterValue("questionPattern"));
    answerPattern = Pattern.compile((String) aContext.getConfigParameterValue("answerPattern"));
  }
  
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    String document = aJCas.getDocumentText();
    
    // Get question
    Matcher matcher = questionPattern.matcher(document);
    while (matcher.find()) {
      Question question = new Question(aJCas);
      question.setBegin(matcher.start(1));
      question.setEnd(matcher.end(1));
      question.setCasProcessorId("TestElementAnnotator");
      question.setConfidence(1.0);
      question.addToIndexes();
    }
    
    // Get answers
    matcher = answerPattern.matcher(document);
    while (matcher.find()) {
      Answer answer = new Answer(aJCas);
      answer.setBegin(matcher.start(2));
      answer.setEnd(matcher.end(2));
      answer.setIsCorrect(matcher.group(1).equals("1"));
      answer.setCasProcessorId("TestElementAnnotator");
      answer.setConfidence(1.0);
      answer.addToIndexes();
    }
  }
}
