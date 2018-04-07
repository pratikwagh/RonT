package myapplication2.com.ront;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class onPlannerDate extends AppCompatActivity {

    TextView tv;
    private RecyclerView mTaskList;
    private DatabaseReference mDatabase;
    FirebaseUser user;

    AlarmManager am;


    String u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_planner_date);

         am = (AlarmManager) getSystemService(ALARM_SERVICE);

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

        Log.d("msg", "hello");
       // Log.d("msg", String.valueOf(time));


        //firebas authentication and makin database referance
        user = FirebaseAuth.getInstance().getCurrentUser();
        u = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(u).child(dayName);


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
            Log.d("msg", String.valueOf(time));
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

                int hrsmilli = 0,actualtime=0,minmilli=0;

                String time,hrs,minute,noon,check;

                //populating the recycler view
                viewHolder.setName(model.getName());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());


                Log.d("msg", "Notify");

                //Notification


          //Getting time
                time = model.getTime();

          //Getting hrs

                 check=Character.toString(time.charAt(1));

                Log.d("msg1", check);
               /* Log.d("msg2",Character.toString(time.charAt(2)));
                Log.d("msg3",Character.toString(time.charAt(3)));
                Log.d("msg4",Character.toString(time.charAt(4)));
                Log.d("msg5",Character.toString(time.charAt(5)));
                Log.d("msg6",Character.toString(time.charAt(6)));
                Log.d("msg7",Character.toString(time.charAt(7)));
                Log.d("msg8",Character.toString(time.charAt(8)));*/

                 if(Character.isWhitespace(time.charAt(1)))
                    hrs = time.substring(0, 1);
                 else
                     hrs = time.substring(0, 2);

                Log.d("msg", hrs);

            //Converting hours in string to int

                   hrsmilli = Integer.parseInt(hrs);

                Log.d("msg", String.valueOf(hrsmilli));


                
//converting hours to millisecond

                hrsmilli = hrsmilli * 60 * 60 * 1000;

                Log.d("msg", String.valueOf(hrsmilli));



      //check AM or PM

                 noon = time.substring(6, 8);

                Log.d("msg", noon);

                if (Objects.equals(noon, "PM")) {
                    hrsmilli = hrsmilli + 12 * 60 * 60 * 1000;
                }


          //getting minutes from string

                if(Character.isWhitespace(time.charAt(5)))
                   minute = time.substring(4, 5);
                else
                    minute = time.substring(4, 6);

                Log.d("msg", minute);

                minmilli = Integer.parseInt(minute);

                minmilli = minmilli * 60 * 1000;


                actualtime = hrsmilli + minmilli;



                actualtime = (int) ((Long) (Long.valueOf(actualtime)) - System.currentTimeMillis());

                Log.d("msg", String.valueOf(actualtime));
                if (actualtime >= 0) {


                Intent i = new Intent(onPlannerDate.this, AssignmentNotification.class);

                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);



                am.set(AlarmManager.RTC_WAKEUP, 200000, pi);

            }

                /*viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //calling the intent SingleTask
                        Intent singleTaskActivity = new Intent(getActivity(),SingleTask.class);
                        singleTaskActivity.putExtra("TaskId",task_key);
                        singleTaskActivity.putExtra("Weekday","Monday");
                        startActivity(singleTaskActivity);

                    }
                });*/

            }
        };
        mTaskList.setAdapter(FBRA);
    }

}
