package com.petertacon.bikecontrol;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "BikeControl";
    public static final String BIKE_TABLE_NAME = "KmControl";
    public static final String BIKE_COLUMN_ID = "_id";
    public static final String BIKE_COLUMN_DRIVER = "Driver";
    public static final String BIKE_COLUMN_PLATE = "Plate";
    public static final String BIKE_COLUMN_KMINITIAL = "KmInitial";
    public static final String BIKE_COLUMN_KMFINAL = "KmFinal";
    public static final String BIKE_COLUMN_PASSWORD = "Password";


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
                + BIKE_COLUMN_KMFINAL + " INT, "
                + BIKE_COLUMN_PASSWORD + " INT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase basedatos, int oldVersion, int newVersion) {
        basedatos.execSQL("DROP TABLE IF EXISTS "+ BIKE_TABLE_NAME);
        onCreate(basedatos);
    }

    public void initialDBWrite() {
        SQLiteDatabase Tintalle = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BIKE_COLUMN_DRIVER, "Carlos Torres");
        values.put(BIKE_COLUMN_PLATE, "LRX44A");
        values.put(BIKE_COLUMN_KMINITIAL, "0001");
        values.put(BIKE_COLUMN_KMFINAL, "0002");
        values.put(BIKE_COLUMN_PASSWORD, "123456");
        Tintalle.insert(BIKE_TABLE_NAME, null, values);
        Tintalle.close();
    }

    public List<String> getPlateLabel() {
        List<String> plateList = new ArrayList<>();
        String queryData = " SELECT " + BIKE_COLUMN_PLATE + " FROM " + BIKE_TABLE_NAME;
        SQLiteDatabase JCheetos = this.getReadableDatabase();
        Cursor cursor = JCheetos.rawQuery(queryData, null);

        if (cursor.moveToFirst()) {
            do {
                plateList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        JCheetos.close();
        return plateList;
    }

    public List<String> loadData() {
        List<String> dataList = new ArrayList<>();
        SQLiteDatabase JamTemplar = this.getReadableDatabase();
        String queryData = "SELECT * " + " FROM " + BIKE_TABLE_NAME + " ORDER BY " + BIKE_COLUMN_ID + " DESC LIMIT 10";
        Cursor cursor = JamTemplar.rawQuery(queryData, null);
        if (cursor.moveToFirst()) {
            do {
                //ID.setText(cursor.getString(1));
                //Password.setText(cursor.getString(3));
                //Client.setText(cursor.getString(2));
                Log.d("TAG", cursor.getString(0) + " " +
                        cursor.getString(1) + " " +
                        cursor.getString(2) + " " +
                        cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        JamTemplar.close();
        return dataList;
    }

    public List<String> getDriverLabel() {
        List<String> driverList = new ArrayList<>();

        String queryData = " SELECT " + BIKE_COLUMN_DRIVER + " FROM " + BIKE_TABLE_NAME;
        SQLiteDatabase SkyRed = this.getReadableDatabase();
        Cursor cursor = SkyRed.rawQuery(queryData, null);

        if (cursor.moveToFirst()) {
            do {
                driverList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        SkyRed.close();
        return driverList;
    }

    public SkyRed Authenticate(SkyRed skyRed) {
        SQLiteDatabase JCheetos = this.getReadableDatabase();
        String query = "SELECT * " + " FROM " + BIKE_TABLE_NAME + " ORDER BY " +
                BIKE_COLUMN_ID + " DESC LIMIT 1";
        Cursor cursor = JCheetos.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            SkyRed skyRed1 = new SkyRed(cursor.getString(1), cursor.getString(2),
                    cursor.getString(5));
            if (skyRed.password.equalsIgnoreCase(skyRed1.password)) {
                return skyRed1;
            }
            //System.out.println(skyRed1);
            cursor.close();
            JCheetos.close();
        }
        return null;
    }

    public boolean BikeRegister(WTacon wTacon) {
        //Método usado para guardar registro en SQlite (MainActivity)
        SQLiteDatabase Lana = this.getWritableDatabase();
        boolean createSuccesful = Lana.insert(BIKE_TABLE_NAME, null, wTacon.toContentValues()) > 0;
        Lana.close();
        return createSuccesful;
    }
}
