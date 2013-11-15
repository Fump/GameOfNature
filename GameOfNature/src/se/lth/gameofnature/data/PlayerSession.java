package se.lth.gameofnature.data;

import java.util.Iterator;
import java.util.TreeMap;

import se.lth.gameofnature.gamemap.markers.TaskMarker;

/* Class containing data describing the current game session */
public class PlayerSession {
	
	public static PlayerSession session;
	
	public static PlayerSession getCurrentSessionInstance() {
		return session;
	}
	
	public static void createNewSessionInstace() {
		session = new PlayerSession();
	}
	
	public TreeMap<String, TaskMarker> taskMarkers;
	
	public PlayerSession() {
		taskMarkers = new TreeMap<String,TaskMarker>();
	}
	
	public void addTaskMarker(String id, TaskMarker marker) {
		taskMarkers.put(id, marker);
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
