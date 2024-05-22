package com.example.priemkomis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VhodKomis extends AppCompatActivity {
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginButton;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vhod_komis);
        TextView textView = findViewById(R.id.textView3);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        mEmailField = findViewById(R.id.username_edit_text);
        mPasswordField = findViewById(R.id.password_edit_text);
        mLoginButton = findViewById(R.id.login_button);
       String email = mEmailField.getText().toString().trim();
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmailField.getText().toString().trim();
                final String password = mPasswordField.getText().toString().trim();

                // Валидация адреса электронной почты
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Введите адрес электронной почты", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!isValidEmail(email)) {
                    Toast.makeText(getApplicationContext(), "Введите корректный адрес электронной почты", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Валидация пароля
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Пароль должен содержать минимум 6 символов", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Проверка учетных данных пользователя
                mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                User user = userSnapshot.getValue(User.class);
                                if (user != null && user.getPassword().equals(password)) {
                                    // Авторизация успешна
                                    Toast.makeText(getApplicationContext(), "Авторизация успешна", Toast.LENGTH_SHORT).show();
                                    // Здесь можно перейти на следующую активность или выполнить другие действия
                                    Intent intent = new Intent(VhodKomis.this, PodachaAct.class);
                                    startActivity(intent);
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Обработка ошибки
                        Toast.makeText(getApplicationContext(), "Ошибка: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VhodKomis.this,LoginAdmin.class);
                startActivity(intent);
            }
        });

        }
    // Метод для валидации адреса электронной почты
    private boolean isValidEmail(String email) {
        // Проверяем, не пуст ли email, и соответствует ли он стандартному формату адреса электронной почты
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}