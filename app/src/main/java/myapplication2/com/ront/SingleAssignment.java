package myapplication2.com.ront;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleAssignment extends AppCompatActivity {

    private  String task_key = null;
    private TextView singletask;
    private TextView singleDate;
    private  TextView singleTime;
    private  TextView singlePriority;
    private  TextView singleEstime;
    private DatabaseReference mDatabase;

    FirebaseUser user;
    String u;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_assignment);

        task_key = getIntent().getExtras().getString("TaskId");

        user = FirebaseAuth.getInstance().getCurrentUser();
        u=user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(u).child("Assignment");

        singletask = (TextView)findViewById(R.id.singletask);
        singleTime = (TextView)findViewById(R.id.singletime);
        singleDate = (TextView)findViewById(R.id.singledate);
        singlePriority = (TextView)findViewById(R.id.singleprior);
        singleEstime = (TextView)findViewById(R.id.singleEstime);

        mDatabase.child(task_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String task_title = (String)dataSnapshot.child("name").getValue();
                String task_date  = (String)dataSnapshot.child("date").getValue();
                String task_time   = (String)dataSnapshot.child("time").getValue();
                String priority   = (String)dataSnapshot.child("priority").getValue();
                String estime   = (String)dataSnapshot.child("Estime").getValue();

                singletask.setText(task_title);
                singleDate.setText(task_date);
                singleTime.setText(task_time);
                singlePriority.setText(priority);
                singleEstime.setText(estime);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    //this function will be executed on click of delete button
    public void delClicked(View view) {


        //getting the passed values
        task_key = getIntent().getExtras().getString("TaskId");

        user = FirebaseAuth.getInstance().getCurrentUser();

        //gtting the user id
        u=user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(u).child("Assignment");


        //deleting the node for that task from firebase
        mDatabase.child(task_key).removeValue();

        //going back to assignmentview activity
        Intent intent = new Intent(SingleAssignment.this,AssignmentView1.class);
        startActivity(intent);


        return;
    }
}