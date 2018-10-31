package dev.whaabaam.com.data.model.other;

import android.os.Parcel;
import android.os.Parcelable;

public class Relationships implements Parcelable {
    private int id;
    private String name;

    protected Relationships(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Relationships> CREATOR = new Creator<Relationships>() {
        @Override
        public Relationships createFromParcel(Parcel in) {
            return new Relationships(in);
        }

        @Override
        public Relationships[] newArray(int size) {
            return new Relationships[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}
