package dev.whaabaam.com.app;

import android.os.Environment;

public class AppConstants {

    public static String[] STORAGE_PERMISSIONS = {"android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"};
    public static String[] GPS_PERMISSIONS = {"android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION"};

    public static String[] ALL_PERMISSIONS = {"android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.CALL_PHONE"};
    public static String IMAGE_PATH_INTENT = "image_path_intent";
    public static String IMAGE_SOURCE = "image_source";
    public static String IMAGE_PATH = "image_path";
    public static String ACTION_PICK_PROFILE_IMAGE = "pick_profile_image";
    public static String ACTION_PICK_EXTRA_PROFILE_IMAGE = "pick_extra_profile_image";
    public static String DEVICE_TYPE = "A";
    public static String LOCATION_UPDATE_WORK = "location_update_work";
    public static String LOGOUT_INTENT = "logout_intent";
    public static String SHOW_REMOVE_OPTION = "show_remove";
    public static String BASE_FILES_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/" + MyApplication.APP_NAME + "/";


    public static final int RESULT_LOAD_IMAGE = 9999;


    public enum TIME {
        PREVIOUS, FUTURE
    }

    public enum API_MODE {
        LOGIN, REGISTER, LOGOUT, MY_PROFILE, CHANGE_PASSWORD, FORGOT_PASSWORD_REQUEST,
        FORGOT_PASSWORD_VERIFY_OTP, RESET_PASSWORD,
        DELETE_PROFILE_PIC, DELETE_OTHER_PIC, SET_DEFAULT_IMAGE,
        NOTIFICATION_PREFS, SAVE_NOTIFICATION_PREFS, GET_CAPTURED_USERS,
        GET_CONNECTION_LIST, GET_NOTIFICATIONS, MY_CONNECTIONS,
        FAMILY_LIST, ADD_FAMILY_MEMBER, REMOVE_FAMILY_MEMBER, CONNECTION_LIST_FOR_FAMILY,
        REQUEST_RELATION_OPTIONS, OTHER_PROFILE_DATA, VIEW_NOTES, ADD_NOTES,
        SEND_CONTACT_REQUEST, WITHDRAW_CONTACT_REQUEST, RESPOND_TO_CONTACT_REQUEST,
        REMOVE_CONNECTION, GET_CITIES, CHECK_IN_CONNECTION, DELETE_RESUME, REPORT_USER,
        MUTUAL_CONTACTS, SEARCH
    }

    public enum IMAGE_PICKER_SOURCE {
        PROFILE_IMAGE, OTHER_PROFILE_IMAGES, CHAT_ATTACHMENT
    }

    public enum QUICKBLOX_STATUS {
        CONNECTED, AUTHENTICATED, CLOSED, RECONNECTING
    }

    public class USER_REQUEST_STATUS {
        public static final String NEW_USER = "NEW_USER";
        public static final String REQ_SENT = "REQ_SENT";
        public static final String REQ_RECEIVED = "REQ_RECEIVED";
        public static final String FRIEND = "FRIEND";
    }
}
