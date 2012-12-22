package uk.co.rison.har.levelling;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.rison.har.levelling.database.ReadingAdapter;
import uk.co.rison.har.levelling.database.TraverseAdapter;

public class DisplayPointsActivity extends Activity {
	private TextView mName;
	private TextView mDate;
	private TextView mObserver;
	private TextView mType;
	private TextView mStaffman;
	private TraverseAdapter dbHelper;
	private ReadingAdapter mDBHelper;
	private Long mRowId;

	public void onCreate(Bundle savedInstanceState) {
		mRowId = (Long) getIntent().getSerializableExtra("rowid");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_traverse_points);
		mName = (TextView) findViewById(R.id.Name);
		mDate = (TextView) findViewById(R.id.Date);
		mObserver = (TextView) findViewById(R.id.Observer);
		mType = (TextView) findViewById(R.id.Type);
		mStaffman = (TextView) findViewById(R.id.StaffMan);
		Log.d("test", "is this caught1");
		dbHelper = new TraverseAdapter(DisplayPointsActivity.this);
		dbHelper.open();
		mDBHelper = new ReadingAdapter(DisplayPointsActivity.this);
		mDBHelper.open();
		PopulatePage();
		// FillList();
		Log.d("message", "finish oncreate");
		Button newObserve = (Button) findViewById(R.id.addObserve);
		newObserve.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Bundle b = new Bundle();
				b.putLong("rowid", mRowId);
				// get next observation number
				Integer observation = mDBHelper.nextObservation(mRowId);
				b.putInt("observation", observation);
				Intent i = new Intent(DisplayPointsActivity.this,
						AddObservationsActivity.class);
				i.putExtras(b);
				DisplayPointsActivity.this.startActivity(i);
				// finish();

			}

		});
	}

	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	protected void onResume() {
		super.onResume();
		FillList();
	}

	public void FillList() {
		// Define List
		ListView listView = (ListView) findViewById(R.id.obsList);
		// Retrive Readings from DB
		Cursor cursor = mDBHelper.fetchObservationReadings(mRowId);
		// Use ArrayList as Size is not yet known
		ArrayList<String> ObservationsList = new ArrayList<String>();
		// Fill array list with number of observations completed
		if (cursor.moveToFirst() != false) {
			int i, d = -1;
			for (i = 0; i < cursor.getCount(); i++) {
				cursor.moveToPosition(i);
				if (d != cursor.getInt(2) && d <= cursor.getInt(2)) {
					ObservationsList.add("Observations " + cursor.getString(2));
				}
				d = cursor.getInt(2);
			}
		}
		// Convert ArrayList into Array
		String[] Observations = new String[ObservationsList.size()];
		Observations = ObservationsList.toArray(Observations);
		// First paramenter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the View to which the data is written
		// Forth - the Array of data
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				Observations);

		// Assign adapter to ListView
		listView.setAdapter(adapter);
		// Set Listener for returning to edit an observation
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Bundle b = new Bundle();
				b.putLong("rowid", mRowId);
				b.putInt("observation", position);
				Intent i = new Intent(DisplayPointsActivity.this,
						AddObservationsActivity.class);
				i.putExtras(b);
				DisplayPointsActivity.this.startActivity(i);

				Toast.makeText(getApplicationContext(),
						"Click ListItem Number " + position, Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	public void PopulatePage() {

		Log.d("test", Long.toString(mRowId));
		Cursor traverse = dbHelper.fetchTraverse(mRowId);
		Log.d("test", "is this caught");
		Log.d("inserted", traverse.getString(traverse
				.getColumnIndexOrThrow(TraverseAdapter.KEY_NAME)));
		mName.setText(traverse.getString(traverse
				.getColumnIndexOrThrow(TraverseAdapter.KEY_NAME)));
		Log.d("test", "is this caught");
		mDate.setText(addSlashesDate(traverse.getString(traverse
				.getColumnIndexOrThrow(TraverseAdapter.KEY_SURVEYDATE))));
		mObserver.setText("O: "
				+ traverse.getString(traverse
						.getColumnIndexOrThrow(TraverseAdapter.KEY_OBSERVER)));
		mType.setText(asUpperCaseFirstChar(traverse.getString(traverse
				.getColumnIndexOrThrow(TraverseAdapter.KEY_TYPE))));
		mStaffman.setText("S: "
				+ traverse.getString(traverse
						.getColumnIndexOrThrow(TraverseAdapter.KEY_STAFFMAN)));

	}

	public final static String asUpperCaseFirstChar(final String target) {

		return Character.toUpperCase(target.charAt(0))
				+ (target.length() > 1 ? target.substring(1) : "");
	}

	public final static String addSlashesDate(final String date) {
		Log.d("Date", date);
		return date.substring(0, 2) + "/" + date.substring(2, 4) + "/"
				+ date.substring(4, 8);
	}
}
