package com.example.sebastian.appclima;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sebastian on 4/14/2016.
 */
public class Weather
{
    private String description;

    public Weather(JSONObject object)
    {
        try {
            setDescription(object.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
