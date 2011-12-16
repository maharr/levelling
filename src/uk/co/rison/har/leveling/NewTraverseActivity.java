package uk.co.rison.har.leveling;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import uk.co.rison.har.leveling.database.TraverseAdapter;

public class NewTraverseActivity extends Activity{
	private TraverseAdapter mDbHelper;
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
			
			@SuppressWarnings("unused")
			public void onClick(View v) {
				EditText NameInput = (EditText) findViewById(R.id.traverseName);
				String name = null;
				if  (NameInput.getText().toString().equals("")){
					name = null;
				}else{	
					name = NameInput.getText().toString();
				}
				
				RadioButton Closed = (RadioButton) findViewById(R.id.travClosed);
				RadioButton Open = (RadioButton) findViewById(R.id.travOpen);
				RadioButton Compound = (RadioButton) findViewById(R.id.travCompound);
				String type = null;
				
				if (Closed.isChecked()==true){
					type = "closed";
				}
				if (Open.isChecked()==true){
					type = "open";
				}
				if (Compound.isChecked()==true){
					type = "compound";
				}
				
				EditText ObserverInput = (EditText) findViewById(R.id.observer);
				String observer = ObserverInput.getText().toString();
				
				EditText StaffmanInput = (EditText) findViewById(R.id.staffman);
				String staffman = StaffmanInput.getText().toString();
				
				DatePicker DateInput = (DatePicker) findViewById(R.id.LevellingDate);
				String survey_date = (String.valueOf(DateInput.getDayOfMonth()) +   String.valueOf(DateInput.getMonth()) + String.valueOf(DateInput.getYear()) );
							
				String modified_date = String.valueOf(System.currentTimeMillis());
							
				mRowId = null;
				
				
				if (name != null){
					
					long id = mDbHelper.createTraverse(name,type,observer,staffman,survey_date,modified_date);
					if (id > 0) {
						mRowId = id;
						Log.d("Debug", "Row Id That Has Been Inserted Is " + mRowId );
						Log.d("The time That has been stored is" , name + type + observer + staffman + survey_date + modified_date );
						Log.d("Value of Name", "one" + name);
				
					}
				}else{
					Toast.makeText(getApplicationContext(), "Please Complete of the Fields marked *", Toast.LENGTH_LONG).show();
				}
			}		
		});
    } 
        
}
