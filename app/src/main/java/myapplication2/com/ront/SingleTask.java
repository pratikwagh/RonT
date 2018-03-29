package myapplication2.com.ront;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleTask extends AppCompatActivity {
    private  String task_key = null;
    private  String weekday = null;
    private TextView singletask;
    private TextView singleDate;
    private  TextView singleTime;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    String u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);

        task_key = getIntent().getExtras().getString("TaskId");
        weekday = getIntent().getExtras().getString("Weekday");

        user = FirebaseAuth.getInstance().getCurrentUser();
        u=user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(u).child(weekday);

        singletask = (TextView)findViewById(R.id.singletask);
        singleTime = (TextView)findViewById(R.id.singletime);
        singleDate = (TextView)findViewById(R.id.singledate);


        mDatabase.child(task_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String task_title = (String)dataSnapshot.child("name").getValue();
                String task_date  = (String)dataSnapshot.child("date").getValue();
                String task_time   = (String)dataSnapshot.child("time").getValue();

                singletask.setText(task_title);
                singleDate.setText(task_date);
                singleTime.setText(task_time);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}