package se.lth.gameofnature.gamemap;

import java.util.List;

import se.lth.gameofnature.TaskMarkerDialog;
import se.lth.gameofnature.data.GameMapData;
import se.lth.gameofnature.gamemap.markers.TaskMarker;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class ReceiveTransitionsIntentService extends IntentService {
	public ReceiveTransitionsIntentService() {
		super("ReceiveTransitionsIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		 if (LocationClient.hasError(intent)) {
	            // Get the error code with a static method
	            int errorCode = LocationClient.getErrorCode(intent);
	            // Log the error
	            Log.e("ReceiveTransitionsIntentService",
	                    "Location Services error: " +
	                    Integer.toString(errorCode));
		  } else {
	            // Get the type of transition (entry or exit)
	            int transitionType =
	                    LocationClient.getGeofenceTransition(intent);
	            // Test that a valid transition was reported
	            if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
	                List <Geofence> triggerList =
	                        LocationClient.getTriggeringGeofences(intent);
	                
	                //Opens a dialog window for the TaskMarker, if it isn't already open.
	                if(!TaskMarkerDialog.active) {
		                Intent i = new Intent(this, TaskMarkerDialog.class);
		                
		                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		                
		                i.putExtra(TaskMarker.TASK_MARKER_ID, triggerList.get(0).getRequestId());
		                
		                startActivity(i);
	                }
	                
	            } else {
	            Log.e("ReceiveTransitionsIntentService",
	                    "Geofence transition error: " +
	                    Integer.toString(transitionType));
	            }
	    }
		
	}

}
