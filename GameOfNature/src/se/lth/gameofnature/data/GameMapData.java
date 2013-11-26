package se.lth.gameofnature.data;

import java.util.ArrayList;
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
		
		Database db = new Database(mContext);
		db.open();
		
		ArrayList<TaskMarkerStatus> statuses = db.getTaskMarkerStatuses();

		if(statuses.isEmpty()) {
			for(TaskMarker m : taskMarkers.values()) {
				db.createTaskMarker(m.getId(), m.getStatus(), m.getLastQuestionId());
			}
		} else {
			for(TaskMarkerStatus s : statuses) {
				TaskMarker m = taskMarkers.get(s.getId());
				
				m.setStatus(s.getCurrentStatus());
				m.setLastQuestionId(s.getLastQuestionId());
			}
		}
		
		db.close();
		db = null;
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
	
	public int getNumberOfMarkers() {
		return taskMarkers.size();
	}
	
	public int getNumberDoneMarkers() {
		int count = 0;
		
		for(TaskMarker m : taskMarkers.values()) {
			if(m.getStatus() == TaskMarker.STATUS_DONE)
				count++;
		}
		
		return count;
	}
}
