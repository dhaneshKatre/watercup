package com.dk.watercup;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "WATERCUP";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_VILLAGE = "village";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VILLAGE_TABLE = "CREATE TABLE IF NOT EXISTS village ( name TEXT, taluka TEXT, phone TEXT );";
        db.execSQL(CREATE_VILLAGE_TABLE);
        Log.d(TAG, "village table created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS village");
        onCreate(db);
    }

    public void addInfo(VillageModel vm) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "INSERT INTO village VALUES ('"+vm.getName()+"','"+vm.getTaluka()+"','"+vm.getPhone()+"');";
        db.execSQL(insert);
        Log.d(TAG, "values inserted in village table sucessfully");
    }

    public void deleteVillages() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("village", null, null);
        db.close();
        Log.d(TAG, "Deleted all villages info from sqlite");
    }

    public HashMap<String, String> getAllValues() {
        HashMap<String, String> values = new HashMap<>();
        String select = "SELECT * FROM village;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            values.put("name", cursor.getString(0));
            values.put("taluka", cursor.getString(1));
            values.put("phone", cursor.getString(2));
        } else {
            Log.e(TAG,"CRITICAL ERROR!!");
            return null;
        }
        cursor.close();
        Log.d(TAG,"Fetching village: " + values.toString());
        return  values;
    }

}
