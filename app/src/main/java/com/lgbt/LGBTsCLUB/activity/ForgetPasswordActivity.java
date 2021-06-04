package com.lgbt.LGBTsCLUB.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.model.ForgotModel;
import com.lgbt.LGBTsCLUB.model.ForgotPasswordModel;
import com.lgbt.LGBTsCLUB.model.serachmodel.SpecialSearchModel;
import com.lgbt.LGBTsCLUB.network.UtilsMethod;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lgbt.LGBTsCLUB.activity.BuyMemberShipPlanActivity.emailid;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        apiInterface = ApiClient.getInterface();
        //ForgotPasswordApi(emailid);
        imageView = findViewById(R.id.img_close);
        textView = findViewById(R.id.txt_back_to_login);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

   }