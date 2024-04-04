package com.example.volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.*;

public class MainActivity extends AppCompatActivity {
    Button sing_in_btn, log_in_btn;
    TextView password_text, email_text;
    ConstraintLayout main;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        main = findViewById(R.id.main);
        sing_in_btn = findViewById(R.id.sing_in_btn);
        log_in_btn = findViewById(R.id.log_in_btn);
        password_text = findViewById(R.id.password_text);
        email_text = findViewById(R.id.email_text);

        mDatabaseHelper = new DatabaseHelper(MainActivity.this);
        mDatabase = mDatabaseHelper.getWritableDatabase();


        sing_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        log_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Перед входом пользователя, получите базу данных и количество записей
                int rowCount = mDatabaseHelper.getCount();
                Log.d("TableRows", "Number of rows in table: " + rowCount);
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = email_text.getText().toString().trim();
        String password = password_text.getText().toString().trim();
        Log.d("Переменные", "Email: " + email + " Пароль: " + password);

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Snackbar.make(main, "Заполните все поля", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (mDatabase != null) {
            // База данных открыта, можно выполнять операции с базой данных
            Cursor cursor = mDatabase.rawQuery("SELECT * FROM users WHERE email = ? AND user_password = ?", new String[]{email, password});
            cursor.close();
            Intent intent = new Intent(MainActivity.this, FeedActivity.class);
            startActivity(intent);
        } else {
            Log.e("Database", "База данных не открыта");
        }
    }
}




