package com.example.byblosmobile;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.Intent;

import androidx.annotation.Nullable;

public class BranchDatabase extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "branches.db";
    public static final String TABLE_BRANCHES = "branches";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BRANCH_NAME = "name";

    public BranchDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_BRANCHES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_BRANCH_NAME
                + " TEXT)";
        db.execSQL(CREATE_PRODUCTS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRANCHES);
        onCreate(db);

    }

    public void addBranch(String branch){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BRANCH_NAME, branch);

        db.insert(TABLE_BRANCHES,null,values);
        db.close();

    }

    public boolean deleteBranch(String branch){
        boolean deleted = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM" + TABLE_BRANCHES + " WHERE" + COLUMN_BRANCH_NAME + " = \"" + branch + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String idString = cursor.getString(0);
            db.delete(TABLE_BRANCHES, COLUMN_ID + " = " + idString, null);
            deleted = true;
        }
        db.close();
        return deleted;

    }
}
