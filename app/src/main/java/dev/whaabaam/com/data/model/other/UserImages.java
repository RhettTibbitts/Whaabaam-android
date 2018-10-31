package dev.whaabaam.com.data.model.other;

import android.os.Parcel;
import android.os.Parcelable;

public class UserImages implements Parcelable {
    private String org, thumb;

    public UserImages() {
    }

    private UserImages(Parcel in) {
        org = in.readString();
        thumb = in.readString();
    }

    public static final Creator<UserImages> CREATOR = new Creator<UserImages>() {
        @Override
        public UserImages createFromParcel(Parcel in) {
            return new UserImages(in);
        }

        @Override
        public UserImages[] newArray(int size) {
            return new UserImages[size];
        }
    };

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(org);
        dest.writeString(thumb);
    }
}
