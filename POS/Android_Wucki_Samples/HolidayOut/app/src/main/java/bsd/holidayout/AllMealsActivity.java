package bsd.holidayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AllMealsActivity extends AppCompatActivity implements android.view.View.OnClickListener{

    Button btnOrder;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_meals);

        getAllGuiElements();
        registerEventHandlers();
    }

    private void registerEventHandlers() {
        btnOrder.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    private void getAllGuiElements() {
        btnOrder = (Button) this.findViewById(R.id.btnOrder);
        btnAdd = (Button) this.findViewById(R.id.btnAdd);
    }

    @Override
    public void onClick(View v) {

    }
}
