package pkgAsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import pkgData.Player;
import pkgData.PlayerResult;

/**
 * Created by Wucki on 01.05.2017.
 */
public class AsyncPlayer extends AsyncTask<String, Void, List<Player>> {
    @Override
    protected List<Player> doInBackground(String... params) {
        try {
            return getAllPlayer();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Player> l = new ArrayList<Player>();

        return l;
    }

    private List<Player> getAllPlayer() throws IOException {
        String url = "http://172.16.230.1:8080/Soccerapp/services/player";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json; charset=UTF-8");
        con.setConnectTimeout(30000);

        System.out.println("\nSending 'GET' request to URL : " + url);

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
        Gson g = new GsonBuilder().create();
        PlayerResult player = g.fromJson(response.toString(), new TypeToken<PlayerResult>() {
        }.getType());
        if(player!=null)
            System.out.println(player.getContent().size()+"xxx");
        else System.out.println("llololoProducts");
        return player.getContent();
    }
}
