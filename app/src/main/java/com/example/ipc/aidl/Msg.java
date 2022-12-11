package com.example.ipc.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Msg implements Parcelable {
    private int id;
    private String name;

    public Msg(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Msg(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Msg> CREATOR = new Creator<Msg>() {
        @Override
        public Msg createFromParcel(Parcel in) {
            return new Msg(in);
        }

        @Override
        public Msg[] newArray(int size) {
            return new Msg[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public void readFromParcel(Parcel dest){
        id = dest.readInt();
        name = dest.readString();
    }

    @Override
    public String toString() {
        return "Msg{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
