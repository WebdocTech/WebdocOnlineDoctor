package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsult;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;
import com.wmalick.webdoc_library.Agora.AudioCallScreenActivity;
import com.wmalick.webdoc_library.Agora.BaseActivity;
import com.wmalick.webdoc_library.Agora.BaseFragment;
import com.wmalick.webdoc_library.Agora.ConstantApp;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;
import de.hdodenhof.circleimageview.CircleImageView;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class DoctorConsultFragment extends BaseActivity {
    TextView tv_name, tv_speciality;
    CircleImageView profile_image;
    Button btn_Text,btn_Video,btn_Audio;
    String callingID = "";

    public DoctorConsultFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_doctor_consult);
        InitControl();
        ActionControl();
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_doctor_consult, container, false);

        InitControl(view);
        ActionControl(view);

        return view;
    }*/

    @Override
    protected void initUIandEvent() {
        String lastChannelName = vSettings().mChannelName;
        if (!TextUtils.isEmpty(lastChannelName)) {
            /*v_channel.setText(lastChannelName);
            v_channel.setSelection(lastChannelName.length());*/
        }
    }

    public void onClickJoin(View view) {
    }

    public void forwardToRoom() {
        /*EditText v_channel = (EditText) findViewById(R.id.channel_name);
        String channel = v_channel.getText().toString();*/
        vSettings().mChannelName = callingID;

        Intent i = new Intent(this, AudioCallScreenActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, callingID);
        startActivity(i);
    }

    @Override
    protected void deInitUIandEvent() {
    }

    private void ActionControl() {
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
                final PrettyDialog pDialog = new PrettyDialog(DoctorConsultFragment.this);
                pDialog.setMessage("Are you sure you want to audio call to the doctor?")
                        .setAnimationEnabled(true)
                        .addButton(
                                "Yes",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_red,
                                new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        forwardToRoom();
                                        pDialog.dismiss();
                                        /*if (callingID.isEmpty()) {
                                            Toast.makeText(getActivity(), "Please enter a user to call", Toast.LENGTH_LONG).show();
                                            return;
                                        }*/
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

                final PrettyDialog pDialog = new PrettyDialog(DoctorConsultFragment.this);
                pDialog.setMessage("Are you sure you want to video call to the doctor?")
                        .setAnimationEnabled(true)
                        .addButton(
                                "Yes",
                                R.color.pdlg_color_white,
                                R.color.pdlg_color_red,
                                new PrettyDialogCallback() {
                                    @Override
                                    public void onClick() {
                                        pDialog.dismiss();
                                        if (callingID.isEmpty()) {
                                            Toast.makeText(DoctorConsultFragment.this, "Please enter a user to call", Toast.LENGTH_LONG).show();
                                            return;
                                        }
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
}
