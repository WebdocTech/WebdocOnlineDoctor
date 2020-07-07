package com.wmalick.webdocandroidlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wmalick.webdoc_library.InitiateSDK.Initiate_sdk;

import java.util.HashMap;

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
                Initiate_sdk initiate_sdk = new Initiate_sdk(MainActivity.this, "03165121519", "Alfa");


            }
        });
    }
}