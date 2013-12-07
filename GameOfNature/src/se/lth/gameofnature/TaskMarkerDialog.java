package se.lth.gameofnature;

import java.util.Iterator;

import se.lth.gameofnature.data.GameMapData;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import se.lth.gameofnature.questions.Question;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TaskMarkerDialog extends Activity {
	public static boolean active = false;
	
	private String currentMarkerId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_task_dialog);
		Bundle extras = getIntent().getExtras();	
		currentMarkerId = extras.getString(TaskMarker.TASK_MARKER_ID);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TaskMarker currentMarker = GameMapData.getCurrentSessionInstance(this).getTaskMarker(currentMarkerId);
		
		TextView title = (TextView)findViewById(R.id.task_dialog_title);
		title.setText(currentMarker.getTitle());
		
		TextView content = (TextView)findViewById(R.id.task_dialog_content);
		
		content.setText(getContentMsg());
	}
	
	public void okClicked(View v) {
		
		TaskMarker currentMarker = GameMapData.getCurrentSessionInstance(this).getTaskMarker(currentMarkerId);
		
		if(currentMarker.getStatus() == TaskMarker.STATUS_ACTIVE)
			currentMarker.getNextQuestion().startQuestionActivity(this, currentMarkerId);
		else
			finish();
	}
	
	public void cancelClicked(View v) {
		finish();
	}
	
	private String getContentMsg() {
		TaskMarker currentMarker = GameMapData.getCurrentSessionInstance(this).getTaskMarker(currentMarkerId);
		
		if(currentMarker.getStatus() == TaskMarker.STATUS_ACTIVE) {
			return currentMarker.getInfoTxt();
		} else if(currentMarker.getStatus() == TaskMarker.STATUS_LOCKED) {
			return "Denna uppgiftspunkten är låst! \n" +
					"Gå till en annan uppgiftspunkt och svara på en fråga för att " +
					"låsa upp punkten igen!";
		} else {
			return "Denna uppgiftspunkten är redan avklarad \n"  + 
					"klara resten av punkterna på kartan för att hitta skatten!";
		}
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
