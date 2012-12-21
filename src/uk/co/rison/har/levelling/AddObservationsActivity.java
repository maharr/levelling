package uk.co.rison.har.levelling;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import uk.co.rison.har.levelling.database.ReadingAdapter;


public class AddObservationsActivity extends Activity {
	public int current;	
	private ReadingAdapter mDbHelper;
	FrameLayout layout_MainMenu;
	public Long traverse;
	public Integer observation;
	public int pos;
	public Long mRowId;
	
	public void onCreate(Bundle savedInstanceState) {
		traverse = (Long) getIntent().getSerializableExtra("rowid");
		observation = (Integer) getIntent().getSerializableExtra("observation");
		super.onCreate(savedInstanceState);
    	 	mDbHelper = new ReadingAdapter(this);
		mDbHelper.open();
        setContentView(R.layout.add_observation);
        final Drawable dBS = findViewById(R.id.BS).getBackground();
        final Drawable dIS = findViewById(R.id.IS).getBackground(); 
        final Drawable dFS = findViewById(R.id.FS).getBackground(); 
        current = 1;
        layout_MainMenu = (FrameLayout) findViewById( R.id.addObs);
        layout_MainMenu.getForeground().setAlpha(0);
        
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
				saveData(current,false);
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
			Cursor cursor = mDbHelper.fetchISReadings(traverse,observation,reading);		
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
				
				displayData(2);
				Log.d("Position",Integer.toString(position));
				pos = position;
				showPopupMenu(view);
		        
				Toast.makeText(getApplicationContext(),	"Click ListItem Number " + position, Toast.LENGTH_LONG).show();
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
		final PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.edit_observation, null, false),400,300, true);
		pw.setFocusable(true);
		pw.showAtLocation(this.findViewById(R.id.addObs), Gravity.CENTER, 0, 0);
		layout_MainMenu.getForeground().setAlpha( 220); // dim
		final EditText updateReading = (EditText) pw.getContentView().findViewById(R.id.editReadingpop);
		final EditText updateLabel = (EditText) pw.getContentView().findViewById(R.id.editLabelpop);
		Button updatePopup = (Button) pw.getContentView().findViewById(R.id.saveEdit);
		Cursor cursor = mDbHelper.fetchISReadings(traverse,observation,current);
		cursor.move(pos+1);
		String rdng = Double.toString(cursor.getDouble(4));
		updateReading.setText(rdng);
		updateLabel.setText(cursor.getString(5));
		updatePopup.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//Get Valve
				Double value = Double.parseDouble(updateReading.getText().toString());
				//Get Label
				String label = updateLabel.getText().toString();
				//Get Current Time
				String modified_date = String.valueOf(System.currentTimeMillis());
				current = 2;
				Cursor cursor = mDbHelper.fetchISReadings(traverse,observation,current);
				cursor.move(pos+1);
				Long  idISpop = cursor.getLong(0);
				//String labelISpop = cursor.getString(5);
				//double readingISpop = cursor.getDouble(4);
				boolean sucess = mDbHelper.updateReading(idISpop,traverse,observation,current,value,label,modified_date);
				layout_MainMenu.getForeground().setAlpha( 0); // dim
				pw.dismiss();
				displayData(2);
			}
		
		
		});	
		
        
	}
	
		
	public void saveData(Integer type, Boolean popup){
		//Get Valve
		EditText val = (EditText) findViewById(R.id.eReadingEdit);
		Double value = Double.parseDouble(val.getText().toString());
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


