package se.lth.gameofnature.questions;

import se.lth.gameofnature.QuestionActivity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TextQuestion extends Question {

	public TextQuestion(String id, String questionTxt,
			String[] answers, int correctAnswer) {
		super(id, Question.QUESTION_TYPE_TEXT, questionTxt, answers, correctAnswer);
	}
	
	@Override
	public void startQuestionActivity(Context source) {
		Intent i = new Intent(source, QuestionActivity.class);
		
		i.putExtra(Question.QUESTION_TXT, questionTxt);
		i.putExtra(Question.ANSWERS, answers);
		i.putExtra(Question.CORRECT_ANSWER, correctAnswer);
		
		source.startActivity(i);
	}
}
