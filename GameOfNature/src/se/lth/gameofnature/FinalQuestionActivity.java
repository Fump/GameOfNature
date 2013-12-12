package se.lth.gameofnature;

import se.lth.gameofnature.gamemap.markers.TaskMarker;
import se.lth.gameofnature.gametimer.GameTimer;
import se.lth.gameofnature.questions.Question;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FinalQuestionActivity extends Activity{
	
	public static final String ACTIVITY_NAME = "FINALQUESTION_ACTIVITY";
	
	private String question; 
	private String code;
	private String sourceTaskMarkerId;
	private EditText codeBox;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finalquestion);
		
		((Button)findViewById(R.id.approveButton)).setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						((Button)findViewById(R.id.approveButton)).setBackgroundResource(R.drawable.button_down);
						tryCode();
						
					}
				});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	
		setQuestion();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
	}
	
	private void setQuestion() {
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			sourceTaskMarkerId = extras.getString(TaskMarker.TASK_MARKER_ID);

			question = extras.getString(Question.QUESTION_TXT);
			code = extras.getString(Question.FINAL_CODE);

			TextView questionView = (TextView) findViewById(R.id.finalquestion_content);
			questionView.setText(question);
			
			codeBox = (EditText) findViewById(R.id.finalquestion_codebox);
		}
	}
	
	private void tryCode(){
		if((codeBox.getText().toString().compareTo(code)==0)){
			Intent intent = new Intent(this, WinnerActivity.class);
			startActivity(intent);
		}else
			Toast.makeText(this, "Felaktig kod. Försök igen!", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onBackPressed() {
		//DO NOTHING
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                                                        INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
	
}
