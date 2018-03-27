package myapplication2.com.ront;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by user on 27/3/18.
 */


public class LogoutActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(LogoutActivity.this,MainActivity.class));
    }
}
