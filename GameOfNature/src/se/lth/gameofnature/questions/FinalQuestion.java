package se.lth.gameofnature.questions;

import se.lth.gameofnature.FinalQuestionActivity;
import se.lth.gameofnature.WinnerActivity;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import android.content.Context;
import android.content.Intent;

public class FinalQuestion extends Question{
	
	private String code;
	
	public FinalQuestion(String id, String code, String questionTxt) {
		super(id, Question.QUESTION_TYPE_FINAL, questionTxt);
		
		this.code = code;
	}
	
	@Override
	public void startQuestionActivity(Context source, String taskMarkerId) {
		Intent intent = new Intent (source,FinalQuestionActivity.class);
		
		intent.putExtra(TaskMarker.TASK_MARKER_ID, taskMarkerId);
		intent.putExtra(Question.QUESTION_TXT, questionTxt);
		intent.putExtra(Question.FINAL_CODE, code);
		
		source.startActivity(intent);
	}
}
