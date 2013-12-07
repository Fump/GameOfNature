package se.lth.gameofnature;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Color;

import java.util.*;

import se.lth.gameofnature.gamemap.markers.TaskMarker;
import se.lth.gameofnature.gametimer.GameTimer;
import se.lth.gameofnature.questions.Question;

public class QuestionActivity extends Activity {

	public static final String ACTIVITY_NAME = "QUESTION_ACTIVITY";
	/*
	 * Statics för frågan
	 */
	private String question; // Stoppa in frågan
	private String[] answers; // Stoppa in svaren
	private int correctAnswer; // Eftersom det går att hämta strängen direkt
								// från en knapp kan vi stoppa in hela svaret
								// här och göra en compareTo.

	private String sourceTaskMarkerId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		setQuestion();
		
		if(!GameTimer.isRunning())
			GameTimer.startTimer(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		GameTimer.stopTimer();
	}

	/*
	 * Anropar statisk klass med frågor, ej implementerat än Kopplar klassens
	 * privata textview & button-objekt till objekten i layouten
	 */
	private void setQuestion() {
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			sourceTaskMarkerId = extras.getString(TaskMarker.TASK_MARKER_ID);

			question = extras.getString(Question.QUESTION_TXT);
			answers = extras.getStringArray(Question.ANSWERS);
			correctAnswer = extras.getInt(Question.CORRECT_ANSWER);

			TextView questionView = (TextView) findViewById(R.id.question_content);
			questionView.setText(question);

			Button answerButton1 = (Button) findViewById(R.id.question_btnAnswer1);
			answerButton1.setText(answers[0]);

			Button answerButton2 = (Button) findViewById(R.id.question_btnAnswer2);
			answerButton2.setText(answers[1]);

			Button answerButton3 = (Button) findViewById(R.id.question_btnAnswer3);
			answerButton3.setText(answers[2]);

			Toast.makeText(this, answerButton3.getText() + " " + correctAnswer,
					Toast.LENGTH_LONG).show();

			Button answerButton4 = (Button) findViewById(R.id.question_btnAnswer4);
			answerButton4.setText(answers[3]);
		}
	}

	public void tryAnswer(View view) {
		Button b = (Button) view;

		System.out.println(b.getText().toString());

		boolean isCorrect;

		if (b.getText().toString().compareTo(answers[correctAnswer]) == 0) {
			System.out.println("Rätt");
			// när grafiken har fixats måste vi ändra denna till en bild
			b.setBackgroundResource(R.drawable.green_button);
			isCorrect = true;
		} else {
			System.out.println("fel");
			//Sätter det felaktiga svaret till röds
			b.setBackgroundResource(R.drawable.red_button);
			isCorrect = false;
			
			//Ändra rätt svar till gul
			if (correctAnswer == 0) {
				Button b1 = (Button) findViewById(R.id.question_btnAnswer1);
				b1.setBackgroundResource(R.drawable.yellow_button);
			}
			else if (correctAnswer == 1) {
				Button b2 = (Button) findViewById(R.id.question_btnAnswer2);
				b2.setBackgroundResource(R.drawable.yellow_button);
			}
			else if (correctAnswer == 2) {
				Button b3 = (Button) findViewById(R.id.question_btnAnswer3);
				b3.setBackgroundResource(R.drawable.yellow_button);
			}
			else if (correctAnswer == 3) {
				Button b4 = (Button) findViewById(R.id.question_btnAnswer4);
				b4.setBackgroundResource(R.drawable.yellow_button);
			}
			// gul: 0xffff00
			// Här sätter vi färg på "rätt"-knappen; detta måste vi göra i
			// samband med setQuestion
		}
		
		
		
		//Fixat ny delay som inte buggar knapparnas grafik.
		
		setButtonsEnabled(false);
		
		Handler myHandler = new Handler();
		myHandler.postDelayed(getDelayedSendBack(isCorrect), 3000);
	}

	private void sendBackToGameBoard(boolean isCorrectAnswer) {
		Intent intent = new Intent(this, GameBoardActivity.class);

		intent.putExtra(GameBoardActivity.INTENT_SOURCE,
				QuestionActivity.ACTIVITY_NAME);
		intent.putExtra(Question.USER_ANSWER, isCorrectAnswer);
		intent.putExtra(TaskMarker.TASK_MARKER_ID, sourceTaskMarkerId);
		
		setButtonsEnabled(true);
		
		startActivity(intent);

	}
	
	private void setButtonsEnabled(boolean enabled) {
		Button b1 = (Button) findViewById(R.id.question_btnAnswer1);
		Button b2 = (Button) findViewById(R.id.question_btnAnswer2);
		Button b3 = (Button) findViewById(R.id.question_btnAnswer3);
		Button b4 = (Button) findViewById(R.id.question_btnAnswer4);
		
		b1.setEnabled(enabled);
		b2.setEnabled(enabled);
		b3.setEnabled(enabled);
		b4.setEnabled(enabled);
	}
	
	private Runnable getDelayedSendBack(final boolean correctAnswer) {
		return new Runnable() {
			
			@Override
			public void run() {
				sendBackToGameBoard(correctAnswer);
			}
		};
	}
	
	@Override
	public void onBackPressed() {
		//DO NOTHING
	}
	
}
