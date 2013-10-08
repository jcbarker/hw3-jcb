package edu.cmu.deiis.annotators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.Question;

/**
 * This class ranks the answers according to correctness and calculates precision.
 * @author Jon Barker
 *
 */
public class Evaluation extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    FSIndex answerScoreIndex = aJCas.getAnnotationIndex(AnswerScore.type);
    FSIterator answerScoreIter = answerScoreIndex.iterator();
    ArrayList<AnswerScore> scores = new ArrayList<AnswerScore>();
    while (answerScoreIter.hasNext()) {
      scores.add((AnswerScore)answerScoreIter.next());
    }
    
    // Rank scores
    Collections.sort(scores, Collections.reverseOrder(AnswerScoreComparator));
    
    // Calculate number of correct answers
    int totalCorrect = 0;
    for (AnswerScore as: scores) {
      if (as.getAnswer().getIsCorrect()) {
        totalCorrect += 1;
      }
    }
    
    // Calculate number of correct answers at N
    int correct = 0;
    for (int i = 0; i < totalCorrect; i++) {
      if (scores.get(i).getAnswer().getIsCorrect()) {
        correct += 1;
      }
    }
    
    // Output results
    FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);
    FSIterator questionIter = questionIndex.iterator();
    Question question = (Question) questionIter.next();
    System.out.println("Question: "+question.getCoveredText());
    for (AnswerScore as: scores){
      String symbol = as.getAnswer().getIsCorrect()?"+":"-";
      System.out.printf("%s %.2f %s\n", symbol, as.getScore(), as.getCoveredText());
    }
    System.out.printf("Precision at %d: %.2f\n", totalCorrect, 1.0*correct/totalCorrect);
  }

  private Comparator<AnswerScore> AnswerScoreComparator = new Comparator<AnswerScore>() {

    @Override
    public int compare(AnswerScore arg0, AnswerScore arg1) {
      if (arg0.getScore() > arg1.getScore()) {
        return 1;
      }
      else if (arg0.getScore() == arg1.getScore()) {
        return 0;
      }
      else {
        return -1;
      }
    }
    
  };
}
