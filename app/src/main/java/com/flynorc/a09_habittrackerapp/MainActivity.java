package com.flynorc.a09_habittrackerapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flynorc.a09_habittrackerapp.data.PartyContract.PartyEntry;
import com.flynorc.a09_habittrackerapp.data.PartyDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private PartyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //onclick handler for the button
        Button addButton = (Button) findViewById(R.id.add_entry);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDummyPartyToDb();
                displayDatabaseInfo();
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        dbHelper = new PartyDbHelper(this);

        //display database info (for debug)
        displayDatabaseInfo();
    }


    /**
     * Method for inserting a new party to the database
     * @param partyName
     * @param dateString
     * @param nrAlcoUnits
     * @param partyDescription
     * @return id of newly inserted row or -1 on error
     */
    private long insert(String partyName, String dateString, int nrAlcoUnits, String partyDescription) {
        // Gets the database in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and a combination of hardcoded and random attributes are set as values.
        ContentValues values = new ContentValues();
        values.put(PartyEntry.COLUMN_PARTY_NAME, partyName);
        values.put(PartyEntry.COLUMN_PARTY_DATE, dateString);
        values.put(PartyEntry.COLUMN_ALCO_UNITS, nrAlcoUnits);
        values.put(PartyEntry.COLUMN_PARTY_DESCRIPTION, partyDescription);

        // Insert a new row in the database, returning the ID of that new row.
        // The first argument for db.insert() is the table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for the party we are inserting.
        long newRowId = db.insert(PartyEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    /**
     * Medhod for reading all the data from the database
     * In a more realistic app there would be some options to add conditions, groupping, sorting
     * as parameters to the method to filter the results
     * @return the cursor
     */
    private Cursor read() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database we need
        String[] projection = {
                PartyEntry._ID,
                PartyEntry.COLUMN_PARTY_NAME,
                PartyEntry.COLUMN_PARTY_DATE,
                PartyEntry.COLUMN_ALCO_UNITS,
                PartyEntry.COLUMN_PARTY_DESCRIPTION };

        // Perform a query on the parties table
        Cursor cursor = db.query(
                PartyEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        return cursor;
    }

    /*
     * The following 2 methods are not required for meeting the project rubric
     * but are debug helper methods, to see the results of inserting to the database
     * and also reading from the database without the need to download the database file from the device
     */

    /**
     * Temporary helper method to insert some random data to database
     * In the real APP the data would come from a form (EditText fields and such)
     */
    private void insertDummyPartyToDb() {
        //make some random date in 2017 (until now)
        //1483228800 is the unix timestamp for 1.1.2017 and we need to multiply that with 1000
        //to get nr milliseconds instead of seconds
        long startTimestamp = 1483228800 * 1000L;
        Date now = new Date();
        //calculate a random value between the two timestamps
        long randomTimestamp = (long) Math.ceil(Math.random() * (now.getTime() - startTimestamp)) + startTimestamp;
        Date randomDate = new Date(randomTimestamp);
        String randomDateString = new SimpleDateFormat("yyyy-MM-dd").format(randomDate);

        //make a random integer from 1 to 20
        int randomNrAlco = (int) Math.ceil(Math.random() * 20);

        //call the insert method with our dummy data
        long insertedRowId = insert("Demo party", randomDateString, randomNrAlco, "Demo description");
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the parties database.
     */
    private void displayDatabaseInfo() {

        //get the cursor (query the DB)
        Cursor cursor = read();

        TextView displayView = (TextView) findViewById(R.id.debug_textview);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The parties table contains <number of rows in Cursor> parties.
            // _id - name - date - alcohol units - description
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The parties table contains " + cursor.getCount() + " parties.\n\n");
            displayView.append(PartyEntry._ID + " - " +
                    PartyEntry.COLUMN_PARTY_NAME + " - " +
                    PartyEntry.COLUMN_PARTY_DATE + " - " +
                    PartyEntry.COLUMN_ALCO_UNITS + " - " +
                    PartyEntry.COLUMN_PARTY_DESCRIPTION + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PartyEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PartyEntry.COLUMN_PARTY_NAME);
            int dateColumnIndex = cursor.getColumnIndex(PartyEntry.COLUMN_PARTY_DATE);
            int alcoColumnIndex = cursor.getColumnIndex(PartyEntry.COLUMN_ALCO_UNITS);
            int descColumnIndex = cursor.getColumnIndex(PartyEntry.COLUMN_PARTY_DESCRIPTION);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                int currentAlcoUnits = cursor.getInt(alcoColumnIndex);
                String currentDescription = cursor.getString(descColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentDate + " - " +
                        currentAlcoUnits + " - " +
                        currentDescription));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}
