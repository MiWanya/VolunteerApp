package com.example.volunteerapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

import java.util.Calendar;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    Button sing_in_btn;
    EditText password_text, email_text, city_text, middlename_text, surname_text, name_text, dob_text, phone_text;
    RadioGroup gender_radio_group;
    ConstraintLayout root;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraint_register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        sing_in_btn = findViewById(R.id.sing_in_btn);
        password_text = findViewById(R.id.password_text);
        email_text = findViewById(R.id.email_text);
        city_text = findViewById(R.id.city_text);
        middlename_text = findViewById(R.id.middlename_text);
        surname_text = findViewById(R.id.surname_text);
        name_text = findViewById(R.id.name_text);
        gender_radio_group = findViewById(R.id.gender_radio_group);
        root = findViewById(R.id.constraint_register);
        dob_text = findViewById(R.id.dob_text);
        phone_text = findViewById(R.id.phone_text);

        dob_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        sing_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        int selectedRadioButtonId = gender_radio_group.getCheckedRadioButtonId();
        String email = email_text.getText().toString().trim();
        String name = name_text.getText().toString().trim();
        String surname = surname_text.getText().toString().trim();
        String middlename = middlename_text.getText().toString().trim();
        String city = city_text.getText().toString().trim();
        String dob = dob_text.getText().toString().trim();
        String password = password_text.getText().toString().trim();
        String phone = phone_text.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) ||
                TextUtils.isEmpty(middlename) || TextUtils.isEmpty(city) || TextUtils.isEmpty(dob) ||
                TextUtils.isEmpty(password)) {
            Snackbar.make(root, "Заполните все поля", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 5) {
            Snackbar.make(root, "Введите более надёжный пароль", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (selectedRadioButtonId == -1) {
            Snackbar.make(root, "Выберите пол", Snackbar.LENGTH_SHORT).show();
            return;
        }

        String gender;
        if (selectedRadioButtonId == R.id.male_radio_button) {
            gender = "Мужской";
        } else {
            gender = "Женский";
        }

        // Вставка пользователя в базу данных
        long newRowId = dbHelper.insertUser(email, name, surname, middlename, password, phone, city, dob, gender);

        if (newRowId != -1) {
            Snackbar.make(root, "Пользователь успешно зарегистрирован", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(root, "Ошибка при регистрации пользователя", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "." + (month + 1) + "." + year;
                        dob_text.setText(date);
                    }
                },
                year, month, dayOfMonth);
        datePickerDialog.show();
    }
}