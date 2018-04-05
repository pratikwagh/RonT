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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class onPlannerDate extends AppCompatActivity {

    TextView tv;
    private RecyclerView mTaskList;
    private DatabaseReference mDatabase;
    FirebaseUser user;

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
