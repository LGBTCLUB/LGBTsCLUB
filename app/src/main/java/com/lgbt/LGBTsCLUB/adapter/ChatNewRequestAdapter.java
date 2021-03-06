package com.lgbt.LGBTsCLUB.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.activity.MoreInfoActivity;
import com.lgbt.LGBTsCLUB.model.NewRequestModel;
import com.lgbt.LGBTsCLUB.model.PendingModel;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER;


public class ChatNewRequestAdapter extends RecyclerView.Adapter<ChatNewRequestAdapter.CustomerViewHolder> {

    Context context;
    Fragment fragment;
    private List<NewRequestModel.NewRequestData> homeDataList;
    private final ApiInterface apiInterface;


    public ChatNewRequestAdapter(Context context, Fragment fragment, List<NewRequestModel.NewRequestData> newRequestDataList) {
        this.context = context;
        this.fragment = fragment;
        this.homeDataList = newRequestDataList;

        apiInterface = ApiClient.getInterface();
    }


    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_newrequest_list, parent, false);

        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final NewRequestModel.NewRequestData customerList = homeDataList.get(position);
        holder.name.setText(customerList.getMatriId());
        holder.chat_message.setText(customerList.getGender());

        final String reqId = customerList.getReq_id();
        String profileStatus = customerList.getProfile_status();

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.logo_final)
                .error(R.drawable.logo_final)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH).dontAnimate()
                .dontTransform();

        if (profileStatus.equals("show")) {
            Glide.with(context)
                    .load(IMAGE_LOAD_USER + customerList.getPhoto1())
                    .apply(options)
                    .into(holder.user_image);
        } else {
            Glide.with(context)
                    .load(IMAGE_LOAD_USER + customerList.getPhoto1())
                    .apply(bitmapTransform(new BlurTransformation(25)))
                    .into(holder.user_image);
        }


        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MoreInfoActivity.class);
                intent.putExtra("martId", customerList.getMatriId());
                context.startActivity(intent);
            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_send_request(customerList.getReq_id(), position);
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRequest(customerList.getReq_id(), position);
            }
        });


        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent intent = new Intent(mContext, ChatInboxActivity.class);
                //  intent.putExtra("user_Name", singleItem.getName());
                // context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return null == homeDataList ? 0 : homeDataList.size();
    }

    public void addCustomerList(List<NewRequestModel.NewRequestData> homeListList) {
        this.homeDataList = homeListList;
        notifyDataSetChanged();
    }

    public void acceptRequest(String req_id, int position) {
        apiInterface.acceptRequest(req_id).enqueue(new Callback<PendingModel>() {
            @Override
            public void onResponse(Call<PendingModel> call, Response<PendingModel> response) {
                if (response.isSuccessful()) {
                    PendingModel homeModel = response.body();
                    if (homeModel != null) {
                        String respons = homeModel.getResponse();
                        if (respons.equals("true")) {
                            Toast.makeText(context, "accepted", Toast.LENGTH_LONG).show();
                            try {
                                homeDataList.remove(position);
                                notifyDataSetChanged();
                            } catch (Exception e) {

                            }
                        } else {
                            Toast.makeText(context, "Already accepted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PendingModel> call, Throwable t) {
                Toast.makeText(context, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void cancel_send_request(String req_id, int position) {
        apiInterface.cancel_send_request(req_id).enqueue(new Callback<PendingModel>() {
            @Override
            public void onResponse(Call<PendingModel> call, Response<PendingModel> response) {
                if (response.isSuccessful()) {
                    PendingModel homeModel = response.body();
                    if (homeModel != null) {
                        String respons = homeModel.getResponse();
                        if (respons.equals("true")) {
                            // homeDataList.get(position).setSendrequest("1");
                            try {
                                homeDataList.remove(position);
                                notifyDataSetChanged();
                            } catch (Exception e) {

                            }
                        } else {
                            //  Toast.makeText(getContext(), "Already accepted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PendingModel> call, Throwable t) {
                Toast.makeText(context, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView user_image;
        private final TextView name;
        private final TextView chat_message;
        private final LinearLayout layout_item;
        //  private final Button bt_request;
        private final TextView moreInfo;
        private ImageView cancel, accept;
        private ImageView iv_heart;


        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.user_image);
            //  iv_heart = itemView.findViewById(R.id.iv_heart);
            name = itemView.findViewById(R.id.name);
            chat_message = itemView.findViewById(R.id.chat_message);
            layout_item = itemView.findViewById(R.id.layout_item);
            //  bt_request = itemView.findViewById(R.id.bt_request);
            moreInfo = itemView.findViewById(R.id.more_info);
            cancel = itemView.findViewById(R.id.cancel);
            accept = itemView.findViewById(R.id.accept);
        }
    }

}