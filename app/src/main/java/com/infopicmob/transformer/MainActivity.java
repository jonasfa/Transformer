package com.infopicmob.transformer;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.infopicmob.transformer.fragments.Door;
import com.infopicmob.transformer.fragments.Map;
import com.infopicmob.transformer.widget.ClassNameArrayAdapter;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {
	private final List<Fragment> fragments = Arrays.asList(new Map(), new Door());

	@Override
	@SuppressWarnings("ConstantConditions")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getActionBar().setListNavigationCallbacks(new ClassNameArrayAdapter<Fragment>(getActionBar().getThemedContext(), fragments), navigationListener);
	}

	private ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {
		public boolean onNavigationItemSelected(int itemPosition, long itemId) {
			getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragments.get(itemPosition)).commit();

			return true;
		}
	};
}