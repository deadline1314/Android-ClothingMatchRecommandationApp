package edu.northeastern.wardrobeapp.android_wardrobeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    /*
     * Note from Ken:
     * On Submit/Next, just pass the password and email to
     * UserProfileActivity in a bundle
     */
}
