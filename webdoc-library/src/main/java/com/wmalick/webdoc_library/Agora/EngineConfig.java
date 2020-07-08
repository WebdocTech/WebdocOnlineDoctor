package com.wmalick.webdoc_library.Agora;

public class EngineConfig {
    public int mUid;

    public String mChannel;
    public String mToken;
    public String mUserAccount;

    public void reset() {
        mChannel = null;
        /*mToken = null;
        mUserAccount = null;*/
    }

    EngineConfig() {
    }
}
