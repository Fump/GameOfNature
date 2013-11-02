package se.lth.gameofnature;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class LocationHandler implements 
	ConnectionCallbacks,
	OnConnectionFailedListener,
	LocationListener{
	
	private Context mContext;
	
	private MyLocationMarker myLocation;
	
	private LocationClient mLocationClient;
	
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	public LocationHandler(Context mContext) {
		this.mContext = mContext;
	}
	
	public void setLocationMarker(MyLocationMarker m) {
		myLocation = m;
	}
	
	public void startTracking() {
        if(mLocationClient == null) {
        	mLocationClient = new LocationClient(
        			mContext,
        			this, // ConnectionCallBacks
        			this); //OnConnectionFailedListner
        }
        
        mLocationClient.connect();
	}
	
	public void stopTracking() {
		if (mLocationClient != null) {
            mLocationClient.disconnect();
        }
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// Do nothing
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mLocationClient.requestLocationUpdates(
				REQUEST, 
				this);
	}

	@Override
	public void onDisconnected() {
		//Do nothing
	}

	@Override
	public void onLocationChanged(Location loc) {
		myLocation.setPosition(new LatLng(loc.getLatitude(), loc.getLongitude()));	
	}
}
