package com.wmalick.webdoc_library.Sinch;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import static android.content.Context.BIND_AUTO_CREATE;
import static org.webrtc.ContextUtils.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SinchBaseFragment extends Fragment implements ServiceConnection {

    private SinchService.SinchServiceInterface mSinchServiceInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService();
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (SinchService.class.getName().equals(componentName.getClassName())) {
            mSinchServiceInterface = (SinchService.SinchServiceInterface) iBinder;
            onServiceConnected();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        if (SinchService.class.getName().equals(componentName.getClassName())) {
            mSinchServiceInterface = null;
            onServiceDisconnected();
        }
    }

    protected void onServiceConnected() {
        // for subclasses
    }

    protected void onServiceDisconnected() {
        // for subclasses
    }

    protected SinchService.SinchServiceInterface getSinchServiceInterface() {
        return mSinchServiceInterface;
    }

    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SinchService.MESSAGE_PERMISSIONS_NEEDED:
                    Bundle bundle = msg.getData();
                    String requiredPermission = bundle.getString(SinchService.REQUIRED_PERMISSION);
                    ActivityCompat.requestPermissions(getActivity(), new String[]{requiredPermission}, 0);
                    break;
            }
        }
    });

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean granted = grantResults.length > 0;
        for (int grantResult : grantResults) {
            granted &= grantResult == PackageManager.PERMISSION_GRANTED;
        }
        if (granted) {
            Toast.makeText(getActivity(), "You may now place a call", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "This application needs permission to use your microphone and camera to function properly.", Toast.LENGTH_LONG).show();
        }
        mSinchServiceInterface.retryStartAfterPermissionGranted();
    }

    private void bindService() {
        Intent serviceIntent = new Intent(getActivity(), SinchService.class);
        serviceIntent.putExtra(SinchService.MESSENGER, messenger);
        getApplicationContext().bindService(serviceIntent, this, BIND_AUTO_CREATE);
    }
}
