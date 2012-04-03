package uk.co.rison.har.leveling.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ReadingTable {
	private static final String READING_TABLE_NAME = "reading";
    private static final String READING_TABLE_CREATE =
                "CREATE TABLE " + READING_TABLE_NAME + " (" 
                + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "traverse" + " INTEGER NOT NULL, "
                + "observation" + " INTEGER NOT NULL, "
                + "type" + " INTEGER NOT NULL, "
                + "reading" + " DOUBLE NOT NULL, "
                + "label" + " TEXT NOT NULL, "
                + "modified_date" + " TEXT NOT NULL );";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(READING_TABLE_CREATE);
    }
    
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(ReadingTable.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS reading");
        onCreate(database);
    }
}