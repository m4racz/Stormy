package com.android.m4racz.stormy.Settings;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedLocations {

    @SerializedName("Locations")
    @Expose
    private List<Location> locations = new ArrayList<Location>();

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

}
