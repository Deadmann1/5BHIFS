package bsd.holidayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import pkgDatamanager.DatamanagerMeals;

public class MainCourseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button btnNext;
    Button btnSkip;
    Button btnCancel;
    Spinner spMC;
    ArrayAdapter<Meal> adMC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_course);

        getAllGuiElements();
        registerEventHandlers();
        fillSpinner();

    }

    private void fillSpinner() {
        adMC = new ArrayAdapter<Meal>(this, android.R.layout.simple_spinner_item, DatamanagerMeals.getInstance().getNeededMeals(2));
        spMC.setAdapter(adMC);
    }

    private void registerEventHandlers() {
        btnNext.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        spMC.setOnItemSelectedListener(this);
    }

    private void getAllGuiElements() {
        btnNext = (Button) this.findViewById(R.id.btnNext);
        btnSkip = (Button) this.findViewById(R.id.btnSkip);
        btnCancel = (Button) this.findViewById(R.id.btnCancel);
        spMC = (Spinner) this.findViewById(R.id.spinner6);
    }

    @Override
    public void onClick(View v) {
        if(v == btnNext) {
            DatamanagerMeals.getInstance().addOrderToMeal((Meal)spMC.getSelectedItem());
            Intent dessertIntent = new Intent(this, DessertActivity.class);
            //dessertIntent.putExtra();
            startActivity(dessertIntent);
        }
        else if(v == btnSkip) {
            DatamanagerMeals.getInstance().skipMeal(2);
            Intent dessertIntent = new Intent(this, DessertActivity.class);
            startActivity(dessertIntent);
        }
        else if(v == btnCancel) {
            Intent restaurantOrderIntent = new Intent(this, RestaurantOrderActivity.class);
            startActivity(restaurantOrderIntent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
