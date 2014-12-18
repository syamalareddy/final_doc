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

public class ListOfUniversities extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle("List of Universities");
		setContentView(R.layout.listofuniversities_layout);
		
	}
	
	public void onTofelUniversitiesClick(View view){
		readFromAssets("tofel/samplepaper/Tofel destinations.pdf");
	}
	
	public void onChooseGREUniversityClick(View view){
		Intent intent = new Intent(this,WebActivity.class);
		intent.putExtra("IsFor", "chooseGREUniversity");
		startActivity(intent);
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
