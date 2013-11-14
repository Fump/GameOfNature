package se.lth.gameofnature;

import java.io.InputStream;
import java.util.ArrayList;

import se.lth.gameofnature.data.PlayerSession;
import se.lth.gameofnature.data.XMLReader;
import se.lth.gameofnature.gamemap.GameMap;
import se.lth.gameofnature.gamemap.LocationHandler;
import se.lth.gameofnature.gamemap.markers.MyLocationMarker;
import se.lth.gameofnature.gamemap.markers.TaskMarker;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameBoardActivity extends Activity {
	private GameMap map;
	private MyLocationMarker myLocation;
	private LocationHandler mLocationHandler;

	public static final String INTENT_SOURCE = "INTENT_SOURCE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_board);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_board, menu);
		return true;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if(mLocationHandler != null)
			mLocationHandler.stopTracking();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		initMapIfNeeded();
		initLocationHandlerIfNeeded();
		initTaskMarkers();

	}
	
	/* Sets up a GameMap connected to Google maps if one does not already exist.
	 */
	private void initMapIfNeeded() { 
		if(map == null) {
			GoogleMap gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
			        .getMap();
			
			map = new GameMap(gMap, this);
			
			Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.bug);
			
			myLocation = new MyLocationMarker(GameMap.ANDREASSONS_MEDOW, icon, 
					"My Location", "Here I am");
			
			map.addGameMarker(myLocation);
		}
	}
	
	/*
	 * Inits all the TaskMarkers by adding them to the map and tracking them.
	 * currently adds only on testmarker in malmö.
	 */
	private void initTaskMarkers() {
		InputStream is = getResources().openRawResource(R.raw.map);
		
		ArrayList<TaskMarker> markers = XMLReader.readTaskMarkers(is);
		
		for(TaskMarker m : markers) {
			addTaskMarker(m);
		}
	}
	
	/* Adds a new TaskMarker to the map and starts tracking it.
	 * if the TaskMarker already exists it is fetched from the PlayerSession and tracked.
	 */
	private void addTaskMarker(TaskMarker m) {
		PlayerSession.addTaskMarker(m.getId(), m);
		map.addGameMarker(m);
		mLocationHandler.trackTaskMarker(m);
	}
	
	/* Sets up a locationHandler object to track the current location of the user 
	 * is only set up if one does not already exist. Also starts tracking the user.
	 */
	public void initLocationHandlerIfNeeded() {
		if(mLocationHandler == null) {
			if(mLocationHandler == null)
				mLocationHandler = new LocationHandler(this, map, myLocation);
		}
		
		mLocationHandler.startTracking();
	}
}
