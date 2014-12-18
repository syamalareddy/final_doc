package com.example.gre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment implements OnClickListener {
	LinearLayout gre, tofel, universities;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.home_layout, null);
		gre = (LinearLayout) view.findViewById(R.id.gre);
		gre.setOnClickListener(this);
		tofel = (LinearLayout) view.findViewById(R.id.tofel);
		tofel.setOnClickListener(this);
		universities = (LinearLayout) view.findViewById(R.id.universities);
		universities.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.gre) {
			Intent intent = new Intent(getActivity(), GREActivity.class);
			startActivity(intent);

		} else if (v.getId() == R.id.tofel) {
			Intent intent = new Intent(getActivity(), TofelActivity.class);
			startActivity(intent);

		} else {
			Intent intent = new Intent(getActivity(), ListOfUniversities.class);
			startActivity(intent);
		}

	}

}
