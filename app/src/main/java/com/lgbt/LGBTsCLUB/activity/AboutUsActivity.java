package com.lgbt.LGBTsCLUB.activity;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.model.AboutUsModel;
import com.lgbt.LGBTsCLUB.network.UtilsMethod;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends AppCompatActivity {

    ImageView iv_back;
    TextView tv_contant;
    WebView wabView;
    private ApiInterface apiInterface;
    private UtilsMethod progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        iv_back = findViewById(R.id.iv_back);
        tv_contant = findViewById(R.id.tv_aboutUs);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);

        aboutUsApi();
    }

    private void aboutUsApi() {

        apiInterface.aboutUs().enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                if (response.isSuccessful()) {
                    AboutUsModel aboutUsModel = response.body();
                    if (aboutUsModel != null) {
                        progress.cancleDialog();
                        boolean respons = aboutUsModel.isResponse();
                        if (respons) {
                            AboutUsModel.AboutUsData loginData = aboutUsModel.getLoginData();
                            tv_contant.setText(Html.fromHtml(Html.fromHtml(loginData.getContent()).toString()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {
                Toast.makeText(AboutUsActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
            }
        });
    }
}
