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


public class QuestionActivity extends Activity {

	/*
	 * Statics för frågan
	 */
	private static String QUESTION; //Stoppa in frågan
	private static String[] ANSWERS = new String[4]; //Stoppa in svaren
	private static String CORRECT_ANSWER; //Eftersom det går att hämta strängen direkt från en knapp kan vi stoppa in hela svaret här och göra en compareTo.

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
	 * Här får vi senare implementera anrop till databasen för att hämta information om en fråga. 
	 * tanken från min sida är att vi kör en slump-int för att bestämma vilken fråga som ska hämtas,
	 * och sedan hämtar infon. vi använder sedan den för att sätta statics i denna klass
	 * 
	 * vid detta laget får det dock räcka med förbestämda svar
	 * 
	 * vi behöver ändra "strings.xml" för att få ut frågorna på knapparna...
	 * 
	 * alternativt försöker vi på något sätt få med hela RelativeLayout-grejen
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
		}
			

	}
}
