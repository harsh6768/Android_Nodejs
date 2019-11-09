package com.example.loginmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginmodule.Model.User;
import com.example.loginmodule.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText regUsername,regEmail,regPass;
    private Button regSignInBtn,regSignUpBtn;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regUsername=findViewById(R.id.register_nameId);
        regEmail=findViewById(R.id.register_email_id);
        regPass=findViewById(R.id.register_passId);
        regSignInBtn=findViewById(R.id.reg_signin_btnId);
        regSignUpBtn=findViewById(R.id.reg_signup_btnId);


        regSignUpBtn.setOnClickListener(this);
        regSignInBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id=v.getId();

        switch (id){

            case R.id.reg_signin_btnId:
                startActivity(new Intent(Register.this,MainActivity.class));
                break;
            case R.id.reg_signup_btnId:
                String mName=regUsername.getText().toString().trim();
                String mEmail=regEmail.getText().toString().trim();
                String mPass=regPass.getText().toString().trim();

                authRegister(mName,mEmail,mPass);
        }
    }

    //auth Login
    private void authRegister(String mName, String mEmail, String mPass) {


        retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.43.134:3000")   //192.168.43.134 is the ip address of the system 
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface=retrofit.create(ApiInterface.class);

        Call<User>  userCall=apiInterface.registerUser(mName,mEmail,mPass);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


                Log.i("Response",response.toString());
                if(response.code()==200){

                    Log.d("TAG", "onResponse: "+response.toString());
                    Toast.makeText(Register.this,"Register Successfully!",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Register.this,Welcome.class));

                }else if(response.code()==400){

                    Log.d("TAG", "onResponse: "+response.toString());
                    Toast.makeText(Register.this,"User already registered!",Toast.LENGTH_SHORT).show();
                }
                
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(Register.this,"Failed to register",Toast.LENGTH_LONG).show();

            }
        });


    }
}

