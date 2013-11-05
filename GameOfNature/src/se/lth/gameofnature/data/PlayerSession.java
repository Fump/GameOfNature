package se.lth.gameofnature.data;

import java.util.HashMap;

import se.lth.gameofnature.gamemap.markers.TaskMarker;

/* Class containing data describing the current game session */
public class PlayerSession {
	public static String TASK_MARKER_ID = "TASK_MARKER_ID";
	
	
	public static HashMap<String, TaskMarker> taskMarkers =
			new HashMap<String, TaskMarker>();
	
	public static void addTaskMarker(String id, TaskMarker marker) {
		taskMarkers.put(id, marker);
	}
	
	public static TaskMarker getTaskMarker(String id) {
		return taskMarkers.get(id);
	}
	
	public static void clearTaskMarkers() {
		taskMarkers = new HashMap<String, TaskMarker>();
	}
	
	public static boolean containsTaskMarker(String id) {
		return taskMarkers.containsKey(id);
	}
}
