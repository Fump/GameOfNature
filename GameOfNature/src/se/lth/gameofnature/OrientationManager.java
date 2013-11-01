package se.lth.gameofnature;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class OrientationManager implements SensorEventListener {
	private Context context;
	private MyLocationMarker myMarker;
	
	private SensorManager sm;
	
	private float[] inR = new float[16];
	private float[] I = new float[16];
	private float[] gravity = new float[3];
	private float[] geomag = new float[3];
	private float[] orientVals = new float[3];
	
	private double azimuth;
	
	private boolean hasNewOrientation;
	
	public OrientationManager(MyLocationMarker myMarker, Context context) {
		this.myMarker = myMarker;
		this.context = context;
		
		sm = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		
		regSensorListners();
		
		hasNewOrientation = false;
	}
	
	public void regSensorListners() {
		sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		
		sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_NORMAL);
		
		azimuth = 0;
	}
	
	public void unRegisterSensorListner() {
		sm.unregisterListener(this);
	}
	
	private void updateGeoMagneticArray(SensorEvent e) {
		geomag = e.values.clone();
	}
	
	private void updateGravitynArray(SensorEvent e) {
		gravity = e.values.clone();
	}
	
	private void updateOrientation() {
		double lastAzimuth = azimuth;
		
		boolean success = SensorManager.getRotationMatrix(inR, I,
                gravity, geomag);
		
		if (success) {
			SensorManager.getOrientation(inR, orientVals);
			
			azimuth = Math.toDegrees(orientVals[0]);	
		}
		
		if(azimuth != lastAzimuth) {
			hasNewOrientation = true;
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		/*if(event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
			return;*/
		
		switch(event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				updateGravitynArray(event);
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				updateGeoMagneticArray(event);
				break;
		}
		
		if(gravity != null && geomag != null) 
			updateOrientation();
		Toast.makeText(context, "Sensor Changed! " + azimuth, Toast.LENGTH_LONG).show();
		if(hasNewOrientation) {
			myMarker.setRotation((float)azimuth);
		}
	}
	
}
