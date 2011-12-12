package uk.co.rison.har.leveling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
        	  Log.w("Debug", "about to launch next screen");
        	  Intent intentMain = new Intent(LevellingActivity.this , NewTraverseActivity.class);
              LevellingActivity.this.startActivity(intentMain);           

          }
        });
        
    }
    
}