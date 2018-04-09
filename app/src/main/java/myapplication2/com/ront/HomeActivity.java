package myapplication2.com.ront;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 15/3/18.
 */

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout Drawer;
    private ActionBarDrawerToggle Toggle;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        TextView bannerDay =(TextView)findViewById(R.id.bannerDay);
        TextView bannerDate =(TextView)findViewById(R.id.bannerDate);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        bannerDay.setText(dayOfTheWeek);

        long date =System.currentTimeMillis();
        SimpleDateFormat sdff = new SimpleDateFormat("dd MMMM yyyy");
        String dateString = sdff.format(date);
        bannerDate.setText(dateString);




        Drawer=(DrawerLayout) findViewById(R.id.drawerlayout);
        NavigationView navigationView=findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.routine:
                                Intent a = new Intent(HomeActivity.this, RoutineActivity.class);
                                startActivity(a);
                                break;
                            case R.id.assign:
                                Intent b = new Intent(HomeActivity.this, AssignmentView1.class);
                                startActivity(b);
                                break;

                            case R.id.event:
                                Intent c = new Intent(HomeActivity.this, EventActivity.class);
                                startActivity(c);
                                break;
                            case R.id.logout:
                                Intent d = new Intent(HomeActivity.this, LogoutActivity.class);
                                startActivity(d);
                                break;

                            case R.id.planner:
                                Intent e = new Intent(HomeActivity.this, OnTimePlanner.class);
                                startActivity(e);
                                break;

                        }

                        return true;
                    }
                });

        Toggle=new ActionBarDrawerToggle(this,Drawer,R.string.open,R.string.close);

        Drawer.addDrawerListener(Toggle);
        Toggle.syncState();

        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){

        if(Toggle.onOptionsItemSelected(item)){
            return true;

        }
return super.onOptionsItemSelected(item);
    }
}