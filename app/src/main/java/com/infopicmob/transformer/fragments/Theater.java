package com.infopicmob.transformer.fragments;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infopicmob.transformer.R;
import com.infopicmob.transformer.view.CurtainLayout;

public class Theater extends Fragment {
	private CurtainLayout curtainLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.theater, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		curtainLayout = (CurtainLayout) view.findViewById(R.id.curtain);
		view.findViewById(android.R.id.button1).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				openCurtain();
			}
		});

		view.findViewById(android.R.id.button2).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				closeCurtain();
			}
		});
	}

	private void openCurtain() {
		ObjectAnimator.ofObject(curtainLayout, "openAmount", new FloatEvaluator(), 1).setDuration(2000).start();
	}

	private void closeCurtain() {
		ObjectAnimator.ofObject(curtainLayout, "openAmount", new FloatEvaluator(), 0).setDuration(2000).start();
	}
}