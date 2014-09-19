package com.infopicmob.transformer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.infopicmob.transformer.R;
import com.infopicmob.transformer.view.SimpleFoldView;

public class SimpleFold extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.simple_fold, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final SimpleFoldView foldView = (SimpleFoldView) view.findViewById(R.id.fold);
		final TextView label = (TextView) view.findViewById(R.id.label);
		final SeekBar seek = (SeekBar) view.findViewById(R.id.seek);
		seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				float newFolding = (float) progress / seekBar.getMax();

				foldView.setFolding(newFolding);
				label.setText(String.valueOf(newFolding));
			}

			public void onStartTrackingTouch(SeekBar seekBar) {}
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
	}
}