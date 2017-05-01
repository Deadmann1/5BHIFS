package bsd.holidayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import AsyncTasks.AsyncAddAddon;
import pkgData.Addon;
import pkgData.AddonWrapper;
import pkgDatamanager.Credentials;
import pkgDatamanager.DatamanagerAddons;

public class AddonsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, android.view.View.OnClickListener {

    TextView lblName;
    TextView lblDescription;
    Button btnBook;
    Spinner spAddons;
    ArrayAdapter<Addon> adapterAddons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getAllGuiElements();
        lblName.setText(Credentials.getInstance().getName());
        try {
            fillSpinnerAddons();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        registerEventHandlers();
    }

    private void registerEventHandlers() {
        btnBook.setOnClickListener(this);
        spAddons.setOnItemSelectedListener(this);
    }

    private void fillSpinnerAddons() throws ExecutionException, InterruptedException {
        adapterAddons = new ArrayAdapter<Addon>(this, android.R.layout.simple_spinner_item, DatamanagerAddons.getInstance().getAllAddons());
        spAddons.setAdapter(adapterAddons);
    }

    private void getAllGuiElements() {
        lblName = (TextView) this.findViewById(R.id.lblName);
        spAddons = (Spinner) this.findViewById(R.id.spAddons);
        lblDescription = (TextView) this.findViewById(R.id.lblDescription);
        btnBook = (Button) this.findViewById(R.id.btnBook);
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
        getMenuInflater().inflate(R.menu.addons, menu);
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
            //this activity
        } else if (id == R.id.nav_room_service) {
            Intent roomServiceIntent = new Intent(this, RoomServiceActivity.class);
            //roomServiceIntent.putExtra();
            startActivity(roomServiceIntent);
        } else if (id == R.id.nav_restaurant_order) {
            Intent restaurantOrderIntent = new Intent(this, RestaurantOrderActivity.class);
            //restaurantOrderIntent.putExtra();
            startActivity(restaurantOrderIntent);
        } else if (id == R.id.nav_logout) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            //loginIntent.putExtra();
            startActivity(loginIntent);
        }
      


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        lblDescription.setText(DatamanagerAddons.getInstance().getItemByIndex(spAddons.getSelectedItemPosition()).getDescription());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == btnBook) {
            Addon addon = (Addon) spAddons.getSelectedItem();
            int id_addon = addon.getID_Addon();
            try {
                AddonWrapper a = new AsyncAddAddon(new AddonWrapper(id_addon, Credentials.getInstance().getUsername())).execute().get();
                Toast.makeText(getApplicationContext(), "AddOns erfolgreich bestellt",
                        Toast.LENGTH_LONG).show();
                Intent overview = new Intent(this, OverviewActivity.class);
                startActivity(overview);
            } catch (Exception e) {
                e.printStackTrace();


            }
        }
    }
}
