package se.lth.gameofnature;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class Alternativsida extends Activity implements OnItemSelectedListener {
	private ImageButton currentColor;
	private ImageView image;
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alternativsida);
		//F�rvald f�rg vid start
		LinearLayout paintLayout = (LinearLayout)findViewById(R.id.choose_colors);
		currentColor = (ImageButton)paintLayout.getChildAt(0);
		currentColor.setImageDrawable(getResources().getDrawable(R.drawable.colorpressed));

		initSpinner();
	}
	private void initSpinner() {	
		image = (ImageView) findViewById(R.id.character_image);
		spinner = (Spinner) findViewById(R.id.character_spinner);
		//en arrayAdapter med val fr�n item listan i string.xml
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.character_list, android.R.layout.simple_spinner_item);
		//best�mmer layouten p� listan
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		//s�tter spinner till det man valde
		spinner.setOnItemSelectedListener(this);
	}
	@SuppressLint("Recycle")
	public void onItemSelected(AdapterView<?> parent, View v,
			int pos, long id) {
		TypedArray charImg = getResources().obtainTypedArray(R.array.character_img_list);
		image.setImageResource(charImg.getResourceId(spinner.getSelectedItemPosition(), -1));
		//Fakta till varje mark�r med popUp box.             
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);  
		switch(spinner.getSelectedItemPosition()){
		case 1:
			alertbox.setMessage("Android Gubben �r ful!");
			alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {        
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			alertbox.show();
			break;
		case 2:  
			alertbox.setMessage("Nyckelpiggan �r fl�kigt!");
			alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {        
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			alertbox.show();
		break;
		} 
	}
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub		
	}
	//Lyssna p� tryck av f�rg
	public void colorClicked(View view){
		if(view!=currentColor){
			ImageButton newColor = (ImageButton)view;
			//�ndra backgrundsf�rgen vid tryck s� att man se vilken f�rg man har valt
			newColor.setImageDrawable(getResources().getDrawable(R.drawable.colorpressed));
			currentColor.setImageDrawable(getResources().getDrawable(R.drawable.colorbackgroundproperties));
			currentColor=newColor;
		}	
	}

	public void nextScreen(View view) {
		Intent intent = new Intent(this, GameBoardActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alternativsida, menu);
		return true;
	}

}
