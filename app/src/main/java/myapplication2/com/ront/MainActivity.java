package myapplication2.com.ront;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.*;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by user on 7/3/18.
 */

public class MainActivity extends AppCompatActivity {

    Button login;
    Button signup;
    private FirebaseAuth auth;
    private EditText emailid,password;
    RelativeLayout activity;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button) findViewById(R.id.login);
        signup=(Button) findViewById(R.id.signup);


        emailid = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        //Initialize firebase

        auth=FirebaseAuth.getInstance();

        //Check if already in session

        if(auth.getCurrentUser()!=null)
            startActivity(new Intent(MainActivity.this,HomeActivity.class));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser(emailid.getText().toString(),password.getText().toString());




            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });



    }

    private void loginUser(String email,final String password){

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("login","login");

                if(!task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();

                }
                else
                {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                }
            }
        });

    }


}

