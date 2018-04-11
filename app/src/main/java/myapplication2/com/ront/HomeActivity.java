


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
        import android.view.ViewGroup;
        import android.widget.LinearLayout;
        import android.widget.RadioGroup;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.stream.Collectors;

        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.math.BigInteger;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;

/**
 * Created by user on 15/3/18.
 */

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mTaskList;
    private DatabaseReference mDatabase,mDatabase1;
    FirebaseUser user;
    String u,dayOfTheWeek ;
    // String[] curr_timestamp = new String[1];

    String current;
    private DrawerLayout Drawer;
    private ActionBarDrawerToggle Toggle;
    Toolbar toolbar;
    int currenttimestamp;
    static int flag =0;

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
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child(u).child("Assignment");




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


        String month,day,hr,min;
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH) +1;
        if (mMonth < 10 )
            month = "0" + String.valueOf(mMonth);
        else
            month=String.valueOf(mMonth);

        int mDay = c.get(Calendar.DAY_OF_MONTH);
        if (mDay < 10 )
            day = "0" + String.valueOf(mDay);
        else
            day=String.valueOf(mDay);

        int mHr=c.get(Calendar.HOUR_OF_DAY);
        if (mHr < 10 )
            hr = "0" + String.valueOf(mHr);
        else
            hr=String.valueOf(mHr);
        int mMin=c.get(Calendar.MINUTE);
        if (mMin < 10 )
            min = "0" + String.valueOf(mMin);
        else
            min=String.valueOf(mMin);

        int add =0;
        current = String.valueOf(mYear)+month+day+hr+min;

        current =current.replaceAll("[^\\p{Print}]","");
        String[] ary = current.split("");
        List<String> list = new ArrayList<String>();

        for(String s : ary) {
            if(s != null && s.length() > 0 && s != "") {
                list.add(s);
            }
        }

        ary = list.toArray(new String[list.size()]);
        current = Arrays.toString(ary);




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

                        FirebaseRecyclerAdapter<Task,TaskViewHolder> FBRA = new FirebaseRecyclerAdapter<Task,TaskViewHolder>(
                                Task.class,
                                R.layout.task_row,
                                TaskViewHolder.class,
                                mDatabase.orderByChild("start")
                        ) {
                            @Override
                            protected void populateViewHolder(TaskViewHolder viewHolder, Task model, int position) {

                                final String task_key = getRef(position).getKey().toString();

                                //populating the recycler view
                                flag =1;
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

                        // Toast.makeText(getApplicationContext(), "Today's Routine ", Toast.LENGTH_LONG).show();


                        //end of case 0

                        break;
                    case 1: // secondbutton

                        FirebaseRecyclerAdapter<Task,TaskViewHolder> FBRA2 = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(
                                Task.class,
                                R.layout.assign_row,
                                TaskViewHolder.class,
                                mDatabase1
                        ) {


                            @Override
                            protected void populateViewHolder(TaskViewHolder viewHolder, Task model, int position) {




                                String timestamp = model.getTimestamp();
                                timestamp =timestamp.replaceAll("[^\\p{Print}]","");
                                String[] ary = timestamp.split("");
                                List<String> list = new ArrayList<String>();


                                for(String s : ary) {
                                    if(s != null && s.length() > 0 && s != "") {
                                        list.add(s);
                                    }
                                }

                                ary = list.toArray(new String[list.size()]);
                                timestamp = Arrays.toString(ary);
                                Log.d("msg",current);
                                Log.d("msg",timestamp);

                                if (current.compareTo(timestamp) > 0) {




                                    viewHolder.mView.setVisibility(View.GONE);
                                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));



                                }
                                else {
                                    final String task_key = getRef(position).getKey().toString();

                                    //populating the recycler view

                                    viewHolder.setName(model.getName());

                                    viewHolder.setDate(model.getDate());

                                    viewHolder.setTime(model.getTime());

                                }




                            }
                        };
                        mTaskList.setAdapter(FBRA2);

                        Toast.makeText(getApplicationContext(), "Upcoming Assignments ", Toast.LENGTH_LONG).show();
                        break;
                }
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
                // String[] ass_timestamp = new String[1];



                //Log.d("currenttimestamp","value:"+currenttimestamp);
                flag=1;

                //populating the recycler view
                viewHolder.setName(model.getName());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());

                /*viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //calling the intent SingleTask
                        Intent singleTaskActivity = new Intent(HomeActivity.this,SingleTask.class);
                        singleTaskActivity.putExtra("TaskId",task_key);
                        singleTaskActivity.putExtra("Weekday",dayOfTheWeek);
                        startActivity(singleTaskActivity);

                    }
                });*/

            }
        };
        mTaskList.setAdapter(FBRA);
    }


}
