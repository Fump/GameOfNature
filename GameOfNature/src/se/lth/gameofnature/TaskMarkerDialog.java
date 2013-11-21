package se.lth.gameofnature;

import java.util.Iterator;

import se.lth.gameofnature.data.PlayerSession;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import se.lth.gameofnature.questions.Question;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TaskMarkerDialog extends Activity {
	public static boolean active = false;
	
	private String currentMarkerId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_dialog);
		
		Bundle extras = getIntent().getExtras();	
		currentMarkerId = extras.getString(TaskMarker.TASK_MARKER_ID);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TaskMarker currentMarker = PlayerSession.getCurrentSessionInstance(this).getTaskMarker(currentMarkerId);
		
		TextView title = (TextView)findViewById(R.id.task_dialog_title);
		title.setText(currentMarker.getTitle());
		
		TextView content = (TextView)findViewById(R.id.task_dialog_content);
		content.setText(currentMarker.getInfoTxt());
	}
	
	public void okClicked(View v) {
		Question q = PlayerSession.getCurrentSessionInstance(this).getTaskMarker(currentMarkerId).getNextQuestion();
		q.startQuestionActivity(this);
	}
	
	public void cancelClicked(View v) {
		finish();
	}
	
    @Override
    public void onStart() {
       super.onStart();
       active = true;
    } 

    @Override
    public void onStop() {
       super.onStop();
       active = false;
    }
}
