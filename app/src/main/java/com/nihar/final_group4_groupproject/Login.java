package com.nihar.final_group4_groupproject;

import static com.nihar.final_group4_groupproject.api.APIClient.postClient;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.nihar.final_group4_groupproject.api.APIInterface;
import com.nihar.final_group4_groupproject.bg_worker.RegisterAlarmBroadcast;
import com.nihar.final_group4_groupproject.firebase.Topic;
import com.nihar.final_group4_groupproject.model.UserModel;
import com.nihar.final_group4_groupproject.prefs.SharedPref;
import com.nihar.final_group4_groupproject.response.Response;
import com.nihar.final_group4_groupproject.sqlite.MyDBHandler;

import retrofit2.Call;
import retrofit2.Callback;

public class Login extends AppCompatActivity {

    private static final String TAG = "LoginTAG";
    private SharedPref sharedPref;
    private MyDBHandler dbHandler;
    MaterialButton btnLogin;
    TextInputLayout layoutPassword;
    private ImageOperations imageOperations;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        sharedPref = new SharedPref(this);
        dbHandler = new MyDBHandler(this);
        imageOperations = new ImageOperations(this);

        toolbar = findViewById(R.id.toolbar);
        btnLogin = findViewById(R.id.btnLogin);
        layoutPassword = findViewById(R.id.layoutPassword);

        toolbar();
//        checkForUpdate();
        initialCalls();
        skipIfLoggedIn();
        handleButtonClicks();
    }

    private void toolbar() {
        setSupportActionBar(toolbar);
        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        }
        if (toolbar.getOverflowIcon() != null) {
            toolbar.getOverflowIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initialCalls() {
        new Topic(this).subscribe("global");
        registerBroadcast();
    }

    private void registerBroadcast() {
        if (!sharedPref.getBroadcastRegistrationFlag()) {
            new RegisterAlarmBroadcast(this).execute();
        }
    }

    private void skipIfLoggedIn() {
        if (sharedPref.getLoginStatus()) {
            startActivity(new Intent(this, Categories.class));
            finish();
        }
    }

    private void handleButtonClicks() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNullOrEmpty(layoutMobile)) {
                    return;
                }
                if (isPasswordLayoutVisible()) {
                    if (checkNullOrEmpty(layoutPassword)) {
                        return;
                    }
                }
                String userMobile = getValue(layoutMobile);
                String userPassword = getValue(layoutPassword);
                userLogin(userMobile, userPassword);
            }
        });
    }

    private boolean isPasswordLayoutVisible() {
        return layoutPassword.getVisibility() == View.VISIBLE;
    }

    private void userLogin(String userMobile, String userPassword) {
        if (!internetAvailable()) {
            view.displaySnackBar(getString(R.string.no_internet));
            return;
        }
        showProgressDialog();

        APIInterface apiService = postClient().create(APIInterface.class);
        Call<Response.UserLogin> call = isPasswordLayoutVisible() ? apiService.performUserLogin(userMobile, userPassword) : apiService.performUserLogin(userMobile);

        call.enqueue(new Callback<Response.UserLogin>() {
            @Override
            public void onResponse(Call<Response.UserLogin> call, retrofit2.Response<Response.UserLogin> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getResponse()) {
                        case "ok":
                            User user = response.body().getUser();

                            int userId = user.getUserId();
                            String mobilePrimary = user.getMobilePrimary();
                            String firstName = user.getFirstName();
                            String middleName = user.getMiddleName();
                            String lastName = user.getLastName();
                            String mobileSecondary = user.getMobileSecondary();
                            String category = user.getCategory();
                            String position = user.getPosition();
                            String email = user.getEmail();
                            String dob = user.getDob();
                            String anniversary = user.getAnniversary();
                            String bloodgroup = user.getBloodgroup();
                            String gender = user.getGender();
                            String country = user.getCountry();
                            String state = user.getState();
                            String city = user.getCity();
                            String zipcode = user.getZipcode();
                            String residentialAddress = user.getResidentialAddress();
                            int adminFlag = user.getAdminFlag();

                            try {
                                sharedPref.setId(userId);
                                sharedPref.setMobilePrimary(mobilePrimary);
                                sharedPref.setFirstname(firstName);
                                sharedPref.setMiddlename(middleName);
                                sharedPref.setLastname(lastName);
                                sharedPref.setMobileSecondary(mobileSecondary);
                                sharedPref.setCategory(category);
                                sharedPref.setPosition(position);
                                sharedPref.setEmail(email);
                                sharedPref.setDOB(dob);
                                sharedPref.setAnniversary(anniversary);
                                sharedPref.setBloodGroup(bloodgroup);
                                sharedPref.setGender(gender);
                                sharedPref.setCountry(country);
                                sharedPref.setState(state);
                                sharedPref.setCity(city);
                                sharedPref.setZipcode(zipcode);
                                sharedPref.setResidentialAddress(residentialAddress);
                                sharedPref.setAdminFlag(adminFlag);
                                sharedPref.setLoginStatus(true);
                            } catch (Exception e) {
                                displayToast(e.getMessage().toString());
                            }

                            handleSubscription();
                            getAllUsersFromServer(userId, userMobile);
                            break;
                        case "failed":
                            hideProgressDialog();
                            displayToast(getString(R.string.login_failed));
                            break;
                        case "able_to_login":
                            hideProgressDialog();
                            actionOnAbleToLogin();
                            break;
                        case "able_to_signup":
                            hideProgressDialog();
                            startActivity(new Intent(Login.this, Signup.class).putExtra("user_mobile", userMobile));
                            break;
                        case "user_not_exist":
                            hideProgressDialog();
                            displayToast(getString(R.string.user_not_exist));
                            break;
                        case "error":
                            hideProgressDialog();
                            displayToast(getString(R.string.sql_error));
                            break;
                        default:
                            hideProgressDialog();
                            displayToast(getString(R.string.unknown_error));
                            break;
                    }
                } else {
                    hideProgressDialog();
                    displayToast(getString(R.string.response_failed));
                }
            }

            @Override
            public void onFailure(Call<Response.UserLogin> call, Throwable t) {
                hideProgressDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void actionOnAbleToLogin() {
        layoutPassword.setVisibility(View.VISIBLE);
        layoutPassword.getEditText().requestFocus();
        btnLogin.setText(getString(R.string.login));
    }

    private void handleSubscription() {
        Topic topic = new Topic(this);
        topic.subscribe(login);
        topic.subscribe(sharedPref.getCategory());
    }

    private void getAllUsersFromServer(String mUserId, String userMobile) {
        APIInterface apiService = postClient().create(APIInterface.class);
        Call<Response.AllUsers> call = apiService.performGetAllUsers(mUserId, userMobile);
        call.enqueue(new Callback<Response.AllUsers>() {
            @Override
            public void onResponse(Call<Response.AllUsers> call, retrofit2.Response<Response.AllUsers> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getResponse()) {
                        case "ok":
                            List<User> users = response.body().getUsers();

                            for (int i = 0; i < users.size(); i++) {
                                User user = users.get(i);
                                String userId = user.getUserId();
                                String mobilePrimary = user.getMobilePrimary();
                                String firstName = user.getFirstName();
                                String middleName = user.getMiddleName();
                                String lastName = user.getLastName();
                                String mobileSecondary = user.getMobileSecondary();
                                String email = user.getEmail();
                                String dob = user.getDob();
                                String anniversary = user.getAnniversary();
                                String bloodgroup = user.getBloodgroup();
                                String gender = user.getGender();
                                String country = user.getCountry();
                                String state = user.getState();
                                String city = user.getCity();
                                String zipcode = user.getZipcode();
                                String residentialAddress = user.getResidentialAddress();
                                String position = user.getPosition();
                                String category = user.getCategory();

                                UserModel mUser = new UserModel(userId, mobilePrimary, firstName, middleName, lastName, mobileSecondary, email, dob, anniversary, bloodgroup, gender, country, state, city, zipcode, residentialAddress, position, category);
                                dbHandler.addUser(mUser);
                            }
                            startActivity(new Intent(Login.this, Categories.class));
                            finish();
                            break;
                        default:
                            displayToast(getString(R.string.unknown_error));
                            break;
                    }
                } else {
                    displayToast(getString(R.string.response_failed));
                }
                hideProgressDialog();
            }

            public void onFailure(Call<Response.AllUsers> call, Throwable t) {
                hideProgressDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }
}