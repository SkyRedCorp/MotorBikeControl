package com.petertacon.bikecontrol;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BikeControl";
    public static final String BIKE_TABLE_NAME = "KmControl";
    public static final String BIKE_COLUMN_ID = "_id";
    public static final String BIKE_COLUMN_DRIVER = "Driver";
    public static final String BIKE_COLUMN_PLATE = "Plate";
    public static final String BIKE_COLUMN_KMINITIAL = "KmInitial";
    public static final String BIKE_COLUMN_KMFINAL = "KmFinal";


    public SQLiteDBHelper(Context contexto) {
        super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase basedatos) {
        basedatos.execSQL("CREATE TABLE " + BIKE_TABLE_NAME +
                " (" + BIKE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BIKE_COLUMN_DRIVER + " TEXT, "
                + BIKE_COLUMN_PLATE + " TEXT, "
                + BIKE_COLUMN_KMINITIAL + " INT, "
                + BIKE_COLUMN_KMFINAL + " INT" + ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase basedatos, int oldVersion, int newVersion) {
        basedatos.execSQL("DROP TABLE IF EXISTS "+ BIKE_TABLE_NAME);
        onCreate(basedatos);
    }

    public void initialDBWrite() {
        SQLiteDatabase Tintalle = this.getWritableDatabase();
        if (Tintalle == null) {
            ContentValues values = new ContentValues();
            values.put(BIKE_COLUMN_DRIVER, "Carlos Torres");
            values.put(BIKE_COLUMN_PLATE, "LRX44A");
            values.put(BIKE_COLUMN_KMINITIAL, "0001");
            values.put(BIKE_COLUMN_KMFINAL, "0002");
            Tintalle.insert(BIKE_TABLE_NAME, null, values);
            Tintalle.close();
        }
    }


    public void insertLabel(String label) {
        SQLiteDatabase basedatos = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BIKE_COLUMN_PLATE, label);
        values.put(BIKE_COLUMN_DRIVER, label);

        basedatos.insert(BIKE_TABLE_NAME, null, values);
        basedatos.close();
    }

    public List<String> getPlateLabel() {
        List<String> plateList = new ArrayList<>();

        String queryData = " SELECT " + BIKE_COLUMN_PLATE + " FROM " + BIKE_TABLE_NAME;
        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(queryData, null);

        if (cursor.moveToFirst()) {
            do {
                plateList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        basedatos.close();
        return plateList;
    }

    public List<String> getDriverLabel() {
        List<String> driverList = new ArrayList<>();

        String queryData = " SELECT " + BIKE_COLUMN_DRIVER + " FROM " + BIKE_TABLE_NAME;
        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(queryData, null);

        if (cursor.moveToFirst()) {
            do {
                driverList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        basedatos.close();
        return driverList;
    }

}
