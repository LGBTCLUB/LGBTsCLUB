package com.lgbt.LGBTsCLUB.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.activity.ChatActivity;
import com.lgbt.LGBTsCLUB.activity.LoginActivity;
import com.lgbt.LGBTsCLUB.model.UserStatusModel;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;

import static com.lgbt.LGBTsCLUB.network.networking.Constant.LOGIN_ID;

public class ChatUsFragment extends Fragment implements View.OnClickListener {
    TextView textViewAccepted, textViewNewRequest,textViewPending;
    private ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_us, container, false);
        textViewAccepted= view.findViewById(R.id.txt_accepted);
        textViewNewRequest= view.findViewById(R.id.txt_new_request);
        textViewPending= view.findViewById(R.id.txt_pending);
        apiInterface = ApiClient.getInterface();

        textViewAccepted.setOnClickListener(this);
        textViewNewRequest.setOnClickListener(this);
        textViewPending.setOnClickListener(this);
        textViewNewRequest.performClick();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_accepted:
                textViewAccepted.setTextColor(getResources().getColor(R.color.navy_blue));
                textViewAccepted.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
                textViewNewRequest.setTextColor(getResources().getColor(R.color.gray_light));
                textViewPending.setTextColor(getResources().getColor(R.color.gray_light));
                pushFragment(new ChatFragment());
                break;

            case R.id.txt_new_request:
                textViewAccepted.setTextColor(getResources().getColor(R.color.gray_light));
                textViewNewRequest.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
                textViewNewRequest.setTextColor(getResources().getColor(R.color.navy_blue));
                textViewPending.setTextColor(getResources().getColor(R.color.gray_light));
                pushFragment(new ChatNewRequestFragment());
                break;

            case R.id.txt_pending:
                textViewAccepted.setTextColor(getResources().getColor(R.color.gray_light));
                textViewPending.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD);
                textViewNewRequest.setTextColor(getResources().getColor(R.color.gray_light));
                textViewPending.setTextColor(getResources().getColor(R.color.navy_blue));
                pushFragment(new ChatPandingFragment());
                break;
                
        }

    }

    private void pushFragment( Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 2) {

            fragmentManager.popBackStack();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.frame, fragment)
                .addToBackStack(null)
                .commit();
    }
    
}