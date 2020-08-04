package com.project.hotelmanagementproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.project.hotelmanagementproject.R;
import com.project.hotelmanagementproject.controller.adapters.AdminSearchUserAdapter;
import com.project.hotelmanagementproject.model.DbMgr;
import com.project.hotelmanagementproject.model.Session;
import com.project.hotelmanagementproject.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.project.hotelmanagementproject.utilities.ConstantUtils.APP_TAG;

public class AdminSearchUserActivity extends AppCompatActivity {
    LinearLayout  adminSearchUser,adminSearchUserResult;
    EditText etAdminSuLastName;
    ArrayList<User> userList;
    ListView lvAdSrUserList;
    AdminSearchUserAdapter adminSearchUserAdpater;
    String userName;
    String ADM_EDIT_USER= "adm_edit_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_users);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
    }

    public void initView() {
        etAdminSuLastName = findViewById(R.id.etAdminSuLastName);
        adminSearchUser = findViewById(R.id.adminSearchUser);
        adminSearchUser.setVisibility(View.VISIBLE);
        adminSearchUserResult = findViewById(R.id.adminSearchUserResult);
        adminSearchUserResult.setVisibility(View.GONE);
        userList = new ArrayList<>();
        lvAdSrUserList = findViewById(R.id.lvAdSrUserList);
        Button btnAdminUserSearch = findViewById(R.id.btnAdminUserSearch);
        btnAdminUserSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminSearchUser.setVisibility(View.GONE);
                adminSearchUser();
            }
        });
    }

    public void adminSearchUser() {

        DbMgr UserDbMgr = DbMgr.getInstance(getApplicationContext());
        String AdminSuLastName = etAdminSuLastName.getText().toString();


        if (null == AdminSuLastName || AdminSuLastName.isEmpty() || AdminSuLastName.equalsIgnoreCase("")) {
            etAdminSuLastName.setError("enter valid input");
            Toast.makeText(getApplicationContext(), "invalid last name", Toast.LENGTH_LONG).show();
        } else {
            adminSearchUser.setVisibility(View.GONE);
            adminSearchUserResult.setVisibility(View.VISIBLE);
            userList = UserDbMgr.getAdminSearchUser(AdminSuLastName);
            adminSearchUserAdpater = new AdminSearchUserAdapter(this, userList);
            lvAdSrUserList.setAdapter(adminSearchUserAdpater);
            adminSearchUserAdpater.notifyDataSetChanged();
        }



        lvAdSrUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent userNameIntent = new Intent(AdminSearchUserActivity.this, AdminViewUserDetails.class);
                User item = (User) adapterView.getItemAtPosition(i);
                userNameIntent.putExtra(ADM_EDIT_USER,item.getUserName());

                startActivity(userNameIntent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        Intent i = new Intent(AdminSearchUserActivity.this, LoginActivity.class);
        new Session(getApplicationContext()).setLoginStatus(false);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        if (adminSearchUserResult.getVisibility() == View.VISIBLE) {
            adminSearchUser.setVisibility(View.VISIBLE);
            adminSearchUserResult.setVisibility(View.GONE);
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}