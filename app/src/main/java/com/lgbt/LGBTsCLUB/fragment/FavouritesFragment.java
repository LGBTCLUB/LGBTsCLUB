package com.lgbt.LGBTsCLUB.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.activity.BookMarkedActivity;
import com.lgbt.LGBTsCLUB.activity.LoginActivity;
import com.lgbt.LGBTsCLUB.adapter.BookmarkAdapter;
import com.lgbt.LGBTsCLUB.adapter.ChatAdapter;
import com.lgbt.LGBTsCLUB.helper.AppHelper;
import com.lgbt.LGBTsCLUB.model.HeartModel;
import com.lgbt.LGBTsCLUB.model.LoginModel;
import com.lgbt.LGBTsCLUB.model.UserStatusModel;
import com.lgbt.LGBTsCLUB.model.YardsItem;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import static com.lgbt.LGBTsCLUB.network.networking.Constant.LOGIN_ID;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.MATRI_ID;

public class FavouritesFragment extends Fragment {

    ImageView iv_home, iv_back, iv_logo;
    BookmarkAdapter bookmarkAdapter;
    List<YardsItem> customerListList = new ArrayList<>();
    YardsItem yardsItem;
    RecyclerView bookmarked_recyclerview;
    ProgressBar progressCircular;
    ProgressBar progress_bar;

    List<HeartModel.HeartData> homeDataList = new ArrayList<>();
    private ApiInterface apiInterface;
    private int last = 0;
    private int offset = 0;

    public FavouritesFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        bookmarked_recyclerview = view.findViewById(R.id.bookmarked_recyclerview);
        progressCircular = view.findViewById(R.id.progress_circular);
        iv_back = view.findViewById(R.id.iv_back);

        progress_bar = view.findViewById(R.id.progress_bar);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookmarked_recyclerview.setLayoutManager(layoutManager);

        //user_status(SharedPrefsManager.getInstance().getString(LOGIN_ID));

        bookmarkAdapter = new BookmarkAdapter(getActivity(),FavouritesFragment.this, homeDataList);
        bookmarked_recyclerview.setAdapter(bookmarkAdapter);
        apiInterface = ApiClient.getInterface();

        myHeartList(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);

        AppHelper.setupLoadMore(bookmarked_recyclerview, new AppHelper.OnScrollToEnd() {
            @Override
            public void scrolledToEnd(int lastVisibleItem) {
                if (last != lastVisibleItem) {
                    last = lastVisibleItem;
                    offset = offset + 5;
                    myHeartList(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);
                }
            }
        });


        return view;
    }

    private void myHeartList(String matriID, int pagecount) {
        progress_bar.setVisibility(View.VISIBLE);
        apiInterface.myHeartList(matriID, pagecount + "").enqueue(new Callback<HeartModel>() {
            @Override
            public void onResponse(Call<HeartModel> call, Response<HeartModel> response) {
                if (response.isSuccessful()) {
                    HeartModel homeModel = response.body();
                    if (homeModel != null) {
                        progress_bar.setVisibility(View.GONE);
                        // homeDataList = homeModel.getHeartDataList();
                        if (homeModel.getHeartDataList().size() > 0) {
                            //iv_logo.setVisibility(View.GONE);
                            homeDataList.addAll(homeModel.getHeartDataList());
                            //  bookmarkAdapter.addCustomerList(homeDataList);
                            bookmarkAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (offset == 0) {
                            progress_bar.setVisibility(View.GONE);
                           // Toast.makeText(BookMarkedActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                            iv_logo.setVisibility(View.VISIBLE);
                        }
                        //  Toast.makeText(getContext(), "No pending request", Toast.LENGTH_LONG).show();
                        if (offset > 0) {
                            --offset;
                        }
                        last = 0;
                    }
                }
            }





//            @Override
//            public void onResume() {
//                super.onResume();
//                user_status(SharedPrefsManager.getInstance().getString(LOGIN_ID));
//            }


            @Override
            public void onFailure(Call<HeartModel> call, Throwable t) {
                //Toast.makeText(BookMarkedActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                //    Toast.makeText(BookMarkedActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                progress_bar.setVisibility(View.GONE);
                iv_logo.setVisibility(View.VISIBLE);
                if (offset > 0) {
                    --offset;
                }
                last = 0;
            }
        });

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
                        if (status.equals("Active")) {

                        } else {
                            SharedPrefsManager.getInstance().clearPrefs();
//                            Intent intent = new Intent(FavouritesFragment.this, LoginActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserStatusModel> call, Throwable t) {
                //Toast.makeText(BookMarkedActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendRequest(String matriId, String loginmatriid) {
            apiInterface.sendFriendRequest(matriId, loginmatriid).enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.isSuccessful()) {
                        LoginModel homeModel = response.body();
                        if (homeModel != null) {
                            boolean respnse = homeModel.isResponse();
                            if (respnse) {
                                // Toast.makeText(FavouritesFragment.this, "Sent Request", Toast.LENGTH_SHORT).show();
                                bookmarkAdapter.notifyDataSetChanged();
                                myHeartList(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);

                            } else {
                                // Toast.makeText(BookMarkedActivity.this, "Already Sent Request", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    // Toast.makeText(BookMarkedActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
                }
            });

    }
}