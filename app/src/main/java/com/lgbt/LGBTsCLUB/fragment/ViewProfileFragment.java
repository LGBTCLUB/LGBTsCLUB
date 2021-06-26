package com.lgbt.LGBTsCLUB.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.lgbt.LGBTsCLUB.activity.MemberShipPlanActivity;
import com.lgbt.LGBTsCLUB.activity.MyPlanActivity;
import com.lgbt.LGBTsCLUB.model.MemberProfileModel;
import com.lgbt.LGBTsCLUB.model.ProfileImageModel;
import com.lgbt.LGBTsCLUB.model.ProfileModel;
import com.lgbt.LGBTsCLUB.model.UserStatusModel;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;
import com.lgbt.LGBTsCLUB.utility.FilePath;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
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
import static com.lgbt.LGBTsCLUB.network.networking.Constant.LOGIN_ID;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.MATRI_ID;


public class ViewProfileFragment extends Fragment {
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    public static String profileDetailApiCall = "No";
    ImageView iv_back, iv_home, iv_image1, iv_image2, iv_image3, iv_image4, iv_image5, iv_image6,
            iv_image7, iv_attachment, bg_blur_iv;
    TextView tv_attachment;
    TextView tv_namee, li_viewPlan, tv_account, tv_personal, tv_professional, tv_name, tv_gender, tv_hight, tv_dob, tv_age,
            tv_material, tv_motherTongue, tv_interest, tv_country, tv_city, tv_nationality, tv_contact,
            tv_email, tv_education, tv_perofessions, tv_salary, tv_bio, tv_upgrade;
    //    LinearLayout li_viewPlan;
    TextView hide;
    Button bt_editprofile;
    LinearLayout li_account, li_prfessional, li_personal;
    String imagePath, clickImage;
    int RESULT_LOAD_IMAGE = 100;
    int RESULT_LOAD_DOC = 200;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageView img;
    CircleImageView img1;


    ProgressBar progress_bar;
    String status = "hide";
    private ApiInterface apiInterface;



    public ViewProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);
        bt_editprofile = view.findViewById(R.id.bt_editprofile);
        tv_account = view.findViewById(R.id.tv_account);
        tv_personal = view.findViewById(R.id.tv_personal);
        tv_professional = view.findViewById(R.id.tv_professional);
        li_account = view.findViewById(R.id.li_account);
        li_prfessional = view.findViewById(R.id.li_prfessional);
        li_personal = view.findViewById(R.id.li_personal);
        img = view.findViewById(R.id.iv_closeone);
        img1 = view.findViewById(R.id.iv_image2);

        tv_name = view.findViewById(R.id.tv_name);
        tv_namee = view.findViewById(R.id.tv_namee);
        tv_gender = view.findViewById(R.id.tv_gender);
        tv_hight = view.findViewById(R.id.tv_hight);
        tv_dob = view.findViewById(R.id.tv_dob);
        tv_age = view.findViewById(R.id.tv_age);
        tv_material = view.findViewById(R.id.tv_material);
        tv_motherTongue = view.findViewById(R.id.tv_motherTongue);
        tv_interest = view.findViewById(R.id.tv_interest);
        tv_country = view.findViewById(R.id.tv_country);
        tv_city = view.findViewById(R.id.tv_city);
        tv_nationality = view.findViewById(R.id.tv_nationality);
        tv_contact = view.findViewById(R.id.tv_contact);
        tv_email = view.findViewById(R.id.tv_email);
        tv_education = view.findViewById(R.id.tv_education);
        tv_perofessions = view.findViewById(R.id.tv_perofessions);
//        tv_bio = findViewById(R.id.tv_bio);
        tv_salary = view.findViewById(R.id.tv_salary);
        iv_image1 = view.findViewById(R.id.iv_image1);
        iv_image2 = view.findViewById(R.id.iv_image2);
        iv_image3 = view.findViewById(R.id.iv_image3);
        iv_image4 = view.findViewById(R.id.iv_image4);
        iv_image5 = view.findViewById(R.id.iv_image5);
        iv_image6 = view.findViewById(R.id.iv_image6);
        iv_image7 = view.findViewById(R.id.iv_image7);
        bg_blur_iv = view.findViewById(R.id.bg_blur_iv);
        progress_bar = view.findViewById(R.id.progress_bar);

//        hide = findViewById(R.id.hide);
        tv_upgrade = view.findViewById(R.id.tv_upgrade);
        li_viewPlan = view.findViewById(R.id.li_viewPlan);

        apiInterface = ApiClient.getInterface();
       // tv_account.performClick();

        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
        frameLayout=(FrameLayout)view.findViewById(R.id.frame);

        fragment = new AccountFragment();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 2) {

            fragmentManager.popBackStack();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.frame, fragment)
                .addToBackStack(null)
                .commit();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        SpannableString content = new SpannableString("Account");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        tv_account.setText(content);

//        tv_account.setTextColor(getResources().getColor(R.color.navy_blue));
//        tv_account.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//        tv_personal.setTextColor(getResources().getColor(R.color.gray_light));
//        tv_professional.setTextColor(getResources().getColor(R.color.gray_light));

        iv_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickImage = "1";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
            }
        });
        iv_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickImage = "2";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
            }
        });
        iv_image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickImage = "3";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
            }
        });
        iv_image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickImage = "4";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
            }
        });
        iv_image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickImage = "5";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
            }
        });
        iv_image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickImage = "6";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
            }
        });
        iv_image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickImage = "7";
                requestCameraAndStorage(RESULT_LOAD_IMAGE);
            }
        });
        tv_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MemberShipPlanActivity.class));

            }
        });
        li_viewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyPlanActivity.class));

            }
        });


//        bt_editprofile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragment = new SaveDetailsFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment);
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                fragmentTransaction.commit();
//            }
//        });

//        tv_account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                li_account.setVisibility(VISIBLE);
//                li_prfessional.setVisibility(GONE);
//                li_personal.setVisibility(GONE);
//
////                SpannableString content = new SpannableString("Account");
////                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
////                tv_account.setText(content);
//
//                tv_account.setTextColor(getResources().getColor(R.color.navy_blue));
//                tv_account.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//                tv_personal.setTextColor(getResources().getColor(R.color.gray_light));
//                tv_professional.setTextColor(getResources().getColor(R.color.gray_light));
//            }
//        });
//        tv_personal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                li_personal.setVisibility(VISIBLE);
//                li_account.setVisibility(GONE);
//                li_prfessional.setVisibility(GONE);
//
////                SpannableString content = new SpannableString("Personal");
////                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
////                tv_personal.setText(content);
//
//                tv_account.setTextColor(getResources().getColor(R.color.gray_light));
//                tv_personal.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//                tv_personal.setTextColor(getResources().getColor(R.color.navy_blue));
//                tv_professional.setTextColor(getResources().getColor(R.color.gray_light));
//            }
//        });
//        tv_professional.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                li_prfessional.setVisibility(VISIBLE);
//                li_account.setVisibility(GONE);
//                li_personal.setVisibility(GONE);
//
////                SpannableString content = new SpannableString("Professional");
////                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
////                tv_professional.setText(content);
//
//                tv_account.setTextColor(getResources().getColor(R.color.gray_light));
//                tv_professional.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//                tv_personal.setTextColor(getResources().getColor(R.color.gray_light));
//                tv_professional.setTextColor(getResources().getColor(R.color.navy_blue));
//            }
////            9584936236
//        });

        isStoragePermissionGranted();
     //   profileDetailApi(SharedPrefsManager.getInstance().getString(MATRI_ID));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new AccountFragment();
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


    private void profileDetailApi(String userId) {
        Log.i("rhl..userId", userId);
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
                            tv_namee.setText(profileData.get(0).getName());
                            tv_gender.setText(profileData.get(0).getGender());
                            tv_hight.setText(profileData.get(0).getUserHeight());
                            tv_dob.setText(profileData.get(0).getDOB());
                            tv_age.setText(profileData.get(0).getAge());
                            tv_material.setText(profileData.get(0).getMaritalstatus());
                            tv_motherTongue.setText(profileData.get(0).getLanguage());
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
                            tv_education.setText(profileData.get(0).getEducation());
                            tv_perofessions.setText(profileData.get(0).getOccupation());
                            tv_salary.setText(profileData.get(0).getAnnualincome());

                            try {
                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .placeholder(R.drawable.logo_final)
                                        .error(R.drawable.logo_final)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .priority(Priority.HIGH).dontAnimate()
                                        .dontTransform();

                                Glide.with(getContext())
                                        .load(IMAGE_LOAD_USER1 + profileData.get(0).getPhoto1())
                                        .apply(options)
                                        .into(iv_image1);
                                Glide.with(getContext())
                                        .load(IMAGE_LOAD_USER1 + profileData.get(0).getPhoto1())
                                        .apply(bitmapTransform(new BlurTransformation(25)))
                                        .into(bg_blur_iv);

                                Glide.with(getContext())
                                        .load(IMAGE_LOAD_USER2 + profileData.get(0).getPhoto2())
                                        .apply(options)
                                        .into(iv_image2);

                                Glide.with(getContext())
                                        .load(IMAGE_LOAD_USER3 + profileData.get(0).getPhoto3())
                                        .apply(options)
                                        .into(iv_image3);

                                Glide.with(getContext())
                                        .load(IMAGE_LOAD_USER4 + profileData.get(0).getPhoto4())
                                        .apply(options)
                                        .into(iv_image4);

                                Glide.with(getContext())
                                        .load(IMAGE_LOAD_USER5 + profileData.get(0).getPhoto5())
                                        .apply(options)
                                        .into(iv_image5);

                                Glide.with(getContext())
                                        .load(IMAGE_LOAD_USER6 + profileData.get(0).getPhoto6())
                                        .apply(options)
                                        .into(iv_image6);

                                Glide.with(getContext())
                                        .load(IMAGE_LOAD_USER7 + profileData.get(0).getPhoto7())
                                        .apply(options)
                                        .into(iv_image7);
                            } catch (Exception e) {

                            }
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            cursor.close();
            imagePath = FilePath.getPath(getContext(), selectedImage);

            iv_image2.setVisibility(VISIBLE);

            if (clickImage.equals("1")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image1);
            } else if (clickImage.equals("2")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image2);
            } else if (clickImage.equals("3")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image3);
            } else if (clickImage.equals("4")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image4);
            } else if (clickImage.equals("5")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image5);
            } else if (clickImage.equals("6")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image6);
            } else if (clickImage.equals("7")) {
                Glide.with(this).load(selectedImage.toString()).into(iv_image7);
            }

            progress_bar.setVisibility(VISIBLE);
            updateProfileImage(clickImage, SharedPrefsManager.getInstance().getString(LOGIN_ID));

        } else if (requestCode == RESULT_LOAD_DOC && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            cursor.close();
            String filePath = FilePath.getPath(getContext(), selectedImage);

            tv_attachment.setText(filePath);
            Log.i("rhl....@@", "callll");
            upload_document(filePath, SharedPrefsManager.getInstance().getString(MATRI_ID));
        }
    }


    private void upload_document(String imagePath, String matriid) {
        progress_bar.setVisibility(VISIBLE);

        File compressedImageFile;
        try {
            compressedImageFile = new Compressor(getContext()).compressToFile(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            compressedImageFile = new File(imagePath);
        }

        MultipartBody.Part imagePart;
        if (imagePath == null || imagePath.isEmpty()) {
            imagePart = MultipartBody.Part.createFormData("image", "");
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), compressedImageFile);
            imagePart = MultipartBody.Part.createFormData("image", compressedImageFile.getPath(), requestBody);
        }


        MultipartBody.Part matriidPart = MultipartBody.Part.createFormData("matriid", matriid);

        apiInterface.upload_document(matriidPart, imagePart).enqueue(new Callback<ProfileImageModel>() {
            @Override
            public void onResponse(Call<ProfileImageModel> call, Response<ProfileImageModel> response) {
                if (response.isSuccessful()) {
                    progress_bar.setVisibility(GONE);
                    ProfileImageModel profileModel = response.body();
                    if (profileModel != null) {
                        String respons = profileModel.getResponse();
                        if (respons.equals("true")) {
                            //  Toast.makeText(getContext().this, "Success", Toast.LENGTH_LONG).show();
                            profileDetail(SharedPrefsManager.getInstance().getString(LOGIN_ID));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileImageModel> call, Throwable t) {
                //      Toast.makeText(getContext().this, "Some thing is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);
            }
        });
    }


    private void updateProfileImage(String imageno, String user_id) {

        MultipartBody.Part imagenoPart = MultipartBody.Part.createFormData("imageno", imageno);


        File compressedImageFile;
        try {
            compressedImageFile = new Compressor(getContext()).compressToFile(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            compressedImageFile = new File(imagePath);
        }

        MultipartBody.Part imagePart;
        if (imagePath == null || imagePath.isEmpty()) {
            imagePart = MultipartBody.Part.createFormData("image", "");
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), compressedImageFile);
            imagePart = MultipartBody.Part.createFormData("image", compressedImageFile.getPath(), requestBody);
        }


        MultipartBody.Part user_idPart = MultipartBody.Part.createFormData("user_id", user_id);

        apiInterface.updateProfileImage(imagenoPart, user_idPart, imagePart).enqueue(new Callback<ProfileImageModel>() {
            @Override
            public void onResponse(Call<ProfileImageModel> call, Response<ProfileImageModel> response) {
                if (response.isSuccessful()) {
                    progress_bar.setVisibility(GONE);
                    ProfileImageModel profileModel = response.body();
                    if (profileModel != null) {
                        String respons = profileModel.getResponse();
                        if (respons.equals("true")) {
                            profileDetail(SharedPrefsManager.getInstance().getString(LOGIN_ID));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileImageModel> call, Throwable t) {
                Toast.makeText(getContext(), "Some thing is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);
            }
        });
    }

    private void profileDetail(String userId) {
        apiInterface.profileDetail(userId).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> prcall, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    ProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        boolean respons = profileModel.isResponse();
                        if (respons) {
                            ProfileModel.ProfileData profileData = profileModel.getProfileData();
                            String editStatus = profileData.getEdit_status();
                            if (!editStatus.equals("Yes")) {
                                afterregisContain();
                            }
                        } else {
                            // Toast.makeText(getContext().this, "No Data Found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                //  Toast.makeText(getContext().this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void afterregisContain() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.after_regis_contain_view);

        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        Button ok = dialog.findViewById(R.id.ok);
        ok.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (profileDetailApiCall.equals("Yes")) {
            profileDetailApi(SharedPrefsManager.getInstance().getString(MATRI_ID));
            profileDetailApiCall = "No";
        }
        user_status(SharedPrefsManager.getInstance().getString(LOGIN_ID));
    }


    private void user_status(String user_id) {
        apiInterface.user_status(user_id).enqueue(new Callback<UserStatusModel>() {
            @Override
            public void onResponse(Call<UserStatusModel> call, Response<UserStatusModel> response) {
                if (response.isSuccessful()) {
                    UserStatusModel userStatusModel = response.body();
                    if (userStatusModel != null) {
                        String status = userStatusModel.getStatus();
                        if (!status.equals("Active")) {
                            SharedPrefsManager.getInstance().clearPrefs();
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserStatusModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }



    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED) {
//                return true;
//            } else {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                return false;
//            }
        }
//        else {
        return true;
//        }
    }
    public void requestCameraAndStorage(int request) {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.check(getContext(), permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, request);
            }
        });
    }



}