package com.lgbt.LGBTsCLUB;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.lgbt.LGBTsCLUB.activity.BookMarkedActivity;
import com.lgbt.LGBTsCLUB.activity.ChatActivity;
import com.lgbt.LGBTsCLUB.activity.LoginActivity;
import com.lgbt.LGBTsCLUB.activity.NotificationActivity;
import com.lgbt.LGBTsCLUB.activity.SearchActivity;
import com.lgbt.LGBTsCLUB.fragment.AboutUsFragment;
import com.lgbt.LGBTsCLUB.fragment.ChatFragment;
import com.lgbt.LGBTsCLUB.fragment.ChatUsFragment;
import com.lgbt.LGBTsCLUB.fragment.FavouritesFragment;
import com.lgbt.LGBTsCLUB.fragment.MainFragment;
import com.lgbt.LGBTsCLUB.fragment.MyProfileFragment;
import com.lgbt.LGBTsCLUB.fragment.searchfragment.AdvancedSearchFragment;
import com.lgbt.LGBTsCLUB.fragment.searchfragment.EducationSearchFragment;
import com.lgbt.LGBTsCLUB.fragment.searchfragment.LocationSearchFragment;
import com.lgbt.LGBTsCLUB.fragment.searchfragment.MatrimonyIDSearchFragment;
import com.lgbt.LGBTsCLUB.fragment.searchfragment.OccupationSearchFragment;
import com.lgbt.LGBTsCLUB.fragment.searchfragment.ResultSearchFragment;
import com.lgbt.LGBTsCLUB.fragment.searchfragment.SearchSelectFragment;
import com.lgbt.LGBTsCLUB.fragment.searchfragment.SmartSearchFragment;
import com.lgbt.LGBTsCLUB.fragment.searchfragment.SpecialSearchFragment;
import com.lgbt.LGBTsCLUB.model.ProfileModel;
import com.lgbt.LGBTsCLUB.model.UserStatusModel;
import com.lgbt.LGBTsCLUB.network.database.SharedPrefsManager;
import com.lgbt.LGBTsCLUB.network.networking.ApiClient;
import com.lgbt.LGBTsCLUB.network.networking.ApiInterface;
import com.lgbt.LGBTsCLUB.network.networking.Constant;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.parseColor;
import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.IMAGE_LOAD_USER1;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.KEY_USER_NAME;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.LOGIN_ID;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.MATRI_ID;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.NO_CHAT_CONTACT;
import static com.lgbt.LGBTsCLUB.network.networking.Constant.STATUS;
import static java.lang.System.load;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageViewHome, imageViewChat, imageViewLike, imageViewAccount;
    LinearLayout linearLayoutHome, linearLayoutChat, linearLayoutLike, linearLayoutAccount;
    ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    Fragment fragment = null;
    Bundle bundle = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    AppUpdateManager appUpdateManager;
    int MY_REQUEST_CODE = 200;
    private ApiInterface apiInterface;
    private DrawerLayout drawerLayout;
    private ImageView notification, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        apiInterface = ApiClient.getInterface();

//        toolbar.getBackground().setAlpha(10);
//        setSupportActionBar(toolbar);

        notification = findViewById(R.id.notification);
        search = findViewById(R.id.search);
        imageViewHome = findViewById(R.id.img_home);
        imageViewChat = findViewById(R.id.img_chat);
        imageViewLike = findViewById(R.id.img_like);
        imageViewAccount = findViewById(R.id.img_account);

        linearLayoutHome = findViewById(R.id.linear_home);
        linearLayoutChat = findViewById(R.id.linear_chat);
        linearLayoutLike = findViewById(R.id.linear_like);
        linearLayoutAccount = findViewById(R.id.linear_account);

        linearLayoutHome.setOnClickListener(this);
        linearLayoutChat.setOnClickListener(this);
        linearLayoutLike.setOnClickListener(this);
        linearLayoutAccount.setOnClickListener(this);


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        fragment = new MainFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        // installButton90to90();

        String afterLogIn = SharedPrefsManager.getInstance().getString(STATUS);

        if (afterLogIn != null) {
            if (afterLogIn.equals("Inactive")) {
                afterregisContain("");
            } else if (afterLogIn.equals("Banned")) {
                afterregisContain("Your profile has been rejected due to inappropriate words.For more details email : umeedlgbt@gmail.com");
            }
        }


        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                    Log.e("sdfdf", "sdfdsf");
                }
            }
        });
    }

    private void initView() {
        drawerLayout = findViewById(R.id.navDrawer);

        ImageView menu = findViewById(R.id.menu);

        findViewById(R.id.linear_profile).setOnClickListener(this);
        findViewById(R.id.linear_message).setOnClickListener(this);
        findViewById(R.id.linear_notification).setOnClickListener(this);
        findViewById(R.id.linear_favourite).setOnClickListener(this);
        findViewById(R.id.linear_settings).setOnClickListener(this);
        TextView userName, matriId;
        userName = findViewById(R.id.txt_user_name);
        matriId = findViewById(R.id.txt_user_id);


        if (SharedPrefsManager.getInstance().getString(KEY_USER_NAME) != null) {
            userName.setText(SharedPrefsManager.getInstance().getString(KEY_USER_NAME));
        }
        if (SharedPrefsManager.getInstance().getString(Constant.MATRI_ID) != null) {
            matriId.setText(SharedPrefsManager.getInstance().getString(MATRI_ID));
        }


        menu.setOnClickListener(v -> {
            try {
                InputMethodManager inputManager = (InputMethodManager) MainActivity.this
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        MainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {

            }
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    public void onBackPressed() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if (currentFragment == null) {
            finish();
        } else if (currentFragment instanceof MainFragment) {
            finish();
        } else {
            pushFragment(new MainFragment());

            imageViewHome.setColorFilter(getResources().getColor(R.color.navy_blue));
            imageViewChat.setColorFilter(getResources().getColor(R.color.grey));
            imageViewLike.setColorFilter(getResources().getColor(R.color.grey));
            imageViewAccount.setColorFilter(getResources().getColor(R.color.grey));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        user_status(SharedPrefsManager.getInstance().getString(LOGIN_ID));
        profileDetailApi(SharedPrefsManager.getInstance().getString(LOGIN_ID));
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            IMMEDIATE,
                                            this,
                                            MY_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.i("Update flow failed: ", String.valueOf(resultCode));
            }
        }
    }

    private void afterregisContain(String text) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.after_regis_contain_view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        TextView text_dialog = dialog.findViewById(R.id.text_dialog);
        if (!text.isEmpty()) {
            text_dialog.setText(text);
        }
        Button ok = dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        dialog.show();
    }

    private void installButton90to90() {
        final AllAngleExpandableButton button = (AllAngleExpandableButton) findViewById(R.id.button_expandable_90_180);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.plus, R.drawable.user, R.drawable.ic_notification_pink, R.drawable.ic_chat_pink, R.drawable.search, R.drawable.ic_heart_pink};
        int[] color = {R.color.red, R.color.white, R.color.white, R.color.white, R.color.white, R.color.white};
        for (int i = 0; i < 6; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 10);
            } else {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            }
            buttonData.setBackgroundColorId(this, color[i]);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);
        // setListener(button);
    }

    private void setListener(AllAngleExpandableButton button) {
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                // showToast("clicked index:" + index);
                String index1 = String.valueOf(index);
                if (index == 1) {
                    fragment = new MyProfileFragment();
                } else if (index == 2) {
                    Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                    startActivity(intent);
                } else if (index == 3) {
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
                } else if (index == 4) {
                    fragment = new SearchSelectFragment();
                } else if (index == 5) {
                    Intent intent = new Intent(MainActivity.this, BookMarkedActivity.class);
                    startActivity(intent);
                } else {
                    fragment = new MainFragment();
                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onExpand() {
//                showToast("onExpand");
            }

            @Override
            public void onCollapse() {
//                showToast("onCollapse");
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
                        if (status.equals("Active") || status.equals("Inactive")) {

                        } else {
                            SharedPrefsManager.getInstance().clearPrefs();
                            afterregisContain("Your profile has been rejected due to inappropriate words.More details email : umeedlgbt@gmail.com");

//                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
                Toast.makeText(MainActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void profileDetailApi(String userId) {
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
                            String nochatContact = profileData.getChatcontact();
                            SharedPrefsManager.getInstance().setString(NO_CHAT_CONTACT, nochatContact);
                            if (editStatus.equals("Yes")) {


                            } else {
//                                afterregisContain("");
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "something is wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_home:
                imageViewHome.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
                imageViewChat.setColorFilter(getResources().getColor(R.color.blueyy));
                imageViewLike.setColorFilter(getResources().getColor(R.color.blueyy));
                imageViewAccount.setColorFilter(getResources().getColor(R.color.blueyy));
                pushFragment(new MainFragment());
                break;
            case R.id.linear_chat:
                imageViewHome.setColorFilter(getResources().getColor(R.color.blueyy));
                imageViewChat.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
                imageViewLike.setColorFilter(getResources().getColor(R.color.blueyy));
                imageViewAccount.setColorFilter(getResources().getColor(R.color.blueyy));
                pushFragment(new ChatUsFragment());
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("jjj");
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                setSupportActionBar(toolbar);
                break;
            case R.id.linear_like:
                imageViewHome.setColorFilter(getResources().getColor(R.color.blueyy));
                imageViewChat.setColorFilter(getResources().getColor(R.color.blueyy));
                imageViewLike.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
                imageViewAccount.setColorFilter(getResources().getColor(R.color.blueyy));
                pushFragment(new FavouritesFragment());
                break;
            case R.id.linear_account:
                imageViewHome.setColorFilter(getResources().getColor(R.color.blueyy));
                imageViewChat.setColorFilter(getResources().getColor(R.color.blueyy));
                imageViewLike.setColorFilter(getResources().getColor(R.color.blueyy));
                imageViewAccount.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
                pushFragment(new MyProfileFragment());
                break;
            case R.id.linear_profile:
                pushFragment(new MyProfileFragment());
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.linear_message:
                pushFragment(new ChatUsFragment());
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.linear_notification:
                Intent intent1 = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent1);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.linear_favourite:
                Intent intent2 = new Intent(MainActivity.this, BookMarkedActivity.class);
                startActivity(intent2);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
//            case R.id.settings:
//                pushFragment(new SearchSelectFragment());
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;

        }
    }

    //load fragment
    private void pushFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 2) {

            fragmentManager.popBackStack();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
