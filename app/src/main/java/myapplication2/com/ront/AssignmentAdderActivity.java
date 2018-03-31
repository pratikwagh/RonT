//this activity is for adding assignments

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AssignmentAdderActivity extends AppCompatActivity {

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

    TextView DeadTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_adder);


        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        u=user.getUid();


        AssName = (EditText) findViewById(R.id.ass_name);
        DeadDate = (EditText) findViewById(R.id.set_dtime);
        DeadTime= (TextView) findViewById(R.id.set_etime);
        Prior = (EditText) findViewById(R.id.set_prior);
        Estime = (EditText) findViewById(R.id.set_Estime);

    }



//this function is called onclick off  date picker
    public void setDate(View view){
        android.app.DatePickerDialog datePickerDialog =  new DatePickerDialog(AssignmentAdderActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        timePickerDialog =new TimePickerDialog(AssignmentAdderActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
        myRef = database.getInstance().getReference().child(u).child("Assignmnet");


        //adding elements to database
        DatabaseReference newTask = myRef.push();
        newTask.child("name").setValue(name);
        newTask.child("dead_date").setValue(dead_date);
        newTask.child("dead_time").setValue(dtime);
        newTask.child("priority").setValue(prior);
        newTask.child("Estime").setValue(estime);
        Log.d("Rohit","push database");


        //going back to home activity
        Intent intent = new Intent(AssignmentAdderActivity.this,HomeActivity.class);
        startActivity(intent);


    }
}
