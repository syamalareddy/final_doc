package com.example.gre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TofelMockTestListActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tofel_mock_list_layout);
		getActionBar().setTitle("TOFEL Mock Test List");
	}

	public void onMockTest1Click(View view) {
		Intent intent = new Intent(this, GREMockTestActivity.class);
		intent.putExtra("isFor", "tofel/mocktest/tofelMockTest1.txt");
		startActivity(intent);
	}

	public void onMockTest2Click(View view) {
		Intent intent = new Intent(this, GREMockTestActivity.class);
		intent.putExtra("isFor", "tofel/mocktest/tofelMockTest2.txt");
		startActivity(intent);
	}

	public void onMockTest3Click(View view) {
		Intent intent = new Intent(this, GREMockTestActivity.class);
		intent.putExtra("isFor", "tofel/mocktest/tofelMockTest3.txt");
		startActivity(intent);
	}

	public void onMockTest4Click(View view) {
		Intent intent = new Intent(this, GREMockTestActivity.class);
		intent.putExtra("isFor", "tofel/mocktest/tofelMockTest4.txt");
		startActivity(intent);
	}



}
