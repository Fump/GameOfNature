package se.lth.gameofnature;

import android.os.Bundle;
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
import se.lth.gameofnature.questions.Question;


public class QuestionActivity extends Activity {
	
	public static final String ACTIVITY_NAME = "QUESTION_ACTIVITY";
	/*
	 * Statics för frågan
	 */
	private String question; //Stoppa in frågan
	private String[] answers; //Stoppa in svaren
	private int correctAnswer; //Eftersom det går att hämta strängen direkt från en knapp kan vi stoppa in hela svaret här och göra en compareTo.
	private Random rand; // för att slumpa en fråga ur xml:en, använd nrQuestions i .xml:en

	private String sourceTaskMarkerId;
	
	 /*  övriga variabler
	 */
	private String filepath = "res/layout/activity_question.xml";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setQuestion();
	}
	
	/*
	 * Anropar statisk klass med frågor, ej implementerat än
	 * Kopplar klassens privata textview & button-objekt till objekten i layouten
	 */
	private void setQuestion(){
		Bundle extras = getIntent().getExtras();
		
		if(extras != null) {
			sourceTaskMarkerId = extras.getString(TaskMarker.TASK_MARKER_ID);
			
			question = extras.getString(Question.QUESTION_TXT);
			answers = extras.getStringArray(Question.ANSWERS);
			correctAnswer = extras.getInt(Question.CORRECT_ANSWER);
			
			TextView questionView = (TextView)findViewById(R.id.question_content);
			questionView.setText(question);
			
			Button answerButton1 = (Button)findViewById(R.id.question_btnAnswer1);
			answerButton1.setText(answers[0]);
			
			Button answerButton2 = (Button)findViewById(R.id.question_btnAnswer2);
			answerButton2.setText(answers[1]);
			
			Button answerButton3 = (Button)findViewById(R.id.question_btnAnswer3);
			answerButton3.setText(answers[2]);
			
			Toast.makeText(this, answerButton3.getText() + " " + correctAnswer, Toast.LENGTH_LONG).show();
			
			Button answerButton4 = (Button)findViewById(R.id.question_btnAnswer4);
			answerButton4.setText(answers[3]);
		}
	}

	public void tryAnswer(View view){
		Button b = (Button) view;
		
		System.out.println(b.getText().toString());
		
		boolean isCorrect;
		
		if(b.getText().toString().compareTo(answers[correctAnswer])==0){
			System.out.println("Rätt");
			//när grafiken har fixats måste vi ändra denna till en bild
			b.setTextColor(Color.GREEN);
			isCorrect = true;
		}else {
			System.out.println("fel");
			b.setTextColor(Color.RED);
			isCorrect = false;
			//gul: 0xffff00
			// Här sätter vi färg på "rätt"-knappen; detta måste vi göra i samband med setQuestion
		}
		long now, t0;
		now = t0 = System.currentTimeMillis();
		while(now-t0<3000){						// alternativt en realtidslösning
			now = System.currentTimeMillis();
			System.out.println("räknare");
		}
		
		sendBackToGameBoard(isCorrect);
	}
	
	private void sendBackToGameBoard(boolean isCorrectAnswer) {
		Intent intent = new Intent(this, GameBoardActivity.class);
		
		intent.putExtra(GameBoardActivity.INTENT_SOURCE, QuestionActivity.ACTIVITY_NAME);
		intent.putExtra(Question.USER_ANSWER, isCorrectAnswer);
		intent.putExtra(TaskMarker.TASK_MARKER_ID, sourceTaskMarkerId);
		
		startActivity(intent);
		
	}
}
