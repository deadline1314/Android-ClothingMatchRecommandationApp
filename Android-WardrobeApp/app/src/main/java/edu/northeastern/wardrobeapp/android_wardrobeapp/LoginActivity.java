package edu.northeastern.wardrobeapp.android_wardrobeapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.util.Log;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity {

    // NOTE: mAuth is defined in BaseActivity
    private static final String TAG = "LoginActivity";
    EditText editTxtEmail;
    EditText editTxtPassword;
    Button btnLogin;
    Button btnSignUp;
    TextView linkForgotPassword;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTxtEmail = (EditText) findViewById(R.id.input_email);
        editTxtPassword = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        linkForgotPassword = (TextView) findViewById(R.id.link_forgot_pass);
        Class context = this.getClass();
        String name = context.getSimpleName();
        Log.d(name, "Logged out " + name);
    }

    public void Login(View view) {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        // Block button
        btnLogin.setEnabled(false);
        showProgressDialog();

        String email = editTxtEmail.getText().toString().trim();
        String password = editTxtPassword.getText().toString().trim();

        // Login with Firebase
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    onLoginFailed();
                    progressDialog.dismiss();
                } else {
                    onLoginSuccess();
                }

            }
        });
    }

    public void SignUp(View view) {
        Intent i = new Intent(LoginActivity.this,UserProfileActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        // Ken: No need for Toast here, we can show a "Welcome" on the MainActivity instead
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void onLoginFailed() {
        Toast toast = Toast.makeText(getBaseContext(),"Sign in failed",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();

        btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = editTxtEmail.getText().toString();
        String password = editTxtPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTxtEmail.setError("Enter a valid email address");
            valid = false;
        } else {
            editTxtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            editTxtPassword.setError("Must be at least 6 characters");
            valid = false;
        } else {
            editTxtPassword.setError(null);
        }

        return valid;
    }

    private void showProgressDialog() {
        // Progress
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
    }
}

