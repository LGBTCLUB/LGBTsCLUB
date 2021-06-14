package com.lgbt.LGBTsCLUB.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.Login;
import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.model.ForgotModel;
import com.lgbt.LGBTsCLUB.model.ForgotPasswordModel;
import com.lgbt.LGBTsCLUB.model.OccupationDataModel;
import com.lgbt.LGBTsCLUB.model.serachmodel.SpecialSearchModel;
import com.lgbt.LGBTsCLUB.network.UtilsMethod;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lgbt.LGBTsCLUB.activity.BuyMemberShipPlanActivity.emailid;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ImageView imageView,img_close;
    private TextView textView;
    private EditText edtEmail;
    private Button ResetPassword;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        imageView = findViewById(R.id.img_close);
        textView = findViewById(R.id.txt_back_to_login);
        edtEmail = findViewById(R.id.edt_email);
        ResetPassword = findViewById(R.id.btn_register);


        apiInterface = ApiClient.getInterface();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                if (email.isEmpty()) {
                    edtEmail.setError("Email Required");
                    edtEmail.requestFocus();
                    return;
                } else {
                    ForgotPasswordApi(email);
                }
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

    private void ForgotPasswordApi(String emailid) {

        apiInterface.forgetPass(emailid).enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                ForgotPasswordModel forgotPasswordModel = response.body();
                Log.d("TAG", "onResponse: " + forgotPasswordModel.getResponse());

                if (forgotPasswordModel != null) {
                    if (forgotPasswordModel.getResponse()) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid EmailId", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                Log.d("TAG", "onResponse: Fail");
            }
        });
    }
}
