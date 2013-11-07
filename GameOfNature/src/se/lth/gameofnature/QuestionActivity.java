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
	 * Statics f�r fr�gan
	 */
	private static String QUESTION; //Stoppa in fr�gan
	private static String[] ANSWERS = new String[4]; //Stoppa in svaren
	private static String CORRECT_ANSWER; //Eftersom det g�r att h�mta str�ngen direkt fr�n en knapp kan vi stoppa in hela svaret h�r och g�ra en compareTo.

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
	 * H�r f�r vi senare implementera anrop till databasen f�r att h�mta information om en fr�ga. 
	 * tanken fr�n min sida �r att vi k�r en slump-int f�r att best�mma vilken fr�ga som ska h�mtas,
	 * och sedan h�mtar infon. vi anv�nder sedan den f�r att s�tta statics i denna klass
	 * 
	 * vid detta laget f�r det dock r�cka med f�rbest�mda svar
	 * 
	 * vi beh�ver �ndra "strings.xml" f�r att f� ut fr�gorna p� knapparna...
	 * 
	 * alternativt f�rs�ker vi p� n�got s�tt f� med hela RelativeLayout-grejen
	 */
	private void setQuestion(){
		CORRECT_ANSWER = "6";


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
		}
			

	}
}
