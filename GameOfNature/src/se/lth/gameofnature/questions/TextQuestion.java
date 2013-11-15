package se.lth.gameofnature.questions;

import se.lth.gameofnature.QuestionActivity;
import android.content.Context;
import android.content.Intent;

public class TextQuestion extends Question {

	public TextQuestion(String id, String questionTxt,
			String[] answers, int correctAnswer) {
		super(id, Question.QUESTION_TYPE_TEXT, questionTxt, answers, correctAnswer);
	}
}
