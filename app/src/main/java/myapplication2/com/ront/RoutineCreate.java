package myapplication2.com.ront;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by user on 21/3/18.
 */

public class RoutineCreate extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    FirebaseUser user;

    TimePickerDialog timePickerDialog;

    TextView set_stime,set_etime;
    EditText tname;
    Button back;

    int hour,minute,day,month,year,phr,pmin,pday,pmonth,pyear;
    String format;
    Integer value;
    String j,u;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_create);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        u=user.getUid();

        tname     = (EditText) findViewById(R.id.set_name);
        set_stime = (TextView) findViewById(R.id.set_stime);
        set_etime = (TextView) findViewById(R.id.set_etime);

        final Intent intent = new Intent(getIntent());
        Bundle extras = intent.getExtras();


        value = intent.getIntExtra("value", -1);

        if(extras!=null)
        {
             j =(String) extras.get("Weekday");
        }

        back= (Button) findViewById(R.id.Back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(RoutineCreate.this,RoutineActivity.class);
                i.putExtra("value",value);
                startActivity(i);
            }
        });

    }

    public void addClicked(View view){

        tname     = (EditText) findViewById(R.id.set_name);
        String name = tname.getText().toString();
        String stime = set_stime.getText().toString();
        String etime = set_etime.getText().toString();

        Log.d("Rohit","open database");


        //u is the user id , j is the weekday
        myRef = database.getInstance().getReference().child(u).child(j);

        DatabaseReference newTask = myRef.push();
        newTask.child("name").setValue(name);
        newTask.child("date").setValue(stime);
        newTask.child("time").setValue(etime);
        Log.d("Rohit","push database");

        Intent intent = new Intent(RoutineCreate.this,RoutineActivity.class);
        intent.putExtra("value",value);
        startActivity(intent);


    }

    public void setsTime(View view){
        timePickerDialog =new TimePickerDialog(RoutineCreate.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                phr=hourOfDay;
                pmin=minute;
                selectedTimeFormat(hourOfDay,minute);
                set_stime.setText(hourOfDay + " : " + minute + " " + format );

            }
        }, hour, minute, false);
        timePickerDialog.show();

    }


    public void seteTime(View view){
        timePickerDialog =new TimePickerDialog(RoutineCreate.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                phr=hourOfDay;
                pmin=minute;
                selectedTimeFormat(hourOfDay,minute);
                set_etime.setText(hourOfDay + " : " + minute + " " + format );

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
    }
}
