package database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gre.model.User;

public class UserDatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "MasterGuideManager";

	private static final String TABLE_USER = "user";
	private static final String KEY_USER_NAME = "username";
	private static final String KEY_PWD = "password";
	private static final String KEY_PHONE_NUMBER = "phonenumber";
	private static final String KEY_COUNTRY_NAME = "countryname";
	private static final String KEY_STATE_NAME = "statename";
	private static final String KEY_DISTRICT = "district";
	private static final String KEY_CITY = "city";

	public UserDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	protected static UserDatabaseHelper dataBaseHelper;

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_USER_NAME + " TEXT  ," + KEY_PWD + " TEXT,"
				+ KEY_PHONE_NUMBER + " TEXT," + KEY_COUNTRY_NAME + " TEXT,"
				+ KEY_STATE_NAME + " TEXT," + KEY_DISTRICT + " TEXT,"
				+ KEY_CITY + " TEXT" + ")";

		db.execSQL(CREATE_TABLE_USER);

	}

	public static UserDatabaseHelper getInstance(Context context) {
		if (dataBaseHelper == null) {
			dataBaseHelper = new UserDatabaseHelper(context);
		}
		return dataBaseHelper;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		onCreate(db);
	}

	public void addUser(String user, String pwd, String phoneNumber,
			String countryName, String stateName, String districtName,
			String cityName) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_USER_NAME, user);
		values.put(KEY_PWD, pwd);
		values.put(KEY_PHONE_NUMBER, phoneNumber);
		values.put(KEY_COUNTRY_NAME, countryName);
		values.put(KEY_STATE_NAME, stateName);
		values.put(KEY_DISTRICT, districtName);
		values.put(KEY_CITY, cityName);
		db.insert(TABLE_USER, null, values);
		db.close();
	}

	public List<User> getUsersList() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_USER_NAME,
				KEY_PWD }, null, null, null, null, null, null);
		List<User> userList = new ArrayList<User>();
		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setUserName(cursor.getString(0));
				user.setPassword(cursor.getString(1));
				userList.add(user);
			} while (cursor.moveToNext());
		}
		return userList;
	}

	public boolean isUserAvailable(String userName, String pwd) {
		List<User> usersList = getUsersList();
		boolean isFound = false;
		for (User user : usersList) {
			if (user.getUserName().equals(userName)
					&& user.getPassword().equals(pwd)) {
				isFound = true;
				break;
			}
		}
		return isFound;

	}

}
