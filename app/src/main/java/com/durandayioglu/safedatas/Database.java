package com.durandayioglu.safedatas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duran Dayıoğlu on 27.07.2017.
 */

public class Database extends SQLiteOpenHelper {

    final private static String DATABASE_NAME = "mydatabase";
    final private static int DATABASE_VERSION = 1;
    final private static String DATABASE_TABLE = "mytable";

    public static final String ROW_ID = "id";
    public static final String ROW_AD = "ad";
    public static final String ROW_SOYAD = "soyad";
    public static final String ROW_EMAIL = "email";
    public static final String ROW_SIFRE = "sifre";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ROW_AD + " TEXT NOT NULL," + ROW_SOYAD + " TEXT NOT NULL," + ROW_EMAIL + " TEXT NOT NULL," + ROW_SIFRE + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + DATABASE_TABLE);
        onCreate(db);
    }

    public void veriEkle(String ad, String soyad, String email, String sifre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ROW_AD, ad.trim());
        cv.put(ROW_SOYAD, soyad.trim());
        cv.put(ROW_EMAIL, email.trim());
        cv.put(ROW_SIFRE, sifre.trim());
        db.insert(DATABASE_TABLE, null, cv);

        db.close();
    }

    public Cursor veriListele() {
        List<String> veriler = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] sutunlar = {ROW_ID, ROW_AD, ROW_SOYAD, ROW_EMAIL, ROW_SIFRE};
        Cursor cr = db.query(DATABASE_TABLE, sutunlar, null, null, null, null, null);

        return cr;
    }

    public void VeriSil(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, ROW_ID + "=" + id, null);
        db.close();
    }

    public void VeriDuzenle(long id, String ad, String soyad,String email,String sifre)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ROW_AD, ad);
        cv.put(ROW_SOYAD, soyad);
        cv.put(ROW_EMAIL, email);
        cv.put(ROW_SIFRE, sifre);

        db.update(DATABASE_TABLE, cv, ROW_ID + "=" + id, null);
        db.close();
    }
}
