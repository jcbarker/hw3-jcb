

/* First created by JCasGen Mon Oct 07 23:23:10 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


import org.apache.uima.jcas.cas.FSList;


/** This holds the results of an evalution of a question's answers.
 * Updated by JCasGen Tue Oct 08 00:08:33 EDT 2013
 * XML source: /home/jon/git/hw3-jcb/hw3-jcb/src/main/resources/descriptors/deiis_types.xml
 * @generated */
public class Results extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Results.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Results() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Results(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Results(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Results(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: question

  /** getter for question - gets The question.
   * @generated */
  public Question getQuestion() {
    if (Results_Type.featOkTst && ((Results_Type)jcasType).casFeat_question == null)
      jcasType.jcas.throwFeatMissing("question", "edu.cmu.deiis.types.Results");
    return (Question)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Results_Type)jcasType).casFeatCode_question)));}
    
  /** setter for question - sets The question. 
   * @generated */
  public void setQuestion(Question v) {
    if (Results_Type.featOkTst && ((Results_Type)jcasType).casFeat_question == null)
      jcasType.jcas.throwFeatMissing("question", "edu.cmu.deiis.types.Results");
    jcasType.ll_cas.ll_setRefValue(addr, ((Results_Type)jcasType).casFeatCode_question, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: answerScores

  /** getter for answerScores - gets The answer scores.
   * @generated */
  public FSArray getAnswerScores() {
    if (Results_Type.featOkTst && ((Results_Type)jcasType).casFeat_answerScores == null)
      jcasType.jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.Results");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Results_Type)jcasType).casFeatCode_answerScores)));}
    
  /** setter for answerScores - sets The answer scores. 
   * @generated */
  public void setAnswerScores(FSArray v) {
    if (Results_Type.featOkTst && ((Results_Type)jcasType).casFeat_answerScores == null)
      jcasType.jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.Results");
    jcasType.ll_cas.ll_setRefValue(addr, ((Results_Type)jcasType).casFeatCode_answerScores, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for answerScores - gets an indexed value - The answer scores.
   * @generated */
  public AnswerScore getAnswerScores(int i) {
    if (Results_Type.featOkTst && ((Results_Type)jcasType).casFeat_answerScores == null)
      jcasType.jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.Results");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Results_Type)jcasType).casFeatCode_answerScores), i);
    return (AnswerScore)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Results_Type)jcasType).casFeatCode_answerScores), i)));}

  /** indexed setter for answerScores - sets an indexed value - The answer scores.
   * @generated */
  public void setAnswerScores(int i, AnswerScore v) { 
    if (Results_Type.featOkTst && ((Results_Type)jcasType).casFeat_answerScores == null)
      jcasType.jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.Results");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Results_Type)jcasType).casFeatCode_answerScores), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Results_Type)jcasType).casFeatCode_answerScores), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: precision

  /** getter for precision - gets The precision at N (the number of correct answers).
   * @generated */
  public double getPrecision() {
    if (Results_Type.featOkTst && ((Results_Type)jcasType).casFeat_precision == null)
      jcasType.jcas.throwFeatMissing("precision", "edu.cmu.deiis.types.Results");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((Results_Type)jcasType).casFeatCode_precision);}
    
  /** setter for precision - sets The precision at N (the number of correct answers). 
   * @generated */
  public void setPrecision(double v) {
    if (Results_Type.featOkTst && ((Results_Type)jcasType).casFeat_precision == null)
      jcasType.jcas.throwFeatMissing("precision", "edu.cmu.deiis.types.Results");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((Results_Type)jcasType).casFeatCode_precision, v);}    
  }

    