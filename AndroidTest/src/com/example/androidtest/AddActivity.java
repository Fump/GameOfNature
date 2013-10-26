package com.example.androidtest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AddActivity extends Activity {
	public static String NEW_NOTEPAD_ENTRY = "NEW_NOTEPAD_ENTRY";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		final TextView title = (TextView)findViewById(R.id.title);
		final TextView text = (TextView)findViewById(R.id.text);
		
		Button save = (Button)findViewById(R.id.addentry);
		
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				NotepadEntry entry = new NotepadEntry(
						title.getText().toString(),
						text.getText().toString());
				
				NotepadDataSource source = new NotepadDataSource(AddActivity.this);
				
				source.open();
				source.addEntry(entry);
				source.close();
				
				Intent i = new Intent(AddActivity.this, MainActivity.class);
				startActivity(i);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

}
