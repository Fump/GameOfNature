package se.lth.gameofnature;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Dialog extends Activity {
	public static final String DIALOG_TXT = "DIALOG_TXT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dialog);
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null) {
			String text = extras.getString(DIALOG_TXT);
			
			TextView t = (TextView)findViewById(R.id.dialog_content);
			
			t.setText(text);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialog, menu);
		return true;
	}
	
	public void okClicked(View view) {
		finish();
	}

}
