package uk.co.rison.har.leveling.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ReadingAdapter {
	// Database fields
		public static final String KEY_ROWID = "_id";
		public static final String KEY_TRAVERSE = "traverse";
		public static final String KEY_OBSERVATION = "observation";
		public static final String KEY_TYPE = "type";
		public static final String KEY_READING = "reading";
		public static final String KEY_LABEL = "label";
		public static final String KEY_MODIFIEDDATE = "modified_date";
		private static final String DB_TABLE = "reading";
		private Context context;
		private SQLiteDatabase db;
		private ReadingDatabaseHelper dbHelper;

		public ReadingAdapter(Context context) {
			this.context = context;
		}

		public ReadingAdapter open() throws SQLException {
			dbHelper = new ReadingDatabaseHelper(context);
			db = dbHelper.getWritableDatabase();
			Log.d("Database Opened ", this.toString());
			return this;
		}

		public void close() {
			dbHelper.close();
			Log.d("Database Closed", this.toString());
		}

		
		/**
		 * Create a new todo If the todo is successfully created return the new
		 * rowId for that note, otherwise return a -1 to indicate failure.
		 */

		public long createReading(String traverse, String observation, String type, String reading, String label, String modified_date) {
			ContentValues values = createContentValues(traverse, observation, type, reading, label, modified_date);

			return db.insert(DB_TABLE, null, values);
		}
		/**
		 * Update the todo
		 */

		public boolean updateReading(long rowId, String traverse, String observation, String type, String reading, String label, String modified_date) {
			ContentValues values = createContentValues(traverse, observation, type, reading, label, modified_date);

			return db.update(DB_TABLE, values, KEY_ROWID + "=" + rowId, null) > 0;
		}

		
		/**
		 * Deletes todo
		 */

		public boolean deleteReading(long rowId) {
			return db.delete(DB_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
		}

		
		/**
		 * Return a Cursor over the list of all todo in the database
		 * 
		 * @return Cursor over all notes
		 */

		public Cursor fetchAllReadings() {
			return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_NAME,
					KEY_TYPE, KEY_STAFFMAN, KEY_OBSERVER, KEY_SURVEYDATE, KEY_MODIFIEDDATE }, null, null, null, null, null);
		}

		
		/**
		 * Return a Cursor positioned at the defined todo
		 */

		public Cursor fetchReading(long rowId) throws SQLException {
			Cursor mCursor = db.query(DB_TABLE, new String[] { KEY_ROWID,
					KEY_NAME, KEY_TYPE, KEY_STAFFMAN, KEY_OBSERVER, KEY_SURVEYDATE, KEY_MODIFIEDDATE }, KEY_ROWID + "="
					+ rowId, null, null, null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
		}

		private ContentValues createContentValues(String name, String type,	String observer, String staffman, String survey_date, String modified_date) {
			ContentValues values = new ContentValues();
			values.put(KEY_NAME, name);
			values.put(KEY_TYPE, type);
			values.put(KEY_OBSERVER, observer);
			values.put(KEY_STAFFMAN, staffman);
			values.put(KEY_SURVEYDATE, survey_date);
			values.put(KEY_MODIFIEDDATE, modified_date);
			return values;
		}
}
