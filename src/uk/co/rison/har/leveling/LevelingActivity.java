package uk.co.rison.har.leveling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;



public class LevelingActivity extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
             
       
               TextView tv = (TextView) findViewById(R.id.new_transverse);
               tv.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  Intent intentMain = new Intent(LevelingActivity.this , NewTraverseActivity.class);
              LevelingActivity.this.startActivity(intentMain);           

          }
        });
        
    }
  
    

	
}





    





