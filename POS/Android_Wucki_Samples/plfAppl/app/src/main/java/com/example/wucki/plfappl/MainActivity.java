package com.example.wucki.plfappl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import pkgAsyncTasks.AsyncProducer;
import pkgAsyncTasks.AsyncProducerDetail;
import pkgAsyncTasks.AsyncProduct;
import pkgAsyncTasks.AsyncProductDetail;
import pkgModel.Producer;
import pkgModel.Product;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    Spinner spProduct;
    Spinner spProducer;

    EditText txtmsg;

    int selectedId = -23;

    Product selectedProd;
    View selectedV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAllGuiElements();
        registerEventHandler();
        fillProducers();
        fillProducts();
    }

    private void fillProducts() {
        try {
            ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(
                    this, android.R.layout.simple_spinner_item, new AsyncProduct().execute().get());
            spProduct.setAdapter(adapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
        spProduct.setOnLongClickListener(this);
        spProducer.setOnLongClickListener(this);
    }


    private void getAllGuiElements() {
        spProduct = (Spinner) findViewById(R.id.spProduct);
        spProducer = (Spinner) findViewById(R.id.spProd);
        txtmsg = (EditText) findViewById(R.id.editText);
        txtmsg.setEnabled(false);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        selectedV = v;
        if(v.getId() == R.id.spProd){
            Spinner s = (Spinner)v;
            Producer p = (Producer) s.getSelectedItem();
            if(p != null) {
                selectedId = p.getId();
            }

            ActionMode actionMode;
            actionMode = this.startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.contextmenu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    boolean ret = false;
                    try {
                        switch (item.getItemId()) {
                            case R.id.action_detail:
                                System.out.println(new AsyncProducerDetail().execute(selectedId).get().toLongString());
                                txtmsg.setText(new AsyncProducerDetail().execute(selectedId).get().toLongString());
                                ret = true;
                                break;
                            case R.id.action_update:
                                System.out.println("FUTUR");
                                break;
                        }
                        if (ret) {
                            mode.finish(); // Action picked, so close the CAB
                        }
                    }
                    catch(Exception ex){
                        mode.finish();
                        Toast.makeText(getApplicationContext(),"Producer not found", Toast.LENGTH_SHORT);
                    }
                    return ret;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }
        else if (v.getId() == R.id.spProduct) {
            ActionMode actionMode;

            Spinner s = (Spinner) v;
            selectedProd = (Product) s.getSelectedItem();
            if (selectedProd != null) {
                selectedId = selectedProd.getId();
            }

            actionMode = this.startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.contextmenu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                    boolean ret = false;
                    try {
                        switch (item.getItemId()) {
                            case R.id.action_detail:
                                System.out.println(new AsyncProductDetail().execute(selectedId).get().toLongString());
                                txtmsg.setText(new AsyncProductDetail().execute(selectedId).get().toLongString());
                                ret = true;
                                break;
                            case R.id.action_update:
                                Intent intent = new Intent(selectedV.getContext(), ProductActivity.class);

                                intent.putExtra("prod", selectedProd);

                                startActivity(intent);

                                break;
                        }
                        if (ret) {
                            mode.finish(); // Action picked, so close the CAB
                        }
                    } catch (Exception ex) {
                        mode.finish();
                        Toast.makeText(getApplicationContext(), "Product not found", Toast.LENGTH_SHORT);
                    }
                    return ret;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }
        return false;
    }
}
