package pkgAsyncTasks;

import android.os.AsyncTask;
import android.os.SystemClock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.List;

import pkgModel.Producer;
import pkgModel.Product;

/**
 * Created by John_13 on 13.01.2017.
 */

public class AsyncProduct extends AsyncTask<String, Void, List<Product>> {
    @Override
    protected List<Product> doInBackground(String... params) {
        try {
            return getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();//doInBackground kann keine Ausnahmen werfen !
        }
        return null;
    }

    private List<Product> getAllProducts() throws Exception {
        String url = "http://192.168.196.185:8080/WebServerProducts/webresources/ProductList";

        String prod = "[{\"id\":110,\"name\":\"Frisby\",\"onMarket\":\"2002-04-" +
                "11\",\"onStock\":200},{\"id\":120,\"name\":\"Frisby\",\"onMarket\":\"2012-11-" +
                "08\",\"onStock\":200},{\"id\":130,\"name\":\"Ball\",\"onMarket\":\"2009-02-" +
                "15\",\"onStock\":190},{\"id\":140,\"name\":\"Ball\",\"onMarket\":\"1992-01-" +
                "11\",\"onStock\":2},{\"id\":150,\"name\":\"Fidschi Gogerl\",\"onMarket\":\"2013-07-" +
                "01\",\"onStock\":1000},{\"id\":160,\"name\":\"Fidschi Gogerl\",\"onMarket\":\"2013-08-" +
                "11\",\"onStock\":10},{\"id\":170,\"name\":\"Fidschi Gogerl\",\"onMarket\":\"2002-04-" +
                "05\",\"onStock\":22},{\"id\":180,\"name\":\"Murmel\",\"onMarket\":\"2010-11-" +
                "13\",\"onStock\":802},{\"id\":190,\"name\":\"Waveboard\",\"onMarket\":\"2011-11-" +
                "13\",\"onStock\":402}]";
        System.out.println(prod);
       URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json; charset=UTF-8");
        con.setConnectTimeout(1000);

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
        Gson g = new GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        List<Product> products = g.fromJson(String.valueOf(response), new TypeToken<List<Product>>() {
        }.getType());
        if(products!=null)
        System.out.println(products.size()+"xxx");
        else System.out.println("llololoProducts");
        return products;
    }
}