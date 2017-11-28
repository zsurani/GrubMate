package com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.usc.zsurani.grubmate.databases.DatabaseHandler;
import com.usc.zsurani.grubmate.base_classes.Transaction;

import java.util.ArrayList;
import java.util.List;

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

    //get list of transactions for the current user
    public List<String> getTransactionsId(int userId) {
        Log.d("DEBUG", "in getTransactionID");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + Transaction.KEY_id +
                " FROM " + Transaction.TABLE + " where " +
                Transaction.KEY_idProvider + " = " + userId
                + " OR " + Transaction.KEY_idRequester + " = " + userId;
        Cursor c = db.rawQuery(selectQuery, null);

        List<String> trans = new ArrayList<String>();
        if(c.moveToFirst()) {
            do {
                trans.add(c.getString(c.getColumnIndex(Transaction.KEY_id)));
                Log.d("DEBUG", c.getString(c.getColumnIndex(Transaction.KEY_id)));
            } while (c.moveToNext());
        }
        db.close();
        return trans;
    }

    //get list of active transactions for the current user
    public List<String> getActiveTransactionsId(int userId) {
        Log.d("DEBUG", "in getTransactionID");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Transaction.TABLE + " where " +
                Transaction.KEY_idProvider + " = " + userId
                + " OR " + Transaction.KEY_idRequester + " = " + userId;
        Cursor c = db.rawQuery(selectQuery, null);

        List<String> trans = new ArrayList<String>();
        if(c.moveToFirst()) {
            do {
                if (c.getString(c.getColumnIndex(Transaction.KEY_status)).equals("OPEN")) {
                    trans.add(c.getString(c.getColumnIndex(Transaction.KEY_id)));
                    Log.d("DEBUG", c.getString(c.getColumnIndex(Transaction.KEY_id)));
                }
            } while (c.moveToNext());
        }
        db.close();
        return trans;
    }

    public List<String> getPastTransactionsId(int userId) {
        Log.d("DEBUG", "in getTransactionID");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Transaction.TABLE + " where " +
                Transaction.KEY_idProvider + " = " + userId
                + " OR " + Transaction.KEY_idRequester + " = " + userId;
        Cursor c = db.rawQuery(selectQuery, null);

        List<String> trans = new ArrayList<String>();
        if(c.moveToFirst()) {
            do {
                if (c.getString(c.getColumnIndex(Transaction.KEY_status)).equals("CLOSED")) {
                    trans.add(c.getString(c.getColumnIndex(Transaction.KEY_id)));
                    Log.d("DEBUG", c.getString(c.getColumnIndex(Transaction.KEY_id)));
                }
            } while (c.moveToNext());
        }
        db.close();
        return trans;
    }

    //gets the instance of the Trasaction class from a transaction id
    public Transaction getTransaction(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + Transaction.KEY_idProvider + ", "
                + Transaction.KEY_idRequester + ", "
                + Transaction.KEY_locRequester + ", "
                + Transaction.KEY_originalPostID + ", "
                + Transaction.KEY_status +
                " FROM " + Transaction.TABLE + " where " +
                Transaction.KEY_id + " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);
        Transaction t = null;
        if(c.moveToFirst()) {
            do {
                String provider = c.getString(c.getColumnIndex(Transaction.KEY_idProvider));
                String requester = c.getString(c.getColumnIndex(Transaction.KEY_idRequester));
                String location = c.getString(c.getColumnIndex(Transaction.KEY_locRequester));
                String orginalPost = c.getString(c.getColumnIndex(Transaction.KEY_originalPostID));

                String status = c.getString(c.getColumnIndex(Transaction.KEY_status));

                t = new Transaction(Integer.parseInt(provider), Integer.parseInt(requester), location, Integer.parseInt(orginalPost));
                t.setId(id);
                t.setStatus(status);
            } while (c.moveToNext());
        }
        return t;
    }

    public Integer getProviderId(Integer id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + Transaction.KEY_idProvider +
                " FROM " + Transaction.TABLE + " where " +
                Transaction.KEY_id+ " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);

        Integer i = -1;
        if(c.moveToFirst()) {
            do {
                i = Integer.parseInt(c.getString(c.getColumnIndex(Transaction.KEY_idProvider)));
                //Log.d("DEBUG", c.getString(c.getColumnIndex(Transaction.KEY_id)));
            } while (c.moveToNext());
        }
        db.close();
        return i;
    }

    public void updateStatus(Integer id, String status) {
        Log.d("DEBUG", "inside of updateStatus, status = " + status);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Transaction.KEY_status, status);

        db.update(Transaction.TABLE, values, Transaction.KEY_id + "= " + id, null);
        Log.d("DEBUG", "id = " + id);
        db.close(); // Closing database connection
    }

}
