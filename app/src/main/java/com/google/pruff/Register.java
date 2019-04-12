package com.google.pruff;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText fName,lName,Phone,Dob,Email_add,Pass,RePass;
    private Button login_register,alreadyRegister;
    //FirebaseAuth auth;
   // DatabaseReference db;
    //FirebaseUser user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //auth = FirebaseAuth.getInstance();
        //db= FirebaseDatabase.getInstance().getReference();

        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        Phone = findViewById(R.id.Phone);
        Dob = findViewById(R.id.dobb);
        Email_add = findViewById(R.id.EmailIdRegister);
        Pass = findViewById(R.id.passRegister);
        RePass = findViewById(R.id.rePassRegister);
        login_register = findViewById(R.id.Register);
        alreadyRegister = findViewById(R.id.alreadyRegistered);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        alreadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,MainActivity.class));
                finish();
            }
        });

        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Fname = fName.getText().toString();
                String Lname = lName.getText().toString();
                String Phoneno = "+91"+Phone.getText().toString();
                String Date = Dob.getText().toString();
                String email = Email_add.getText().toString();
                String password = Pass.getText().toString();
                String repassword = RePass.getText().toString();

                //String id=db.push().getKey();

                if(TextUtils.isEmpty(Fname))
                {
                    Toast.makeText(getApplicationContext(),"Enter First Name",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(Lname))
                {
                    Toast.makeText(getApplicationContext(),"Enter Last Name",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(Phoneno))
                {
                    Toast.makeText(getApplicationContext(),"Enter Phone number",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(Date))
                {
                    Toast.makeText(getApplicationContext(),"Enter Date of Birth",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(repassword))
                {
                    Toast.makeText(getApplicationContext(),"Enter Password Again",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(repassword))
                {
                    Toast.makeText(getApplicationContext(),"Passwords don't match",Toast.LENGTH_SHORT).show();
                    return;
                }

                //sending data to firebase

             /*   user_id = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user_id.getUid();

                User user=new User(Fname,Lname,Phoneno,Date,email);
                db.child("profile").child(uid).setValue(user);*/

                Intent i = new Intent(Register.this,OneTimePassword.class);
                i.putExtra("PhoneNum",Phoneno);
                i.putExtra("firstName",Fname);
                i.putExtra("lastName",Lname);
                i.putExtra("DateOB",Date);
                i.putExtra("EMAil",email);
                i.putExtra("password",password);
                startActivity(i);

            }
        });

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

