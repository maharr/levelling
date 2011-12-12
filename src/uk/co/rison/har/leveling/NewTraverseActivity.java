package uk.co.rison.har.leveling;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import uk.co.rison.har.leveling.database.TraverseAdapter;

public class NewTraverseActivity extends Activity{
	private TraverseAdapter mDbHelper;
	private Cursor cursor;
	private Long mRowId;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.new_traverse);
        mDbHelper = new TraverseAdapter(this);
		mDbHelper.open();
        Log.w("Debug", "Page Has been Opened");
        Button CreateTraverse = (Button) findViewById(R.id.TraverseInfo);
        CreateTraverse.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EditText NameBox = (EditText) findViewById(R.id.traverseName);
				String name = NameBox.getText().toString();
				
				/*Log.w("Clicked Button", "Clicked Button");
				EditText NameInput = (EditText) findViewById(R.id.traverseName);
				String name = NameInput.getText().toString();
				
				RadioButton TypeInput = (RadioButton) findViewById(R.id.traverseType);
				String type = TypeInput.getText().toString();
				
				EditText ObserverInput = (EditText) findViewById(R.id.observer);
				String observer = ObserverInput.getText().toString();
				
				EditText StaffmanInput = (EditText) findViewById(R.id.staffman);
				String staffman = StaffmanInput.getText().toString();
				
				long survey_date = System.currentTimeMillis();
				
				long modified_date = survey_date;
				
				mRowId = null;
				*/
					long id = mDbHelper.createTraverse(name,"open","Dan","Matt","Number","Number");
					if (id > 0) {
						mRowId = id;
						Log.w("Debug", "Button has been pressed." + mRowId );
				
					}
			}		
		});
    } 
        
}
