package com.infopicmob.transformer.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ClassNameArrayAdapter<T> extends ArrayAdapter<T> {
	public ClassNameArrayAdapter(Context context, List<T> items) {
		super(context, android.R.layout.simple_spinner_dropdown_item, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) super.getView(position, convertView, parent);
		view.setText(getItem(position).getClass().getSimpleName());
		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) super.getDropDownView(position, convertView, parent);
		view.setText(getItem(position).getClass().getSimpleName());
		return view;
	}
}