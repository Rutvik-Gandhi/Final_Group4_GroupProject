package com.nihar.final_group4_groupproject.activities.categories;

import static com.nihar.final_group4_groupproject.api.APIClient.postClient;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.nihar.final_group4_groupproject.R;
import com.nihar.final_group4_groupproject.api.APIInterface;
import com.nihar.final_group4_groupproject.operations.Operations;
import com.nihar.final_group4_groupproject.prefs.SharedPref;
import com.nihar.final_group4_groupproject.response.Response.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Announcement extends AppCompatActivity {
    private static final String TAG = "AnnouncementTAG";
    private Operations operations;
    private SharedPref sharedPref;
    Toolbar toolbar;
    TextView tvTitle, tvMessage, tvTemp;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement);

        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tvTitle);
        tvMessage = findViewById(R.id.tvMessage);
        tvTemp = findViewById(R.id.tvTemp);
        cardView = findViewById(R.id.cardView);

        operations = new Operations(this);
        sharedPref = new SharedPref(this);

        toolbar();
        handleButtonClicks();
        getAnnouncement();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setColorFilter(
                    getResources().getColor(R.color.colorWhite),
                    PorterDuff.Mode.SRC_ATOP
            );
        }
        if (toolbar.getOverflowIcon() != null) {
            toolbar.getOverflowIcon().setColorFilter(
                    getResources().getColor(R.color.colorWhite),
                    PorterDuff.Mode.SRC_ATOP
            );
        }
    }

    private void handleButtonClicks() {
    /*btnMarkRead.setOnClickListener {
        finish()
    }*/
    }

    private void getAnnouncement() {
        operations.showProgressDialog();

        String topic = sharedPref.getCategory();


        APIInterface apiService = postClient().create(APIInterface.class);
        Call<GetAnnouncement> call = apiService.performGetAnnouncement(topic);

        call.enqueue(new Callback<GetAnnouncement>() {
            @Override
            public void onResponse(@NonNull Call<GetAnnouncement> call, @NonNull Response<GetAnnouncement> response) {
                if (response.isSuccessful()) {
                    GetAnnouncement mResponse = response.body();
                    if (mResponse != null) {
                        switch (mResponse.getResponse()) {
                            case "ok":
                                String title = mResponse.getTitle();
                                String message = mResponse.getMessage();

                                tvTitle.setText(title);
                                tvMessage.setText(message);
                                showAnnouncement(true);
                                break;
                            case "null":
                                showAnnouncement(false);
                                break;
                            default:
                                operations.displayToast(getString(R.string.unknown_error));
                                showAnnouncement(false);
                                break;
                        }
                    } else {
                        operations.displayToast(getString(R.string.unknown_error));
                        showAnnouncement(false);
                    }
                } else {
                    operations.displayToast(getString(R.string.response_failed));
                    showAnnouncement(false);
                }
                operations.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<GetAnnouncement> call, Throwable t) {
                operations.hideProgressDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void showAnnouncement(boolean flag) {
        if (flag) {
            tvTemp.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
        } else {
            tvTemp.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        }
    }

}
