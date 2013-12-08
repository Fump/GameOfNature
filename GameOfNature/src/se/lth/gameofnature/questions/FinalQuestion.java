package se.lth.gameofnature.questions;

import se.lth.gameofnature.FinalQuestionActivity;
import se.lth.gameofnature.WinnerActivity;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import android.content.Context;
import android.content.Intent;

public class FinalQuestion extends Question{
	
	public FinalQuestion(String id, String type, String questionTxt) {
		super(id, Question.QUESTION_TYPE_FINAL, questionTxt);
	}
	
	@Override
	public void startQuestionActivity(Context source, String taskMarkerId) {
		Intent intent = new Intent (source,FinalQuestionActivity.class);
		intent.putExtra(TaskMarker.TASK_MARKER_ID, taskMarkerId);
		intent.putExtra(Question.QUESTION_TXT, questionTxt);
		source.startActivity(intent);
	}
}
