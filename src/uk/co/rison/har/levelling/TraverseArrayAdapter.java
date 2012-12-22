package uk.co.rison.har.levelling;

import uk.co.rison.har.levelling.database.TraverseAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TraverseArrayAdapter extends ArrayAdapter<Integer> {
	private final Context context;
	private final Integer[] values;

	public TraverseArrayAdapter(Context context, Integer[] values) {
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.values = values;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		
		
		
		Cursor traverse = dbHelper.fetchTraverse(mRowId);
		
		Log.d("inserted", traverse.getString(traverse
				.getColumnIndexOrThrow(TraverseAdapter.KEY_NAME)));
		mName.setText(traverse.getString(traverse
				.getColumnIndexOrThrow(TraverseAdapter.KEY_NAME)));
		Log.d("test", "is this caught");
		mDate.setText(addSlashesDate(traverse.getString(traverse
				.getColumnIndexOrThrow(TraverseAdapter.KEY_SURVEYDATE))));
		mObserver.setText("O: "
				+ traverse.getString(traverse
						.getColumnIndexOrThrow(TraverseAdapter.KEY_OBSERVER)));
		mType.setText(asUpperCaseFirstChar(traverse.getString(traverse
				.getColumnIndexOrThrow(TraverseAdapter.KEY_TYPE))));
		mStaffman.setText("S: "
				+ traverse.getString(traverse
						.getColumnIndexOrThrow(TraverseAdapter.KEY_STAFFMAN)));
		
		
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		textView.setText(values[position]);
		// Change the icon for Windows and iPhone
		String s = values[position];
		if (s.startsWith("Windows7") || s.startsWith("iPhone")
				|| s.startsWith("Solaris")) {
			imageView.setImageResource(R.drawable.no);
		} else {
			imageView.setImageResource(R.drawable.ok);
		}

		return rowView;
	}

}
