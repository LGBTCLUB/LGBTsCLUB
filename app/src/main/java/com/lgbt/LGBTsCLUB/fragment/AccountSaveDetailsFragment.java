package com.lgbt.LGBTsCLUB.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import id.zelory.compressor.Compressor;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
import com.lgbt.LGBTsCLUB.R;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER1;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.LOGIN_ID;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.MATRI_ID;

public class AccountSaveDetailsFragment extends Fragment {
    Button bt_savedetails;
    ImageView iv_back, iv_home, iv_userprofile, iv_blur;

    EditText et_firstName, et_interest, et_phone, et_email, et_income, et_bio, et_hobbies, et_description;

    Spinner sp_gender, sp_feet, sp_inch, sp_day, sp_month, sp_year, sp_material, sp_motherToung,
            sp_religon, sp_country, sp_countrys, sp_state, sp_city, sp_education, sp_profession, sp_interest;

    TextView et_dob;

    RadioGroup radioGroup;
    RadioButton rb_typeYes, rb_typeNo;
    String childrenlivingstatus = "No";
    String countryN, StateN, cityN, religionN, maritalstatusN, GenderN, EducationN, OccupationN, dobN, heightN, showgenderN, childrenlivingstatusN;
    RelativeLayout rvuserImage;
    String Gender, hightF, mstatus, matstatusName, MotherToung, motherToungID, motherToungName, religiousID, country_id,
            religiousIDName, countryId, countryName, countryNameS, countryIdP, stateId, stateName, cityId, cityName, day, month, year,
            educationID, educationName, professionID, professionName, heightFeetName, heighttoFeetName, intrest;

    String clickStatus, country, imagePath;
    String dobDate, dobMonth, dobYear;

    Date date;
    ProgressBar progress_bar;
    // private ProgressDailog progress;
    List<String> genderlist = new ArrayList<>();
    List<String> interestlist = new ArrayList<>();
    List<String> heightfromlist = new ArrayList<>();
    List<String> heighttolist = new ArrayList<>();
    List<String> daylist = new ArrayList<>();
    List<String> monthlist = new ArrayList<>();
    List<String> yearlist = new ArrayList<>();
    List<String> materiallist = new ArrayList<>();
    int RESULT_LOAD_IMAGE;
    private ApiInterface apiInterface;
    private AccountSaveDetailsFragment.SpinnerAdapter genderAdapter;
    private AccountSaveDetailsFragment.SpinnerAdapter interestAdapter;
    private AccountSaveDetailsFragment.SpinnerAdapter fromheightAdapter;
    private AccountSaveDetailsFragment.SpinnerAdapter toheightAdapter;
    private AccountSaveDetailsFragment.SpinnerAdapter dayAdapter;
    private AccountSaveDetailsFragment.SpinnerAdapter monthAdapter;
    private AccountSaveDetailsFragment.SpinnerAdapter yearAdapter;
    private AccountSaveDetailsFragment.SpinnerAdapter materialAdapter;
    private List<MotherToungueDataModel.MotherToungueData> motherToungueDataArrayList;
    private List<SpecialSearchModel.SpecialData> specialDataArrayList;
    private List<CountryDataModel.DataBean> countryModelArrayList;
    private List<StateDataModel.DataEntity> stateModelArrayList;
    private List<CityDataModel.DataCity> cityModelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_save_details, container, false);
        bt_savedetails = view.findViewById(R.id.bt_savedetails);
        et_firstName = view.findViewById(R.id.et_firstName);

        et_interest = view.findViewById(R.id.et_interest);
        et_phone = view.findViewById(R.id.et_phone);
        et_email = view.findViewById(R.id.et_email);
        et_income = view.findViewById(R.id.et_income);
        et_bio = view.findViewById(R.id.et_bio);
        et_hobbies = view.findViewById(R.id.et_hobbies);
        iv_blur = view.findViewById(R.id.iv_blur);

        sp_gender = view.findViewById(R.id.sp_gender);
        sp_feet = view.findViewById(R.id.sp_feet);
        sp_inch = view.findViewById(R.id.sp_inch);
        sp_day = view.findViewById(R.id.sp_day);
        sp_month = view.findViewById(R.id.sp_month);
        sp_year = view.findViewById(R.id.sp_year);
        sp_material = view.findViewById(R.id.sp_material);
        sp_motherToung = view.findViewById(R.id.sp_motherToung);
        sp_religon = view.findViewById(R.id.sp_religon);
        sp_country = view.findViewById(R.id.sp_country);
        sp_state = view.findViewById(R.id.sp_state);
        sp_city = view.findViewById(R.id.sp_city);
        sp_countrys = view.findViewById(R.id.sp_countrys);
        sp_education = view.findViewById(R.id.sp_education);
        sp_profession = view.findViewById(R.id.sp_profession);
        sp_interest = view.findViewById(R.id.sp_interest);

        et_dob = view.findViewById(R.id.et_dob);
        iv_userprofile = view.findViewById(R.id.iv_userprofile);
        progress_bar = view.findViewById(R.id.progress_bar);
        rvuserImage = view.findViewById(R.id.rvuserImage);
        radioGroup = view.findViewById(R.id.radioGroup);
        rb_typeYes = view.findViewById(R.id.rb_typeYes);
        rb_typeNo = view.findViewById(R.id.rb_typeNo);

        et_description = view.findViewById(R.id.et_description);


        apiInterface = ApiClient.getInterface();

        motherToungueDataArrayList = new ArrayList<>();
        specialDataArrayList = new ArrayList<>();
        countryModelArrayList = new ArrayList<>();
        stateModelArrayList = new ArrayList<>();
        cityModelArrayList = new ArrayList<>();

        progress_bar.setVisibility(VISIBLE);                        //String dob = year+"/"+month+"/"+day;
        profileDetailApi(SharedPrefsManager.getInstance().getString(MATRI_ID));


        clickStatus = "Account";
        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Gender.equals("Lesbian") || Gender.equals("Bisexual (Woman)") || Gender.equals("Transgender Woman")) {
                    final Calendar dob_date = Calendar.getInstance();
                    final Calendar dob_currentDate = Calendar.getInstance();
                    DatePickerDialog dob_atePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            dob_date.set(year, monthOfYear, dayOfMonth);
                            dobMonth = (monthOfYear + 1) + "";
                            dobDate = dayOfMonth + "";
                            dobYear = year + "";
                            date = dob_date.getTime();
                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

                            et_dob.setText(dateFormat.format(date));

                        }
                    }, dob_currentDate.get(Calendar.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DATE));

                    dob_date.add(Calendar.YEAR, -18);

                    dob_atePickerDialog.getDatePicker().setMaxDate(dob_date.getTimeInMillis());
                    dob_atePickerDialog.show();
                } else {
                    final Calendar dob_date = Calendar.getInstance();
                    final Calendar dob_currentDate = Calendar.getInstance();
                    DatePickerDialog dob_atePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            dob_date.set(year, monthOfYear, dayOfMonth);
                            dobMonth = (monthOfYear + 1) + "";
                            dobDate = dayOfMonth + "";
                            dobYear = year + "";
                            date = dob_date.getTime();
                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

                            et_dob.setText(dateFormat.format(date));

                        }
                    }, dob_currentDate.get(Calendar.YEAR), dob_currentDate.get(Calendar.MONTH), dob_currentDate.get(Calendar.DATE));

                    dob_date.add(Calendar.YEAR, -21);

                    dob_atePickerDialog.getDatePicker().setMaxDate(dob_date.getTimeInMillis());
                    dob_atePickerDialog.show();
                }


            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb) {
                    //   Toast.makeText(TellusaboutActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    childrenlivingstatus = rb.getText().toString();
                }
            }
        });


        bt_savedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clickStatus.equals("Account")) {
                    if (validationSuccess()) {
                        progress_bar.setVisibility(VISIBLE);
                        //String dob = year+"/"+month+"/"+day;
                        String dob = dobYear + "/" + dobMonth + "/" + dobDate;
                        update_userAboutUs(et_firstName.getText().toString(), "", dob, Gender,
                                motherToungName, religiousIDName, countryName, heightFeetName, matstatusName, SharedPrefsManager.getInstance().getString(LOGIN_ID), childrenlivingstatus);
                    }
                } else if (clickStatus.equals("Personal")) {
                    if (validationSuccessP()) {
                        progress_bar.setVisibility(VISIBLE);                        //String dob = year+"/"+month+"/"+day;
                        update_userpersonhal(et_hobbies.getText().toString(), intrest, countryNameS, stateName, cityId, et_phone.getText().toString(), et_email.getText().toString(), SharedPrefsManager.getInstance().getString(LOGIN_ID), cityName, et_description.getText().toString());
                    }
                } else if (clickStatus.equals("Profess")) {
                    if (validationSuccessPro()) {
                        progress_bar.setVisibility(VISIBLE);                        //String dob = year+"/"+month+"/"+day;
                        updateprofessional_details(educationName, professionName, et_income.getText().toString(), et_bio.getText().toString(), SharedPrefsManager.getInstance().getString(LOGIN_ID));
                    }

                } else {

                }
            }
        });
        sp_motherToung.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (motherToungueDataArrayList.size() > 0) {
                    motherToungID = motherToungueDataArrayList.get(position).getMotherTongueId();
                    motherToungName = motherToungueDataArrayList.get(position).getMotherTongue();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_religon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (specialDataArrayList.size() > 0) {
                    religiousID = specialDataArrayList.get(position).getId();
                    religiousIDName = specialDataArrayList.get(position).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);
                Log.d("TAG", "bandId: " + bandId);

                if (countryModelArrayList.size() > 0) {
                    country_id = countryModelArrayList.get(position).getCountryId();
                    Log.d("TAG", "countryIdd: " + country_id);
                    countryName = countryModelArrayList.get(position).getCountry();
                    Toast.makeText(getContext(), "" + country_id, Toast.LENGTH_SHORT).show();

                    if (country_id != null) {
                        StateApi(country_id);
                        //  CityApi(stateName);

                    }
                } else {
                    Toast.makeText(getContext(), "" + countryModelArrayList.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String bandId = (String) parent.getItemAtPosition(position);
//                Log.d("TAG", "bandId: " + bandId);
//
//                if (stateModelArrayList.size() > 0) {
//                    stateId = stateModelArrayList.get(position).getStateId();
//                    Log.d("TAG", "stateId: " + stateId);
//                    stateName = stateModelArrayList.get(position).getState();
//                    Toast.makeText(getContext(), "" + stateName, Toast.LENGTH_SHORT).show();
//
//                    if (stateName != null) {
//                        CityApi(stateName);
//
//                    }
//                } else {
//                    Toast.makeText(getContext(), "" + stateModelArrayList.size(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String bandId = (String) parent.getItemAtPosition(position);
//                Log.d("TAG", "bandId: " + bandId);
//
//                if (cityModelArrayList.size() > 0) {
//                    cityId = cityModelArrayList.get(position).getId();
//                    cityName = cityModelArrayList.get(position).getCity();
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//        sp_education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String bandId = (String) parent.getItemAtPosition(position);
//
//                if (educationDataArrayList.size() > 0) {
//                    educationID = educationDataArrayList.get(position).getEducationId();
//                    educationName = educationDataArrayList.get(position).getEducation();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        sp_profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String bandId = (String) parent.getItemAtPosition(position);
//
//                if (occupationDataArrayList.size() > 0) {
//                    professionID = occupationDataArrayList.get(position).getOccupationId();
//                    professionName = occupationDataArrayList.get(position).getOccupation();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        rvuserImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
//            }
//        });
        return view;
    }

    private void profileDetailApi(String userId) {
        progress_bar.setVisibility(VISIBLE);                        //String dob = year+"/"+month+"/"+day;

        apiInterface.member_profile_details(userId, userId).enqueue(new Callback<MemberProfileModel>() {
            @Override
            public void onResponse(Call<MemberProfileModel> call, Response<MemberProfileModel> response) {
                if (response.isSuccessful()) {
                    MemberProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        progress_bar.setVisibility(GONE);                        //String dob = year+"/"+month+"/"+day;
                        boolean respons = profileModel.isResponse();
                        //  String message = loginModel.getMessage();
                        //  Log.i("rhl....R", String.valueOf(respons));
                        if (respons) {
                            List<MemberProfileModel.MemberProfileData> profileData = profileModel.getMemberProfileDataList();

                            country = profileData.get(0).getCountry();

                            et_firstName.setText(profileData.get(0).getName());

//                            et_hobbies.setText(profileData.get(0).getHobbies());
//                            et_phone.setText(profileData.get(0).getMobile());
//                            et_email.setText(profileData.get(0).getConfirmEmail());
//                            et_income.setText(profileData.get(0).getAnnualincome());
//                            et_bio.setText(profileData.get(0).getProfile());
                            et_dob.setText(profileData.get(0).getDOB());
//                            et_description.setText(profileData.get(0).getPartnerExpectations());
                            countryN = profileData.get(0).getCountry();
                            StateN = profileData.get(0).getState();
                            cityN = profileData.get(0).getCity_name();
//                            religionN = profileData.get(0).getReligion();
                            MotherToung = profileData.get(0).getLanguage();
                            maritalstatusN = profileData.get(0).getMaritalstatus();
                            GenderN = profileData.get(0).getGender();
//                            EducationN = profileData.get(0).getEducation();
//                            OccupationN = profileData.get(0).getOccupation();
                            dobN = profileData.get(0).getDOB();
                            heightN = profileData.get(0).getUserHeight();
                            showgenderN = profileData.get(0).getShow_gender();
                            childrenlivingstatusN = profileData.get(0).getChildrenlivingstatus();

                            if (childrenlivingstatusN != null) {
                                if (childrenlivingstatusN.equalsIgnoreCase("Yes")) {
                                    rb_typeNo.setChecked(false);
                                    rb_typeYes.setChecked(true);
                                } else {
                                    rb_typeYes.setChecked(false);
                                    rb_typeNo.setChecked(true);
                                }
                                childrenlivingstatus = childrenlivingstatusN;
                            }

                            if (dobN != null) {
                                String[] dobs = dobN.split("-");
                                dobYear = dobs[0];
                                dobMonth = dobs[1];
                                dobDate = dobs[2];
                            }


                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.drawable.logo_final)
                                    .error(R.drawable.logo_final)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .priority(Priority.HIGH).dontAnimate()
                                    .dontTransform();

                            init();
                            MotherToungueApi();
                            //  ReligiousApi();
                            CountryApi();
                            //  EducationApi();
                            //  OccupationApi();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberProfileModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);                        //String dob = year+"/"+month+"/"+day;

            }
        });

    }


    private void init() {
        //--------------------------Gender----------------------------
        genderAdapter = new AccountSaveDetailsFragment.SpinnerAdapter(getContext());
        sp_gender.setAdapter(genderAdapter);
       /* genderlist.add("Male");
        genderlist.add("Female");
        genderlist.add("Transgender");
*/

        genderlist.add("Gay");
        genderlist.add("Lesbian");
        genderlist.add("Bisexual (Man)");
        genderlist.add("Bisexual (Woman)");
        genderlist.add("Transgender Man");
        genderlist.add("Transgender Woman");

        genderAdapter.addTaxRateList(genderlist);

        if (genderlist.size() > 0) {
            for (int y = 0; y < genderlist.size(); y++) {
                String gender = genderlist.get(y);
                if (GenderN != null) {
                    if (GenderN.equals(gender)) {
                        sp_gender.setSelection(y);
                    }
                }
            }
        }

        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (genderlist.size() > 0) {
                    int position1 = sp_gender.getSelectedItemPosition();
                    Gender = genderlist.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        interestAdapter = new AccountSaveDetailsFragment.SpinnerAdapter(getContext());
        sp_interest.setAdapter(interestAdapter);

      /*  interestlist.add("Man seeking Man");
        interestlist.add("Woman seeking Woman");
        interestlist.add("Man seeking Woman");
        interestlist.add("Woman seeking Man");
        interestlist.add("Transgender Man");
        interestlist.add("Transgender Woman");
        interestlist.add("Bisexual Man");
        interestlist.add("Bisexual Woman");
*/
        interestlist.add("Male");
        interestlist.add("Female");
        interestlist.add("Transgender");


        interestAdapter.addTaxRateList(interestlist);

        if (interestlist.size() > 0) {
            for (int y = 0; y < interestlist.size(); y++) {
                String gender = interestlist.get(y);
                if (showgenderN != null) {
                    if (showgenderN.equals(gender)) {
                        sp_interest.setSelection(y);
                    }
                }
            }
        }
        sp_interest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (interestlist.size() > 0) {
                    int position1 = sp_interest.getSelectedItemPosition();
                    intrest = interestlist.get(position);

                   /* if (position1 == 0) {
                        Gender = "Male";
                    } else if (position1 == 1) {
                        Gender = "Female";
                    }
                    else if (position1 == 2) {
                        Gender = "Transgender";
                    }*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //-------------------------------Day-----------------------------------------------
        dayAdapter = new AccountSaveDetailsFragment.SpinnerAdapter(getContext());
        sp_day.setAdapter(dayAdapter);
        daylist.add("1");
        daylist.add("2");
        daylist.add("3");
        daylist.add("4");
        daylist.add("5");
        daylist.add("6");
        daylist.add("7");
        daylist.add("8");
        daylist.add("9");
        daylist.add("10");
        daylist.add("11");
        daylist.add("12");
        daylist.add("13");
        daylist.add("14");
        daylist.add("15");
        daylist.add("16");
        daylist.add("17");
        daylist.add("18");
        daylist.add("19");
        daylist.add("20");
        daylist.add("21");
        daylist.add("22");
        daylist.add("23");
        daylist.add("24");
        daylist.add("25");
        daylist.add("26");
        daylist.add("27");
        daylist.add("28");
        daylist.add("29");
        daylist.add("30");
        daylist.add("31");

        dayAdapter.addTaxRateList(daylist);
        sp_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (daylist.size() > 0) {
                    day = daylist.get(position);
                    int position1 = sp_day.getSelectedItemPosition();
                    Log.i("position....", String.valueOf(position1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//------------------------------------Month-----------------------------------------
        monthAdapter = new AccountSaveDetailsFragment.SpinnerAdapter(getContext());
        sp_month.setAdapter(monthAdapter);
        monthlist.add("Jun");
        monthlist.add("Feb");
        monthlist.add("Mar");
        monthlist.add("Apr");
        monthlist.add("May");
        monthlist.add("Jun");
        monthlist.add("Jul");
        monthlist.add("Aug");
        monthlist.add("Shep");
        monthlist.add("Oct");
        monthlist.add("Nov");
        monthlist.add("Dec");

        monthAdapter.addTaxRateList(monthlist);
        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (monthlist.size() > 0) {
                    int position1 = sp_month.getSelectedItemPosition();

                    if (position1 == 0) {
                        month = "1";
                    } else if (position1 == 1) {
                        month = "2";
                    } else if (position1 == 2) {
                        month = "3";
                    } else if (position1 == 3) {
                        month = "4";
                    } else if (position1 == 4) {
                        month = "5";
                    } else if (position1 == 5) {
                        month = "6";
                    } else if (position1 == 6) {
                        month = "7";
                    } else if (position1 == 7) {
                        month = "8";
                    } else if (position1 == 8) {
                        month = "9";
                    } else if (position1 == 9) {
                        month = "10";
                    } else if (position1 == 10) {
                        month = "11";
                    } else if (position1 == 11) {
                        month = "12";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //------------------------------------Year-----------------------------------------
        yearAdapter = new AccountSaveDetailsFragment.SpinnerAdapter(getContext());
        sp_year.setAdapter(yearAdapter);
        yearlist.add("1985");
        yearlist.add("1986");
        yearlist.add("1987");
        yearlist.add("1988");
        yearlist.add("1989");
        yearlist.add("1990");
        yearlist.add("1991");
        yearlist.add("1992");
        yearlist.add("1993");
        yearlist.add("1994");
        yearlist.add("1995");
        yearlist.add("1996");
        yearlist.add("1997");
        yearlist.add("1998");
        yearlist.add("1999");
        yearlist.add("2000");
        yearlist.add("2001");
        yearlist.add("2002");
        yearlist.add("2003");
        yearlist.add("2004");
        yearlist.add("2005");
        yearlist.add("2006");
        yearlist.add("2007");
        yearlist.add("2008");
        yearlist.add("2009");
        yearlist.add("2010");
        yearlist.add("2011");
        yearlist.add("2012");
        yearlist.add("2013");
        yearlist.add("2014");
        yearlist.add("2015");
        yearlist.add("2016");
        yearlist.add("2017");
        yearlist.add("2018");
        yearlist.add("2019");
        yearlist.add("2020");
        yearlist.add("2021");
        yearlist.add("2022");
        yearlist.add("2023");
        yearlist.add("2024");
        yearlist.add("2025");
        yearlist.add("2026");
        yearlist.add("2027");
        yearlist.add("2028");
        yearlist.add("2029");
        yearlist.add("2030");
        yearlist.add("2031");
        yearlist.add("2032");
        yearlist.add("2033");
        yearlist.add("2034");
        yearlist.add("2035");
        yearlist.add("2036");
        yearlist.add("2037");
        yearlist.add("2038");
        yearlist.add("2039");
        yearlist.add("2040");

        yearAdapter.addTaxRateList(yearlist);
        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (yearlist.size() > 0) {
                    int position1 = sp_year.getSelectedItemPosition();
                    year = yearlist.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //-------------------------------Height from-----------------------------------------------
        fromheightAdapter = new AccountSaveDetailsFragment.SpinnerAdapter(getContext());
        sp_feet.setAdapter(fromheightAdapter);
        heightfromlist.add("4ft ( 121cm )");
        heightfromlist.add("4ft 1in ( 124cm)");
        heightfromlist.add("4ft 2in ( 127cm )");
        heightfromlist.add("4ft 3in ( 129cm ) ");
        heightfromlist.add("4ft 4in ( 132cm )");
        heightfromlist.add("4ft 5in ( 134cm )");
        heightfromlist.add("4ft 6in ( 137cm )");
        heightfromlist.add("4ft 7in ( 139cm )");
        heightfromlist.add("4ft 8in ( 142cm )");
        heightfromlist.add("4ft 9in ( 144cm )");
        heightfromlist.add("4ft 10in ( 147cm )");
        heightfromlist.add("4ft 11in ( 149cm )");
        heightfromlist.add("5ft ( 152cm )");
        heightfromlist.add("5ft 1in ( 154cm )");
        heightfromlist.add("5ft 2in ( 157cm )");
        heightfromlist.add("5ft 3in ( 160cm )");
        heightfromlist.add("5ft 4in ( 162cm )");
        heightfromlist.add("5ft 5in ( 165cm )");
        heightfromlist.add("5ft 6in ( 167cm )");
        heightfromlist.add("5ft 7in ( 170cm )");
        heightfromlist.add("5ft 8in ( 172cm )");
        heightfromlist.add("5ft 9in ( 175cm )");
        heightfromlist.add("5ft 10in ( 177cm )");
        heightfromlist.add("5ft 11in ( 180cm )");
        heightfromlist.add("6ft ( 182cm )");
        heightfromlist.add("6ft 1in ( 185cm )");
        heightfromlist.add("6ft 2in ( 187cm )");
        heightfromlist.add("6ft 3in ( 190cm ) ");
        heightfromlist.add("6ft 4in ( 193cm )");
        heightfromlist.add("6ft 5in ( 195cm )");
        heightfromlist.add("6ft 6in ( 198cm )");
        heightfromlist.add("6ft 7in ( 200cm ) ");
        heightfromlist.add("6ft 8in ( 203cm )");
        heightfromlist.add("6ft 9in ( 205cm )");
        heightfromlist.add("6ft 10in ( 208cm )");
        heightfromlist.add("6ft 11in ( 210cm )");
        heightfromlist.add("7ft ( 213cm )");
        fromheightAdapter.addTaxRateList(heightfromlist);

        if (heightfromlist.size() > 0) {
            for (int y = 0; y < heightfromlist.size(); y++) {
                String height = heightfromlist.get(y);
                if (heightN != null) {
                    if (heightN.equals(height)) {
                        sp_feet.setSelection(y);
                    }
                }
            }
        }

        sp_feet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (heightfromlist.size() > 0) {
                    int position1 = sp_feet.getSelectedItemPosition();
                    hightF = String.valueOf(position1);
                    // heightFeetName = heightfromlist.get(position);
                    heightFeetName = String.valueOf(position1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//------------------------------------Height To-----------------------------------------
        toheightAdapter = new AccountSaveDetailsFragment.SpinnerAdapter(getContext());
        sp_inch.setAdapter(toheightAdapter);
        heighttolist.add("6ft  (182cm)");
        heighttolist.add("6ft 1in (185cm)");
        heighttolist.add("6ft 2in (187cm)");
        heighttolist.add("6ft 3in (190cm) ");
        heighttolist.add("6ft 4in (193cm)");
        heighttolist.add("6ft 5in (195cm)");
        heighttolist.add("6ft 6in (198cm)");
        heighttolist.add("6ft 7in (200cm) ");
        heighttolist.add("6ft 8in (203cm)");
        heighttolist.add("6ft 9in (205cm)");
        heighttolist.add("6ft 10in (208cm)");
        heighttolist.add("6ft 11in (210cm) ");
        heighttolist.add("7ft  (213cm)");
        toheightAdapter.addTaxRateList(heighttolist);
        sp_inch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (heighttolist.size() > 0) {
                    int position1 = sp_inch.getSelectedItemPosition();
                    heighttoFeetName = heighttolist.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //--------------------------Material----------------------------
        materialAdapter = new AccountSaveDetailsFragment.SpinnerAdapter(getContext());
        sp_material.setAdapter(materialAdapter);
        materiallist.add("Unmarried");
        materiallist.add("Widowed");
        materiallist.add("Divorced");
        materiallist.add("Seprated");

        materialAdapter.addTaxRateList(materiallist);

        if (materiallist.size() > 0) {
            for (int y = 0; y < materiallist.size(); y++) {
                String materiall = materiallist.get(y);
                if (maritalstatusN != null) {
                    if (maritalstatusN.equals(materiall)) {
                        sp_material.setSelection(y);
                    }
                }
            }
        }

        sp_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (materiallist.size() > 0) {

                    int position1 = sp_material.getSelectedItemPosition();
                    matstatusName = materiallist.get(position);

                    if (position1 == 0) {
                        mstatus = "1";
                    } else if (position1 == 1) {
                        mstatus = "2";
                    } else if (position1 == 2) {
                        mstatus = "3";
                    } else if (position1 == 3) {
                        mstatus = "4";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void CountryApi() {
        apiInterface.get_country().enqueue(new Callback<CountryDataModel>() {
            @Override
            public void onResponse(Call<CountryDataModel> call, retrofit2.Response<CountryDataModel> response) {
                CountryDataModel countryDataModel = response.body();
                if (countryDataModel != null) {
                    if (countryDataModel.getResponse()) {
                        countryModelArrayList = countryDataModel.getData();
                        Log.d("TAG", "onResponse: " + countryModelArrayList.get(0).getCountry());
                        sp_country.setAdapter(new AccountSaveDetailsFragment.CountryAdapter(getContext(), countryModelArrayList));
                        StateApi(countryModelArrayList.get(0).getCountryId());
                    }
                }
            }

            @Override
            public void onFailure(Call<CountryDataModel> call, Throwable t) {
                Log.d("TAG", "onResponse: Fail");

            }
        });
    }


    private void StateApi(String country_id) {
        apiInterface.get_state(country_id).enqueue(new Callback<StateDataModel>() {
            @Override
            public void onResponse(Call<StateDataModel> call, retrofit2.Response<StateDataModel> response) {
                StateDataModel stateDataModel = response.body();
                Log.d("TAG", "stateData2: " + stateDataModel);

                if (stateDataModel != null) {
                    Log.d("TAG", "stateData: " + stateDataModel.getData());
                    if (stateDataModel.getResponse()) {
                        stateModelArrayList = stateDataModel.getData();
                        Log.d("TAG", "onResponse2: " + stateModelArrayList.get(0).getState());
                        sp_state.setAdapter(new AccountSaveDetailsFragment.StateAdapter(getContext(), stateModelArrayList));
                        CityApi(stateModelArrayList.get(0).getState());

                    }
                }
            }

            @Override
            public void onFailure(Call<StateDataModel> call, Throwable t) {
                Log.d("TAG", "onResponse2: Fail");

            }
        });
    }

    private void CityApi(String stateName) {
        apiInterface.get_city(stateName).enqueue(new Callback<CityDataModel>() {
            @Override
            public void onResponse(Call<CityDataModel> call, retrofit2.Response<CityDataModel> response) {
                CityDataModel cityDataModel = response.body();
                if (cityDataModel != null) {
                    if (cityDataModel.getResponse()) {
                        cityModelArrayList = cityDataModel.getData();
                        Log.d("TAG", "onResponse3: " + cityModelArrayList.get(0).getCity());
                        sp_city.setAdapter(new AccountSaveDetailsFragment.CityAdapter(getContext(), cityModelArrayList));

                    }
                }
            }

            @Override
            public void onFailure(Call<CityDataModel> call, Throwable t) {
                Log.d("TAG", "onResponse3: Fail");

            }


        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ProfileActivity.profileDetailApiCall = "Yes";
        user_status(SharedPrefsManager.getInstance().getString(LOGIN_ID));

    }

    private void user_status(String user_id) {
        apiInterface.user_status(user_id).enqueue(new Callback<UserStatusModel>() {
            @Override
            public void onResponse(Call<UserStatusModel> call, Response<UserStatusModel> response) {
                if (response.isSuccessful()) {
                    UserStatusModel userStatusModel = response.body();
                    if (userStatusModel != null) {
                        String respons = userStatusModel.getResponse();
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

    private void update_userAboutUs(String userFname, String userLname, String dob, String gender,
                                    String motherTongue, String religion, String ethnicity, String height, String marital_status,
                                    String userId, String childern_status) {

        apiInterface.update_userAboutUs(userFname, userLname, dob, gender, motherTongue, religion, ethnicity
                , height, marital_status, userId, childern_status).enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                if (response.isSuccessful()) {
                    UpdateProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        progress_bar.setVisibility(GONE);                        //String dob = year+"/"+month+"/"+day;
                        boolean respons = profileModel.isResponse();
                        if (respons) {
//                            Toast.makeText(EditProfileActivity.this, profileModel.getMessage(), Toast.LENGTH_SHORT).show();
                            profileDetail(SharedPrefsManager.getInstance().getString(LOGIN_ID));
                        } else {
                            Toast.makeText(getContext(), profileModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);                        //String dob = year+"/"+month+"/"+day;

            }
        });


    }

    private void profileDetail(String userId) {
        apiInterface.profileDetail(userId).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.isSuccessful()) {
                    ProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        boolean respons = profileModel.isResponse();
                        //  String message = loginModel.getMessage();
                        if (respons) {
                            ProfileModel.ProfileData profileData = profileModel.getProfileData();
                            String editStatus = profileData.getEdit_status();
                            if (!editStatus.equals("Yes")) {

                             /*   SharedPrefsManager.getInstance().clearPrefs();
                                Intent intent = new Intent(EditProfileActivity.this, EditProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                               // finish();*/
                                afterregisContain();
                            }

                        } else {
                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
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
//                startActivity(new Intent(EditProfileActivity.this, .class));
//                finish();
        });

        dialog.show();
    }

    private void updateProfileImage(String imageno, String user_id) {

        progress_bar.setVisibility(VISIBLE);
//        MultipartBody.Part imagePart;
//
//        if (imagePath == null || imagePath.isEmpty()) {
//            imagePart = MultipartBody.Part.createFormData("image", "");
//        } else {
//            File file = new File(imagePath);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
//        }


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

        MultipartBody.Part imagenoPart = MultipartBody.Part.createFormData("imageno", imageno);
        MultipartBody.Part user_idPart = MultipartBody.Part.createFormData("user_id", user_id);

        apiInterface.updateProfileImage(imagenoPart, user_idPart, imagePart).enqueue(new Callback<ProfileImageModel>() {
            @Override
            public void onResponse(Call<ProfileImageModel> call, Response<ProfileImageModel> response) {
                if (response.isSuccessful()) {
                    ProfileImageModel profileModel = response.body();
                    if (profileModel != null) {
                        progress_bar.setVisibility(GONE);
                        String respons = profileModel.getResponse();
                        Log.i("respons...!", String.valueOf(respons));
                        if (respons.equals("true")) {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
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

    private Boolean validationSuccess() {


        if (et_firstName.getText().toString().length() == 0) {
            et_firstName.setError("Please enter First Name !");
            et_firstName.requestFocus();
            return false;
        }


        return true;
    }


    private Boolean validationSuccessP() {
        if (et_phone.getText().toString().length() == 0) {
            et_phone.setError("Please enter phone number !");
            et_phone.requestFocus();
            return false;
        }
        if (et_email.getText().toString().length() == 0) {
            et_email.setError("Please enter email id !");
            et_email.requestFocus();
            return false;
        }

        if (stateName != null) {

        } else {
            Toast.makeText(getContext(), "Please Select State", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cityName != null) {

        } else {
            Toast.makeText(getContext(), "Please Select City", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (et_description.getText().toString().length() < 10) {
            //  et_description.setError("Please enter bio minimum 10 characters and maximum 50 !");
            et_description.setError("Please enter bio minimum 10 characters !");
            return false;
        }

        return true;
    }

    private Boolean validationSuccessPro() {

/*
        if (et_income.getText().toString().length() == 0) {
            et_income.setError("Please enter income !");
            et_income.requestFocus();
            return false;
        }
*/
        if (et_bio.getText().toString().length() < 30 || et_bio.getText().toString().length() > 150) {
            et_bio.setError("Please enter bio minimum 30 characters and maximum 150 !");

            return false;
        }


        return true;
    }

    private void update_userpersonhal(String hobbies, String interest, String country, String state, String city,
                                      String phone_number, String email, String user_id, String city_name, String PartnerExpectations) {

        Log.i("rhl...upd", interest + "  C " + country + "  " + state + city + "  " + phone_number + "  " + email + "  " + user_id + "  " + city_name);
        apiInterface.update_userpersonhal(hobbies, interest, country, state, city, phone_number, email, user_id, city_name, PartnerExpectations).enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                if (response.isSuccessful()) {
                    UpdateProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        progress_bar.setVisibility(GONE);                        //String dob = year+"/"+month+"/"+day;
                        boolean respons = profileModel.isResponse();
                        //  String message = loginModel.getMessage();
                        Log.i("rhl....R", String.valueOf(respons));
                        if (respons) {
//                            Toast.makeText(EditProfileActivity.this, profileModel.getMessage(), Toast.LENGTH_SHORT).show();
                            profileDetail(SharedPrefsManager.getInstance().getString(LOGIN_ID));

                        } else {
                            Toast.makeText(getContext(), profileModel.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);                        //String dob = year+"/"+month+"/"+day;
            }
        });
    }

    private void updateprofessional_details(String education, String profession, String income, String bio, String user_id) {

        apiInterface.updateprofessional_details(education, profession, income, bio, user_id).enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                if (response.isSuccessful()) {
                    UpdateProfileModel profileModel = response.body();
                    if (profileModel != null) {
                        progress_bar.setVisibility(GONE);                        //String dob = year+"/"+month+"/"+day;
                        boolean respons = profileModel.isResponse();
                        //  String message = loginModel.getMessage();
                        Log.i("rhl....R", String.valueOf(respons));
                        if (respons) {
//                            Toast.makeText(EditProfileActivity.this, profileModel.getMessage(), Toast.LENGTH_SHORT).show();
                            profileDetail(SharedPrefsManager.getInstance().getString(LOGIN_ID));

                        } else {
                            Toast.makeText(getContext(), profileModel.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(GONE);                        //String dob = year+"/"+month+"/"+day;
            }
        });
    }


    private void MotherToungueApi() {
        apiInterface.get_mother_tongue().enqueue(new Callback<MotherToungueDataModel>() {
            @Override
            public void onResponse(Call<MotherToungueDataModel> call, Response<MotherToungueDataModel> response) {
                MotherToungueDataModel motherToungueDataModel = response.body();
                if (motherToungueDataModel != null) {
                    if (motherToungueDataModel.getResponse()) {
                        motherToungueDataArrayList = motherToungueDataModel.getData();
                        Log.d("TAG", "onResponse: " + motherToungueDataArrayList.get(0).getMotherTongue());
                        sp_motherToung.setAdapter(new AccountSaveDetailsFragment.MOtherToungAdapter(getContext(), motherToungueDataArrayList));
                    }
                }
            }

            @Override
            public void onFailure(Call<MotherToungueDataModel> call, Throwable t) {

            }
        });
    }

    public class MOtherToungAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;
        private List<MotherToungueDataModel.MotherToungueData> spinnerList;

        public MOtherToungAdapter(Context context, List<MotherToungueDataModel.MotherToungueData> motherToungueDataList) {
            layoutInflater = LayoutInflater.from(context);
            this.spinnerList = motherToungueDataList;
        }

        @Override
        public int getCount() {
            return null == spinnerList ? 0 : spinnerList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(R.layout.item_spinner, parent, false);
            TextView stateTxt = convertView.findViewById(R.id.spinner_text_view);
            stateTxt.setText(spinnerList.get(position).getMotherTongue());
            return convertView;
        }
    }

    public class CountryAdapter extends BaseAdapter {
        LayoutInflater inflator;
        List<CountryDataModel.DataBean> spinnerArrayList;


        public CountryAdapter(Context context, List<CountryDataModel.DataBean> spinnerArrayList) {
            inflator = LayoutInflater.from(context);
            this.spinnerArrayList = spinnerArrayList;
        }

        @Override
        public int getCount() {
            return spinnerArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflator.inflate(R.layout.item_spinner, null);
            TextView deposit_channerl_ = (TextView) convertView.findViewById(R.id.spinner_text_view);
            deposit_channerl_.setText(spinnerArrayList.get(position).getCountry());
            return convertView;
        }
    }

    public class StateAdapter extends BaseAdapter {
        LayoutInflater inflator;
        List<StateDataModel.DataEntity> spinnerArrayList;

        public StateAdapter(Context context, List<StateDataModel.DataEntity> spinnerArrayList) {
            inflator = LayoutInflater.from(context);
            this.spinnerArrayList = spinnerArrayList;
        }

        @Override
        public int getCount() {
            return spinnerArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflator.inflate(R.layout.item_spinner, null);
            TextView deposit_channerl_ = (TextView) convertView.findViewById(R.id.spinner_text_view);
            deposit_channerl_.setText(spinnerArrayList.get(position).getState());
            return convertView;
        }
    }

    public class CityAdapter extends BaseAdapter {
        LayoutInflater inflator;
        List<CityDataModel.DataCity> spinnerArrayList;

        public CityAdapter(Context context, List<CityDataModel.DataCity> spinnerArrayList) {
            inflator = LayoutInflater.from(context);
            this.spinnerArrayList = spinnerArrayList;
        }

        @Override
        public int getCount() {
            return spinnerArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflator.inflate(R.layout.item_spinner, null);
            TextView deposit_channerl_ = (TextView) convertView.findViewById(R.id.spinner_text_view);
            deposit_channerl_.setText(spinnerArrayList.get(position).getCity());
            return convertView;
        }
    }


    public class SpinnerAdapter extends BaseAdapter{
        private final LayoutInflater layoutInflater;
        private List<String> spinnerList;

        public SpinnerAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return null == spinnerList ? 0 : spinnerList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void addTaxRateList(List<String> spinnerList) {
            this.spinnerList = spinnerList;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(R.layout.item_spinner, parent, false);
            TextView stateTxt = convertView.findViewById(R.id.spinner_text_view);
            stateTxt.setText(spinnerList.get(position));
            return convertView;
        }
    }
}
