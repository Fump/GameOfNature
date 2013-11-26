package se.lth.gameofnature;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import se.lth.gameofnature.data.Database;
import se.lth.gameofnature.data.GameMapData;
import se.lth.gameofnature.data.Team;
import se.lth.gameofnature.data.XMLReader;
import se.lth.gameofnature.gamemap.GameMap;
import se.lth.gameofnature.gamemap.LocationHandler;
import se.lth.gameofnature.gamemap.markers.MyLocationMarker;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import se.lth.gameofnature.questions.Question;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GameBoardActivity extends Activity {
	private GameMap map;
	private MyLocationMarker myLocation;
	private LocationHandler mLocationHandler;

	private String markerCount = "";
	
	public static final String INTENT_SOURCE = "INTENT_SOURCE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_board);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getActionBar().setDisplayShowHomeEnabled(false);
		Drawable iconBlue = getResources().getDrawable(R.drawable.marker_icon_a_blue);
		Drawable iconGreen = getResources().getDrawable(R.drawable.marker_icon_a_green);  
		iconBlue.setAlpha(40);
		iconGreen.setAlpha(40);
		
		getMenuInflater().inflate(R.menu.game_board, menu);
		
		View count = menu.findItem(R.id.badge).getActionView();
		Button countButton = (Button)count.findViewById(R.id.notif_count);
		countButton.setText(markerCount);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if(mLocationHandler != null)
			mLocationHandler.stopTracking();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		
		if(mLocationHandler != null)
			mLocationHandler.stopTracking();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		Database db = new Database(this);
		db.open();
		
		Team teamStatus = db.getTeamStatus();
		
		initMapIfNeeded(teamStatus.getIconId());
		initLocationHandlerIfNeeded();
		
		handleIntent();
		
		setMarkerCount(GameMapData.getCurrentSessionInstance(this).getNumberDoneMarkers(), 
				GameMapData.getCurrentSessionInstance(this).getNumberOfMarkers());
		
		db.close();
	}
	
	private void handleIntent() {
		Bundle extras = getIntent().getExtras();
		
		if(extras != null) {
			String source = extras.getString(GameBoardActivity.INTENT_SOURCE);
			
			if(source.equals(QuestionActivity.ACTIVITY_NAME)) {
				
				String taskMarkerId = extras.getString(TaskMarker.TASK_MARKER_ID);
				boolean isCorrectAnswer = extras.getBoolean(Question.USER_ANSWER);
				
				TaskMarker marker = GameMapData.getCurrentSessionInstance(this).getTaskMarker(taskMarkerId);
				
				unlockAllMarkers();
				
				if(isCorrectAnswer)
					marker.setDone();
				else
					marker.setLocked();
				
				//Tillfälligt kod, bara för att kolla om man har vunnit lite snabbt!
				if(checkWin()) {
					Intent i = new Intent(this, WinnerActivity.class);
					startActivity(i);
				}
				
			} else if(source.equals(Alternativsida.ACTIVITY_NAME)) {
				
			}
		}
	}
	
	/* Sets up a GameMap connected to Google maps if one does not already exist.
	 */
	private void initMapIfNeeded(int iconId) { 
		if(map == null) {
			GoogleMap gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
			        .getMap();
			
			map = new GameMap(gMap, this);
			
			Bitmap icon = BitmapFactory.decodeResource(getResources(), iconId);
			
			myLocation = new MyLocationMarker(GameMap.ANDREASSONS_MEDOW, icon, 
					"My Location", "Here I am");
			
			map.addGameMarker(myLocation);
			
			Iterator<TaskMarker> itr = GameMapData.getCurrentSessionInstance(this).getMarkerIterator();
			
			while(itr.hasNext()) {
				map.addGameMarker(itr.next());
			}
		}
	}
	
	/* Sets up a locationHandler object to track the current location of the user 
	 * is only set up if one does not already exist. Also starts tracking the user.
	 */
	public void initLocationHandlerIfNeeded() {
		if(mLocationHandler == null) {
			mLocationHandler = new LocationHandler(this, map, myLocation);		
		} 
		
		mLocationHandler.startTracking();
		
		Iterator<TaskMarker> itr = GameMapData.getCurrentSessionInstance(this).getMarkerIterator();
		
		while(itr.hasNext()) {
			mLocationHandler.trackTaskMarker(itr.next());
		}
	}
	
	private void unlockAllMarkers() {
		Iterator<TaskMarker> itr = GameMapData.getCurrentSessionInstance(this).getMarkerIterator();
		
		while(itr.hasNext()) {
			TaskMarker m = itr.next();
			
			if(m.getStatus() == TaskMarker.STATUS_LOCKED)
				m.setActive();
		}
	}
	
	private void setMarkerCount(int current, int total) {
		markerCount = current + " / " + total;
	}
	
	private boolean checkWin() {
		return GameMapData.getCurrentSessionInstance(this).getNumberDoneMarkers() ==
				GameMapData.getCurrentSessionInstance(this).getNumberOfMarkers();
	}
}
