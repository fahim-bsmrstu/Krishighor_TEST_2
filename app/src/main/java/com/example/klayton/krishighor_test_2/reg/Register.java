package com.example.klayton.krishighor_test_2.reg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.klayton.krishighor_test_2.MainActivity;
import com.example.klayton.krishighor_test_2.R;
import com.example.klayton.krishighor_test_2.helper.SQLiteHandler;
import com.example.klayton.krishighor_test_2.helper.SessionManager;

public class Register extends AppCompatActivity {

    EditText e_name,e_user,e_pass,e_add,e_mob;
    Button reg;
    ProgressDialog progressDialog;
    SessionManager session;
    SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        e_name =(EditText)findViewById(R.id.fullName);
        e_user =(EditText)findViewById(R.id.userName);
        e_pass =(EditText)findViewById(R.id.password);
        e_add = (EditText)findViewById(R.id.editText);
        e_mob =(EditText)findViewById(R.id.mobile);
        reg = (Button)findViewById(R.id.register);


        //progress dialog

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        //session manager

        session = new SessionManager(getApplicationContext());

        // SQLite database Handler

        db = new SQLiteHandler(getApplicationContext());


        // Check the user is already logged in

        if(session.isLoggedIn()){

            // User is already logged in . Redirect him in main Page

            Intent i = new Intent(Register.this, MainActivity.class);
            startActivity(i);
            finish();


        }


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = e_name.getText().toString().trim();
                String user_name = e_user.getText().toString().trim();
                String pass = e_pass.getText().toString().trim();
                String address = e_add.getText().toString().trim();
                String mobile = e_mob.getText().toString().trim();

                if(!name.isEmpty() && !user_name.isEmpty() && !pass.isEmpty() && !address.isEmpty() && !mobile.isEmpty()){




                }
                else {
                    Toast.makeText(getApplicationContext(),"Please Enter Details",Toast.LENGTH_LONG).show();

                }


            }
        });









    }

}
