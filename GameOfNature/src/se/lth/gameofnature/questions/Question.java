package se.lth.gameofnature.questions;

import android.content.Context;

//Skapade denna som tom bara f�r att kunna g�ra TaskMarker-klassen p� ett logiskt s�tt.
//T�nkte att denna klass kan vara en abstrakt super-klass som �rvs av de olika fr�getyperna,
//G�r det l�ttare med implementeringen p� kartan.
public abstract class Question {
	public static final String QUESTION_ID = "QUESTION_ID";
	public static final String QUESTION_TYPE = "QUESTION_TYPE";
	public static final String QUESTION_TXT = "QUESTION_TXT";
	public static final String ANSWERS = "ANSWERS";
	public static final String CORRECT_ANSWER = "CORRECT_ANSWER";
	
	public static final String QUESTION_TYPE_TEXT = "text";
	
	protected String id;
	
	protected String questionType; 
	protected String questionTxt;
	
	protected String[] answers;
	protected int correctAnswer;

	public Question(String id, String questionType, String questionTxt,
			String[] answers, int correctAnswer) {
		this.id = id;
		this.questionType = questionType;
		this.questionTxt = questionTxt;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}
	
	public String getId() {
		return id;
	}
	
	public String getQuestionType() {
		return questionType;
	}
	
	public String getQuestionTxt() {
		return questionTxt;
	}
	
	public String[] getAnswerOptions() {
		return answers;
	}
	
	public int getCorrectAnswerIndex() {
		return correctAnswer;
	}
	
}
