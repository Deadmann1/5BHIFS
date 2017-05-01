package bsd.holidayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import pkgDatamanager.DatamanagerMeals;

public class DessertActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnShowMenu;
    Button btnSkip;
    Button btnCancel;
    Spinner spDe;
    ArrayAdapter<Meal> adDe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert);

        getAllGuiElements();
        registerEventHandlers();
        fillSpinner();

    }

    private void fillSpinner() {
        adDe = new ArrayAdapter<Meal>(this, android.R.layout.simple_spinner_item, DatamanagerMeals.getInstance().getNeededMeals(3));
        spDe.setAdapter(adDe);
    }

    private void registerEventHandlers() {
        btnShowMenu.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void getAllGuiElements() {
        btnShowMenu = (Button) this.findViewById(R.id.btnShowMenu);
        btnSkip = (Button) this.findViewById(R.id.btnSkip);
        btnCancel = (Button) this.findViewById(R.id.btnCancel);
        spDe = (Spinner)this.findViewById(R.id.spinner7);
    }

    @Override
    public void onClick(View v) {
        if(v == btnShowMenu) {
            DatamanagerMeals.getInstance().addOrderToMeal((Meal)spDe.getSelectedItem());
            Intent menuIntent = new Intent(this, MenuActivity.class);
            //menuIntent.putExtra();
            startActivity(menuIntent);
        }
        else if(v == btnSkip) {
            DatamanagerMeals.getInstance().skipMeal(3);
            Intent menuIntent = new Intent(this, MenuActivity.class);
            //menuIntent.putExtra();
            startActivity(menuIntent);
        }
        else if(v == btnCancel) {
            Intent restaurantOrderIntent = new Intent(this, RestaurantOrderActivity.class);
            startActivity(restaurantOrderIntent);
        }
    }
}
