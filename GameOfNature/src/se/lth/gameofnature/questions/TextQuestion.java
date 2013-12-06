package se.lth.gameofnature.questions;

import se.lth.gameofnature.QuestionActivity;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import android.content.Context;
import android.content.Intent;

public class TextQuestion extends Question {
	
	private String[] answers;
	private int correctAnswer;
	
	public TextQuestion(String id, String questionTxt,
			String[] answers, int correctAnswer) {
		super(id, Question.QUESTION_TYPE_TEXT, questionTxt);
		
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}
	
	public String[] getAnswerOptions() {
		return answers;
	}
	
	public int getCorrectAnswerIndex() {
		return correctAnswer;
	}
	
	@Override
	public void startQuestionActivity(Context source, String taskMarkerId) {
		Intent i = new Intent(source, QuestionActivity.class);
		
		i.putExtra(TaskMarker.TASK_MARKER_ID, taskMarkerId);
		i.putExtra(Question.QUESTION_TXT, questionTxt);
		i.putExtra(Question.ANSWERS, answers);
		i.putExtra(Question.CORRECT_ANSWER, correctAnswer);
		
		source.startActivity(i);
	}
}
