package com.infopicmob.transformer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infopicmob.transformer.R;

public class Door extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.door, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		DrawerLayout drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
		final View drawer = view.findViewById(R.id.drawer);

		drawer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				v.setPivotY(v.getHeight() / 2);
			}
		});
		drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(View view, float v) {
				drawer.setRotationY(90 * (1 - v));
				drawer.setTranslationX(drawer.getWidth() * (1 - v));
			}

			@Override
			public void onDrawerOpened(View view) {}
			@Override
			public void onDrawerClosed(View view) {}
			@Override
			public void onDrawerStateChanged(int i) {}
		});
	}
}