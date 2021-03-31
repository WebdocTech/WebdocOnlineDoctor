package com.wmalick.webdocandroidlibrary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wmalick.webdoc_library.InitiateSDK.Initiate_sdk;

//import com.wmalick.webdoc_library.InitiateSDK.Initiate_sdk;

public class MainActivity extends AppCompatActivity {
    Button btnWOD;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWOD = findViewById(R.id.btnWOD);
        btnWOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new Initiate_sdk(MainActivity.this, "03165121519", "06ad39b1-131d-49f4-86fe-bb564adfca94","Alfa", "#F1A01F");
                //new Initiate_sdk(MainActivity.this, "03008248148","TPL", "#F1A01F");
                new Initiate_sdk(MainActivity.this, "03165121519", "KK", "#F1A01F", true);
            }
        });
    }
}