package com.example.gre;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ContactUsFragment extends Fragment {

	EditText name, email, phone, query;
	Button submitButton, cancelButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.contact_us_layout, null);
		name = (EditText) view.findViewById(R.id.name);
		email = (EditText) view.findViewById(R.id.email);
		phone = (EditText) view.findViewById(R.id.phone);
		query = (EditText) view.findViewById(R.id.query);
		submitButton = (Button) view.findViewById(R.id.submitButton);
		cancelButton = (Button) view.findViewById(R.id.cancelButton);
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String mName = name.getText().toString();
				String mEmail = email.getText().toString();
				String mPhone = phone.getText().toString();
				String mQuery = query.getText().toString();
				String body = "Hi, \n\n Please find the details, \n\n";
				body = body + "Name :" + mName + "\n";
				body = body + "Email :" + mEmail + "\n";
				body = body + "Phone :" + mPhone + "\n";
				body = body + "Query :" + mQuery + "\n";

				String subject = "Masters Guide";
				StringBuilder builder = new StringBuilder("mailto:");
				if (subject != null) {
					builder.append("?subject=" + subject);
					if (body != null) {
						builder.append("&body=" + body);
					}
				}
				String uri = builder.toString();
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
				startActivity(intent);
			}
		});
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name.setText("");
				email.setText("");
				phone.setText("");
				query.setText("");
			}
		});

		return view;
	}

}
