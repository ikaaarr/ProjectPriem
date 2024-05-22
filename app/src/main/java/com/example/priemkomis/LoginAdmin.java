package com.example.priemkomis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAdmin extends AppCompatActivity {
EditText editTextLogin, editTextPassword;
Button buttonEnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonEnter = findViewById(R.id.buttonEnter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Login = editTextLogin.getText().toString();
                String Pass = editTextPassword.getText().toString();
                if(Login.equals("Admin") & Pass.equals("Admin")){
                    Intent intent = new Intent(LoginAdmin.this, AdminActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginAdmin.this, "Выполняется вход", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(LoginAdmin.this, "Неверные данные!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}