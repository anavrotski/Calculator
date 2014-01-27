package com.example.calculator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * @author Andrei. Activity with history of previous inputed equations and
 *         results.
 */
public class History extends Activity {
	ListView lvMain;
	ArrayList<Item> items;

	@SuppressWarnings("unchecked")
	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);

		Intent intent = getIntent();
		items = (ArrayList<Item>) intent.getSerializableExtra("items");

		lvMain = (ListView) findViewById(R.id.lvMain);
		lvMain.setAdapter(new MyAdapter(this, items));
	}

	public void searchGoogle(View v) {
		int pos = lvMain.getPositionForView(v);
		String URL = "https://www.google.com.ua/search?q="
				+ Uri.encode(items.get(pos).getEquation());
		Intent httpIntent = new Intent(Intent.ACTION_VIEW);
		httpIntent.setData(Uri.parse(URL));

		startActivity(httpIntent);
	}
}
