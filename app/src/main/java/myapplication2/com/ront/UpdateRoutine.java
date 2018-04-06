package myapplication2.com.ront;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateRoutine extends AppCompatActivity {


    //firebase variables as public
    private FirebaseDatabase database;
    public static DatabaseReference myRef;
    private DatabaseReference mDatabase;
    FirebaseUser user;

    private AwesomeValidation awesomeValidation;

    TimePickerDialog timePickerDialog;

    TextView set_stime, set_etime;
    EditText tname;
    Button back, confirm;

    int hour, minute, day, month, year, phr, pmin, pday, pmonth, pyear;
    String format;
    Integer value;
    String j, u, taskid;


    //global variables to store time in firebase
    //storing time for ease of use
    public static int bst, bet;


    //flag to test the clash
    public static int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_routine);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        database = FirebaseDatabase.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        u = user.getUid();

        tname = (EditText) findViewById(R.id.set_name);
        set_stime = (TextView) findViewById(R.id.set_stime);
        set_etime = (TextView) findViewById(R.id.set_etime);

        final Intent intent = new Intent(getIntent());
        Bundle extras = intent.getExtras();

        //Awesome validation

        awesomeValidation.addValidation(this, R.id.set_name, ".{1,}", R.string.tnameerror);
        // awesomeValidation.addValidation(this, R.id.set_stime, ".{6,}", R.string.passworderror);
        //  awesomeValidation.addValidation(this, R.id.set_etime, ".{6,}", R.string.passworderror);


        value = intent.getIntExtra("value", -1);

        if (extras != null) {
            j = (String) extras.get("Weekday");
            taskid = (String) extras.get("TaskId");
        }

        mDatabase = FirebaseDatabase.getInstance().getReference().child(u).child(j);
        mDatabase.child(taskid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String task_title = (String) dataSnapshot.child("name").getValue();
                String task_date = (String) dataSnapshot.child("date").getValue();
                String task_time = (String) dataSnapshot.child("time").getValue();

                tname.setText(task_title);
                set_stime.setText(task_date);
                set_etime.setText(task_time);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        back = (Button) findViewById(R.id.Back);
        confirm = (Button) findViewById(R.id.add);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UpdateRoutine.this, SingleTask.class);
                // intent.putExtra("value", value);
                intent.putExtra("TaskId",taskid);
                intent.putExtra("Weekday",j);
                intent.putExtra("value", value);
                startActivity(intent);
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()) {
                    addClicked(view);
                }
            }
        });

    }

    public void addClicked(View view){

        flag=0;



        if (bst >= bet)
        {
            Toast.makeText(getApplicationContext(), "Start time should be less than End Time",
                    Toast.LENGTH_LONG).show();

            return;
        }


        database.getInstance().getReference().child(u).child(j).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("slotClash","2");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Log.d("slotClash", "3");


                    int start = Integer.parseInt(snapshot.child("start").getValue().toString());
                    Log.d("slotClash", "4");
                    int end = Integer.parseInt(snapshot.child("end").getValue().toString());

                    Log.d("slotClash", "" + start + " " + end);

                    if (bst > start && bst < end) {

                        Log.d("slotClash", "5");
                        flag = 1;
                    }

                    if (bet > start && bet < end) {
                        Log.d("slotClash", "6");
                        flag = 1;
                    }



                    if (bst <= start && bet >= end) {
                        flag = 1;
                    }



                }

                if(flag==1)
                {

                    Log.d("slotClash","if");

                    Toast.makeText(getApplicationContext(), "This time slot is in use. Please choose an empty time slot.",
                            Toast.LENGTH_LONG).show();

                    return;

                }
                else {


                    //tname     = (EditText) findViewById(R.id.set_name);
                    String name = tname.getText().toString();
                    String stime = set_stime.getText().toString();
                    String etime = set_etime.getText().toString();

                    Log.d("RoutineCreatorDebug", "open database");

                    myRef = database.getInstance().getReference().child(u).child(j).child(taskid);

                   // DatabaseReference newTask = myRef.push();
                    myRef.child("name").setValue(name);
                    myRef.child("date").setValue(stime);
                    myRef.child("time").setValue(etime);

                    //extra time field for easy computation
                    myRef.child("start").setValue(bst);
                    myRef.child("end").setValue(bet);


                    Log.d("RoutineCreatorDebug", "push database");


                    Intent intent = new Intent(UpdateRoutine.this, SingleTask.class);
                    intent.putExtra("value", value);
                    intent.putExtra("TaskId",taskid);
                    intent.putExtra("Weekday",j);
                    startActivity(intent);

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });





    }

    public void setsTime(View view){
        timePickerDialog =new TimePickerDialog(UpdateRoutine.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                phr=hourOfDay;
                pmin=minute;
                selectedTimeFormat(hourOfDay,minute);
                set_stime.setText(hourOfDay%12 + " : " + minute + " " + format );

                //extra time field for easy computation
                bst= (hourOfDay*100)+minute;

            }
        }, hour, minute, false);
        timePickerDialog.show();

    }


    public void seteTime(View view){
        timePickerDialog =new TimePickerDialog(UpdateRoutine.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                phr=hourOfDay;
                pmin=minute;
                selectedTimeFormat(hourOfDay,minute);
                set_etime.setText(hourOfDay%12 + " : " + minute + " " + format );

                //extra time field for easy computation
                bet=(hourOfDay*100)+minute;

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

