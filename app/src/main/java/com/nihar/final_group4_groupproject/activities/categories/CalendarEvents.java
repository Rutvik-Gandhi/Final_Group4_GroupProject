package com.nihar.final_group4_groupproject.activities.categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nihar.final_group4_groupproject.R;
import com.nihar.final_group4_groupproject.adapter.CalendarAdapter;
import com.nihar.final_group4_groupproject.model.ListItemCalendar;
import com.nihar.final_group4_groupproject.sqlite.MyDBHandler;

import java.util.ArrayList;

public class CalendarEvents extends AppCompatActivity {

    private static final String TAG = "CalendarEventsTAG";
    private final ArrayList<ListItemCalendar> userListToday = new ArrayList<>();
    private final ArrayList<ListItemCalendar> userListTomorrow = new ArrayList<>();
    private CalendarAdapter calendarAdapter;
    private MyDBHandler dbHandler;
    Toolbar toolbar;
    RecyclerView recyclerView, recyclerViewTomorrow;
    TextView tvNoEventsToday, tvNoEventsTomorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_events);

        dbHandler = new MyDBHandler(this);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView = findViewById(R.id.recyclerViewTomorrow);
        tvNoEventsToday = findViewById(R.id.tvNoEventsToday);
        tvNoEventsTomorrow = findViewById(R.id.tvNoEventsTomorrow);

        toolbar();
        setUserListToday();
        setUserListTomorrow();
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

    private void setUserListToday() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userListToday.clear();

        ArrayList<ListItemCalendar> users = new ArrayList<>();
        users.addAll(dbHandler.checkBirthdayToday());
        users.addAll(dbHandler.checkAnniversaryToday());

        for (int i = 0; i < users.size(); i++) {
            String userId = users.get(i).getUserId();
            String name = users.get(i).getName();
            String date = users.get(i).getDate();
            String type = users.get(i).getType();

            userListToday.add(new ListItemCalendar(userId, name, date, type));
        }

        if (userListToday.isEmpty()) {
            tvNoEventsToday.setVisibility(View.VISIBLE);
        } else {
            tvNoEventsToday.setVisibility(View.GONE);
        }

        calendarAdapter = new CalendarAdapter(this, userListToday);
        recyclerView.setAdapter(calendarAdapter);
    }

    private void setUserListTomorrow() {
        recyclerViewTomorrow.setLayoutManager(new LinearLayoutManager(this));
        userListTomorrow.clear();

        ArrayList<ListItemCalendar> users = new ArrayList<>();
        users.addAll(dbHandler.checkBirthdayTomorrow());
        users.addAll(dbHandler.checkAnniversaryTomorrow());

        for (int i = 0; i < users.size(); i++) {
            String userId = users.get(i).getUserId();
            String name = users.get(i).getName();
            String date = users.get(i).getDate();
            String type = users.get(i).getType();

            userListTomorrow.add(new ListItemCalendar(userId, name, date, type));
        }

        if (userListTomorrow.isEmpty()) {
            tvNoEventsTomorrow.setVisibility(View.VISIBLE);
        } else {
            tvNoEventsTomorrow.setVisibility(View.GONE);
        }

        calendarAdapter = new CalendarAdapter(this, userListTomorrow);
        recyclerViewTomorrow.setAdapter(calendarAdapter);
    }

}