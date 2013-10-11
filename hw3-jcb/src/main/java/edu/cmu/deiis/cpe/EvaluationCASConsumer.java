package edu.cmu.deiis.cpe;

import java.util.ArrayList;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Type;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.resource.ResourceProcessException;

import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Results;

public class EvaluationCASConsumer extends CasConsumer_ImplBase {

  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {
    FSIndex resultsIndex = ((JCas) aCAS).getAnnotationIndex(Results.type);
    FSIterator resultsIter = resultsIndex.iterator();
    
    Double result_num = 0.0;
    Double added_prec = 0.0;
    
    while(resultsIter.hasNext()) {
      Results r = (Results) resultsIter.next();
      result_num += 1.0;
      added_prec += r.getPrecision();
      
      System.out.println("Question: "+r.getQuestion().getCoveredText());
      FSArray scores = r.getAnswerScores();
      int totalCorrect = 0;
      for (int i = 0; i < scores.size(); i++){
        AnswerScore as = (AnswerScore) scores.get(i);
        String symbol = as.getAnswer().getIsCorrect()?"+":"-";
        if (as.getAnswer().getIsCorrect())
           totalCorrect++;
        System.out.printf("%s %.2f %s\n", symbol, as.getScore(), as.getCoveredText());
      }

      System.out.printf("Precision at %d: %.2f\n", totalCorrect, r.getPrecision());
    }
    
    System.out.printf("\nAverage precision: %.2f\n", added_prec/result_num);
  }

}
