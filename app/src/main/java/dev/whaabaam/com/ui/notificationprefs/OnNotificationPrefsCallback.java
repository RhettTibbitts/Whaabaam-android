package dev.whaabaam.com.ui.notificationprefs;

import dev.whaabaam.com.data.model.other.FilterData;

public interface OnNotificationPrefsCallback {
    void onPrefsSelected(FilterData data, int position);
}
