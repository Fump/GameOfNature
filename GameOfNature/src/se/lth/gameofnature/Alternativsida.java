package se.lth.gameofnature;

import se.lth.gameofnature.data.Database;
import se.lth.gameofnature.data.Team;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

public class Alternativsida extends Activity implements OnItemSelectedListener {
	public static final String ACTIVITY_NAME = "OPTIONS_ACTIVITY";
	
	private ImageButton currentColor;
	private ImageView image;
	private Spinner spinner;

	private Database db;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alternativsida);

		//Förvald färg vid start
		LinearLayout paintLayout = (LinearLayout)findViewById(R.id.choose_colors);
		currentColor = (ImageButton)paintLayout.getChildAt(0);
		currentColor.setImageDrawable(getResources().getDrawable(R.drawable.colorpressed));


		initSpinner();
	}

	private void initSpinner() {
		image = (ImageView) findViewById(R.id.character_image);
		spinner = (Spinner) findViewById(R.id.character_spinner);
		// en arrayAdapter med val från item listan i string.xml
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.character_list,
				android.R.layout.simple_spinner_item);
		// bestämmer layouten på listan
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		// sätter spinner till det man valde
		spinner.setOnItemSelectedListener(this);
	}

	public void onItemSelected(AdapterView<?> parent, View v,
			int pos, long id) {
		TypedArray charImg = getResources().obtainTypedArray(R.array.character_img_list);
		image.setImageResource(charImg.getResourceId(spinner.getSelectedItemPosition(), -1));
		//Fakta till varje markör med popUp box.             
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);  
		switch(spinner.getSelectedItemPosition()){
		case 1:
			alertbox.setMessage("Android Gubben är ful!");
			alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {        
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			alertbox.show();
			break;
		case 2:  
			alertbox.setMessage("Nyckelpiggan är fläkigt!");
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

	// Lyssna på tryck av färg
	public void colorClicked(View view) {
		if (view != currentColor) {
			ImageButton newColor = (ImageButton) view;
			// ändra backgrundsfärgen vid tryck så att man se vilken färg man
			// har valt
			newColor.setImageDrawable(getResources().getDrawable(
					R.drawable.colorpressed));
			currentColor.setImageDrawable(getResources().getDrawable(
					R.drawable.colorbackgroundproperties));
			currentColor = newColor;
		}
	}

	public void nextScreen(View view) {
		Intent intent = new Intent(this, GameBoardActivity.class);
		db = new Database(this);
		db.open();
		EditText name = (EditText) findViewById(R.id.LagnamnText);
		String iconId = Integer.toString(image.getId());
		String colorCode = (String) currentColor.getTag();
		db.createTeam(name.getText().toString(), iconId, colorCode, 0, 0, 0, 0);		
		Toast.makeText(this,db.table(), Toast.LENGTH_LONG).show();
		db.close();
		db = null;
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alternativsida, menu);
		return true;
	}

}
