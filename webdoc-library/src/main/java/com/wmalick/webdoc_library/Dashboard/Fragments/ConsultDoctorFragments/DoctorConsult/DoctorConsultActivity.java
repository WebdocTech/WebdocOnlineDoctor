package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wmalick.webdoc_library.Agora.BaseActivity;
import com.wmalick.webdoc_library.Agora.ConstantApp;
import com.wmalick.webdoc_library.AgoraNew.AudioCall.VoiceCall;
import com.wmalick.webdoc_library.AgoraNew.VideoCall.VideoCall;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.databinding.FragmentDoctorConsultBinding;

import org.json.JSONException;
import org.json.JSONObject;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class DoctorConsultActivity extends BaseActivity {
    private FragmentDoctorConsultBinding layoutBinding;
    public static Toolbar toolbar;
    String callingID = "";
    DatabaseReference reference;
    String token;
    AlertDialog alertDialog;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = FragmentDoctorConsultBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        getWindow().setStatusBarColor(Color.parseColor(Global.THEME_COLOR_CODE));

        toolbar = findViewById(R.id.toolbar_DoctorConsultActivity);
        toolbar.setBackgroundColor(Color.parseColor(Global.THEME_COLOR_CODE));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        getSupportActionBar().setTitle(getString(R.string.consult_doctor));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        InitControl();
        ActionControl();
        doctorStatusRealTime();
    }

    @Override
    protected void initUIandEvent() {
        String lastChannelName = vSettings().mChannelName;
        if (!TextUtils.isEmpty(lastChannelName)) {
        }
    }

    public void onClickJoin(View view) {
    }

    public void forwardToAudioRoom() {
        if ((!(Integer.parseInt(Global.getCustomerDataModel.getGetcustomerDataResult().getCustomerData().getFreecall()) < 1))
                || (!Global.getCustomerDataModel.getGetcustomerDataResult().getCustomerData().getPackageSubscribed().equalsIgnoreCase("none"))) {

            JSONObject params = new JSONObject();
            try {
                params.put("to", token);
                params.put("data", new JSONObject()
                        .put("title", "Incoming Audio Call")
                        .put("channel", callingID)
                        .put("appointmentID", "0")
                        .put("body", Global.getCustomerDataModel.getGetcustomerDataResult().getCustomerData().getEmail()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Global.utils.sendNotification(this, params);
            vSettings().mChannelName = callingID;
            Global.selectedDoctorDeviceToken = token;
                /*Intent i = new Intent(this, AudioCallScreenActivity.class);
                i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, callingID);
                i.putExtra(ConstantApp.ACTION_KEY_USER_ACCOUNT, Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getEmail());
                //i.putExtra(ConstantApp.CALLED_USER, "waleed@webdoc.com.pk");
                i.putExtra(ConstantApp.ACTION_KEY_USER_TOKEN, "");
                startActivity(i);*/

            Intent i = new Intent(this, VoiceCall.class);
            i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, callingID);
            startActivity(i);
        } else {
            FeedBackDialog();
        }
    }

    public void forwardToVideoRoom() {
        if ((!(Integer.parseInt(Global.getCustomerDataModel.getGetcustomerDataResult().getCustomerData().getFreecall()) < 1))
                || (!Global.getCustomerDataModel.getGetcustomerDataResult().getCustomerData().getPackageSubscribed().equalsIgnoreCase("none"))) {

            Global.utils.startMediaPlayer(DoctorConsultActivity.this, R.raw.dialing_tone);

            JSONObject params = new JSONObject();
            try {
                params.put("to", token);
                params.put("data", new JSONObject()
                        .put("title", "Incoming Video Call")
                        .put("channel", callingID)
                        .put("appointmentID", "0")
                        .put("body", Global.getCustomerDataModel.getGetcustomerDataResult().getCustomerData().getEmail()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Global.utils.sendNotification(this, params);
            vSettings().mChannelName = callingID;
            Global.selectedDoctorDeviceToken = token;
        /*Intent i = new Intent(this, VideoCallScreenActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, callingID);
        i.putExtra(ConstantApp.ACTION_KEY_USER_ACCOUNT, Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getEmail());
        //i.putExtra(ConstantApp.CALLED_USER, "waleed@webdoc.com.pk");
        i.putExtra(ConstantApp.ACTION_KEY_USER_TOKEN, "");
        startActivity(i);*/

            Intent i = new Intent(this, VideoCall.class);
            i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, callingID);
            startActivity(i);
        } else {
            FeedBackDialog();
        }
    }

    @Override
    protected void deInitUIandEvent() {
    }

    private void ActionControl() {
        activity = this;
        FirebaseApp appReference = firebaseAppReference(activity);
        FirebaseDatabase databaseReference1 = FirebaseDatabase.getInstance(appReference);
        reference = databaseReference1.getReference().child("Tokens").child("Doctors");
        reference.child(Global.selectedDoctor.getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                token = dataSnapshot.child("token").getValue().toString();
                //Toast.makeText(DoctorConsultActivity.this, token, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        layoutBinding.tvName.setText(Global.selectedDoctor.getFirstName() + " " + Global.selectedDoctor.getLastName());
        layoutBinding.tvSpeciality.setText(Global.selectedDoctor.getDoctorSpecialty());

        Picasso.get().load(Global.selectedDoctor.getImgLink())
                .placeholder(R.drawable.ic_placeholder_doctor)
                .error(R.drawable.ic_placeholder_doctor)
                .into(layoutBinding.userImage);

        layoutBinding.btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.selectedDoctor.getOnlineDoctor().equalsIgnoreCase("online")) {
                    layoutBinding.btnAudio.setBackgroundResource(R.drawable.buttonborder_black_gray);
                    layoutBinding.btnAudio.setTextColor(Color.parseColor(Global.THEME_COLOR_CODE));
                    layoutBinding.btnVideo.setBackgroundResource(R.drawable.buttonborder_black);
                    layoutBinding.btnVideo.setTextColor(getResources().getColor(R.color.black));
                    layoutBinding.btnText.setBackgroundResource(R.drawable.buttonborder_black);
                    layoutBinding.btnText.setTextColor(getResources().getColor(R.color.black));
                    final PrettyDialog pDialog = new PrettyDialog(DoctorConsultActivity.this);
                    Integer color = Color.parseColor(Global.THEME_COLOR_CODE);
                    pDialog.setMessage("Are you sure you want to audio call to the doctor?")
                            .setAnimationEnabled(true)
                            .addButton(
                                    "Yes",
                                    R.color.pdlg_color_white,
                                    R.color.pdlg_color_gray,
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {
                                            forwardToAudioRoom();
                                            pDialog.dismiss();
                                        }
                                    })
                            .addButton(
                                    "No",
                                    R.color.pdlg_color_white,
                                    R.color.pdlg_color_gray,
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {
                                            pDialog.dismiss();
                                        }
                                    })
                            .setIcon(
                                    R.drawable.ic_dialer,     // icon resource
                                    R.color.pdlg_color_green,     // icon tint
                                    new PrettyDialogCallback() {   // icon OnClick listener
                                        @Override
                                        public void onClick() {
                                            // Do what you gotta do
                                        }
                                    })
                            .show();
                } else {
                    Toast.makeText(DoctorConsultActivity.this, "Doctor is busy please wait for your turn.", Toast.LENGTH_LONG).show();
                }

            }
        });

        layoutBinding.btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.selectedDoctor.getOnlineDoctor().equalsIgnoreCase("online")) {
                    layoutBinding.btnVideo.setBackgroundResource(R.drawable.buttonborder_black_gray);
                    layoutBinding.btnVideo.setTextColor(Color.parseColor(Global.THEME_COLOR_CODE));
                    layoutBinding.btnAudio.setBackgroundResource(R.drawable.buttonborder_black);
                    layoutBinding.btnAudio.setTextColor(getResources().getColor(R.color.black));
                    layoutBinding.btnText.setBackgroundResource(R.drawable.buttonborder_black);
                    layoutBinding.btnText.setTextColor(getResources().getColor(R.color.black));
                    final PrettyDialog pDialog = new PrettyDialog(DoctorConsultActivity.this);
                    pDialog.setMessage("Are you sure you want to video call to the doctor?")
                            .setAnimationEnabled(true)
                            .addButton(
                                    "Yes",
                                    R.color.pdlg_color_white,
                                    R.color.pdlg_color_gray,
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {
                                            forwardToVideoRoom();
                                            pDialog.dismiss();
                                        }
                                    })
                            .addButton(
                                    "No",
                                    R.color.pdlg_color_white,
                                    R.color.pdlg_color_gray,
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {
                                            pDialog.dismiss();
                                        }
                                    })
                            .setIcon(
                                    R.drawable.ic_videocam_black_24dp,     // icon resource
                                    R.color.pdlg_color_green,      // icon tint
                                    new PrettyDialogCallback() {   // icon OnClick listener
                                        @Override
                                        public void onClick() {
                                            // Do what you gotta do
                                        }
                                    })
                            .show();
                } else {
                    Toast.makeText(DoctorConsultActivity.this, "Doctor is busy please wait for your turn.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void InitControl() {
        callingID = Global.selectedDoctor.getEmail();
    }

    private void FeedBackDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.alert_no_package_consult_doctor, null);
        dialogBuilder.setView(v);

        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView lblAlertTitle = v.findViewById(R.id.lblMessage_AlertNoPackageConsultDoctor);
        lblAlertTitle.setTextColor(Color.parseColor(Global.THEME_COLOR_CODE));

        Button btnOkay = v.findViewById(R.id.btnDismiss_AlertNoPackage);
        btnOkay.getBackground().setTint(Color.parseColor(Global.THEME_COLOR_CODE));
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }//alert

    private void doctorStatusRealTime() {
        FirebaseApp appReference = firebaseAppReference(activity);
        FirebaseDatabase databaseReference = FirebaseDatabase.getInstance(appReference);
        databaseReference.getReference().child("Doctors").child(Global.selectedDoctor.getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    if (dataSnapshot.child("status").getValue() != null) {
                        Global.selectedDoctor.setOnlineDoctor(dataSnapshot.child("status").getValue().toString());
                        if (dataSnapshot.child("status").getValue().toString().equals("online")) {
                            layoutBinding.ivOnlineStatusConsultDoctor.setImageResource(R.drawable.online);
                        } else {
                            layoutBinding.ivOnlineStatusConsultDoctor.setImageResource(R.drawable.ic_offline);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private static FirebaseApp firebaseAppReference(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyAY2_N3ab_45ggVlisDcuOWxhbzVZZqN34")
                .setApplicationId("1:642677587502:android:d0de10b1c22b1ee21a69bf")
                .setDatabaseUrl("https://webdoc-896a8.firebaseio.com/")
                .build();

        try {
            FirebaseApp app = FirebaseApp.initializeApp(context, options, "WebDocDoctorSDK");
            return app;
        } catch (IllegalStateException e) {
            return FirebaseApp.getInstance("WebDocDoctorSDK");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Global.call_not_answered) {
            Global.utils.callNotAnsweredDialog(this, Global.selectedDoctor.getFirstName() + " " + Global.selectedDoctor.getLastName());
            Global.call_not_answered = false;
        }
    }
}
