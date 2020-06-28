package com.wmalick.webdoc_library.Sinch;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.calling.CallState;
import com.sinch.android.rtc.video.VideoController;
import com.wmalick.webdoc_library.R;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public class SinchCallScreen extends SinchBaseActivity {

    ImageView ivMicroPhone, ivCam, ivRotateCamera, ivSpeaker, ivEndCall;
    TextView mCallDuration, mCallState, mCallerName;



    static final String TAG = SinchCallScreen.class.getSimpleName();
    private AudioPlayer mAudioPlayer;
    private Timer mTimer;
    private UpdateCallDurationTask mDurationTask;
    private String mCallId;
    private String mCallType;
    private static Boolean isMuteOn = false, isSpeakerOn = false, isCamOn = false;

    private boolean mAddedListener = false;
    private boolean mLocalVideoViewAdded = false;
    private boolean mRemoteVideoViewAdded = false;
    boolean mToggleVideoViewPositions = false;
    static final String ADDED_LISTENER = "addedListener";
    static final String VIEWS_TOGGLED = "viewsToggled";


    private class UpdateCallDurationTask extends TimerTask {
        @Override
        public void run() {
            SinchCallScreen.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallDuration();
                }
            });
        }
    }

    /*@Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(ADDED_LISTENER, mAddedListener);
        savedInstanceState.putBoolean(VIEWS_TOGGLED, mToggleVideoViewPositions);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mAddedListener = savedInstanceState.getBoolean(ADDED_LISTENER);
        mToggleVideoViewPositions = savedInstanceState.getBoolean(VIEWS_TOGGLED);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinch_call_screen);

        InitiliseControls();
        ActionControls();
    }

    private void InitiliseControls() {

        ivMicroPhone = findViewById(R.id.ivMicroPhone_SichCallScreenActivity);
        ivCam = findViewById(R.id.ivCam_SichCallScreenActivity);
        ivEndCall = findViewById(R.id.ivEndCall_SinchCallScreenActivity);
        ivRotateCamera = findViewById(R.id.ivRotateCamera_SichCallScreenActivity);
        ivSpeaker = findViewById(R.id.ivSpeaker_SichCallScreenActivity);
        mCallDuration = findViewById(R.id.tvDuration_SinchCallScreenActivity);
        mCallerName = findViewById(R.id.tvUsername_SinchCallScreenActivity);
        mCallState = findViewById(R.id.tvCallStatus_SinchCallScreenActivity);



        mAudioPlayer = new AudioPlayer(this);
        ButterKnife.bind(this);
        ivMicroPhone.setImageDrawable(getDrawable(R.drawable.ic_mic_on_black_24dp));
        ivCam.setImageDrawable(getDrawable(R.drawable.ic_videocam_off_black_24dp));
        ivRotateCamera.setImageDrawable(getDrawable(R.drawable.ic_rotate_camera_black_24dp));
        ivSpeaker.setImageDrawable(getDrawable(R.drawable.ic_speaker_on_black_24dp));

        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
        mCallType = getIntent().getStringExtra(SinchService.CALL_TYPE);
        if(mCallType.equalsIgnoreCase("audio")){
            findViewById(R.id.LocalUserVideoView_CallScreenActivity).setVisibility(View.GONE);
        }else{
            findViewById(R.id.LocalUserVideoView_CallScreenActivity).setVisibility(View.VISIBLE);
        }
    }

    private void ActionControls() {
        ivMicroPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMuteOn){
                    ivMicroPhone.setImageDrawable(getDrawable(R.drawable.ic_mic_off_black_24dp));
                    getSinchServiceInterface().getAudioController().mute();
                    isMuteOn = true;
                }else{
                    ivMicroPhone.setImageDrawable(getDrawable(R.drawable.ic_mic_on_black_24dp));
                    getSinchServiceInterface().getAudioController().unmute();
                    isMuteOn = false;
                }

            }
        });

        ivSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSpeakerOn){
                    ivSpeaker.setImageDrawable(getDrawable(R.drawable.ic_speaker_off_black_24dp));
                    getSinchServiceInterface().getAudioController().enableSpeaker();
                    isSpeakerOn = true;
                }else{
                    ivSpeaker.setImageDrawable(getDrawable(R.drawable.ic_speaker_on_black_24dp));
                    getSinchServiceInterface().getAudioController().disableSpeaker();
                    isSpeakerOn = false;
                }
            }
        });

        ivEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endCall();
            }
        });
    }

    @Override
    public void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            if(call.getDetails().isVideoOffered()){
                if (!mAddedListener) {
                    call.addCallListener(new SinchCallListener());
                    mAddedListener = true;
                    updateUI();
                }
            }else{
                call.addCallListener(new SinchCallListener());
                mCallerName.setText(call.getRemoteUserId());
                mCallState.setText(call.getState().toString());
            }

        } else {
            Log.e(TAG, "Started with invalid callId, aborting.");
            finish();
        }
    }

    private void updateUI() {
        if (getSinchServiceInterface() == null) {
            return; // early
        }

        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            mCallerName.setText(call.getRemoteUserId());
            mCallState.setText(call.getState().toString());
            if (call.getDetails().isVideoOffered()) {
                if (call.getState() == CallState.ESTABLISHED) {
                    setVideoViewsVisibility(true, true);
                } else {
                    setVideoViewsVisibility(true, false);
                }
            }
        } else {
            setVideoViewsVisibility(false, false);
        }
    }

    private void setVideoViewsVisibility(final boolean localVideoVisibile, final boolean remoteVideoVisible) {
        if (getSinchServiceInterface() == null)
            return;
        if (mRemoteVideoViewAdded == false) {
            addRemoteView();
        }
        if (mLocalVideoViewAdded == false) {
            addLocalView();
        }
        final VideoController vc = getSinchServiceInterface().getVideoController();
        if (vc != null) {
            runOnUiThread(() -> {
                vc.getLocalView().setVisibility(localVideoVisibile ? View.VISIBLE : View.GONE);
                vc.getRemoteView().setVisibility(remoteVideoVisible ? View.VISIBLE : View.GONE);
            });
        }
    }

    private void addRemoteView() {
        if (mRemoteVideoViewAdded || getSinchServiceInterface() == null) {
            return; //early
        }
        final VideoController vc = getSinchServiceInterface().getVideoController();
        if (vc != null) {
            runOnUiThread(() -> {
                ViewGroup remoteView = getVideoView(false);
                remoteView.addView(vc.getRemoteView());
                remoteView.setOnClickListener((View v) -> {
                    removeVideoViews();
                    mToggleVideoViewPositions = !mToggleVideoViewPositions;
                    addRemoteView();
                    addLocalView();
                });
                mRemoteVideoViewAdded = true;
                vc.setLocalVideoZOrder(!mToggleVideoViewPositions);
            });
        }
    }



    private void removeVideoViews() {
        if (getSinchServiceInterface() == null) {
            return; // early
        }

        if(getSinchServiceInterface().getCall(mCallId).getDetails().isVideoOffered()){
            VideoController vc = getSinchServiceInterface().getVideoController();
            if (vc != null) {
                runOnUiThread(() -> {
                    ((ViewGroup)(vc.getRemoteView().getParent())).removeView(vc.getRemoteView());
                    ((ViewGroup)(vc.getLocalView().getParent())).removeView(vc.getLocalView());
                    mLocalVideoViewAdded = false;
                    mRemoteVideoViewAdded = false;
                });
            }
        }


    }

    private ViewGroup getVideoView(boolean localView) {
        if (mToggleVideoViewPositions) {
            localView = !localView;
        }
        return localView ? findViewById(R.id.LocalUserVideoView_CallScreenActivity) : findViewById(R.id.RemoteUserVideoView_CallScreenActivity);
    }

    private void addLocalView() {
        if (mLocalVideoViewAdded || getSinchServiceInterface() == null) {
            return; //early
        }
        final VideoController vc = getSinchServiceInterface().getVideoController();
        if (vc != null) {
            runOnUiThread(() -> {
                ViewGroup localView = getVideoView(true);
                localView.addView(vc.getLocalView());
                localView.setOnClickListener(v -> vc.toggleCaptureDevicePosition());
                mLocalVideoViewAdded = true;
                vc.setLocalVideoZOrder(!mToggleVideoViewPositions);
            });
        }
    }

    @Override
    public void onBackPressed() {
        // User should exit activity by ending call, not by going back.
    }

    @Override
    public void onPause() {
        super.onPause();
        mDurationTask.cancel();
        mTimer.cancel();
        removeVideoViews();
    }

    @Override
    public void onDestroy()
    {
        if(getSinchServiceInterface() != null){
            endCall();
        }
        super.onDestroy();
        //Do whatever you want to do when the application is destroyed.
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Timer();
        mDurationTask = new UpdateCallDurationTask();
        mTimer.schedule(mDurationTask, 0, 500);
        updateUI();
    }

    /*@Override
    public void onStop() {
        super.onStop();
        mDurationTask.cancel();
        mTimer.cancel();
        if(mCallType.equals("video")){
            removeVideoViews();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mTimer = new Timer();
        mDurationTask = new UpdateCallDurationTask();
        mTimer.schedule(mDurationTask, 0, 500);
        updateUI();
    }*/

    private void endCall() {
        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }
        finish();
    }

    private String formatTimespan(int totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    private void updateCallDuration() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            mCallDuration.setText(formatTimespan(call.getDetails().getDuration()));


        }
    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallEnded(Call call) {
            mAudioPlayer.stopProgressTone();
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            CallEndCause cause = call.getDetails().getEndCause();

            if(call.getDetails().getEndCause().getValue() == 1){
                mCallState.setText("User Not Available!");
            }else if(call.getDetails().getEndCause().getValue() == 2){
                mCallState.setText("Call Declined!!");
            }else if(call.getDetails().getEndCause().getValue() == 3){
                mCallState.setText("Not Answering!");
            }else if(call.getDetails().getEndCause().getValue() == 4){
                if(call.getDetails().getError().getCode() == 1002){
                    mCallState.setText("Internet Issue");
                }else if(call.getDetails().getError().getCode() == 4000){
                    mCallState.setText("User not found!");
                }
            }else if(call.getDetails().getEndCause().getValue() == 6){
                mCallState.setText("Call Ended!");
            }else if(call.getDetails().getEndCause().getValue() == 6){
                mCallState.setText("Call Cancelled!");
            }


            Log.d(TAG, "Call ended. Reason: " + cause.toString());
            Log.d(TAG, "Call ended. Cause: " + call.getDetails().getEndCause().getValue());
            if(call.getDetails().getError() != null){
                Log.d(TAG, "Call ended. Cause: " + call.getDetails().getError().toString());
                Log.d(TAG, "Call ended. Error Code: " + call.getDetails().getError().getCode());
                Log.d(TAG, "Call ended. Error Type: " + call.getDetails().getError().getErrorType());
                Log.d(TAG, "Call ended. Error Message: " + call.getDetails().getError().getMessage());
            }

            String endMsg = "Call ended: " + call.getDetails().toString();
            //Toast.makeText(SinchCallScreen.this, endMsg, Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    endCall();
                }
            },3000);


        }

        @Override
        public void onCallEstablished(Call call) {
            Log.d(TAG, "Call established");
            mAudioPlayer.stopProgressTone();
            mCallState.setText(call.getState().toString());
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            AudioController audioController = getSinchServiceInterface().getAudioController();
            if (call.getDetails().isVideoOffered()) {
                audioController.enableSpeaker();
                setVideoViewsVisibility(true, true);
            }
            audioController.disableSpeaker();
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
            mAudioPlayer.playProgressTone();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // no need to implement if you use managed push
        }

    }
}
