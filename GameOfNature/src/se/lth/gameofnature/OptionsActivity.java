package se.lth.gameofnature;

import se.lth.gameofnature.data.Database;
import se.lth.gameofnature.data.Team;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsActivity extends Activity implements OnItemSelectedListener {
	public static final String ACTIVITY_NAME = "OPTIONS_ACTIVITY";

	private ImageButton currentColor;
	private ImageView image;
	private Spinner spinner;
	private TypedArray charImg;
	private AlertDialog.Builder alertbox;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);

		//F�rvald f�rg vid start
		LinearLayout paintLayout = (LinearLayout)findViewById(R.id.choose_colors);
		currentColor = (ImageButton)paintLayout.getChildAt(0);
		currentColor.setImageDrawable(getResources().getDrawable(R.drawable.colorpressed));


		initSpinner();
	}

	private void initSpinner() {
		image = (ImageView) findViewById(R.id.character_image);
		spinner = (Spinner) findViewById(R.id.character_spinner);
		// en arrayAdapter med val fr�n item listan i string.xml
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.character_list,
				android.R.layout.simple_spinner_item);
		// best�mmer layouten p� listan
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		// s�tter spinner till det man valde
		spinner.setOnItemSelectedListener(this);
	}

	public void onItemSelected(AdapterView<?> parent, View v,
			int pos, long id) {
		charImg = getResources().obtainTypedArray(R.array.character_img_list);
		image.setImageResource(charImg.getResourceId(spinner.getSelectedItemPosition(), -1));
		//Fakta till varje mark�r med popUp box.             
		
		Intent dialogIntent = new Intent(this, Dialog.class);
		
		switch(spinner.getSelectedItemPosition()){
		case 1:
			dialogIntent.putExtra(Dialog.DIALOG_TXT, 
					"Visste du att grodor sv�ljer sin mat hel?");
			break;
		case 2:  
			dialogIntent.putExtra(Dialog.DIALOG_TXT, 
					"Visste du att det finns giftiga fj�rilar?");
			break;
		case 3:  
			dialogIntent.putExtra(Dialog.DIALOG_TXT, 
					"Visste du att nyckelpigan �r k�tt�tare?");
			break;
		} 
		
		if(dialogIntent.hasExtra(Dialog.DIALOG_TXT))
			startActivity(dialogIntent);
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
	}

	// Lyssna p� tryck av f�rg
	public void colorClicked(View view) {
		if (view != currentColor) {
			ImageButton newColor = (ImageButton) view;
			// �ndra backgrundsf�rgen vid tryck s� att man se vilken f�rg man
			// har valt
			newColor.setImageDrawable(getResources().getDrawable(
					R.drawable.colorpressed));
			currentColor.setImageDrawable(getResources().getDrawable(
					R.drawable.colorbackgroundproperties));
			currentColor = newColor;
		}
	}

	public void nextScreen(View view) {
		Button button = (Button) findViewById(R.id.alternative_confirmButton);
		button.setBackgroundResource(R.drawable.button_down);
		
		
		Intent intent = new Intent(this, GameBoardActivity.class);
		
		Database db = new Database(this);
		
		db.open();
		
		EditText name = (EditText) findViewById(R.id.LagnamnText);
		int iconId = charImg.getResourceId(spinner.getSelectedItemPosition(), -1);
		String colorCode = (String) currentColor.getTag();
		
		db.createTeam(name.getText().toString(), iconId, colorCode, 0, 0, 0, 0);		
		
		db.close();
		db = null;
		
		String lagnamn = name.getText().toString();
		alertbox = new AlertDialog.Builder(this);
		if(spinner.getSelectedItemPosition()==0 || lagnamn.matches("") ){
			button.setBackgroundResource(R.drawable.button_up);
			alertbox.setMessage("Var god v�lj lagnamn och Spelkarakt�r");
			alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {        
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			
			alertbox.show();
		}else{
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alternativsida, menu);
		return true;
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                                                        INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

}
