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
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AssignmentActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth auth;

    TimePickerDialog timePickerDialog;
    android.app.DatePickerDialog DatePickerDialog;

    EditText AssName,DeadDate,Prior,Estime;
    Button back;
    int hour,minute,day,month,year,phr,pmin,pday,pmonth,pyear;
    String format,u;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);


        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        u=user.getUid();


        AssName = (EditText) findViewById(R.id.ass_name);
        DeadDate = (EditText) findViewById(R.id.set_dtime);
        Prior = (EditText) findViewById(R.id.set_prior);
        Estime = (EditText) findViewById(R.id.set_Estime);

    }
/*
    public void setEstime(View view){
        timePickerDialog =new TimePickerDialog(AssignmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                phr=hourOfDay;
                pmin=minute;
                selectedTimeFormat(hourOfDay,minute);
                Estime.setText(hourOfDay + " : " + minute + " " + format );

            }
        }, hour, minute, false);
        timePickerDialog.show();

    }

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
    }*/

    public void setDate(View view){
        android.app.DatePickerDialog datePickerDialog =  new DatePickerDialog(AssignmentActivity.this, new DatePickerDialog.OnDateSetListener() {
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

    public void addAssignment(View view){

        String name = AssName.getText().toString();
        String dead_date = DeadDate.getText().toString();
        String prior = Prior.getText().toString();
        String estime = Estime.getText().toString();

        Log.d("Rohit","open database");
        /*to reach a particular naode and then add the following data to it*/
        myRef = database.getInstance().getReference().child(u).child("Assignmnet");

        DatabaseReference newTask = myRef.push();
        newTask.child("name").setValue(name);
        newTask.child("dead_date").setValue(dead_date);
        newTask.child("priority").setValue(prior);
        newTask.child("Estime").setValue(estime);
        Log.d("Rohit","push database");

        Intent intent = new Intent(AssignmentActivity.this,HomeActivity.class);
        startActivity(intent);


    }
}
