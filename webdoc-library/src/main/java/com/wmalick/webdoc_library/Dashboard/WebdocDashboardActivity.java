package com.wmalick.webdoc_library.Dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.wmalick.webdoc_library.Dashboard.Fragments.HomeFrag_WebdocDashboadActivity;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.ServerManager.VolleyListener;
import org.json.JSONException;
import org.json.JSONObject;

public class WebdocDashboardActivity extends AppCompatActivity implements  VolleyListener {
    public static Toolbar toolbar;
    private String mSinchUserId;
    private long mSigningSequence = 1;
    FirebaseAuth mAuth;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor(Global.THEME_COLOR_CODE));

        setContentView(R.layout.activity_webdoc_dashboard);

        //this is test code to commitg

        FirebaseApp.initializeApp(this);

        /*HashMap<String, String> abc = new HashMap<>();
        abc.put("name", "Waleed");
        abc.put("id", "12345");

        FirebaseDatabase.getInstance().getReference().child("test").setValue(abc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //String abc = task.getResult().toString();
                    Toast.makeText(WebdocDashboardActivity.this, "Success", Toast.LENGTH_SHORT).show();

                }else{
                    String xy = task.getException().getMessage();
                    Toast.makeText(WebdocDashboardActivity.this, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();

                }
            }
        });*/


        progressBar = findViewById(R.id.progressBar_WebdocDashboardActivity);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar_WebdocDashboardActivity);
        toolbar.setBackgroundColor(Color.parseColor(Global.THEME_COLOR_CODE));
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

        progressBar.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer_WebdocDashboardActivity, new HomeFrag_WebdocDashboadActivity())
                .commit();
    }

    @Override
    public void getRequestResponse(JSONObject response, String requestedApi) throws JSONException {
    }
}
