package com.wmalick.webdoc_library.Agora;

public class ConstantApp {
    public static final String APP_BUILD_DATE = "today";

    /*public static final String AGORA_APP_ID = "f6e6a403884949ca8199d53d07914f44";*/

    public static final String AGORA_APP_ID = "f0924810ffd04733b7a726cb961157cd";

    public static final int BASE_VALUE_PERMISSION = 0X0001;
    public static final int PERMISSION_REQ_ID_RECORD_AUDIO = BASE_VALUE_PERMISSION + 1;
    public static final int PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE = BASE_VALUE_PERMISSION + 3;

    public static class PrefManager {
        public static final String PREF_PROPERTY_UID = "pOCXx_uid";
    }

    public static final String ACTION_KEY_CHANNEL_NAME = "ecHANEL";
    public static final String ACTION_KEY_USER_TOKEN = "etOKEN";
    public static final String ACTION_KEY_USER_ACCOUNT = "euSERACCOUNT";
    public static final String CALLED_USER = "called_User";

    public static class AppError {
        public static final int NO_NETWORK_CONNECTION = 3;
    }
}
