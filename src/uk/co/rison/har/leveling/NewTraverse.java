package uk.co.rison.har.leveling;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import uk.co.rison.har.leveling.database.TraverseAdapter;


public class NewTraverse extends Activity {
	private Long mRowId;
	private TraverseAdapter mDbHelper;
	//private String name;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mDbHelper = new TraverseAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.new_traverse);
		
		Button cn = (Button) findViewById(R.id.TraverseInfo);
		cn.setOnClickListener(new View.OnClickListener() {
	
				public void onClick(View v) {
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
					
						
					
										
						long id = mDbHelper.createTodo(name, type, observer, staffman, survey_date, modified_date);
							if (id > 0) {
								mRowId = id;
							}
						
				Context context = getApplicationContext();
				CharSequence text = mRowId.toString();
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
					
		
				}
});
			
	

	}
}