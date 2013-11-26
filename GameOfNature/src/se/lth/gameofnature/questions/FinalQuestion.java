package se.lth.gameofnature.questions;

import se.lth.gameofnature.QuestionActivity;
import se.lth.gameofnature.StatisticsActivity;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import android.content.Context;
import android.content.Intent;

public class FinalQuestion extends Question{
	
	
	public FinalQuestion(String id, String questionTxt,
			String[] answers, int correctAnswer) {
		super(id, Question.QUESTION_TYPE_TEXT, questionTxt, answers, correctAnswer);
	}
	
	@Override
	public void startQuestionActivity(Context source, String taskMarkerId) {
	Intent intent = new Intent (source,StatisticsActivity.class);
	source.startActivity(intent);
	}
}
