package edu.cmu.deiis.annotators;

import java.util.ArrayList;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.NGram;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Token;

/**
 * This class scores the correctness of Answers to the Question by combining Token and NGram overlap scores
 * @author Jon Barker
 *
 */
public class AnswerScorer extends JCasAnnotator_ImplBase {
  private JCas thisJCas;
  
  /**
   * This scores the correctness of Answers to the Question by combining Token and NGram overlap scores
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    thisJCas = aJCas;
    
    FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);
    FSIterator questionIter = questionIndex.iterator();
    Question question = (Question) questionIter.next();
    ArrayList<Token> questionTokens = get_tokens(question.getBegin(), question.getEnd());
    ArrayList<NGram> questionNGrams = get_ngrams(question.getBegin(), question.getEnd());
    
    // Score each answer
    FSIndex answerIndex = aJCas.getAnnotationIndex(Answer.type);
    FSIterator answerIter = answerIndex.iterator();
    while (answerIter.hasNext()) {
      Answer answer = (Answer) answerIter.next();
      
      ArrayList<Token> answerTokens = get_tokens(answer.getBegin(), answer.getEnd());
      ArrayList<NGram> answerNGrams = get_ngrams(answer.getBegin(), answer.getEnd());
      
      AnswerScore answerScore = new AnswerScore(aJCas);
      answerScore.setBegin(answer.getBegin());
      answerScore.setEnd(answer.getEnd());
      answerScore.setAnswer(answer);
      answerScore.setScore(token_overlap(questionTokens, answerTokens)+ngram_overlap(questionNGrams, answerNGrams));
      answerScore.setCasProcessorId("AnswerScorer");
      answerScore.setConfidence(1.0);
      answerScore.addToIndexes();
    }
  }

  private ArrayList<Token> get_tokens(int begin, int end) {
    FSIndex tokenIndex = thisJCas.getAnnotationIndex(Token.type);
    FSIterator tokenIter = tokenIndex.iterator();
    ArrayList<Token> tokens = new ArrayList<Token>();
    while(tokenIter.hasNext()) {
      Token token = (Token) tokenIter.next();
      if (token.getBegin() >= begin && token.getEnd() <= end) {
        tokens.add(token);
      }
    }
    return tokens;
  }
  
  private ArrayList<NGram> get_ngrams(int begin, int end) {
    FSIndex ngramIndex = thisJCas.getAnnotationIndex(NGram.type);
    FSIterator ngramIter = ngramIndex.iterator();
    ArrayList<NGram> ngrams = new ArrayList<NGram>();
    while(ngramIter.hasNext()) {
      NGram ngram = (NGram) ngramIter.next();
      if (ngram.getBegin() >= begin && ngram.getEnd() <= end) {
        ngrams.add(ngram);
      }
    }
    return ngrams;
  }
  
  private double token_overlap(ArrayList<Token> question, ArrayList<Token> answer) {
    ArrayList<String> answerWords = new ArrayList<String>();
    for (Token token: answer) {
      answerWords.add(token.getCoveredText());
    }
    
    // Tally number of question tokens found in answer
    int found = 0;
    for (Token token: question) {
      if (answerWords.contains(token.getCoveredText())) {
        found += 1;
      }
    }
    
    return 1.0*found/answerWords.size();
  }
  
  private double ngram_overlap(ArrayList<NGram> question, ArrayList<NGram> answer) {
    ArrayList<String> answerWords = new ArrayList<String>();
    for (NGram ngram: answer) {
      answerWords.add(ngram.getCoveredText());
    }
    
    // Tally number of question ngrams found in answer
    int found = 0;
    for (NGram ngram: question) {
      if (answerWords.contains(ngram.getCoveredText())) {
        found += 1;
      }
    }
    
    return 1.0*found/answerWords.size();
  }
}
