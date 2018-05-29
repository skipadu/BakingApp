package com.pihrit.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    public static final String PARCELABLE_ID = "recipe";

    // No real use; Seems to be always empty
    @SerializedName("image")
    private String image;

    @SerializedName("servings")
    private int servings;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private ArrayList<IngredientsItem> ingredients;

    @SerializedName("id")
    private int id;

    @SerializedName("steps")
    private ArrayList<StepsItem> steps;

    protected Recipe(Parcel in) {
        image = in.readString();
        servings = in.readInt();
        name = in.readString();
        id = in.readInt();
        ingredients = in.createTypedArrayList(IngredientsItem.CREATOR);
        steps = in.createTypedArrayList(StepsItem.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getServings() {
        return servings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIngredients(ArrayList<IngredientsItem> ingredients) {
        this.ingredients = ingredients;
    }

    public List<IngredientsItem> getIngredients() {
        return ingredients;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSteps(ArrayList<StepsItem> steps) {
        this.steps = steps;
    }

    public ArrayList<StepsItem> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return
                "Recipe{" +
                        "image = '" + image + '\'' +
                        ",servings = '" + servings + '\'' +
                        ",name = '" + name + '\'' +
                        ",ingredients = '" + ingredients + '\'' +
                        ",id = '" + id + '\'' +
                        ",steps = '" + steps + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(image);
        parcel.writeInt(servings);
        parcel.writeString(name);
        parcel.writeInt(id);

        parcel.writeTypedArray(ingredients.toArray(IngredientsItem.CREATOR.newArray(0)), flags);
        parcel.writeTypedArray(steps.toArray(StepsItem.CREATOR.newArray(0)), flags);
    }
}