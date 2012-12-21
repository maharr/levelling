package uk.co.rison.har.levelling.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TraverseTable {
	//Table for storing the information about each individual traverse
	private static final String TRAVERSE_TABLE_NAME = "traverse";
    private static final String TRAVERSE_TABLE_CREATE =
                "CREATE TABLE " + TRAVERSE_TABLE_NAME + " (" 
                + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name" + " TEXT NOT NULL, "
                + "type" + " TEXT NOT NULL, "
                + "observer" + " TEXT, "
                + "staffman" + " TEXT, "
                + "survey_date" + " TEXT NOT NULL, "
                + "modified_date" + " TEXT NOT NULL );";
    //Table for storing the readings from each observation
    private static final String READING_TABLE_NAME = "reading";
    private static final String READING_TABLE_CREATE =
                "CREATE TABLE " + READING_TABLE_NAME + " (" 
                + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "traverse" + " LONG NOT NULL, "
                + "observation" + " INTEGER NOT NULL, "
                + "type" + " INTEGER NOT NULL, "
                + "reading" + " DOUBLE NOT NULL, "
                + "label" + " TEXT NOT NULL, "
                + "modified_date" + " TEXT NOT NULL );";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TRAVERSE_TABLE_CREATE);
        database.execSQL(READING_TABLE_CREATE);
    }
    
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(TraverseTable.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS traverse");
        onCreate(database);
    }
}