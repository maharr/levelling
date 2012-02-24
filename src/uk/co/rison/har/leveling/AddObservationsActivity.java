package uk.co.rison.har.leveling;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddObservationsActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.add_observation);
        final Drawable dBS = findViewById(R.id.BS).getBackground();
        final Drawable dIS = findViewById(R.id.IS).getBackground(); 
        final Drawable dFS = findViewById(R.id.FS).getBackground(); 
        final Integer Obsnumber = 1;
        
                
        Button BS = (Button) findViewById(R.id.BS);
        Button IS = (Button) findViewById(R.id.IS);
        Button FS = (Button) findViewById(R.id.FS);
        final PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP); 
        dBS.setColorFilter(filter);
        
        //LinearLayout Lay = (LinearLayout) findViewById(R.id.linearLayout1);
        //Lay.inflate(context, R.id.BS, this);
        TextView ObserveNumber = (TextView) findViewById(R.id.ObservationNumber);
        ObserveNumber.setText("Observation Number " + Obsnumber.toString());
        
        BS.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				dBS.setColorFilter(filter);
		        findViewById(R.id.IS).invalidateDrawable(dIS);
		        dIS.clearColorFilter();
		        findViewById(R.id.FS).invalidateDrawable(dFS);
				dFS.clearColorFilter();
			}
		});
        
        IS.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				dIS.setColorFilter(filter);
		        findViewById(R.id.BS).invalidateDrawable(dBS);
		        dBS.clearColorFilter();
		        findViewById(R.id.FS).invalidateDrawable(dFS);
				dFS.clearColorFilter();
			}
		});
        
        FS.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				dFS.setColorFilter(filter);
		        findViewById(R.id.BS).invalidateDrawable(dBS);
		        dBS.clearColorFilter();
		        findViewById(R.id.IS).invalidateDrawable(dIS);
				dIS.clearColorFilter();
			}
		});
        
              
        
        
	}

}

