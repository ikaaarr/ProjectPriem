package com.example.priemkomis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mRegisterButton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        mEmailField = findViewById(R.id.username_edit_text);
        mPasswordField = findViewById(R.id.password_edit_text);
        mRegisterButton = findViewById(R.id.register_button);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
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

                // Генерация уникального идентификатора пользователя
                final String userId = mDatabase.push().getKey();

                // Создание объекта пользователя для сохранения в базе данных
                User user = new User(email, password);

                // Сохранение пользователя в базе данных
                mDatabase.child(userId).setValue(user)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Регистрация успешна
                                    Toast.makeText(getApplicationContext(), "Пользователь добавлен", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    // Регистрация не удалась
                                    Toast.makeText(getApplicationContext(), "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    private boolean isValidEmail(String email) {
        // Проверяем, не пуст ли email, и соответствует ли он стандартному формату адреса электронной почты
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}