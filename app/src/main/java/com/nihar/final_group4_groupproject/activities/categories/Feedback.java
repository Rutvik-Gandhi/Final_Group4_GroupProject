package com.nihar.final_group4_groupproject.activities.categories;

import static com.nihar.final_group4_groupproject.api.APIClient.postClient;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nihar.final_group4_groupproject.response.Response.*;

import com.nihar.final_group4_groupproject.R;
import com.nihar.final_group4_groupproject.api.APIInterface;
import com.nihar.final_group4_groupproject.operations.Operations;
import com.nihar.final_group4_groupproject.prefs.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feedback extends AppCompatActivity {

    private static final String TAG = "FeedbackTAG";

    private Operations operations;
    private SharedPref sharePref;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        toolbar = findViewById(R.id.toolbar);
        operations = new Operations(this);
        sharePref = new SharedPref(this);

        toolbar();
        setMinimumRating();
        handleButtonClicks();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.getNavigationIcon().setColorFilter(
                getResources().getColor(R.color.colorWhite),
                PorterDuff.Mode.SRC_ATOP
        );
        toolbar.getOverflowIcon().setColorFilter(
                getResources().getColor(R.color.colorWhite),
                PorterDuff.Mode.SRC_ATOP
        );
    }

    private void handleButtonClicks() {
        findViewById(R.id.btnSubmit).setOnClickListener(v -> {
            RatingBar ratingBar = findViewById(R.id.ratingBar);
            if (ratingBar.getRating() < 1.0f) {
                operations.displayToast(getString(R.string.rating_is_required));
                return;
            }
            if (operations.checkNullOrEmpty(findViewById(R.id.layoutFeedback))) {
                return;
            }
            // send if not empty
            String rating = String.valueOf(ratingBar.getRating());
            String feedback = operations.getValue(findViewById(R.id.layoutFeedback));
            sendFeedback(rating, feedback);
        });
    }

    private void setMinimumRating() {
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (rating < 1.0f) {
                ratingBar1.setRating(1.0f);
            }
        });
    }

    private void sendFeedback(String rating, String feedback) {
        operations.showProgressDialog();
        String userId = sharePref.getId();
        String brand = Build.BRAND;
        String model = Build.MODEL;
        String device = Build.DEVICE;
        String version = Build.VERSION.RELEASE;

        Log.d(TAG, brand);
        Log.d(TAG, model);
        Log.d(TAG, device);
        Log.d(TAG, version);

        APIInterface apiService = postClient().create(APIInterface.class);
        Call<GeneralResponse> call = apiService.performSendFeedback(userId, rating, feedback, brand, model, device, version);

        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                operations.hideProgressDialog();
                Log.d("onFailure", t.toString());
            }

            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.isSuccessful()) {
                    GeneralResponse mResponse = response.body();
                    String responseMessage = mResponse.getMessage();
                    switch (mResponse.getResponse()) {
                        case "ok":
                            operations.displayToast(responseMessage);
                            finish();
                            break;
                        case "error":
                            operations.displayToast(responseMessage);
                            break;
                        default:
                            operations.displayToast(getString(R.string.unknown_error));
                            break;
                    }
                } else {
                    operations.displayToast(getString(R.string.response_failed));
                }
                operations.hideProgressDialog();
            }
        });
    }
}