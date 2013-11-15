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

	/*
	 * Statics för frågan
	 */
	private static String QUESTION; //Stoppa in frågan
	private static String[] ANSWERS = new String[4]; //Stoppa in svaren
	private static String CORRECT_ANSWER; //Eftersom det går att hämta strängen direkt från en knapp kan vi stoppa in hela svaret här och göra en compareTo.
	private Random rand; // för att slumpa en fråga ur xml:en, använd nrQuestions i .xml:en
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
	 * Anropa xml-fil med frågor, ej implementerat än
	 * I samband med att denna implementeras måste vi även se till att det skapas objekt för samtliga buttons & textfältet på layouten.
	 */
	private void setQuestion(){
		CORRECT_ANSWER = "6";


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
