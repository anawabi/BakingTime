package com.amannawabi.bakingtime.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer nID;
    @SerializedName("shortDescription")
    @Expose
    private String strShortDescription;
    @SerializedName("description")
    @Expose
    private String strDescription;
    @SerializedName("videoURL")
    @Expose
    private String strVideoURL;
    @SerializedName("thumbnailURL")
    @Expose
    private String strThumbnailURL;

    public Integer getId() {
        return nID;
    }

    public void setId(Integer nID) {
        this.nID = nID;
    }

    public String getShortDescription() {
        return strShortDescription;
    }

    public void setShortDescription(String strSD) {
        this.strShortDescription = strSD;
    }

    public String getDescription() {
        return strDescription;
    }

    public void setDescription(String strD) {
        this.strDescription = strD;
    }

    public String getVideoURL() {
        return strVideoURL;
    }

    public void setVideoURL(String strVURL) {
        this.strVideoURL = strVURL;
    }

    public String getThumbnailURL() {
        return strThumbnailURL;
    }

    public void setThumbnailURL(String strTURL) {
        this.strThumbnailURL = strTURL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.nID);
        dest.writeString(this.strShortDescription);
        dest.writeString(this.strDescription);
        dest.writeString(this.strVideoURL);
        dest.writeString(this.strThumbnailURL);
    }

    public Step() {
    }

    protected Step(Parcel in) {
        this.nID = (Integer) in.readValue(Integer.class.getClassLoader());
        this.strShortDescription = in.readString();
        this.strDescription = in.readString();
        this.strVideoURL = in.readString();
        this.strThumbnailURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}