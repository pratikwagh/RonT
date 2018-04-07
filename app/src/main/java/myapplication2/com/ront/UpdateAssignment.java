package myapplication2.com.ront;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;

public class UpdateAssignment extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;

    TimePickerDialog timePickerDialog;
    android.app.DatePickerDialog DatePickerDialog;

    EditText AssName,DeadDate,Prior,Estime,DeadTime;
    Button back;
    int hour,minute,day,month,year,phr,pmin,pday,pmonth,pyear;
    String format,u;
    FirebaseUser user;

    String task_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_assignment);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        u=user.getUid();


        AssName = (EditText) findViewById(R.id.ass_name);
        DeadDate = (EditText) findViewById(R.id.set_dtime);
        DeadTime= (EditText) findViewById(R.id.set_etime);
        Prior = (EditText) findViewById(R.id.set_prior);
        Estime = (EditText) findViewById(R.id.set_Estime);


        task_key = getIntent().getExtras().getString("TaskId");

        mDatabase = FirebaseDatabase.getInstance().getReference().child(u).child("Assignment");
        mDatabase.child(task_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String task_title = (String) dataSnapshot.child("name").getValue();
                String task_date = (String) dataSnapshot.child("date").getValue();
                String task_time = (String) dataSnapshot.child("time").getValue();
                String priority = (String) dataSnapshot.child("priority").getValue();
                String est_time = (String) dataSnapshot.child("Estime").getValue();

                AssName.setText(task_title);
                DeadDate.setText(task_date);
                DeadTime.setText(task_time);
                Prior.setText(priority);
                Estime.setText(est_time);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //this function is called onclick off  date picker
    public void setDate(View view){
        android.app.DatePickerDialog datePickerDialog =  new DatePickerDialog(UpdateAssignment.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month =month+1;
                pday=dayOfMonth;
                pmonth=month;
                pyear=year;
                DeadDate.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },year,month,day);
        datePickerDialog.show();

    }

    //this function is called onclick off  time picker
    public void seteTime(View view){
        timePickerDialog =new TimePickerDialog(UpdateAssignment.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                phr=hourOfDay;
                pmin=minute;
                selectedTimeFormat(hourOfDay,minute);
                DeadTime.setText(hourOfDay%12 + " : " + minute + " " + format );

            }
        }, hour, minute, false);
        timePickerDialog.show();

    }


    //this function is called in function seteTim to specify time format
    public void selectedTimeFormat(int hour,int min){

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

    public void addAssignment(View view){

        String name = AssName.getText().toString();
        String dead_date = DeadDate.getText().toString();
        String dtime = DeadTime.getText().toString();
        String prior = Prior.getText().toString();
        String estime = Estime.getText().toString();

        Log.d("Rohit","open database");


        /*to reach a particular naode and then add the following data to it.This thing is same in all activity*/
        myRef = database.getInstance().getReference().child(u).child("Assignment").child(task_key);


        //adding elements to database
       // DatabaseReference newTask = myRef.push();
        myRef.child("name").setValue(name);
        myRef.child("date").setValue(dead_date);
        myRef.child("time").setValue(dtime);
        myRef.child("priority").setValue(prior);
        myRef.child("Estime").setValue(estime);

        //creating a timestamp of the assignment


        //appending 0 if less than 10
        String m0="",d0="",h0="",mi0="";
        if (pmonth<10)
            m0="0";

        if (pday<10)
            d0="0";

        if (phr<10)
            h0="0";

        if (pmin<10)
            mi0="0";



        String timestamp;
        timestamp=""+pyear+m0+pmonth+d0+pday+h0+phr+mi0+pmin;

        Log.d("tss","yr"+pyear);
        Log.d("tss","mn"+pmonth);
        Log.d("tss","dy"+pday);
        Log.d("tss","hr"+phr);
        Log.d("tss","mn"+pmin);
        Log.d("tss","ts"+timestamp);


        //pusing timestamp too the database
        myRef.child("Timestamp").setValue(timestamp);



        Log.d("AssAddr","after pushing");


        //going back to SingleAssignment activity
        Intent intent = new Intent(UpdateAssignment.this,SingleAssignment.class);
        intent.putExtra("TaskId",task_key);
        startActivity(intent);


    }

    //for navigating back
    public void back (View view){

        Intent intent = new Intent(UpdateAssignment.this,SingleAssignment.class);
        intent.putExtra("TaskId",task_key);
        startActivity(intent);

    }

}
