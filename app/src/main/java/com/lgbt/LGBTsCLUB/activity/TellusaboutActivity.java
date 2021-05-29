package com.lgbt.LGBTsCLUB.activity;

import android.annotation.SuppressLint;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.model.CityDataModel;
import com.lgbt.LGBTsCLUB.model.EducationDataModel;
import com.lgbt.LGBTsCLUB.model.LoginModel;
import com.lgbt.LGBTsCLUB.model.MotherToungueDataModel;
import com.lgbt.LGBTsCLUB.model.OccupationDataModel;
import com.lgbt.LGBTsCLUB.model.StateDataModel;
import com.lgbt.LGBTsCLUB.model.serachmodel.SpecialSearchModel;
import com.lgbt.LGBTsCLUB.model.usermodel.CityModel;
import com.lgbt.LGBTsCLUB.model.usermodel.CountryModel;
import com.lgbt.LGBTsCLUB.model.usermodel.EducationModel;
import com.lgbt.LGBTsCLUB.model.usermodel.MotherToungModel;
import com.lgbt.LGBTsCLUB.model.usermodel.ProfessionModel;
import com.lgbt.LGBTsCLUB.model.usermodel.ReligiousModel;
import com.lgbt.LGBTsCLUB.model.usermodel.StateModel;
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

public class TellusaboutActivity extends AppCompatActivity {

    Button btn_getStart;
    ImageView iv_back;

    Spinner sp_feet, sp_inch, sp_material, sp_education, sp_religon, sp_motherToung, sp_profession,
            sp_country, sp_state, sp_city;
    String MotherToung;

    List<String> materiallist = new ArrayList<>();
    List<String> heightfromlist = new ArrayList<>();
    List<String> heighttolist = new ArrayList<>();
    List<String> educationlist = new ArrayList<>();
    String mstatus, matstatus, hightF, motherToungID, motherToungName,
            educationName, educationID, professionName, professionID, religiousIDName, religiousID;
    EditText et_annualIncome, et_zipcode, et_nationality, et_bio;
    RadioButton rb_typeyes, rb_typeno;
    String childrenlivingstatus = "No";
    RadioGroup radioGroup;
    private SpinnerAdapter materialAdapter;
    private SpinnerAdapter fromheightAdapter;
    private SpinnerAdapter toheightAdapter;
    private SpinnerAdapter educationAdapter;
    private ArrayList<MotherToungModel> motherToungModelArrayList;
    private ArrayList<ReligiousModel> religiousModelArrayList;
    private List<EducationDataModel.EducationData> educationDataArrayList;
    private List<OccupationDataModel.OccupationData> occupationDataArrayList;
    private List<CountryDataModel.DataBean> countryModelArrayList;
    private List<StateDataModel.DataEntity> stateModelArrayList;
    private List<CityDataModel.DataCity> cityModelArrayList;

    private List<SpecialSearchModel.SpecialData> specialDataArrayList;
    private List<MotherToungueDataModel.MotherToungueData> motherToungueDataArrayList;
    String country_id, countryName, countryId, stateId, stateName, cityId, cityName;

    private ApiInterface apiInterface;
    private UtilsMethod progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tellusabout);

        btn_getStart = findViewById(R.id.btn_getStart);
        iv_back = findViewById(R.id.iv_back);
        sp_feet = findViewById(R.id.sp_feet);
        sp_inch = findViewById(R.id.sp_inch);
        sp_material = findViewById(R.id.sp_material);
        sp_education = findViewById(R.id.sp_education);
        sp_religon = findViewById(R.id.sp_religon);
        sp_motherToung = findViewById(R.id.sp_motherToung);
        sp_profession = findViewById(R.id.sp_profession);
        sp_country = findViewById(R.id.sp_country);
        sp_state = findViewById(R.id.sp_state);
        sp_city = findViewById(R.id.sp_city);
        et_bio = findViewById(R.id.et_bio);

        et_annualIncome = findViewById(R.id.et_annualIncome);
        et_zipcode = findViewById(R.id.et_zipcode);
        et_nationality = findViewById(R.id.et_nationality);
        rb_typeyes = findViewById(R.id.rb_typeyes);
        rb_typeno = findViewById(R.id.rb_typeno);
        radioGroup = findViewById(R.id.radioGroup);

        apiInterface = ApiClient.getInterface();
        progress = new UtilsMethod(this);

        motherToungModelArrayList = new ArrayList<>();
        religiousModelArrayList = new ArrayList<>();
        educationDataArrayList = new ArrayList<>();
        occupationDataArrayList = new ArrayList<>();
        countryModelArrayList = new ArrayList<>();
        stateModelArrayList = new ArrayList<>();
        cityModelArrayList = new ArrayList<>();

        motherToungueDataArrayList = new ArrayList<>();


        ReligiousApi();
        MotherToungueApi();
        EducationApi();
        OccupationApi();
        CountryApi();
        init();

        btn_getStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validationSuccess()) {
                    tellusaboutApi(hightF, matstatus, religiousIDName, motherToungName, educationName, professionName, countryName, stateName, cityId,
                            SharedPrefsManager.getInstance().getString(LOGIN_ID), et_zipcode.getText().toString(), et_nationality.getText().toString()
                            , et_annualIncome.getText().toString(), childrenlivingstatus, et_bio.getText().toString());
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogeback();
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

        sp_education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (educationDataArrayList.size() > 0) {
                    educationID = educationDataArrayList.get(position).getEducation();
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

        sp_religon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);

                if (religiousModelArrayList.size() > 0) {
                    religiousID = religiousModelArrayList.get(position).getId();
                    religiousIDName = religiousModelArrayList.get(position).getName();
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
                    Toast.makeText(TellusaboutActivity.this, "" + country_id, Toast.LENGTH_SHORT).show();

                    if (country_id != null) {
                        StateApi(country_id);
                        //  CityApi(stateName);

                    }
                } else {
                    Toast.makeText(TellusaboutActivity.this, "" + countryModelArrayList.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);
                Log.d("TAG", "bandId: " + bandId);

                if (stateModelArrayList.size() > 0) {
                    stateId = stateModelArrayList.get(position).getStateId();
                    Log.d("TAG", "stateId: " + stateId);
                    stateName = stateModelArrayList.get(position).getState();
                    Toast.makeText(TellusaboutActivity.this, "" + stateName, Toast.LENGTH_SHORT).show();

                    if (stateName != null) {
                        CityApi(stateName);

                    }
                } else {
                    Toast.makeText(TellusaboutActivity.this, "" + stateModelArrayList.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);
                Log.d("TAG", "bandId: " + bandId);

                if (cityModelArrayList.size() > 0) {
                    cityId = cityModelArrayList.get(position).getId();
                    cityName = cityModelArrayList.get(position).getCity();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                        sp_country.setAdapter(new CountryAdapter(TellusaboutActivity.this, countryModelArrayList));
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
                        sp_state.setAdapter(new StateAdapter(TellusaboutActivity.this, stateModelArrayList));
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
                        sp_city.setAdapter(new CityAdapter(TellusaboutActivity.this, cityModelArrayList));

                    }
                }
            }

            @Override
            public void onFailure(Call<CityDataModel> call, Throwable t) {
                Log.d("TAG", "onResponse3: Fail");

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
                        sp_profession.setAdapter(new OccupationAdapter(TellusaboutActivity.this, occupationDataArrayList));

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
                        sp_education.setAdapter(new EducationAdapter(TellusaboutActivity.this, educationDataArrayList));
                    }
                }
            }

            @Override
            public void onFailure(Call<EducationDataModel> call, Throwable t) {

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
                        sp_motherToung.setAdapter(new MOtherToungAdapter(TellusaboutActivity.this, motherToungueDataArrayList));
                    }
                }
            }

            @Override
            public void onFailure(Call<MotherToungueDataModel> call, Throwable t) {

            }
        });
    }

    private void ReligiousApi() {

        apiInterface.get_religion().enqueue(new Callback<SpecialSearchModel>() {
            @Override
            public void onResponse(Call<SpecialSearchModel> call, Response<SpecialSearchModel> response) {
                SpecialSearchModel specialSearchModel = response.body();
                if (specialSearchModel != null) {
                    if (specialSearchModel.getResponse()) {
                        specialDataArrayList = specialSearchModel.getData();
                        Log.d("TAG", "onResponse6: " + specialDataArrayList.get(0).getName());
                        sp_religon.setAdapter(new SpecialCaseAdapter(TellusaboutActivity.this, specialDataArrayList));
                    }

                }
            }

            @Override
            public void onFailure(Call<SpecialSearchModel> call, Throwable t) {

            }
        });

    }

    private void dialogeback() {
        final Dialog dialog = new Dialog(TellusaboutActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.back_page_dialogbox);

        Button ok = dialog.findViewById(R.id.ok);
        Button no = dialog.findViewById(R.id.no);
        final EditText text_dialog = dialog.findViewById(R.id.text_dialog);

        no.setOnClickListener(v -> dialog.dismiss());

        ok.setOnClickListener(v -> {
            finish();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void init() {

        //--------------------------Material----------------------------
        materialAdapter = new SpinnerAdapter(this);
        sp_material.setAdapter(materialAdapter);
        materiallist.add("Select");
        materiallist.add("Married awaiting divorce");
        materiallist.add("Divorce");
        materiallist.add("Never been married");

        materialAdapter.addTaxRateList(materiallist);
        sp_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (materiallist.size() > 0) {
                    int position1 = sp_material.getSelectedItemPosition();
                    matstatus = materiallist.get(position);

                    if (position1 == 0) {
                        mstatus = "1";
                    } else if (position1 == 1) {
                        mstatus = "2";
                    } else if (position1 == 2) {
                        mstatus = "3";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //-------------------------------Height from-----------------------------------------------
        fromheightAdapter = new SpinnerAdapter(this);
        sp_feet.setAdapter(fromheightAdapter);
        heightfromlist.add("4ft  (121cm)");
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
        sp_feet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (heightfromlist.size() > 0) {
                    int position1 = sp_feet.getSelectedItemPosition();
                    hightF = String.valueOf(position1);
                    //  hightF = heightfromlist.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//------------------------------------Height To-----------------------------------------
        toheightAdapter = new SpinnerAdapter(this);
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    //---------------Base Spinner Adapter-------------

    private void tellusaboutApi(String height, String meritalStatus, String religion, String motherTongue, String education, String profession
            , String country, String state, String city, String userId, String zipcode, String nationality, String income
            , String childrenlivingstatus, String ShortBio) {

        apiInterface.tellusAbout(height, meritalStatus, religion, motherTongue, education, profession, country
                , state, city, userId, zipcode, nationality, income, childrenlivingstatus, ShortBio).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel loginModel = response.body();
                    if (loginModel != null) {
                        progress.cancleDialog();
                        boolean respons = loginModel.isResponse();
                        //  String message = loginModel.getMessage();
                        if (respons) {
                            //  Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(TellusaboutActivity.this, SetpreferenceActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(TellusaboutActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress.cancleDialog();

            }

        });

    }

    private Boolean validationSuccess() {

        if (et_nationality.getText().toString().length() == 0) {
            et_nationality.setError("Please enter nationality !");
            et_nationality.requestFocus();
            return false;
        }
        if (et_zipcode.getText().toString().length() == 0) {
            et_zipcode.setError("Please enter zip code !");
            et_zipcode.requestFocus();
            return false;
        }


        if (matstatus.equals("Select")) {
            Toast.makeText(this, "Please select Marital Status", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (motherToungName.equals("Mother Tounge")) {
            Toast.makeText(this, "Please select mother tounge", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (religiousIDName.equals("Religious")) {
            Toast.makeText(this, "Please select Religious", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (educationName.equals("Education")) {
            Toast.makeText(this, "Please select Education", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (professionName.equals("Profession")) {
            Toast.makeText(this, "Please select Profession", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (stateName == null) {
            Toast.makeText(this, "Please select State", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cityName == null) {
            Toast.makeText(this, "Please select City", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (et_annualIncome.getText().toString().length() == 0) {
            et_annualIncome.setError("Please enter Annual Income!");
            et_annualIncome.requestFocus();
            return false;
        }
        if (et_bio.getText().toString().length() < 30 || et_bio.getText().toString().length() > 150) {
            et_bio.setError("Please enter bio minimum 30 characters and maximum 150!");
            return false;
        }

        return true;
    }

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

    // ---------------------Education Adapter ------------------------------

    //-------------------Religious---------------------
    public class SpecialCaseAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;
        private List<SpecialSearchModel.SpecialData> spinnerList;

        public SpecialCaseAdapter(Context context, List<SpecialSearchModel.SpecialData> specialDataArrayList) {
            layoutInflater = LayoutInflater.from(context);
            this.spinnerList = specialDataArrayList;
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
            stateTxt.setText(spinnerList.get(position).getName());
            return convertView;
        }
    }


    //------------------------------ProfessionAdapter-----------------------

    // -------------------MotherToungAdapter----------------
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

    //----------------------------satate code--------------------------------
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
}
