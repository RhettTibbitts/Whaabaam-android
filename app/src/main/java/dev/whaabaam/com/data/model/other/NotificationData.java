package dev.whaabaam.com.data.model.other;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;

public class NotificationData {
    private String last_page;

    private String message;

    private String per_page;

    private String status;

    private ArrayList<Data> data;

    private String current_page;

    public String getLast_page() {
        return last_page;
    }

    public void setLast_page(String last_page) {
        this.last_page = last_page;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public static class Data {
        private String message;

        private String id;

        private String event_type;

        private String created_at;

        private Concerned_user concerned_user;

        private String req_status;

        private String profile_user_id;

        public Data() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEvent_type() {
            return event_type;
        }

        public void setEvent_type(String event_type) {
            this.event_type = event_type;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public Concerned_user getConcerned_user() {
            return concerned_user;
        }

        public void setConcerned_user(Concerned_user concerned_user) {
            this.concerned_user = concerned_user;
        }

        public String getReq_status() {
            return req_status;
        }

        public void setReq_status(String req_status) {
            this.req_status = req_status;
        }

        public String getProfile_user_id() {
            return profile_user_id;
        }

        public void setProfile_user_id(String profile_user_id) {
            this.profile_user_id = profile_user_id;
        }

        public static class Concerned_user {
            private String id;

            private String first_name;

            private String last_name;

            @SerializedName("image")
            private UserImages image;

            private String email, quickblox_id;

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
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Objects.equals(id, data.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }


}
