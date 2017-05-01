package pkgAsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import pkgModel.Producer;

/**
 * Created by Wucki on 19.01.2017.
 */
public class AsyncProducerDetail  extends AsyncTask<Integer, Void, Producer>{
    @Override
    protected Producer doInBackground(Integer... params) {
        try {
            return getProducerDetail(params[0].intValue());
        } catch (Exception e) {
            e.printStackTrace();//doInBackground kann keine Ausnahmen werfen !
        }
        return null;
    }

    private Producer getProducerDetail(int id) throws IOException {
        //URL serverURL = new URL(Dao.ip + "/WebServerProducts/webresources/ProducerDetail/" + selectedId);

        URL obj = new URL("http://192.168.196.185:8080/WebServerProducts/webresources/ProducerDetail/" + id);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json; charset=UTF-8");
        con.setConnectTimeout(1000);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + obj);
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

        Producer producer = g.fromJson(String.valueOf(response), Producer.class);
        if(producer!=null)
            System.out.println(producer.getName()+"xxx");
        else System.out.println("producerddd");
        return producer;
    }


}
