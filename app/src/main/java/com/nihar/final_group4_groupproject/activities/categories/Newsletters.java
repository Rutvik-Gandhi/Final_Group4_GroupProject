package com.nihar.final_group4_groupproject.activities.categories;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import static com.nihar.final_group4_groupproject.api.APIClient.postClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nihar.final_group4_groupproject.R;
import com.nihar.final_group4_groupproject.adapter.NewsletterAdapter;
import com.nihar.final_group4_groupproject.api.APIInterface;
import com.nihar.final_group4_groupproject.model.ListItemNewsletter;
import com.nihar.final_group4_groupproject.operations.Operations;
import com.nihar.final_group4_groupproject.prefs.SharedPref;
import com.nihar.final_group4_groupproject.response.Response.*;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Newsletters extends AppCompatActivity {
    public static final String TAG = "NewslettersTAG";
    public static final int REQUEST_CODE_PERMISSION_SETTING = 101;
    private Operations operations;
    private SharedPref sharedPref;
    private NewsletterAdapter newsletterAdapter;
    private final ArrayList<ListItemNewsletter> newsletterList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsletters);

        operations = new Operations(this);
        sharedPref = new SharedPref(this);

        recyclerView = findViewById(R.id.recyclerView);

        toolbar();
        getNewsletters();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        newsletterAdapter.notifyDataSetChanged();
    }

    private void toolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(
                getResources().getColor(R.color.colorWhite),
                PorterDuff.Mode.SRC_ATOP);
        toolbar.getOverflowIcon().setColorFilter(
                getResources().getColor(R.color.colorWhite),
                PorterDuff.Mode.SRC_ATOP);
    }

    private void getNewsletters() {
        operations.showProgressDialog();

        String userId = sharedPref.getId();
        String userMobile = sharedPref.getMobilePrimary();

        APIInterface apiService = postClient().create(APIInterface.class);
        Call<GetNewsletters> call = apiService.performGetNewsletters(userId, userMobile);

        call.enqueue(new Callback<GetNewsletters>() {
            @Override
            public void onResponse(Call<GetNewsletters> call, Response<GetNewsletters> response) {
                if (response.isSuccessful()) {
                    GetNewsletters mResponse = response.body();
                    switch (mResponse.getResponse()) {
                        case "ok":
                            recyclerView.setLayoutManager(new LinearLayoutManager(Newsletters.this));
                            newsletterList.clear();

                            List<Newsletter> newsletter = mResponse.getNewsletter();
                            for (int i = 0; i < newsletter.size(); i++) {
                                String id = newsletter.get(i).getId();
                                String fileName = newsletter.get(i).getFileName();
                                String path = newsletter.get(i).getPath();
                                String type = newsletter.get(i).getType();
                                String uploadTime = newsletter.get(i).getUploadTime();

                                newsletterList.add(new ListItemNewsletter(id, fileName, path, type, uploadTime));
                            }

                            newsletterAdapter = new NewsletterAdapter(Newsletters.this, newsletterList);
                            recyclerView.setAdapter(newsletterAdapter);
                            break;
                        case "failed":
                            operations.displayToast(getString(R.string.please_login_again));
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

            @Override
            public void onFailure(Call<GetNewsletters> call, Throwable t) {
                operations.hideProgressDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }
}