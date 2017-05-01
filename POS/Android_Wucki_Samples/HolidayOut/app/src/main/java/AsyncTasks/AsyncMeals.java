package AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import bsd.holidayout.Meal;
import pkgData.SnackCategory;


public class AsyncMeals  extends AsyncTask<String, Void, List<Meal>> {
    @Override
    protected List<Meal> doInBackground(String... params) {
        try {
            return getAllMeals();
        } catch (Exception e) {
            e.printStackTrace();//doInBackground kann keine Ausnahmen werfen !
        }
        return null;
    }

    private List<Meal> getAllMeals() throws Exception {
        String url = "http://192.168.75.1:18080/HolidayOutServer/webresources/meals";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json; charset=UTF-8");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        Gson g = new GsonBuilder().setDateFormat("dd.MMM.yyyy").create();
        List<Meal> meals = g.fromJson(response.toString(), new TypeToken<List<Meal>>() {
        }.getType());

        return meals;
    }
}