package com.azimgaurav.smartstudentassistant;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout textEtEmail,textEtPassword;
    EditText etEmail,etPassword;
    Button btnLogIn,btnRegister;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    String email,password;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize the components
        textEtEmail=findViewById(R.id.til_email);
        textEtPassword=findViewById(R.id.til_password);
        etEmail=textEtEmail.getEditText();
        etPassword=textEtPassword.getEditText();
        btnLogIn=findViewById(R.id.btn_login);
        btnRegister=findViewById(R.id.btn_register);

        //Set up firebase auth and database
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                {
                    //Toast.makeText(getApplicationContext(),"User is already logged",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        };

        //Handle user events
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logIn();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    //Log in existing user
    void logIn()
    {
        //Check internet connection
        if(isNetworkAvailable())
        {
            email=etEmail.getText().toString();
            password=etPassword.getText().toString();
            if(email.isEmpty())
            {
                textEtEmail.setError("Please enter Email ID!");
            }
            else if(password.isEmpty())
            {
                textEtPassword.setError("Please enter password!");
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                textEtEmail.setFocusable(true);
                textEtEmail.setError("Invalid Email ID!");
            }
            else
            {
                progressDialog = new ProgressDialog(this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setTitle("Logging in");
                progressDialog.setMessage("Please wait..");
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    progressDialog.dismiss();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                                else
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Invalid User!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
        else
        {
            Toast.makeText(LoginActivity.this, "Please turn on internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    //Register new user
    void registerUser()
    {

    }

    boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(LoginActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onResume() {
        etEmail.setText("");
        etPassword.setText("");
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);

        /*try {
            firebaseUser = firebaseAuth.getCurrentUser();
            boolean verified = firebaseUser.isEmailVerified();
            if (!verified)
            {
                Toast.makeText(this,"Please verify email",Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (firebaseUser != null)
                {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
                else
                {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }*/
    }
}