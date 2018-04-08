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
import java.util.Stack;

public class onPlannerDate extends AppCompatActivity {

    TextView tv;
    private RecyclerView mTaskList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DatabaseReference mDatabase;
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


    //json to store assignments information
    JSONObject json = new JSONObject();
    JSONArray array = new JSONArray();
    JSONObject item = new JSONObject();

    String u;

    Integer esti;

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
        mDatabase1 = FirebaseDatabase.getInstance();


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


        Log.d("loopr","List1");


        //creating json file from
        database.getInstance().getReference().child(u).child("Assignment").orderByChild("npriority").addListenerForSingleValueEvent(new ValueEventListener() {

            //we want to reverse the prirority , can be done using extra attribut storing negative of prirority
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.d("tester","inside assognmet data change");

                    esti = Integer.parseInt(snapshot.child("Estime").getValue().toString());
                    String ts = snapshot.child("Timestamp").getValue().toString();
                    final String name = snapshot.child("name").getValue().toString();

                    int datex=Integer.parseInt(ts.substring(6,7));
                    int yrx=Integer.parseInt(ts.substring(0,3));
                    int monx=Integer.parseInt(ts.substring(4,5));

                    int hrx=Integer.parseInt(ts.substring(8,9));
                    int minx=Integer.parseInt(ts.substring(10,11));

                    //calculating total minutes
                    final int timex=(hrx*60)+minx;


                    //calling function to get name of day
                    String dnx=getDayName(monx,datex,yrx);

                    List<Integer> temps=new ArrayList<Integer>();
                    List<Integer> tempe=new ArrayList<Integer>();


                    Log.d("loopr","List2");


                    database1 = FirebaseDatabase.getInstance();

                    while(esti > 0)
                    {
                        Log.d("tester","inside while:"+esti);



                    if(dnx.equals("Monday"))
                    {

                        Log.d("loopr","Monday");
                        Log.d("tester","inside Monday");
                       final Stack<String> monday = new Stack<String>();

                        database.getInstance().getReference().child(u).child("Monday").orderByChild("nend").addListenerForSingleValueEvent(new ValueEventListener() {


                            //

                       @Override

                       public void onDataChange(DataSnapshot dataSnapshot) {
                           Log.d("tester","inside modat data change");
                           Log.d("loopr","Monday");

                           Integer flag=0;
                           int Size = Mondays.size() - 1;
                           Log.d("Monday","1");


                    //snapshot will contain days node
                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                         if(Mondaye.get(Size) >= timex)
                         {
                            String routine=snapshot.child("name").getValue().toString() +","+ Mondays.get(Size) + ","+  Mondaye.get(Size);

                            monday.push(routine);
                             Log.d("Monday","2");
                         }
                         else
                         {
                             Log.d("Monday","3");
                             Integer remaining;
                             if(flag == 1)
                             {
                               esti = esti - ( Mondaye.get(Size) - Mondays.get(Size+1) );

                               remaining= ( Mondaye.get(Size) - Mondays.get(Size+1) );
                             }
                             else {
                                 esti = esti - (timex - Mondaye.get(Size));
                                 remaining=(timex - Mondaye.get(Size));
                             }
                             flag=1;
                             String assignmentname = name + ",a," + remaining;
                             monday.push(assignmentname);
                         }

                         Size=Size-1;

                         if(Size<0)
                             break;

                       }


                       //Call recycler view

                           mAdapter = new MyAdapter(monday);
                           mTaskList.setAdapter(mAdapter);
                           mAdapter.notifyDataSetChanged();


                       }





                       public void onCancelled(DatabaseError databaseError) {

                           Log.d("Monday","cancel");

                           }
                       });



                      dnx="Sunday";
                    }
                    else if(dnx.equals("Sunday"))
                    {
                        Log.d("loopr","Sunday");
                       final Stack<String> sunday = new Stack<String>();



                        FirebaseDatabase.getInstance().getReference().child(u).child("Sunday").orderByChild("nend").addListenerForSingleValueEvent(new ValueEventListener() {

                            Integer Size = Sundaye.size() - 1;


                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Integer flag=0;

                                //snapshot will contain days node
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    if(Sundaye.get(Size) >= timex)
                                    {
                                        Log.d("Tuesday","1");
                                        String routine=snapshot.child("name").getValue().toString() +","+ Sundays.get(Size) + ","+  Sundaye.get(Size);

                                        sunday.push(routine);
                                    }
                                    else
                                    {
                                        Log.d("Monday","2");
                                        Integer remaining;
                                        if(flag == 1)
                                        {
                                            esti = esti - ( Sundaye.get(Size) - Sundays.get(Size+1) );

                                            remaining= ( Sundaye.get(Size) - Sundays.get(Size+1) );
                                        }
                                        else {
                                            esti = esti - (timex - Sundaye.get(Size));
                                            remaining=(timex - Sundaye.get(Size));
                                        }
                                        flag=1;
                                        String assignmentname = name + ",a," + remaining;
                                        sunday.push(assignmentname);
                                    }

                                    Size=Size-1;

                                    if(Size<0)
                                        break;

                                }
                            }




                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                Log.d("Tuesday","3");
                            }
                        });

                      dnx="Saturday";
                    }
                    else if(dnx.equals("Saturday"))
                    {
                        Log.d("loopr","Saturday");
                       final Stack<String> saturday = new Stack<String>();


                        FirebaseDatabase.getInstance().getReference().child(u).child("Saturday").orderByChild("nend").addListenerForSingleValueEvent(new ValueEventListener() {

                            Integer Size = Saturdaye.size() - 1;


                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Integer flag=0;

                                //snapshot will contain days node
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    if(Saturdaye.get(Size) >= timex)
                                    {
                                        Log.d("Saturday","1");
                                        String routine=snapshot.child("name").getValue().toString() +","+ Saturdays.get(Size) + ","+  Saturdaye.get(Size);

                                        saturday.push(routine);
                                    }
                                    else
                                    {
                                        Log.d("Saturday","2");
                                        Integer remaining;
                                        if(flag == 1)
                                        {
                                            esti = esti - ( Saturdaye.get(Size) - Saturdays.get(Size+1) );

                                            remaining= ( Saturdaye.get(Size) - Saturdays.get(Size+1) );
                                        }
                                        else {
                                            esti = esti - (timex - Saturdaye.get(Size));
                                            remaining=(timex - Saturdaye.get(Size));
                                        }
                                        flag=1;
                                        String assignmentname = name + ",a," + remaining;
                                        saturday.push(assignmentname);
                                    }

                                    Size=Size-1;

                                    if(Size<0)
                                        break;

                                }
                            }




                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                Log.d("Saturday","3");
                            }
                        });


                        dnx="Friday";
                    }
                    else if(dnx.equals("Friday"))
                    {
                        Log.d("loopr","Friday");
                       final Stack<String> friday = new Stack<String>();


                        FirebaseDatabase.getInstance().getReference().child(u).child("Friday").orderByChild("nend").addListenerForSingleValueEvent(new ValueEventListener() {

                            Integer Size = Fridaye.size() - 1;


                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Integer flag=0;

                                //snapshot will contain days node
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    if(Fridaye.get(Size) >= timex)
                                    {
                                        Log.d("Friday","1");
                                        String routine=snapshot.child("name").getValue().toString() +","+ Fridays.get(Size) + ","+  Fridaye.get(Size);

                                        friday.push(routine);
                                    }
                                    else
                                    {
                                        Log.d("Friday","2");
                                        Integer remaining;
                                        if(flag == 1)
                                        {
                                            esti = esti - ( Fridaye.get(Size) - Fridays.get(Size+1) );

                                            remaining= ( Fridaye.get(Size) - Fridays.get(Size+1) );
                                        }
                                        else {
                                            esti = esti - (timex - Fridaye.get(Size));
                                            remaining=(timex - Fridaye.get(Size));
                                        }
                                        flag=1;
                                        String assignmentname = name + ",a," + remaining;
                                        friday.push(assignmentname);
                                    }

                                    Size=Size-1;

                                    if(Size<0)
                                        break;

                                }
                            }




                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                Log.d("Friday","3");
                            }
                        });

                      dnx="Thursday";
                    }
                    else if(dnx.equals("Thursday"))
                    {
                        Log.d("loopr","Thursday");
                      final Stack<String> thursday = new Stack<String>();

                        FirebaseDatabase.getInstance().getReference().child(u).child("Thursday").orderByChild("nend").addListenerForSingleValueEvent(new ValueEventListener() {

                            Integer Size = Thursdaye.size() - 1;


                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Integer flag=0;

                                //snapshot will contain days node
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    if(Thursdaye.get(Size) >= timex)
                                    {
                                        Log.d("Thursday","1");
                                        String routine=snapshot.child("name").getValue().toString() +","+ Thursdays.get(Size) + ","+  Thursdaye.get(Size);

                                        thursday.push(routine);
                                    }
                                    else
                                    {
                                        Log.d("Thursday","2");
                                        Integer remaining;
                                        if(flag == 1)
                                        {
                                            esti = esti - ( Thursdaye.get(Size) - Thursdays.get(Size+1) );

                                            remaining= ( Thursdaye.get(Size) - Thursdays.get(Size+1) );
                                        }
                                        else {
                                            esti = esti - (timex - Thursdaye.get(Size));
                                            remaining=(timex - Thursdaye.get(Size));
                                        }
                                        flag=1;
                                        String assignmentname = name + ",a," + remaining;
                                        thursday.push(assignmentname);
                                    }

                                    Size=Size-1;

                                    if(Size<0)
                                        break;

                                }
                            }




                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                Log.d("Thursday","3");
                            }
                        });

                      dnx="Wednesday";
                    }
                    else if(dnx.equals("Wednesday"))
                    {
                        Log.d("loopr","Wednesday");
                       final Stack<String> wednesday = new Stack<String>();

                        FirebaseDatabase.getInstance().getReference().child(u).child("Wednesday").orderByChild("nend").addListenerForSingleValueEvent(new ValueEventListener() {

                            Integer Size = Wednesdaye.size() - 1;


                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Integer flag=0;

                                //snapshot will contain days node
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    if(Wednesdaye.get(Size) >= timex)
                                    {
                                        Log.d("Wednseday","1");
                                        String routine=snapshot.child("name").getValue().toString() +","+ Wednesdays.get(Size) + ","+  Wednesdaye.get(Size);

                                        wednesday.push(routine);
                                    }
                                    else
                                    {
                                        Log.d("Wednesday","2");
                                        Integer remaining;
                                        if(flag == 1)
                                        {
                                            esti = esti - ( Wednesdaye.get(Size) - Wednesdays.get(Size+1) );

                                            remaining= ( Wednesdaye.get(Size) - Wednesdays.get(Size+1) );
                                        }
                                        else {
                                            esti = esti - (timex - Wednesdaye.get(Size));
                                            remaining=(timex - Wednesdaye.get(Size));
                                        }
                                        flag=1;
                                        String assignmentname = name + ",a," + remaining;
                                        wednesday.push(assignmentname);
                                    }

                                    Size=Size-1;

                                    if(Size<0)
                                        break;

                                }
                            }




                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                Log.d("Wednesday","3");
                            }
                        });

                       dnx="Tuesday";
                    }
                    else if(dnx.equals("Tuesday"))
                    {
                        Log.d("loopr","Tuesday");
                        final Stack<String> tuesday = new Stack<String>();

                        FirebaseDatabase.getInstance().getReference().child(u).child("Tuesday").orderByChild("nend").addListenerForSingleValueEvent(new ValueEventListener() {

                            Integer Size = Tuesdaye.size() - 1;


                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Integer flag=0;

                                //snapshot will contain days node
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    if(Tuesdaye.get(Size) >= timex)
                                    {
                                        Log.d("Tuesday","1");
                                        String routine=snapshot.child("name").getValue().toString() +","+ Tuesdays.get(Size) + ","+  Tuesdaye.get(Size);

                                        tuesday.push(routine);
                                    }
                                    else
                                    {
                                        Log.d("Tuesday","2");
                                        Integer remaining;
                                        if(flag == 1)
                                        {
                                            esti = esti - ( Tuesdaye.get(Size) - Tuesdays.get(Size+1) );

                                            remaining= ( Tuesdaye.get(Size) - Tuesdays.get(Size+1) );
                                        }
                                        else {
                                            esti = esti - (timex - Tuesdaye.get(Size));
                                            remaining=(timex - Tuesdaye.get(Size));
                                        }
                                        flag=1;
                                        String assignmentname = name + ",a," + remaining;
                                        tuesday.push(assignmentname);
                                    }

                                    Size=Size-1;

                                    if(Size<0)
                                        break;

                                }
                            }




                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                Log.d("Tuesday","3");
                            }
                        });

                       dnx="Monday";
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