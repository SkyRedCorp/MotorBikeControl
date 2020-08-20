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


/*** Clase tipo Activity en la cual estará la interfaz de Configuraciones ***/

public class SettingsActivity extends AppCompatActivity {

    //Interfaz usada
    Button exitActivity, saveData;
    EditText driver, plate, password;

    //Método usado al momento de crear el Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Asignación de botones y textview con archivo XML
        exitActivity = findViewById(R.id.btnExit);
        saveData = findViewById(R.id.btnSave);
        driver = findViewById(R.id.txtDriver);
        plate = findViewById(R.id.txtPlate);
        password = findViewById(R.id.txtPassword);

    }

    //Método usado para salir de app
    public void onExitClick (View v) {
        final Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        //Click Listener para comunicar botón
        exitActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    //Método usado para guardar registro en SQLite
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