package dev.whaabaam.com.data.remote;

import dev.whaabaam.com.BuildConfig;

/**
 * @author rahul
 */
public final class ApiEndPoint {


    public static final String BASE_URL = BuildConfig.WHAABAAM_BASE_URL;

    static final String ENDPOINT_LOGIN = BASE_URL + "/login";
    static final String ENDPOINT_MY_PROFILE = BASE_URL + "/profile";
    static final String ENDPOINT_LOGOUT = BASE_URL + "/logout";
    static final String ENDPOINT_REGISTER = BASE_URL + "/signup";
    static final String ENDPOINT_CHANGE_PASSWORD = BASE_URL + "/change-password";
    static final String ENDPOINT_FORGOT_PASSWORD = BASE_URL + "/forgot-password";
    static final String ENDPOINT_VERIFY_OTP = BASE_URL + "/forgot-password/verify";
    static final String ENDPOINT_RESET_PASSWORD = BASE_URL + "/forgot-password/set-password";
    public static final String ENDPOINT_UPLOAD_PROFILE_IMAGE = BASE_URL + "/profile/pic/upload";
    public static final String ENDPOINT_PROFILE_EDIT = BASE_URL + "/profile/edit";
    static final String ENDPOINT_DELETE_PROFILE = BASE_URL + "/profile/main-pic/delete";
    static final String ENDPOINT_DELETE_OTHER_PIC = BASE_URL + "/profile/pic/delete";
    static final String ENDPOINT_GET_NOTIF_PREFS = BASE_URL + "/capture-options";
    static final String ENDPOINT_SAVE_NOTIF_PREFS = BASE_URL + "/capture-options/edit";
    static final String ENDPOINT_MY_CONNECTIONS = BASE_URL + "/friends";
    static final String ENDPOINT_NOTIFICATIONS = BASE_URL + "/notifications";
    static final String ENDPOINT_CLOSE_CONTACTS = BASE_URL + "/captured-users";
    static final String ENDPOINT_FAMILY_LIST = BASE_URL + "/family-members";
    static final String ENDPOINT_REMOVE_FAMILY = BASE_URL + "/family-member/delete";
    static final String ENDPOINT_ADD_FAMILY = BASE_URL + "/family-member/add";
    static final String ENDPOINT_CONNECTION_LIST_FOR_FAMILY = BASE_URL + "/family/add-friend-list";
    static final String ENDPOINT_RELATION_LIST = BASE_URL + "/family/show-relations";
    static final String ENDPOINT_VIEW_OTHER_PROFILE = BASE_URL + "/profile/view";
    static final String ENDPOINT_VIEW_NOTES = BASE_URL + "/profile/notes";
    static final String ENDPOINT_ADD_NOTE = BASE_URL + "/profile/note/add";

    static final String ENDPOINT_SEND_CONNECTION_REQUEST = BASE_URL + "/friend-req/send";
    static final String ENDPOINT_WITHDRAW_CONNECTION_REQ = BASE_URL + "/friend-req/cancel";
    static final String ENDPOINT_RESPOND_CONNECTION_REQUEST = BASE_URL + "/friend-req/response";
    static final String ENDPOINT_REMOVE_CONNECTION = BASE_URL + "/friend/unfriend";
    static final String ENDPOINT_GET_CITIES = BASE_URL + "/get-cities";
    static final String ENDPOINT_CHECK_IN_CONNECTION = BASE_URL + "/friendship/check";
    static final String ENDPOINT_DELETE_RESUME = BASE_URL + "/profile/resume/delete";
    static final String END_POINT_MUTUAL_CONTACT = BASE_URL + "/mutual-friends";
    static final String END_POINT_REPORT_USER = BASE_URL + "/report";
    static final String END_POINT_SEARCH = BASE_URL + "/search";


    private ApiEndPoint() {
        // This class is not publicly instantiable
    }
}
