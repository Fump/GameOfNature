package se.lth.gameofnature.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MySQLiteHelper extends SQLiteOpenHelper {
	public static final String TEAM = "Team";
	public static final String TASK_MARKER = "TaskMarker";
	public static final String NAME = "name";
	public static final String ICON_ID = "iconId";
	public static final String COLOR = "color";

	public static final String GAME_TIME = "game_time";
	public static final String DISTANCE_TRAVELED = "distanceTraveled";
	public static final String HAS_ACTIVE_SESSION = "hasActiveSession";
	public static final String CLUES = "clues";
	public static final String TASK_MARKER_ID = "id";
	public static final String CURRENT_STATUS = "currentStatus";
	public static final String LAST_QUESTION = "lastQuestionId";

	private static final String DATABASE_NAME = "Database.db";
	private static final int DATABASE_VERSION = 1;

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL("Create table " + TEAM + "( "+ NAME + " TEXT not null,"
				+ ICON_ID + " INTEGER not null, "+ COLOR + " TEXT not null, "+ GAME_TIME + "  INTEGER not null,"
				+  DISTANCE_TRAVELED + "  INTEGER not null, "+ HAS_ACTIVE_SESSION + "  INTEGER not null,"
				+  CLUES + "  INTEGER not null);");
		database.execSQL("Create table " + TASK_MARKER + "( id TEXT primary key, "
				+ "currentStatus INTEGER not null, lastQuestionId TEXT not null );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TEAM);
		db.execSQL("DROP TABLE IF EXISTS " + TASK_MARKER);
		onCreate(db);
	}

}
