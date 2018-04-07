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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class onPlannerDate extends AppCompatActivity {

    TextView tv;
    private RecyclerView mTaskList;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    private FirebaseDatabase database;


    //list to store time slots of routines of day
    List<Integer> Mondays=new ArrayList<Integer>();
    List<Integer> Tuesdays=new ArrayList<Integer>();
    List<Integer> Wednesdays=new ArrayList<Integer>();
    List<Integer> Thursdays=new ArrayList<Integer>();
    List<Integer> Fridays=new ArrayList<Integer>();
    List<Integer> Saturdays=new ArrayList<Integer>();
    List<Integer> Sundays=new ArrayList<Integer>();


    List<Integer> Mondaye=new ArrayList<Integer>();
    List<Integer> Tuesdaye=new ArrayList<Integer>();
    List<Integer> Wednesdaye=new ArrayList<Integer>();
    List<Integer> Thursdaye=new ArrayList<Integer>();
    List<Integer> Fridaye=new ArrayList<Integer>();
    List<Integer> Saturdaye=new ArrayList<Integer>();
    List<Integer> Sundaye=new ArrayList<Integer>();


    //json to store assignments information
    JSONObject json = new JSONObject();
    JSONArray array = new JSONArray();
    JSONObject item = new JSONObject();

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

                            Mondays.add(start);
                            Mondaye.add(end);

                            int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));

                            Log.d("loopr",""+Mondays.size());


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

                            Tuesdays.add(start);
                            Tuesdaye.add(end);

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

                            Wednesdays.add(start);
                            Wednesdaye.add(end);

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

                            Thursdays.add(start);
                            Thursdaye.add(end);

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

                            Fridays.add(start);
                            Fridaye.add(end);

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

                            Saturdays.add(start);
                            Saturdaye.add(end);

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

                            Sundays.add(start);
                            Sundaye.add(end);

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




        //creating json file from
        database.getInstance().getReference().child(u).child("Assignment").orderByChild("priority").addListenerForSingleValueEvent(new ValueEventListener() {

            //we want to reverse the prirority , can be done using extra attribut storing negative of prirority
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    int esti = Integer.parseInt(snapshot.child("Estime").getValue().toString());
                    String ts = snapshot.child("Timestamp").getValue().toString();
                    String name = snapshot.child("name").getValue().toString();

                    int monx=Integer.parseInt(ts.substring(6,7));
                    int yrx=Integer.parseInt(ts.substring(0,3));
                    int datex=Integer.parseInt(ts.substring(4,5));

                    int hrx=Integer.parseInt(ts.substring(8,9));
                    int minx=Integer.parseInt(ts.substring(10,11));

                    //calculating total minutes
                    int timex=(hrx*60)+minx;


                    //calling function to get name of day
                    String dnx=getDayName(monx,datex,yrx);

                    List<Integer> temps=new ArrayList<Integer>();
                    List<Integer> tempe=new ArrayList<Integer>();

                    if (dnx.equals("Monday"))
                    {
                        int index=0;
                        for (int i:Mondays)
                        {
                            if (timex<=0)
                            {
                                break;
                            }

                            if (timex>minx && timex<Mondays.get(index+1)){
                                if (Mondays.get(index+1)-Mondaye.get(index)!=0)
                                {
                                    /*if (timex<Mondays.get(index+1)-Mondaye.get(index))
                                    {
                                        tempe.add(Mondays.get(index+1));

                                    }*/

                                        temps.add(Mondaye.get(index));
                                        tempe.add(Mondays.get(index+1));

                                        timex=timex-(Mondays.get(index+1)-Mondaye.get(index));


                                }

                            }


                            index++;

                        }

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    String getDayName(int monx,int datex,int yrx)
    {

        //grtting day of week
        Calendar calendar = Calendar.getInstance();
        calendar.set(monx, datex, yrx);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String dayNamex="";

        switch (dayOfWeek){
            case Calendar.SUNDAY:
                dayNamex="Sunday";

                break;
            case Calendar.MONDAY:
                dayNamex="Monday";

                break;
            case Calendar.TUESDAY:
                dayNamex="Tuesday";

                break;
            case Calendar.WEDNESDAY:
                dayNamex="Wednesday";

                break;
            case Calendar.THURSDAY:
                dayNamex="Thursday";

                break;
            case Calendar.FRIDAY:
                dayNamex="Friday";

                break;
            case Calendar.SATURDAY:
                dayNamex="Saturday";

                break;
        }

        return dayNamex;


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
