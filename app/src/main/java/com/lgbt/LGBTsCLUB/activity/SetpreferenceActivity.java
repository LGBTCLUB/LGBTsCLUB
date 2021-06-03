package com.lgbt.LGBTsCLUB.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lgbt.LGBTsCLUB.MainActivity;
import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.model.CityDataModel;
import com.lgbt.LGBTsCLUB.model.EducationDataModel;
import com.lgbt.LGBTsCLUB.model.LoginModel;
import com.lgbt.LGBTsCLUB.model.OccupationDataModel;
import com.lgbt.LGBTsCLUB.model.StateDataModel;
import com.lgbt.LGBTsCLUB.model.usermodel.CountryModel;
import com.lgbt.LGBTsCLUB.model.usermodel.EducationModel;
import com.lgbt.LGBTsCLUB.model.usermodel.ProfessionModel;
import com.lgbt.LGBTsCLUB.network.UtilsMethod;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;
import com.lgbt.LGBTsCLUB.network.networking.CountryDataModel;
import com.lgbt.LGBTsCLUB.network.networking.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lgbt.LGBTsCLUB.network.networking.Constant.LOGIN_ID;

public class SetpreferenceActivity extends AppCompatActivity {

    Button btn_preference;
    ImageView iv_back;
    Spinner sp_minAge, sp_maxAge, sp_minHight, sp_maxHight, sp_gender, sp_education, sp_profession, sp_country, sp_specialCases;

    EditText et_annualIncome;
    List<String> genderlist = new ArrayList<>();
    List<String> agelist = new ArrayList<>();
    List<String> agelistFrom = new ArrayList<>();
    List<String> heightfromlist = new ArrayList<>();
    List<String> heighttolist = new ArrayList<>();
    List<String> specialcaselist = new ArrayList<>();
    EditText et_description;
    String Gender, minAge, minAgeName, maxAge, msxageName, minHightName, minHight, maxHightName, maxHight, specialCases,
            educationName, educationID, professionID, professionName, countryName, countryId;
    private SpinnerAdapter genderAdapter;
    String country_id, stateId, stateName, cityId, cityName;
    private SpinnerAdapter ageAdapter;
    private SpinnerAdapter ageFromAdapter;
    private SpinnerAdapter fromheightAdapter;
    private SpinnerAdapter toheightAdapter;
    private SpinnerAdapter specialcaseAdapter;
    private ApiInterface apiInterface;
    private UtilsMethod progress;
    private List<EducationDataModel.EducationData> educationDataArrayList;
    private List<OccupationDataModel.OccupationData> occupationDataArrayList;
    private List<CountryDataModel.DataBean> countryModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpreference);

        btn_preference = findViewById(R.id.btn_preference);
        iv_back = findViewById(R.id.iv_back);

        sp_gender = findViewById(R.id.sp_gender);

        sp_minAge = findViewById(R.id.sp_minAge);
        sp_maxAge = findViewById(R.id.sp_maxAge);

        sp_minHight = findViewById(R.id.sp_minHight);
        sp_maxHight = findViewById(R.id.sp_maxHight);

        sp_education = findViewById(R.id.sp_education);
        sp_profession = findViewById(R.id.sp_profession);
        sp_country = findViewById(R.id.sp_country);
        sp_specialCases = findViewById(R.id.sp_specialCases);

        et_annualIncome = findViewById(R.id.et_annualIncome);
        et_description = findViewById(R.id.et_description);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);

        educationDataArrayList = new ArrayList<>();
        occupationDataArrayList = new ArrayList<>();
        countryModelArrayList = new ArrayList<>();


        EducationApi();
        OccupationApi();
        CountryApi();
        init();

        btn_preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validationSuccess(Gender, minAgeName, msxageName, educationName, professionName)) {
                    setpreferenceApi(Gender, minAgeName, msxageName, "1", "1", "20000",
                            educationName, professionName, countryName, specialCases, SharedPrefsManager.getInstance().getString(LOGIN_ID), et_description.getText().toString());
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogeback();
            }
        });


        sp_education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (educationDataArrayList.size() > 0) {
                    educationID = educationDataArrayList.get(position).getEducationId();
                    educationName = educationDataArrayList.get(position).getEducation();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (occupationDataArrayList.size() > 0) {
                    professionID = occupationDataArrayList.get(position).getOccupationId();
                    professionName = occupationDataArrayList.get(position).getOccupation();
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
                    Toast.makeText(SetpreferenceActivity.this, "" + country_id, Toast.LENGTH_SHORT).show();

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
                        sp_country.setAdapter(new CountryAdapter(SetpreferenceActivity.this, countryModelArrayList));
                      //  StateApi(countryModelArrayList.get(0).getCountryId());
                    }
                }
            }

            @Override
            public void onFailure(Call<CountryDataModel> call, Throwable t) {
                Log.d("TAG", "onResponse: Fail");

            }
        });
    }


    private void OccupationApi() {
        apiInterface.get_occupation().enqueue(new Callback<OccupationDataModel>() {
            @Override
            public void onResponse(Call<OccupationDataModel> call, retrofit2.Response<OccupationDataModel> response) {
                OccupationDataModel occupationDataModel = response.body();
                if (occupationDataModel != null) {
                    if (occupationDataModel.getResponse()) {
                        occupationDataArrayList = occupationDataModel.getData();
                        Log.d("TAG", "onResponse5: " + occupationDataArrayList.get(0).getOccupation());
                        sp_profession.setAdapter(new OccupationAdapter(SetpreferenceActivity.this, occupationDataArrayList));

                    }

                }
            }

            @Override
            public void onFailure(Call<OccupationDataModel> call, Throwable t) {

            }
        });

    }

    private void EducationApi() {
        apiInterface.get_education().enqueue(new Callback<EducationDataModel>() {
            @Override
            public void onResponse(Call<EducationDataModel> call, retrofit2.Response<EducationDataModel> response) {
                EducationDataModel educationDataModel = response.body();
                if (educationDataModel != null) {
                    if (educationDataModel.getResponse()) {
                        educationDataArrayList = educationDataModel.getData();
                        Log.d("TAG", "onResponse4: " + educationDataArrayList.get(0).getEducation());
                        sp_education.setAdapter(new EducationAdapter(SetpreferenceActivity.this, educationDataArrayList));
                    }
                }
            }

            @Override
            public void onFailure(Call<EducationDataModel> call, Throwable t) {

            }
        });
    }

    private void dialogeback() {
        final Dialog dialog = new Dialog(SetpreferenceActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.back_page_dialogbox);


        Button ok = dialog.findViewById(R.id.ok);
        Button no = dialog.findViewById(R.id.no);
        final EditText text_dialog = dialog.findViewById(R.id.text_dialog);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void initFemale() {
        ageAdapter = new SpinnerAdapter(this);
        sp_minAge.setAdapter(ageAdapter);
        agelist.add("Select");
        agelist.add("18 Years");
        agelist.add("19 Years");
        agelist.add("20 Years");
        agelist.add("21 Years");
        agelist.add("22 Years");
        agelist.add("23 Years");
        agelist.add("24 Years");
        agelist.add("25 Years");
        agelist.add("26 Years");
        agelist.add("27 Years");
        agelist.add("28 Years");
        agelist.add("29 Years");
        agelist.add("30 Years");
        agelist.add("31 Years");
        agelist.add("32 Years");
        agelist.add("33 Years");
        agelist.add("34 Years");
        agelist.add("35 Years");
        agelist.add("36 Years");
        agelist.add("37 Years");
        agelist.add("38 Years");
        agelist.add("39 Years");
        agelist.add("40 Years");
        agelist.add("41 Years");
        agelist.add("42 Years");
        agelist.add("43 Years");
        agelist.add("44 Years");
        agelist.add("45 Years");
        agelist.add("46 Years");
        agelist.add("47 Years");
        agelist.add("48 Years");
        agelist.add("49 Years");
        agelist.add("50 Years");
        agelist.add("51 Years");
        agelist.add("52 Years");
        agelist.add("53 Years");
        agelist.add("54 Years");
        agelist.add("55 Years");
        agelist.add("56 Years");
        agelist.add("57 Years");
        agelist.add("58 Years");
        agelist.add("59 Years");
        agelist.add("60 Years");
        ageAdapter.addTaxRateList(agelist);
        sp_minAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (agelist.size() > 0) {
                    minAgeName = agelist.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initMale() {
        ageAdapter = new SpinnerAdapter(this);
        sp_minAge.setAdapter(ageAdapter);
        agelist.add("Select");
        agelist.add("21 Years");
        agelist.add("22 Years");
        agelist.add("23 Years");
        agelist.add("24 Years");
        agelist.add("25 Years");
        agelist.add("26 Years");
        agelist.add("27 Years");
        agelist.add("28 Years");
        agelist.add("29 Years");
        agelist.add("30 Years");
        agelist.add("31 Years");
        agelist.add("32 Years");
        agelist.add("33 Years");
        agelist.add("34 Years");
        agelist.add("35 Years");
        agelist.add("36 Years");
        agelist.add("37 Years");
        agelist.add("38 Years");
        agelist.add("39 Years");
        agelist.add("40 Years");
        agelist.add("41 Years");
        agelist.add("42 Years");
        agelist.add("43 Years");
        agelist.add("44 Years");
        agelist.add("45 Years");
        agelist.add("46 Years");
        agelist.add("47 Years");
        agelist.add("48 Years");
        agelist.add("49 Years");
        agelist.add("50 Years");
        agelist.add("51 Years");
        agelist.add("52 Years");
        agelist.add("53 Years");
        agelist.add("54 Years");
        agelist.add("55 Years");
        agelist.add("56 Years");
        agelist.add("57 Years");
        agelist.add("58 Years");
        agelist.add("59 Years");
        agelist.add("60 Years");
        ageAdapter.addTaxRateList(agelist);
        sp_minAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (agelist.size() > 0) {
                    minAgeName = agelist.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init() {

        //----------------------------Gender---------------------------------------------
        genderAdapter = new SpinnerAdapter(this);
        sp_gender.setAdapter(genderAdapter);
        genderlist.add("Select");
        genderlist.add("Male");
        genderlist.add("Female");
        genderlist.add("Transgender");

        genderAdapter.addTaxRateList(genderlist);
        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (genderlist.size() > 0) {
                    Gender = genderlist.get(position);
                    int position1 = sp_gender.getSelectedItemPosition();
                    agelist.clear();
                    if (Gender.equals("Female")) {
                        initFemale();
                    } else {
                        initMale();
                    }
                    ageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //---------------------------Age From--------------------------------------------

        ageAdapter = new SpinnerAdapter(this);
        sp_minAge.setAdapter(ageAdapter);
        agelist.add("Select");
        agelist.add("18 Years");
        agelist.add("19 Years");
        agelist.add("20 Years");
        agelist.add("21 Years");
        agelist.add("22 Years");
        agelist.add("23 Years");
        agelist.add("24 Years");
        agelist.add("25 Years");
        agelist.add("26 Years");
        agelist.add("27 Years");
        agelist.add("28 Years");
        agelist.add("29 Years");
        agelist.add("30 Years");
        agelist.add("31 Years");
        agelist.add("32 Years");
        agelist.add("33 Years");
        agelist.add("34 Years");
        agelist.add("35 Years");
        agelist.add("36 Years");
        agelist.add("37 Years");
        agelist.add("38 Years");
        agelist.add("39 Years");
        agelist.add("40 Years");
        agelist.add("41 Years");
        agelist.add("42 Years");
        agelist.add("43 Years");
        agelist.add("44 Years");
        agelist.add("45 Years");
        agelist.add("46 Years");
        agelist.add("47 Years");
        agelist.add("48 Years");
        agelist.add("49 Years");
        agelist.add("50 Years");
        agelist.add("51 Years");
        agelist.add("52 Years");
        agelist.add("53 Years");
        agelist.add("54 Years");
        agelist.add("55 Years");
        agelist.add("56 Years");
        agelist.add("57 Years");
        agelist.add("58 Years");
        agelist.add("59 Years");
        agelist.add("60 Years");
        ageAdapter.addTaxRateList(agelist);
        sp_minAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (agelist.size() > 0) {
                    minAgeName = agelist.get(position);
                    //int position1 = sp_minAge.getSelectedItemPosition();
                    // minAge = String.valueOf(position1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //---------------------------Age to--------------------------------------------
        ageFromAdapter = new SpinnerAdapter(this);
        sp_maxAge.setAdapter(ageFromAdapter);
        agelistFrom.add("Select");
        agelistFrom.add("30 Years");
        agelistFrom.add("31 Years");
        agelistFrom.add("32 Years");
        agelistFrom.add("33 Years");
        agelistFrom.add("34 Years");
        agelistFrom.add("35 Years");
        agelistFrom.add("36 Years");
        agelistFrom.add("37 Years");
        agelistFrom.add("38 Years");
        agelistFrom.add("39 Years");
        agelistFrom.add("40 Years");
        agelistFrom.add("41 Years");
        agelistFrom.add("42 Years");
        agelistFrom.add("43 Years");
        agelistFrom.add("44 Years");
        agelistFrom.add("45 Years");
        agelistFrom.add("46 Years");
        agelistFrom.add("47 Years");
        agelistFrom.add("48 Years");
        agelistFrom.add("49 Years");
        agelistFrom.add("50 Years");
        agelistFrom.add("51 Years");
        agelistFrom.add("52 Years");
        agelistFrom.add("53 Years");
        agelistFrom.add("54 Years");
        agelistFrom.add("55 Years");
        agelistFrom.add("56 Years");
        agelistFrom.add("57 Years");
        agelistFrom.add("58 Years");
        agelistFrom.add("59 Years");
        agelistFrom.add("60 Years");
        ageFromAdapter.addTaxRateList(agelistFrom);
        sp_maxAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (agelistFrom.size() > 0) {
                    // int position1 = sp_maxAge.getSelectedItemPosition();
                    msxageName = agelistFrom.get(position);
                    // maxAge = String.valueOf(position1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //-------------------------------Height from-----------------------------------------------
        fromheightAdapter = new SpinnerAdapter(this);

        sp_minHight.setAdapter(fromheightAdapter);
        heightfromlist.add("4ft (121cm)");
        heightfromlist.add("4ft 1in (124cm)");
        heightfromlist.add("4ft 2in (127cm)");
        heightfromlist.add("4ft 3in (129cm) ");
        heightfromlist.add("4ft 4in (132cm)");
        heightfromlist.add("4ft 5in (134cm)");
        heightfromlist.add("4ft 6in (137cm)");
        heightfromlist.add("4ft 7in (139cm) ");
        heightfromlist.add("4ft 8in (142cm)");
        heightfromlist.add("4ft 9in (144cm)");
        heightfromlist.add("4ft 10in (147cm)");
        heightfromlist.add("4ft 11in (149cm) ");
        heightfromlist.add("5ft (152cm)");
        heightfromlist.add("5ft 1in (154cm)");
        heightfromlist.add("5ft 2in (157cm)");
        heightfromlist.add("5ft 3in (160cm) ");
        heightfromlist.add("5ft 4in (162cm)");
        heightfromlist.add("5ft 5in (165cm)");
        heightfromlist.add("5ft 6in (167cm)");
        heightfromlist.add("5ft 7in (170cm) ");
        heightfromlist.add("5ft 8in (172cm)");
        heightfromlist.add("5ft 9in (175cm)");
        heightfromlist.add("5ft 10in (177cm)");
        heightfromlist.add("5ft 11in (180cm) ");
        heightfromlist.add("6ft  (182cm)");
        heightfromlist.add("6ft 1in (185cm)");
        heightfromlist.add("6ft 2in (187cm)");
        heightfromlist.add("6ft 3in (190cm) ");
        heightfromlist.add("6ft 4in (193cm)");
        heightfromlist.add("6ft 5in (195cm)");
        heightfromlist.add("6ft 6in (198cm)");
        heightfromlist.add("6ft 7in (200cm) ");
        heightfromlist.add("6ft 8in (203cm)");
        heightfromlist.add("6ft 9in (205cm)");
        heightfromlist.add("6ft 10in (208cm)");
        heightfromlist.add("6ft 11in (210cm) ");
        heightfromlist.add("7ft  (213cm)");
        fromheightAdapter.addTaxRateList(heightfromlist);

        sp_minHight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (heightfromlist.size() > 0) {
                    minHightName = heightfromlist.get(position);
                    int position1 = sp_minHight.getSelectedItemPosition();
                    minHight = String.valueOf(position1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//------------------------------------Height To-----------------------------------------
        toheightAdapter = new SpinnerAdapter(this);
        sp_maxHight.setAdapter(toheightAdapter);
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
        sp_maxHight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (heighttolist.size() > 0) {
                    int position1 = sp_maxHight.getSelectedItemPosition();
                    maxHight = String.valueOf(position1);
                    maxHightName = heightfromlist.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //----------------------------Gender---------------------------------------------

        specialcaseAdapter = new SpinnerAdapter(this);
        sp_specialCases.setAdapter(specialcaseAdapter);
        specialcaselist.add("No");
        specialcaselist.add("Physically challenged");

        specialcaseAdapter.addTaxRateList(specialcaselist);
        sp_specialCases.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (specialcaselist.size() > 0) {
                    // Gender = specialcaselist.get(position);
                    int position1 = sp_specialCases.getSelectedItemPosition();
                    // specialCases = String.valueOf(position1);
                    specialCases = specialcaselist.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void setpreferenceApi(String show_gender, String min_age, String max_age, String min_height, String max_height, String income
            , String education, String profession, String location, String specialCase, String userId, String PartnerExpectations) {

        apiInterface.setPartnerPrefrence(show_gender, min_age, max_age, min_height, max_height, income, education
                , profession, location, specialCase, userId, PartnerExpectations).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();
                    if (loginModel != null) {
                        progress.cancleDialog();
                        boolean respons = loginModel.isResponse();
                        if (respons) {
                            Intent intent = new Intent(SetpreferenceActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(SetpreferenceActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();
            }
        });
    }


    // ---------------------Education Adapter ------------------------------

    private Boolean validationSuccess(String gender, String minAge, String maxAge, String educationName, String professionName) {
        if (gender.equals("Select")) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        } else if (minAge.equals("Select")) {
            Toast.makeText(this, "Please select minimum age", Toast.LENGTH_SHORT).show();
            return false;
        } else if (maxAge.equals("Select")) {
            Toast.makeText(this, "Please select maximum age", Toast.LENGTH_SHORT).show();
            return false;
        } else if (educationName.equals("Education")) {
            Toast.makeText(this, "Please select Education", Toast.LENGTH_SHORT).show();
            return false;
        } else if (professionName.equals("Profession")) {
            Toast.makeText(this, "Please select Profession", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_description.getText().toString().length() < 10 || et_description.getText().toString().length() > 50) {
            et_description.setError("Please enter bio minimum 10 characters and maximum 50!");
            et_description.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    //------------------------------ProfessionAdapter-----------------------

    public class SpinnerAdapter extends BaseAdapter {

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

    //--------------------CountryAdapter-----------------------------

    public class EducationAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;
        private List<EducationDataModel.EducationData> spinnerList;

        public EducationAdapter(Context context, List<EducationDataModel.EducationData> educationDataArrayList) {
            layoutInflater = LayoutInflater.from(context);
            this.spinnerList = educationDataArrayList;
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
            stateTxt.setText(spinnerList.get(position).getEducation());
            return convertView;
        }
    }

    public class OccupationAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;
        private List<OccupationDataModel.OccupationData> spinnerList;

        public OccupationAdapter(Context context, List<OccupationDataModel.OccupationData> occupationDataArrayList) {
            layoutInflater = LayoutInflater.from(context);
            this.spinnerList = occupationDataArrayList;
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

//        public void addTaxRateList(List<String> spinnerList) {
//            this.spinnerList = spinnerList;
//            notifyDataSetChanged();
//        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(R.layout.item_spinner, parent, false);
            TextView stateTxt = convertView.findViewById(R.id.spinner_text_view);
            stateTxt.setText(spinnerList.get(position).getOccupation());
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


}
