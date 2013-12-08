package se.lth.gameofnature;

import se.lth.gameofnature.gametimer.GameTimer;
import android.app.Activity;
import android.os.Bundle;

public class FinalQuestionActivity extends Activity{
	
	public static final String ACTIVITY_NAME = "FINALQUESTION_ACTIVITY";
	
	private String question; 
	private String[] answers;
	private int correctAnswer;

	private String sourceTaskMarkerId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finalquestion);
	}
	
	@Override
	public void onBackPressed() {
		//DO NOTHING
	}

}
