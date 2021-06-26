package com.lgbt.LGBTsCLUB.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.activity.EditProfileActivity;
import com.lgbt.LGBTsCLUB.activity.ProfileActivity;
import com.lgbt.LGBTsCLUB.model.MemberProfileModel;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.MATRI_ID;

public class ProfessionalFragment extends Fragment {

    private ApiInterface apiInterface;
    ProgressBar progress_bar;
    Button bt_editprofile;
    TextView tv_education, tv_perofessions, tv_salary, tv_bio, tv_upgrade;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_professional, container, false);

        progress_bar = view.findViewById(R.id.progress_bar);

        tv_education = view.findViewById(R.id.tv_education);
        tv_perofessions = view.findViewById(R.id.tv_perofessions);
        tv_salary = view.findViewById(R.id.tv_salary);
        bt_editprofile = view.findViewById(R.id.bt_editprofile);
        bt_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new SaveDetailsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
            }
        });

        apiInterface = ApiClient.getInterface();
        profileDetailApi(SharedPrefsManager.getInstance().getString(MATRI_ID));
        return view;
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

                            tv_education.setText(profileData.get(0).getEducation());
                            tv_perofessions.setText(profileData.get(0).getOccupation());
                            tv_salary.setText(profileData.get(0).getAnnualincome());

//                            try {
//                                RequestOptions options = new RequestOptions()
//                                        .centerCrop()
//                                        .placeholder(R.drawable.logo_final)
//                                        .error(R.drawable.logo_final)
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                        .priority(Priority.HIGH).dontAnimate()
//                                        .dontTransform();
//
//                                Glide.with(getContext())
//                                        .load(IMAGE_LOAD_USER1 + profileData.get(0).getPhoto1())
//                                        .apply(options)
//                                        .into(iv_image1);
//                                Glide.with(getContext())
//                                        .load(IMAGE_LOAD_USER1 + profileData.get(0).getPhoto1())
//                                        .apply(bitmapTransform(new BlurTransformation(25)))
//                                        .into(bg_blur_iv);
//
//                                Glide.with(getContext())
//                                        .load(IMAGE_LOAD_USER2 + profileData.get(0).getPhoto2())
//                                        .apply(options)
//                                        .into(iv_image2);
//
//                                Glide.with(getContext())
//                                        .load(IMAGE_LOAD_USER3 + profileData.get(0).getPhoto3())
//                                        .apply(options)
//                                        .into(iv_image3);
//
//                                Glide.with(getContext())
//                                        .load(IMAGE_LOAD_USER4 + profileData.get(0).getPhoto4())
//                                        .apply(options)
//                                        .into(iv_image4);
//
//                                Glide.with(getContext())
//                                        .load(IMAGE_LOAD_USER5 + profileData.get(0).getPhoto5())
//                                        .apply(options)
//                                        .into(iv_image5);
//
//                                Glide.with(getContext())
//                                        .load(IMAGE_LOAD_USER6 + profileData.get(0).getPhoto6())
//                                        .apply(options)
//                                        .into(iv_image6);
//
//                                Glide.with(getContext())
//                                        .load(IMAGE_LOAD_USER7 + profileData.get(0).getPhoto7())
//                                        .apply(options)
//                                        .into(iv_image7);
//                            } catch (Exception e) {
//
//                            }
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