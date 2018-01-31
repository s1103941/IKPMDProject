package com.example.abdoul.ikpmdproject;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Controllers.UserController;
import Models.UserModel;

public class LoginActivity extends AppCompatActivity {

    private UserController controller;
    private Toast toast;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new UserController(this);
        setContentView(R.layout.activity_login);


    }

        public void login(View v){


            EditText userText = (EditText) findViewById(R.id.usernameText);
            EditText passText = (EditText) findViewById(R.id.passwordText);

            String username = userText.getText().toString();
            String password = passText.getText().toString();

            if (!controller.loginHey(username,password)){
                Snackbar mySnack = Snackbar.make(v, "Verkeerde gegevens ingevoerd", 3000);
                mySnack.show();
            }

            else {
                user = controller.getUser();
                Intent intent = new Intent(this, KeuzeActivity.class);
                intent.putExtra("gebruiker", user);
                startActivity(intent);
            }
        }

    }




