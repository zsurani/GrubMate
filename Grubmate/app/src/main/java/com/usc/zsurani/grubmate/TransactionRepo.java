package com.usc.zsurani.grubmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Madison on 10/14/17.
 */

public class TransactionRepo {
    private DatabaseHandler dbHelper;

    public TransactionRepo(Context context){
        dbHelper = new DatabaseHandler(context);
    }

    public int insert(Transaction t) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Transaction.KEY_status, t.getStatus());
        values.put(Transaction.KEY_idProvider, t.getProviderID());
        values.put(Transaction.KEY_idRequester, t.getRequesterID());
        values.put(Transaction.KEY_locRequester, t.getLocation());
        values.put(Transaction.KEY_originalPostID, t.getPostID());

        // Inserting Row
        long transaction_Id = db.insert(Transaction.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) transaction_Id;
    }

}
