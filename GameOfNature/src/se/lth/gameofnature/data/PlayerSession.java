package se.lth.gameofnature.data;

import java.util.Iterator;
import java.util.TreeMap;

import android.content.Context;
import android.util.Log;

import se.lth.gameofnature.gamemap.markers.FinalQuestionMarker;
import se.lth.gameofnature.gamemap.markers.TaskMarker;

/* Class containing data describing the current game session */
public class PlayerSession {
	
	public static PlayerSession session;
	
	public static PlayerSession getCurrentSessionInstance(Context mContext) {
		if(session == null) {
			session = new PlayerSession(mContext);
		}
		
		return session;
	}
	
	private TreeMap<String, TaskMarker> taskMarkers;
	private FinalQuestionMarker fqm;
	public PlayerSession(Context mContext) {
		taskMarkers = XMLReader.readTaskMarkers(mContext);
		/*TODO: Gör klart denna. Gör en getFinalQuestion för användning i TaskMarkerDialog
		 * fqm = XMLReader.getFinalQuestion(e, mContext)
		 */
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
