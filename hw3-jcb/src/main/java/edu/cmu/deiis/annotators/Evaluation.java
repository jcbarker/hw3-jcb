package edu.cmu.deiis.annotators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;

import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Results;

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

    FSArray asArray = new FSArray(aJCas, scores.size());

    int i = 0;
    for (AnswerScore as: scores){
      asArray.set(i, as);
      i++;
    }

    
    Double precision = 1.0*correct/totalCorrect;
    
    Results results =  new Results(aJCas);
    results.setAnswerScores(asArray);
    results.setQuestion(question);
    results.setPrecision(precision);
    results.setBegin(question.getBegin());
    results.setEnd(question.getEnd());
    results.setCasProcessorId("Evaluation");
    results.setConfidence(1.0);
    results.addToIndexes();
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
