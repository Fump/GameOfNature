package se.lth.gameofnature;

import se.lth.gameofnature.data.PlayerSession;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		TaskMarker marker = PlayerSession.getCurrentSessionInstance().getTaskMarker(currentMarkerId);
		
		TextView title = (TextView)findViewById(R.id.task_dialog_title);
		title.setText(marker.getTitle());
		
		TextView content = (TextView)findViewById(R.id.task_dialog_content);
		content.setText(marker.getInfoTxt());
	}
	
	public void okClicked(View v) {
		//Starta fråga
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
