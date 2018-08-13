package com.amannawabi.bakingtime.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    @SerializedName("quantity")
    @Expose
    private Double dQuantity;
    @SerializedName("measure")
    @Expose
    private String strMeasure;
    @SerializedName("ingredient")
    @Expose
    private String strIngredient;

    public Ingredient(Double dQ, String strM, String strI) {
        this.dQuantity = dQ;
        this.strMeasure = strM;
        this.strIngredient = strI;
    }
    public Double getQuantity() {
        return dQuantity;
    }

    public void setQuantity(Double dQ) {
        this.dQuantity = dQ;
    }

    public String getMeasure() {
        return strMeasure;
    }

    public void setMeasure(String strM) {
        this.strMeasure = strM;
    }

    public String getIngredient() {
        return strIngredient;
    }

    public void setIngredient(String strI) {
        this.strIngredient = strI;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.dQuantity);
        dest.writeString(this.strMeasure);
        dest.writeString(this.strIngredient);
    }

    public Ingredient() {
    }

    protected Ingredient(Parcel in) {
        this.dQuantity = (Double) in.readValue(Double.class.getClassLoader());
        this.strMeasure = in.readString();
        this.strIngredient = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
