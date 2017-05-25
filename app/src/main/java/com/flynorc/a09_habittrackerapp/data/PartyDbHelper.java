package com.flynorc.a09_habittrackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.flynorc.a09_habittrackerapp.data.PartyContract.PartyEntry;

/**
 * Created by Flynorc on 25-May-17.
 */

public class PartyDbHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    private static final String DATABASE_NAME = "party_tracker.db";

    /** Database version */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructor for the PartyDbHelper
     * @param context of the app
     */
    public PartyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the parties table
        String SQL_CREATE_PARTIES_TABLE =  "CREATE TABLE " + PartyEntry.TABLE_NAME + " ("
                + PartyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PartyEntry.COLUMN_PARTY_NAME + " TEXT NOT NULL, "
                + PartyEntry.COLUMN_PARTY_DATE + " TEXT NOT NULL, "
                + PartyEntry.COLUMN_ALCO_UNITS + " INTEGER NOT NULL, "
                + PartyEntry.COLUMN_PARTY_DESCRIPTION + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PARTIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
