package com.example.priemkomis;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class User {
    public String email;
    public String password;

    public User() {
        // Пустой конструктор, необходим для чтения из Firebase Realtime Database
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}