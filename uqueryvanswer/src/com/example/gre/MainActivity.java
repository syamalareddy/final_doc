package com.example.gre;

import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import database.UserDatabaseHelper;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private EditText userName, pwd, phoneNumber, countryName, stateName,
			district, city;

	private UserDatabaseHelper dataBaseHelper;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	BaseApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataBaseHelper = UserDatabaseHelper.getInstance(this);
		application = (BaseApplication) getApplication();
		if (application.isLoggedIn()) {
			setHomePage(savedInstanceState);
		} else {
			setLoginPage(savedInstanceState);
		}
	}

	public void setLoginPage(final Bundle savedInstanceState) {
		setContentView(R.layout.login_signup_page);
		userName = (EditText) findViewById(R.id.userName);
		pwd = (EditText) findViewById(R.id.password);
		findViewById(R.id.login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String user = userName.getText().toString();
				String pass = pwd.getText().toString();
				if (user.equalsIgnoreCase("") && pass.equalsIgnoreCase("")) {
					Toast.makeText(MainActivity.this,
							"Please provide all the data", Toast.LENGTH_LONG)
							.show();
				} else {
					if (dataBaseHelper.isUserAvailable(user, pass)) {
						application.saveLoginIn(true);
						invalidateOptionsMenu();
						setHomePage(savedInstanceState);
					} else {
						Toast.makeText(MainActivity.this,
								"Incorrect user name and password",
								Toast.LENGTH_LONG).show();
					}

				}
			}

		});
		findViewById(R.id.Cancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MainActivity.this.finish();

			}
		});
		findViewById(R.id.signUp).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setContentView(R.layout.signup_page);

				userName = (EditText) findViewById(R.id.userName);
				pwd = (EditText) findViewById(R.id.password);
				phoneNumber = (EditText) findViewById(R.id.phoneNumber);
				countryName = (EditText) findViewById(R.id.countryName);
				stateName = (EditText) findViewById(R.id.stateName);
				district = (EditText) findViewById(R.id.district);
				city = (EditText) findViewById(R.id.city);

				findViewById(R.id.create).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {

								String user = userName.getText().toString();
								String pass = pwd.getText().toString();

								String mphoneNumber = phoneNumber.getText()
										.toString();
								String mcountryName = countryName.getText()
										.toString();
								String mstateName = stateName.getText()
										.toString();
								String mdistrict = district.getText()
										.toString();
								String mcity = city.getText().toString();

								if (user.equalsIgnoreCase("")
										&& pass.equalsIgnoreCase("")) {
									Toast.makeText(MainActivity.this,
											"Please provide all the data",
											Toast.LENGTH_LONG).show();
								} else {
									dataBaseHelper.addUser(user, pass,
											mphoneNumber, mcountryName,
											mstateName, mdistrict, mcity);

									application.saveLoginIn(true);
									invalidateOptionsMenu();
									setHomePage(null);

								}
							}
						});

				findViewById(R.id.Cancel).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								MainActivity.this.finish();
							}
						});

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (application.isLoggedIn()) {
			getMenuInflater().inflate(R.menu.main, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.logout) {
			application.saveLoginIn(false);
			finish();
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			
		}
		return super.onOptionsItemSelected(item);
	}

	private void setHomePage(Bundle savedInstanceState) {

		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = null;
			if (position == 0) {
				fragment = new HomeFragment();
			} else {
				fragment = new ContactUsFragment();
			}

			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);

			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

}
