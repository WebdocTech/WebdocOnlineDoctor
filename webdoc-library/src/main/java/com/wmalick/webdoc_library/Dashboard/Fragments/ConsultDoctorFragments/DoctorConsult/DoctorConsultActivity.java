package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsult;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wmalick.webdoc_library.Agora.AudioCallScreenActivity;
import com.wmalick.webdoc_library.Agora.BaseActivity;
import com.wmalick.webdoc_library.Agora.ConstantApp;
import com.wmalick.webdoc_library.Agora.VideoCallScreenActivity;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.Essentials.Utils;
import com.wmalick.webdoc_library.FormModels.SubmitWebdocFeedbackFormModel;
import com.wmalick.webdoc_library.R;
import org.json.JSONException;
import org.json.JSONObject;
import de.hdodenhof.circleimageview.CircleImageView;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class DoctorConsultActivity extends BaseActivity {
    TextView tv_name, tv_speciality;
    CircleImageView profile_image;
    Button btn_Text,btn_Video,btn_Audio;
    String callingID = "";
    DatabaseReference reference;
    String token;
    AlertDialog alertDialog;

    public DoctorConsultActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_doctor_consult);
        InitControl();
        ActionControl();
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
        if((!(Integer.parseInt(Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getFreecall()) <1))
        || (!Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getPackageSubscribed().equalsIgnoreCase("none"))){

            Global.utils.startMediaPlayer(DoctorConsultActivity.this, R.raw.dialing_tone);

            JSONObject params = new JSONObject();
            try {
                params.put("to", token);
                params.put("data", new JSONObject()
                        .put("title", "Incoming Audio Call")
                        .put("channel", callingID)
                        .put("body", Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getMobileNumber()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Global.utils.sendNotification(this, token, params);
            vSettings().mChannelName = callingID;
            Intent i = new Intent(this, AudioCallScreenActivity.class);
            i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, callingID);
            i.putExtra(ConstantApp.ACTION_KEY_USER_ACCOUNT, Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getEmail());
            //i.putExtra(ConstantApp.CALLED_USER, "waleed@webdoc.com.pk");
            i.putExtra(ConstantApp.ACTION_KEY_USER_TOKEN, "");
            startActivity(i);
        }else{
            FeedBackDialog();
        }


    }

    public void forwardToVideoRoom() {
        if((!(Integer.parseInt(Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getFreecall()) <1))
                || (!Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getPackageSubscribed().equalsIgnoreCase("none"))){

            Global.utils.startMediaPlayer(DoctorConsultActivity.this, R.raw.dialing_tone);

            JSONObject params = new JSONObject();
            try {
                params.put("to", token);
                params.put("data", new JSONObject()
                        .put("title", "Incoming Video Call")
                        .put("body", Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getMobileNumber()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        Global.utils.sendNotification(this, token, params);
        vSettings().mChannelName = callingID;
        Intent i = new Intent(this, VideoCallScreenActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, callingID);
        i.putExtra(ConstantApp.ACTION_KEY_USER_ACCOUNT, Global.getCustomerDataApiResponse.getGetcustomerDataResult().getCustomerData().getEmail());
        //i.putExtra(ConstantApp.CALLED_USER, "waleed@webdoc.com.pk");
        i.putExtra(ConstantApp.ACTION_KEY_USER_TOKEN, "");
        startActivity(i);
        }else{
            FeedBackDialog();
        }
    }

    @Override
    protected void deInitUIandEvent() {
    }

    private void ActionControl() {

        reference = FirebaseDatabase.getInstance().getReference().child("Tokens").child("Doctors");

        reference.child(Global.selectedDoctor.getEmail().replace(".","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                token = dataSnapshot.child("token").getValue().toString();
                Toast.makeText(DoctorConsultActivity.this, token, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        tv_name.setText(Global.selectedDoctor.getFirstName() + " " + Global.selectedDoctor.getLastName());
        tv_speciality.setText(Global.selectedDoctor.getDoctorSpecialty());
        Picasso.get().load(Global.selectedDoctor.getImgLink()).placeholder(R.color.gray_btn_bg_color).error(R.drawable.doctor).into(profile_image);

        btn_Audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Audio.setBackgroundResource(R.drawable.buttonborder_black_gray);
                btn_Audio.setTextColor(Color.parseColor("#d32e33"));
                btn_Video.setBackgroundResource(R.drawable.buttonborder_black);
                btn_Video.setTextColor(Color.parseColor("#000000"));
                btn_Text.setBackgroundResource(R.drawable.buttonborder_black);
                btn_Text.setTextColor(Color.parseColor("#000000"));
                final PrettyDialog pDialog = new PrettyDialog(DoctorConsultActivity.this);
                pDialog.setMessage("Are you sure you want to audio call to the doctor?")
                        .setAnimationEnabled(true)
                        .addButton(
                                "Yes",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_red,
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
                                } )
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
            }
        });

        btn_Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Video.setBackgroundResource(R.drawable.buttonborder_black_gray);
                btn_Video.setTextColor(Color.parseColor("#d32e33"));
                btn_Audio.setBackgroundResource(R.drawable.buttonborder_black);
                btn_Audio.setTextColor(Color.parseColor("#000000"));
                btn_Text.setBackgroundResource(R.drawable.buttonborder_black);
                btn_Text.setTextColor(Color.parseColor("#000000"));

                final PrettyDialog pDialog = new PrettyDialog(DoctorConsultActivity.this);
                pDialog.setMessage("Are you sure you want to video call to the doctor?")
                        .setAnimationEnabled(true)
                        .addButton(
                                "Yes",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_red,
                                new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        forwardToVideoRoom();
                                        pDialog.dismiss();
                                    }
                                } )
                        .addButton(
                                "No",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_gray,
                                new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        pDialog.dismiss();
                                    }
                                } )
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
            }
        });
    }

    private void InitControl() {
        callingID = Global.selectedDoctor.getEmail();
        tv_name = findViewById(R.id.tv_name);
        tv_speciality = findViewById(R.id.tv_speciality);
        profile_image = findViewById(R.id.user_image);
        btn_Text = findViewById(R.id.btn_Text);
        btn_Video = findViewById(R.id.btn_Video);
        btn_Audio = findViewById(R.id.btn_Audio);
    }

    private void FeedBackDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.alert_no_package_consult_doctor, null);
        dialogBuilder.setView(v);

        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        Button btnOkay = v.findViewById(R.id.btnDismiss_AlertNoPackage);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }//alert
}
