package com.project.hotelmanagementproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.hotelmanagementproject.R;

public class ViewProfileActivity extends AppCompatActivity {
    private Button btnAdminVpModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        init();
    }

    public void init(){
        btnAdminVpModify = findViewById(R.id.btnAdminVpModify);
        btnAdminVpModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToastMessage("Feature Under Construction");
                startActivity(new Intent(ViewProfileActivity.this, UpdateProfileActivity.class));
            }
        });
    }

    public void showToastMessage(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        View view = toast.getView();
//       view.setBackgroundResource(R.drawable.toast_frame);
//        view.setBackgroundColor(Color.BLACK);
//        TextView text = (TextView) view.findViewById(android.R.id.message);
        toast.show();
    }
}