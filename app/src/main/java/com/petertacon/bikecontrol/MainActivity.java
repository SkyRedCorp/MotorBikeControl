package com.petertacon.bikecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_COLUMN_KMFINAL;
import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_COLUMN_KMINITIAL;
import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_TABLE_NAME;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner plate, driver;
    EditText KmInitial, KmFinal;
    Button showData, saveData, checkNet, cleanData, syncData;
    TextView observer;
    private DatabaseReference firedata;

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
        checkNet = findViewById(R.id.btnChkNet);
        cleanData = findViewById(R.id.btnClearData);
        syncData = findViewById(R.id.btnSync);

        //TextView
        observer = findViewById(R.id.txtObservations);

        plate.setOnItemSelectedListener(this);
        driver.setOnItemSelectedListener(this);

        //firedata = FirebaseDatabase.getInstance().getReference();

        loadInitialData();
        loadPlateData();
        loadDriverData();
        loadinitKm();
        loadlastKm();


    }

    private void loadInitialData() {
        SQLiteDBHelper basedatos = new SQLiteDBHelper((getApplicationContext()));
        basedatos.initialDBWrite();
    }

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
        SQLiteDatabase Janeatus = new SQLiteDBHelper(this).getReadableDatabase();
        String queryData = " SELECT " + BIKE_COLUMN_KMINITIAL + " FROM " + BIKE_TABLE_NAME + " ORDER BY " + BIKE_COLUMN_KMINITIAL +" DESC LIMIT 1";
        Cursor cursor = Janeatus.rawQuery(queryData, null);
        if (cursor.moveToFirst()) {
            do {
                KmInitial.setText(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        Janeatus.close();
    }

    private void loadlastKm() {
        SQLiteDatabase Janeatus = new SQLiteDBHelper(this).getReadableDatabase();
        String queryData = " SELECT " + BIKE_COLUMN_KMFINAL + " FROM " + BIKE_TABLE_NAME + " ORDER BY " + BIKE_COLUMN_KMFINAL +" DESC LIMIT 1";
        Cursor cursor = Janeatus.rawQuery(queryData, null);
        if (cursor.moveToFirst()) {
            do {
                KmFinal.setText(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        Janeatus.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @IgnoreExtraProperties
    public class FireData {
        public String driver;
        public String plate;

        public FireData() {

        }
        public FireData(String driver, String plate) {
            this.driver = driver;
            this.plate = plate;
        }
    }

    private void writeNewData (String userId, String driver, String plate) {
        FireData fireData = new FireData(driver, plate);
        firedata.child("Data").child(userId).setValue(fireData);
    }

    public void onLoginClick(View v) {
        final Dialog loginDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog);
        loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.setContentView(R.layout.dialog_login);
        loginDialog.show();

        (loginDialog.findViewById(R.id.btnExit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String clave = ((EditText) loginDialog.findViewById(R.id.txtPassword)).getText().toString();
                if (clave.equals("123456")) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Login Correcto", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Login Incorrecto", Toast.LENGTH_SHORT).show();
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


}