package com.nihar.final_group4_groupproject.activities.categories;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;

import com.nihar.final_group4_groupproject.R;

public class About extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        toolbar = findViewById(R.id.toolbar);

        toolbar();
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(
                getResources().getColor(R.color.colorWhite),
                PorterDuff.Mode.SRC_ATOP
        );
        toolbar.getOverflowIcon().setColorFilter(
                getResources().getColor(R.color.colorWhite),
                PorterDuff.Mode.SRC_ATOP
        );
    }

}
