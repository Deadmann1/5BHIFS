package AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pkgData.Account;
import pkgData.Addon;

/**
 * Created by John_13 on 25.12.2016.
 */
public class AsyncAddon extends AsyncTask<String, Void, List<Addon>> {
    @Override
    protected List<Addon> doInBackground(String... params) {
        try {
            return getAllAddons();
        } catch (Exception e) {
            e.printStackTrace();//doInBackground kann keine Ausnahmen werfen !
        }
        return null;
    }

    private List<Addon> getAllAddons() throws Exception {
        String url = "http://192.168.75.1:18080/HolidayOutServer/webresources/addons";

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
        List<Addon> addons = new Gson().fromJson(response.toString(), new TypeToken<List<Addon>>(){}.getType());
        for(int i = 0;i<addons.size();i++)
        {
            System.out.println("**listelol "+addons.get(i));
        }
        return addons;
    }
}