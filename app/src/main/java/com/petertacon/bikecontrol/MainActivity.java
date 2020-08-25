package com.petertacon.bikecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_COLUMN_KMFINAL;
import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_COLUMN_KMINITIAL;
import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_TABLE_NAME;

/*** Clase tipo Activity en la cual estará la interfaz de Configuraciones ***/

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //definición inicial de campos a usar
    Spinner plate, driver;
    EditText KmInitial, KmFinal;
    Button showData, saveData, cleanData, syncData;
    private DatabaseReference firedata;
    String driverText, plateText, KMInitial, KMFinal;

    SQLiteDBHelper database;

    /*** Método usado para creación de Activity ***/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //spinner
        plate = findViewById(R.id.spPlate);
        driver = findViewById(R.id.spDriver);

        //EditText
        KmInitial = findViewById(R.id.txtKmIniData);
        KmFinal = findViewById(R.id.txtKmFinData);

        //Button
        showData = findViewById(R.id.btnQuery);
        saveData = findViewById(R.id.btnSaveData);
        cleanData = findViewById(R.id.btnClearData);
        syncData = findViewById(R.id.btnSync);

        //TextView
        plate.setOnItemSelectedListener(this);
        driver.setOnItemSelectedListener(this);

        //Base de datos
        database = new SQLiteDBHelper(this);

        //firedata = FirebaseDatabase.getInstance().getReference();


        //función usada para determinar si hay un primer inicio de app
        //para cargar datos iniciales en SQLite
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        //condicional para verificar en SharedPreferences
        if (isFirstRun) {
            SQLiteDBHelper LeoZion = new SQLiteDBHelper((getApplicationContext()));
            LeoZion.initialDBWrite();
            Toast.makeText(MainActivity.this, "Datos cargados. Clave inicial:  123456", Toast.LENGTH_LONG).show();
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();

        //Carga de métodos donde se cargan los datos de la tabla
        loadPlateData();
        loadDriverData();
        loadinitKm();
        loadlastKm();
    }

    // Método usado para pausa de Activity
    @Override
    protected void onPause() {
        super.onPause();
    }

    // Métodos usados para carga de datos en Spinner y EditText
    private void loadPlateData() {
        SQLiteDBHelper basedatos = new SQLiteDBHelper(getApplicationContext());
        List<String> plateList = basedatos.getPlateLabel();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, plateList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plate.setAdapter(dataAdapter);
    }

    private void loadDriverData() {
        SQLiteDBHelper basedatos = new SQLiteDBHelper(getApplicationContext());
        List<String> driverList = basedatos.getDriverLabel();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, driverList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driver.setAdapter(dataAdapter);
    }

    private void loadinitKm() {
        SQLiteDatabase StickDead = new SQLiteDBHelper(this).getReadableDatabase();
        String queryData = " SELECT " + BIKE_COLUMN_KMINITIAL + " FROM " + BIKE_TABLE_NAME + " ORDER BY " + BIKE_COLUMN_KMINITIAL +" DESC LIMIT 1";
        Cursor cursor = StickDead.rawQuery(queryData, null);
        if (cursor.moveToFirst()) {
            do {
                KmInitial.setText(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        StickDead.close();
    }

    private void loadlastKm() {
        SQLiteDatabase Janeateus = new SQLiteDBHelper(this).getReadableDatabase();
        String queryData = " SELECT " + BIKE_COLUMN_KMFINAL + " FROM " + BIKE_TABLE_NAME +
                " ORDER BY " + BIKE_COLUMN_KMFINAL +" DESC LIMIT 1";
        Cursor cursor = Janeateus.rawQuery(queryData, null);
        if (cursor.moveToFirst()) {
            do {
                KmFinal.setText(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        Janeateus.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spPlate) {
            plateText = plate.getSelectedItem().toString();
        } else if ( parent.getId() == R.id.spDriver) {
            driverText = driver.getSelectedItem().toString();
        } 
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(MainActivity.this, "Seleccione Conductor y Placa",Toast.LENGTH_LONG).show();
    }

    // Método usado para apertura de diálogo de contraseña
    public void onLoginClick(View v) {
        final Dialog loginDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog);
        loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.setContentView(R.layout.dialog_login);
        loginDialog.show();

        (loginDialog.findViewById(R.id.btnExit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtPass;
                txtPass = loginDialog.findViewById(R.id.txtPassword);
                String clave = txtPass.getText().toString();
                SkyRed authPass = database.Authenticate(new SkyRed(null, null, clave));

                if (authPass != null) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Login Correcto",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Login Incorrecto",
                            Toast.LENGTH_SHORT).show();
                     txtPass.setText("");
                }
            }
        });

        (loginDialog.findViewById(R.id.btnExit2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
            }
        });

    }

    //método usado para limpiar campos
    public void onCleanClick(View v) {
        cleanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KmInitial.setText(" ");
                KmFinal.setText(" ");
            }
        });
    }

    //método usado para guardar los datos registrados
    public void onSaveClick(View v) {
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se usa Try para manejo de excepción de formato Numérico
                try {
                    //Conversión de String a Integer
                    int kmInitial = Integer.parseInt(KmInitial.getText().toString());
                    int kmFinal = Integer.parseInt(KmFinal.getText().toString());

                    // Condicional para verificar que Valor inicial sea menor al valor final
                    if (kmInitial > kmFinal) {
                        KmInitial.setError("El valor inicial, debe ser menor al final");
                        KmInitial.setText("");
                        KmFinal.setText("");
                    } else {
                        //Carga de datos a SQLite
                        KMInitial = KmInitial.getText().toString();
                        KMFinal = KmFinal.getText().toString();
                        boolean SaveData = database.BikeRegister(new WTacon(plateText, driverText,
                                KMInitial, KMFinal));
                        if (SaveData) {
                            Toast.makeText(MainActivity.this, "Registro Guardado.",
                                    Toast.LENGTH_SHORT).show();
                            KmInitial.setText("");
                            KmFinal.setText("");
                        } else {
                            Toast.makeText(MainActivity.this, "Error en registro.",
                                    Toast.LENGTH_SHORT).show();
                            KmInitial.setText("");
                            KmFinal.setText("");
                        }
                    }
                    // En caso de que algún campo esté vacío
                }catch (NumberFormatException nfe) {
                    KmInitial.setError("Inserte Datos");
                    KmFinal.setError("Inserte Datos");
                    KmInitial.setText("");
                    KmFinal.setText("");
                }

            }
        });
    }

    public void onOpenRegisterClick(View v) {
        showData.setOnClickListener(new View.OnClickListener() {
            final Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}