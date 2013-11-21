package se.lth.gameofnature.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
/*
	public TaskMarker createTaskMarker(String task_ID) {
		return task;
	}

	public Team createTeam(String name, String iconId, String color,
			int game_time, int distanceTraveled, int hasActiveSession,
			int clues) {
		 ContentValues values = new ContentValues();
		    values.put(MySQLiteHelper.NAME, name);
		    values.put(MySQLiteHelper.ICON_ID, iconId);
		    values.put(MySQLiteHelper.COLOR,color);
		    values.put(MySQLiteHelper.GAME_TIME,game_time);
		    values.put(MySQLiteHelper.DISTANCE_TRAVELED,distanceTraveled);
		    values.put(MySQLiteHelper.HAS_ACTIVE_SESSION, hasActiveSession);
		    values.put(MySQLiteHelper.CLUES, clues);
		    long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
		        values);
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
		        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Comment newComment = cursorToComment(cursor);
		    cursor.close();
		    return newComment;
		return team;
	}
*/
	public void deleteTaskMarker(TaskMarker task) {
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
