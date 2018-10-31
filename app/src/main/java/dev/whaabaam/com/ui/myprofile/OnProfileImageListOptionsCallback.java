package dev.whaabaam.com.ui.myprofile;

public interface OnProfileImageListOptionsCallback {
    void onProfileImageSelected();

    void onProfileImageRemoved(int position, int imageID);
    void onAddProfileImageRequested();
}
