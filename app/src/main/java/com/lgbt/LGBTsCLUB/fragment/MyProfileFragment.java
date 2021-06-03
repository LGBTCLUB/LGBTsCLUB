package com.lgbt.LGBTsCLUB.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.activity.AboutUsActivity;
import com.lgbt.LGBTsCLUB.activity.ChangePasswordActivity;
import com.lgbt.LGBTsCLUB.activity.ContactUsActivity;
import com.lgbt.LGBTsCLUB.activity.DeleteAccountActivity;
import com.lgbt.LGBTsCLUB.activity.LoginActivity;
import com.lgbt.LGBTsCLUB.activity.MemberShipPlanActivity;
import com.lgbt.LGBTsCLUB.activity.MyProfileVisitorActivity;
import com.lgbt.LGBTsCLUB.activity.ProfileActivity;
import com.lgbt.LGBTsCLUB.activity.TermsConditionActivity;
import com.lgbt.LGBTsCLUB.model.AboutUsModel;
import com.lgbt.LGBTsCLUB.model.ProfileModel;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import java.util.Objects;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER1;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.KEY_LOGIN_STATUS;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.LOGIN_ID;

public class MyProfileFragment extends Fragment {
    GoogleSignInClient mGoogleSignInClient;
    ImageView iv_back, iv_userprofile,bg_blur_iv;
    Button bt_profile,bt_contactUs;
    ProgressBar progress_bar;
    TextView tv_contactUs, tv_aboutUs, tv_termsCondition, tv_Membership, tv_changePassword, tv_logout,
            tv_username, tv_professional, tv_deleteAccount, profile_visitor, visit_count;
    private ApiInterface apiInterface;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Api.getInstance().call(ApiClient.getInterface().AllPostApi(SharedPrefsManager.getInstance().getString(WORD_ID),"0"), this, 4);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        iv_back = view.findViewById(R.id.iv_back);
        iv_userprofile = view.findViewById(R.id.iv_userprofile);
        tv_username = view.findViewById(R.id.tv_username);
        tv_professional = view.findViewById(R.id.tv_professional);
        bt_profile = view.findViewById(R.id.bt_profile);
        bt_contactUs=view.findViewById(R.id.bt_contactUs);
        bg_blur_iv=view.findViewById(R.id.bg_blur_iv);
        tv_aboutUs = view.findViewById(R.id.tvv_about);
        tv_termsCondition = view.findViewById(R.id.tv_termsCondition);
        tv_changePassword = view.findViewById(R.id.tv_changePassword);
        tv_logout = view.findViewById(R.id.tv_logout);
        tv_Membership = view.findViewById(R.id.tv_Membership);
        progress_bar = view.findViewById(R.id.progress_bar);
        tv_deleteAccount = view.findViewById(R.id.tv_deleteAccount);
        profile_visitor = view.findViewById(R.id.profile_visitor);
        visit_count = view.findViewById(R.id.visit_count);


        SpannableString content = new SpannableString("Terms and condition");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv_termsCondition.setText(content);

        apiInterface = ApiClient.getInterface();
        aboutUsApi();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);


        bt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        bt_contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ContactUsActivity.class);
                startActivity(intent);
            }
        });

        tv_termsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TermsConditionActivity.class);
                startActivity(intent);
            }
        });

        tv_Membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemberShipPlanActivity.class);
                startActivity(intent);
            }
        });

        tv_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);

            }
        });

        tv_deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DeleteAccountActivity.class);
                startActivity(intent);
            }
        });

        profile_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyProfileVisitorActivity.class);
                startActivity(intent);
            }
        });


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signOut();
                SharedPrefsManager.getInstance().setBoolean(KEY_LOGIN_STATUS, false);
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });


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
                            tv_aboutUs.setText(Html.fromHtml(Html.fromHtml(loginData.getContent()).toString()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        profileDetailApi(SharedPrefsManager.getInstance().getString(LOGIN_ID));

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //  unbinder.unbind();
    }

    private void profileDetailApi(String userId) {
        progress_bar.setVisibility(View.VISIBLE);

        apiInterface.profileDetail(userId).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ProfileModel> call, @NonNull Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    ProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        boolean respons = profileModel.isResponse();
                        //  String message = loginModel.getMessage();
                        if (respons) {
                            ProfileModel.ProfileData profileData = profileModel.getProfileData();
                            tv_username.setText(profileData.getName() + " (" + profileData.getMatriID() + ")");
                            tv_professional.setText(profileData.getOccupation());
                            visit_count.setText(profileData.getPagecount());

                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.drawable.logo_final)
                                    .error(R.drawable.logo_final)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .priority(Priority.HIGH).dontAnimate()
                                    .dontTransform();

                            if (profileData.getPhoto1() != null) {
                                Glide.with(Objects.requireNonNull(getActivity()))
                                        .load(IMAGE_LOAD_USER1 + profileData.getPhoto1())
                                        .apply(options)
                                        .into(iv_userprofile);
                                Glide.with(Objects.requireNonNull(getActivity()))
                                        .load(IMAGE_LOAD_USER1 + profileData.getPhoto1())
                                        .apply(bitmapTransform(new BlurTransformation(25)))
                                        .into(bg_blur_iv);
                            }
                        } else {
                            if (getContext() != null) {
                                Toast.makeText(getContext(), "Data not Found", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileModel> call, @NonNull Throwable t) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                }
                progress_bar.setVisibility(View.GONE);
            }
        });
    }

}
