package bsd.holidayout;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import AsyncTasks.AsyncValidation;
import pkgData.Account;
import pkgDatamanager.Credentials;


public class LoginActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnLogin;
    EditText txtUsername;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getAllGuiComponents();
        registerEventhandlers();
    }

    private void registerEventhandlers() {
        btnLogin.setOnClickListener(this);
    }

    private void getAllGuiComponents() {
        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        txtUsername = (EditText) this.findViewById(R.id.txtUsername);
        txtPassword = (EditText) this.findViewById(R.id.txtPassword);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            Account acc = new Account();
            acc.setUsername(txtUsername.getText().toString());
            acc.setPassword(txtPassword.getText().toString());
            try {

                AsyncTask<String, Void, Account> asyncTask = new AsyncValidation(acc).execute();
                Account ret = asyncTask.get();
                if(ret.getRole_id() != 2) //Nur Nutzer mit der RoleID 2  (=Gast) dürfen rein---
                {
                    Toast.makeText(getApplicationContext(), "Falsches Login",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Credentials.getInstance().setUsername(acc.getUsername());
                    Intent overview = new Intent(this, OverviewActivity.class);
                    startActivity(overview);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }


        }
    }
    /*

    public Account validateAccount(Account toSend)
    {

        URL url;
        Account get = new Account();
        String response = "lol";
        try {
            url = new URL("http://192.168.148.1:18080/HolidayOutServer/webresources/validateacc");

            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlCon.setRequestProperty("Accept", "application/json; charset=UTF-8");

            urlCon.setDoOutput(true); // to be able to write.
            urlCon.setDoInput(true); // to be able to read.

            try( DataOutputStream wr = new DataOutputStream( urlCon.getOutputStream())) {
                wr.write(new Gson().toJson(toSend).getBytes());
            }
            Reader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            response = sb.toString();
           get =  new Gson().fromJson(response, Account.class);
        } catch (IOException e) {
            e.printStackTrace();
        }



        return get;
    }
class AsyncValidation extends AsyncTask<String, Void, Account>
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
}
*/


}

