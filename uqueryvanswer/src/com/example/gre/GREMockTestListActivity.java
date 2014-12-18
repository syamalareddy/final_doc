package com.example.gre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GREMockTestListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gre_mock_test_list_layout);
		getActionBar().setTitle("GRE Mock Test List");
	}

	public void onMockTest1Click(View view) {
		Intent intent = new Intent(this, GREMockTestActivity.class);
		intent.putExtra("isFor", "gre/mocktest/greMockTest1.txt");
		startActivity(intent);
	}

	public void onMockTest2Click(View view) {
		Intent intent = new Intent(this, GREMockTestActivity.class);
		intent.putExtra("isFor", "gre/mocktest/greMockTest2.txt");
		startActivity(intent);
	}

	public void onMockTest3Click(View view) {
		Intent intent = new Intent(this, GREMockTestActivity.class);
		intent.putExtra("isFor", "gre/mocktest/greMockTest3.txt");
		startActivity(intent);
	}

	public void onMockTest4Click(View view) {
		Intent intent = new Intent(this, GREMockTestActivity.class);
		intent.putExtra("isFor", "gre/mocktest/greMockTest4.txt");
		startActivity(intent);
	}

}
