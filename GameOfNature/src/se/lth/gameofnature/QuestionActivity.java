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


public class QuestionActivity extends Activity {

	private static String CORRECT_ANSWER; //Eftersom det g�r att h�mta str�ngen direkt fr�n en knapp kan vi stoppa in hela svaret h�r och g�ra en compareTo.
	private Random rand; // f�r att slumpa en fr�ga ur xml:en, anv�nd nrQuestions i .xml:en			kanske inte beh�vs
	private TextView questionTitle, questionContent; 
	private Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
	/*
	 * �vriga variabler
	 */
	private String filepath = "res/layout/activity_question.xml";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		setQuestion();
	}
	/*
	 * Anropar statisk klass med fr�gor, ej implementerat �n
	 * Kopplar klassens privata textview & button-objekt till objekten i layouten
	 */
	private void setQuestion(){
		questionTitle = (TextView)findViewById(R.id.question_title);
		questionContent = (TextView)findViewById(R.id.question_content);
		btnAnswer1 = (Button)findViewById(R.id.question_btnAnswer1);
		btnAnswer2 = (Button)findViewById(R.id.question_btnAnswer2);
		btnAnswer3 = (Button)findViewById(R.id.question_btnAnswer3);
		btnAnswer4 = (Button)findViewById(R.id.question_btnAnswer4);
		
//		questionTitle.setText(text); 
//		questionContent.setText(text);
//		btnAnswer1.setText(text);
//		btnAnswer2.setText(text);				Dessa g�r vi klart n�r den statiska klassen �r l�st
//		btnAnswer3.setText(text);
//		btnAnswer4.setText(text);
		
		CORRECT_ANSWER = "6";// H�mta fr�n XML-fil
		


	}

	public void tryAnswer(View view){
		Button b = (Button) view;
		System.out.println(b.getText().toString());
		if(b.getText().toString().compareTo(CORRECT_ANSWER)==0){
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
