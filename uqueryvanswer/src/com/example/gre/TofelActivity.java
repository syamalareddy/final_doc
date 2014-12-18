package com.example.gre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class TofelActivity extends Activity implements OnClickListener {

	LinearLayout pattern, samplePaper, mockTest, assesment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle("TOFEL");
		setContentView(R.layout.tofel_layout);
		pattern = (LinearLayout) findViewById(R.id.pattern);
		samplePaper = (LinearLayout) findViewById(R.id.samplePapers);
		mockTest = (LinearLayout) findViewById(R.id.mockTest);
		assesment = (LinearLayout) findViewById(R.id.assesment);
		pattern.setOnClickListener(this);
		samplePaper.setOnClickListener(this);
		mockTest.setOnClickListener(this);
		assesment.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.pattern) {
			Intent intent = new Intent(this, WebActivity.class);
			intent.putExtra("IsFor", "TofelPattern");
			startActivity(intent);

		} else if (v.getId() == R.id.mockTest) {
			Intent intent = new Intent(this, TofelMockTestListActivity.class);
			startActivity(intent);

		} else if (v.getId() == R.id.assesment) {

		} else {

			Intent intent = new Intent(this, TofelSamplePapersActivity.class);
			startActivity(intent);
		}
	}

}
