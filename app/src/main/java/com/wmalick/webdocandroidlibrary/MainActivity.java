package com.wmalick.webdocandroidlibrary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnWOD;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWOD = findViewById(R.id.btnWOD);
        btnWOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {/*
                 new Initiate_sdk(MainActivity.this, "03165121519", "Alfa", "#F1A01F");*/
            }
        });
    }
}