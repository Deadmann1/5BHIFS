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
import android.widget.Button;
import android.widget.TextView;

import pkgDatamanager.Credentials;
import pkgDatamanager.DatamanagerMeals;

public class RestaurantOrderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, android.view.View.OnClickListener {

    TextView lblName;
    Button btnMenu;
    Button btnAllMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_order);
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
        registerEventHandlers();
        lblName.setText(Credentials.getInstance().getName());
        DatamanagerMeals.getInstance().loadMeals();

    }

    private void registerEventHandlers() {
        btnMenu.setOnClickListener(this);
        btnAllMeals.setOnClickListener(this);
    }

    private void getAllGuiElements() {
        btnMenu = (Button) this.findViewById(R.id.btnMenu);
        btnAllMeals = (Button) this.findViewById(R.id.btnAllMeals);
        lblName = (TextView)this.findViewById(R.id.lblName);
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
        getMenuInflater().inflate(R.menu.restaurant_order, menu);
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
            Intent roomServiceIntent = new Intent(this, RoomServiceActivity.class);
            //roomServiceIntent.putExtra();
            startActivity(roomServiceIntent);
        } else if (id == R.id.nav_restaurant_order) {
            //this activity
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
        if(v == btnMenu) {
            Intent firstCourseIntent = new Intent(this, FirstCourseActivity.class);
            //roomServiceIntent.putExtra();
            startActivity(firstCourseIntent);
        }
        else if(v == btnAllMeals) {
            Intent allMealsIntent = new Intent(this, AllMealsActivity.class);
            //allMealsIntent.putExtra();
            startActivity(allMealsIntent);
        }
    }
}
