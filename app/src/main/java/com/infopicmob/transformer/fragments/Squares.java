package com.infopicmob.transformer.fragments;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infopicmob.transformer.R;
import com.infopicmob.transformer.view.SquaresLayout;

public class Squares extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.squares, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final SquaresLayout squares = (SquaresLayout) view.findViewById(R.id.squares);

		view.findViewById(R.id.animateForward).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ObjectAnimator.ofObject(squares, "openAmount", new FloatEvaluator(), 1).setDuration(1000).start();
			}
		});

		view.findViewById(R.id.animateBackwards).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ObjectAnimator.ofObject(squares, "openAmount", new FloatEvaluator(), 0).setDuration(1000).start();
			}
		});
	}
}