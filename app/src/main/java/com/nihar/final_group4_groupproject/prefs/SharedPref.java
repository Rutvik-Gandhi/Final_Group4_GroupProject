package com.nihar.final_group4_groupproject.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.nihar.final_group4_groupproject.model.UserModel;

public class SharedPref {

    private final Context context;


    private static final String defaultValue = "";
    private static final String myPreference = "myPref";
    private static final String DEFAULT_VALUE = "";
    private static final String MY_PREFERENCE = "myPref";
    private static final String KEY_LOGIN_STATUS = "login_status";
    private static final String KEY_FCM_TOKEN = "fcm_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_MOBILE_PRIMARY = "mobile_primary";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_MIDDLENAME = "middlename";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_MOBILE_SECONDARY = "mobile_secondary";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_POSITION = "position";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DOB = "dob";
    private static final String KEY_ANNIVERSARY = "anniversary";
    private static final String KEY_BLOODGROUP = "bloodgroup";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_STATE = "state";
    private static final String KEY_CITY = "city";
    private static final String KEY_ZIPCODE = "zipcode";
    private static final String KEY_RESIDENTIAL_ADDRESS = "residential_address";
    private static final String KEY_ADMIN_FLAG = "admin_flag";

    private static final String KEY_BROADCAST_REGISTRATION = "broadcast_registration_flag";

    private final SharedPreferences sharedPreferences;

    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void setBroadcastRegistrationFlag(boolean flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_BROADCAST_REGISTRATION, flag);
        editor.apply();
    }

    public boolean getBroadcastRegistrationFlag() {
        return sharedPreferences.getBoolean(KEY_BROADCAST_REGISTRATION, false);
    }

    public void setLoginStatus(boolean loginStatus) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_LOGIN_STATUS, loginStatus);
        editor.apply();
    }

    public boolean getLoginStatus() {
        return sharedPreferences.getBoolean(KEY_LOGIN_STATUS, false);
    }

    public void setFCMToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FCM_TOKEN, token);
        editor.apply();
    }

    public String getFCMToken() {
        return sharedPreferences.getString(KEY_FCM_TOKEN, DEFAULT_VALUE);
    }

    public void setId(String id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, id);
        editor.apply();
    }

    public String getId() {
        return sharedPreferences.getString(KEY_USER_ID, DEFAULT_VALUE);
    }

    public void setMobilePrimary(String mobilePrimary) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MOBILE_PRIMARY, mobilePrimary);
        editor.apply();
    }

    public String getMobilePrimary() {
        return sharedPreferences.getString(KEY_MOBILE_PRIMARY, DEFAULT_VALUE);
    }

    public void setFirstname(String firstname) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FIRSTNAME, firstname);
        editor.apply();
    }

    public String getFirstname() {
        return sharedPreferences.getString(KEY_FIRSTNAME, DEFAULT_VALUE);
    }

    public void setMiddlename(String middlename) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MIDDLENAME, middlename);
        editor.apply();
    }

    public String getMiddlename() {
        return sharedPreferences.getString(KEY_MIDDLENAME, DEFAULT_VALUE);
    }

    public void setLastname(String lastname) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LASTNAME, lastname);
        editor.apply();
    }

    public String getLastname() {
        return sharedPreferences.getString(KEY_LASTNAME, defaultValue);
    }

    public void setMobileSecondary(String mobileSecondary) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MOBILE_SECONDARY, mobileSecondary);
        editor.apply();
    }

    public String getMobileSecondary() {
        return sharedPreferences.getString(KEY_MOBILE_SECONDARY, defaultValue);
    }

    public void setCategory(String category) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CATEGORY, category);
        editor.apply();
    }

    public String getCategory() {
        return sharedPreferences.getString(KEY_CATEGORY, defaultValue);
    }

    public void setPosition(String position) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_POSITION, position);
        editor.apply();
    }

    public String getPosition() {
        return sharedPreferences.getString(KEY_POSITION, defaultValue);
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, defaultValue);
    }

    public void setDOB(String dob) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (dob.equals("0000-00-00")) {
            editor.putString(KEY_DOB, "");
        } else {
            editor.putString(KEY_DOB, dob);
        }
        editor.apply();
    }

    public String getDOB() {
        return sharedPreferences.getString(KEY_DOB, defaultValue);
    }

    public void setAnniversary(String anniversary) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (anniversary.equals("0000-00-00")) {
            editor.putString(KEY_ANNIVERSARY, "");
        } else {
            editor.putString(KEY_ANNIVERSARY, anniversary);
        }
        editor.apply();
    }

    public String getAnniversary() {
        return sharedPreferences.getString(KEY_ANNIVERSARY, defaultValue);
    }

    public void setBloodGroup(String bloodgroup) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_BLOODGROUP, bloodgroup);
        editor.apply();
    }

    public String getBloodGroup() {
        return sharedPreferences.getString(KEY_BLOODGROUP, defaultValue);
    }

    public void setGender(String gender) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_GENDER, gender);
        editor.apply();
    }

    public String getGender() {
        return sharedPreferences.getString(KEY_GENDER, defaultValue);
    }

    public void setCountry(String country) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
//  String defaultCountryValue = getResources().getStringArray(R.array.spinner_select_category)[0];
//
//  if (country.equals(defaultCountryValue)) {
//      editor.putString(KEY_COUNTRY, "");
//  } else {
        editor.putString(KEY_COUNTRY, country);
//  }
        editor.apply();
    }

    public String getCountry() {
        return sharedPreferences.getString(KEY_COUNTRY, defaultValue);
    }

    public void setState(String state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
//  String defaultStateValue = getResources().getStringArray(R.array.spinner_select_category)[0];
//
//  if (state.equals(defaultStateValue)) {
//      editor.putString(KEY_STATE, "");
//  } else {
        editor.putString(KEY_STATE, state);
//  }
        editor.apply();
    }

    public String getState() {
        return sharedPreferences.getString(KEY_STATE, defaultValue);
    }

    public void setCity(String city) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
//  String defaultStateValue = getResources().getStringArray(R.array.spinner_select_category)[0];
//
//  if (city.equals(defaultStateValue)) {
//      editor.putString(KEY_CITY, "");
//  } else {
        editor.putString(KEY_CITY, city);
//  }
        editor.apply();
    }

    public String getCity() {
        return sharedPreferences.getString(KEY_CITY, defaultValue);
    }

    public void setZipcode(String zipcode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ZIPCODE, zipcode);
        editor.apply();
    }

    public String getZipcode() {
        return sharedPreferences.getString(KEY_ZIPCODE, defaultValue);
    }

    public void setResidentialAddress(String residentialAddress) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_RESIDENTIAL_ADDRESS, residentialAddress);
        editor.apply();
    }

    public String getResidentialAddress() {
        return sharedPreferences.getString(KEY_RESIDENTIAL_ADDRESS, defaultValue);
    }

    public void setAdminFlag(Boolean flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ADMIN_FLAG, flag);
        editor.apply();
    }

    public Boolean getAdminFlag() {
        return sharedPreferences.getBoolean(KEY_ADMIN_FLAG, false);
    }

    public UserModel getAllDetails() {
        String userId = getId();
        String mobilePrimary = getMobilePrimary();
        String firstName = getFirstname();
        String middleName = getMiddlename();
        String lastName = getLastname();
        String mobileSecondary = getMobileSecondary();
        String email = getEmail();
        String dob = getDOB();
        String anniversary = getAnniversary();
        String bloodgroup = getBloodGroup();
        String gender = getGender();
        String country = getCountry();
        String state = getState();
        String city = getCity();
        String zipcode = getZipcode();
        String residentialAddress = getResidentialAddress();
        String position = getPosition();
        String category = getCategory();

        return new UserModel(userId, mobilePrimary, firstName, middleName, lastName, mobileSecondary, email, dob, anniversary, bloodgroup, gender, country, state, city, zipcode, residentialAddress, position, category);
    }

    public void clearEditor() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        setLoginStatus(false);
        editor.apply();
    }

    public String defaultValue() {
        return defaultValue;
    }



}