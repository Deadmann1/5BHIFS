package pkgAsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import pkgModel.Producer;

/**
 * Created by John_13 on 13.01.2017.
 */

public class AsyncProducer extends AsyncTask<String, Void, List<Producer>> {
    @Override
    protected List<Producer> doInBackground(String... params) {
        try {
            return getAllProducers();
        } catch (Exception e) {
            e.printStackTrace();//doInBackground kann keine Ausnahmen werfen !
        }
        return null;
    }

    private List<Producer> getAllProducers() throws Exception {

        String url = "http://192.168.196.185:8080/WebServerProducts/webresources/ProducerList";
        String p = "[{\"id\":22,\"name\":\"Scheiben AG\"},{\"id\":33,\"name\":\"CeDe\n" +
                "AG\",\"sales\":10000.11},{\"id\":44,\"name\":\"ÖFBB\",\"sales\":5000.55},{\"i\n" +
                "d\":55,\"name\":\"DFBB\",\"sales\":1000.1},{\"id\":66,\"name\":\"Haitek\",\"sale\n" +
                "s\":909.9},{\"id\":77,\"name\":\"Kornblum\"}]";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        con.setConnectTimeout(1000);
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
       // System.out.println(response.toString());
        Gson g = new GsonBuilder().create();

        List<Producer> producer = g.fromJson(String.valueOf(response), new TypeToken<List<Producer>>() {
        }.getType());
        if(producer!=null)
            System.out.println(producer.size()+"xxx");
        else System.out.println("producerddd");
        return producer;
    }


}