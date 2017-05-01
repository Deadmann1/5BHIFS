package AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pkgData.AddonWrapper;
import pkgData.SnackOrder;


public class AsyncOrderSnackCart  extends AsyncTask<String, Void, SnackOrder> {
    SnackOrder hilf;

    public AsyncOrderSnackCart(SnackOrder a) {
        super();
        hilf = a;
    }

    @Override
    protected SnackOrder doInBackground(String... params) {
        return orderCart(hilf);
    }

    private SnackOrder orderCart(SnackOrder hilf) {
        URL url;
        try {
            url = new URL("http://192.168.75.1:18080/HolidayOutServer/webresources/snackorder");
            System.out.println("***post " + new Gson().toJson(hilf));
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlCon.setRequestProperty("Accept", "application/json; charset=UTF-8");

            urlCon.setDoOutput(true); // to be able to write.
            urlCon.setDoInput(true); // to be able to read.

            try (DataOutputStream wr = new DataOutputStream(urlCon.getOutputStream())) {
                wr.write(new Gson().toJson(hilf).getBytes());
            }
            System.out.println(urlCon.getResponseCode() + "code");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hilf;
    }
}
