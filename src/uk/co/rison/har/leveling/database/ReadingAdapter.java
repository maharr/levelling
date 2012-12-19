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
		private TraverseDatabaseHelper dbHelper;

		public ReadingAdapter(Context context) {
			this.context = context;
		}

		public ReadingAdapter open() throws SQLException {
			dbHelper = new TraverseDatabaseHelper(context);
			db = dbHelper.getWritableDatabase();
			Log.d("Database Opened ", this.toString());
			return this;
		}

		public void close() {
			dbHelper.close();
			Log.d("Database Closed", this.toString());
		}

		
		/**
		 * Create a new reading If the reading is successfully created return the new
		 * rowId for that note, otherwise return a -1 to indicate failure.
		 */

		public long createReading(Long traverse, Integer observation, Integer type, Double reading, String label, String modified_date) {
			ContentValues values = createContentValues(traverse, observation, type, reading, label, modified_date);

			return db.insert(DB_TABLE, null, values);
		}
		/**
		 * Update the reading, returns true or false based on whether the update was sucessful
		 */

		public boolean updateReading(long rowId, Long traverse, Integer observation, Integer type, Double reading, String label, String modified_date) {
			ContentValues values = createContentValues(traverse, observation, type, reading, label, modified_date);

			return db.update(DB_TABLE, values, KEY_ROWID + "=" + rowId, null) > 0;
		}

		
		/**
		 * Deletes reading, returns true or false based on whether the update was successful
		 */

		public boolean deleteReading(long rowId) {
			return db.delete(DB_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
		}

		/**
		 * Return a Cursor over the list of all readings for current Observation
		 * 
		 * @return Cursor over all readings
		 */

		public Cursor fetchObservationReadings(Long traverse) {
			return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_TRAVERSE,
					KEY_OBSERVATION, KEY_TYPE, KEY_READING, KEY_LABEL, KEY_MODIFIEDDATE },KEY_TRAVERSE + "="
							+ traverse , null, null, null, null, null);
			
		}
		
		
		
		/**
		 * Return a Cursor over the list of all readings that have already been taken for the IS
		 * 
		 * @return Cursor over all readings
		 */

		public Cursor fetchISReadings(Long traverse, Integer observation, Integer type) {
			return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_TRAVERSE,
					KEY_OBSERVATION, KEY_TYPE, KEY_READING, KEY_LABEL, KEY_MODIFIEDDATE },KEY_TRAVERSE + "="
							+ traverse +" AND " + KEY_OBSERVATION + "=" + observation + " AND " + KEY_TYPE + "=" + type  , null, null, null, null, null);
		}
		
		/**
		 * Return a Cursor over the list of all reading in the database
		 * 
		 * @return Cursor over all readings
		 */

		public Cursor fetchAllReadings() {
			return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_TRAVERSE,
					KEY_OBSERVATION, KEY_TYPE, KEY_READING, KEY_LABEL, KEY_MODIFIEDDATE }, null, null, null, null, null);
		}

		
		/**
		 * Check if there is already a reading taken of the given type at the observation point
		 */

		public boolean checkDuplicate(Long traverse, Integer observation, Integer type) throws SQLException {
			Cursor mCursor = db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_TRAVERSE,
					KEY_OBSERVATION, KEY_TYPE, KEY_READING, KEY_LABEL, KEY_MODIFIEDDATE }, KEY_TRAVERSE + "="
					+ traverse +" AND " + KEY_OBSERVATION + "=" + observation +" AND " + KEY_TYPE + "=" + type , null , null, null, null, null);
			if (mCursor.getCount() != 0) {
				return true;
			}
			return false;
		}
		
		/**
		 * Return the id for the next observation
		 */
		
		public Integer nextObservation(Long traverse) throws SQLException {
			Cursor mCursor = db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_TRAVERSE,
					KEY_OBSERVATION, KEY_TYPE, KEY_READING, KEY_LABEL, KEY_MODIFIEDDATE }, KEY_TRAVERSE + "="
					+ traverse , null , null, null, null, null);
			
				return  mCursor.getCount();
			
		}
		
		/**
		 * Return the id for of the reading that has already been taken matching the type and observation
		 */
		
		public long idDuplicate(Long traverse, Integer observation, Integer type) throws SQLException {
			Cursor mCursor = db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_TRAVERSE,
					KEY_OBSERVATION, KEY_TYPE, KEY_READING, KEY_LABEL, KEY_MODIFIEDDATE }, KEY_TRAVERSE + "="
					+ traverse +" AND " + KEY_OBSERVATION + "=" + observation +" AND " + KEY_TYPE + "=" + type , null , null, null, null, null);
			Log.d("Column Count", Integer.toString(mCursor.getColumnCount()));
			Log.d("Column Names", Integer.toString(mCursor.getCount()));
			mCursor.moveToFirst();
			return mCursor.getLong(0);
					
		}
		
		public Cursor fetchReading(long rowId) throws SQLException {
			Cursor mCursor = db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_TRAVERSE,
					KEY_OBSERVATION, KEY_TYPE, KEY_READING, KEY_LABEL, KEY_MODIFIEDDATE }, KEY_ROWID + "="
					+ rowId, null, null, null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
		}

		private ContentValues createContentValues(Long traverse, Integer observation, Integer type, Double reading, String label, String modified_date) {
			ContentValues values = new ContentValues();
			values.put(KEY_TRAVERSE, traverse);
			values.put(KEY_OBSERVATION, observation);
			values.put(KEY_TYPE, type);
			values.put(KEY_READING, reading);
			values.put(KEY_LABEL, label);
			values.put(KEY_MODIFIEDDATE, modified_date);
			return values;
		}
}
