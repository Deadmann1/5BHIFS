package pkgAsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import pkgModel.Product;

/**
 * Created by Wucki on 20.01.2017.
 */
public class AsyncUpdateProduct  extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        try {
            return updateProduct(params[0]);
        } catch (Exception e) {
            e.printStackTrace();//doInBackground kann keine Ausnahmen werfen !
        }
        return null;
    }

    private String updateProduct(String param) throws IOException {
        String url = "http://192.168.196.185:8080/WebServerProducts/webresources/ProductDetail";

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("PUT");

        //add request header
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        con.setConnectTimeout(1000);

        BufferedWriter b = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));

        b.write(param);
        b.flush();
        b.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String s = response.toString();
        return s;
    }

}
