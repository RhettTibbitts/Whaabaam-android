package dev.whaabaam.com.data.model.other;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;

public class UserModel {

    @SerializedName("image")
    private UserImages image;

    private int relationship_id, from_state_id, from_city_id, from_city_id_access;

    private String capture_time_period;

    private int occupation_access;

    private int education_access;

    private int name_access;

    private int military_id_access;

    private int city_id_access;

    private int last_name_access;

    private String alma_matter;

    private String education;

    private int likes_access;

    private int political_id_access;

    private int city_id;

    private int id;

    private int state_id;

    private String first_name;

    private int political_id;

    private int religion_id;

    private String created_at;

    private int military_id;

    private int work_website_access;

    private String occupation;

    private int relationship_id_access;

    private String status;

    @Nullable
    private String deleted_at;

    private int religion_id_access;

    private int email_access, is_profile_updated;

    private int alma_matter_access;

    private String work_website;

    private String capture_distance;

    private String resume;

    private String updated_at;

    private String email;

    private String likes;

    private String last_name;
    private ArrayList<Images> images;

    private String high_school;
    private int high_school_access;
    private String college;
    private int college_access;
    private String quickblox_id;
    private String fb_link, insta_link, twit_link, phone, linked_in_link;
    private int fb_link_access, insta_link_access, twit_link_access, family_access, phone_access, linked_in_link_access;


    public int getFrom_state_id() {
        return from_state_id;
    }

    public void setFrom_state_id(int from_state_id) {
        this.from_state_id = from_state_id;
    }

    public int getFrom_city_id() {
        return from_city_id;
    }

    public void setFrom_city_id(int from_city_id) {
        this.from_city_id = from_city_id;
    }

    public int getFrom_city_id_access() {
        return from_city_id_access;
    }

    public void setFrom_city_id_access(int from_city_id_access) {
        this.from_city_id_access = from_city_id_access;
    }

    public String getLinked_in_link() {
        return linked_in_link;
    }

    public void setLinked_in_link(String linked_in_link) {
        this.linked_in_link = linked_in_link;
    }

    public int getLinked_in_link_access() {
        return linked_in_link_access;
    }

    public void setLinked_in_link_access(int linked_in_link_access) {
        this.linked_in_link_access = linked_in_link_access;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPhone_access() {
        return phone_access;
    }

    public void setPhone_access(int phone_access) {
        this.phone_access = phone_access;
    }

    public int getFamily_access() {
        return family_access;
    }

    public void setFamily_access(int family_access) {
        this.family_access = family_access;
    }

    public int getIs_profile_updated() {
        return is_profile_updated;
    }

    public void setIs_profile_updated(int is_profile_updated) {
        this.is_profile_updated = is_profile_updated;
    }

    public int getLast_name_access() {
        return last_name_access;
    }

    public void setLast_name_access(int last_name_access) {
        this.last_name_access = last_name_access;
    }

    public String getFb_link() {
        return fb_link;
    }

    public void setFb_link(String fb_link) {
        this.fb_link = fb_link;
    }

    public String getInsta_link() {
        return insta_link;
    }

    public void setInsta_link(String insta_link) {
        this.insta_link = insta_link;
    }

    public String getTwit_link() {
        return twit_link;
    }

    public void setTwit_link(String twit_link) {
        this.twit_link = twit_link;
    }

    public int getFb_link_access() {
        return fb_link_access;
    }

    public void setFb_link_access(int fb_link_access) {
        this.fb_link_access = fb_link_access;
    }

    public int getInsta_link_access() {
        return insta_link_access;
    }

    public void setInsta_link_access(int insta_link_access) {
        this.insta_link_access = insta_link_access;
    }

    public int getTwit_link_access() {
        return twit_link_access;
    }

    public void setTwit_link_access(int twit_link_access) {
        this.twit_link_access = twit_link_access;
    }

    public String getQuickblox_id() {
        return quickblox_id;
    }

    public void setQuickblox_id(String quickblox_id) {
        this.quickblox_id = quickblox_id;
    }

    public int getRelationship_id() {
        return relationship_id;
    }

    public void setRelationship_id(int relationship_id) {
        this.relationship_id = relationship_id;
    }

    public String getCapture_time_period() {
        return capture_time_period;
    }

    public void setCapture_time_period(String capture_time_period) {
        this.capture_time_period = capture_time_period;
    }

    public int getOccupation_access() {
        return occupation_access;
    }

    public void setOccupation_access(int occupation_access) {
        this.occupation_access = occupation_access;
    }

    public int getEducation_access() {
        return education_access;
    }

    public void setEducation_access(int education_access) {
        this.education_access = education_access;
    }

    public int getName_access() {
        return name_access;
    }

    public void setName_access(int name_access) {
        this.name_access = name_access;
    }

    public int getMilitary_id_access() {
        return military_id_access;
    }

    public void setMilitary_id_access(int military_id_access) {
        this.military_id_access = military_id_access;
    }

    public int getCity_id_access() {
        return city_id_access;
    }

    public void setCity_id_access(int city_id_access) {
        this.city_id_access = city_id_access;
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

    public int getLikes_access() {
        return likes_access;
    }

    public void setLikes_access(int likes_access) {
        this.likes_access = likes_access;
    }

    public int getPolitical_id_access() {
        return political_id_access;
    }

    public void setPolitical_id_access(int political_id_access) {
        this.political_id_access = political_id_access;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getPolitical_id() {
        return political_id;
    }

    public void setPolitical_id(int political_id) {
        this.political_id = political_id;
    }

    public int getReligion_id() {
        return religion_id;
    }

    public void setReligion_id(int religion_id) {
        this.religion_id = religion_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getMilitary_id() {
        return military_id;
    }

    public void setMilitary_id(int military_id) {
        this.military_id = military_id;
    }

    public int getWork_website_access() {
        return work_website_access;
    }

    public void setWork_website_access(int work_website_access) {
        this.work_website_access = work_website_access;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getRelationship_id_access() {
        return relationship_id_access;
    }

    public void setRelationship_id_access(int relationship_id_access) {
        this.relationship_id_access = relationship_id_access;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public int getReligion_id_access() {
        return religion_id_access;
    }

    public void setReligion_id_access(int religion_id_access) {
        this.religion_id_access = religion_id_access;
    }

    public int getEmail_access() {
        return email_access;
    }

    public void setEmail_access(int email_access) {
        this.email_access = email_access;
    }

    public int getAlma_matter_access() {
        return alma_matter_access;
    }

    public void setAlma_matter_access(int alma_matter_access) {
        this.alma_matter_access = alma_matter_access;
    }

    public String getWork_website() {
        return work_website;
    }

    public void setWork_website(String work_website) {
        this.work_website = work_website;
    }

    public String getCapture_distance() {
        return capture_distance;
    }

    public void setCapture_distance(String capture_distance) {
        this.capture_distance = capture_distance;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public ArrayList<Images> getImages() {
        return images;
    }

    public void setImages(ArrayList<Images> images) {
        this.images = images;
    }

    public UserImages getImage() {
        return image;
    }

    public void setImage(UserImages image) {
        this.image = image;
    }

    public String getHigh_school() {
        return high_school;
    }

    public void setHigh_school(String high_school) {
        this.high_school = high_school;
    }

    public int getHigh_school_access() {
        return high_school_access;
    }

    public void setHigh_school_access(int high_school_access) {
        this.high_school_access = high_school_access;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getCollege_access() {
        return college_access;
    }

    public void setCollege_access(int college_access) {
        this.college_access = college_access;
    }

    public static class Images implements Parcelable {
        private int id;

        @SerializedName("name")
        private UserImages userImages;

        private int user_id;

        public Images(UserImages userImages) {
            this.userImages = userImages;
        }

        public Images() {
            userImages = new UserImages();
        }


        protected Images(Parcel in) {
            id = in.readInt();
            userImages = in.readParcelable(UserImages.class.getClassLoader());
            user_id = in.readInt();
        }

        public static final Creator<Images> CREATOR = new Creator<Images>() {
            @Override
            public Images createFromParcel(Parcel in) {
                return new Images(in);
            }

            @Override
            public Images[] newArray(int size) {
                return new Images[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public UserImages getUserImages() {
            return userImages;
        }

        public void setUserImages(UserImages userImages) {
            this.userImages = userImages;
        }


        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeParcelable(userImages, flags);
            dest.writeInt(user_id);
        }
    }
}
