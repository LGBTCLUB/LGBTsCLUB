package com.lgbt.LGBTsCLUB.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.activity.EditProfileActivity;
import com.lgbt.LGBTsCLUB.activity.LoginActivity;
import com.lgbt.LGBTsCLUB.activity.ProfileActivity;
import com.lgbt.LGBTsCLUB.model.CityDataModel;
import com.lgbt.LGBTsCLUB.model.EducationDataModel;
import com.lgbt.LGBTsCLUB.model.MemberProfileModel;
import com.lgbt.LGBTsCLUB.model.MotherToungueDataModel;
import com.lgbt.LGBTsCLUB.model.OccupationDataModel;
import com.lgbt.LGBTsCLUB.model.ProfileImageModel;
import com.lgbt.LGBTsCLUB.model.ProfileModel;
import com.lgbt.LGBTsCLUB.model.StateDataModel;
import com.lgbt.LGBTsCLUB.model.UpdateProfileModel;
import com.lgbt.LGBTsCLUB.model.UserStatusModel;
import com.lgbt.LGBTsCLUB.model.serachmodel.SpecialSearchModel;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;
import com.lgbt.LGBTsCLUB.network.networking.CountryDataModel;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import id.zelory.compressor.Compressor;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER1;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.LOGIN_ID;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.MATRI_ID;

public class SaveDetailsFragment extends Fragment {

    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    ImageView iv_userprofile, iv_blur;
    RelativeLayout rvuserImage;
    String clickStatus, country, imagePath;
    ProgressBar progress_bar;
    int RESULT_LOAD_IMAGE;

    public SaveDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_details, container, false);
        iv_blur = view.findViewById(R.id.iv_blur);

        iv_userprofile = view.findViewById(R.id.iv_userprofile);
        progress_bar = view.findViewById(R.id.progress_bar);
        rvuserImage = view.findViewById(R.id.rvuserImage);

        progress_bar.setVisibility(VISIBLE);

        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
        frameLayout=(FrameLayout)view.findViewById(R.id.frame);

        fragment = new AccountSaveDetailsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 2) {

            fragmentManager.popBackStack();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.frame, fragment)
                .addToBackStack(null)
                .commit();

        rvuserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new AccountSaveDetailsFragment();
                        break;
                    case 1:
                        fragment = new PersonalFragment();
                        break;
                    case 2:
                        fragment = new ProfessionalFragment();
                        break;
                }
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 2) {

                    fragmentManager.popBackStack();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.frame, fragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ProfileActivity.profileDetailApiCall = "Yes";

    }

}