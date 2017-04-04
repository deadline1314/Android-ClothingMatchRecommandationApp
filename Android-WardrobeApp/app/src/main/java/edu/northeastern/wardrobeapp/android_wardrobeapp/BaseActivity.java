package edu.northeastern.wardrobeapp.android_wardrobeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Extend from this class to access all menu/Firebase logic
 * By Ken
 */
public class BaseActivity extends AppCompatActivity {

    // Firebase variables
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    // Ken: Access Firebase methods through this variable
    protected DataAccess DA;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Auth
        mAuth = FirebaseAuth.getInstance();
        DA = new DataAccess();
        final Context mContext = this;
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    DA.setUserId(firebaseAuth.getCurrentUser().getUid());
                } else {
                    String currentActivity = mContext.getClass().getSimpleName();
                    if (!currentActivity.equals("LoginActivity")) {
                        // Ken: When no user is detected, ask for login
                        startActivity(new Intent(mContext, LoginActivity.class));
                    }
                }
            }
        };
    }

    public String getCurrentUserId() {
        return DA.getUserId();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void SignOut() {
        mAuth.signOut();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                SignOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
