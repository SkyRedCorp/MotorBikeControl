package com.petertacon.bikecontrol;

import android.content.ContentValues;
import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_COLUMN_DRIVER;
import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_COLUMN_KMFINAL;
import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_COLUMN_KMINITIAL;
import static com.petertacon.bikecontrol.SQLiteDBHelper.BIKE_COLUMN_PLATE;

/*** Clase usada para consulta y escritura de base de datos (MainActivity)***/

public class WTacon {
    //Declarción de atributos
    private String driver;
    private String plate;
    private String kmInitial;
    private String kmFinal;

    public WTacon(String driver, String plate, String kmInitial, String kmFinal) {
        this.driver = driver;
        this.plate = plate;
        this.kmInitial = kmInitial;
        this.kmFinal = kmFinal;
    }

    ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(BIKE_COLUMN_DRIVER, driver);
        values.put(BIKE_COLUMN_PLATE, plate);
        values.put(BIKE_COLUMN_KMINITIAL, kmInitial);
        values.put(BIKE_COLUMN_KMFINAL, kmFinal);
        return values;
    }
}
