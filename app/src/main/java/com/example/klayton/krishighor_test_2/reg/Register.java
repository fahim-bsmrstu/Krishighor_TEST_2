package com.example.klayton.krishighor_test_2.reg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.klayton.krishighor_test_2.MainActivity;
import com.example.klayton.krishighor_test_2.R;
import com.example.klayton.krishighor_test_2.app.AppConfig;
import com.example.klayton.krishighor_test_2.app.AppController;
import com.example.klayton.krishighor_test_2.helper.SQLiteHandler;
import com.example.klayton.krishighor_test_2.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = Register.class.getSimpleName();

    EditText e_name,e_user,e_pass,e_add,e_mob;
    Button reg;
    private ProgressDialog progressDialog;
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
        e_add = (EditText)findViewById(R.id.location);
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

                    registerUser(name,user_name,pass,mobile,address);


                }
                else {
                    Toast.makeText(getApplicationContext(),"Please Enter Details",Toast.LENGTH_LONG).show();

                }


            }
        });









    }


    private void registerUser(final String name, final String user, final String pass, final String mob, final String location){



        String tag_string_req = "req_register";

        progressDialog.setMessage("Registering.....");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");

                    if(!error){

                        JSONObject user = jsonObject.getJSONObject("user");
                        String name = user.getString("name");
                        String user_name = user.getString("user_name");
                        String mob = user.getString("mobile");
                        String location = user.getString("location");


                       // db.addUser(name,user_name,mob,location);

                        Toast.makeText(getApplicationContext(), "User Successfully Registered", Toast.LENGTH_LONG).show();


                        // Launch Login Activity
                        /*
                        Intent i = new Intent(Register.this, Login.class);
                        startActivity(i);

                        */
                        finish();

                    } else {

                        String errorMsg = jsonObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();

            }
        }){
            @Override
            protected Map<String, String> getParams(){

                Map<String, String>params = new HashMap<String, String>();

                params.put("name",name);
                params.put("user_name",user);
                params.put("pass",pass);
                params.put("mob",mob);
                params.put("location",location);

                return params;


            }

        };


        // Adding requst to request queue
        AppController.getInstance().addToRequestQueue(strReq,tag_string_req);








    }

    private void showDialog(){

        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    private void hideDialog(){

        if(progressDialog.isShowing()){

            progressDialog.dismiss();
        }

    }




}
