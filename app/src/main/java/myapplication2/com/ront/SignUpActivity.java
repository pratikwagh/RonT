package myapplication2.com.ront;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by user on 22/3/18.
 */

public class SignUpActivity extends AppCompatActivity {

    Button signup;
    private EditText emailid,password;
    private FirebaseAuth auth;
    private AwesomeValidation awesomeValidation;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup= findViewById(R.id.signup);

        emailid = (EditText) findViewById(R.id.editText4);
        password = (EditText) findViewById(R.id.editText6);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Awesome validation

        awesomeValidation.addValidation(this, R.id.editText4, Patterns.EMAIL_ADDRESS, R.string.emailiderror);
        awesomeValidation.addValidation(this, R.id.editText6, ".{6,}", R.string.passworderror);



        //Initialize firebase

        auth= FirebaseAuth.getInstance();

        //Check if already in session

        if(auth.getCurrentUser()!=null)
           startActivity(new Intent(SignUpActivity.this,HomeActivity.class));

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()) {

                    signUp(emailid.getText().toString(), password.getText().toString());
                }

            }
        });

    }

    private void signUp(String email, String password){

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {

                        if(!task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                        }
                    }
                });

    }
}