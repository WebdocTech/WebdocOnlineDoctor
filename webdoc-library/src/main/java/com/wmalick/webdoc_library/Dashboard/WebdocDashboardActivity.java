package com.wmalick.webdoc_library.Dashboard;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushTokenRegistrationCallback;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.UserController;
import com.sinch.android.rtc.UserRegistrationCallback;
import com.wmalick.webdoc_library.Dashboard.Fragments.HomeFrag_WebdocDashboadActivity;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.ServerManager.VolleyListener;
import com.wmalick.webdoc_library.Sinch.SinchBaseActivity;
import com.wmalick.webdoc_library.Sinch.SinchService;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;

import static com.wmalick.webdoc_library.Sinch.SinchService.APP_KEY;
import static com.wmalick.webdoc_library.Sinch.SinchService.APP_SECRET;
import static com.wmalick.webdoc_library.Sinch.SinchService.ENVIRONMENT;

public class WebdocDashboardActivity extends SinchBaseActivity/*AppCompatActivity*/ implements SinchService.StartFailedListener, PushTokenRegistrationCallback, UserRegistrationCallback,  VolleyListener {

    public static Toolbar toolbar;
    private String mSinchUserId;
    private long mSigningSequence = 1;
    FirebaseAuth mAuth;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webdoc_dashboard);

        //this is test code to commitg

        FirebaseApp.initializeApp(this);
        progressBar = findViewById(R.id.progressBar_WebdocDashboardActivity);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar_WebdocDashboardActivity);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));

        getSupportActionBar().setTitle(getString(R.string.health));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*progressBar.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer_WebdocDashboardActivity, new HomeFrag_WebdocDashboadActivity())
                .commit();*/
    }

    @Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException
    {

    }

    @Override
    protected void onServiceConnected() {
        if (getSinchServiceInterface().isStarted()) {
            SinchLogin(Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getMobileNumber());
            Toast.makeText(this, "sinch started msg on onServiceConnected ", Toast.LENGTH_SHORT).show();
        } else {
            getSinchServiceInterface().setStartListener(this);
            startClientAndOpenPlaceCallActivity();
        }
    }

    @Override
    public void tokenRegistered() {
        openPlaceCallActivity();
    }

    @Override
    public void tokenRegistrationFailed(SinchError sinchError) {
        Toast.makeText(this, "Push token registration failed - incoming calls can't be received!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCredentialsRequired(ClientRegistration clientRegistration) {
        String toSign = mSinchUserId + APP_KEY + mSigningSequence + APP_SECRET;
        String signature;
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] hash = messageDigest.digest(toSign.getBytes("UTF-8"));
            signature = Base64.encodeToString(hash, Base64.DEFAULT).trim();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        clientRegistration.register(signature, mSigningSequence++);
    }

    @Override
    public void onUserRegistered() {
        openPlaceCallActivity();
    }

    @Override
    public void onUserRegistrationFailed(SinchError sinchError) {
        Toast.makeText(this, "Registration failed!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStarted() {
        SinchLogin(Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getMobileNumber());
    }

    private void startClientAndOpenPlaceCallActivity() {
        // start Sinch Client, it'll result onStarted() callback from where the place call activity will be started
        if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().setUsername(Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getMobileNumber());
            getSinchServiceInterface().startClient();
        }
    }

    private void openPlaceCallActivity() {
        Global.utils.hideProgressDialog();
        progressBar.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer_WebdocDashboardActivity, new HomeFrag_WebdocDashboadActivity())
                .commit();
    }

    private void SinchLogin(String username) {
        getSinchServiceInterface().setUsername(username);
        if (!username.equals(getSinchServiceInterface().getUsername())) {
            getSinchServiceInterface().stopClient();
        }

        mSinchUserId = username;
        UserController uc = Sinch.getUserControllerBuilder()
                .context(getApplicationContext())
                .applicationKey(APP_KEY)
                .userId(mSinchUserId)
                .environmentHost(ENVIRONMENT)
                .build();
        uc.registerUser(this, this);
    }
}
