package uk.co.rison.har.levelling;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import uk.co.rison.har.levelling.database.ReadingAdapter;
import uk.co.rison.har.levelling.database.TraverseAdapter;

public class DisplayTraverse extends Activity {

	private TraverseAdapter DBHelper;
	private ReadingAdapter mDBHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_traverse);
		DBHelper = new TraverseAdapter(DisplayTraverse.this);
		DBHelper.open();
		mDBHelper = new ReadingAdapter(DisplayTraverse.this);
		mDBHelper.open();

	}

	protected void onResume() {
		super.onResume();
		FillList();
	}

	public void FillList() {
		Log.d("Open", "FillList");
		// Define List
		ListView listView = (ListView) findViewById(R.id.traverseListView);
		// Retrieve Readings from DB
		final Cursor cursor = DBHelper.fetchAllTraverses();
		// Use ArrayList as Size is not yet known
		ArrayList<String> TraverseList = new ArrayList<String>();
		// Fill array list with number of traverses found
		if (cursor.moveToFirst() != false) {
			int i;
			for (i = 0; i < cursor.getCount(); i++) {
				cursor.moveToPosition(i);
				TraverseList.add("Traverse " + cursor.getString(1));
				Log.d("traverse number", cursor.getString(0));
			}
		}
		// Convert ArrayList into Array
		String[] Traverses = new String[TraverseList.size()];
		Traverses = TraverseList.toArray(Traverses);
		// First paramenter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the View to which the data is written
		// Forth - the Array of data
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				Traverses);

		// Assign adapter to ListView
		listView.setAdapter(adapter);
		// Set Listener for returning to edit an observation
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Bundle b = new Bundle();
				cursor.moveToPosition(position);
				Log.d("Traverse Number", cursor.getString(0));
				Log.d("Position", Integer.toString(position));
				b.putLong("rowid", cursor.getLong(0));
				Intent i = new Intent(DisplayTraverse.this,
						DisplayPointsActivity.class);
				i.putExtras(b);
				DisplayTraverse.this.startActivity(i);

				Toast.makeText(getApplicationContext(),
						"Click ListItem Number " + position, Toast.LENGTH_LONG)
						.show();
			}
		});
	}

}
