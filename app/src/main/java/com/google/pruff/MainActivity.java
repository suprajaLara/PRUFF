package com.google.pruff;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {


    RelativeLayout rel2,rel3;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            rel2.setVisibility(View.VISIBLE);
            rel3.setVisibility(View.VISIBLE);

        }
    };

    private FirebaseAuth auth;
    private EditText email,password;
    private Button login,signup,forgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rel2 = findViewById(R.id.rel_lay_2);
        rel3 = findViewById(R.id.rel_lay_3);

        FirebaseApp.initializeApp(getApplicationContext());
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null)
        {

            startActivity(new Intent(MainActivity.this,FirstOpenActivity.class));
            finish();

        }

        handler.postDelayed(runnable,2000);



        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signUP);
        forgot_pass = findViewById(R.id.forgotPassword);
        login = findViewById(R.id.login);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Register.class));

            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // startActivity(new Intent(MainActivity.this,ForgotReset.class));
                resetPassword();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Eid = email.getText().toString();
                final String pass = password.getText().toString();

                if(TextUtils.isEmpty(Eid))
                {
                    Toast.makeText(getApplicationContext(),"Enter email address!!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    Toast.makeText(getApplicationContext(),"Enter password!!",Toast.LENGTH_LONG).show();
                    return;
                }

                auth.signInWithEmailAndPassword(Eid,pass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful())
                        {

                            if(pass.length()<6)
                            {
                                password.setError(getString(R.string.minimum_password));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),getString(R.string.auth_failed),Toast.LENGTH_LONG).show();
                            }

                        }
                        else
                        {
                            startActivity(new Intent(MainActivity.this,FirstOpenActivity.class));
                            finish();
                        }

                    }
                });
            }
        });

    }

    private void resetPassword(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_reset_password, null);
        dialogBuilder.setView(dialogView);
        final EditText editEmail = (EditText) dialogView.findViewById(R.id.email);
        final Button btnReset = (Button) dialogView.findViewById(R.id.btn_reset_password);
        final ProgressBar progressBar1 = (ProgressBar) dialogView.findViewById(R.id.progressBar);
        //dialogBuilder.setTitle("Send Photos");
        final AlertDialog dialog = dialogBuilder.create();
        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar1.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }
                                progressBar1.setVisibility(View.GONE);
                                dialog.dismiss();
                            }
                        });
            }
        });
        dialog.show();
    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this).setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Activities.this.finish();
            }
        }).setNegativeButton("No", null).show();
    }
}
//References : https://www.youtube.com/watch?v=-7xLyPLJ_NI