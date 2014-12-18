package com.example.gre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends Activity {
	int scoreAcheived;
	TextView scoreView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_layout);
		scoreView = (TextView) findViewById(R.id.score);
		scoreAcheived = getIntent().getExtras().getInt("score");
		scoreView.setText("Your score : " + scoreAcheived);
	}

	public void onDoneClick(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

	}

}
