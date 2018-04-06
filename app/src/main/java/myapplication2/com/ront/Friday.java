package myapplication2.com.ront;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Friday extends Fragment
{

    private RecyclerView mTaskList;
    private DatabaseReference mDatabase;
    FirebaseUser user;

    String u;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        // keep the fragment and all its data across screen rotation
        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.fragment_wednesday, container, false);

        mTaskList = (RecyclerView) rootView.findViewById(R.id.task_list);
        mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(new LinearLayoutManager(getActivity()));

        user = FirebaseAuth.getInstance().getCurrentUser();
        u=user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(u).child("Friday");

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent a = new Intent(getActivity(), RoutineCreate.class);
                a.putExtra("Weekday", "Friday");
                a.putExtra("value", 4);
                startActivity(a);

            }
        });

        return rootView;
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
        FirebaseRecyclerAdapter<Task,TaskViewHolder> FBRA = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(
                Task.class,
                R.layout.task_row,
                TaskViewHolder.class,
                mDatabase.orderByChild("start")
        ) {
            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, Task model, int position) {

                final String task_key = getRef(position).getKey().toString();
                viewHolder.setName(model.getName());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleTaskActivity = new Intent(getActivity(),SingleTask.class);
                        singleTaskActivity.putExtra("TaskId",task_key);
                        singleTaskActivity.putExtra("Weekday","Friday");
                        singleTaskActivity.putExtra("value", 4);
                        startActivity(singleTaskActivity);

                    }
                });

            }
        };
        mTaskList.setAdapter(FBRA);
    }








}