package se.lth.gameofnature.gamemap;

import java.util.ArrayList;

import se.lth.gameofnature.data.Database;
import se.lth.gameofnature.data.Team;
import se.lth.gameofnature.gamemap.markers.MyLocationMarker;
import se.lth.gameofnature.gamemap.markers.TaskMarker;


import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationClient.OnRemoveGeofencesResultListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.maps.model.LatLng;

public class LocationHandler implements 
	ConnectionCallbacks,
	OnConnectionFailedListener,
	LocationListener, 
	OnAddGeofencesResultListener,
	OnRemoveGeofencesResultListener {
	
	private Context mContext;
	
	private MyLocationMarker myLocation;
	private GameMap map;
	
	private LocationClient mLocationClient;
	
	private boolean hasPendingRemove;
	private boolean hasPendingAdd;
	
	private ArrayList<Geofence> geofencesToAdd;
	private ArrayList<String> geofencesToRemove;
	
	private static final int TRACK_RADIUS = 15;
	
	private static final int UPDATE_INTERVAL = 5000;
	private static final int FASTEST_INTERVAL = 16;
	
	private Location lastLocation;
	private int distanceTraveled;
	
	private boolean track;
	
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(UPDATE_INTERVAL)         // 5 seconds
            .setFastestInterval(FASTEST_INTERVAL)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	public LocationHandler(Context mContext, GameMap map, MyLocationMarker myLocation) {
		this.mContext = mContext;
		this.map = map;
		this.myLocation = myLocation;
		
		track = false;
		
		geofencesToAdd = new ArrayList<Geofence>();
		geofencesToRemove = new ArrayList<String>();
		
		hasPendingRemove = false;
		hasPendingAdd = false;
		
		Database db = new Database(mContext);
		
		db.open();
		
		if(db.hasActiveSession()) {
			Team t = db.getTeamStatus();
			distanceTraveled = t.getDistanceTraveled();
		} else {
			distanceTraveled = 0;
		}
		
		db.close();
		db = null;
	}
	
	/*
	 * Starts tracking the users current position.
	 */
	public void startTracking() {
        if(mLocationClient == null) {
    		LocationManager manager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
    		
    		if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    			buildAlertMessageNoGps();
    		
        	mLocationClient = new LocationClient(
        			mContext,
        			this, // ConnectionCallBacks
        			this); //OnConnectionFailedListner
        }
        
        mLocationClient.connect();
	}
	
	/*
	 * Stops tracking the users current position.
	 */
	public void stopTracking() {
		if (mLocationClient != null) {
			
			untrackTaskMarkers();
			
			if(mLocationClient.isConnected())
				mLocationClient.removeLocationUpdates(this);
            
			mLocationClient.disconnect();
			mLocationClient = null;
		}
	}
	
	/*
	 * Starts tracking the specified TaskMarker.
	 */
	public void trackTaskMarker(TaskMarker m) {
		Geofence g = new Geofence.Builder()
			.setCircularRegion(m.getPosition().latitude, m.getPosition().longitude, TRACK_RADIUS)
			.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
			.setExpirationDuration(Geofence.NEVER_EXPIRE)
			.setRequestId(m.getId())
			.build();
		
		if(geofencesToAdd.contains(m))
			geofencesToAdd.remove(m);
		
		geofencesToAdd.add(g);
		geofencesToRemove.add(m.getId());
		
		if(mLocationClient.isConnected()) {
			mLocationClient.addGeofences(geofencesToAdd, getTransitionPendingIntent(), this);
			geofencesToAdd.clear();
		} else {
			hasPendingAdd = true;
		}
	}
	
	/*
	 * Stops tracking the specified TaskMarker
	 */
	private void untrackTaskMarkers() {
		if(geofencesToRemove != null && !geofencesToRemove.isEmpty()) {
			if(mLocationClient.isConnected()) {
				mLocationClient.removeGeofences(geofencesToRemove, this);
				geofencesToRemove.clear();
			} else {
				hasPendingRemove = true;
			}
		}
	}
	
	public LatLng getLastLocation() {
		if(mLocationClient.isConnected()) {
			Location l = mLocationClient.getLastLocation();
			
			return l != null ? new LatLng(l.getLatitude(), l.getLongitude()) : null;
		}
		
		return null;
	}
	
	/*
	 * Generates a PendingIntent to be sent when a TaskMarker has been detected
	 * at the users current location.
	 */
	private PendingIntent getTransitionPendingIntent() {
		
		Intent intent = new Intent(mContext, ReceiveTransitionsIntentService.class);
			
		return PendingIntent.getService(
						mContext, 
						0, 
						intent, 
						PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// Do nothing
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mLocationClient.requestLocationUpdates(
				REQUEST, 
				this); //LocationListner
		
		if(hasPendingAdd) {
			mLocationClient.addGeofences(geofencesToAdd, getTransitionPendingIntent(), this);
			geofencesToAdd.clear();
			hasPendingAdd = false;
		}
			
		if(hasPendingRemove) {
			mLocationClient.removeGeofences(geofencesToRemove, this);
			geofencesToRemove.clear();
			hasPendingRemove = false;
		}
		
		Location prevLocation = mLocationClient.getLastLocation();
		
		if(prevLocation!= null) {
			LatLng pos = new LatLng(prevLocation.getLatitude(),
					prevLocation.getLongitude());
			
			map.setPosition(pos);
			myLocation.setPosition(pos);
		}
	}

	@Override
	public void onDisconnected() {
	}

	@Override
	public void onLocationChanged(Location loc) {
		LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());
		
		myLocation.setPosition(pos);	
		
		if(track)
			map.setPosition(pos);
		
		if(lastLocation != null) {
			float[] distance = new float[1];
			
			Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(), 
					loc.getLatitude(), loc.getLongitude(), 
					distance);
			
			if(distanceTraveled <= 10){
				distanceTraveled += distance[0];
				
				Database db = new Database(mContext);
				db.open();
				
				db.setDistanceTravled(distanceTraveled);
				
				db.close();
				db = null;
			}
		}
		
		lastLocation = loc;
	}
	
	public void setTrackLocation(boolean track) {
		this.track = track;
	}

	@Override
	public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) {
		 // If adding the geofences was successful
        if (LocationStatusCodes.SUCCESS == statusCode) {
        	Log.e("Added geofences", "Added geofences");
        } else {
        	for(String id : geofenceRequestIds)
        		Log.e("geofences", "Failed to add geofence with id: " + id);
        }
	}

	@Override
	public void onRemoveGeofencesByPendingIntentResult(int statusCode,
			PendingIntent intent) {
		 // If adding the geofences was successful
        if (LocationStatusCodes.SUCCESS == statusCode) {
        	Log.e("Added geofences", "Added geofences");
        } else {
        	Log.e("Failed to add geofences", "Failed to add geofences");
        }
	}

	@Override
	public void onRemoveGeofencesByRequestIdsResult(int statusCode, String[] requestIds) {
		 // If adding the geofences was successful
        if (LocationStatusCodes.SUCCESS == statusCode) {
            Log.d("REMOVED GEOFENCE", "REMOVED");
        } else {
        	Log.e("Failed to add geofences", "Failed to remove geofences");
        }
	}
	
	  private void buildAlertMessageNoGps() {
		    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		    
		    builder.setMessage("Din GPS är avstängd, du måste aktivera denna för att spela, vill du göra detta?")
		           .setCancelable(false)
		           .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
		               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
		                   mContext.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		               }
		           })
		           .setNegativeButton("Nej", new DialogInterface.OnClickListener() {
		               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
		                    dialog.cancel();
		               }
		           });
		    
		    final AlertDialog alert = builder.create();
		    
		    alert.show();
		}
}
