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
import android.webkit.WebView;

public class WebActivity extends Activity {
	private WebView webView;
	private String isFor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_layout);
		webView = (WebView) findViewById(R.id.webView);
		isFor = getIntent().getExtras().getString("IsFor");
		if (isFor.equalsIgnoreCase("GrePattern")) {
			getActionBar().setTitle("GRE Test Pattern");
			setGRETurorialPage();
		} else if (isFor.equalsIgnoreCase("TofelPattern")) {
			//setGRESamplePaper();
			getActionBar().setTitle("TOFEL Test Pattern");
			setTofelTurorialPage();
		}else if(isFor.equalsIgnoreCase("chooseGREUniversity")){
			getActionBar().setTitle("How to choose GRE University");
			setGREUnivesityChoosePage();
		}
	}

	public void setGRETurorialPage() {

		webView.loadUrl("file:///android_asset/gre/grepattern.html");

	}
	
	public void setTofelTurorialPage() {

		webView.loadUrl("file:///android_asset/tofel/tofelpattern.html");

	}
	
	public void setGREUnivesityChoosePage() {

		webView.loadUrl("file:///android_asset/gre/howtochoosegreuniversity.html");

	}
	
	private void ReadFromAssets()
	{
	    AssetManager assetManager = getAssets();

	    InputStream in = null;
	    OutputStream out = null;
	    File file = new File(getFilesDir(), "Paper1.pdf");
	    try
	    {
	        in = assetManager.open("gre/samplepaper/Paper1.pdf");
	        out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

	        copyFile(in, out);
	        in.close();
	        in = null;
	        out.flush();
	        out.close();
	        out = null;
	    } catch (Exception e)
	    {
	        Log.e("tag", e.getMessage());
	    }

	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    intent.setDataAndType(
	            Uri.parse("file://" + getFilesDir() + "/Paper1.pdf"),
	            "application/pdf");

	    startActivity(intent);
	}
	
	private void copyFile(InputStream in, OutputStream out) throws IOException
	{
	    byte[] buffer = new byte[1024];
	    int read;
	    while ((read = in.read(buffer)) != -1)
	    {
	        out.write(buffer, 0, read);
	    }
	}

	public void setGRESamplePaper() {
		

		
		Intent intent = new Intent(Intent.ACTION_VIEW);
	    intent.setDataAndType(
	            Uri.parse("file:///android_asset/gre/samplepaper/Paper1.pdf"),
	            "application/pdf");

	    startActivity(intent);
	}

}
