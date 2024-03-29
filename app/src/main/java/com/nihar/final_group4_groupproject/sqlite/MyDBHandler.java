package com.nihar.final_group4_groupproject.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nihar.final_group4_groupproject.R;
import com.nihar.final_group4_groupproject.model.ListItemCalendar;
import com.nihar.final_group4_groupproject.model.UserModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MyDBHandler extends SQLiteOpenHelper {
    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "we_app.db";
    public static final String TABLE_NAME = "users";
    public static final String defaultValue = "";
    public static final String TAG = "MyDBHandlerTAG";

    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_MOBILE_PRIMARY = "mobile_primary";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_MIDDLENAME = "middlename";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_MOBILE_SECONDARY = "mobile_secondary";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_ANNIVERSARY = "anniversary";
    public static final String COLUMN_BLOODGROUP = "bloodgroup";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_ZIPCODE = "zipcode";
    public static final String COLUMN_RESIDENTIAL_ADDRESS = "residential_address";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_CATEGORY = "category";

    private Context context;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                COLUMN_MOBILE_PRIMARY + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_FIRSTNAME + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_MIDDLENAME + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_LASTNAME + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_MOBILE_SECONDARY + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_EMAIL + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_DOB + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_ANNIVERSARY + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_BLOODGROUP + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_GENDER + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_COUNTRY + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_STATE + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_CITY + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_ZIPCODE + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_RESIDENTIAL_ADDRESS + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_POSITION + " TEXT DEFAULT '" + defaultValue + "', " +
                COLUMN_CATEGORY + " TEXT DEFAULT '" + defaultValue + "');";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean isUserExist(String userId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = " + userId;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public void addUser(UserModel model) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, model.getUserId());
        values.put(COLUMN_MOBILE_PRIMARY, model.getMobilePrimary());
        values.put(COLUMN_FIRSTNAME, model.getFirstname());
        values.put(COLUMN_MIDDLENAME, model.getMiddlename());
        values.put(COLUMN_LASTNAME, model.getLastname());
        values.put(COLUMN_MOBILE_SECONDARY, model.getMobileSecondary());
        values.put(COLUMN_EMAIL, model.getEmail());
        values.put(COLUMN_DOB, model.getDOB());
        values.put(COLUMN_ANNIVERSARY, model.getAnniversary());
        values.put(COLUMN_BLOODGROUP, model.getBloodGroup());
        values.put(COLUMN_GENDER, model.getGender());
        values.put(COLUMN_COUNTRY, model.getCountry());
        values.put(COLUMN_STATE, model.getState());
        values.put(COLUMN_CITY, model.getCity());
        values.put(COLUMN_ZIPCODE, model.getZipcode());
        values.put(COLUMN_RESIDENTIAL_ADDRESS, model.getResidentialAddress());
        values.put(COLUMN_POSITION, model.getPosition());
        try {
            values.put(COLUMN_CATEGORY, model.getCategory());
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage());
            addColumnIfNotExist();
        }

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteUser(String userId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_USER_ID + " = ?", new String[]{userId});
        db.close();
    }

    public void updateUserDetails(UserModel model) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, model.getUserId());
        values.put(COLUMN_MOBILE_PRIMARY, model.getMobilePrimary());
        values.put(COLUMN_FIRSTNAME, model.getFirstname());
        values.put(COLUMN_MIDDLENAME, model.getMiddlename());
        values.put(COLUMN_LASTNAME, model.getLastname());
        values.put(COLUMN_MOBILE_SECONDARY, model.getMobileSecondary());
        values.put(COLUMN_EMAIL, model.getEmail());
        values.put(COLUMN_DOB, model.getDOB());
        values.put(COLUMN_ANNIVERSARY, model.getAnniversary());
        values.put(COLUMN_BLOODGROUP, model.getBloodGroup());
        values.put(COLUMN_GENDER, model.getGender());
        values.put(COLUMN_COUNTRY, model.getCountry());
        values.put(COLUMN_STATE, model.getState());
        values.put(COLUMN_CITY, model.getCity());
        values.put(COLUMN_ZIPCODE, model.getZipcode());
        values.put(COLUMN_RESIDENTIAL_ADDRESS, model.getResidentialAddress());
        values.put(COLUMN_POSITION, model.getPosition());
        try {
            values.put(COLUMN_CATEGORY, model.getCategory());
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage());
            addColumnIfNotExist();
        }

        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME, values, COLUMN_USER_ID + " = ?", new String[]{model.getUserId()});
        db.close();
    }

    public ArrayList<ListItemCalendar> checkBirthdayToday() {
        ArrayList<ListItemCalendar> users = new ArrayList();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM", Locale.getDefault());
        String systemDate = simpleDateFormat.format(Calendar.getInstance().getTime());

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DOB + " LIKE '" + systemDate + "%'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
                String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME));
                String dob = cursor.getString(cursor.getColumnIndex(COLUMN_DOB));

                String name = firstName + " " + lastName;
                String type = context.getString(R.string.type_birthday);

                users.add(new ListItemCalendar(userId, name, dob, type));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public ArrayList<ListItemCalendar> checkBirthdayTomorrow() {
        ArrayList<ListItemCalendar> users = new ArrayList();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String tomorrowDate = simpleDateFormat.format(calendar.getTime());

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DOB + " LIKE '" + tomorrowDate + "%'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
                String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME));
                String dob = cursor.getString(cursor.getColumnIndex(COLUMN_DOB));

                String name = firstName + " " + lastName;
                String type = context.getString(R.string.type_birthday);

                users.add(new ListItemCalendar(userId, name, dob, type));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public ArrayList<ListItemCalendar> checkAnniversaryToday() {
        ArrayList<ListItemCalendar> users = new ArrayList();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM", Locale.getDefault());
        String systemDate = simpleDateFormat.format(Calendar.getInstance().getTime());

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ANNIVERSARY + " LIKE '" + systemDate + "%'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
                String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME));
                String anniversary = cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARY));

                String name = firstName + " " + lastName;
                String type = context.getString(R.string.type_anniversary);

                users.add(new ListItemCalendar(userId, name, anniversary, type));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public ArrayList<ListItemCalendar> checkAnniversaryTomorrow() {
        ArrayList<ListItemCalendar> users = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String tomorrowDate = simpleDateFormat.format(calendar.getTime());

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ANNIVERSARY + " LIKE '" + tomorrowDate + "%'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
                String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME));
                String anniversary = cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARY));

                String name = firstName + " " + lastName;
                String type = context.getString(R.string.type_anniversary);

                users.add(new ListItemCalendar(userId, name, anniversary, type));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> users = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_FIRSTNAME + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
                String mobilePrimary = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_PRIMARY));
                String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME));
                String middleName = cursor.getString(cursor.getColumnIndex(COLUMN_MIDDLENAME));
                String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME));
                String mobileSecondary = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_SECONDARY));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String dob = cursor.getString(cursor.getColumnIndex(COLUMN_DOB));
                String anniversary = cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARY));
                String bloodgroup = cursor.getString(cursor.getColumnIndex(COLUMN_BLOODGROUP));
                String gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
                String country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY));
                String state = cursor.getString(cursor.getColumnIndex(COLUMN_STATE));
                String city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
                String zipcode = cursor.getString(cursor.getColumnIndex(COLUMN_ZIPCODE));
                String residentialAddress = cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTIAL_ADDRESS));
                String position = cursor.getString(cursor.getColumnIndex(COLUMN_POSITION));

                String category = "";
                try {
                    category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                    Log.d(TAG, category);
                } catch (IllegalStateException e) {
                    Log.e(TAG, e.getMessage());
                    addColumnIfNotExist();
                }

                users.add(new UserModel(userId, mobilePrimary, firstName, middleName, lastName, mobileSecondary, email, dob, anniversary, bloodgroup, gender, country, state, city, zipcode, residentialAddress, position, category));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public boolean isColumnExist() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            db.rawQuery("SELECT " + COLUMN_CATEGORY + " FROM " + TABLE_NAME, null);
            Log.d(TAG, "exist");
            return true;
        } catch (SQLiteException e) {
            Log.e(TAG, "not exist");
            return false;
        }
    }

    public void addColumnIfNotExist() {
        SQLiteDatabase db = getWritableDatabase();
        if (!isColumnExist()) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_CATEGORY + " TEXT DEFAULT '" + defaultValue + "'");
        }
        db.close();
    }

    public UserModel getUserDetails(String mUserId) {
        UserModel userDetails = null;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = " + mUserId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
                String mobilePrimary = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_PRIMARY));
                String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME));
                String middleName = cursor.getString(cursor.getColumnIndex(COLUMN_MIDDLENAME));
                String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME));
                String mobileSecondary = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_SECONDARY));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String dob = cursor.getString(cursor.getColumnIndex(COLUMN_DOB));
                String anniversary = cursor.getString(cursor.getColumnIndex(COLUMN_ANNIVERSARY));
                String bloodgroup = cursor.getString(cursor.getColumnIndex(COLUMN_BLOODGROUP));
                String gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
                String country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY));
                String state = cursor.getString(cursor.getColumnIndex(COLUMN_STATE));
                String city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
                String zipcode = cursor.getString(cursor.getColumnIndex(COLUMN_ZIPCODE));
                String residentialAddress = cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENTIAL_ADDRESS));
                String position = cursor.getString(cursor.getColumnIndex(COLUMN_POSITION));
                String category = "";
                try {
                    category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                    Log.d(TAG, category);
                } catch (IllegalStateException e) {
                    Log.e(TAG, e.getMessage());
                    addColumnIfNotExist();
                }
                userDetails = new UserModel(userId, mobilePrimary, firstName, middleName, lastName, mobileSecondary, email, dob, anniversary, bloodgroup, gender, country, state, city, zipcode, residentialAddress, position, category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userDetails;
    }

    public void clearDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
}
