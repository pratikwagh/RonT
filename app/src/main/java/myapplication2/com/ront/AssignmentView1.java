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

public class AssignmentView1 extends AppCompatActivity {

    private RecyclerView mTaskList;
    private DatabaseReference mDatabase;
    FirebaseUser user;

    String u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_view1);

        Log.d("assx","1");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //recycler view object

        mTaskList = (RecyclerView) findViewById(R.id.assi_list);
        mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(new LinearLayoutManager(AssignmentView1.this));


        //firebas authentication and makin database referanc
        user = FirebaseAuth.getInstance().getCurrentUser();
        u=user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(u).child("Assignmnt");

        Log.d("assx","2");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.ass_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Intent a = new Intent(AssignmentView1.this, AssignmentAdderActivity.class);

                startActivity(a);
            }
        });
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

        Log.d("assx","3");
        super.onStart();
        FirebaseRecyclerAdapter<Task,AssignmentView1.TaskViewHolder> FBRA = new FirebaseRecyclerAdapter<Task, AssignmentView1.TaskViewHolder>(
                Task.class,
                R.layout.task_row,
                AssignmentView1.TaskViewHolder.class,
                mDatabase.orderByChild("name")
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

                        /*//calling the intent SingleTask
                        Intent singleTaskActivity = new Intent(AssignmentView1.this,SingleTask.class);
                        singleTaskActivity.putExtra("TaskId",task_key);
                        singleTaskActivity.putExtra("Weekday","Monday");
                        startActivity(singleTaskActivity);
*/
                    }
                });

            }
        };
        mTaskList.setAdapter(FBRA);
    }
}
