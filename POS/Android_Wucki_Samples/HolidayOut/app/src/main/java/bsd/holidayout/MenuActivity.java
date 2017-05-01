package bsd.holidayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import AsyncTasks.AsyncOrderMealList;
import pkgData.MealWrapper;
import pkgDatamanager.Credentials;
import pkgDatamanager.DatamanagerMeals;

public class MenuActivity extends AppCompatActivity implements android.view.View.OnClickListener{

    Button btnOrder;
    Button btnCancel;
    TextView fc;
    TextView mc;
    TextView de;
    TextView sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getAllGuiElements();
        registerEventHandlers();
        fillTextViews();
    }

    private void fillTextViews() {
        for (Integer name: DatamanagerMeals.getInstance().getMealsOrdered().keySet()){

            String key = name.toString();
            String value = DatamanagerMeals.getInstance().getMealsOrdered().get(name).toString();
            switch(name)
            {
                case 1:
                    fc.setText(DatamanagerMeals.getInstance().getMealsOrdered().get(name).toString());
                    break;
                case 2:
                    mc.setText(DatamanagerMeals.getInstance().getMealsOrdered().get(name).toString());
                    break;
                case 3:
                    de.setText(DatamanagerMeals.getInstance().getMealsOrdered().get(name).toString());
                    break;
            }
        }
        sum.setText(DatamanagerMeals.getInstance().getSumFromOrderAsString());
    }

    private void registerEventHandlers() {
        btnOrder.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    private void getAllGuiElements() {
        btnOrder = (Button) this.findViewById(R.id.btnOrder);
        btnCancel = (Button) this.findViewById(R.id.btnCancel);
        fc = (TextView) this.findViewById(R.id.lblFirstCourse);
        mc = (TextView) this.findViewById(R.id.lblMainCourse);
        de = (TextView) this.findViewById(R.id.lblDessert);
        sum = (TextView) this.findViewById(R.id.textView13);
    }

    @Override
    public void onClick(View v) {
        if(v == btnCancel) {
            Intent restaurantOrderIntent = new Intent(this, RestaurantOrderActivity.class);
            startActivity(restaurantOrderIntent);
        }
        if(v==btnOrder)
        {
            MealWrapper w = new MealWrapper();
            w.setUsername(Credentials.getInstance().getUsername());
            w.setListOfMeals(DatamanagerMeals.getInstance().getIDsFromOrders());
            new AsyncOrderMealList(w).execute();
            Toast.makeText(getApplicationContext(), "Bestellung erfolgreich aufgegeben",
                    Toast.LENGTH_LONG).show();
            Intent overview = new Intent(this, OverviewActivity.class);
            startActivity(overview);
        }
    }
}
