package com.lgbt.LGBTsCLUB.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.activity.EditProfileActivity;
import com.lgbt.LGBTsCLUB.model.MemberProfileModel;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER1;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER2;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER3;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER4;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER5;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER6;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER7;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.MATRI_ID;

public class AccountFragment extends Fragment {
    TextView tv_namee, li_viewPlan, tv_account, tv_personal, tv_professional, tv_name, tv_gender, tv_hight, tv_dob, tv_age,
            tv_material, tv_motherTongue;
    private ApiInterface apiInterface;

    Button bt_editprofile;
    ProgressBar progress_bar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        progress_bar = view.findViewById(R.id.progress_bar);
        tv_name = view.findViewById(R.id.tv_name);
        tv_namee = view.findViewById(R.id.tv_namee);
        tv_gender = view.findViewById(R.id.tv_gender);
        tv_hight = view.findViewById(R.id.tv_hight);
        tv_dob = view.findViewById(R.id.tv_dob);
        tv_age = view.findViewById(R.id.tv_age);
        tv_material = view.findViewById(R.id.tv_material);
        tv_motherTongue = view.findViewById(R.id.tv_motherTongue);
        bt_editprofile = view.findViewById(R.id.bt_editprofile);
        bt_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                pushFragment(new SaveDetailsFragment());
            }
        });

        apiInterface = ApiClient.getInterface();
        profileDetailApi(SharedPrefsManager.getInstance().getString(MATRI_ID));
        return view;
    }

    private void pushFragment(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 2) {

            fragmentManager.popBackStack();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void profileDetailApi(String userId) {
        progress_bar.setVisibility(VISIBLE);
        apiInterface.member_profile_details(userId, userId).enqueue(new Callback<MemberProfileModel>() {
            @Override
            public void onResponse(Call<MemberProfileModel> call, Response<MemberProfileModel> response) {
                if (response.isSuccessful()) {
                    MemberProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        progress_bar.setVisibility(GONE);
                        boolean respons = profileModel.isResponse();
                        if (respons) {
                            List<MemberProfileModel.MemberProfileData> profileData = profileModel.getMemberProfileDataList();
                            tv_name.setText(profileData.get(0).getName());
                            tv_gender.setText(profileData.get(0).getGender());
                            tv_hight.setText(profileData.get(0).getUserHeight());
                            tv_dob.setText(profileData.get(0).getDOB());
                            tv_age.setText(profileData.get(0).getAge());
                            tv_material.setText(profileData.get(0).getMaritalstatus());
                            tv_motherTongue.setText(profileData.get(0).getLanguage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberProfileModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);
            }
        });
    }
}