package se.lth.gameofnature;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TaskMarkerDialog extends Activity {
	public static boolean active = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_dialog);
		
		Bundle extras = getIntent().getExtras();
		
		String id = extras.getString(PlayerSession.TASK_MARKER_ID);
		TaskMarker marker = PlayerSession.getTaskMarker(id);
		
		TextView title = (TextView)findViewById(R.id.task_dialog_title);
		title.setText(marker.title);
		
		TextView content = (TextView)findViewById(R.id.task_dialog_content);
		content.setText(marker.snippet);
		
		Button okButton = (Button)findViewById(R.id.task_dialog_ButtonOk);
		Button cancelButton =(Button)findViewById(R.id.task_dialog_ButtonCancel);
		
		okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(TaskMarkerDialog.this, "Start Question", Toast.LENGTH_SHORT).show();
            }
        });
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	TaskMarkerDialog.this.finish();
            }
        });
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
