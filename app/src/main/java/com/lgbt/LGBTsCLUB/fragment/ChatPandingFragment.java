package com.lgbt.LGBTsCLUB.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.adapter.ChatPendingAdapter;
import com.lgbt.LGBTsCLUB.helper.AppHelper;
import com.lgbt.LGBTsCLUB.model.PendingModel;
import com.lgbt.LGBTsCLUB.model.YardsItem;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lgbt.LGBTsCLUB.network.networking.Constant.MATRI_ID;


public class ChatPandingFragment extends Fragment {

    ImageView iv_back, iv_logo;
    RecyclerView pending_recy;
    ProgressBar progressCircular;
    boolean isFilter = true;
    List<PendingModel.PendingData> homeDataList = new ArrayList<>();
    ChatPendingAdapter chatPendingAdapter;
    List<YardsItem> customerListList = new ArrayList<>();
    YardsItem yardsItem;
    ProgressBar progress_bar;
    int OFFSET = 0;
    private final boolean loading = true;
    RecyclerView.LayoutManager layoutManager;
    private ApiInterface apiInterface;
    private int last = 0;
    private int offset = 0;

    public ChatPandingFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_panding, container, false);

        iv_back = view.findViewById(R.id.iv_back);
        pending_recy = view.findViewById(R.id.pending_recy);
        progressCircular = view.findViewById(R.id.progress_circular);
        iv_logo = view.findViewById(R.id.iv_logo);
        progress_bar = view.findViewById(R.id.progress_bar);
        apiInterface = ApiClient.getInterface();
        layoutManager = new LinearLayoutManager(getActivity());
        pending_recy.setLayoutManager(layoutManager);
        pending_recy.setHasFixedSize(true);
        pending_recy.setNestedScrollingEnabled(false);

        chatPendingAdapter = new ChatPendingAdapter(getActivity(), ChatPandingFragment.this, homeDataList);
        pending_recy.setAdapter(chatPendingAdapter);

        pendingList(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);

        AppHelper.setupLoadMore(pending_recy, new AppHelper.OnScrollToEnd() {
            @Override
            public void scrolledToEnd(int lastVisibleItem) {
                if (last != lastVisibleItem) {
                    last = lastVisibleItem;
                    offset = offset + 5;
                    if (!isFilter) {
                        pendingList(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
    
    private void pendingList(String matriID, int OFFSET) {
        isFilter = false;
        Log.i("rhl...", String.valueOf(OFFSET));
        progress_bar.setVisibility(View.VISIBLE);
        apiInterface.pendingList(matriID, OFFSET + "").enqueue(new Callback<PendingModel>() {
            @Override
            public void onResponse(Call<PendingModel> call, Response<PendingModel> response) {
                if (response.isSuccessful()) {
                    PendingModel homeModel = response.body();
                    if (homeModel != null) {
                        String responses = homeModel.getResponse();
                        progress_bar.setVisibility(View.GONE);
                        if (responses.equals("true")) {
                            iv_logo.setVisibility(View.GONE);
                            if (homeModel.getPendingData().size() > 0) {
                                homeDataList.addAll(homeModel.getPendingData());
                                chatPendingAdapter.notifyDataSetChanged();
                            }
                        } else {
                            if (offset == 0) {
                                Toast.makeText(getContext(), "No pending request", Toast.LENGTH_LONG).show();
                                iv_logo.setVisibility(View.VISIBLE);
                            }
                            if (offset > 0) {
                                --offset;
                            }
                            last = 0;
                        }
                    } else {
                        progress_bar.setVisibility(View.GONE);

                        if (offset > 0) {
                            --offset;
                        }
                        last = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<PendingModel> call, Throwable t) {
                if (offset > 0) {
                    --offset;
                }
                last = 0;
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void cancel_send_request(String req_id) {
        apiInterface.cancel_send_request(req_id).enqueue(new Callback<PendingModel>() {
            @Override
            public void onResponse(Call<PendingModel> call, Response<PendingModel> response) {
                if (response.isSuccessful()) {
                    PendingModel homeModel = response.body();
                    if (homeModel != null) {
                        String respons = homeModel.getResponse();
                        if (respons.equals("true")) {
                            // Toast.makeText(getContext(), "accepted", Toast.LENGTH_LONG).show();
                            pendingList(SharedPrefsManager.getInstance().getString(MATRI_ID), offset);
                            chatPendingAdapter.notifyDataSetChanged();
                        } else {
                            //  Toast.makeText(getContext(), "Already accepted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PendingModel> call, Throwable t) {
                Toast.makeText(getContext(), "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

}
