package se.lth.gameofnature.data;

import java.util.Iterator;
import java.util.TreeMap;

import android.content.Context;
import android.util.Log;

import se.lth.gameofnature.gamemap.markers.TaskMarker;

/* Class containing data describing the current game session */
public class GameMapData {
	
	public static GameMapData session;
	
	public static GameMapData getCurrentSessionInstance(Context mContext) {
		if(session == null) {
			session = new GameMapData(mContext);
		}
		
		return session;
	}
	
	private TreeMap<String, TaskMarker> taskMarkers;
	
	public GameMapData(Context mContext) {
		taskMarkers = XMLReader.readTaskMarkers(mContext);
	}
	
	public TaskMarker getTaskMarker(String id) {
		return taskMarkers.get(id);
	}
	
	public boolean containsTaskMarker(String id) {
		return taskMarkers.containsKey(id);
	}
	
	public Iterator<TaskMarker> getMarkerIterator() {
		return taskMarkers.values().iterator();
	}
}
