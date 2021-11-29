package com.wmalick.webdoc_library.AgoraNew.AudioCall;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;
import com.wmalick.webdoc_library.Agora.ConstantApp;
import com.wmalick.webdoc_library.AgoraNew.CallService.CallService;
import com.wmalick.webdoc_library.AgoraNew.CallService.CheckServiceStatus;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.models.UserInfo;

public class VoiceCall extends AppCompatActivity implements SensorEventListener {

    String channelName;
    TextView tv_call_status, tv_call_time;
    int call_seconds, ringing_seconds = 0;
    Handler call_time_handler = new Handler();
    Handler ringing_handler = new Handler();
    Runnable runnable, ringing_runnable;
    CircleImageView profile_image;
    boolean callEndClicked = false;

    private SensorManager mSensorManager;
    private Sensor mProximity;
    private static final int SENSOR_SENSITIVITY = 4;

    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;
    private int field = 0x00000020;


    private static final String LOG_TAG = VoiceCall.class.getSimpleName();

    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;

    private RtcEngine mRtcEngine; // Tutorial Step 1
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1

        /**
         * Occurs when a remote user (Communication)/host (Live Broadcast) leaves the channel.
         *
         * There are two reasons for users to become offline:
         *
         *     Leave the channel: When the user/host leaves the channel, the user/host sends a goodbye message. When this message is received, the SDK determines that the user/host leaves the channel.
         *     Drop offline: When no data packet of the user or host is received for a certain period of time (20 seconds for the communication profile, and more for the live broadcast profile), the SDK assumes that the user/host drops offline. A poor network connection may lead to false detections, so we recommend using the Agora RTM SDK for reliable offline detection.
         *
         * @param uid ID of the user or host who
         * leaves
         * the channel or goes offline.
         * @param reason Reason why the user goes offline:
         *
         *     USER_OFFLINE_QUIT(0): The user left the current channel.
         *     USER_OFFLINE_DROPPED(1): The SDK timed out and the user dropped offline because no data packet was received within a certain period of time. If a user quits the call and the message is not passed to the SDK (due to an unreliable channel), the SDK assumes the user dropped offline.
         *     USER_OFFLINE_BECOME_AUDIENCE(2): (Live broadcast only.) The client role switched from the host to the audience.
         */
        @Override
        public void onUserOffline(final int uid, final int reason) { // Tutorial Step 4
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserLeft(uid, reason);
                    quitCall();
                }
            });
        }

        /**
         * Occurs when a remote user stops/resumes sending the audio stream.
         * The SDK triggers this callback when the remote user stops or resumes sending the audio stream by calling the muteLocalAudioStream method.
         *
         * @param uid ID of the remote user.
         * @param muted Whether the remote user's audio stream is muted/unmuted:
         *
         *     true: Muted.
         *     false: Unmuted.
         */
        @Override
        public void onUserMuteAudio(final int uid, final boolean muted) { // Tutorial Step 6
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserVoiceMuted(uid, muted);
                }
            });
        }


        @Override
        public void onUserJoined(int uid, int elapsed) {
            super.onUserJoined(uid, elapsed);

            int a = uid;
            UserInfo userInfo = new UserInfo();
            userInfo.uid = uid;
            //String abc = worker().getUserInfoByUid(uid,  userInfo);

            ringing_handler.removeCallbacks(ringing_runnable);

            call_time_handler.postDelayed(runnable = new Runnable() {
                public void run() {
                    //do something
                    call_seconds++;

                    String call_duration = convertSeconds(call_seconds);
                    tv_call_time.setText(call_duration);

                    startService(call_duration);

                    call_time_handler.postDelayed(runnable, 1000);
                }
            }, 1000);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_call_status.setText("Connected");
                }
            });

            Global.utils.stopMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            field = PowerManager.class.getField("PROXIMITY_SCREEN_OFF_WAKE_LOCK").getInt(null);
        } catch (Throwable ignored) {
        }

        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(field, getLocalClassName());

        setContentView(R.layout.activity_voice_call);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        startService("Ringing...");

        tv_call_status = (TextView) findViewById(R.id.tv_call_status);
        tv_call_time = (TextView) findViewById(R.id.tv_call_time);
        TextView textChannelName = (TextView) findViewById(R.id.channel_name);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);

        Intent i = getIntent();
        channelName = i.getStringExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME);
        textChannelName.setText(channelName);

        Picasso.get().load(R.drawable.ic_placeholder_doctor).placeholder(R.color.gray_btn_bg_color).error(R.drawable.ic_user_black_24dp).into(profile_image);

        if (tv_call_status.getText().equals("Ringing")) {
            ringing_handler.postDelayed(ringing_runnable = new Runnable() {
                public void run() {
                    ringing_seconds++;
                    String call_duration = convertSeconds(ringing_seconds);

                    if (call_duration.equalsIgnoreCase("00:00:20")) {
                        Global.call_not_answered = true;
                        quitCall();
                    } else {
                        ringing_handler.postDelayed(ringing_runnable, 1000);
                    }
                }
            }, 1000);
        }


        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO)) {
            initAgoraEngineAndJoinChannel();
        }

        Global.utils.startMediaPlayer(VoiceCall.this, R.raw.dialing_tone);

        /*Starts ringing on earpiece by default*/
        mRtcEngine.setEnableSpeakerphone(false);
    }

    private void initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine();     // Tutorial Step 1
        joinChannel();               // Tutorial Step 2
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        Log.i(LOG_TAG, "checkSelfPermission " + permission + " " + requestCode);
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        Log.i(LOG_TAG, "onRequestPermissionsResult " + grantResults[0] + " " + requestCode);

        switch (requestCode) {
            case PERMISSION_REQ_ID_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAgoraEngineAndJoinChannel();
                } else {
                    showLongToast("No permission for " + Manifest.permission.RECORD_AUDIO);
                    finish();
                }
                break;
            }
        }
    }

    public final void showLongToast(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        leaveChannel();
        RtcEngine.destroy();
        mRtcEngine = null;
    }

    // Tutorial Step 7
    public void onLocalAudioMuteClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
            iv.setColorFilter(Color.parseColor(Global.THEME_COLOR_CODE), PorterDuff.Mode.MULTIPLY);
        }

        // Stops/Resumes sending the local audio stream.
        mRtcEngine.muteLocalAudioStream(iv.isSelected());
    }

    // Tutorial Step 5
    public void onSwitchSpeakerphoneClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
            iv.setColorFilter(Color.parseColor(Global.THEME_COLOR_CODE), PorterDuff.Mode.MULTIPLY);
        }

        // Enables/Disables the audio playback route to the speakerphone.
        //
        // This method sets whether the audio is routed to the speakerphone or earpiece. After calling this method, the SDK returns the onAudioRouteChanged callback to indicate the changes.
        mRtcEngine.setEnableSpeakerphone(view.isSelected());
    }

    // Tutorial Step 3
    public void onEncCallClicked(View view) {
        callEndClicked = true;
        finish();
        quitCall();
    }

    // Tutorial Step 1
    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
            // Sets the channel profile of the Agora RtcEngine.
            // CHANNEL_PROFILE_COMMUNICATION(0): (Default) The Communication profile. Use this profile in one-on-one calls or group calls, where all users can talk freely.
            // CHANNEL_PROFILE_LIVE_BROADCASTING(1): The Live-Broadcast profile. Users in a live-broadcast channel have a role as either broadcaster or audience. A broadcaster can both send and receive streams; an audience can only receive streams.
            mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
        } catch (Exception e) {
            Log.e(LOG_TAG, Log.getStackTraceString(e));

            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    // Tutorial Step 2
    private void joinChannel() {
        String accessToken = "";
        if (TextUtils.equals(accessToken, "") || TextUtils.equals(accessToken, "")) {
            accessToken = null; // default, no token
        }

        // Allows a user to join a channel.
        mRtcEngine.joinChannel(accessToken, channelName, "Extra Optional Data", 0); // if you do not specify the uid, we will generate the uid for you
    }

    // Tutorial Step 3
    private void leaveChannel() {
        mRtcEngine.leaveChannel();
    }

    // Tutorial Step 4
    private void onRemoteUserLeft(int uid, int reason) {
        //showLongToast(String.format(Locale.US, "user %d left %d", (uid & 0xFFFFFFFFL), reason));
        /*View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
        tipMsg.setVisibility(View.VISIBLE);*/
    }

    // Tutorial Step 6
    private void onRemoteUserVoiceMuted(int uid, boolean muted) {
        //showLongToast(String.format(Locale.US, "user %d muted or unmuted %b", (uid & 0xFFFFFFFFL), muted));
    }

    private void quitCall() {
        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/

        if (CheckServiceStatus.isServiceRunningInForeground(this, CallService.class)) {
            stopService();
        }

        if (Global.utils.mediaPlayer != null) {
            Global.utils.stopMediaPlayer();
        }
        ringing_handler.removeCallbacks(ringing_runnable);
        call_time_handler.removeCallbacks(runnable);

        if (tv_call_status.getText().equals("Ringing")) {
            if (!callEndClicked) {
                Global.call_not_answered = true;
            } else {
                Global.call_not_answered = false;
            }
            missedCallNotification();
        }

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        quitCall();
    }

    private void missedCallNotification() {
        JSONObject params = new JSONObject();
        try {
            params.put("to", Global.selectedDoctorDeviceToken);
            params.put("data", new JSONObject()
                    .put("title", "Missed Audio Call")
                    .put("body", Global.getCustomerDataModel.getGetcustomerDataResult().getCustomerData().getEmail())
                    .put("channel", channelName));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Global.utils.sendNotification(this, params);
    }

    String convertSeconds(int sec) {
        int seconds = sec % 60;
        int minutes = sec / 60;
        if (minutes >= 60) {
            int hours = minutes / 60;
            minutes %= 60;
            if (hours >= 24) {
                int days = hours / 24;
                return String.format("%d days %02d:%02d:%02d", days, hours % 24, minutes, seconds);
            }
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("00:%02d:%02d", minutes, seconds);
    }

    private void startService(String body) {
        Intent serviceIntent = new Intent(this, CallService.class);
        serviceIntent.putExtra("title", "Audio Call");
        serviceIntent.putExtra("body", body);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void stopService() {
        Intent serviceIntent = new Intent(this, CallService.class);
        stopService(serviceIntent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] < mProximity.getMaximumRange()) {
                if (!wakeLock.isHeld()) {
                    wakeLock.acquire();
                }
            } else {
                if (wakeLock.isHeld()) {
                    wakeLock.release();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, 2 * 1000 * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
