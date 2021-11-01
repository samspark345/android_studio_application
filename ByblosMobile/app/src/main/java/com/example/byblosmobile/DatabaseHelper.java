package com.example.byblosmobile;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "services.db";
    public static final String TABLE_SERVICES ="users";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INFO = "requiredInfo";
    public static final String COLUMN_Rate = "rate";
    public static final String COLUMN_Location = "service";
    public static final String COLUMN_ID = "_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_SERVICES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + "TEXT " + COLUMN_INFO
                + COLUMN_Rate +COLUMN_Location +"TEXT)";
        db.execSQL(CREATE_PRODUCTS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        onCreate(db);

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

    public void deleteService(String serviceName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM services WHERE name  = \"" + serviceName + "\"";
        db.execSQL(query);
    }

    public void updateService(String name, String info, String rate){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE services SET info = \"" + info + "\"" + " WHERE name = \"" + name + "\"";
        String query1 = "UPDATE services SET rate = \"" + rate + "\"" + " WHERE name = \"" + name + "\"";
        db.execSQL(query);
        db.execSQL(query1);
    }

    //method to view Data
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query= "Select * from services"  ;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

}
