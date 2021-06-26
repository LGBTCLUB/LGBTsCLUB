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
import com.lgbt.LGBTsCLUB.model.MemberProfileModel;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.MATRI_ID;


public class PersonalFragment extends Fragment {
    private ApiInterface apiInterface;
    ProgressBar progress_bar;
    TextView tv_interest, tv_country, tv_city, tv_nationality, tv_contact,
            tv_email;
    Button bt_editprofile;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        tv_interest = view.findViewById(R.id.tv_interest);
        tv_country = view.findViewById(R.id.tv_country);
        tv_city = view.findViewById(R.id.tv_city);
        tv_nationality = view.findViewById(R.id.tv_nationality);
        tv_contact = view.findViewById(R.id.tv_contact);
        tv_email = view.findViewById(R.id.tv_email);
        progress_bar = view.findViewById(R.id.progress_bar);
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
                            tv_interest.setText(profileData.get(0).getHobbies());
                            tv_country.setText(profileData.get(0).getCountry());
                            if (profileData.get(0).getCity_name().equals("") || profileData.get(0).getCity_name() == null) {
                                tv_city.setText("Amloh");
                            } else {
                                tv_city.setText(profileData.get(0).getCity_name());
                            }
                            tv_nationality.setText(profileData.get(0).getNationality());
                            tv_contact.setText(profileData.get(0).getMobile());
                            tv_email.setText(profileData.get(0).getConfirmEmail());
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