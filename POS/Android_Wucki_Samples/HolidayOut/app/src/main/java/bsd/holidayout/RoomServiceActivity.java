package bsd.holidayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pkgData.Snack;
import pkgData.SnackCategory;
import pkgDatahelper.SnackHelper;
import pkgDatamanager.Credentials;
import pkgDatamanager.DatamanagerSnacks;

public class RoomServiceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, android.view.View.OnClickListener {

    List<SnackHelper> shoppingCart;

    TextView lblName;
    Button btnAdd;
    Button btnOrder;
    EditText txtAmount;

    Spinner spCategory;
    ArrayAdapter<SnackCategory> adapterCategory;
    Spinner spProduct;
    ArrayAdapter<Snack> adapterProduct;

    ListView listViewShoppingCart;
    ArrayAdapter<SnackHelper> adapterShoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        shoppingCart = new ArrayList<SnackHelper>();
        adapterShoppingCart = new ArrayAdapter<SnackHelper>(this, android.R.layout.simple_list_item_1, shoppingCart);
        getAllGuiElements();
        listViewShoppingCart.setAdapter(adapterShoppingCart);
        lblName.setText(Credentials.getInstance().getName());
        registerEventHandlers();

        fillSpinnerCategory();

    }

    private void fillSpinnerCategory() {
        adapterCategory = new ArrayAdapter<SnackCategory>(this, android.R.layout.simple_spinner_item, DatamanagerSnacks.getInstance().getAllCategories());
        spCategory.setAdapter(adapterCategory);
    }

    private void fillSpinnerProducts(String snackType) {
        if(snackType.compareTo("Drink")==0) {
            adapterProduct = new ArrayAdapter<Snack>(this, android.R.layout.simple_spinner_item, DatamanagerSnacks.getInstance().getAllDrinks());
            spProduct.setAdapter(adapterProduct);
        }
        else if(snackType.compareTo("Sweet")==0) {
            adapterProduct = new ArrayAdapter<Snack>(this, android.R.layout.simple_spinner_item, DatamanagerSnacks.getInstance().getAllSweets());
            spProduct.setAdapter(adapterProduct);
        }
        else if(snackType.compareTo("Snack")==0) {
            adapterProduct = new ArrayAdapter<Snack>(this, android.R.layout.simple_spinner_item, DatamanagerSnacks.getInstance().getAllSnacks());
            spProduct.setAdapter(adapterProduct);
        }


    }

    private void registerEventHandlers() {
        btnOrder.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        spCategory.setOnItemSelectedListener(this);
        spProduct.setOnItemSelectedListener(this);
    }

    private void getAllGuiElements() {
        lblName = (TextView)this.findViewById(R.id.lblName);
        btnAdd = (Button) this.findViewById(R.id.btnAdd);
        btnOrder = (Button) this.findViewById(R.id.btnOrder);
        txtAmount = (EditText) this.findViewById(R.id.txtAmount);

        spCategory = (Spinner) this.findViewById(R.id.spCategory);
        spProduct = (Spinner) this.findViewById(R.id.spProduct);

        listViewShoppingCart = (ListView) this.findViewById(R.id.listViewShoppingCart);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_overview) {
            Intent overviewIntent = new Intent(this, OverviewActivity.class);
            //overviewIntent.putExtra();
            startActivity(overviewIntent);
        } else if (id == R.id.nav_addons) {
            Intent addonsIntent = new Intent(this, AddonsActivity.class);
            //addonsIntent.putExtra();
            startActivity(addonsIntent);
        } else if (id == R.id.nav_room_service) {
            //this Activity
        } else if (id == R.id.nav_restaurant_order) {
            Intent restaurantOrderIntent = new Intent(this, RestaurantOrderActivity.class);
            //restaurantOrderIntent.putExtra();
            startActivity(restaurantOrderIntent);
        }
        else if(id == R.id.nav_logout) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            //loginIntent.putExtra();
            startActivity(loginIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v == btnAdd) {
            Snack s = new Snack();
            if(txtAmount.getText().toString().compareTo("") != 0 && spCategory.getSelectedItem() != null && spProduct.getSelectedItem() != null) {
                if(spCategory.getSelectedItemPosition() == 0) {
                    s = DatamanagerSnacks.getInstance().getAllDrinks().get(spProduct.getSelectedItemPosition());
                }
                if(spCategory.getSelectedItemPosition() == 2) {
                    s = DatamanagerSnacks.getInstance().getAllSweets().get(spProduct.getSelectedItemPosition());
                }
                if(spCategory.getSelectedItemPosition() == 1) {
                    s = DatamanagerSnacks.getInstance().getAllSnacks().get(spProduct.getSelectedItemPosition());
                }
                shoppingCart.add(new SnackHelper(s, Integer.parseInt(txtAmount.getText().toString())));
                adapterShoppingCart.notifyDataSetChanged();
            }
            else {
                Toast.makeText(RoomServiceActivity.this, "Fill in the required information", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v == btnOrder) {
            DatamanagerSnacks.getInstance().orderShoppingCart(shoppingCart);
            Toast.makeText(getApplicationContext(), "Snacks erfolgreich bestellt",
                    Toast.LENGTH_LONG).show();
            Intent overview = new Intent(this, OverviewActivity.class);
            startActivity(overview);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if(parent == spCategory) {
            if(spCategory.getSelectedItemPosition() == 0) {
                fillSpinnerProducts("Drink");
            }
            else if(spCategory.getSelectedItemPosition() == 1) {
                fillSpinnerProducts("Snack");
            }
            else if(spCategory.getSelectedItemPosition() == 2) {
                fillSpinnerProducts("Sweet");
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
