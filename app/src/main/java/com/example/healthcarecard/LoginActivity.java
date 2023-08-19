package com.example.healthcarecard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
TextView tv1,pass;
    Button b1;
    EditText mobile,password;
    TestAdapter adapter;
    String smedicalid,spassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv1=(TextView) findViewById(R.id.register);
        pass=(TextView)findViewById(R.id.password_link);

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Intent i=new Intent(LoginActivity.this,ForgetPassword.class);
startActivity(i);
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        b1=(Button)findViewById(R.id.btn_login);
        mobile=(EditText)findViewById(R.id.txt_mobile);
        password=(EditText)findViewById(R.id.txt_password);

        tv1=(TextView) findViewById(R.id.register);
        try {

            adapter=new TestAdapter(this);
            adapter.createDatabase();
            adapter.open();

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    smedicalid = mobile.getText().toString().trim();
                    spassword= password.getText().toString().trim();

                    if(TextUtils.isEmpty(smedicalid))
                    {
                        mobile.setError("Medical Id is Required.");
                        return;
                    }

                    if(TextUtils.isEmpty(spassword))
                    {
                        password.setError("Password is Required.");
                        return;
                    }

                    int i = adapter.checkUserLogin(smedicalid,spassword);
                    if (i == 1) {

                        userlogin();
                        return;

                    }

                    if (smedicalid.equalsIgnoreCase("9197")&&(spassword.equalsIgnoreCase("1234"))){
                        Intent intent = new Intent(getApplicationContext(), DoctorHomeActivity.class);
                        startActivity(intent);

                        Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        return;

                    }

                    else{
                        Toast.makeText(LoginActivity.this, "Invalid Medical ID Or Password", Toast.LENGTH_SHORT).show();
                        return;

                    }


                }
            });

        }catch (Exception e){}


    }

    private void userlogin() {
        final ProgressDialog dialog =
                new ProgressDialog(LoginActivity.this);
        dialog.setIcon(R.drawable.login);
        dialog.setTitle("Login");
        dialog.setMessage("Please wait User Login is Processing...");
        dialog.show();

        final Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                dialog.cancel();
                String mpass = null;

                Intent i = new Intent(LoginActivity.this, UserHomeActivity.class);
                i.putExtra("Key",smedicalid);
                startActivity(i);

                Toast.makeText(getApplicationContext(), "Your Login is Successfull..." , Toast.LENGTH_SHORT).show();

                mobile.setText("");
                password.setText("");


            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 6000);


    }


    }
