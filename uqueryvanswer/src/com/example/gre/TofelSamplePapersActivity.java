package com.example.gre;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TofelSamplePapersActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle("TOFEL Sample Papers");
		setContentView(R.layout.tofel_sample_papers_layout);
	}

	public void onSamplePaper5Click(View view) {
		readFromAssets("tofel/samplepaper/TOEFL Sample Paper 5.pdf");
	}

	public void onSamplePaper4Click(View view) {
		readFromAssets("tofel/samplepaper/TOEFL Sample Paper 4.pdf");
	}

	public void onSamplePaper3Click(View view) {
		readFromAssets("tofel/samplepaper/TOEFL Sample Paper 3.pdf");
	}

	public void onSamplePaper2Click(View view) {
		readFromAssets("tofel/samplepaper/TOEFL Sample Paper 2.pdf");
	}

	public void onSamplePaper1Click(View view) {
		readFromAssets("tofel/samplepaper/TOEFL Sample Paper 1.pdf");
	}

	private void readFromAssets(String fileName) {
		AssetManager assetManager = getAssets();

		InputStream in = null;
		OutputStream out = null;
		File file = new File(getFilesDir(), fileName.split("/")[2]);
		try {
			in = assetManager.open(fileName);
			out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(
				Uri.parse("file://" + getFilesDir() + "/"
						+ fileName.split("/")[2]), "application/pdf");

		startActivity(intent);
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}
}
