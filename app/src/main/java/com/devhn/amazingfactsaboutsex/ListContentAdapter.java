package com.devhn.amazingfactsaboutsex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListContentAdapter extends BaseAdapter {
	Context mContext;

	public ListContentAdapter(Context ctx) {
		mContext = ctx;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Constants.spines.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return Constants.spines[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub[
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater infalInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			v = infalInflater.inflate(R.layout.content_item, null);
		}
		TextView title = (TextView) v.findViewById(R.id.title);
		title.setText((String) getItem(position));
		return v;
	}

}
