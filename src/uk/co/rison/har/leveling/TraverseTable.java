package uk.co.rison.har.leveling;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TraverseTable {
	private static final String TRANSVERSE_TABLE_NAME = "transverse";
    private static final String TRANSVERSE_TABLE_CREATE =
                "CREATE TABLE " + TRANSVERSE_TABLE_NAME + " (" 
                + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name" + " TEXT NOT NULL, "
                + "type" + " TEXT NOT NULL, "
                + "observer" + " TEXT, "
                + "staffman" + " TEXT, "
                + "survey_date" + " INTEGER NOT NULL, "
                + "modified_date" + " INTEGER NOT NULL );";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TRANSVERSE_TABLE_CREATE);
    }
    
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(TraverseTable.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS transverse");
        onCreate(database);
    }

}