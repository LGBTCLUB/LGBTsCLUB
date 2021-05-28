package com.lgbt.LGBTsCLUB.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.model.CityDataModel;
import com.lgbt.LGBTsCLUB.model.EducationDataModel;
import com.lgbt.LGBTsCLUB.model.StateDataModel;
import com.lgbt.LGBTsCLUB.model.serachmodel.OccupicationModel;
import com.lgbt.LGBTsCLUB.model.serachmodel.SpecialSearchModel;
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

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner sp_occupation, sp_gender, sp_sexual_orientation, sp_country, sp_state, sp_city, sp_education, sp_special_case;

    List<String> occupation_list = new ArrayList<>();
    List<String> gender_list = new ArrayList<>();
    List<String> sexual_orientation_list = new ArrayList<>();
    private List<CountryDataModel.DataBean> countryModelArrayList;
    private List<StateDataModel.DataEntity> stateModelArrayList;
    private List<CityDataModel.DataCity> cityModelArrayList;
    private List<EducationDataModel.EducationData> educationDataArrayList;


    private SpinnerAdapter occupationAdapter;
    private GenderAdapter genderAdapter;
    private EducationAdapter educationAdapter;
    private SexualOrientationAdapter sexualOrientationAdapter;
    String country_id, countryName, countryId, stateId, stateName, cityId, cityName, educationId, education;


    private String occupationstatus, genderStatus, sexualorientationStatus, countryStatus, stateStatus, cityStatus, educationStatus;
    private Button buttonSearch;

    private final ArrayList<OccupicationModel> occupicationModelArrayList = new ArrayList<>();
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sp_occupation = findViewById(R.id.sp_occupation);
        sp_gender = findViewById(R.id.sp_gender);
        sp_sexual_orientation = findViewById(R.id.sp_sexual_orientation);
        sp_country = findViewById(R.id.sp_country);
        sp_state = findViewById(R.id.sp_state);
        sp_city = findViewById(R.id.sp_city);
        sp_education = findViewById(R.id.sp_education);
        sp_special_case = findViewById(R.id.sp_special_case);

        buttonSearch = findViewById(R.id.btn_search);

        init();
        apiInterface = ApiClient.getInterface();

        countryModelArrayList = new ArrayList<>();
        stateModelArrayList = new ArrayList<>();
        cityModelArrayList = new ArrayList<>();
        educationDataArrayList = new ArrayList<>();

        CountryApi();
        EducationApi();
        ReligionApi();

        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);
                Log.d("TAG", "bandId: " + bandId);

                if (countryModelArrayList.size() > 0) {
                    country_id = countryModelArrayList.get(position).getCountryId();
                    Log.d("TAG", "countryIdd: " + country_id);
                    countryName = countryModelArrayList.get(position).getCountry();
                    Toast.makeText(SearchActivity.this, "" + country_id, Toast.LENGTH_SHORT).show();

                    if (country_id != null) {
                        StateApi(country_id);
                        //  CityApi(stateName);

                    }
                } else {
                    Toast.makeText(SearchActivity.this, "" + countryModelArrayList.size(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SearchActivity.this, "" + stateName, Toast.LENGTH_SHORT).show();

                    if (stateName != null) {
                        CityApi(stateName);

                    }
                } else {
                    Toast.makeText(SearchActivity.this, "" + stateModelArrayList.size(), Toast.LENGTH_SHORT).show();
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
        sp_education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bandId = (String) parent.getItemAtPosition(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                        sp_education.setAdapter(new EducationAdapter(SearchActivity.this, educationDataArrayList));
                        sp_education.setAdapter(educationAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<EducationDataModel> call, Throwable t) {

            }
        });
    }


    private void ReligionApi() {
        apiInterface.get_religion().enqueue(new Callback<SpecialSearchModel>() {
            @Override
            public void onResponse(Call<SpecialSearchModel> call, retrofit2.Response<SpecialSearchModel> response) {
                SpecialSearchModel specialSearchModel = response.body();
                if (specialSearchModel != null) {
                    if (specialSearchModel.getResponse()) {
                        List<SpecialSearchModel.SpecialData> specialData = specialSearchModel.getData();
                        Log.d("TAG", "search: " + specialData.get(0).getName());

                    }
                }
            }

            @Override
            public void onFailure(Call<SpecialSearchModel> call, Throwable t) {

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
                        sp_country.setAdapter(new CountryAdapter(SearchActivity.this, countryModelArrayList));
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
                        sp_state.setAdapter(new StateAdapter(SearchActivity.this, stateModelArrayList));
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
                        sp_city.setAdapter(new CityAdapter(SearchActivity.this, cityModelArrayList));

                    }
                }
            }

            @Override
            public void onFailure(Call<CityDataModel> call, Throwable t) {
                Log.d("TAG", "onResponse3: Fail");

            }


        });

    }


    private void init() {
        occupationAdapter = new SpinnerAdapter(this);
        sp_occupation.setAdapter(occupationAdapter);
        occupation_list.add("Select Occupation");
        occupation_list.add("Dancer");
        occupation_list.add("Engineer");
        occupation_list.add("Doctor");

        occupationAdapter.addTaxRateList(occupation_list);
        sp_occupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (occupation_list.size() > 0) {
                    int position1 = sp_occupation.getSelectedItemPosition();
                    occupationstatus = occupation_list.get(position);

                    if (position1 == 0) {
                        occupationstatus = "1";
                    } else if (position1 == 1) {
                        occupationstatus = "2";
                    } else if (position1 == 2) {
                        occupationstatus = "3";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        genderAdapter = new GenderAdapter(this);
        sp_gender.setAdapter(genderAdapter);
        gender_list.add("Select");
        gender_list.add("Male");
        gender_list.add("Female");
        gender_list.add("Transgender");

        genderAdapter.addTaxRateList(gender_list);
        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (gender_list.size() > 0) {
                    int position1 = sp_gender.getSelectedItemPosition();
                    genderStatus = gender_list.get(position);

                    if (position1 == 0) {
                        genderStatus = "1";
                    } else if (position1 == 1) {
                        genderStatus = "2";
                    } else if (position1 == 2) {
                        genderStatus = "3";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sexualOrientationAdapter = new SexualOrientationAdapter(this);
        sp_sexual_orientation.setAdapter(sexualOrientationAdapter);
        sexual_orientation_list.add("Select");
        sexual_orientation_list.add("Gay");
        sexual_orientation_list.add("Lesbian");
        sexual_orientation_list.add("Bisexual (Man)");
        sexual_orientation_list.add("Bisexual (Woman)");
        sexual_orientation_list.add("Transgender Man");
        sexual_orientation_list.add("Transgender Woman");


        sexualOrientationAdapter.addTaxRateList(sexual_orientation_list);
        sp_sexual_orientation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sexual_orientation_list.size() > 0) {
                    int position1 = sp_sexual_orientation.getSelectedItemPosition();
                    sexualorientationStatus = sexual_orientation_list.get(position);

                    if (position1 == 0) {
                        sexualorientationStatus = "1";
                    } else if (position1 == 1) {
                        sexualorientationStatus = "2";
                    } else if (position1 == 2) {
                        sexualorientationStatus = "3";
                    } else if (position1 == 3) {
                        sexualorientationStatus = "4";
                    } else if (position1 == 4) {
                        sexualorientationStatus = "5";
                    } else if (position1 == 5) {
                        sexualorientationStatus = "6";
                    } else if (position1 == 6) {
                        sexualorientationStatus = "7";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                occupationApi();
                break;
        }

    }

    private void occupationApi() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, ApiClient.BASE_URL + "get_occupation",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                Log.v("uploading", response);
                                JSONObject jsonObject = new JSONObject(response);
                                try {
                                    String success = jsonObject.getString("response");
                                    if (success.equals("true")) {

                                        OccupicationModel occupicationModel = new OccupicationModel("", "Occupation", "", "");
                                        occupicationModelArrayList.add(occupicationModel);
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if (jsonArray.length() == 0) {
                                            OccupicationModel occupicationModel1 = new OccupicationModel("", "No Record Found", "", "");
                                            occupicationModelArrayList.add(occupicationModel1);

                                        }
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                            OccupicationModel occupicationModel1 = new OccupicationModel(jsonObject1.getString("occupation_id"),
                                                    jsonObject1.getString("occupation"),
                                                    jsonObject1.getString("status"),
                                                    jsonObject1.getString("sortorder"), false);


                                            occupicationModelArrayList.add(occupicationModel1);
                                            //  progress_bar.setVisibility(View.INVISIBLE);

                                        }
                                    } else {

                                        Toast.makeText(getContext(), "Error1!!!..", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //spinnerOccupation.setAdapter(new OccupicationAdapter(getContext(), occupicationModelArrayList));


                            } catch (OutOfMemoryError | NullPointerException e) {
                                //    progress_bar.setVisibility(View.INVISIBLE);
                                // TODO: handle exception
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //   progress_bar.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.v("errrr", String.valueOf(error));

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
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

    public class GenderAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;
        private List<String> spinnerList;

        public GenderAdapter(Context context) {
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

    public class SexualOrientationAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;
        private List<String> spinnerList;

        public SexualOrientationAdapter(Context context) {
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

    public class EducationAdapter extends BaseAdapter {

        private final LayoutInflater layoutInflater;
        private List<String> spinnerList;

        public EducationAdapter(Context context, List<EducationDataModel.EducationData> educationDataArrayList) {
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


}


