package dev.whaabaam.com.data.model.other;
/*
 * Created by RahulGupta on 27/8/18
 */

public class MyConnectionsData {
    private String first_name;

    private String time;

    private String address;

    private String last_name;
    private String image;

    private String friend_user_id;

    private String image_path;

    private String thumb_path;
    private String friend_request_id, quickblox_id;


    public String getQuickblox_id() {
        return quickblox_id;
    }

    public void setQuickblox_id(String quickblox_id) {
        this.quickblox_id = quickblox_id;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public void setThumb_path(String thumb_path) {
        this.thumb_path = thumb_path;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFriend_user_id() {
        return friend_user_id;
    }

    public void setFriend_user_id(String friend_user_id) {
        this.friend_user_id = friend_user_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getFriend_request_id() {
        return friend_request_id;
    }

    public void setFriend_request_id(String friend_request_id) {
        this.friend_request_id = friend_request_id;
    }
}
