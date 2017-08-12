package com.applymetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.applymetest.Adapters.InstrumentsAdapter;
import com.orhanobut.hawk.Hawk;

import Singletons.InstrumentsRepo;

public class ChooseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rv;
    InstrumentsAdapter instrumentsAdapter;
    EditText user_name_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        prepareBars();
        Hawk.init(this).build();

        rv = (RecyclerView) findViewById(R.id.instrument_rv);
        user_name_edit = (EditText) findViewById(R.id.user_name_edit);

        InstrumentsResource instrumentsResource = new InstrumentsResource();
        InstrumentsRepo.getInstance().setStrategy(instrumentsResource);
        InstrumentsRepo.getInstance().createInstruments(this).then((res) -> {
                    InstrumentsRepo.getInstance().setInstrumentsArray(res);
                    initializeActionsAdapter();
                }
        );


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
            InstrumentsRepo.getInstance().setStrategy(new InstrumentsLocalCreator());
            InstrumentsRepo.getInstance().createInstruments(this).done((res) -> {
                        InstrumentsRepo.getInstance().setInstrumentsArray(res);
                    }
            );
            return true;
        } else {
            InstrumentsResource instrumentsResource = new InstrumentsResource();
            InstrumentsRepo.getInstance().setStrategy(instrumentsResource);
            InstrumentsRepo.getInstance().createInstruments(this).done((res) -> {
                        InstrumentsRepo.getInstance().setInstrumentsArray(res);
                    }
            );

            return true;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_poll) {
            Intent goToPoll = new Intent(getApplicationContext(), ChooseActivity.class);
            startActivity(goToPoll);
        } else if (id == R.id.nav_results) {

            Intent goToResult = new Intent(getApplicationContext(), ResultActivity.class);
            String userName = String.valueOf(user_name_edit.getText());
            Hawk.put("username", userName);
            startActivity(goToResult);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void prepareBars() {
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

    public void initializeActionsAdapter() {
        instrumentsAdapter = new InstrumentsAdapter(InstrumentsRepo.getInstance().getInstrumentsArray()) {
            @Override
            public void onBindViewHolder(InstrumentsHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InstrumentsRepo.getInstance().addChoose(getApplicationContext(), position).then((res) -> {
                            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                            String userName = String.valueOf(user_name_edit.getText());
                            Hawk.put("username", userName);
                            Hawk.put("choose", position);
                            Intent goToResults = new Intent(getApplicationContext(), ResultActivity.class);
                            getApplication().startActivity(goToResults);
                        });
                    }
                });
            }
        };
        GridLayoutManager glm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(glm);
        rv.setAdapter(instrumentsAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }
}
