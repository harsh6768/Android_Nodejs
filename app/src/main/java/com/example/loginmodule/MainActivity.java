package com.example.loginmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginmodule.Model.User;
import com.example.loginmodule.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mainEmail,mainPass;
    private Button mainRegisterBtn,mainLoginBtn;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainEmail=findViewById(R.id.main_email_id);
        mainPass=findViewById(R.id.main_pass_id);
        mainRegisterBtn=findViewById(R.id.main_register_btnId);
        mainLoginBtn=findViewById(R.id.main_login_btnId);

        mainRegisterBtn.setOnClickListener(this);
        mainLoginBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id=v.getId();

        switch (id){

            case R.id.main_register_btnId:
                startActivity(new Intent(MainActivity.this,Register.class));
                break;
            case R.id.main_login_btnId:
                String mEmail=mainEmail.getText().toString().trim();
                String mPass=mainPass.getText().toString().trim();

                authLogin(mEmail,mPass);
        }
    }

    //register user
    private void authLogin(String mEmail, String mPass) {

        retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.43.134:3000") //base url for emulator 10.0.2.2
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface=retrofit.create(ApiInterface.class);

        Call<User> userCall=apiInterface.loginUser(mEmail,mPass);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.code()==200){
                    Log.d("TAG", "onResponse: "+response);
                    Toast.makeText(MainActivity.this,"Login Successfully!",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(MainActivity.this,Welcome.class));

                }else if(response.code()==400){
                    Toast.makeText(MainActivity.this,"Failed to register user",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}
