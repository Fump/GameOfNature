package se.lth.gameofnature.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class Database {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;

	public Database(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public TaskMarkerStatus createTaskMarker(String task_ID,
			int current_status, int lastQuestionId) {
		ContentValues values = new ContentValues();
		dbHelper.onUpgrade(database, 0, 1);
		values.put(MySQLiteHelper.TASK_MARKER_ID, task_ID);
		values.put(MySQLiteHelper.CURRENT_STATUS, current_status);
		values.put(MySQLiteHelper.LAST_QUESTION, lastQuestionId);
		database.insert(MySQLiteHelper.TEAM, null, values);
		TaskMarkerStatus task = new TaskMarkerStatus(task_ID, current_status,
				lastQuestionId);
		return task;
	}

	public Team createTeam(String name, String iconId, String color,
			int game_time, int distanceTraveled, int hasActiveSession, int clues) {
		ContentValues values = new ContentValues();
		dbHelper.onUpgrade(database, 0, 1);
		values.put(MySQLiteHelper.NAME, name);
		values.put(MySQLiteHelper.ICON_ID, iconId);
		values.put(MySQLiteHelper.COLOR, color);
		values.put(MySQLiteHelper.GAME_TIME, game_time);
		values.put(MySQLiteHelper.DISTANCE_TRAVELED, distanceTraveled);
		values.put(MySQLiteHelper.HAS_ACTIVE_SESSION, hasActiveSession);
		values.put(MySQLiteHelper.CLUES, clues);
		database.insert(MySQLiteHelper.TEAM, null, values);
		Team newTeam = new Team(name, iconId, color, game_time,
				distanceTraveled, hasActiveSession, clues);
		return newTeam;
	}

	// // Skapade för att testa att det sparas ////
	public String table() {
		String selectQuery = "SELECT  * FROM " + MySQLiteHelper.TEAM +  ";";
		Cursor cursor = database.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		String iconId = cursor.getString(cursor.getColumnIndex("iconId"));
		String color =  cursor.getString(cursor.getColumnIndex("color"));
		String name =  cursor.getString(cursor.getColumnIndex("name"));;
		return (name + " " + color + " " + iconId); 
	}

	public void modifyTaskMarker(String task_ID, int current_status,
			int lastQuestionId) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.CURRENT_STATUS, current_status);
		values.put(MySQLiteHelper.LAST_QUESTION, lastQuestionId);
		database.update(MySQLiteHelper.TASK_MARKER, values, task_ID, null);
	}

	public void modifyTeam(String name, int game_time, int distanceTraveled,
			int hasActiveSession, int clues) {

		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.GAME_TIME, game_time);
		values.put(MySQLiteHelper.DISTANCE_TRAVELED, distanceTraveled);
		values.put(MySQLiteHelper.HAS_ACTIVE_SESSION, hasActiveSession);
		values.put(MySQLiteHelper.CLUES, clues);
		database.update(MySQLiteHelper.TEAM, values, name, null);
	}

	public void deleteTaskMarker(TaskMarkerStatus task) {
		String id = task.getId();
		database.delete(MySQLiteHelper.TASK_MARKER,
				MySQLiteHelper.TASK_MARKER_ID + " = " + id, null);
	}

	public void deleteTeam(Team team) {
		String name = team.getName();
		database.delete(MySQLiteHelper.TEAM,
				MySQLiteHelper.NAME + " = " + name, null);
	}

	/*
	 * public Team getTeam() { return team; }
	 */

}
