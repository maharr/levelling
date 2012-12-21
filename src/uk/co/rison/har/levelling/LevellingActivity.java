package uk.co.rison.har.levelling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LevellingActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//Assign the textview a name and a click handler to take the user to the creation of a new traverse
		TextView tv = (TextView) findViewById(R.id.new_traverse);
		tv.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(LevellingActivity.this,
						NewTraverseActivity.class);
				LevellingActivity.this.startActivity(i);
			}

		});
		//Assign the second textview a name and a click handler to take the user to view their saved traverses
		TextView tv2 = (TextView) findViewById(R.id.view_traverse);
		tv2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent ii = new Intent(LevellingActivity.this,
						DisplayTraverse.class);
				LevellingActivity.this.startActivity(ii);
			}
		});

	}

}