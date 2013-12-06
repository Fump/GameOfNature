package se.lth.gameofnature;

import java.util.HashMap;
import java.util.Iterator;

import se.lth.gameofnature.data.Database;
import se.lth.gameofnature.data.GameMapData;
import se.lth.gameofnature.data.Team;
import se.lth.gameofnature.gamemap.GameMap;
import se.lth.gameofnature.gamemap.LocationHandler;
import se.lth.gameofnature.gamemap.OrientationManager;
import se.lth.gameofnature.gamemap.markers.MyLocationMarker;
import se.lth.gameofnature.gamemap.markers.TaskMarker;
import se.lth.gameofnature.gametimer.GameTimer;
import se.lth.gameofnature.questions.Question;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.os.Bundle;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GameBoardActivity extends Activity {
	private GameMap map;
	
	private MyLocationMarker myLocation;
	private LocationHandler mLocationHandler;
	private OrientationManager mRotation;

	private Button count;
	
	private HashMap<String, Drawable> markerIcons;
	
	public static final String INTENT_SOURCE = "INTENT_SOURCE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_board);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if(mLocationHandler != null)
			mLocationHandler.stopTracking();
		
		if(mRotation != null)
			mRotation.Unregister();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		
		if(mLocationHandler != null)
			mLocationHandler.stopTracking();
		
		GameTimer.stopTimer();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		Database db = new Database(this);
		db.open();
		
		Team teamStatus = db.getTeamStatus();
		
		initMapIfNeeded(teamStatus.getIconId(), teamStatus.getColor());
		initLocationHandlerIfNeeded();
		initRotationManagerIfNeeded();
		
		initIconBar();
		handleIntent();
		
		setMarkerCount(GameMapData.getCurrentSessionInstance(this).getNumberDoneMarkers(), 
				GameMapData.getCurrentSessionInstance(this).getNumberOfMarkers());
		
		db.close();
		
		if(!GameTimer.isRunning())
			GameTimer.startTimer(this);
		
		checkWin();
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
				
				if(isCorrectAnswer){
					marker.setDone();
					markerIcons.get(marker.getId()).setAlpha(200);
				}else if(GameMapData.getCurrentSessionInstance(this).getNumberDoneMarkers()
						== GameMapData.getCurrentSessionInstance(this).getNumberOfMarkers() - 1) {
					marker.getNextQuestion().startQuestionActivity(this, marker.getId());
				}
				else{
					marker.setLocked();
				}
				//Tillf�lligt kod, bara f�r att kolla om man har vunnit lite snabbt!
				checkWin();
				
			} else if(source.equals(Alternativsida.ACTIVITY_NAME)) {
				
			}
		}
	}
	
	/* Sets up a GameMap connected to Google maps if one does not already exist.
	 */
	private void initMapIfNeeded(int iconId, String colorId) { 
		if(map == null) {
			GoogleMap gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
			        .getMap();
			
			map = new GameMap(gMap);
			
			Bitmap icon = BitmapFactory.decodeResource(getResources(), iconId);
			
			myLocation = new MyLocationMarker(GameMap.ANDREASSONS_MEDOW, icon, 
					"My Location", "Here I am");
			
			map.addGameMarker(myLocation);
			
			Iterator<TaskMarker> itr = GameMapData.getCurrentSessionInstance(this).getMarkerIterator();
			
			while(itr.hasNext()) {
				TaskMarker m = itr.next();
				m.setTeamColor(colorId);
				
				map.addGameMarker(m);
			}
			
			map.addGameMarker(GameMapData.getCurrentSessionInstance(this).getFinalMarker());
			GameMapData.getCurrentSessionInstance(this).getFinalMarker().setVisibility(false);
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
	
	private void initRotationManagerIfNeeded() {
		if(mRotation == null) {
			mRotation = new OrientationManager((SensorManager)this.getSystemService(Service.SENSOR_SERVICE), myLocation, null);
		}
		
		mRotation.Register(this, SensorManager.SENSOR_DELAY_NORMAL);
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
		count.setText(current + " / " + total);
	}
	
	private void checkWin() {
		boolean win = GameMapData.getCurrentSessionInstance(this).getNumberDoneMarkers() ==
				GameMapData.getCurrentSessionInstance(this).getNumberOfMarkers();
	
		if(win) {
			TaskMarker fMarker = GameMapData.getCurrentSessionInstance(this).getFinalMarker();
			fMarker.setVisibility(true);

			mLocationHandler.trackTaskMarker(GameMapData.getCurrentSessionInstance(this).getFinalMarker());
		}
	}
	
	private void initIconBar() {
		if(markerIcons == null) {
		
			Iterator<TaskMarker> itr = GameMapData.getCurrentSessionInstance(this).getMarkerIterator();
			markerIcons = new HashMap<String, Drawable>();
			
			LinearLayout l = (LinearLayout) findViewById(R.id.LinearBarLayout);
			
			while(itr.hasNext()) {
				
				TaskMarker m = itr.next();
				
				ImageView img = new ImageView(this);
				
				img.setVisibility(View.VISIBLE);
				img.setTag(m.getId());
				img.setImageResource(R.drawable.marker_birdhouse);
				
				Drawable icon = getResources().getDrawable(m.getDrawableIdActive());
				
				if(m.getStatus() != TaskMarker.STATUS_DONE)
					icon.setAlpha(40);
				else if(m.getStatus() == TaskMarker.STATUS_DONE)
					icon.setAlpha(200);
				
				img.setImageDrawable(icon);
				markerIcons.put(m.getId(), icon);
				
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					     LinearLayout.LayoutParams.MATCH_PARENT, 
					     LinearLayout.LayoutParams.WRAP_CONTENT,
					     0.11f);

				layoutParams.setMargins(10, 0, 0, 0);
				
				img.setLayoutParams(layoutParams);
				
				l.addView(img);
			}
			
			count = (Button)findViewById(R.id.count);
		}
	}
	
	@Override
	public void onBackPressed() {
		GameTimer.stopTimer();
		
		Intent i = new Intent(this, StartActivity.class);
		startActivity(i);
	}
}
