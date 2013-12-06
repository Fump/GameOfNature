package se.lth.gameofnature.questions;

import se.lth.gameofnature.WinnerActivity;
import android.content.Context;
import android.content.Intent;

public class FinalQuestion extends Question{
	
	public FinalQuestion(String id, String questionTxt) {
		super(id, Question.QUESTION_TYPE_FINAL, questionTxt);
	}
	
	@Override
	public void startQuestionActivity(Context source, String taskMarkerId) {
		Intent intent = new Intent (source,WinnerActivity.class);
		source.startActivity(intent);
	}
}
