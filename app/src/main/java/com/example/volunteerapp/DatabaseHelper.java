package com.example.volunteerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "VolunteerDataBase.db";
    private static final int DB_VERSION = 1;
    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Обработка обновления базы данных, если это необходимо
    }

    public void copyDatabase() throws IOException {
        File dbFile = mContext.getDatabasePath(DB_NAME);
        if (!dbFile.exists() || !isTableExists("users")) {
            // Копирование базы данных только если таблица "users" не существует
            InputStream inputStream = mContext.getAssets().open(DB_NAME);
            OutputStream outputStream = new FileOutputStream(dbFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
    }

    private boolean isTableExists(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '" + tableName + "'", null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }


    public SQLiteDatabase openDatabase() throws SQLException {
        return getWritableDatabase();
    }

    private void logAllTables() {
        SQLiteDatabase db = getWritableDatabase(); // Изменено здесь
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        String tableName = cursor.getString(0);
                        Log.d("DatabaseHelper", "Table Name: " + tableName);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
    }

    public long insertUser(String email, String name, String surname, String middlename, String password, String phone, String city, String dob, String gender) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("user_name", name);
        values.put("user_secondname", surname);
        values.put("user_middlename", middlename);
        values.put("user_password", password);
        values.put("user_phone", phone);
        values.put("user_city", city);
        values.put("user_dateOfBirth", dob);
        values.put("user_gender", gender);

        long newRowId = db.insert("users", null, values);

        // Возвращаем идентификатор новой строки
        return newRowId;
    }

    public int getCount() {
        String countQuery = "SELECT COUNT(*) FROM users";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }


}
