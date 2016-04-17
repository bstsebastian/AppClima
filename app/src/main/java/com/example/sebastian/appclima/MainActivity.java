package com.example.sebastian.appclima;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastian on 4/14/2016.
 */
public class MainActivity extends Activity {
    private TextView description, temperature, pressure, humidity, mintemp, maxtemp;
    Spinner ciudades;
    String linkCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ciudades = (Spinner) findViewById(R.id.ciudades);

        //Se declaran los valores del spinner
        List list = new ArrayList();
        list.add("Cancún");
        list.add("Guadalaja");
        list.add("Nayarit");
        list.add("Chihuahua");
        list.add("Colima");
        list.add("Cuernavaca");
        list.add("Merida");
        list.add("Morelia");
        list.add("Hidalgo");
        list.add("Sinaloa");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ciudades.setAdapter(arrayAdapter);


        //Se mandan llamar los elementos de la vista
        description = (TextView) findViewById(R.id.description);
        temperature = (TextView) findViewById(R.id.temperature);
        pressure = (TextView) findViewById(R.id.pressure);
        humidity = (TextView) findViewById(R.id.humidity);
        mintemp = (TextView) findViewById(R.id.mintemp);
        maxtemp = (TextView) findViewById(R.id.maxtemp);



    }

    public void updateWeather(View view)
    {

        //Mensaje Al dar clic al botón
        Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
        //Se manda llamar la varible que contiene el url del clima completo
        linkCurrent = chooseCity(ciudades.getSelectedItemPosition());
        WeatherWS con = new WeatherWS();
        con.execute(linkCurrent);
    }

    private String chooseCity(int spn)
    {
        //Se obtiene el url del sitio y se especifica el código de las ciudades en el spinner
        linkCurrent = "http://api.openweathermap.org/data/2.5/weather?appid=f983c7c6d014cdd6a6485a8d3f6477e5&units=metric&lang=es";
        String city = "3531673";
        switch (spn)
        {
            case 0:
                //Cancún
                city = "3531673";
                break;
            case 1:
                //Guadalajara
                city = "4005539";
                break;
            case 2:
                //Nayarit
                city = "3997773";
                break;
            case 3:
                //chihuahua
                city = "4014338";
                break;
            case 4:
                //Colima
                city = "4013516";
                break;
            case 5:
                //Cuernavaca
                city = "3529947";
                break;
            case 6:
                //Merida
                city = "3523349";
                break;
            case 7:
                //Morelia
                city ="3995402";
                break;
            case 8:
                //Hidalgo
                city = "3792044";
                break;
            case 9:
                //Sinaloa
                city = "3983032";
                break;
            default:
                //Cancún predeterminada
                city = "3531673";
                break;
        }

        //Se regresa una variable con los valores concatenados para obtener los datos
        linkCurrent = linkCurrent + "&id=" + city;
        return linkCurrent;
    }

    //JSON para la conexión
    public class WeatherWS extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jsonObject = null;

            URL link = null;
            String line;

            try
            {
                link = new URL(params[0]);

                HttpURLConnection connection = (HttpURLConnection) link.openConnection();

                connection.setRequestProperty("User-Agent", "Mozilla/5.0"+"(Linux: Android 6.0; es-ES Servicio Web)");
                StringBuilder result = new StringBuilder();
                int response = connection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK)
                {
                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    while((line = bufferedReader.readLine()) != null)
                    {
                        result.append(line);
                    }

                    jsonObject = new JSONObject(result.toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override

        //Se obtienen los valores del documento del sitio
        protected void onPostExecute(JSONObject jsonObject)
        {
            if (jsonObject != null) {
                JSONArray weatherArray = jsonObject.optJSONArray("weather");
                JSONObject mainObject = jsonObject.optJSONObject("main");
                Weather weatherData = null;
                Main mainData = null;
                try {
                    weatherData = new Weather(weatherArray.getJSONObject(0));
                    mainData = new Main(mainObject);
                    description.setText(weatherData.getDescription());
                    temperature.setText(mainData.getTemperature() + " °C");
                    pressure.setText(mainData.getPressure() + " hpa");
                    humidity.setText(mainData.getHumidity() + "%");
                    mintemp.setText(mainData.getMinTemp() + " °C");
                    maxtemp.setText(mainData.getMaxTemp() + " °C");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }
    }
}
