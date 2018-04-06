package myapplication2.com.ront;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class onPlannerDate extends AppCompatActivity {

    TextView tv;
    private RecyclerView mTaskList;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    private FirebaseDatabase database;


    //list to store time slots of routines of day
    List<String> Mondays=new ArrayList<String>();
    List<String> Tuesdays=new ArrayList<String>();
    List<String> Wednesdays=new ArrayList<String>();
    List<String> Thursdays=new ArrayList<String>();
    List<String> Fridays=new ArrayList<String>();
    List<String> Saturdays=new ArrayList<String>();
    List<String> Sundays=new ArrayList<String>();


    List<String> Mondaye=new ArrayList<String>();
    List<String> Tuesdaye=new ArrayList<String>();
    List<String> Wednesdaye=new ArrayList<String>();
    List<String> Thursdaye=new ArrayList<String>();
    List<String> Fridaye=new ArrayList<String>();
    List<String> Saturdaye=new ArrayList<String>();
    List<String> Sundaye=new ArrayList<String>();


    //json to store assignments information
    JSONObject json = new JSONObject();

    String u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_planner_date);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting clicked day from clicked date
        String date = getIntent().getExtras().getString("Date");
        String dayName = getIntent().getExtras().getString("DayName");

        int day = getIntent().getExtras().getInt("Day");

        tv = (TextView) findViewById(R.id.plDate);


        tv.setText(date + " " + dayName);


        //recycler view object

        mTaskList = (RecyclerView) findViewById(R.id.planner_list);
        mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(new LinearLayoutManager(onPlannerDate.this));


        //firebase authentication and makin database referance
        user = FirebaseAuth.getInstance().getCurrentUser();
        u = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(u).child(dayName);

        database = FirebaseDatabase.getInstance();


        //working on scheduling here
        String[] Days={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

        for (final String arrDay: Days) {


            database.getInstance().getReference().child(u).child(arrDay).orderByChild("start").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //snapshot will contain days node
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d("loopr", "inside first for loop");

                        Log.d("loopr", "Key 1 " + snapshot.getKey());


                        //storing start and end time of each day into a list
                        if(arrDay.equals("Monday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);


                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Mondays.add(""+start);
                            Mondaye.add(""+end);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/


                        }

                        if(arrDay.equals("Tuesday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Tuesdays.add(""+start);
                            Tuesdaye.add(""+end);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/


                        }

                        if(arrDay.equals("Wednesday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Wednesdays.add(""+start);
                            Wednesdaye.add(""+end);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/


                        }

                        if(arrDay.equals("Thursday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Thursdays.add(""+start);
                            Thursdaye.add(""+end);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/


                        }

                        if(arrDay.equals("Friday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Fridays.add(""+start);
                            Fridaye.add(""+end);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/


                        }

                        if(arrDay.equals("Saturday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Saturdays.add(""+start);
                            Saturdaye.add(""+end);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/


                        }

                        if(arrDay.equals("Sunday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Sundays.add(""+start);
                            Sundaye.add(""+end);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/

                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }
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

    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Task,Monday.TaskViewHolder> FBRA = new FirebaseRecyclerAdapter<Task, Monday.TaskViewHolder>(
                Task.class,
                R.layout.task_row,
                Monday.TaskViewHolder.class,
                mDatabase.orderByChild("start")
        ) {
            @Override
            protected void populateViewHolder(Monday.TaskViewHolder viewHolder, Task model, int position) {

                final String task_key = getRef(position).getKey().toString();

                //populating the recycler view
                viewHolder.setName(model.getName());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());

                String startTime=model.getDate();
                String endTime=model.getTime();

                Log.d("onxx st",startTime);
                Log.d("onxx et",endTime);



            }
        };
        mTaskList.setAdapter(FBRA);
    }

}
