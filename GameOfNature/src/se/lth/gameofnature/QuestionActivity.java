package se.lth.gameofnature;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import java.util.*;

import se.lth.gameofnature.questions.Question;


public class QuestionActivity extends Activity {

	/*
	 * Statics f�r fr�gan
	 */
	private String question; //Stoppa in fr�gan
	private String[] answers; //Stoppa in svaren
	private int correctAnswer; //Eftersom det g�r att h�mta str�ngen direkt fr�n en knapp kan vi stoppa in hela svaret h�r och g�ra en compareTo.
	private Random rand; // f�r att slumpa en fr�ga ur xml:en, anv�nd nrQuestions i .xml:en

	 * �vriga variabler
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
	 * Anropar statisk klass med fr�gor, ej implementerat �n
	 * Kopplar klassens privata textview & button-objekt till objekten i layouten
	 */
	private void setQuestion(){
		Bundle extras = getIntent().getExtras();
		
		if(extras != null) {
			question = extras.getString(Question.QUESTION_TXT);
			answers = extras.getStringArray(Question.ANSWERS);
			correctAnswer = extras.getInt(Question.CORRECT_ANSWER);
			
			TextView questionView = (TextView)findViewById(R.id.question_content);
			questionView.setText(question);
			
			Button answerButton1 = (Button)findViewById(R.id.question_btnAnswer1);
			answerButton1.setText(answers[0]);
			
			Button answerButton2 = (Button)findViewById(R.id.question_btnAnswer2);
			answerButton1.setText(answers[1]);
			
			Button answerButton3 = (Button)findViewById(R.id.question_btnAnswer3);
			answerButton1.setText(answers[2]);
			
			Button answerButton4 = (Button)findViewById(R.id.question_btnAnswer4);
			answerButton1.setText(answers[3]);
	}

	public void tryAnswer(View view){
		Button b = (Button) view;
		
		System.out.println(b.getText().toString());
		
		if(b.getText().toString().compareTo(answers[correctAnswer])==0){
			System.out.println("R�tt");
			b.setTextColor(0x228b22); //n�r grafiken har fixats m�ste vi �ndra denna till en bild
		}else {
			System.out.println("fel");
			b.setTextColor(0xff0000);
			//gul: 0xffff00
			// H�r s�tter vi f�rg p� "r�tt"-knappen; detta m�ste vi g�ra i samband med setQuestion
		}
		long now, t0;
		now = t0 = System.currentTimeMillis();
		while(now-t0<3000){						// alternativt en realtidsl�sning
			now = System.currentTimeMillis();
			System.out.println("r�knare");
		}
		Intent intent = new Intent(this, GameBoardActivity.class);
		startActivity(intent);	//H�r m�ste vi antagligen skicka med spelardata, etc senare

	}
}
