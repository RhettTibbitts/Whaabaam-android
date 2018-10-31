package dev.whaabaam.com.data.model.other;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/*
 * Created by RahulGupta on 11/9/18
 */

/**
 * @author rahul
 */
public class UserProfileData {

    private String insta_link, linked_in_link;

    private String college;

    @SerializedName("state")
    private States state;

    private String alma_matter;

    private String education;

    @SerializedName("military")
    private Militaries military;


    @SerializedName("city")
    private Cities city;

    private String id;
    @SerializedName("religion")
    private Religions religion;

    private String first_name;

    private Family family;

    private String quickblox_id;

    private Mutual_friends mutual_friends;

    private String occupation;

    @SerializedName("political")
    private Politicals political;

    private UserImages image;

    private String fb_link;

    private String work_website;

    private String twit_link;

    @SerializedName("relationship")
    private Relationships relationship;

    private String resume;

    private String email;

    private String high_school;

    private String likes;

    private String last_name, req_status, phone;

    private ArrayList<UserModel.Images> images;


    @SerializedName("from_state")
    private FromState fromState;
    @SerializedName("from_city")
    private FromCity fromCity;


    public FromState getFromState() {
        return fromState;
    }

    public void setFromState(FromState fromState) {
        this.fromState = fromState;
    }

    public FromCity getFromCity() {
        return fromCity;
    }

    public void setFromCity(FromCity fromCity) {
        this.fromCity = fromCity;
    }

    public String getLinked_in_link() {
        return linked_in_link;
    }

    public void setLinked_in_link(String linked_in_link) {
        this.linked_in_link = linked_in_link;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReq_status() {
        return req_status;
    }

    public void setReq_status(String req_status) {
        this.req_status = req_status;
    }

    public String getInsta_link() {
        return insta_link;
    }

    public void setInsta_link(String insta_link) {
        this.insta_link = insta_link;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public String getAlma_matter() {
        return alma_matter;
    }

    public void setAlma_matter(String alma_matter) {
        this.alma_matter = alma_matter;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Militaries getMilitary() {
        return military;
    }

    public void setMilitary(Militaries military) {
        this.military = military;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Religions getReligion() {
        return religion;
    }

    public void setReligion(Religions religion) {
        this.religion = religion;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public String getQuickblox_id() {
        return quickblox_id;
    }

    public void setQuickblox_id(String quickblox_id) {
        this.quickblox_id = quickblox_id;
    }

    public Mutual_friends getMutual_friends() {
        return mutual_friends;
    }

    public void setMutual_friends(Mutual_friends mutual_friends) {
        this.mutual_friends = mutual_friends;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Politicals getPolitical() {
        return political;
    }

    public void setPolitical(Politicals political) {
        this.political = political;
    }

    public UserImages getImage() {
        return image;
    }

    public void setImage(UserImages image) {
        this.image = image;
    }

    public String getFb_link() {
        return fb_link;
    }

    public void setFb_link(String fb_link) {
        this.fb_link = fb_link;
    }

    public String getWork_website() {
        return work_website;
    }

    public void setWork_website(String work_website) {
        this.work_website = work_website;
    }

    public String getTwit_link() {
        return twit_link;
    }

    public void setTwit_link(String twit_link) {
        this.twit_link = twit_link;
    }

    public Relationships getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationships relationship) {
        this.relationship = relationship;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHigh_school() {
        return high_school;
    }

    public void setHigh_school(String high_school) {
        this.high_school = high_school;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public ArrayList<UserModel.Images> getImages() {
        return images;
    }

    public void setImages(ArrayList<UserModel.Images> images) {
        this.images = images;
    }

    public static class Family {
        private int last_page, total;

        private int per_page;

        private ArrayList<Data> data;

        private int current_page;

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public static class Data {
            private FamilyData.User_info user_info;

            private String id;

            private String another_user_id;

            private String family_relation_id;

            private String other_relation_detail;

            private FamilyData.Relation relation;

            private String user_id;

            private String friend_req_id;

            public FamilyData.User_info getUser_info() {
                return user_info;
            }

            public void setUser_info(FamilyData.User_info user_info) {
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

            public FamilyData.Relation getRelation() {
                return relation;
            }

            public void setRelation(FamilyData.Relation relation) {
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
        }
    }

    public static class Mutual_friends {
        private int last_page, total;

        private int per_page;

        private ArrayList<Data> data;

        private int current_page;

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public static class Data {
            private String first_name;

            private String last_name;

            @SerializedName("image")
            private UserImages image;

            private String friend_user_id;

            private String image_path;

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


        }
    }

    public static class FromState {
        private int id, country_id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class FromCity {
        private int id, state_id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getState_id() {
            return state_id;
        }

        public void setState_id(int state_id) {
            this.state_id = state_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
