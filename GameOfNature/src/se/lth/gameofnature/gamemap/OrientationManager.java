package se.lth.gameofnature.gamemap;

import se.lth.gameofnature.gamemap.markers.MyLocationMarker;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Surface;
import android.view.WindowManager;

public class OrientationManager implements SensorEventListener {
	
    public final static int SENSOR_UNAVAILABLE = -1;

    // references to other objects
    SensorManager m_sm;
    SensorEventListener m_parent;   // non-null if this class should call its parent after onSensorChanged(...) and onAccuracyChanged(...) notifications
    Activity m_activity;            // current activity for call to getWindowManager().getDefaultDisplay().getRotation()

    // raw inputs from Android sensors
    float m_Norm_Gravity;           // length of raw gravity vector received in onSensorChanged(...).  NB: should be about 10
    float[] m_NormGravityVector;    // Normalised gravity vector, (i.e. length of this vector is 1), which points straight up into space
    float m_Norm_MagField;          // length of raw magnetic field vector received in onSensorChanged(...). 
    float[] m_NormMagFieldValues;   // Normalised magnetic field vector, (i.e. length of this vector is 1)

    // accuracy specifications. SENSOR_UNAVAILABLE if unknown, otherwise SensorManager.SENSOR_STATUS_UNRELIABLE, SENSOR_STATUS_ACCURACY_LOW, SENSOR_STATUS_ACCURACY_MEDIUM or SENSOR_STATUS_ACCURACY_HIGH
    int m_GravityAccuracy;          // accuracy of gravity sensor
    int m_MagneticFieldAccuracy;    // accuracy of magnetic field sensor

    // values calculated once gravity and magnetic field vectors are available
    float[] m_NormEastVector;       // normalised cross product of raw gravity vector with magnetic field values, points east
    float[] m_NormNorthVector;      // Normalised vector pointing to magnetic north
    boolean m_OrientationOK;        // set true if m_azimuth_radians and m_pitch_radians have successfully been calculated following a call to onSensorChanged(...)
    float m_azimuth_radians;        // angle of the device from magnetic north
    float m_pitch_radians;          // tilt angle of the device from the horizontal.  m_pitch_radians = 0 if the device if flat, m_pitch_radians = Math.PI/2 means the device is upright.
    float m_pitch_axis_radians;     // angle which defines the axis for the rotation m_pitch_radians

    private MyLocationMarker m_Marker;
    
    public OrientationManager(SensorManager sm, MyLocationMarker m_Marker, SensorEventListener parent) {
        m_sm = sm;
        m_parent = parent;
        m_activity = null;
        m_NormGravityVector = m_NormMagFieldValues = null;
        m_NormEastVector = new float[3];
        m_NormNorthVector = new float[3];
        m_OrientationOK = false;
    
        this.m_Marker = m_Marker;
    }

    public int Register(Activity activity, int sensorSpeed) {
        m_activity = activity;  // current activity required for call to getWindowManager().getDefaultDisplay().getRotation()
        m_NormGravityVector = new float[3];
        m_NormMagFieldValues = new float[3];
        m_OrientationOK = false;
        int count = 0;
        Sensor SensorGravity = m_sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if (SensorGravity != null) {
            m_sm.registerListener(this, SensorGravity, sensorSpeed);
            m_GravityAccuracy = SensorManager.SENSOR_STATUS_ACCURACY_HIGH;
            count++;
        } else {
            m_GravityAccuracy = SENSOR_UNAVAILABLE;
        }
        Sensor SensorMagField = m_sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (SensorMagField != null) {
            m_sm.registerListener(this, SensorMagField, sensorSpeed);
            m_MagneticFieldAccuracy = SensorManager.SENSOR_STATUS_ACCURACY_HIGH;     
            count++;
        } else {
            m_MagneticFieldAccuracy = SENSOR_UNAVAILABLE;
        }
        return count;
    }

    public void Unregister() {
        m_activity = null;
        m_NormGravityVector = m_NormMagFieldValues = null;
        m_OrientationOK = false;
        m_sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent evnt) {
        int SensorType = evnt.sensor.getType();
        switch(SensorType) {
            case Sensor.TYPE_GRAVITY:
                if (m_NormGravityVector == null) m_NormGravityVector = new float[3];
                System.arraycopy(evnt.values, 0, m_NormGravityVector, 0, m_NormGravityVector.length);                   
                m_Norm_Gravity = (float)Math.sqrt(m_NormGravityVector[0]*m_NormGravityVector[0] + m_NormGravityVector[1]*m_NormGravityVector[1] + m_NormGravityVector[2]*m_NormGravityVector[2]);
                for(int i=0; i < m_NormGravityVector.length; i++) m_NormGravityVector[i] /= m_Norm_Gravity;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                if (m_NormMagFieldValues == null) m_NormMagFieldValues = new float[3];
                System.arraycopy(evnt.values, 0, m_NormMagFieldValues, 0, m_NormMagFieldValues.length);
                m_Norm_MagField = (float)Math.sqrt(m_NormMagFieldValues[0]*m_NormMagFieldValues[0] + m_NormMagFieldValues[1]*m_NormMagFieldValues[1] + m_NormMagFieldValues[2]*m_NormMagFieldValues[2]);
                for(int i=0; i < m_NormMagFieldValues.length; i++) m_NormMagFieldValues[i] /= m_Norm_MagField;  
                break;
        }
        if (m_NormGravityVector != null && m_NormMagFieldValues != null) {
            // first calculate the horizontal vector that points due east
            float East_x = m_NormMagFieldValues[1]*m_NormGravityVector[2] - m_NormMagFieldValues[2]*m_NormGravityVector[1];
            float East_y = m_NormMagFieldValues[2]*m_NormGravityVector[0] - m_NormMagFieldValues[0]*m_NormGravityVector[2];
            float East_z = m_NormMagFieldValues[0]*m_NormGravityVector[1] - m_NormMagFieldValues[1]*m_NormGravityVector[0];
            float norm_East = (float)Math.sqrt(East_x * East_x + East_y * East_y + East_z * East_z);
            if (m_Norm_Gravity * m_Norm_MagField * norm_East < 0.1f) {  // Typical values are  > 100.
                m_OrientationOK = false; // device is close to free fall (or in space?), or close to magnetic north pole.
            } else {
                m_NormEastVector[0] = East_x / norm_East; m_NormEastVector[1] = East_y / norm_East; m_NormEastVector[2] = East_z / norm_East;

                // next calculate the horizontal vector that points due north                   
                float M_dot_G = (m_NormGravityVector[0] *m_NormMagFieldValues[0] + m_NormGravityVector[1]*m_NormMagFieldValues[1] + m_NormGravityVector[2]*m_NormMagFieldValues[2]);
                float North_x = m_NormMagFieldValues[0] - m_NormGravityVector[0] * M_dot_G;
                float North_y = m_NormMagFieldValues[1] - m_NormGravityVector[1] * M_dot_G;
                float North_z = m_NormMagFieldValues[2] - m_NormGravityVector[2] * M_dot_G;
                float norm_North = (float)Math.sqrt(North_x * North_x + North_y * North_y + North_z * North_z);
                m_NormNorthVector[0] = North_x / norm_North; m_NormNorthVector[1] = North_y / norm_North; m_NormNorthVector[2] = North_z / norm_North;

                // take account of screen rotation away from its natural rotation
                
                int rotation = Surface.ROTATION_0;

                float screen_adjustment = 90;
                switch(rotation) {
                    case Surface.ROTATION_0:   screen_adjustment =          0;         break;
                    case Surface.ROTATION_90:  screen_adjustment =   (float)Math.PI/2; break;
                    case Surface.ROTATION_180: screen_adjustment =   (float)Math.PI;   break;
                    case Surface.ROTATION_270: screen_adjustment = 3*(float)Math.PI/2; break;
                }
                // NB: the rotation matrix has now effectively been calculated. It consists of the three vectors m_NormEastVector[], m_NormNorthVector[] and m_NormGravityVector[]

                // calculate all the required angles from the rotation matrix
                // NB: see http://math.stackexchange.com/questions/381649/whats-the-best-3d-angular-co-ordinate-system-for-working-with-smartfone-apps
                float sin = m_NormEastVector[1] -  m_NormNorthVector[0], cos = m_NormEastVector[0] +  m_NormNorthVector[1];
                m_azimuth_radians = (float) (sin != 0 && cos != 0 ? Math.atan2(sin, cos) : 0);
                m_pitch_radians = (float) Math.acos(m_NormGravityVector[2]);
                sin = -m_NormEastVector[1] -  m_NormNorthVector[0]; cos = m_NormEastVector[0] -  m_NormNorthVector[1];
                float aximuth_plus_two_pitch_axis_radians = (float)(sin != 0 && cos != 0 ? Math.atan2(sin, cos) : 0);
                m_pitch_axis_radians = (float)(aximuth_plus_two_pitch_axis_radians - m_azimuth_radians) / 2;
                m_azimuth_radians += screen_adjustment;
                m_pitch_axis_radians += screen_adjustment;
                m_OrientationOK = true;            
                
                m_Marker.setRotation((float)Math.toDegrees(m_azimuth_radians));
            }
        }
        if (m_parent != null) m_parent.onSensorChanged(evnt);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        int SensorType = sensor.getType();
        switch(SensorType) {
            case Sensor.TYPE_GRAVITY: m_GravityAccuracy = accuracy; break;
            case Sensor.TYPE_MAGNETIC_FIELD: m_MagneticFieldAccuracy = accuracy; break;
        }
        if (m_parent != null) m_parent.onAccuracyChanged(sensor, accuracy);
    }
}
/*
	private float[] mGravity = null;
	private float[] mGeomagnetic = null;
	
	private SensorManager manager;
	
	private Sensor accelerometer;
	private Sensor magnetometer;
	
	private Context mContext;
	
	private MyLocationMarker mMarker;
	
	float phoneAngle = 0;
	float iconRotation = 0;
	
	public OrientationManager(Context mContext, MyLocationMarker mMarker) {
		this.mContext = mContext;
		this.mMarker = mMarker;
		
		manager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		
		accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	}
	
	public void startRotation() {
		manager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		manager.registerListener(this, magnetometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void stopRotation() {
		manager.unregisterListener(this);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			mGravity = event.values.clone();
		}
		
		if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			mGeomagnetic = event.values.clone();
		}
		
		if(mGravity != null && mGeomagnetic != null) {
			float R[] = new float[9];
			float I[] = new float[9];
			
			boolean sucess = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
			
			if(sucess) {
				float[] orientation = new float[3];
				SensorManager.getOrientation(R, orientation);
				
				phoneAngle = (float)Math.toDegrees(orientation[0]);
				
				if(Math.abs(iconRotation - phoneAngle) >= 1) {
					iconRotation = phoneAngle;
					mMarker.setRotation(iconRotation);
				}
			}
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

} */
