package dev.whaabaam.com.data.model.other;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CloseContactsData {
    private User_info user_info;

    private String id;

    private String updated_at;

    private String address;

    private String capture_user_id;

    private String lng;

    private String user_id;

    private String lat;

    private String req_status;

    public User_info getUser_info() {
        return user_info;
    }

    public void setUser_info(User_info user_info) {
        this.user_info = user_info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCapture_user_id() {
        return capture_user_id;
    }

    public void setCapture_user_id(String capture_user_id) {
        this.capture_user_id = capture_user_id;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getReq_status() {
        return req_status;
    }

    public void setReq_status(String req_status) {
        this.req_status = req_status;
    }

    public static class User_info implements Parcelable {
        private String id;

        private String first_name;

        private String last_name;

        @SerializedName("image")
        private UserImages image;
        private String email;
        private String quickblox_id;


        protected User_info(Parcel in) {
            id = in.readString();
            first_name = in.readString();
            last_name = in.readString();
            email = in.readString();
            quickblox_id = in.readString();
        }

        public static final Creator<User_info> CREATOR = new Creator<User_info>() {
            @Override
            public User_info createFromParcel(Parcel in) {
                return new User_info(in);
            }

            @Override
            public User_info[] newArray(int size) {
                return new User_info[size];
            }
        };

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getQuickblox_id() {
            return quickblox_id;
        }

        public void setQuickblox_id(String quickblox_id) {
            this.quickblox_id = quickblox_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public UserImages getImage() {
            return image;
        }

        public void setImage(UserImages image) {
            this.image = image;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(first_name);
            dest.writeString(last_name);
            dest.writeString(email);
            dest.writeString(quickblox_id);
        }
    }
}
