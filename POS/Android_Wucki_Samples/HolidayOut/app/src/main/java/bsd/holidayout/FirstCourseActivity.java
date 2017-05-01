package bsd.holidayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import pkgData.Addon;
import pkgDatamanager.DatamanagerAddons;
import pkgDatamanager.DatamanagerMeals;

public class FirstCourseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button btnNext;
    Button btnSkip;
    Button btnCancel;
    Spinner spFC;
    ArrayAdapter<Meal> adapterMeal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_course);

        getAllGuiElements();
        registerEventHandlers();
        fillSpinner();

    }

    private void fillSpinner() {
        adapterMeal = new ArrayAdapter<Meal>(this, android.R.layout.simple_spinner_item, DatamanagerMeals.getInstance().getNeededMeals(1));
        spFC.setAdapter(adapterMeal);
    }

    private void registerEventHandlers() {
        btnNext.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        spFC.setOnItemSelectedListener(this);
    }

    private void getAllGuiElements() {
        btnNext = (Button) this.findViewById(R.id.btnNext);
        btnSkip = (Button) this.findViewById(R.id.btnSkip);
        btnCancel = (Button) this.findViewById(R.id.btnCancel);
        spFC = (Spinner) this.findViewById(R.id.spinner5);
    }

    @Override
    public void onClick(View v) {
        if(v == btnNext) {
            DatamanagerMeals.getInstance().addOrderToMeal((Meal)spFC.getSelectedItem());
            Intent mainCourseIntent = new Intent(this, MainCourseActivity.class);
            //mainCourseIntent.putExtra();
            startActivity(mainCourseIntent);
        }
        else if(v == btnSkip) {
            DatamanagerMeals.getInstance().skipMeal(1);
            Intent mainCourseIntent = new Intent(this, MainCourseActivity.class);
            startActivity(mainCourseIntent);
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
