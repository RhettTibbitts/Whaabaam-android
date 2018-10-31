package dev.whaabaam.com.data.model.other;
/*
 * Created by RahulGupta on 27/8/18
 */

import com.google.gson.annotations.SerializedName;

public class FamilyData {
    private User_info user_info;

    private String id;

    private String another_user_id;

    private String family_relation_id;

    private String other_relation_detail;

    private Relation relation;

    private String user_id;

    private String friend_req_id;

    public FamilyData(String id) {
        this.id = id;
    }

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

    public String getAnother_user_id() {
        return another_user_id;
    }

    public void setAnother_user_id(String another_user_id) {
        this.another_user_id = another_user_id;
    }

    public String getFamily_relation_id() {
        return family_relation_id;
    }

    public void setFamily_relation_id(String family_relation_id) {
        this.family_relation_id = family_relation_id;
    }

    public String getOther_relation_detail() {
        return other_relation_detail;
    }

    public void setOther_relation_detail(String other_relation_detail) {
        this.other_relation_detail = other_relation_detail;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFriend_req_id() {
        return friend_req_id;
    }

    public void setFriend_req_id(String friend_req_id) {
        this.friend_req_id = friend_req_id;
    }

    public static class User_info {
        private String id;

        private String first_name;

        private String last_name;

        @SerializedName("image")
        private UserImages image;

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
    }

    public static class Relation {
        private String id;

        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
