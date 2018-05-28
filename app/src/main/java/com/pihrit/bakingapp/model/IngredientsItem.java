package com.pihrit.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class IngredientsItem implements Parcelable {

    @SerializedName("quantity")
    private float quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

    protected IngredientsItem(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<IngredientsItem> CREATOR = new Creator<IngredientsItem>() {
        @Override
        public IngredientsItem createFromParcel(Parcel in) {
            return new IngredientsItem(in);
        }

        @Override
        public IngredientsItem[] newArray(int size) {
            return new IngredientsItem[size];
        }
    };

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getMeasure() {
        return measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public String toString() {
        return
                "IngredientsItem{" +
                        "quantity = '" + quantity + '\'' +
                        ",measure = '" + measure + '\'' +
                        ",ingredient = '" + ingredient + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }
}