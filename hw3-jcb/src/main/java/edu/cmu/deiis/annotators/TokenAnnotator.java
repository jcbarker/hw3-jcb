package edu.cmu.deiis.annotators;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Token;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.PTBTokenizerAnnotator;

/**
 * This class annotates Tokens found in Question and Answers using the StanfordTokenizer
 * @author Jon Barker
 *
 */
public class TokenAnnotator extends JCasAnnotator_ImplBase {
  private PTBTokenizerAnnotator ptb = new PTBTokenizerAnnotator();
  private JCas thisJCas;
  
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    thisJCas = aJCas;
    
    // Get tokens from Question
    FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);
    FSIterator questionIter = questionIndex.iterator();
    Question question = (Question) questionIter.next();
    tokenize(question.getCoveredText(), question.getBegin());
    
    // Get tokens from Answer
    FSIndex answerIndex = aJCas.getAnnotationIndex(Answer.type);
    FSIterator answerIter = answerIndex.iterator();
    while (answerIter.hasNext()) {
      Answer answer = (Answer) answerIter.next();
      tokenize(answer.getCoveredText(), answer.getBegin());
    }
  }

  private void tokenize(String text, int offset) {
    Annotation ann = new Annotation(text);
    ptb.annotate(ann);
    for (CoreLabel tok: ann.get(TokensAnnotation.class)) {
      Token token = new Token(thisJCas);
      token.setBegin(tok.beginPosition()+offset);
      token.setEnd(tok.endPosition()+offset);
      token.setCasProcessorId("TokenAnnotator");
      token.setConfidence(1.0);
      token.addToIndexes();
    }
  }
}
