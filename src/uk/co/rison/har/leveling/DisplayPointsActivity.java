package uk.co.rison.har.leveling;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
	}
	
	public void PopulatePage(){
		//if (mRowId != null) {
			Log.d("test",Long.toString(mRowId));
			Cursor traverse = dbHelper.fetchTraverse(mRowId);
			Log.d("test","is this caught");
			startManagingCursor(traverse);
			Log.d("inserted",traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_NAME)));
			mName.setText(traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_NAME)));
			Log.d("test","is this caught");
			mDate.setText(traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_SURVEYDATE)));
			mObserver.setText(traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_OBSERVER)));
			mType.setText(traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_TYPE)));
			mStaffman.setText(traverse.getString(traverse.getColumnIndexOrThrow(TraverseAdapter.KEY_STAFFMAN)));
			
			
		//}
	}
}
