package se.lth.gameofnature.questions;

import java.util.ArrayList;

import se.lth.gameofnature.QuestionActivity;
import se.lth.gameofnature.WinnerActivity;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import android.content.Context;
import android.content.Intent;

public class FinalQuestion extends Question{
	
	//Är ej säker på hur detta ska hanteras
	protected ArrayList<Clue> clues;
	
	public FinalQuestion(String id, String questionTxt,
			String[] answers, int correctAnswer, ArrayList<Clue> clues) {
		super(id, Question.QUESTION_TYPE_TEXT, questionTxt, answers, correctAnswer);
		this.clues = clues;
	}
	
	@Override
	public void startQuestionActivity(Context source, String taskMarkerId) {
	Intent intent = new Intent (source,WinnerActivity.class);
	source.startActivity(intent);
	}
}
