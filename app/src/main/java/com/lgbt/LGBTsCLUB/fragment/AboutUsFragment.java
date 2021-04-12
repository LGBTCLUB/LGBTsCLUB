package com.lgbt.LGBTsCLUB.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.activity.AboutUsActivity;
import com.lgbt.LGBTsCLUB.model.AboutUsModel;
import com.lgbt.LGBTsCLUB.network.UtilsMethod;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

public class AboutUsFragment extends Fragment {
    TextView tv_contant;
    private ApiInterface apiInterface;
    private UtilsMethod progress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        tv_contant = view.findViewById(R.id.tv_aboutUs);
        apiInterface = ApiClient.getInterface();
      //  progress = new UtilsMethod(this);

//        iv_back.setOnClickListener(v -> finish());
        aboutUsApi();
        // Inflate the layout for this fragment
        return view;


    }

    private void aboutUsApi() {
        apiInterface.aboutUs().enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                if (response.isSuccessful()) {
                    AboutUsModel aboutUsModel = response.body();
                    if (aboutUsModel != null) {
                       // progress.cancleDialog();
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
//                Toast.makeText(AboutUsActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
//                progress.cancleDialog();
            }
        });
    }
}