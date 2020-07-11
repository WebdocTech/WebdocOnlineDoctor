package com.wmalick.webdoc_library.Agora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.wmalick.webdoc_library.R;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

public class VideoCallScreenActivity extends BaseActivity implements AGEventHandler {

    public static final String TAG = VideoCallScreenActivity.class.getSimpleName();

    public static final int PERMISSION_REQ_ID = 22;

    public static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //public RtcEngine mRtcEngine;
    public boolean mCallEnd;
    public boolean mMuted;

    public FrameLayout mLocalContainer;
    public RelativeLayout mRemoteContainer;
    public SurfaceView mLocalView;
    public SurfaceView mRemoteView;

    public ImageView mCallBtn;
    public ImageView mMuteBtn;
    public ImageView mSwitchCameraBtn;

    String userName, channelName;


    public void setupRemoteVideo(int uid) {
        int count = mRemoteContainer.getChildCount();
        View view = null;
        for (int i = 0; i < count; i++) {
            View v = mRemoteContainer.getChildAt(i);
            if (v.getTag() instanceof Integer && ((int) v.getTag()) == uid) {
                view = v;
            }
        }

        if (view != null) {
            return;
        }


        mRemoteView = rtcEngine().CreateRendererView(getBaseContext());
        mRemoteContainer.addView(mRemoteView);
        // Initializes the video view of a remote user.
        rtcEngine().setupRemoteVideo(new VideoCanvas(mRemoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
        mRemoteView.setTag(uid);
    }

    public void onRemoteUserLeft() {
        removeRemoteVideo();
    }

    public void removeRemoteVideo() {
        if (mRemoteView != null) {
            mRemoteContainer.removeView(mRemoteView);
        }
        // Destroys remote view
        mRemoteView = null;
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call_screen);

        initUI();
        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID) &&
                checkSelfPermission(REQUESTED_PERMISSIONS[2], PERMISSION_REQ_ID)) {
            initEngineAndJoinChannel();
        }
    }

    @Override
    protected void initUIandEvent() {
        /*event().addEventHandler(this);
        Intent i = getIntent();
        channelName = i.getStringExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME);
        userName = i.getStringExtra(ConstantApp.ACTION_KEY_USER_ACCOUNT);
        calledUser = i.getStringExtra(ConstantApp.CALLED_USER);
        String token = i.getStringExtra(ConstantApp.ACTION_KEY_USER_TOKEN);

        *//*
          Allows a user to join a channel.
          Users in the same channel can talk to each other, and multiple users in the same channel can start a group chat. Users with different App IDs cannot call each other.
          You must call the leaveChannel method to exit the current call before joining another channel.
          A successful joinChannel method call triggers the following callbacks:
          The local client: onJoinChannelSuccess.
          The remote client: onUserJoined, if the user joining the channel is in the Communication profile, or is a BROADCASTER in the Live Broadcast profile.
          When the connection between the client and Agora's server is interrupted due to poor
          network conditions, the SDK tries reconnecting to the server. When the local client
          successfully rejoins the channel, the SDK triggers the onRejoinChannelSuccess callback
          on the local client.
         *//*
        //worker().joinChannel(channelName, config().mUid);
        worker().joinChannelWithUserAccount(token, channelName, userName);
        TextView textChannelName = (TextView) findViewById(R.id.channel_name);
        textChannelName.setText(channelName);*/
    }

    @Override
    protected void deInitUIandEvent() {
        //optionalDestroy();
        endCall();
        event().removeEventHandler(this);
    }

    public void initUI() {
        mLocalContainer = findViewById(R.id.local_video_view_container);
        mRemoteContainer = findViewById(R.id.remote_video_view_container);

        mCallBtn = findViewById(R.id.btn_call);
        mMuteBtn = findViewById(R.id.btn_mute);
        mSwitchCameraBtn = findViewById(R.id.btn_switch_camera);

        mCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endCall();
            }
        });

        //     mLogView = findViewById(R.id.log_recycler_view);

        // Sample logs are optional.
        showSampleLogs();
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQ_ID) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                showLongToast("Need permissions " + Manifest.permission.RECORD_AUDIO +
                        "/" + Manifest.permission.CAMERA + "/" + Manifest.permission.WRITE_EXTERNAL_STORAGE);
                finish();
                return;
            }

            initEngineAndJoinChannel();
        }
    }

    /*public void showLongToast(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }*/

    public void initEngineAndJoinChannel() {

        event().addEventHandler(this);
        Intent i = getIntent();
        channelName = i.getStringExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME);
        userName = i.getStringExtra(ConstantApp.ACTION_KEY_USER_ACCOUNT);
        //calledUser = i.getStringExtra(ConstantApp.CALLED_USER);
        String token = i.getStringExtra(ConstantApp.ACTION_KEY_USER_TOKEN);
        //initializeEngine();
        setupVideoConfig();
        setupLocalVideo();
        joinChannel();
    }

    /*public void initializeEngine() {
        try {
            rtcEngine() = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }*/

    public void setupVideoConfig() {
        rtcEngine().enableVideo();
        // Please go to this page for detailed explanation
        // https://docs.agora.io/en/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_rtc_engine.html#af5f4de754e2c1f493096641c5c5c1d8f
        rtcEngine().setVideoEncoderConfiguration(new VideoEncoderConfiguration(
                VideoEncoderConfiguration.VD_640x360,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT));
    }

    public void setupLocalVideo() {
        mLocalView = RtcEngine.CreateRendererView(getBaseContext());
        mLocalView.setZOrderMediaOverlay(true);
        mLocalContainer.addView(mLocalView);
        rtcEngine().setupLocalVideo(new VideoCanvas(mLocalView, VideoCanvas.RENDER_MODE_HIDDEN, 0));
    }

    public void joinChannel()
    {
       /* String token = getString(R.string);
        if (TextUtils.isEmpty(token) || TextUtils.equals(token, getString(R.string.agora_access_token))) {
            token = null; // default, no token
        }*/
        /*String token = "006f0924810ffd04733b7a726cb961157cdIAC4SEYp3wYwsHSn4I9b6aQK4h8s9N8TKgKnVL0CSNY0b+LcsooAAAAAEACqC04C3oL8XgEAAQDdgvxe";
        rtcEngine().joinChannelWithUserAccount("", channelName, userName);*/

        rtcEngine().joinChannelWithUserAccount("", channelName, userName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mCallEnd)
        {leaveChannel();}

        rtcEngine().destroy();
    }

    public void leaveChannel() {
        rtcEngine().leaveChannel();
        finish();
    }

    public void onLocalAudioMuteClicked(View view) {
        mMuted = !mMuted;
        // Stops/Resumes sending the local audio stream.
        rtcEngine().muteLocalAudioStream(mMuted);
        int res = mMuted ? R.drawable.btn_mute : R.drawable.btn_unmute;
        mMuteBtn.setImageResource(res);
    }

    public void showSampleLogs() {
      /*  mLogView.logI("Welcome to Agora 1v1 video call");
        mLogView.logW("You will see custom logs here");
        mLogView.logE("You can also use this to show errors");*/
    }

    public void onSwitchCameraClicked(View view) {
        // Switches between front and rear cameras.
        rtcEngine().switchCamera();
    }



    public void endCall() {
        removeLocalVideo();
        removeRemoteVideo();
        leaveChannel();
    }

    public void removeLocalVideo() {
        if (mLocalView != null) {
            mLocalContainer.removeView(mLocalView);
        }
        mLocalView = null;
    }

    public void showButtons(boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        mMuteBtn.setVisibility(visibility);
        mSwitchCameraBtn.setVisibility(visibility);
    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        Toast.makeText(this, "onJoinChannelSuccess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserOffline(int uid, int reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //    mLogView.logI("User offline, uid: " + (uid & 0xFFFFFFFFL));
                onRemoteUserLeft();
            }
        });
    }

    @Override
    public void onExtraCallback(int type, Object... data) {
        int peerUid;
        boolean muted;
        switch (type) {
            case AGEventHandler.EVENT_TYPE_ON_USER_AUDIO_MUTED: {
                peerUid = (Integer) data[0];
                muted = (boolean) data[1];
                //notifyMessageChanged("mute: " + (peerUid & 0xFFFFFFFFL) + " " + muted);
                break;
            }

            case AGEventHandler.EVENT_TYPE_ON_AUDIO_QUALITY: {
                peerUid = (Integer) data[0];
                int quality = (int) data[1];
                short delay = (short) data[2];
                short lost = (short) data[3];

                //notifyMessageChanged("quality: " + (peerUid & 0xFFFFFFFFL) + " " + quality + " " + delay + " " + lost);
                break;
            }

            case AGEventHandler.EVENT_TYPE_ON_SPEAKER_STATS: {
                IRtcEngineEventHandler.AudioVolumeInfo[] infos = (IRtcEngineEventHandler.AudioVolumeInfo[]) data[0];

                if (infos.length == 1 && infos[0].uid == 0) { // local guy, ignore it
                    break;
                }

                StringBuilder volumeCache = new StringBuilder();
                for (IRtcEngineEventHandler.AudioVolumeInfo each : infos) {
                    peerUid = each.uid;
                    int peerVolume = each.volume;
                    if (peerUid == 0) {
                        continue;
                    }
                    volumeCache.append("volume: ").append(peerUid & 0xFFFFFFFFL).append(" ").append(peerVolume).append("\n");
                }

                if (volumeCache.length() > 0) {
                    String volumeMsg = volumeCache.substring(0, volumeCache.length() - 1);
                    //notifyMessageChanged(volumeMsg);
                    if ((System.currentTimeMillis() / 1000) % 10 == 0) {
                        //log.debug(volumeMsg);
                    }
                }
                break;
            }

            case AGEventHandler.EVENT_TYPE_ON_APP_ERROR: {
                int subType = (int) data[0];
                if (subType == ConstantApp.AppError.NO_NETWORK_CONNECTION) {
                    showLongToast(getString(R.string.msg_no_network_connection));
                }
                break;
            }

            case AGEventHandler.EVENT_TYPE_ON_AGORA_MEDIA_ERROR: {
                int error = (int) data[0];
                String description = (String) data[1];
                //notifyMessageChanged(error + " " + description);
                break;
            }

            case AGEventHandler.EVENT_TYPE_ON_AUDIO_ROUTE_CHANGED: {
                //notifyHeadsetPlugged((int) data[0]);
                break;
            }
        }
    }

    @Override
    public void onUserLeaveChannel(IRtcEngineEventHandler.RtcStats stats) {
        String x= "Adsflkdas";
        endCall();
    }

    @Override
    public void onUserJoinChannel(int uid, int elapsed) {
        int abc = uid;
    }

    @Override
    public void onRtcStatsChangeEveryTwoSeconds(IRtcEngineEventHandler.RtcStats stats) {
        int x = 20;
    }

    @Override
    public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //  mLogView.logI("First remote video decoded, uid: " + (uid & 0xFFFFFFFFL));
                setupRemoteVideo(uid);
            }
        });
    }
}