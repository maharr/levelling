package uk.co.rison.har.leveling;

import android.R.color;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import uk.co.rison.har.leveling.database.ReadingAdapter;


public class AddObservationsActivity extends Activity {
	public int current;	
	private ReadingAdapter mDbHelper;
	private Long mRowId;
	public final Integer traverse = 1;
	public final Integer observation = 1;
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	mDbHelper = new ReadingAdapter(this);
		mDbHelper.open();
        setContentView(R.layout.add_observation);
        final Drawable dBS = findViewById(R.id.BS).getBackground();
        final Drawable dIS = findViewById(R.id.IS).getBackground(); 
        final Drawable dFS = findViewById(R.id.FS).getBackground(); 
        current = 1;      
        
        Button save = (Button) findViewById(R.id.saveButton);        
        Button BS = (Button) findViewById(R.id.BS);
        Button IS = (Button) findViewById(R.id.IS);
        Button FS = (Button) findViewById(R.id.FS);
        final PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP); 
        dBS.setColorFilter(filter);
        
        //LinearLayout Lay = (LinearLayout) findViewById(R.id.linearLayout1);
        //Lay.inflate(context, R.id.BS, this);
        TextView ObserveNumber = (TextView) findViewById(R.id.ObservationNumber);
        ObserveNumber.setText("Observation Number " + observation.toString());
        
        save.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				saveData(current);
			}
		});
        
        BS.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				current = 1;
				displayData(1);
				dBS.setColorFilter(filter);
		        findViewById(R.id.IS).invalidateDrawable(dIS);
		        dIS.clearColorFilter();
		        findViewById(R.id.FS).invalidateDrawable(dFS);
				dFS.clearColorFilter();
			}
		});
        
        IS.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				current = 2;
				displayData(2);
				dIS.setColorFilter(filter);
		        findViewById(R.id.BS).invalidateDrawable(dBS);
		        dBS.clearColorFilter();
		        findViewById(R.id.FS).invalidateDrawable(dFS);
				dFS.clearColorFilter();
				
						
			}
		});
        
        FS.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				current = 3;
				displayData(3);
				dFS.setColorFilter(filter);
		        findViewById(R.id.BS).invalidateDrawable(dBS);
		        dBS.clearColorFilter();
		        findViewById(R.id.IS).invalidateDrawable(dIS);
				dIS.clearColorFilter();
			}
		});
        
              
        
        
	}
	public void displayData(int reading){
		ListView listView = (ListView) findViewById(R.id.mylist);
		if (reading == 2){
			Cursor cursor = mDbHelper.fetchISReadings(traverse,observation);		
			String[] readingsIS = new String[cursor.getCount()];
			Long  [] idIS = new Long[cursor.getCount()];
			int i;
			for (i=0; i<cursor.getCount(); i++) {
				  	
					if (cursor.isLast()){
						break;
					}else{
						cursor.moveToNext();
						idIS[i] = cursor.getLong(0);
						readingsIS[i] = cursor.getString(5) + " - " + Double.toString(cursor.getDouble(4));
						
					}
						  
				}
			
			// First paramenter - Context
			// Second parameter - Layout for the row
			// Third parameter - ID of the View to which the data is written
			// Forth - the Array of data
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, readingsIS);
	
			// Assign adapter to ListView
			listView.setAdapter(adapter);
			
			
			listView.setOnItemClickListener(new ListView.OnItemClickListener() {
				
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				showPopupMenu(view);
		        
				Toast.makeText(getApplicationContext(),
				"Click ListItem Number " + position, Toast.LENGTH_LONG)
				.show();
			}
			});
			
		}else{
			String[] empty = new String [] {"Nothing Here!"}; 
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, android.R.id.text1, empty);
		
				// Assign adapter to ListView
				listView.setAdapter(adapter);
			
		}
		
	}
	
	public void showPopupMenu (View v){
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.edit_observation, null, false),400,300, true);
		pw.showAtLocation(this.findViewById(R.id.addObs), Gravity.CENTER, 0, 0);
        
	}
	
		
	public void saveData(Integer type){
		//Get Valve
		EditText val = (EditText) findViewById(R.id.eReadingEdit);
		Log.d("value first time", val.getText().toString());
		Double value = Double.parseDouble(val.getText().toString());
		Log.d("value", val.getText().toString());
		//Get Label
		EditText lab = (EditText) findViewById(R.id.elabelEdit);
		String label = lab.getText().toString();
		//Get Current Time
		String modified_date = String.valueOf(System.currentTimeMillis());	
		mRowId = null;
		boolean duplicate=false;
		if (type != 2){
			duplicate = mDbHelper.checkDuplicate(traverse,observation,type);
		}			
		Log.d("Save Data","Opened");
		if (value != null || label != null){
			if (duplicate == true ){
				long id = mDbHelper.idDuplicate(traverse,observation,type);
				boolean update = mDbHelper.updateReading(id,traverse,observation,type,value,label,modified_date);
				if (update == true) {
					mRowId = id;
					/*Bundle b = new Bundle();
					b.putLong("message", mRowId);
					Intent i = new Intent(NewTraverseActivity.this,DisplayPointsActivity.class);
					i.putExtras(b);
					NewTraverseActivity.this.startActivity(i);
					finish();
					*/
					Toast.makeText(getApplicationContext(), "Reading Number " + Long.toString(id) + " Updated", Toast.LENGTH_SHORT).show();
				
				}	
				
			}else{
				long id = mDbHelper.createReading(traverse,observation,type,value,label,modified_date);
				if (id > 0) {
					mRowId = id;
					/*Bundle b = new Bundle();
					b.putLong("message", mRowId);
					Intent i = new Intent(NewTraverseActivity.this,DisplayPointsActivity.class);
					i.putExtras(b);
					NewTraverseActivity.this.startActivity(i);
					finish();
					*/
					Toast.makeText(getApplicationContext(), "Reading Number " + Long.toString(id) , Toast.LENGTH_SHORT).show();
				
				}	
	
			}
			
		}else{
			Toast.makeText(getApplicationContext(), "Please Fill In Value and Label", Toast.LENGTH_SHORT).show();
		}
		
		if (type == 2){
			displayData(2);
		}

	}

}


