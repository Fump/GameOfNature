package com.example.androidtest;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private NotepadDataSource source;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		source = new NotepadDataSource(this);
		source.open();
		
		final ListView listView = (ListView)findViewById(R.id.listview);
		
		final List<NotepadEntry> list = source.getAllEntires();
		
		final ArrayAdapter adapter = new ArrayAdapter(this,  
				android.R.layout.simple_expandable_list_item_1, list);
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				NotepadEntry entry = list.get(position);
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				
				builder.setTitle(entry.getTitle())
				.setMessage(entry.getText());
				
				builder.create().show();
			}
		});
		
		Button addBtn = (Button)findViewById(R.id.add_button);
		
		addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	  @Override
	  protected void onResume() {
	    source.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    source.close();
	    super.onPause();
	  }

}
