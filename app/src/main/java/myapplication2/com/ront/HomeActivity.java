package myapplication2.com.ront;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 15/3/18.
 */

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mTaskList;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    String u,dayOfTheWeek ;


    private DrawerLayout Drawer;
    private ActionBarDrawerToggle Toggle;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        mTaskList = (RecyclerView) findViewById(R.id.task_list);
        mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        TextView bannerDay =(TextView)findViewById(R.id.bannerDay);
        TextView bannerDate =(TextView)findViewById(R.id.bannerDate);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        dayOfTheWeek = sdf.format(d);
        bannerDay.setText(dayOfTheWeek);

        long date =System.currentTimeMillis();
        SimpleDateFormat sdff = new SimpleDateFormat("dd MMMM yyyy");
        String dateString = sdff.format(date);
        bannerDate.setText(dateString);

        user = FirebaseAuth.getInstance().getCurrentUser();
        u=user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(u).child(dayOfTheWeek);




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

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TaskViewHolder(View itemView){
            super(itemView);
            mView =itemView;

        }
        public void setName(String name){
            TextView task_name =(TextView) mView.findViewById(R.id.taskName);
            task_name.setText(name);
        }
        public void setTime(String time){
            TextView task_time =(TextView) mView.findViewById(R.id.taskTime);
            task_time.setText(time);
        }

        public void setDate(String date){
            TextView task_date =(TextView) mView.findViewById(R.id.taskDate);
            task_date.setText(date);
        }
    }


    @Override
    public  boolean onOptionsItemSelected(MenuItem item){

        if(Toggle.onOptionsItemSelected(item)){
            return true;

        }
return super.onOptionsItemSelected(item);
    }



    public void onStart() {

        Log.d("assx","3");
        super.onStart();
        FirebaseRecyclerAdapter<Task,TaskViewHolder> FBRA = new FirebaseRecyclerAdapter<Task,TaskViewHolder>(
                Task.class,
                R.layout.task_row,
                TaskViewHolder.class,
                mDatabase.orderByChild("start")
        ) {


            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, Task model, int position) {

                Log.d("assx","4");

                final String task_key = getRef(position).getKey().toString();

                //populating the recycler view
                viewHolder.setName(model.getName());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //calling the intent SingleTask
                        Intent singleTaskActivity = new Intent(HomeActivity.this,SingleAssignment.class);
                        singleTaskActivity.putExtra("TaskId",task_key);
                        singleTaskActivity.putExtra("Weekday",dayOfTheWeek);
                        startActivity(singleTaskActivity);

                    }
                });

            }
        };
        mTaskList.setAdapter(FBRA);
    }
}