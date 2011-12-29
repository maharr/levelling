package uk.co.rison.har.leveling;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.Button;

public class AddObservationsActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.add_observation);
        Button BS = (Button) findViewById(R.id.BS);
        BS.setBackgroundColor(Color.rgb(250, 105, 0));
	}

}
