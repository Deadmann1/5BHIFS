package com.example.wucki.plfappl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

import pkgAsyncTasks.AsyncProducer;
import pkgAsyncTasks.AsyncProducerDetail;
import pkgAsyncTasks.AsyncUpdateProduct;
import pkgModel.Producer;
import pkgModel.Product;

/**
 * Created by Wucki on 20.01.2017.
 */
public class ProductActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {


    Spinner spProducer;
    EditText name;
    EditText stock;
    EditText marketDate;
    EditText msg;

    Product selectedProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
        System.out.println("opened VIEw");
        Intent i = getIntent();

        Product p = (Product) i.getSerializableExtra("prod");

        selectedProd = p;

        getAllGuiElements();
        registerEventHandler();
        fillProducers();

        name.setText(selectedProd.getName());
         stock.setText("");
        marketDate.setText(selectedProd.getOnMarket().toString());


    }

    private void fillProducers() {
        try {
            ArrayAdapter<Producer> adapter = new ArrayAdapter<Producer>(
                    this, android.R.layout.simple_spinner_item, new AsyncProducer().execute().get());
            spProducer.setAdapter(adapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void registerEventHandler() {
        spProducer.setOnLongClickListener(this);
    }

    private void getAllGuiElements() {
        spProducer = (Spinner) findViewById(R.id.spProducer);
    name = (EditText) findViewById(R.id.txtName);
    stock = (EditText) findViewById(R.id.txtOnStock);
    marketDate = (EditText) findViewById(R.id.txtOnMarket);
        msg = (EditText) findViewById(R.id.txtmsg);
        marketDate.setEnabled(false);
        msg.setEnabled(false);
}

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.product_menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mitemSave: System.out.println("save");
                try {
                    selectedProd.setName(name.getText().toString());
                    selectedProd.setOnStock(Integer.parseInt(stock.getText().toString()));
                    selectedProd.setProducer((Producer) spProducer.getSelectedItem());
                } catch(Exception ex){
                    msg.setText(ex.getMessage());
                }
                try {
                    msg.setText(new AsyncUpdateProduct().execute(new Gson().toJson(selectedProd)).get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.mitemCancel:
               this.finish();
                break;
            default: break;
        }
        return true;
    }
}
