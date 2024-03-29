package com.nihar.final_group4_groupproject.activities.categories;

import java.util.ArrayList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.nihar.final_group4_groupproject.R;
import com.nihar.final_group4_groupproject.model.ListItemUsers;
import com.nihar.final_group4_groupproject.model.UserModel;
import com.nihar.final_group4_groupproject.sqlite.MyDBHandler;

public class UserDirectory extends AppCompatActivity {
    private static final String TAG = "UserDirectoryTAG";
    private ArrayList<ListItemUsers> userList;
    private UsersAdapter usersAdapter;
    private MyDBHandler dbHandler;
    private RecyclerView recyclerView;
    private EditText etSearch;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_directory);

        dbHandler = new MyDBHandler(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(
                getResources().getColor(R.color.colorWhite),
                PorterDuff.Mode.SRC_ATOP
        );

        recyclerView = findViewById(R.id.recyclerView);
        etSearch = findViewById(R.id.etSearch);
        userList = new ArrayList<ListItemUsers>();

        setUserList();
        search();
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

    private void setUserList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList.clear();

        ArrayList<UserModel> users = dbHandler.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            String userId = users.get(i).getUserId();
            String name = users.get(i).getFirstname() + " " + users.get(i).getLastname();
            String position = users.get(i).getPosition();

            userList.add(new ListItemUsers(userId, name, position));
        }

        usersAdapter = new UsersAdapter(this, userList);
        recyclerView.setAdapter(usersAdapter);
    }

    private void search() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void filter(String text) {
        ArrayList<ListItemUsers> temp = new ArrayList<ListItemUsers>();
        for (ListItemUsers data : userList) {
            if (data.getName().contains(text, true)) {
                temp.add(data);
            }
        }
        // update recyclerview
        usersAdapter.updateList(temp);
    }
}
