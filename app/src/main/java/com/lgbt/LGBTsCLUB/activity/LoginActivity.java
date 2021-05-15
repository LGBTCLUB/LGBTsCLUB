package com.lgbt.LGBTsCLUB.activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.lgbt.LGBTsCLUB.MainActivity;
import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.model.LoginModel;
import com.lgbt.LGBTsCLUB.model.usermodel.VeryfyEmailRegisterModel;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lgbt.LGBTsCLUB.network.networking.Constant.CONFIRM_EMAIL;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_URL;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.KEY_LOGIN_STATUS;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.KEY_USER_NAME;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.LOGIN_ID;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.MATRI_ID;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.MOBILE;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.SHOW_GENDER;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.STATUS;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.USER_GENDER;

public class LoginActivity extends AppCompatActivity {
    TextView tv_forget_password, tv_register,txt_signup;
    Button btn_login;
    PopupWindow popupWindow;
    RelativeLayout relativeLayout;
    EditText et_emailId,et_password;
    private ProgressBar progress;
    String newToken;
    String otp;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        relativeLayout = findViewById(R.id.rel_layout);
        tv_forget_password = findViewById(R.id.txt_forgotpasssword);
        progress = findViewById(R.id.progress_bar);
        
//        tv_forget_password.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////              popUp();
//            }
//        });

        tv_register = findViewById(R.id.txt_sign_in);
        btn_login = findViewById(R.id.btn_login);

        et_emailId = findViewById(R.id.edt_email);
        et_password = findViewById(R.id.edt_password);

        txt_signup=findViewById(R.id.text_signup);
        apiInterface = ApiClient.getInterface();



        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        tv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });


        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });



        et_emailId.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 1) {
                    btn_login.setText("SIGN IN");
                }

            }
        });

        et_password.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 1) {
                    btn_login.setText("SIGN IN");
                }

            }
        });


        btn_login.setOnClickListener(view -> {

            if (btn_login.getText().toString().equalsIgnoreCase("SIGN IN")) {
                if (validationSuccess()) {
                    LoginApi(et_emailId.getText().toString(), et_password.getText().toString(), newToken);
                }
            } else {
//                if (validationSuccess2()) {
//                    verifyEmailMobile("a@gmail.com", et_mobile.getText().toString());
//                }
            }
        });

    }

    private void popUp() {

        LayoutInflater layoutInflater = (LayoutInflater) LoginActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup,null);

//        closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

        //instantiate popup window
        popupWindow = new PopupWindow(customView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        //display the popup window
       // popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        //close the popup window on button click
//        closePopupBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
        }
  //  }


    private void LoginApi(String emailId, String passWord, String newToken) {
        Log.i("rhl....", emailId + "   " + passWord + "    " + newToken);
        apiInterface.userLogin(emailId, passWord, newToken).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();
                    if (loginModel != null) {
                        boolean respons = loginModel.isResponse();
                        //  String message = loginModel.getMessage();
                        if (respons) {
                            List<LoginModel.LoginData> loginDataList = loginModel.getLoginDataList();
                            SharedPrefsManager.getInstance().setString(LOGIN_ID, loginDataList.get(0).getLoginId());
                            SharedPrefsManager.getInstance().setString(MATRI_ID, loginDataList.get(0).getMatriId());
                            SharedPrefsManager.getInstance().setString(USER_GENDER, loginDataList.get(0).getGenderUser());
                            SharedPrefsManager.getInstance().setString(KEY_USER_NAME, loginDataList.get(0).getNameUser());
                            SharedPrefsManager.getInstance().setString(CONFIRM_EMAIL, loginDataList.get(0).getConfirmEmail());
                            SharedPrefsManager.getInstance().setString(MOBILE, loginDataList.get(0).getMobileUser());
                            SharedPrefsManager.getInstance().setString(STATUS, loginDataList.get(0).getStatus());
                            SharedPrefsManager.getInstance().setString(SHOW_GENDER, loginDataList.get(0).getShow_gender());
                            SharedPrefsManager.getInstance().setBoolean(KEY_LOGIN_STATUS, true);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(LoginActivity.this, "Email Id and password does not match", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void verifyEmailMobile(String userEmail, String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://myteachers.live/app/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //  RetrofitService api = retrofit.create(RetrofitService.class);
        ApiInterface api = retrofit.create(ApiInterface.class);
        //  ApiInterface api   = ApiClient.getInterface();
        api.verifyEmail(userEmail, phone).enqueue(new Callback<VeryfyEmailRegisterModel>() {
            @Override
            public void onResponse(Call<VeryfyEmailRegisterModel> call, Response<VeryfyEmailRegisterModel> response) {
                if (response.isSuccessful()) {
                    VeryfyEmailRegisterModel registerModel = response.body();
                    if (registerModel != null) {
                        String respons = registerModel.getResponse();
                        String message = registerModel.getMessage();
                        otp = registerModel.getOtp();
                        Log.i("rhl..otp", otp);
                        if (respons.equals("true")) {
                            dialogeOTP();
                        } else {
                            Toast toast = Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VeryfyEmailRegisterModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "something is wrong", Toast.LENGTH_LONG).show();

            }
        });

    }


    private void dialogeOTP() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialogbox2);


        Button ok = dialog.findViewById(R.id.ok);
        final EditText text_dialog = dialog.findViewById(R.id.text_dialog);
        ok.setOnClickListener(v -> {
            if (text_dialog.getText().toString().equals(otp)) {
                progress.setVisibility(View.VISIBLE);
//                loginwidthmobile(et_mobile.getText().toString());
            } else {
                Toast toast = Toast.makeText(LoginActivity.this, "Enter Valid Otp", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    private void loginwidthmobile(String mobile) {

        apiInterface.loginwidthmobile(mobile).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();
                    if (loginModel != null) {

                        boolean respons = loginModel.isResponse();
                        //  String message = loginModel.getMessage();
                        if (respons) {
                            List<LoginModel.LoginData> loginDataList = loginModel.getLoginDataList();
                            //  Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                            SharedPrefsManager.getInstance().setString(LOGIN_ID, loginDataList.get(0).getLoginId());
                            SharedPrefsManager.getInstance().setString(MATRI_ID, loginDataList.get(0).getMatriId());
                            SharedPrefsManager.getInstance().setString(USER_GENDER, loginDataList.get(0).getGenderUser());
                            SharedPrefsManager.getInstance().setString(CONFIRM_EMAIL, loginDataList.get(0).getConfirmEmail());
                            SharedPrefsManager.getInstance().setString(MOBILE, loginDataList.get(0).getMobileUser());

                            SharedPrefsManager.getInstance().setString(STATUS, loginDataList.get(0).getStatus());
                            SharedPrefsManager.getInstance().setBoolean(KEY_LOGIN_STATUS, true);
                            SharedPrefsManager.getInstance().setString(SHOW_GENDER, loginDataList.get(0).getShow_gender());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(LoginActivity.this, "Please enter correct mobile no.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


    private Boolean validationSuccess() {

        if (et_emailId.getText().toString().length() == 0) {
            et_emailId.setError("Please enter Email Id!");
            et_emailId.requestFocus();
            return false;
        }

        if (et_password.getText().toString().length() == 0) {
            et_password.setError("Please enter password!");
            et_password.requestFocus();
            return false;
        }

        return true;
    }

//    private Boolean validationSuccess2() {
//
//        if (et_mobile.getText().toString().length() < 10) {
//            et_mobile.setError("Please enter phone number!");
//            et_mobile.requestFocus();
//            return false;
//        }
//        return true;
//    }


}
