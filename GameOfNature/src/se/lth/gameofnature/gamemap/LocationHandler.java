package se.lth.gameofnature.gamemap;

import java.util.ArrayList;

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
	
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	public LocationHandler(Context mContext, GameMap map, MyLocationMarker myLocation) {
		this.mContext = mContext;
		this.map = map;
		this.myLocation = myLocation;
		
		geofencesToAdd = new ArrayList<Geofence>();
		geofencesToRemove = new ArrayList<String>();
		
		hasPendingRemove = false;
		hasPendingAdd = false;
	}
	
	/*
	 * Starts tracking the users current position.
	 */
	public void startTracking() {
		LocationManager manager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
		
		if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
			buildAlertMessageNoGps();
		
		manager = null;
		
        if(mLocationClient == null) {
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
            mLocationClient.disconnect();
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
		
		geofencesToAdd.add(g);
		
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
	public void untrackTaskMarker(TaskMarker m) {
		geofencesToRemove.add(m.getId());
		
		if(mLocationClient.isConnected()) {
			mLocationClient.removeGeofences(geofencesToRemove, this);
			geofencesToRemove.clear();
		} else {
			hasPendingRemove = true;
		}
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
	}

	@Override
	public void onDisconnected() {
		//Do nothing
	}

	@Override
	public void onLocationChanged(Location loc) {
		LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());

		myLocation.setPosition(pos);	
		map.setPosition(pos);
	}

	@Override
	public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) {
		 // If adding the geofences was successful
        if (LocationStatusCodes.SUCCESS == statusCode) {
            Toast.makeText(mContext, "Geofences added", Toast.LENGTH_SHORT).show();
        } else {
        	Log.e("Failed to add geofences", "Failed to add geofences");
        }
	}

	@Override
	public void onRemoveGeofencesByPendingIntentResult(int statusCode,
			PendingIntent intent) {
		 // If adding the geofences was successful
        if (LocationStatusCodes.SUCCESS == statusCode) {
            Toast.makeText(mContext, "Geofences added", Toast.LENGTH_SHORT).show();
        } else {
        	Log.e("Failed to add geofences", "Failed to add geofences");
        }
	}

	@Override
	public void onRemoveGeofencesByRequestIdsResult(int statusCode, String[] requestIds) {
		 // If adding the geofences was successful
        if (LocationStatusCodes.SUCCESS == statusCode) {
            Toast.makeText(mContext, "Geofences added", Toast.LENGTH_SHORT).show();
        } else {
        	Log.e("Failed to add geofences", "Failed to add geofences");
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
