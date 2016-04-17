package com.example.sebastian.appclima;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class Main {
    private String temperature, pressure, humidity, minTemp, maxTemp;

    public Main(JSONObject jsonObject)
    {
        try {
            setTemperature(jsonObject.getString("temp"));
            setPressure(jsonObject.getString("pressure"));
            setHumidity(jsonObject.getString("humidity"));
            setMinTemp(jsonObject.getString("temp_min"));
            setMaxTemp(jsonObject.getString("temp_max"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }
}
