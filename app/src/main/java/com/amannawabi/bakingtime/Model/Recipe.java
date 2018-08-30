package com.amannawabi.bakingtime.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer nID;
    @SerializedName("name")
    @Expose
    private String strName;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> lstIngredients;
    @SerializedName("steps")
    @Expose
    private List<Step> lstSteps = null;
    @SerializedName("servings")
    @Expose
    private Integer nServings;
    @SerializedName("image")
    @Expose
    private String strImage;
    private int nImageId;

    public Integer getId() {
        return nID;
    }

    public void setId(Integer nID) {
        this.nID = nID;
    }

    public String getName() {
        return strName;
    }

    public void setName(String strN) {
        this.strName = strN;
    }

    public List<Ingredient> getIngredients() {
        return lstIngredients;
    }

    public void setIngredients(List<Ingredient> lstI) {
        this.lstIngredients = lstI;
    }

    public List<Step> getSteps() {
        return lstSteps;
    }

    public void setSteps(List<Step> lstS) {
        this.lstSteps = lstS;
    }

    public Integer getServings() {
        return nServings;
    }

    public void setServings(Integer nS) {
        this.nServings = nS;
    }

    public String getImage() {
        return strImage;
    }

    public void setImage(String strI) {
        this.strImage = strI;
    }

    public void setImageId(int imageId) {
        this.nImageId = imageId;
    }

    public int getImageId () {
        return nImageId;
    }

    public ArrayList<String> getShortDescriptionsFromSteps (){
        ArrayList<String> alResult = new ArrayList<>();
        alResult.add("Recipe Ingredients");
        for (int i = 0; i < lstSteps.size(); i++){
            alResult.add(lstSteps.get(i).getShortDescription());
        }
        return alResult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.nID);
        dest.writeString(this.strName);
        dest.writeList(this.lstIngredients);
        dest.writeList(this.lstSteps);
        dest.writeValue(this.nServings);
        dest.writeString(this.strImage);
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        this.nID = (Integer) in.readValue(Integer.class.getClassLoader());
        this.strName = in.readString();
        this.lstIngredients = new ArrayList<>();
        in.readList(this.lstIngredients, Ingredient.class.getClassLoader());
        this.lstSteps = new ArrayList<>();
        in.readList(this.lstSteps, Step.class.getClassLoader());
        this.nServings = (Integer) in.readValue(Integer.class.getClassLoader());
        this.strImage = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}