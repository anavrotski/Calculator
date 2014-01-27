package com.example.calculator;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Andrei. Customized BaseAdapter.
 */
final class MyAdapter extends BaseAdapter {
	/**
	 * Interface to global information about an application environment. This is
	 * an abstract class whose implementation is provided by the Android system.
	 * It allows access to application-specific resources and classes, as well
	 * as up-calls for application-level operations such as launching
	 * activities, broadcasting and receiving intents, etc.
	 */
	// private final Context context;

	/**
	 * Instantiates a layout XML file into its corresponding View objects. It is
	 * never used directly. Instead, use getLayoutInflater() or
	 * getSystemService(String) to retrieve a standard LayoutInflater instance
	 * that is already hooked up to the current context and correctly configured
	 * for the device you are running on.
	 */
	private final LayoutInflater layoutInflater;

	/**
	 * Array of ListView element's data.
	 */
	private final ArrayList<Item> items;

	/**
	 * @param context
	 *            Android system context.
	 * @param itms
	 *            Array of items with data to show in ListView.
	 */
	@SuppressWarnings("unchecked")
	protected MyAdapter(final Context context, final Serializable itms) {
		this.items = (ArrayList<Item>) itms;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(final int pos) {
		return items.get(pos);
	}

	@Override
	public long getItemId(final int pos) {
		return pos;
	}

	/**
	 * @param pos
	 *            Position (int) in list.
	 * @return Equation (string) at target position.
	 */
	public String getEq(final int pos) {
		return items.get(pos).getEquation();
	}

	/**
	 * @param pos
	 *            Position (int) in list.
	 * @return Result of equation (string) at target position.
	 */
	public String getResult(final int pos) {
		return items.get(pos).getResult().toString();
	}

	@Override
	public View getView(final int pos, final View convertView,
			final ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			if (pos % 2 == 0) {
				view = layoutInflater.inflate(R.layout.item, parent, false);
			} else {
				view = layoutInflater.inflate(R.layout.item2, parent, false);
			}
		}

		((TextView) view.findViewById(R.id.tvEq)).setText(getEq(pos));
		((TextView) view.findViewById(R.id.tvRes)).setText(getResult(pos)
				.toString());
		return view;
	}

}
