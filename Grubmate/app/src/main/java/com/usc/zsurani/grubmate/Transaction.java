package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class Transaction {
    public static final String TABLE = "Transactions";
    public static final String KEY_id = "id";
    public static final String KEY_status = "status";
    public static final String KEY_idProvider = "idProvider";
    public static final String KEY_idRequester = "idRequester";
    public static final String KEY_locRequester = "locRequester";
    public static final String KEY_originalPostID = "originalPostID";

    Integer id;
    String status;
    Integer idProvider;
    Integer idRequester;
    Integer originalPostID;
    String locRequester;

    /*
     * Constructor setting provider and requester ids along
     * with the location of the requester and the id of the
     * original post
     */
    Transaction(Integer provider, Integer requester,
                String locOfRequester, Integer originalPost){}

    /*
     * Returns the User id of the provider
     */
    Integer getProviderID(){return idProvider;}

    /*
     * Returns the User id of the requester
     */
    Integer getRequesterID(){return idRequester;}

    /*
     * Returns the Post id that is associated with this request
     */
    Integer getPostID(){return originalPostID;}

    /*
     * Returns a string that represents the location inputed
     * by the provider
     */
    String getLocation(){return locRequester;}

    /*
     * Returns a string that represents the status of the Transaction
     */
    String getStatus(){return status;}

    /*
     * Sets the status as the inputted string
     */
    void setStatus(String status){this.status = status;}
}
