package com.example.priemkomis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {
    public static final String COLUMN_USERNAME = "username";

    public static final String COLUMN_PASSWORD = "password";

    // Название базы данных
    private static final String DATABASE_NAME = "users.db";
    // Таблица пользователей
    public static final String USERS_TABLE = "users";
    // Колонки таблицы пользователей
    public static final String COLUMN_ID = "_id";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создаем таблицу пользователей
        db.execSQL("CREATE TABLE " + USERS_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT" + " NOT NULL, " +
                COLUMN_PASSWORD + " TEXT" + " NOT NULL " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаляем таблицу, если она существует
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        // Создаем новую таблицу
        onCreate(db);
    }
}

