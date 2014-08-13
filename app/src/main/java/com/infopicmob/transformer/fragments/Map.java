package com.infopicmob.transformer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.infopicmob.transformer.R;
import com.infopicmob.transformer.view.AccordionLayout;

public class Map extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.map, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final AccordionLayout accordion = (AccordionLayout) view.findViewById(R.id.accordion);
		final ScrollView scroll = (ScrollView) view.findViewById(R.id.scroll);
		scroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
			public void onScrollChanged() {
				accordion.setFolding(scroll.getScrollY() / (float) accordion.getHeight() / 1.15f);
			}
		});
	}
}