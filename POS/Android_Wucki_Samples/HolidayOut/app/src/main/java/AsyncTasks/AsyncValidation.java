package AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import pkgData.Account;


public class AsyncValidation extends AsyncTask<String, Void, Account>
{

    Account aa;
    public AsyncValidation(Account a)
    {
        super();
        aa = a;
    }
    @Override
    protected Account doInBackground(String... params) {
        return validateAccount(aa);
    }
    public Account validateAccount(Account toSend)
    {

        URL url;
        Account get = new Account();
        String response = "lol";
        try {
            url = new URL("http://192.168.75.1:18080/HolidayOutServer/webresources/validateacc");

            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlCon.setRequestProperty("Accept", "application/json; charset=UTF-8");

            urlCon.setDoOutput(true); // to be able to write.
            urlCon.setDoInput(true); // to be able to read.

            try( DataOutputStream wr = new DataOutputStream( urlCon.getOutputStream())) {
                wr.write(new Gson().toJson(toSend).getBytes());
                wr.close();
            }
            Reader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            response = sb.toString();
            get =  new Gson().fromJson(response, Account.class);
            urlCon.disconnect();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return get;
    }
}
