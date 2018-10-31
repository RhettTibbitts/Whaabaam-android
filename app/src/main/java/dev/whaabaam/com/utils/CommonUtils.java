package dev.whaabaam.com.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.util.Preconditions;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import dev.whaabaam.com.R;
import dev.whaabaam.com.app.AppKeys;
import dev.whaabaam.com.app.AppPreferences;
import dev.whaabaam.com.app.MyApplication;
import dev.whaabaam.com.data.model.other.CloseContactsData;
import dev.whaabaam.com.data.model.other.FilterData;
import dev.whaabaam.com.data.model.other.UserModel;
import dev.whaabaam.com.services.LocationUpdateServices;
import dev.whaabaam.com.services.QuickbloxPushService;
import dev.whaabaam.com.ui.base.BaseActivity;
import dev.whaabaam.com.ui.home.chatlist.ChatListFragment;
import dev.whaabaam.com.ui.home.closecontacts.CloseContactsFragment;
import dev.whaabaam.com.ui.home.notifications.NotificationsFragment;
import dev.whaabaam.com.ui.home.settings.SettingsFragment;

import static android.content.Context.NOTIFICATION_SERVICE;
import static dev.whaabaam.com.app.AppConstants.BASE_FILES_PATH;
import static dev.whaabaam.com.app.MyApplication.userModel;

public class CommonUtils {

    public static boolean isValidEmail(String email) {
        return (!isEmpty(email) && email.matches(String.valueOf(Patterns.EMAIL_ADDRESS)));
    }

    public static boolean isValidPassword(String password) {
        return !isEmpty(password);
    }

    static boolean isEmpty(String input) {
        return TextUtils.isEmpty(input);
    }

    public static void setAstericPasswordTransformation(EditText password) {
        password.setTransformationMethod(new AsteriskPasswordTransformationUtils());
    }

    public static String getMonthNameByDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.getDefault());
        return sdf.format(date) + " ";

    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        return calendar.get(Calendar.MONTH);
    }

    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    @SuppressLint("RestrictedApi")
    public static String getDayOfMonthWithSuffix(final int n) {
        Preconditions.checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return n + "th ";
        }
        switch (n % 10) {
            case 1:
                return n + "st ";
            case 2:
                return n + "nd ";
            case 3:
                return n + "rd ";
            default:
                return n + "th ";
        }
    }

    public static String getDDMMYY(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
        return sdf.format(date);
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String currentTimeHHMM() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        if (manager != null)
            info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();

    }

    public static void startNewTaskInstance(Context context, Class<?> cl) {
        Intent intent = new Intent(context, cl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        ((AppCompatActivity) context).finish();
    }

    //    public static Class<?> getClassFromJSON(String json, Class<?> type) {
//        return new Gson().fromJson(json, type.getClass());
//    }
    public static void storeStringInPrefs(Context mContext, String key, String value) {
        AppPreferences.getInstance(mContext).storeString(key, value);
    }

    public static void storeIntInPrefs(Context mContext, String key, int value) {
        AppPreferences.getInstance(mContext).storeInt(key, value);
    }

    public static void storeBooleanInPrefs(Context mContext, String key, boolean value) {
        AppPreferences.getInstance(mContext).storeBoolean(key, value);
    }

    public static String getStringFromPrefs(Context mContext, String key) {
        return AppPreferences.getInstance(mContext).getString(key);
    }

    public static int getIntFromPrefs(Context mContext, String key) {
        return AppPreferences.getInstance(mContext).getInt(key);
    }

    public static boolean getBooleanFromPrefs(Context mContext, String key) {
        return AppPreferences.getInstance(mContext).getBoolean(key);
    }

    public static void requestLocationUpdateTask(Context context) {
        context.startService(new Intent(context, LocationUpdateServices.class));
    }

    public static boolean intToBoolean(int m) {
        return m != 0;
    }

    public static Date getDateFromString(String time) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        return format.parse(time);
    }

    public static String getConnectionRequestText(String name) {
        return "You are sending a connection request to " + name + ". Are you sure you want to move ahead? ";
    }

    public static String getConnectionCancelText(String name) {
        return "You are about to cancel a connection request sent to " + name + ". Are you sure you want to move ahead? ";
    }

    public static String getConnectionRemoveText(String name) {
        return "You are about to remove " + name + " from your connection. Are you sure you want to move ahead? ";
    }

    public static void saveRememberMeData(Context context, String email, String password, boolean isChecked) {
        String s1 = isChecked ? email : "";
        String s2 = isChecked ? password : "";
        AppPreferences.getInstance(context).storeString(AppKeys.EMAIL, s1);
        AppPreferences.getInstance(context).storeString(AppKeys.PASSWORD, s2);
        AppPreferences.getInstance(context).storeBoolean(AppPreferences.PREF_KEYS.IS_REMEMBER_CHECKED, isChecked);
    }

    public static String getRememberedData(Activity mContext, int i) {
        if (i == 1) {
            return AppPreferences.getInstance(mContext).getString(AppKeys.EMAIL);
        } else
            return AppPreferences.getInstance(mContext).getString(AppKeys.PASSWORD);
    }

    public static void switchBottomTabs(FragmentManager fragmentManager, MenuItem menuItem) {

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                if (!(fragment instanceof CloseContactsFragment)) {
                    replaceFragment(new CloseContactsFragment(), false, fragmentManager);
                    menuItem.setChecked(true);
                }
                break;
            case R.id.nav_notifications:
                if (!(fragment instanceof NotificationsFragment)) {
                    replaceFragment(new NotificationsFragment(), false, fragmentManager);
                }
                break;
            case R.id.nav_settings:
                if (!(fragment instanceof SettingsFragment)) {
                    replaceFragment(new SettingsFragment(), false, fragmentManager);
                }
                break;
            case R.id.nav_chats:
                if (!(fragment instanceof ChatListFragment)) {
                    replaceFragment(new ChatListFragment(), false, fragmentManager);
                }
                break;
            default:
                break;
        }
    }

    private static void replaceFragment(Fragment fragment, boolean addToBackStack, FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.setCustomAnimations(R.anim.entry, R.anim.right_to_left, R.anim.exit, R.anim.left_to_right);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack("");
        }
        fragmentTransaction.commit();
    }

    public static void handleOptionSelections(Context context, CloseContactsData data) {

    }

    public static String getTime(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static String getDate(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd", Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static long getDateAsHeaderId(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        return Long.parseLong(dateFormat.format(new Date(milliseconds)));
    }

    public static String secondsToTimeStamp(long seconds) {
        if (seconds > 0) {
            Date d = new Date(seconds);
            PrettyTime prettyTime = new PrettyTime();
            return prettyTime.format(d);
        } else {
            return "";
        }
//        String dt = new SimpleDateFormat("dd-MM-yyyy hh:MM:ss", Locale.getDefault()).format(d);
//        return dt;
    }

    public static void sendNotificationForNewMessage(Context context, QBChatMessage message, String dialogID) {

        QBRestChatService.getChatDialogById(dialogID).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                int notificationID = new Random().nextInt(100);
                getQBNotification(context, message.getBody(),
                        qbChatDialog.getName(), notificationID);
//

            }

            @Override
            public void onError(QBResponseException e) {
                e.printStackTrace();

            }
        });


    }

    public static void getQBNotification(Context context, String message, String dialogName, int notificationID) {
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context, String.valueOf(notificationID));
        builder.setOngoing(true)
                .setContentTitle(dialogName)
                .setSmallIcon(R.drawable.logo_small)
                .setOngoing(false)
                .setAutoCancel(true)
                .setContentText(message)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo));

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(3245, builder.build());
    }

    public static void saveImageToStorage(ImageView imageView, InputStream in, String fileUid) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), MyApplication.APP_NAME);
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        String filePath = BASE_FILES_PATH + fileUid + ".jpg";
        File file = new File(filePath);
        new AsyncTask<Void, Void, Void>() {

//            Bitmap bitmap;


            @Override
            protected Void doInBackground(Void... voids) {


                OutputStream out = null;

                try {
                    out = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
//                    bitmap = BitmapFactory.decodeStream(in);


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // Ensure that the InputStreams are closed even if there's an exception.
                    try {
                        if (out != null) {
                            out.close();
                        }

                        // If you want to close the "in" InputStream yourself then remove this
                        // from here but ensure that you close it yourself eventually.
//                in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (!((AppCompatActivity) imageView.getContext()).isDestroyed()) {
                    Glide.with(imageView.getContext()).load(file).into(imageView);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }

    public static String getChatFilePath(String id) {
        return BASE_FILES_PATH + id + ".jpg";
    }

    public static boolean chatImageExist(String id) {
        File file = new File(getChatFilePath(id));
        if (file.exists())
            return true;
        else
            return false;
    }

    public static ArrayList<FilterData> getRawFilterPreference() {

        /* 'city_id','state_id','occupation','education','high_school','college','alma_matter','relationship_id'*/

        ArrayList<FilterData> filterData = new ArrayList<>();
        filterData.add(new FilterData("Hide Connections", 13, false, "hide_friends"));
        filterData.add(new FilterData("Hide Strangers", 14, false, "hide_strangers"));
        filterData.add(new FilterData("City", 1, false, "city_id"));
        filterData.add(new FilterData("State", 2, false, "state_id"));
        filterData.add(new FilterData("Occupation", 3, false, "occupation"));
        filterData.add(new FilterData("Education", 4, false, "education"));
        filterData.add(new FilterData("High School", 5, false, "high_school"));
        filterData.add(new FilterData("College", 6, false, "college"));
        filterData.add(new FilterData("Alma Matter", 7, false, "alma_matter"));
        filterData.add(new FilterData("Likes, Hobbies, Interests", 8, false, "likes_id"));
        filterData.add(new FilterData("Military", 9, false, "military_id"));
        filterData.add(new FilterData("Political", 10, false, "political_id"));
        filterData.add(new FilterData("Religion", 11, false, "religion_id"));
        filterData.add(new FilterData("Relationship Status", 12, false, "relationship_id"));

        return filterData;
    }

    public static ArrayList<String> getUserFilters() {
        ArrayList<String> a = new ArrayList<>();
        for (FilterData d : MyApplication.filterPreferences) {
            if (d.isChecked()) {
                a.add(d.getKey());
            }
        }
        return a;
    }

    //    public static String[] getFiltersArray(ArrayList<String> list) {
//        String[] s = new String[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            s[i] = list.get(i);
//        }
//        return s;
//    }
    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
//        Log.d(Constants.TAG, String.format("Service:%s", runningServiceInfo.service.getClassName()));
            if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())) {
                return true;
            }
        }
        return false;
    }

    public static JSONObject getSendContactRequestBody(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put("receiver_user_id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getWithdrawOrDeleteRequestBody(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put("friend_user_id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getWithdrawOrDeleteRequestQB(int quickbloxId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());
            jsonObject.put("friend_quickblox_id", quickbloxId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static String getAcceptRejectText(String userName) {
        return "Are you sure, you want to accept " + userName + " request? ";
    }

    public static String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());
    }

    public static void saveUserData(UserModel userModel) {
        AppPreferences.getInstance(MyApplication.context).storeString(AppPreferences.PREF_KEYS.USER_DATA,
                new Gson().toJson(userModel));
    }

    public static Bundle getFirstRunBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppPreferences.PREF_KEYS.IS_FIRST_RUN, true);
        return bundle;
    }

    public static JSONObject getRequestBodyWithUserID() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppKeys.USER_ID, userModel.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getAcceptRejectRequestBody(boolean value, String capture_user_id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("another_user_id", capture_user_id);
            jsonObject.put("user_id", userModel.getId());
            jsonObject.put("action", value ? "A" : "R");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static boolean hasPermission(Context context, String permission) {

        int res = context.checkCallingOrSelfPermission(permission);

//        Log.v(TAG, "permission: " + permission + " = \t\t" +
//                (res == PackageManager.PERMISSION_GRANTED ? "GRANTED" : "DENIED"));

        return res == PackageManager.PERMISSION_GRANTED;

    }

    /**
     * Determines if the context calling has the required permissions
     *
     * @param context     - the IPC context
     * @param permissions - The permissions to check
     * @return true if the IPC has the granted permission
     */
    public static boolean hasPermissions(Context context, String... permissions) {

        boolean hasAllPermissions = true;

        for (String permission : permissions) {
            //you can return false instead of assigning, but by assigning you can log all permission values
            if (!hasPermission(context, permission)) {
                hasAllPermissions = false;
            }
        }

        return hasAllPermissions;

    }


}
