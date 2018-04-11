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
import android.widget.RadioGroup;
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
import java.util.Stack;

public class onPlannerDate extends AppCompatActivity {

    TextView tv;
    TextView tt;
    private RecyclerView mTaskList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DatabaseReference mDatabase,mDatabase2;
    FirebaseUser user;
    private FirebaseDatabase database,database1;
    private FirebaseDatabase mDatabase1;


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

    List<String> Mondayna=new ArrayList<String>();
    List<String> Tuesdayna=new ArrayList<String>();
    List<String> Wednesdayna=new ArrayList<String>();
    List<String> Thursdayna=new ArrayList<String>();
    List<String> Fridayna=new ArrayList<String>();
    List<String> Saturdayna=new ArrayList<String>();
    List<String> Sundayna=new ArrayList<String>();


    //for storing free slots
    List<String> Mondaytt=new ArrayList<String>();
    List<String> Tuesdaytt=new ArrayList<String>();
    List<String> Wednesdaytt=new ArrayList<String>();
    List<String> Thursdaytt=new ArrayList<String>();
    List<String> Fridaytt=new ArrayList<String>();
    List<String> Saturdaytt=new ArrayList<String>();
    List<String> Sundaytt=new ArrayList<String>();


    //json to store assignments information
    JSONObject json = new JSONObject();
    JSONArray array = new JSONArray();
    JSONObject item = new JSONObject();

    String u;


    //for storing total time
    public int totalTime=0;

    //store the result of scheduling
    StringBuilder result = new StringBuilder();

    Integer esti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_planner_date);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting clicked day from clicked date
        final String date = getIntent().getExtras().getString("Date");
        final String dayName = getIntent().getExtras().getString("DayName");

        int day = getIntent().getExtras().getInt("Day");


        //setting text views
        tv = (TextView) findViewById(R.id.plDate);
        tt= (TextView) findViewById(R.id.ttime);


        tv.setText(date + " " + dayName);


        //recycler view object

        mTaskList = (RecyclerView) findViewById(R.id.planner_list);
        mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(new LinearLayoutManager(onPlannerDate.this));


        //firebase authentication and makin database referance
        user = FirebaseAuth.getInstance().getCurrentUser();
        u = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(u).child(dayName);
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child(u).child("Assignment");

        database = FirebaseDatabase.getInstance();
        mDatabase1 = FirebaseDatabase.getInstance();


        //testing
       // TaskViewHolder taskViewHolder=new TaskViewHolder();
        //taskViewHolder.setName("testing");


        //configuring radio button
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgp);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button

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
                                //viewHolder.setName("lol");
                                viewHolder.setDate(model.getDate());
                                viewHolder.setTime(model.getTime());

                                String startTime=model.getDate();
                                String endTime=model.getTime();

                                Log.d("onxx st",startTime);
                                Log.d("onxx et",endTime);



                            }
                        };
                        mTaskList.setAdapter(FBRA);

                        Toast.makeText(getApplicationContext(), "Today's Routine ", Toast.LENGTH_LONG).show();


                        //end of case 0

                break;
                    case 1: // secondbutton

                        FirebaseRecyclerAdapter<Task,AssignmentView1.TaskViewHolder> FBRA2 = new FirebaseRecyclerAdapter<Task, AssignmentView1.TaskViewHolder>(
                                Task.class,
                                R.layout.assign_row,
                                AssignmentView1.TaskViewHolder.class,
                                mDatabase2.orderByChild("date").equalTo(date)
                        ) {


                            @Override
                            protected void populateViewHolder(AssignmentView1.TaskViewHolder viewHolder, Task model, int position) {


                                final String task_key = getRef(position).getKey().toString();

                                if ((model.getDate()).equals(date)) {

                                    //populating the recycler view
                                    viewHolder.setName(model.getName());
                                    viewHolder.setDate(model.getDate());
                                    viewHolder.setTime(model.getTime());

                                }





                            }
                        };
                        mTaskList.setAdapter(FBRA2);

                        Toast.makeText(getApplicationContext(), "Today's Assignments ", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });









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

                            String rname=snapshot.child("name").getValue().toString();

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);


                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Mondays.add(start);
                            Mondaye.add(end);
                            Mondayna.add(rname);

                            int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));

                            Log.d("loopr",""+Mondays.size());

                            Log.d("loopr",rname);


                        }

                        if(arrDay.equals("Tuesday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            String rname=snapshot.child("name").getValue().toString();

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Tuesdays.add(start);
                            Tuesdaye.add(end);
                            Tuesdayna.add(rname);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/


                        }

                        if(arrDay.equals("Wednesday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            String rname=snapshot.child("name").getValue().toString();

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Wednesdays.add(start);
                            Wednesdaye.add(end);
                            Wednesdayna.add(rname);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/


                        }

                        if(arrDay.equals("Thursday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            String rname=snapshot.child("name").getValue().toString();

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Thursdays.add(start);
                            Thursdaye.add(end);
                            Thursdayna.add(rname);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/


                        }

                        if(arrDay.equals("Friday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            String rname=snapshot.child("name").getValue().toString();

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Fridays.add(start);
                            Fridaye.add(end);
                            Fridayna.add(rname);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/


                        }

                        if(arrDay.equals("Saturday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            String rname=snapshot.child("name").getValue().toString();

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Saturdays.add(start);
                            Saturdaye.add(end);
                            Saturdayna.add(rname);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/


                        }

                        if(arrDay.equals("Sunday"))
                        {

                            int start = Integer.parseInt(snapshot.child("start").getValue().toString());

                            int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                            String rname=snapshot.child("name").getValue().toString();

                            //converting to minutes
                            start=((start/100)*60)+(start%100);
                            end=((end/100)*60)+(end%100);

                            Log.d("loopr", "" + start);
                            Log.d("loopr", "" + end);

                            Sundays.add(start);
                            Sundaye.add(end);
                            Sundayna.add(rname);

                            /*int i=0;
                            Log.d("loopr","List"+Mondays.get(i++));*/

                        }

                    }

                    //calculating total time
                    Log.d("plln",dayName);

                    if (dayName.equals("Monday"))
                    {
                        Log.d("plln","Insie if");
                        Log.d("plln","size:"+Mondaye.size());
                        int index=0;
                        totalTime=0;
                        for (int i=0;i<Mondaye.size();i++)
                        {
                            int x=Mondaye.get(index)-Mondays.get(index);
                            totalTime=totalTime+x;
                            index++;

                            Log.d("plln","x:"+x);
                            Log.d("plln","i"+i);
                        }

                    }

                    if (dayName.equals("Tuesday"))
                    {
                        Log.d("plln","Inside if tues");
                        int index=0;
                        totalTime=0;
                        for (int i:Tuesdaye)
                        {
                            int x=Tuesdaye.get(index)-Tuesdays.get(index);
                            totalTime=totalTime+x;
                            index++;
                        }

                    }

                    if (dayName.equals("Wednesday"))
                    {
                        int index=0;
                        totalTime=0;
                        for (int i:Wednesdaye)
                        {
                            int x=Wednesdaye.get(index)-Wednesdays.get(index);
                            totalTime=totalTime+x;
                            index++;
                        }

                    }

                    if (dayName.equals("Thursday"))
                    {
                        int index=0;
                        totalTime=0;
                        for (int i:Thursdaye)
                        {
                            int x=Thursdaye.get(index)-Thursdays.get(index);
                            totalTime=totalTime+x;
                            index++;
                        }

                    }

                    if (dayName.equals("Friday"))
                    {
                        int index=0;
                        totalTime=0;
                        for (int i:Fridaye)
                        {
                            int x=Fridaye.get(index)-Fridays.get(index);
                            totalTime=totalTime+x;
                            index++;
                        }

                    }

                    if (dayName.equals("Saturday"))
                    {
                        int index=0;
                        totalTime=0;
                        for (int i:Saturdaye)
                        {
                            int x=Saturdaye.get(index)-Saturdays.get(index);
                            totalTime=totalTime+x;
                            index++;
                        }

                    }

                    if (dayName.equals("Sunday"))
                    {
                        int index=0;
                        totalTime=0;
                        for (int i:Sundaye)
                        {
                            int x=Sundaye.get(index)-Sundays.get(index);
                            totalTime=totalTime+x;
                            index++;
                        }

                    }

                    totalTime=(24*60)-totalTime;

                    //setting totAL Time on text view
                    tt.setText("You have "+(totalTime/60)+":"+(totalTime%60)+" hours of free time.");
                    Log.d("plln","total time:"+totalTime);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }


        Log.d("loopr","List1");






        //creating json file from
        database.getInstance().getReference().child(u).child("Assignment").orderByChild("npriority").addListenerForSingleValueEvent(new ValueEventListener() {

            //we want to reverse the prirority , can be done using extra attribut storing negative of prirority
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("tester", "inside assognmet data change");

                    esti = Integer.parseInt(snapshot.child("Estime").getValue().toString());
                    String ts = snapshot.child("Timestamp").getValue().toString();
                    String name = snapshot.child("name").getValue().toString();

                    int datex = Integer.parseInt(ts.substring(6, 7));
                    int yrx = Integer.parseInt(ts.substring(0, 3));
                    int monx = Integer.parseInt(ts.substring(4, 5));

                    int hrx = Integer.parseInt(ts.substring(8, 9));
                    int minx = Integer.parseInt(ts.substring(10, 11));

                    //calculating total minutes
                    int timex = (hrx * 60) + minx;


                    //calling function to get name of day
                    String dnx = getDayName(monx, datex, yrx);



                    List<Integer> temps = new ArrayList<Integer>();
                    List<Integer> tempe = new ArrayList<Integer>();


                    Log.d("loopr", "List2");


                    database1 = FirebaseDatabase.getInstance();
/*

                    switch (dnx)
                    {
                        case "Monday":
                            mondaySh(esti,name,timex);

                    }
*/



                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


  /* void mondaySh(int esti,String name,int timex)
   {
       int index=0;
        int i;

        //flag for getting first free slot
        int flag=0;

           for (i=Mondaye.size()-1; i>=0;i--)
           {
               //getting from end of the list
               int et=Mondaye.get(i);
               int st=Mondays.get(i);


               if (flag==0) {
                   if (et < timex && i == Mondaye.size() - 1) {


                       result.append(name + "," + et + "," + timex);
                       flag = 1;
                       esti=esti-(timex-et);

                   } else if (et < timex && i != Mondaye.size() - 1 && Mondaye.get(i + 1) > timex) {

                       result.append(name + "," + et + "," + timex);
                       flag = 1;
                       esti=esti-(timex-et);

                   } else if (et < timex && i != Mondaye.size() - 1 && Mondaye.get(i + 1) <= timex) {

                       result.append(name + "," + et + "," + Mondays.get(i + 1));
                       flag = 1;
                       esti=esti-(Mondays.get(i + 1)-et);

                   }
                   else if(Mondays.get(i-1)>timex)
                   {
                       result.append(name + "," + "0" + "," + timex);
                       esti=esti-timex;
                   }


               }
               else {
                   if(timex<Mondays.get(i-1) && timex<et && et!=Mondays.get(i-1))
                       result.append(name+","+","+et+","+Mondays.get(i-1));

               }


               result.append(Mondayna.get(i)+","+Mondays.get(i)+","+Mondaye.get(i));

           }

           Log.d("loopee",result.toString());
   }

    void tuesdaySh()
    {

    }

    void wednesdaySh()
    {

    }

    void thursdaySh()
    {

    }


    void fridaySh()
    {

    }


    void saturdaySh()
    {

    }

    void sundaySh()
    {

    }
*/


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
                //viewHolder.setName("lol");
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