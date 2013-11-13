package se.lth.gameofnature.questions;

import se.lth.gameofnature.QuestionActivity;
import android.content.Context;
import android.content.Intent;

public class TextQuestion extends Question {

	public TextQuestion(String id, String questionTxt,
			String[] answers, int correctAnswer) {
		super(id, Question.QUESTION_TYPE_TEXT, questionTxt, answers, correctAnswer);
	}

	@Override
	public void startQuestionActivity(Context sourceContext) {
		Intent i = new Intent(sourceContext, QuestionActivity.class);
		
		i.putExtra(QUESTION_ID, id);
		i.putExtra(QUESTION_TXT, questionTxt);
		i.putExtra(ANSWERS, answers);
		i.putExtra(CORRECT_ANSWER, correctAnswer);
		
		sourceContext.startActivity(i);
		
	}

}
