package com.example.byblosmobile;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "branches.db";
    public static final String TABLE_SERVICES ="users";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INFO = "requiredInfo";
    public static final String COLUMN_Location = "branch";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean addService(Service service){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", service.getName());
        values.put("requiredInfo", service.getRequiredInfo());
        values.put("rate", service.getRate());
        values.put("branch", service.getBranch());

        long result = db.insert("services", null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

}
