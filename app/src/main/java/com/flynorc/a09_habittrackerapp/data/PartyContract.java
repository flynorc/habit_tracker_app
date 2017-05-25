package com.flynorc.a09_habittrackerapp.data;

import android.provider.BaseColumns;

/**
 * Created by Flynorc on 25-May-17.
 */

public final class PartyContract {
    //private constructor to prevent from instantiating the class
    private PartyContract() {}

    /**
     * Inner class that defines constants for the parties database table
     */
    public static final class PartyEntry implements BaseColumns {
        /** Name of database table for parties */
        public final static String TABLE_NAME = "parties";

        /**
         * Unique ID number for the party
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the party event.
         * Type: TEXT
         */
        public final static String COLUMN_PARTY_NAME ="name";

        /**
         * Date of the party.
         * Type: TEXT
         * Type could also be REAL or INTEGER, but in this implementation I decided to store it as TEXT in format YYYY-MM-DD
         */
        public final static String COLUMN_PARTY_DATE = "date";

        /**
         * Alcohol units consumed.
         * Type: INTEGER
         */
        public final static String COLUMN_ALCO_UNITS = "alco_units";

        /**
         * Description of the party (notes)
         * Type: TEXT
         */
        public final static String COLUMN_PARTY_DESCRIPTION = "description";

    }

}
