package com.lgbt.LGBTsCLUB.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lgbt.LGBTsCLUB.R;

public class SecondActivity extends AppCompatActivity {

    Button btn_create_account,btn_login_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btn_create_account=findViewById(R.id.btn_create_account);
        btn_login_account=findViewById(R.id.btn_login_account);
        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SecondActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btn_login_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SecondActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });




    }
}
