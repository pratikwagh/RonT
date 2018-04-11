package myapplication2.com.ront;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddTask extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    FirebaseUser user;


    EditText editTask;
    int  click = 0;

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    Calendar currentTime;
    int hour,minute,day,month,year,phr,pmin,pday,pmonth,pyear;
    String format,u;
    TextView set_time,set_date;
    Calendar calendar = null;

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    TimePickerDialog timePickerDialog;
    android.app.DatePickerDialog DatePickerDialog;
    private static AddTask inst;

    public static AddTask instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        u=user.getUid();


        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        set_time = (TextView) findViewById(R.id.set_time);
        set_date = (TextView) findViewById(R.id.set_date);

        currentTime = Calendar.getInstance();
        hour= currentTime.get(Calendar.HOUR_OF_DAY);
        minute= currentTime.get(Calendar.MINUTE);
        year=currentTime.get(Calendar.YEAR);
        month=currentTime.get(Calendar.MONTH);
        day=currentTime.get(Calendar.DAY_OF_MONTH);
        month=month;



        selectedTimeFormat(hour);
        //set_time.setText(hour + " : " + minute + " " + format );


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:

                Log.d("Rohit", "Fab 1");
                break;
            case R.id.fab2:

                Log.d("Rohit", "Fab 2");
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Rohit", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Rohit","open");

        }
    }

    public void addButtonClicked(View view){

        editTask = (EditText)findViewById(R.id.editTask);
        String name = editTask.getText().toString();

        //long date = System.currentTimeMillis();

        // SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        // String datestring = sdf.format(date);
        String timestring = set_time.getText().toString();
        String datestring = set_date.getText().toString();

        myRef = database.getInstance().getReference().child(u).child("Tasks");

        DatabaseReference newTask = myRef.push();
        newTask.child("name").setValue(name);
        newTask.child("date").setValue(datestring);
        newTask.child("time").setValue(timestring);

        calendar = Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), phr, pmin, 0);

        long time = calendar.getTimeInMillis();



        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, AlarmReceiver.class);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();

        Intent in = new Intent(AddTask.this,EventActivity.class);
        startActivity(in);

    }

    public void selectedTimeFormat(int hour){

        if(hour == 0){
            hour += 12;
            format = "AM";
        }
        else if(hour == 12){
            format ="PM";
        }
        else if(hour > 12){
            hour -= 12;
            format = "PM";
        }
        else {
            format = "AM";
        }
    }

    public void setDate(View view){
        DatePickerDialog datePickerDialog =  new DatePickerDialog(AddTask.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month =month+1;
                pday=dayOfMonth;
                pmonth=month;
                pyear=year;
                set_date.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },year,month,day);
        datePickerDialog.show();

    }

    public void setTime(View view){
        timePickerDialog =new TimePickerDialog(AddTask.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                phr=hourOfDay;
                pmin=minute;
                selectedTimeFormat(hourOfDay);
                set_time.setText(hourOfDay + " : " + minute + " " + format );

            }
        }, hour, minute, true);
        timePickerDialog.show();

    }

    public void onSwitchRepeat(View view){

    }

    public void setRepeatNo(View view){

    }

    public void setRepeatType(View view){

    }

    public void setAlarm(View view){

        if(view.getId() == R.id.fab1){
            click  = 1;
        }
        else if(view.getId() == R.id.fab2){
            click = 2;
        }

    }

}

