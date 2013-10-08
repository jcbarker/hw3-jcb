package edu.cmu.deiis.annotators;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.types.NGram;
import edu.cmu.deiis.types.Token;

public class NGramAnnotator extends JCasAnnotator_ImplBase {
  private Integer[] N;
  
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    // Get config. parameter values
    N = (Integer[]) aContext.getConfigParameterValue("N");
  }
  
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    FSIndex tokenIndex = aJCas.getAnnotationIndex(Token.type);
    FSIterator tokenIter = tokenIndex.iterator();
    ArrayList<ArrayList<Token>> sentences = new ArrayList<ArrayList<Token>>();
    ArrayList<Token> sentence = new ArrayList<Token>();
    
    while (tokenIter.hasNext()) {
      Token token = (Token) tokenIter.next();
      
      // Divide Tokens into sentences by checking gap between them
      if (sentence.isEmpty()) {
        sentence.add(token);
      }
      else if (token.getBegin() - sentence.get(sentence.size()-1).getEnd() <= 3) {
        sentence.add(token);
      }
      else {
        sentences.add(sentence);
        sentence = new ArrayList<Token>();
        sentence.add(token);
      }
    }
    sentences.add(sentence);
    
    for (ArrayList<Token> s: sentences) {
      for (int n: N){                                           // For every different type of ngram
        for (int start = 0; start <= s.size()-n; start++) {
          FSArray elements = new FSArray(aJCas, n);
          for (int i = 0; i < n; i++) {
            elements.set(i, s.get(start+i));
          }
          NGram ngram = new NGram(aJCas);
          ngram.setBegin(((Token)elements.get(0)).getBegin());
          ngram.setEnd(((Token)elements.get(n-1)).getEnd());
          ngram.setElements(elements);
          ngram.setElementType("Token");
          ngram.setCasProcessorId("NGramAnnotator");
          ngram.setConfidence(1.0);
          ngram.addToIndexes();
        }
      }
    }
  }

}
