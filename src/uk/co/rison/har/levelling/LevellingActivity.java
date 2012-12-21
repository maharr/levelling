package uk.co.rison.har.levelling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LevellingActivity extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView tv = (TextView) findViewById(R.id.new_transverse);
        tv.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Intent i = new Intent(LevellingActivity.this,NewTraverseActivity.class);
        		LevellingActivity.this.startActivity(i);           
        	}
        	
       });
       Button addOb = (Button) findViewById(R.id.addObsText);
       addOb.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			Intent ii = new Intent(LevellingActivity.this,AddObservationsActivity.class);
    		LevellingActivity.this.startActivity(ii);  
		}
	});
        
        
    }
    
}