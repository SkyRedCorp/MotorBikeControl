package com.petertacon.bikecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_COLUMN_DRIVER;
import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_COLUMN_PASSWORD;
import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_COLUMN_PLATE;
import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_TABLE_NAME;

public class SettingsActivity extends AppCompatActivity {

    Button exitActivity, saveData;
    EditText driver, plate, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        exitActivity = findViewById(R.id.btnExit);
        saveData = findViewById(R.id.btnSave);
        driver = findViewById(R.id.txtDriver);
        plate = findViewById(R.id.txtPlate);
        password = findViewById(R.id.txtPassword);

        //loadInitialData();
    }

    private void loadInitialData() {
        SQLiteDBHelper Insectosarus = new SQLiteDBHelper((getApplicationContext()));
        Insectosarus.initialDBWrite();
    }

    public void onExitClick (View v) {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        exitActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveToDB() {
        SQLiteDatabase gamindarte = new SQLiteDBHelper(this).getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put(BIKE_COLUMN_DRIVER, driver.getText().toString());
        valores.put(BIKE_COLUMN_PLATE, plate.getText().toString());
        valores.put(BIKE_COLUMN_PASSWORD, password.getText().toString());

        gamindarte.insert(BIKE_TABLE_NAME, null, valores);
        Toast.makeText(SettingsActivity.this, "Datos guardados: " + valores, Toast.LENGTH_LONG).show();
        gamindarte.close();
    }

    public void onClick(View view) {
        saveToDB();
    }

    @Override
    public void onBackPressed(){
        // usado para evitar que cierre la app
    }

}