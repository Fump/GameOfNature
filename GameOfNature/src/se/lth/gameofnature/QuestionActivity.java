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

	private static String CORRECT_ANSWER; //Eftersom det går att hämta strängen direkt från en knapp kan vi stoppa in hela svaret här och göra en compareTo.
	private Random rand; // för att slumpa en fråga ur xml:en, använd nrQuestions i .xml:en			kanske inte behövs
	private TextView questionTitle, questionContent; 
	private Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
	/*
	 * övriga variabler
	 */
	private String filepath = "res/layout/activity_question.xml";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		setQuestion();
	}
	/*
	 * Anropar statisk klass med frågor, ej implementerat än
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
//		btnAnswer2.setText(text);				Dessa gör vi klart när den statiska klassen är löst
//		btnAnswer3.setText(text);
//		btnAnswer4.setText(text);
		
		CORRECT_ANSWER = "6";// Hämta från XML-fil
		


	}

	public void tryAnswer(View view){
		Button b = (Button) view;
		System.out.println(b.getText().toString());
		if(b.getText().toString().compareTo(CORRECT_ANSWER)==0){
			System.out.println("Rätt");
			b.setTextColor(0x228b22); //när grafiken har fixats måste vi ändra denna till en bild
		}else {
			System.out.println("fel");
			b.setTextColor(0xff0000);
			//gul: 0xffff00
			// Här sätter vi färg på "rätt"-knappen; detta måste vi göra i samband med setQuestion
		}
		long now, t0;
		now = t0 = System.currentTimeMillis();
		while(now-t0<3000){						// alternativt en realtidslösning
			now = System.currentTimeMillis();
			System.out.println("räknare");
		}
		Intent intent = new Intent(this, GameBoardActivity.class);
		startActivity(intent);	//Här måste vi antagligen skicka med spelardata, etc senare

	}
}
