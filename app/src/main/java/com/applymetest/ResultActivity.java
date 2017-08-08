package com.applymetest;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;

import Singletones.InstrumentsRepo;

public class ResultActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView bass_percent;
    TextView banjo_percent;
    TextView electric_percent;
    TextView guitar_percent;
    TextView result_title;
    FrameLayout bass_frame;
    FrameLayout banjo_frame;
    FrameLayout electric_frame;
    FrameLayout guitar_frame;
    String userName;
    Integer choosenId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        prepearBars();



        bass_percent = (TextView)findViewById(R.id.bass_percent);
        banjo_percent = (TextView)findViewById(R.id.banjo_percent);
        electric_percent = (TextView)findViewById(R.id.electric_percent);
        guitar_percent = (TextView)findViewById(R.id.guitar_percent);
        result_title = (TextView)findViewById(R.id.result_title);
        bass_frame = (FrameLayout)findViewById(R.id.bass_frame);
        banjo_frame = (FrameLayout)findViewById(R.id.banjo_frame);
        electric_frame = (FrameLayout)findViewById(R.id.electric_frame);
        guitar_frame = (FrameLayout)findViewById(R.id.guitar_frame);

        setupPercents();
        loadPercentsView();
        loadResult();
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
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
            startActivity(goToResult);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setupPercents(){
        int sum = 0;
        for (int i = 0; i < InstrumentsRepo.getInstance().getInstrumentsArray().size(); i++){
            sum = sum + InstrumentsRepo.getInstance().getInstrumentsArray().get(i).getLikeCount();
        }

        for (int i = 0; i < InstrumentsRepo.getInstance().getInstrumentsArray().size(); i++){
            InstrumentsRepo.getInstance().getInstrumentsArray().get(i)
                    .setPercent((float) ((InstrumentsRepo.getInstance().getInstrumentsArray().get(i).getLikeCount() * 100)/ sum));
            Log.d("log", "percent: " + String.valueOf(InstrumentsRepo.getInstance().getInstrumentsArray().get(i).getPercent()));
        }
    }

    public void loadPercentsView(){
        setNewWeight(Math.round(InstrumentsRepo.getInstance().getInstrumentsArray().get(0).getPercent()),bass_frame);
        setNewWeight(Math.round(InstrumentsRepo.getInstance().getInstrumentsArray().get(1).getPercent()),banjo_frame);
        setNewWeight(Math.round(InstrumentsRepo.getInstance().getInstrumentsArray().get(2).getPercent()),guitar_frame);
        setNewWeight(Math.round(InstrumentsRepo.getInstance().getInstrumentsArray().get(3).getPercent()),electric_frame);
        bass_percent.setText(String.valueOf(Math.round(InstrumentsRepo.getInstance().getInstrumentsArray().get(0).getPercent())) + " %");
        banjo_percent.setText(String.valueOf(Math.round(InstrumentsRepo.getInstance().getInstrumentsArray().get(1).getPercent())) + " %");
        guitar_percent.setText(String.valueOf(Math.round(InstrumentsRepo.getInstance().getInstrumentsArray().get(2).getPercent())) + " %");
        electric_percent.setText(String.valueOf(Math.round(InstrumentsRepo.getInstance().getInstrumentsArray().get(3).getPercent())) + " %");

    }

    public void loadResult(){
        Hawk.init(this).build();
        if (Hawk.contains("username") && Hawk.contains("choose")) {
            if (Hawk.contains("username") && !Hawk.get("username").equals("")) {
                userName = Hawk.get("username");
            } else {
                userName = "Guest";
            }
            choosenId = Hawk.get("choose");

            result_title.setText(
                    userName + ", " +
                            String.valueOf(Math.round(InstrumentsRepo.getInstance().getInstrumentsArray().get(choosenId).getPercent())+"% " +
                                    " also like "+
                                    getResources().getString(InstrumentsRepo.getInstance().getInstrumentsArray().get(choosenId).getTitle()) +
                                    "!"
                            ));

        }


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
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    public void setNewWeight(Integer newWeight, FrameLayout v){
        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) v.getLayoutParams();
        param.width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newWeight * 3, getResources().getDisplayMetrics()));
        v.setLayoutParams(param);
    }
}
