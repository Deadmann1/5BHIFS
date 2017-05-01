package com.example.wucki.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.concurrent.ExecutionException;

import pkgAsyncTasks.AsyncPlayer;
import pkgData.Player;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {

    Spinner spPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllGuiElements();
        registerEventHandler();
        try {
            fillPlayer();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fillPlayer() throws ExecutionException, InterruptedException {
        ArrayAdapter<Player> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, new AsyncPlayer().execute().get());
        spPlayer.setAdapter(adapter);
    }

    private void registerEventHandler() {
        spPlayer.setOnLongClickListener(this);
    }

    private void getAllGuiElements() {
        spPlayer = (Spinner) findViewById(R.id.sp_Player);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
