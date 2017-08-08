package com.applymetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.applymetest.Adapters.InsrumentsAdapter;
import com.applymetest.Models.Instrument;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.ArrayList;

import Singletones.InstrumentsRepo;

public class ChooseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rv;
    InsrumentsAdapter insrumentsAdapter;
    EditText user_name_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        prepearBars();
        Hawk.init(this).build();

        rv = (RecyclerView)findViewById(R.id.instrument_rv);
        user_name_edit = (EditText)findViewById(R.id.user_name_edit);

        if(InstrumentsRepo.getInstance().getInstrumentsArray().size() == 0) {
            try {
                InstrumentsRepo.getInstance().createInstruments();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        initializeActionsAdapter();
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
        getMenuInflater().inflate(R.menu.choose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_local) {
            InstrumentsRepo.getInstance().setCreateType( new InstrumentsLocalCreator() );
            try {
                InstrumentsRepo.getInstance().createInstruments();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            InstrumentsRepo.getInstance().setCreateType( new InstrumentsResourse());
            try {
                InstrumentsRepo.getInstance().createInstruments();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_poll) {
            Intent goToPoll = new Intent(getApplicationContext(), ChooseActivity.class);
            startActivity(goToPoll);
        } else if (id == R.id.nav_results) {
            Intent goToResult = new Intent(getApplicationContext(), ResultActivity.class);
            Hawk.put("username", user_name_edit.getText());
            startActivity(goToResult);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void  prepearBars(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    public  void initializeActionsAdapter(){
        insrumentsAdapter = new InsrumentsAdapter(InstrumentsRepo.getInstance().getInstrumentsArray()){
            @Override
            public void onBindViewHolder(InsrumentsHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InstrumentsRepo.getInstance().getInstrumentsArray().get(position).addLikeCount();
                        String userName = String.valueOf(user_name_edit.getText());
                        Hawk.put("username", userName);
                        Hawk.put("choose", position);
                        Intent goToResults = new Intent(getApplicationContext(), ResultActivity.class);
                        getApplication().startActivity(goToResults);
                    }
                });
            }
        };
        GridLayoutManager glm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(glm);
        rv.setAdapter(insrumentsAdapter);

    }
}
