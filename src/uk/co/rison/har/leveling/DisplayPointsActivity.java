package uk.co.rison.har.leveling;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.co.rison.har.leveling.database.TraverseAdapter;

public class DisplayPointsActivity extends Activity {
	private TextView mName;
	private TextView mDate;
	private TextView mObserver;
	private TextView mType;
	private TextView mStaffman;	
	private TraverseAdapter dbHelper;
	private Long mRowId;
	
	public void onCreate(Bundle savedInstanceState) {
		mRowId = (Long) getIntent().getSerializableExtra("message");
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.display_traverse_points);
        mName = (TextView) findViewById(R.id.Name);
        mDate = (TextView) findViewById(R.id.Date);
        mObserver = (TextView) findViewById(R.id.Observer);
        mType = (TextView) findViewById(R.id.Type);
        mStaffman = (TextView) findViewById(R.id.StaffMan);
        Log.d("test","is this caught");
        dbHelper = new TraverseAdapter(DisplayPointsActivity.this);
		dbHelper.open();
		PopulatePage();
        Log.d("message", "finish oncreate");
        Button newObserve = (Button) findViewById(R.id.addObserve);
        newObserve.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Bundle b = new Bundle();
				b.putLong("message", mRowId);
				Intent i = new Intent(DisplayPointsActivity.this,AddObservationsActivity.class);
				i.putExtras(b);
				DisplayPointsActivity.this.startActivity(i);
				finish();
				
			}
				
		});
	}
	
	public void onDestroy(){
		super.onDestroy();
		
	}
	
	public void PopulatePage(){
		
			Log.d("test",Long.toString(mRowId));
			Cursor traverse = dbHelper.fetchTraverse(mRowId);
			Log.d("test","is this caught");
			startManagingCursor(traverse);
			Log.d("inserted",traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_NAME)));
			mName.setText(traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_NAME)));
			Log.d("test","is this caught");
			mDate.setText(addSlashesDate(traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_SURVEYDATE))));
			mObserver.setText(traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_OBSERVER)));
			mType.setText(asUpperCaseFirstChar(traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_TYPE))));
			mStaffman.setText(traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_STAFFMAN)));
			
			
		
	}

	public final static String asUpperCaseFirstChar(final String target) {

	    return Character.toUpperCase(target.charAt(0))
	            + (target.length() > 1 ? target.substring(1) : "");
	}
	
	public final static String addSlashesDate(final String date){
		Log.d("Date", date);
		return date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4, 8);
	}
}


